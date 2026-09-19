package com.hitboy.loader.api;

import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

public final class HitBoyPlatform {
    private static final Map<String, ModMetadata> MODS = new LinkedHashMap<>();
    private static final Map<String, BiFunction<String, byte[], byte[]>> TRANSFORMERS = new LinkedHashMap<>();

    private HitBoyPlatform() {}

    public static Path getGameDirectory() {
        return Path.of(System.getProperty("hitboy.game-directory", ".hitboys-modloader"))
            .toAbsolutePath().normalize();
    }

    public static Path getConfigDirectory() {
        return getGameDirectory().resolve("config");
    }

    public static boolean isDevelopmentEnvironment() {
        return Boolean.getBoolean("hitboy.development");
    }

    public static MappingService getMappingService() {
        return MappingHolder.SERVICE;
    }

    public static synchronized void registerMod(String id, String name, String version, Map<String, String> customValues) {
        MODS.put(Objects.requireNonNull(id), new ModMetadata(id, name, version, customValues));
    }

    public static synchronized Optional<ModMetadata> getMod(String id) {
        return Optional.ofNullable(MODS.get(id));
    }

    public static synchronized Map<String, ModMetadata> getMods() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(MODS));
    }

    public static synchronized void registerBytecodeTransformer(
        String id, BiFunction<String, byte[], byte[]> transformer
    ) {
        if (TRANSFORMERS.putIfAbsent(id, Objects.requireNonNull(transformer)) != null) {
            throw new IllegalArgumentException("Duplicate bytecode transformer: " + id);
        }
    }

    public static synchronized byte[] transformBytecode(String className, byte[] bytecode) {
        byte[] transformed = bytecode;
        for (Map.Entry<String, BiFunction<String, byte[], byte[]>> entry : TRANSFORMERS.entrySet()) {
            transformed = Objects.requireNonNull(
                entry.getValue().apply(className, transformed),
                "Transformer returned null: " + entry.getKey()
            );
        }
        return transformed;
    }

    public record ModMetadata(String id, String name, String version, Map<String, String> customValues) {
        public ModMetadata {
            Objects.requireNonNull(id);
            Objects.requireNonNull(name);
            Objects.requireNonNull(version);
            customValues = Map.copyOf(customValues);
        }

        public String getCustomValue(String key) {
            return customValues.get(key);
        }
    }

    public static final class MappingService {
        public String mapClassName(String fromNamespace, String className) { return className; }
        public String mapFieldName(String fromNamespace, String owner, String name, String descriptor) { return name; }
        public String mapMethodName(String fromNamespace, String owner, String name, String descriptor) { return name; }
    }

    private static final class MappingHolder {
        private static final MappingService SERVICE = new MappingService();
    }
}
