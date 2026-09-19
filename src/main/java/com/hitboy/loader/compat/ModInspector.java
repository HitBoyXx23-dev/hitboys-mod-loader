package com.hitboy.loader.compat;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class ModInspector {
    private final List<HitBoyCompatibilityAdapter> adapters = List.of(
        new NativeCompatibilityAdapter(),
        new FabricCompatibilityAdapter(),
        new ForgeCompatibilityAdapter(SourceLoader.NEOFORGE, "META-INF/neoforge.mods.toml"),
        new ForgeCompatibilityAdapter(SourceLoader.FORGE, "META-INF/mods.toml")
    );

    public Inspection inspect(Path path) throws IOException {
        try (JarFile jar = new JarFile(path.toFile())) {
            HitBoyCompatibilityAdapter adapter = adapters.stream().filter(candidate -> candidate.supports(jar)).findFirst()
                .orElseThrow(() -> new IOException("Unsupported mod descriptor in " + path.getFileName()));
            HitBoyModDescriptor descriptor = adapter.readDescriptor(path, jar);
            CompatibilityReport report = new CompatibilityReport();
            scanUnsafeContent(jar, report);
            adapter.analyze(descriptor, jar, report);
            return new Inspection(descriptor, report);
        }
    }

    private void scanUnsafeContent(JarFile jar, CompatibilityReport report) {
        jar.stream().map(JarEntry::getName).forEach(name -> {
            String lower = name.toLowerCase(Locale.ROOT);
            if (lower.endsWith(".dll") || lower.endsWith(".so") || lower.endsWith(".dylib")) {
                report.add(CompatibilityIssue.Severity.ERROR, "NATIVE_CODE", "Native library is blocked: " + name);
            }
            if (lower.contains("coremods") && lower.endsWith(".js")) {
                report.add(CompatibilityIssue.Severity.ERROR, "COREMOD_SCRIPT", "Coremod script requires manual review: " + name);
            }
        });
    }

    public static final class Inspection {
        private final HitBoyModDescriptor descriptor;
        private final CompatibilityReport report;

        Inspection(HitBoyModDescriptor descriptor, CompatibilityReport report) {
            this.descriptor = descriptor;
            this.report = report;
        }

        public HitBoyModDescriptor getDescriptor() { return descriptor; }
        public CompatibilityReport getReport() { return report; }
    }
}
