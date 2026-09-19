package com.hitboy.launcher;

import com.hitboy.loader.EventBus;
import com.hitboy.loader.ModManager;

/**
 * Context holder for HitBoy's Mod Loader launcher state.
 */
public class NavigationContext {
    private static EventBus eventBus;
    private static ModManager modManager;
    private static String gameDirectory;
    private static String minecraftVersion;
    private static String nativeModsDirectory;
    private static String mappingsFile;

    public static String defaultGameDirectory() {
        java.io.File home = new java.io.File(System.getProperty("user.home"));
        java.io.File hitBoyDirectory = new java.io.File(home, ".hitboys-modloader");
        java.io.File legacyDirectory = new java.io.File(home, ".hitboys-mod-loader");
        if (!hitBoyDirectory.exists() && legacyDirectory.isDirectory()) {
            try {
                java.nio.file.Files.move(legacyDirectory.toPath(), hitBoyDirectory.toPath());
                System.out.println("Migrated HitBoy data directory to " + hitBoyDirectory + ".");
            } catch (java.io.IOException e) {
                System.err.println("Could not migrate legacy HitBoy directory; continuing to use it: " + e.getMessage());
                return legacyDirectory.getAbsolutePath();
            }
        }
        return hitBoyDirectory.getAbsolutePath();
    }

    public static void initializeDefaults() {
        if (gameDirectory == null) {
            gameDirectory = defaultGameDirectory();
        }
        if (nativeModsDirectory == null) {
            nativeModsDirectory = gameDirectory + java.io.File.separator + "native_mods";
        }
        if (mappingsFile == null) {
            mappingsFile = gameDirectory + java.io.File.separator + "mappings.json";
        }
    }

    public static EventBus getEventBus() { return eventBus; }
    public static void setEventBus(EventBus eb) { eventBus = eb; }
    public static ModManager getModManager() { return modManager; }
    public static void setModManager(ModManager mm) { modManager = mm; }
    public static String getGameDirectory() { return gameDirectory; }
    public static void setGameDirectory(String s) { gameDirectory = s; }
    public static String getMinecraftVersion() { return minecraftVersion; }
    public static void setMinecraftVersion(String s) { minecraftVersion = s; }
    public static String getNativeModsDirectory() { return nativeModsDirectory; }
    public static void setNativeModsDirectory(String s) { nativeModsDirectory = s; }
    public static String getMappingsFile() { return mappingsFile; }
    public static void setMappingsFile(String s) { mappingsFile = s; }
}
