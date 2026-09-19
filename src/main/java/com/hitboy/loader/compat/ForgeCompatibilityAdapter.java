package com.hitboy.loader.compat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class ForgeCompatibilityAdapter implements HitBoyCompatibilityAdapter {
    private final SourceLoader sourceLoader;
    private final String descriptorPath;

    public ForgeCompatibilityAdapter(SourceLoader sourceLoader, String descriptorPath) {
        if (sourceLoader != SourceLoader.FORGE && sourceLoader != SourceLoader.NEOFORGE) {
            throw new IllegalArgumentException("Forge adapter requires FORGE or NEOFORGE source loader");
        }
        this.sourceLoader = sourceLoader;
        this.descriptorPath = descriptorPath;
    }

    @Override public SourceLoader getSourceLoader() { return sourceLoader; }
    @Override public boolean supports(JarFile jar) { return jar.getJarEntry(descriptorPath) != null; }

    @Override
    public HitBoyModDescriptor readDescriptor(Path path, JarFile jar) throws IOException {
        String toml = read(jar);
        String id = value(toml, "modId", true);
        String name = value(toml, "displayName", false);
        String version = value(toml, "version", true);
        return HitBoyModDescriptor.builder(path, sourceLoader)
            .id(id)
            .name(name == null ? id : name)
            .version(version)
            .environment(ModEnvironment.UNIVERSAL)
            .build();
    }

    @Override
    public void analyze(HitBoyModDescriptor descriptor, JarFile jar, CompatibilityReport report) {
        report.add(CompatibilityIssue.Severity.INFO, "FORGE_DESCRIPTOR", sourceLoader + " metadata was normalized successfully.");
        report.add(CompatibilityIssue.Severity.ERROR, "ANNOTATION_ENTRYPOINT", sourceLoader + " annotation entrypoints require HitBoy bytecode discovery.");
        report.add(CompatibilityIssue.Severity.ERROR, "LOADER_API", sourceLoader + " event, registry, networking, and transformer APIs are not implemented yet.");
    }

    private String read(JarFile jar) throws IOException {
        JarEntry entry = jar.getJarEntry(descriptorPath);
        try (InputStream input = jar.getInputStream(entry)) {
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private String value(String toml, String key, boolean required) throws IOException {
        Matcher matcher = Pattern.compile("(?m)^\\s*" + Pattern.quote(key) + "\\s*=\\s*[\"']([^\"']+)[\"']").matcher(toml);
        if (matcher.find()) return matcher.group(1).trim();
        if (required) throw new IOException(descriptorPath + " requires " + key);
        return null;
    }
}
