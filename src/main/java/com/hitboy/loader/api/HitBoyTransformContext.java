package com.hitboy.loader.api;

import java.util.Objects;

public final class HitBoyTransformContext {
    private final String minecraftVersion;
    private final String sourceNamespace;
    private final String runtimeNamespace;

    public HitBoyTransformContext(String minecraftVersion, String sourceNamespace, String runtimeNamespace) {
        this.minecraftVersion = Objects.requireNonNull(minecraftVersion, "minecraftVersion");
        this.sourceNamespace = Objects.requireNonNull(sourceNamespace, "sourceNamespace");
        this.runtimeNamespace = Objects.requireNonNull(runtimeNamespace, "runtimeNamespace");
    }

    public String getMinecraftVersion() { return minecraftVersion; }
    public String getSourceNamespace() { return sourceNamespace; }
    public String getRuntimeNamespace() { return runtimeNamespace; }
}
