package com.hitboy.launcher.minecraft;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Creates a local version profile consumed by the official Minecraft Launcher.
 */
public final class OfficialPatchInstaller {
    public static final String SUPPORTED_VERSION = "1.21.11";
    public static final String PATCHED_VERSION_ID = SUPPORTED_VERSION + "-HitBoy";
    public static final String HITBOY_HOME_DIRECTORY = ".hitboys-modloader";
    private static final String PATCH_ARTIFACT = "hitboys-mod-loader-patch-1.0.0-SNAPSHOT.jar";
    private static final String PATCH_LIBRARY_PATH =
        "com/hitboy/hitboys-mod-loader-patch/1.0.0-SNAPSHOT/" + PATCH_ARTIFACT;
    private static final String[] BUNDLED_MODS = {
        "meteor-client-hitboy-edition-1.0.0.jar"
    };

    private OfficialPatchInstaller() {
    }

    public static File defaultOfficialGameDirectory() {
        String appData = System.getenv("APPDATA");
        if (appData != null && !appData.isBlank()) {
            return new File(appData, ".minecraft");
        }
        return new File(System.getProperty("user.home"), ".minecraft");
    }

    public static void install(File officialGameDirectory, File loaderJar) throws IOException {
        install(officialGameDirectory, loaderJar, SUPPORTED_VERSION, false);
    }

    public static void install(
        File officialGameDirectory,
        File loaderJar,
        String minecraftVersion,
        boolean mixedCompatibility
    ) throws IOException {
        if (loaderJar == null || !loaderJar.isFile()) {
            throw new IOException("HitBoy loader JAR was not found beside the launcher.");
        }
        if (!officialGameDirectory.isDirectory()) {
            throw new IOException(
                "Official Minecraft directory not found: " + officialGameDirectory
                    + ". Start Minecraft 1.21.11 once in the official Launcher first."
            );
        }
        File baseVersion = new File(
            officialGameDirectory,
            "versions" + File.separator + minecraftVersion + File.separator + minecraftVersion + ".json"
        );
        if (!baseVersion.isFile()) {
            throw new IOException(
                "Official Minecraft " + minecraftVersion + " is not installed in " + officialGameDirectory
                    + ". Start that version once in the official Minecraft Launcher first."
            );
        }

        File library = new File(officialGameDirectory, "libraries" + File.separator
            + PATCH_LIBRARY_PATH.replace('/', File.separatorChar));
        Files.createDirectories(library.toPath().getParent());
        Files.copy(loaderJar.toPath(), library.toPath(), StandardCopyOption.REPLACE_EXISTING);
        File hitBoyHome = new File(officialGameDirectory, HITBOY_HOME_DIRECTORY);
        Files.createDirectories(hitBoyHome.toPath());
        seedBundledMods(officialGameDirectory);
        Files.writeString(
            new File(hitBoyHome, "README.txt").toPath(),
            "HitBoy's Mod Loader data directory.\n"
                + "Native mods belong in the normal .minecraft\\mods folder.\n"
                + "This directory is created by the " + minecraftVersion + "-HitBoy profile installer.\n"
        );

        File versionDirectory = new File(
            officialGameDirectory,
            "versions" + File.separator + minecraftVersion + "-HitBoy"
        );
        Files.createDirectories(versionDirectory.toPath());
        File profile = new File(versionDirectory, minecraftVersion + "-HitBoy.json");
        try (FileWriter writer = new FileWriter(profile)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(profileJson(minecraftVersion), writer);
        }

        File readme = new File(versionDirectory, "HITBOY-OFFICIAL-PATCH.txt");
        Files.writeString(
            readme.toPath(),
            "HitBoy's Mod Loader official profile for Minecraft " + minecraftVersion + ".\n"
                + "Select \"" + minecraftVersion + "-HitBoy\" in the official Minecraft Launcher.\n"
                + "The official Launcher supplies your Microsoft account session for online play.\n"
                + "HitBoy data is loaded from " + hitBoyHome + ".\n"
                + "HitBoy mods are loaded from " + new File(officialGameDirectory, "mods") + ".\n"
        );
    }

    private static void seedBundledMods(File officialGameDirectory) throws IOException {
        File modsDirectory = new File(officialGameDirectory, "mods");
        Files.createDirectories(modsDirectory.toPath());
        for (String mod : BUNDLED_MODS) {
            try (InputStream input = OfficialPatchInstaller.class.getResourceAsStream("/bundled_mods/" + mod)) {
                if (input == null) {
                    throw new IOException("Bundled mod is missing from the launcher: " + mod);
                }
                File destination = new File(modsDirectory, mod);
                if (!destination.exists()) {
                    Files.copy(input, destination.toPath());
                }
            }
        }
    }

    private static JsonObject profileJson(String minecraftVersion) {
        JsonObject profile = new JsonObject();
        profile.addProperty("id", minecraftVersion + "-HitBoy");
        profile.addProperty("inheritsFrom", minecraftVersion);
        profile.addProperty("type", "release");
        profile.addProperty("mainClass", "com.hitboy.loader.NativeLoader");

        JsonObject javaVersion = new JsonObject();
        javaVersion.addProperty("component", "java-runtime-delta");
        javaVersion.addProperty("majorVersion", 21);
        profile.add("javaVersion", javaVersion);

        JsonArray libraries = new JsonArray();
        JsonObject loaderLibrary = new JsonObject();
        loaderLibrary.addProperty("name", "com.hitboy:hitboys-mod-loader-patch:1.0.0-SNAPSHOT");
        libraries.add(loaderLibrary);
        profile.add("libraries", libraries);

        JsonArray jvmArguments = new JsonArray();
        jvmArguments.add("-javaagent:${library_directory}/" + PATCH_LIBRARY_PATH
            + "=${game_directory}/" + HITBOY_HOME_DIRECTORY + "/mappings.json");
        jvmArguments.add("-Dhitboy.game-version=" + minecraftVersion);
        jvmArguments.add("-Dhitboy.game-directory=${game_directory}");
        jvmArguments.add("-Dhitboy.home=${game_directory}/" + HITBOY_HOME_DIRECTORY);
        jvmArguments.add("-Dhitboy.mods-dir=${game_directory}/mods");
        jvmArguments.add("-Dhitboy.mappings=${game_directory}/" + HITBOY_HOME_DIRECTORY + "/mappings.json");
        JsonObject arguments = new JsonObject();
        arguments.add("jvm", jvmArguments);
        profile.add("arguments", arguments);
        return profile;
    }
}
