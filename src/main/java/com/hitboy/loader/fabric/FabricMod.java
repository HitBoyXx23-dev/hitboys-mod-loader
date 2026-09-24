package com.hitboy.loader.fabric;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.metadata.CustomValue;
import net.fabricmc.loader.api.metadata.ModMetadata;

/** One Fabric mod: its fabric.mod.json plus the remapped JAR HitBoy runs it from. */
final class FabricMod implements ModContainer, ModMetadata {
    final JsonObject json;
    final Path sourceJar;
    Path runtimeJar;
    private FileSystem fileSystem;

    FabricMod(JsonObject json, Path sourceJar) {
        this.json = json;
        this.sourceJar = sourceJar;
    }

    // ---- ModContainer ----

    @Override public ModMetadata getMetadata() { return this; }

    @Override
    public synchronized List<Path> getRootPaths() {
        try {
            if (fileSystem == null) fileSystem = FileSystems.newFileSystem(runtimeJar != null ? runtimeJar : sourceJar, (ClassLoader) null);
            return Collections.singletonList(fileSystem.getPath("/"));
        } catch (java.io.IOException exception) {
            throw new IllegalStateException("Could not open " + sourceJar, exception);
        }
    }

    // ---- ModMetadata ----

    @Override public String getType() { return "fabric"; }
    @Override public String getId() { return json.get("id").getAsString(); }
    @Override public Version getVersion() { return FabricVersion.parseLenient(string("version", "0")); }
    @Override public String getName() { return string("name", getId()); }
    @Override public String getDescription() { return string("description", ""); }

    @Override
    public Collection<String> getProvides() {
        List<String> provides = new ArrayList<>();
        if (json.has("provides") && json.get("provides").isJsonArray()) {
            for (JsonElement value : json.getAsJsonArray("provides")) provides.add(value.getAsString());
        }
        return provides;
    }

    @Override
    public Optional<String> getIconPath(int size) {
        if (!json.has("icon")) return Optional.empty();
        JsonElement icon = json.get("icon");
        if (icon.isJsonPrimitive()) return Optional.of(icon.getAsString());
        String best = null;
        for (Map.Entry<String, JsonElement> entry : icon.getAsJsonObject().entrySet()) best = entry.getValue().getAsString();
        return Optional.ofNullable(best);
    }

    @Override
    public boolean containsCustomValue(String key) {
        return custom().has(key);
    }

    @Override
    public CustomValue getCustomValue(String key) {
        return custom().has(key) ? FabricCustomValue.of(custom().get(key)) : null;
    }

    @Override
    public Map<String, CustomValue> getCustomValues() {
        Map<String, CustomValue> values = new LinkedHashMap<>();
        for (Map.Entry<String, JsonElement> entry : custom().entrySet()) values.put(entry.getKey(), FabricCustomValue.of(entry.getValue()));
        return values;
    }

    // ---- fabric.mod.json helpers ----

    List<String> mixinConfigs() {
        List<String> configs = new ArrayList<>();
        if (!json.has("mixins")) return configs;
        for (JsonElement entry : json.getAsJsonArray("mixins")) {
            if (entry.isJsonPrimitive()) configs.add(entry.getAsString());
            else if (entry.isJsonObject() && entry.getAsJsonObject().has("config")) {
                JsonObject object = entry.getAsJsonObject();
                if (object.has("environment") && "server".equals(object.get("environment").getAsString())) continue;
                configs.add(object.get("config").getAsString());
            }
        }
        return configs;
    }

    String accessWidener() {
        return json.has("accessWidener") ? json.get("accessWidener").getAsString() : null;
    }

    /** Entrypoint class/member references for a key such as "main" or "client". */
    List<String> entrypoints(String key) {
        List<String> values = new ArrayList<>();
        if (!json.has("entrypoints") || !json.getAsJsonObject("entrypoints").has(key)) return values;
        for (JsonElement entry : json.getAsJsonObject("entrypoints").getAsJsonArray(key)) {
            if (entry.isJsonPrimitive()) values.add(entry.getAsString());
            else if (entry.isJsonObject() && entry.getAsJsonObject().has("value")) values.add(entry.getAsJsonObject().get("value").getAsString());
        }
        return values;
    }

    /** Required dependency ids from "depends". */
    List<String> requiredDependencies() {
        List<String> ids = new ArrayList<>();
        if (json.has("depends") && json.get("depends").isJsonObject()) ids.addAll(json.getAsJsonObject("depends").keySet());
        return ids;
    }

    boolean isClientCompatible() {
        return !"server".equals(string("environment", "*"));
    }

    private JsonObject custom() {
        return json.has("custom") && json.get("custom").isJsonObject() ? json.getAsJsonObject("custom") : new JsonObject();
    }

    private String string(String key, String fallback) {
        return json.has(key) && json.get(key).isJsonPrimitive() ? json.get(key).getAsString() : fallback;
    }

    @Override public String toString() { return getName() + " " + getVersion().getFriendlyString(); }
}
