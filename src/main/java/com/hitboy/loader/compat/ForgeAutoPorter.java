package com.hitboy.loader.compat;

import com.hitboy.loader.ActiveMods;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;

/**
 * NeoForge base: Forge mods in the mods folder are ported to NeoForge with port.exe's converter
 * ({@link ForgeToNeoForgePorter}) when the game starts, cached, and handed to NeoForge through its
 * "fml.modFolders" setting. Mods the converter cannot port are skipped with the reasons in the log.
 */
public final class ForgeAutoPorter {
    private ForgeAutoPorter() {
    }

    public static void prepare(Path modsDirectory, String minecraftVersion) {
        List<Path> forgeJars = new ArrayList<>();
        try (DirectoryStream<Path> jars = Files.newDirectoryStream(modsDirectory, "*.jar")) {
            for (Path jar : jars) if (!ActiveMods.isSkipped(jar) && isForgeOnly(jar)) forgeJars.add(jar);
        } catch (IOException exception) {
            System.err.println("[HitBoy Forge] Could not scan " + modsDirectory + ": " + exception);
            return;
        }
        if (forgeJars.isEmpty()) return;
        Path cache = Path.of(System.getProperty("hitboy.home", "."), "cache", "forge-neoforge", minecraftVersion);
        List<String> folders = new ArrayList<>();
        Set<Path> current = new HashSet<>();
        ForgeToNeoForgePorter porter = new ForgeToNeoForgePorter();
        for (Path jar : forgeJars) {
            try {
                Path folder = cache.resolve(hash(jar));
                Path ported = firstJar(folder);
                if (ported == null) {
                    ForgeToNeoForgePorter.Result result = porter.port(jar, folder, minecraftVersion);
                    if (!result.ported()) {
                        System.out.println("[HitBoy Forge] Skipping " + jar.getFileName() + ": it cannot be ported to NeoForge automatically:");
                        result.blockers().stream().limit(5).forEach(blocker -> System.out.println("[HitBoy Forge]   - " + blocker));
                        if (result.blockers().size() > 5) System.out.println("[HitBoy Forge]   ... and " + (result.blockers().size() - 5) + " more (run port.exe for the full list)");
                        continue;
                    }
                    ported = result.output();
                }
                current.add(folder.toAbsolutePath().normalize());
                folders.add("hitboy_forge_" + folders.size() + "%%" + ported.toAbsolutePath());
                System.out.println("[HitBoy Forge] Running Forge mod " + jar.getFileName() + " (ported to NeoForge)");
            } catch (Exception exception) {
                System.err.println("[HitBoy Forge] Could not port " + jar.getFileName() + ": " + exception);
            }
        }
        cleanCache(cache, current);
        if (folders.isEmpty()) return;
        String existing = System.getProperty("fml.modFolders", "");
        String added = String.join(java.io.File.pathSeparator, folders);
        System.setProperty("fml.modFolders", existing.isEmpty() ? added : existing + java.io.File.pathSeparator + added);
    }

    /**
     * NeoForge only scans "<game>/mods". When HitBoy's mods folder is elsewhere (the standalone launcher's
     * native_mods), the NeoForge mods in it are handed to NeoForge the same way.
     */
    public static void addNeoForgeMods(Path modsDirectory) {
        Path gameMods = Path.of(System.getProperty("hitboy.game-directory", "."), "mods").toAbsolutePath().normalize();
        if (modsDirectory.toAbsolutePath().normalize().equals(gameMods)) return;
        List<String> folders = new ArrayList<>();
        try (DirectoryStream<Path> jars = Files.newDirectoryStream(modsDirectory, "*.jar")) {
            for (Path jar : jars) {
                if (ActiveMods.isSkipped(jar)) continue;
                try (JarFile file = new JarFile(jar.toFile())) {
                    if (file.getJarEntry("META-INF/neoforge.mods.toml") != null) folders.add("hitboy_neoforge_" + folders.size() + "%%" + jar.toAbsolutePath());
                }
            }
        } catch (IOException exception) {
            System.err.println("[HitBoy] Could not scan " + modsDirectory + " for NeoForge mods: " + exception);
        }
        if (folders.isEmpty()) return;
        String existing = System.getProperty("fml.modFolders", "");
        String added = String.join(java.io.File.pathSeparator, folders);
        System.setProperty("fml.modFolders", existing.isEmpty() ? added : existing + java.io.File.pathSeparator + added);
        System.out.println("[HitBoy] Running " + folders.size() + " NeoForge mod(s) from " + modsDirectory);
    }

    /** A Forge mod: mods.toml without NeoForge's neoforge.mods.toml. */
    static boolean isForgeOnly(Path jar) {
        try (JarFile file = new JarFile(jar.toFile())) {
            return file.getJarEntry("META-INF/mods.toml") != null && file.getJarEntry("META-INF/neoforge.mods.toml") == null;
        } catch (IOException | RuntimeException unreadable) {
            return false;
        }
    }

    private static Path firstJar(Path folder) throws IOException {
        if (!Files.isDirectory(folder)) return null;
        try (DirectoryStream<Path> jars = Files.newDirectoryStream(folder, "*.jar")) {
            for (Path jar : jars) return jar;
        }
        return null;
    }

    private static void cleanCache(Path cache, Set<Path> current) {
        if (!Files.isDirectory(cache)) return;
        try (DirectoryStream<Path> folders = Files.newDirectoryStream(cache)) {
            for (Path folder : folders) {
                if (current.contains(folder.toAbsolutePath().normalize()) || !Files.isDirectory(folder)) continue;
                try (DirectoryStream<Path> files = Files.newDirectoryStream(folder)) {
                    for (Path file : files) Files.deleteIfExists(file);
                }
                Files.deleteIfExists(folder);
            }
        } catch (IOException exception) {
            System.err.println("[HitBoy Forge] Could not clean " + cache + ": " + exception);
        }
    }

    private static String hash(Path jar) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.update(Files.readAllBytes(jar));
        digest.update("hitboy-forge-port-1".getBytes(java.nio.charset.StandardCharsets.UTF_8));
        StringBuilder text = new StringBuilder();
        for (byte value : digest.digest()) text.append(String.format("%02x", value));
        return text.substring(0, 12);
    }
}
