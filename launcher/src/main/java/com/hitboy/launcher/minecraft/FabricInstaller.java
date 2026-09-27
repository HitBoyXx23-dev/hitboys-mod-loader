package com.hitboy.launcher.minecraft;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Instant;

/**
 * Installs HitBoy on top of the real Fabric Loader, like {@link NeoForgeInstaller}: writes Fabric's own
 * launcher profile (what Fabric's installer writes), then adds an installation that inherits it and
 * attaches HitBoy as a Java agent. Fabric Loader runs Fabric mods; HitBoy runs HitBoy mods.
 */
public final class FabricInstaller {
    private static final String META = "https://meta.fabricmc.net/v2/versions/loader/";

    private FabricInstaller() {
    }

    public static String versionId(String minecraftVersion) {
        return minecraftVersion + "-HitBoy-Fabric";
    }

    public static String profileName(String minecraftVersion) {
        return "Minecraft " + minecraftVersion + "/HitBoy's Mod Loader + Fabric";
    }

    public static void install(File officialGameDirectory, File loaderJar, String minecraftVersion) throws IOException {
        // Shared steps: HitBoy library, data folder, mods folder, and checks.
        OfficialPatchInstaller.install(officialGameDirectory, loaderJar, minecraftVersion, false);

        String loaderVersion = latestFabricLoader(minecraftVersion);
        System.out.println("Installing Fabric Loader " + loaderVersion + " for Minecraft " + minecraftVersion + "...");
        JsonObject fabricProfile = JsonParser.parseString(
            read(META + minecraftVersion + "/" + loaderVersion + "/profile/json")).getAsJsonObject();
        String fabricId = fabricProfile.get("id").getAsString();
        writeVersion(officialGameDirectory, fabricId, fabricProfile);

        writeVersion(officialGameDirectory, versionId(minecraftVersion), profileJson(minecraftVersion, fabricId));
        OfficialPatchInstaller.addLauncherProfile(officialGameDirectory, versionId(minecraftVersion), profileName(minecraftVersion));
        System.out.println("Installed \"" + profileName(minecraftVersion) + "\" (Fabric Loader " + loaderVersion + ").");
    }

    /** The newest stable Fabric Loader for this Minecraft version, or the newest build when none is stable. */
    static String latestFabricLoader(String minecraftVersion) throws IOException {
        JsonArray loaders;
        try {
            loaders = JsonParser.parseString(read(META + minecraftVersion)).getAsJsonArray();
        } catch (IOException | RuntimeException missing) {
            throw new IOException("Fabric Loader has no release for Minecraft " + minecraftVersion + " yet.", missing);
        }
        if (loaders.isEmpty()) throw new IOException("Fabric Loader has no release for Minecraft " + minecraftVersion + " yet.");
        for (JsonElement entry : loaders) {
            JsonObject loader = entry.getAsJsonObject().getAsJsonObject("loader");
            if (loader.get("stable").getAsBoolean()) return loader.get("version").getAsString();
        }
        return loaders.get(0).getAsJsonObject().getAsJsonObject("loader").get("version").getAsString();
    }

    private static void writeVersion(File officialGameDirectory, String id, JsonObject profile) throws IOException {
        File directory = new File(officialGameDirectory, "versions" + File.separator + id);
        Files.createDirectories(directory.toPath());
        Files.writeString(new File(directory, id + ".json").toPath(), OfficialPatchInstaller.GSON.toJson(profile));
        File placeholder = new File(directory, id + ".jar");
        if (!placeholder.exists()) Files.write(placeholder.toPath(), new byte[0]);
    }

    private static String read(String url) throws IOException {
        try (InputStream input = new URL(url).openStream()) {
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    static JsonObject profileJson(String minecraftVersion, String fabricId) {
        JsonObject profile = new JsonObject();
        profile.addProperty("id", versionId(minecraftVersion));
        profile.addProperty("inheritsFrom", fabricId);
        profile.addProperty("releaseTime", Instant.now().toString());
        profile.addProperty("time", Instant.now().toString());
        profile.addProperty("type", "release");
        // No mainClass: Fabric's Knot launcher is inherited.
        profile.add("libraries", new JsonArray());

        String home = "${game_directory}/" + OfficialPatchInstaller.HITBOY_HOME_DIRECTORY;
        JsonArray jvm = new JsonArray();
        jvm.add("-javaagent:${library_directory}/" + OfficialPatchInstaller.PATCH_LIBRARY_PATH + "=" + home + "/mappings.json");
        jvm.add("-Dhitboy.base=fabric");
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
