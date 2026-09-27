package com.hitboy.loader;

import java.nio.file.Path;
import java.util.jar.JarFile;

/**
 * Lets HitBoy run on top of NeoForge. NeoForge's mod loader scans the same mods folder and shows a
 * "not a valid mod file" warning screen for JARs it does not understand, so HitBoy's agent makes it
 * skip the JARs that HitBoy itself runs: HitBoy mods and (in mixed mode) Fabric mods.
 */
public final class NeoForgeCompat {
    private NeoForgeCompat() {
    }

    /** Called from NeoForge's mods-folder scan; true means "HitBoy runs this JAR, NeoForge should skip it". */
    public static boolean claimedByHitBoy(Path jar) {
        String name = jar.getFileName().toString().toLowerCase(java.util.Locale.ROOT);
        if (!name.endsWith(".jar")) return false;
        try (JarFile file = new JarFile(jar.toFile())) {
            if (file.getJarEntry("META-INF/neoforge.mods.toml") != null || file.getJarEntry("META-INF/mods.toml") != null) return false;
            if (file.getJarEntry("hitboy.json") != null) return true;
            return file.getJarEntry("fabric.mod.json") != null && com.hitboy.loader.fabric.FabricRuntime.enabled();
        } catch (java.io.IOException | RuntimeException unreadable) {
            return false;
        }
    }
}
