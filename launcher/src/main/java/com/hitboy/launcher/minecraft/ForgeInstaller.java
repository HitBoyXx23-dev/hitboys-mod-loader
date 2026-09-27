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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Installs HitBoy on top of Forge, the same way as {@link NeoForgeInstaller}: runs Forge's own installer
 * into .minecraft, then adds an installation that inherits the Forge version and attaches HitBoy as a
 * Java agent.
 */
public final class ForgeInstaller {
    private static final String MAVEN = "https://maven.minecraftforge.net/net/minecraftforge/forge/";
    private static final String PROMOTIONS = "https://files.minecraftforge.net/net/minecraftforge/forge/promotions_slim.json";

    private ForgeInstaller() {
    }

    public static String versionId(String minecraftVersion) {
        return minecraftVersion + "-HitBoy-Forge";
    }

    public static String profileName(String minecraftVersion) {
        return "Minecraft " + minecraftVersion + "/HitBoy's Mod Loader + Forge";
    }

    public static void install(File officialGameDirectory, File loaderJar, String minecraftVersion) throws IOException {
        // Shared steps: HitBoy library, data folder, mods folder, and checks.
        OfficialPatchInstaller.install(officialGameDirectory, loaderJar, minecraftVersion, false);

        String forgeVersion = latestForge(minecraftVersion);
        System.out.println("Installing Forge " + forgeVersion + " for Minecraft " + minecraftVersion + "...");
        runForgeInstaller(officialGameDirectory, minecraftVersion + "-" + forgeVersion);

        String versionId = versionId(minecraftVersion);
        File versionDirectory = new File(officialGameDirectory, "versions" + File.separator + versionId);
        Files.createDirectories(versionDirectory.toPath());
        Files.writeString(new File(versionDirectory, versionId + ".json").toPath(),
            OfficialPatchInstaller.GSON.toJson(profileJson(minecraftVersion, forgeVersion)));
        File placeholder = new File(versionDirectory, versionId + ".jar");
        if (!placeholder.exists()) Files.write(placeholder.toPath(), new byte[0]);
        OfficialPatchInstaller.addLauncherProfile(officialGameDirectory, versionId, profileName(minecraftVersion));
        System.out.println("Installed \"" + profileName(minecraftVersion) + "\" (Forge " + forgeVersion + ").");
    }

    /** Forge's recommended build for this Minecraft version, or its latest when none is recommended yet. */
    static String latestForge(String minecraftVersion) throws IOException {
        String json;
        try (InputStream input = new URL(PROMOTIONS).openStream()) {
            json = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
        for (String kind : new String[] {"-recommended", "-latest"}) {
            Matcher matcher = Pattern.compile("\"" + Pattern.quote(minecraftVersion + kind) + "\"\s*:\s*\"([^\"]+)\"").matcher(json);
            if (matcher.find()) return matcher.group(1);
        }
        throw new IOException("Forge has no release for Minecraft " + minecraftVersion + " yet.");
    }

    private static void runForgeInstaller(File officialGameDirectory, String fullVersion) throws IOException {
        Path installer = Files.createTempFile("forge-installer-", ".jar");
        try {
            String url = MAVEN + fullVersion + "/forge-" + fullVersion + "-installer.jar";
            try (InputStream input = new URL(url).openStream()) {
                Files.copy(input, installer, StandardCopyOption.REPLACE_EXISTING);
            }
            String java = Path.of(System.getProperty("java.home"), "bin", "java").toString();
            Process process = new ProcessBuilder(java, "-jar", installer.toString(), "--installClient",
                officialGameDirectory.getAbsolutePath())
                .directory(installer.getParent().toFile())
                .redirectErrorStream(true)
                .start();
            String output;
            try (InputStream input = process.getInputStream()) {
                output = new String(input.readAllBytes(), StandardCharsets.UTF_8);
            }
            int exit = process.waitFor();
            if (exit != 0) {
                throw new IOException("Forge's installer failed (exit " + exit + "):\n" + output);
            }
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while installing Forge", interrupted);
        } finally {
            Files.deleteIfExists(installer);
        }
    }

    static JsonObject profileJson(String minecraftVersion, String forgeVersion) {
        JsonObject profile = new JsonObject();
        profile.addProperty("id", versionId(minecraftVersion));
        profile.addProperty("inheritsFrom", minecraftVersion + "-forge-" + forgeVersion);
        profile.addProperty("releaseTime", Instant.now().toString());
        profile.addProperty("time", Instant.now().toString());
        profile.addProperty("type", "release");
        // No mainClass: Forge's own startup class is inherited.
        profile.add("libraries", new JsonArray());

        String home = "${game_directory}/" + OfficialPatchInstaller.HITBOY_HOME_DIRECTORY;
        JsonArray jvm = new JsonArray();
        jvm.add("-javaagent:${library_directory}/" + OfficialPatchInstaller.PATCH_LIBRARY_PATH + "=" + home + "/mappings.json");
        jvm.add("-Dhitboy.base=forge");
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
