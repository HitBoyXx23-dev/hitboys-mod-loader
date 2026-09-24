package com.hitboy.loader.compat;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class InstanceVerifier {
    private final ModInspector inspector = new ModInspector();

    public Result verify(Path modsDirectory) throws IOException {
        List<ModInspector.Inspection> inspections = new ArrayList<>();
        CompatibilityReport aggregate = new CompatibilityReport();
        if (!Files.isDirectory(modsDirectory)) return new Result(inspections, aggregate);
        List<Path> jars = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(modsDirectory, "*.jar")) {
            for (Path path : stream) if (!com.hitboy.loader.ActiveMods.isSkipped(path)) jars.add(path);
        }
        jars.sort(Comparator.comparing(path -> path.getFileName().toString(), String.CASE_INSENSITIVE_ORDER));
        Map<String, HitBoyModDescriptor> ids = new HashMap<>();
        for (Path jar : jars) {
            try {
                ModInspector.Inspection inspection = inspector.inspect(jar);
                inspections.add(inspection);
                HitBoyModDescriptor descriptor = inspection.getDescriptor();
                if (descriptor.getSourceLoader() != SourceLoader.HITBOY) {
                    aggregate.add(
                        CompatibilityIssue.Severity.ERROR,
                        "PORT_REQUIRED",
                        jar.getFileName() + " is a " + descriptor.getSourceLoader()
                            + " mod. Port it to the HitBoy API and add hitboy.json before launching."
                    );
                }
                HitBoyModDescriptor duplicate = ids.putIfAbsent(descriptor.getId().toLowerCase(Locale.ROOT), descriptor);
                if (duplicate != null) aggregate.add(CompatibilityIssue.Severity.ERROR, "DUPLICATE_MOD", descriptor.getId() + " is declared by " + duplicate.getSourceJar().getFileName() + " and " + jar.getFileName());
                for (CompatibilityIssue issue : inspection.getReport().getIssues()) aggregate.add(issue.getSeverity(), issue.getCode(), jar.getFileName() + ": " + issue.getMessage());
            } catch (IOException exception) {
                aggregate.add(CompatibilityIssue.Severity.ERROR, "DESCRIPTOR", jar.getFileName() + ": " + exception.getMessage());
            }
        }
        for (ModInspector.Inspection inspection : inspections) {
            for (HitBoyDependency dependency : inspection.getDescriptor().getDependencies()) {
                if (dependency.isRequired() && !isPlatformDependency(dependency.getId()) && !ids.containsKey(dependency.getId().toLowerCase(Locale.ROOT))) {
                    aggregate.add(CompatibilityIssue.Severity.ERROR, "MISSING_DEPENDENCY", inspection.getDescriptor().getId() + " requires " + dependency.getId() + " " + dependency.getVersionRange());
                }
            }
        }
        return new Result(inspections, aggregate);
    }

    public void requireCompatible(Path modsDirectory) throws IOException, CompatibilityException {
        Result result = verify(modsDirectory);
        if (!result.getReport().isCompatible()) throw new CompatibilityException("HitBoy compatibility verification failed:" + System.lineSeparator() + result.getReport().format());
    }

    /**
     * Like {@link #requireCompatible(Path)}, but for a mods folder shared with other loaders (the official
     * Launcher's .minecraft\mods): Fabric, Forge, and NeoForge JARs are skipped with a warning instead of
     * aborting the game, and only HitBoy mods must verify cleanly.
     */
    public void requireCompatibleHitBoyMods(Path modsDirectory) throws IOException, CompatibilityException {
        Result result = verify(modsDirectory);
        java.util.Set<String> hitBoyJars = new java.util.HashSet<>();
        java.util.Set<String> hitBoyIds = new java.util.HashSet<>();
        for (ModInspector.Inspection inspection : result.getInspections()) {
            HitBoyModDescriptor descriptor = inspection.getDescriptor();
            String jarName = descriptor.getSourceJar().getFileName().toString();
            if (descriptor.getSourceLoader() == SourceLoader.HITBOY) {
                hitBoyJars.add(jarName);
                hitBoyIds.add(descriptor.getId());
            } else {
                System.out.println("Skipping " + descriptor.getSourceLoader() + " mod (not a HitBoy mod): " + jarName);
            }
        }
        CompatibilityReport hitBoyReport = new CompatibilityReport();
        for (CompatibilityIssue issue : result.getReport().getIssues()) {
            if (issue.getSeverity() != CompatibilityIssue.Severity.ERROR || "PORT_REQUIRED".equals(issue.getCode())) continue;
            if (hitBoyJars.stream().anyMatch(jar -> issue.getMessage().contains(jar))
                || hitBoyIds.stream().anyMatch(id -> issue.getMessage().startsWith(id + " requires "))) {
                hitBoyReport.add(issue.getSeverity(), issue.getCode(), issue.getMessage());
            }
        }
        if (!hitBoyReport.isCompatible()) throw new CompatibilityException("HitBoy compatibility verification failed:" + System.lineSeparator() + hitBoyReport.format());
    }

    private boolean isPlatformDependency(String id) {
        return id.equalsIgnoreCase("minecraft") || id.equalsIgnoreCase("java") || id.equalsIgnoreCase("fabricloader") || id.equalsIgnoreCase("forge") || id.equalsIgnoreCase("neoforge");
    }

    public static final class Result {
        private final List<ModInspector.Inspection> inspections;
        private final CompatibilityReport report;
        Result(List<ModInspector.Inspection> inspections, CompatibilityReport report) { this.inspections = List.copyOf(inspections); this.report = report; }
        public List<ModInspector.Inspection> getInspections() { return inspections; }
        public CompatibilityReport getReport() { return report; }
    }
}
