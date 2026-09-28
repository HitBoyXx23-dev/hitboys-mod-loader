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
 * Installs HitBoy's Mixed Compatible Mod Loader for Minecraft 26.x: runs NeoForge's own installer into
 * .minecraft, then adds an installation that inherits the NeoForge version and attaches HitBoy as a Java
 * agent. NeoForge runs underneath so NeoForge mods can run; HitBoy runs HitBoy, Fabric, and (ported)
 * Forge mods next to them, and the game shows HitBoy's Mixed Compatible Mod Loader (see HitBoyBranding).
 *
 * <p>HitBoy is attached only as {@code -javaagent} (not as a library) because the launcher puts a
 * profile's own libraries first, and NeoForge's loader must come before HitBoy's bundled Mixin/ASM.
 */
public final class NeoForgeInstaller {
    private static final String MAVEN = "https://maven.neoforged.net/releases/net/neoforged/neoforge/";

    private NeoForgeInstaller() {
    }

    /** Versions where the mixed loader can run NeoForge underneath (NeoForge and Forge mods): 26.x and 1.21.11. */
    public static boolean supportsNeoForgeEngine(String minecraftVersion) {
        return !minecraftVersion.startsWith("1.") || minecraftVersion.equals("1.21.11");
    }

    public static String mixedVersionId(String minecraftVersion) {
        return OfficialPatchInstaller.versionId(minecraftVersion, true);
    }

    public static String mixedProfileName(String minecraftVersion) {
        return OfficialPatchInstaller.profileName(minecraftVersion, true);
    }

    public static void installMixed(File officialGameDirectory, File loaderJar, String minecraftVersion) throws IOException {
        OfficialPatchInstaller.prepareShared(officialGameDirectory, loaderJar, minecraftVersion);

        String neoForgeVersion = latestNeoForge(minecraftVersion);
        System.out.println("Installing NeoForge " + neoForgeVersion + " (runs underneath HitBoy's Mixed Compatible Mod Loader)...");
        java.util.Set<String> before = OfficialPatchInstaller.launcherProfileKeys(officialGameDirectory);
        runNeoForgeInstaller(officialGameDirectory, neoForgeVersion);
        // NeoForge's installer adds its own "NeoForge" installation; NeoForge only runs underneath HitBoy here.
        OfficialPatchInstaller.removeLauncherProfilesExcept(officialGameDirectory, before);

        String versionId = mixedVersionId(minecraftVersion);
        String profileName = mixedProfileName(minecraftVersion);
        File versionDirectory = new File(officialGameDirectory, "versions" + File.separator + versionId);
        Files.createDirectories(versionDirectory.toPath());
        Files.writeString(new File(versionDirectory, versionId + ".json").toPath(),
            OfficialPatchInstaller.GSON.toJson(profileJson(minecraftVersion, neoForgeVersion)));
        File placeholder = new File(versionDirectory, versionId + ".jar");
        if (!placeholder.exists()) Files.write(placeholder.toPath(), new byte[0]);
        OfficialPatchInstaller.addLauncherProfile(officialGameDirectory, versionId, profileName);
        System.out.println("Installed \"" + profileName + "\" (runs NeoForge " + neoForgeVersion + " underneath).");
    }

    /**
     * NeoForge versions are numbered from the Minecraft version: 26.3 -> 26.3.x, 1.21.11 -> 21.11.x.
     * Picks the newest stable build, or the newest beta when no stable build exists yet.
     */
    static String latestNeoForge(String minecraftVersion) throws IOException {
        String prefix = minecraftVersion.startsWith("1.") ? minecraftVersion.substring(2) + "." : minecraftVersion + ".";
        String metadata;
        metadata = new String(download(MAVEN + "maven-metadata.xml"), StandardCharsets.UTF_8);
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

    /** Downloads with a real user agent, timeouts, and a few retries (NeoForge's server sometimes refuses a request). */
    private static byte[] download(String url) throws IOException {
        IOException last = null;
        for (int attempt = 1; attempt <= 4; attempt++) {
            try {
                java.net.HttpURLConnection connection = (java.net.HttpURLConnection) new URL(url).openConnection();
                connection.setRequestProperty("User-Agent", "HitBoysModLoader/1.0 (+https://github.com/HitBoyXx23-dev/hitboys-mod-loader)");
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(60000);
                int status = connection.getResponseCode();
                if (status != 200) throw new IOException("HTTP " + status);
                try (InputStream input = connection.getInputStream()) {
                    return input.readAllBytes();
                }
            } catch (IOException failure) {
                last = failure;
                try {
                    Thread.sleep(1500L * attempt);
                } catch (InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        throw new IOException("Could not download " + url + " (" + (last == null ? "interrupted" : last.getMessage()) + "). Check the internet connection and try again.", last);
    }

    static void runNeoForgeInstaller(File officialGameDirectory, String neoForgeVersion) throws IOException {
        Path installer = Files.createTempFile("neoforge-installer-", ".jar");
        try {
            String url = MAVEN + neoForgeVersion + "/neoforge-" + neoForgeVersion + "-installer.jar";
            Files.write(installer, download(url));
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
        profile.addProperty("id", mixedVersionId(minecraftVersion));
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
        jvm.add("-Dhitboy.mixed-compatibility=true");
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
