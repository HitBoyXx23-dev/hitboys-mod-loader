package com.hitboy.loader;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarFile;

/**
 * HitBoy's Mixed Compatible Mod Loader picks its engine when the game starts. With NeoForge or Forge mods
 * in the mods folder, NeoForge runs underneath (Fabric mods are converted, Forge mods ported). Without
 * them, HitBoy runs the game itself, where Fabric mods (Fabric API, Sodium, Iris, ...) have full support.
 */
public final class MixedEngine {
    private MixedEngine() {
    }

    /** True when the folder holds a NeoForge or Forge mod, so NeoForge has to run underneath. */
    public static boolean needsNeoForge(Path modsDirectory) {
        if (modsDirectory == null || !Files.isDirectory(modsDirectory)) return false;
        try (DirectoryStream<Path> jars = Files.newDirectoryStream(modsDirectory, "*.jar")) {
            for (Path jar : jars) {
                if (ActiveMods.isSkipped(jar)) continue;
                try (JarFile file = new JarFile(jar.toFile())) {
                    if (file.getJarEntry("hitboy.json") != null) continue;
                    if (file.getJarEntry("META-INF/neoforge.mods.toml") != null || file.getJarEntry("META-INF/mods.toml") != null) return true;
                } catch (IOException | RuntimeException unreadable) {
                    // not a readable mod JAR
                }
            }
        } catch (IOException exception) {
            return false;
        }
        return false;
    }

    /**
     * In a NeoForge-based mixed installation without NeoForge or Forge mods: switches this game to HitBoy's
     * own engine. NeoForge's startup class then hands over to {@link NativeLoader#runInsteadOfNeoForge}.
     */
    static boolean chooseHitBoyEngine() {
        if (!"neoforge".equalsIgnoreCase(System.getProperty("hitboy.base", ""))) return false;
        if (!com.hitboy.loader.fabric.FabricRuntime.enabled()) return false;
        String mods = System.getProperty("hitboy.mods-dir", "");
        if (mods.isEmpty() || needsNeoForge(Path.of(mods))) return false;
        System.setProperty("hitboy.base", "");
        System.setProperty("hitboy.engine", "hitboy");
        System.out.println("HitBoy's Mixed Compatible Mod Loader: no NeoForge or Forge mods, so HitBoy runs the game itself"
            + " (full Fabric support, including Fabric API).");
        return true;
    }

    static boolean hitBoyEngine() {
        return "hitboy".equals(System.getProperty("hitboy.engine"));
    }
}
