package net.fabricmc.api;

/** Fabric's common entrypoint ("main" in fabric.mod.json), provided by HitBoy's Fabric runtime. */
@FunctionalInterface
public interface ModInitializer {
    void onInitialize();
}
