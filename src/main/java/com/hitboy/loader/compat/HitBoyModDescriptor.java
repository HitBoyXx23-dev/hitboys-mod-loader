package com.hitboy.loader.compat;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class HitBoyModDescriptor {
    private final String id;
    private final String name;
    private final String version;
    private final SourceLoader sourceLoader;
    private final ModEnvironment environment;
    private final Path sourceJar;
    private final Map<String, List<String>> entrypoints;
    private final List<HitBoyDependency> dependencies;
    private final List<String> mixinConfigs;
    private final List<String> nestedJars;
    private final String accessWidener;

    private HitBoyModDescriptor(Builder builder) {
        id = requireText(builder.id, "id");
        name = requireText(builder.name, "name");
        version = requireText(builder.version, "version");
        sourceLoader = Objects.requireNonNull(builder.sourceLoader, "sourceLoader");
        environment = Objects.requireNonNull(builder.environment, "environment");
        sourceJar = Objects.requireNonNull(builder.sourceJar, "sourceJar").toAbsolutePath().normalize();
        Map<String, List<String>> copiedEntrypoints = new LinkedHashMap<>();
        builder.entrypoints.forEach((key, value) -> copiedEntrypoints.put(key, List.copyOf(value)));
        entrypoints = Collections.unmodifiableMap(copiedEntrypoints);
        dependencies = List.copyOf(builder.dependencies);
        mixinConfigs = List.copyOf(builder.mixinConfigs);
        nestedJars = List.copyOf(builder.nestedJars);
        accessWidener = builder.accessWidener;
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value.trim();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getVersion() { return version; }
    public SourceLoader getSourceLoader() { return sourceLoader; }
    public ModEnvironment getEnvironment() { return environment; }
    public Path getSourceJar() { return sourceJar; }
    public Map<String, List<String>> getEntrypoints() { return entrypoints; }
    public List<HitBoyDependency> getDependencies() { return dependencies; }
    public List<String> getMixinConfigs() { return mixinConfigs; }
    public List<String> getNestedJars() { return nestedJars; }
    public String getAccessWidener() { return accessWidener; }

    public static Builder builder(Path sourceJar, SourceLoader sourceLoader) {
        return new Builder(sourceJar, sourceLoader);
    }

    public static final class Builder {
        private String id;
        private String name;
        private String version;
        private final SourceLoader sourceLoader;
        private ModEnvironment environment = ModEnvironment.UNIVERSAL;
        private final Path sourceJar;
        private final Map<String, List<String>> entrypoints = new LinkedHashMap<>();
        private final List<HitBoyDependency> dependencies = new ArrayList<>();
        private final List<String> mixinConfigs = new ArrayList<>();
        private final List<String> nestedJars = new ArrayList<>();
        private String accessWidener;

        private Builder(Path sourceJar, SourceLoader sourceLoader) {
            this.sourceJar = sourceJar;
            this.sourceLoader = sourceLoader;
        }

        public Builder id(String value) { id = value; return this; }
        public Builder name(String value) { name = value; return this; }
        public Builder version(String value) { version = value; return this; }
        public Builder environment(ModEnvironment value) { environment = value; return this; }
        public Builder accessWidener(String value) { accessWidener = value; return this; }
        public Builder entrypoint(String type, String className) {
            entrypoints.computeIfAbsent(type, ignored -> new ArrayList<>()).add(className);
            return this;
        }
        public Builder dependency(HitBoyDependency value) { dependencies.add(value); return this; }
        public Builder mixinConfig(String value) { mixinConfigs.add(value); return this; }
        public Builder nestedJar(String value) { nestedJars.add(value); return this; }
        public HitBoyModDescriptor build() { return new HitBoyModDescriptor(this); }
    }
}
