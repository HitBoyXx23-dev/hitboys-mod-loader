package com.hitboy.launcher.minecraft;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Base64;

/**
 * Installs HitBoy into an official {@code .minecraft} directory the same way the Fabric installer does:
 * an inherited version JSON, an empty placeholder client JAR, the loader library, and a
 * {@code launcher_profiles.json} entry. Mojang's client JAR is never modified.
 */
public final class OfficialPatchInstaller {
    public static final String SUPPORTED_VERSION = SupportedMinecraftVersions.DEFAULT_VERSION;
    public static final String PATCHED_VERSION_ID = SUPPORTED_VERSION + "-HitBoy";
    public static final String HITBOY_HOME_DIRECTORY = ".hitboys-modloader";
    private static final String PATCH_ARTIFACT = "hitboys-mod-loader-patch-1.0.0-SNAPSHOT.jar";
    static final String PATCH_LIBRARY_PATH =
        "com/hitboy/hitboys-mod-loader-patch/1.0.0-SNAPSHOT/" + PATCH_ARTIFACT;
    private static final String[] LAUNCHER_PROFILE_FILES = {
        "launcher_profiles.json",
        "launcher_profiles_microsoft_store.json"
    };
    static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private OfficialPatchInstaller() {
    }

    /** HitBoy's Mod Loader (HitBoy mods only). */
    public static String versionId(String minecraftVersion) {
        return versionId(minecraftVersion, false);
    }

    public static String profileName(String minecraftVersion) {
        return profileName(minecraftVersion, false);
    }

    /** Mixed = HitBoy's Mixed Compatible Mod Loader, a separate installation for mods made for other loaders. */
    public static String versionId(String minecraftVersion, boolean mixed) {
        return minecraftVersion + (mixed ? "-HitBoy-Mixed" : "-HitBoy");
    }

    public static String profileName(String minecraftVersion, boolean mixed) {
        return "Minecraft " + minecraftVersion + (mixed ? "/HitBoy's Mixed Compatible Mod Loader" : "/HitBoy's Mod Loader");
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
        prepareShared(officialGameDirectory, loaderJar, minecraftVersion);
        String versionId = versionId(minecraftVersion, mixedCompatibility);
        String profileName = profileName(minecraftVersion, mixedCompatibility);
        File hitBoyHome = new File(officialGameDirectory, HITBOY_HOME_DIRECTORY);

        File versionDirectory = new File(officialGameDirectory, "versions" + File.separator + versionId);
        Files.createDirectories(versionDirectory.toPath());
        Files.writeString(
            new File(versionDirectory, versionId + ".json").toPath(),
            GSON.toJson(profileJson(minecraftVersion, mixedCompatibility))
        );
        // Like Fabric: an empty placeholder JAR keeps the launcher from treating the version as broken.
        // The real client JAR is resolved from the inherited vanilla version.
        File placeholderJar = new File(versionDirectory, versionId + ".jar");
        if (!placeholderJar.exists()) {
            Files.write(placeholderJar.toPath(), new byte[0]);
        }
        Files.writeString(
            new File(versionDirectory, "HITBOY-OFFICIAL-PATCH.txt").toPath(),
            profileName.substring(profileName.indexOf('/') + 1) + " profile for Minecraft " + minecraftVersion + ".\n"
                + "Select \"" + profileName + "\" in the official Minecraft Launcher.\n"
                + "HitBoy data is loaded from " + hitBoyHome + ".\n"
                + "Mods are loaded from " + new File(officialGameDirectory, "mods") + ".\n"
        );

        addLauncherProfile(officialGameDirectory, versionId, profileName);
    }

    /** The steps every HitBoy installation shares: checks, the HitBoy library, its data folder, and the mods folder. */
    static void prepareShared(File officialGameDirectory, File loaderJar, String minecraftVersion) throws IOException {
        if (!SupportedMinecraftVersions.isSupported(minecraftVersion)) {
            throw new IOException(
                "Unsupported Minecraft version " + minecraftVersion + ". Supported: "
                    + SupportedMinecraftVersions.displayList()
            );
        }
        if (loaderJar == null || !loaderJar.isFile()) {
            throw new IOException("HitBoy loader JAR was not found beside the launcher.");
        }
        if (!officialGameDirectory.isDirectory()) {
            throw new IOException(
                "Minecraft directory not found: " + officialGameDirectory
                    + ". Run the official Minecraft Launcher once, or choose the correct .minecraft folder."
            );
        }
        File library = new File(officialGameDirectory, "libraries" + File.separator
            + PATCH_LIBRARY_PATH.replace('/', File.separatorChar));
        Files.createDirectories(library.toPath().getParent());
        Files.copy(loaderJar.toPath(), library.toPath(), StandardCopyOption.REPLACE_EXISTING);
        File hitBoyHome = new File(officialGameDirectory, HITBOY_HOME_DIRECTORY);
        Files.createDirectories(hitBoyHome.toPath());
        Files.createDirectories(new File(officialGameDirectory, "mods").toPath());
        Files.writeString(
            new File(hitBoyHome, "README.txt").toPath(),
            "HitBoy's Mod Loader data directory.\n"
                + "Native mods belong in the normal .minecraft\\mods folder.\n"
                + "This directory is created by HitBoy's profile installer.\n"
        );
    }

    /** The installation keys currently in launcher_profiles.json (empty when there is none). */
    static java.util.Set<String> launcherProfileKeys(File officialGameDirectory) throws IOException {
        java.util.Set<String> keys = new java.util.HashSet<>();
        for (String fileName : LAUNCHER_PROFILE_FILES) {
            File file = new File(officialGameDirectory, fileName);
            if (!file.isFile()) continue;
            JsonObject root = JsonParser.parseString(Files.readString(file.toPath(), StandardCharsets.UTF_8)).getAsJsonObject();
            if (root.has("profiles") && root.get("profiles").isJsonObject()) keys.addAll(root.getAsJsonObject("profiles").keySet());
        }
        return keys;
    }

    /** Removes installations that were not there before (such as the one NeoForge's installer adds). */
    static void removeLauncherProfilesExcept(File officialGameDirectory, java.util.Set<String> keep) throws IOException {
        for (String fileName : LAUNCHER_PROFILE_FILES) {
            File file = new File(officialGameDirectory, fileName);
            if (!file.isFile()) continue;
            JsonObject root = JsonParser.parseString(Files.readString(file.toPath(), StandardCharsets.UTF_8)).getAsJsonObject();
            if (!root.has("profiles") || !root.get("profiles").isJsonObject()) continue;
            JsonObject profiles = root.getAsJsonObject("profiles");
            boolean changed = profiles.keySet().removeIf(key -> !keep.contains(key));
            if (changed) Files.writeString(file.toPath(), GSON.toJson(root), StandardCharsets.UTF_8);
        }
    }

    /** Adds or updates an installation in launcher_profiles.json, the way the Fabric installer does. */
    static void addLauncherProfile(File officialGameDirectory, String versionId, String displayName) throws IOException {
        String now = Instant.now().toString();
        boolean found = false;
        for (String fileName : LAUNCHER_PROFILE_FILES) {
            File file = new File(officialGameDirectory, fileName);
            if (!file.isFile()) {
                continue;
            }
            found = true;
            JsonObject root = JsonParser.parseString(
                Files.readString(file.toPath(), StandardCharsets.UTF_8)
            ).getAsJsonObject();
            if (!root.has("profiles") || !root.get("profiles").isJsonObject()) {
                root.add("profiles", new JsonObject());
            }
            JsonObject profiles = root.getAsJsonObject("profiles");
            JsonObject profile = profiles.has(versionId) && profiles.get(versionId).isJsonObject()
                ? profiles.getAsJsonObject(versionId)
                : new JsonObject();
            profile.addProperty("name", displayName);
            profile.addProperty("type", "custom");
            profile.addProperty("lastVersionId", versionId);
            profile.addProperty("icon", launcherIcon());
            if (!profile.has("created")) {
                profile.addProperty("created", now);
            }
            profile.addProperty("lastUsed", now);
            profiles.add(versionId, profile);
            Files.writeString(file.toPath(), GSON.toJson(root), StandardCharsets.UTF_8);
        }
        if (!found) {
            throw new IOException(
                "launcher_profiles.json was not found in " + officialGameDirectory
                    + ". Run the official Minecraft Launcher once, then install again."
            );
        }
    }

    private static String launcherIcon() {
        try (InputStream input = OfficialPatchInstaller.class.getResourceAsStream("/icon.png")) {
            if (input != null) {
                return "data:image/png;base64," + Base64.getEncoder().encodeToString(input.readAllBytes());
            }
        } catch (IOException ignored) {
        }
        return "Furnace";
    }

    static JsonObject profileJson(String minecraftVersion, boolean mixedCompatibility) {
        JsonObject profile = new JsonObject();
        profile.addProperty("id", versionId(minecraftVersion, mixedCompatibility));
        profile.addProperty("inheritsFrom", minecraftVersion);
        profile.addProperty("releaseTime", Instant.now().toString());
        profile.addProperty("time", Instant.now().toString());
        profile.addProperty("type", "release");
        profile.addProperty("mainClass", "com.hitboy.loader.NativeLoader");
        // No javaVersion override: like Fabric, the Java runtime is inherited from the vanilla
        // version (Java 21 for 1.21.x, Java 25 for 26.x).

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
        if (mixedCompatibility) {
            jvmArguments.add("-Dhitboy.mixed-compatibility=true");
        }
        JsonObject arguments = new JsonObject();
        arguments.add("jvm", jvmArguments);
        profile.add("arguments", arguments);
        return profile;
    }
}
