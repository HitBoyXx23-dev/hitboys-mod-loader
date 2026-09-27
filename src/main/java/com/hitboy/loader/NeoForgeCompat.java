package com.hitboy.loader;

import java.nio.file.Path;
import java.util.jar.JarFile;

/**
 * Lets HitBoy run on top of NeoForge. NeoForge's mod loader scans the same mods folder and shows a
 * "not a valid mod file" warning screen for JARs it does not understand, so HitBoy's agent makes it
 * skip the JARs that HitBoy runs (HitBoy mods) or that cannot run on NeoForge (Forge and Fabric mods),
 * which HitBoy reports in the log instead.
 */
public final class NeoForgeCompat {
    private NeoForgeCompat() {
    }

    /** Called from NeoForge's mods-folder scan; true means "HitBoy runs this JAR, NeoForge should skip it". */
    public static boolean claimedByHitBoy(Path jar) {
        String name = jar.getFileName().toString().toLowerCase(java.util.Locale.ROOT);
        if (!name.endsWith(".jar")) return false;
        try (JarFile file = new JarFile(jar.toFile())) {
            if (file.getJarEntry("META-INF/neoforge.mods.toml") != null) return false;
            // Forge mods (mods.toml only) cannot run on NeoForge, and NeoForge would stop on a warning
            // screen for them; HitBoy skips them with a log message instead.
            if (file.getJarEntry("META-INF/mods.toml") != null) return true;
            if (file.getJarEntry("hitboy.json") != null) return true;
            // Fabric mods are either run by HitBoy (mixed mode) or skipped with a log message.
            return file.getJarEntry("fabric.mod.json") != null;
        } catch (java.io.IOException | RuntimeException unreadable) {
            return false;
        }
    }
}
