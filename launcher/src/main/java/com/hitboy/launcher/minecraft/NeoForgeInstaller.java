package com.hitboy.launcher.minecraft;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Installs HitBoy on top of NeoForge: runs NeoForge's own installer into .minecraft, then adds an
 * installation that inherits the NeoForge version and adds HitBoy as a Java agent. NeoForge keeps its
 * own main class and runs NeoForge mods; HitBoy's agent loads HitBoy mods before NeoForge starts.
 *
 * <p>HitBoy is attached only as {@code -javaagent} (not as a library) because the launcher puts a
 * profile's own libraries first, and NeoForge's loader must come before HitBoy's bundled Mixin/ASM.
 */
public final class NeoForgeInstaller {
    private static final String MAVEN = "https://maven.neoforged.net/releases/net/neoforged/neoforge/";

    private NeoForgeInstaller() {
    }

    public static String versionId(String minecraftVersion) {
        return minecraftVersion + "-HitBoy-NeoForge";
    }

    public static String profileName(String minecraftVersion) {
        return "Minecraft " + minecraftVersion + "/HitBoy's Mod Loader + NeoForge";
    }

    public static void install(File officialGameDirectory, File loaderJar, String minecraftVersion) throws IOException {
        // Shared steps: HitBoy library, data folder, mods folder, and checks.
        OfficialPatchInstaller.install(officialGameDirectory, loaderJar, minecraftVersion, false);

        String neoForgeVersion = latestNeoForge(minecraftVersion);
        System.out.println("Installing NeoForge " + neoForgeVersion + " for Minecraft " + minecraftVersion + "...");
        runNeoForgeInstaller(officialGameDirectory, neoForgeVersion);

        String versionId = versionId(minecraftVersion);
        File versionDirectory = new File(officialGameDirectory, "versions" + File.separator + versionId);
        Files.createDirectories(versionDirectory.toPath());
        Files.writeString(new File(versionDirectory, versionId + ".json").toPath(),
            OfficialPatchInstaller.GSON.toJson(profileJson(minecraftVersion, neoForgeVersion)));
        File placeholder = new File(versionDirectory, versionId + ".jar");
        if (!placeholder.exists()) Files.write(placeholder.toPath(), new byte[0]);
        OfficialPatchInstaller.addLauncherProfile(officialGameDirectory, versionId, profileName(minecraftVersion));
        System.out.println("Installed \"" + profileName(minecraftVersion) + "\" (NeoForge " + neoForgeVersion + ").");
    }

    /**
     * NeoForge versions are numbered from the Minecraft version: 26.3 -> 26.3.x, 1.21.11 -> 21.11.x.
     * Picks the newest stable build, or the newest beta when no stable build exists yet.
     */
    static String latestNeoForge(String minecraftVersion) throws IOException {
        String prefix = minecraftVersion.startsWith("1.") ? minecraftVersion.substring(2) + "." : minecraftVersion + ".";
        String metadata;
        try (InputStream input = new URL(MAVEN + "maven-metadata.xml").openStream()) {
            metadata = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
        List<String> matching = new ArrayList<>();
        Matcher matcher = Pattern.compile("<version>([^<]+)</version>").matcher(metadata);
        while (matcher.find()) {
            String version = matcher.group(1);
            if (version.startsWith(prefix) && Character.isDigit(version.charAt(prefix.length()))) matching.add(version);
        }
        if (matching.isEmpty()) throw new IOException("NeoForge has no release for Minecraft " + minecraftVersion + " yet.");
        for (int index = matching.size() - 1; index >= 0; index--) {
            if (!matching.get(index).contains("-")) return matching.get(index);
        }
        return matching.get(matching.size() - 1);
    }

    private static void runNeoForgeInstaller(File officialGameDirectory, String neoForgeVersion) throws IOException {
        Path installer = Files.createTempFile("neoforge-installer-", ".jar");
        try {
            String url = MAVEN + neoForgeVersion + "/neoforge-" + neoForgeVersion + "-installer.jar";
            try (InputStream input = new URL(url).openStream()) {
                Files.copy(input, installer, StandardCopyOption.REPLACE_EXISTING);
            }
            String java = Path.of(System.getProperty("java.home"), "bin", "java").toString();
            Process process = new ProcessBuilder(java, "-jar", installer.toString(), "--install-client",
                officialGameDirectory.getAbsolutePath())
                .directory(installer.getParent().toFile())
                .redirectErrorStream(true)
                .start();
            String output;
            try (InputStream input = process.getInputStream()) {
                output = new String(input.readAllBytes(), StandardCharsets.UTF_8);
            }
            int exit = process.waitFor();
            if (exit != 0 || !output.contains("Successfully installed")) {
                throw new IOException("NeoForge's installer failed (exit " + exit + "):\n" + output);
            }
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while installing NeoForge", interrupted);
        } finally {
            Files.deleteIfExists(installer);
        }
    }

    static JsonObject profileJson(String minecraftVersion, String neoForgeVersion) {
        JsonObject profile = new JsonObject();
        profile.addProperty("id", versionId(minecraftVersion));
        profile.addProperty("inheritsFrom", "neoforge-" + neoForgeVersion);
        profile.addProperty("releaseTime", Instant.now().toString());
        profile.addProperty("time", Instant.now().toString());
        profile.addProperty("type", "release");
        // No mainClass: NeoForge's own startup class is inherited.
        profile.add("libraries", new JsonArray());

        String home = "${game_directory}/" + OfficialPatchInstaller.HITBOY_HOME_DIRECTORY;
        JsonArray jvm = new JsonArray();
        jvm.add("-javaagent:${library_directory}/" + OfficialPatchInstaller.PATCH_LIBRARY_PATH + "=" + home + "/mappings.json");
        jvm.add("-Dhitboy.base=neoforge");
        jvm.add("-Dhitboy.game-version=" + minecraftVersion);
        jvm.add("-Dhitboy.game-directory=${game_directory}");
        jvm.add("-Dhitboy.home=" + home);
        jvm.add("-Dhitboy.mods-dir=${game_directory}/mods");
        jvm.add("-Dhitboy.mappings=" + home + "/mappings.json");
        JsonObject arguments = new JsonObject();
        arguments.add("jvm", jvm);
        profile.add("arguments", arguments);
        return profile;
    }
}
