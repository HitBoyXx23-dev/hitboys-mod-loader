package net.fabricmc.loader.api.entrypoint;

/** Fabric's "preLaunch" entrypoint, run before Minecraft starts. */
@FunctionalInterface
public interface PreLaunchEntrypoint {
    void onPreLaunch();
}
