package net.fabricmc.api;

/** Fabric's client entrypoint ("client" in fabric.mod.json). */
@FunctionalInterface
public interface ClientModInitializer {
    void onInitializeClient();
}
