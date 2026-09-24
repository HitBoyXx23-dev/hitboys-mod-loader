package net.fabricmc.api;

/** Fabric's dedicated-server entrypoint ("server" in fabric.mod.json). */
@FunctionalInterface
public interface DedicatedServerModInitializer {
    void onInitializeServer();
}
