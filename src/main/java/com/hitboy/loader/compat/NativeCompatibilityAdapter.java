package com.hitboy.loader.compat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Path;
import java.util.jar.JarFile;

public final class NativeCompatibilityAdapter implements HitBoyCompatibilityAdapter {
    @Override public SourceLoader getSourceLoader() { return SourceLoader.HITBOY; }
    @Override public boolean supports(JarFile jar) { return jar.getJarEntry("hitboy.json") != null; }

    @Override
    public HitBoyModDescriptor readDescriptor(Path path, JarFile jar) throws IOException {
        JsonObject json = JsonDescriptorSupport.read(jar, "hitboy.json");
        String name = JsonDescriptorSupport.requiredString(json, "name", "hitboy.json");
        String id = json.has("id") ? json.get("id").getAsString() : normalizeId(name);
        HitBoyModDescriptor.Builder builder = HitBoyModDescriptor.builder(path, SourceLoader.HITBOY)
            .id(id)
            .name(name)
            .version(JsonDescriptorSupport.requiredString(json, "version", "hitboy.json"))
            .environment(parseEnvironment(json.has("type") ? json.get("type").getAsString() : "universal"));
        boolean libraryOnly = json.has("libraryOnly") && json.get("libraryOnly").getAsBoolean();
        if (!libraryOnly) {
            builder.entrypoint("main", JsonDescriptorSupport.requiredString(json, "mainClass", "hitboy.json"));
        }
        if (json.has("dependsOn") && json.get("dependsOn").isJsonArray()) {
            for (JsonElement dependency : json.getAsJsonArray("dependsOn")) {
                builder.dependency(new HitBoyDependency(dependency.getAsString(), "*", true));
            }
        }
        return builder.build();
    }

    @Override
    public void analyze(HitBoyModDescriptor descriptor, JarFile jar, CompatibilityReport report) {
        report.add(CompatibilityIssue.Severity.INFO, "NATIVE", descriptor.getName() + " is a native HitBoy mod.");
    }

    private ModEnvironment parseEnvironment(String value) {
        if ("client".equalsIgnoreCase(value)) return ModEnvironment.CLIENT;
        if ("server".equalsIgnoreCase(value)) return ModEnvironment.SERVER;
        return ModEnvironment.UNIVERSAL;
    }

    private String normalizeId(String name) {
        return name.toLowerCase(java.util.Locale.ROOT).replaceAll("[^a-z0-9_-]+", "-").replaceAll("^-|-$", "");
    }
}
