package com.hitboy.loader.compat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.jar.JarFile;

public final class FabricCompatibilityAdapter implements HitBoyCompatibilityAdapter {
    @Override public SourceLoader getSourceLoader() { return SourceLoader.FABRIC; }
    @Override public boolean supports(JarFile jar) { return jar.getJarEntry("fabric.mod.json") != null; }

    @Override
    public HitBoyModDescriptor readDescriptor(Path path, JarFile jar) throws IOException {
        JsonObject json = JsonDescriptorSupport.read(jar, "fabric.mod.json");
        String id = JsonDescriptorSupport.requiredString(json, "id", "fabric.mod.json");
        HitBoyModDescriptor.Builder builder = HitBoyModDescriptor.builder(path, SourceLoader.FABRIC)
            .id(id)
            .name(json.has("name") ? json.get("name").getAsString() : id)
            .version(JsonDescriptorSupport.requiredString(json, "version", "fabric.mod.json"))
            .environment(parseEnvironment(json.has("environment") ? json.get("environment").getAsString() : "*"));
        if (json.has("entrypoints") && json.get("entrypoints").isJsonObject()) {
            for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject("entrypoints").entrySet()) {
                for (String className : JsonDescriptorSupport.entrypoints(entry.getValue())) builder.entrypoint(entry.getKey(), className);
            }
        }
        addDependencies(builder, json, "depends", true);
        addDependencies(builder, json, "recommends", false);
        for (String mixin : JsonDescriptorSupport.strings(json.get("mixins"))) builder.mixinConfig(mixin);
        if (json.has("accessWidener")) builder.accessWidener(json.get("accessWidener").getAsString());
        if (json.has("jars") && json.get("jars").isJsonArray()) {
            for (JsonElement element : json.getAsJsonArray("jars")) {
                if (element.isJsonObject() && element.getAsJsonObject().has("file")) builder.nestedJar(element.getAsJsonObject().get("file").getAsString());
            }
        }
        return builder.build();
    }

    private void addDependencies(HitBoyModDescriptor.Builder builder, JsonObject json, String key, boolean required) {
        if (!json.has(key) || !json.get(key).isJsonObject()) return;
        for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject(key).entrySet()) {
            String range = entry.getValue().isJsonPrimitive() ? entry.getValue().getAsString() : entry.getValue().toString();
            builder.dependency(new HitBoyDependency(entry.getKey(), range, required));
        }
    }

    @Override
    public void analyze(HitBoyModDescriptor descriptor, JarFile jar, CompatibilityReport report) throws IOException {
        report.add(CompatibilityIssue.Severity.INFO, "FABRIC_DESCRIPTOR", "Fabric metadata was normalized successfully.");
        if (!descriptor.getMixinConfigs().isEmpty()) report.add(CompatibilityIssue.Severity.ERROR, "MIXIN_RUNTIME", "Mixin execution is not implemented for " + descriptor.getMixinConfigs());
        if (descriptor.getAccessWidener() != null) report.add(CompatibilityIssue.Severity.ERROR, "ACCESS_WIDENER", "Access widener transformation is not implemented: " + descriptor.getAccessWidener());
        if (!descriptor.getNestedJars().isEmpty()) report.add(CompatibilityIssue.Severity.ERROR, "NESTED_JARS", "Nested JAR extraction is not implemented for " + descriptor.getNestedJars());
        if (descriptor.getEntrypoints().isEmpty()) report.add(CompatibilityIssue.Severity.ERROR, "ENTRYPOINT", "No Fabric entrypoints were declared.");
        else report.add(CompatibilityIssue.Severity.ERROR, "FABRIC_API", "Fabric entrypoints require conversion to HitBoy lifecycle APIs.");
    }

    private ModEnvironment parseEnvironment(String value) {
        if ("client".equalsIgnoreCase(value)) return ModEnvironment.CLIENT;
        if ("server".equalsIgnoreCase(value)) return ModEnvironment.SERVER;
        return ModEnvironment.UNIVERSAL;
    }
}
