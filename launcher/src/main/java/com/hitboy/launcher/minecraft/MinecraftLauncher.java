package com.hitboy.launcher.minecraft;

import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MinecraftLauncher {
    private File baseDir() {
        String d = com.hitboy.launcher.NavigationContext.getGameDirectory();
        if (d == null) d = com.hitboy.launcher.NavigationContext.defaultGameDirectory();
        return new File(d);
    }

    public void launch(String version, String username, int ramMb, Consumer<String> log) throws Exception {
        if (!SupportedMinecraftVersions.isSupported(version)) {
            throw new IllegalArgumentException(
                "Unsupported Minecraft version " + version + ". HitBoy native hooks are verified for: "
                    + SupportedMinecraftVersions.displayList()
            );
        }
        File verDir = new File(baseDir(), "versions" + File.separator + version);
        File jsonFile = new File(verDir, version + ".json");
        File jarFile = new File(verDir, version + ".jar");
        if (!jsonFile.exists()) throw new FileNotFoundException("Version not downloaded: " + version + " run Download first");
        if (!jarFile.exists()) throw new FileNotFoundException("Minecraft client JAR is missing: " + jarFile);
        jarFile = HitBoyClientJar.prepare(jarFile);
        JsonObject verJson = JsonParser.parseString(new String(Files.readAllBytes(jsonFile.toPath()))).getAsJsonObject();

        // Build classpath
        String mainClass = verJson.get("mainClass").getAsString();
        String cp = buildClasspath(verJson, jarFile);
        String natives = new File(verDir, "natives").getAbsolutePath();
        File assetsDir = new File(baseDir(), "assets");
        String assetIndex = verJson.getAsJsonObject("assetIndex").get("id").getAsString();

        String javaExe = findJava(version, log);
        verifyJavaRuntime(javaExe, version, log);
        String uuid = "Notch".equalsIgnoreCase(username)
            ? "069a79f4-44e9-4726-a5be-fca90e38aaf5"
            : UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes()).toString();
        String accessToken = "0";
        List<String> cmd = new ArrayList<>();
        cmd.add(javaExe);
        cmd.add("-Xmx" + ramMb + "M");
        cmd.add("-Xms512M");
        cmd.add("-Djava.library.path=" + natives);
        File loaderJar = findLoaderJar();
        String mappings = new File(baseDir(), "mappings.json").getAbsolutePath();
        String configuredModsDirectory = com.hitboy.launcher.NavigationContext.getNativeModsDirectory();
        File modsDir = configuredModsDirectory == null
            ? new File(baseDir(), "native_mods")
            : new File(configuredModsDirectory);
        modsDir.mkdirs();
        if (loaderJar == null || !loaderJar.isFile()) {
            throw new FileNotFoundException("HitBoy loader JAR is missing.");
        }
        cmd.add("-javaagent:" + loaderJar.getAbsolutePath() + "=" + mappings);
        cmd.add("-Dhitboy.game-version=" + version);
        cmd.add("-Dhitboy.game-directory=" + baseDir().getAbsolutePath());
        cmd.add("-Dhitboy.mods-dir=" + modsDir.getAbsolutePath());
        cmd.add("-Dhitboy.mappings=" + mappings);
        cmd.add("-Dhitboy.minecraft.main=" + mainClass);
        String mixinsEnabled = System.getenv("HITBOY_MIXINS");
        if ("true".equalsIgnoreCase(mixinsEnabled) || "1".equals(mixinsEnabled)) {
            cmd.add("-Dhitboy.mixin.enabled=true");
        }
        String minecraftArguments = String.join("\u001F", Arrays.asList(
            "--username", username,
            "--version", version,
            "--gameDir", baseDir().getAbsolutePath(),
            "--assetsDir", assetsDir.getAbsolutePath(),
            "--assetIndex", assetIndex,
            "--uuid", uuid,
            "--accessToken", accessToken,
            "--userType", "mojang",
            "--versionType", "release"
        ));
        cmd.add("-Dhitboy.minecraft.args=" + minecraftArguments);
        String modsClasspath = modsClasspath();
        if (!modsClasspath.isEmpty()) {
            cp = cp + File.pathSeparator + modsClasspath;
        }
        cmd.add("-cp"); cmd.add(cp);
        cmd.add("com.hitboy.loader.NativeLoader");
        // Game args
        cmd.add("--username"); cmd.add(username);
        cmd.add("--version"); cmd.add(version);
        cmd.add("--gameDir"); cmd.add(baseDir().getAbsolutePath());
        cmd.add("--assetsDir"); cmd.add(assetsDir.getAbsolutePath());
        cmd.add("--assetIndex"); cmd.add(assetIndex);
        cmd.add("--uuid"); cmd.add(uuid);
        cmd.add("--accessToken"); cmd.add(accessToken);
        cmd.add("--userType"); cmd.add("mojang");
        cmd.add("--versionType"); cmd.add("release");

        log.accept("Launching: " + String.join(" ", cmd).substring(0, Math.min(400, String.join(" ", cmd).length())) + "...");
        log.accept("Classpath entries: " + cp.split(File.pathSeparator).length);

        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.directory(baseDir());
        pb.redirectErrorStream(true);
        // Inherit env
        Map<String,String> env = pb.environment();
        // Start
        Process p = pb.start();
        new Thread(() -> {
            try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line; while ((line = r.readLine()) != null) log.accept(line);
            } catch (Exception ignored) {}
        }).start();
        int code = p.waitFor();
        log.accept("Minecraft exited with code " + code);
        if (code != 0) throw new IOException("Minecraft exited " + code);
    }

    private String buildClasspath(JsonObject verJson, File jarFile) throws Exception {
        List<String> entries = new ArrayList<>();
        entries.add(jarFile.getAbsolutePath());
        JsonArray libs = verJson.getAsJsonArray("libraries");
        for (JsonElement el : libs) {
            JsonObject lib = el.getAsJsonObject();
            if (lib.has("rules") && !allow(lib.getAsJsonArray("rules"))) continue;
            if (!lib.has("downloads")) continue;
            JsonObject art = lib.getAsJsonObject("downloads").getAsJsonObject("artifact");
            if (art == null) continue;
            String path = art.get("path").getAsString();
            File f = new File(baseDir(), "libraries" + File.separator + path.replace("/", File.separator));
            if (f.exists()) entries.add(f.getAbsolutePath());
        }
        File loader = findLoaderJar();
        if (loader != null && loader.exists()) {
            entries.add(loader.getAbsolutePath());
        }
        return String.join(File.pathSeparator, entries);
    }

    private String modsClasspath() throws IOException {
        String dir = com.hitboy.launcher.NavigationContext.getNativeModsDirectory();
        if (dir == null) dir = new File(baseDir(), "native_mods").getAbsolutePath();
        File mods = new File(dir);
        if (!mods.exists()) return "";
        File[] jars = mods.listFiles((d,n) -> n.endsWith(".jar") && isEnabled(new File(d, n)));
        if (jars == null) return "";
        List<String> ps = new ArrayList<>();
        for (File jar : jars) {
            requireNativeHitBoyMod(jar);
            ps.add(jar.getAbsolutePath());
            ps.addAll(extractNestedLibraries(jar));
        }
        return String.join(File.pathSeparator, ps);
    }

    private void requireNativeHitBoyMod(File mod) throws IOException {
        try (JarFile jar = new JarFile(mod)) {
            if (jar.getJarEntry("hitboy.json") != null) return;
            String source = jar.getJarEntry("fabric.mod.json") != null ? "Fabric"
                : jar.getJarEntry("META-INF/neoforge.mods.toml") != null ? "NeoForge"
                : jar.getJarEntry("META-INF/mods.toml") != null ? "Forge" : "unknown";
            throw new IOException(
                mod.getName() + " is a " + source
                    + " mod and cannot launch directly. Port it to the HitBoy API first."
            );
        }
    }

    private List<String> extractNestedLibraries(File outerJar) {
        List<String> paths = new ArrayList<>();
        File destination = new File(baseDir(), "cache" + File.separator + "nested" + File.separator
            + outerJar.getName().substring(0, outerJar.getName().length() - 4));
        try (JarFile jar = new JarFile(outerJar)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.isDirectory() || !entry.getName().startsWith("META-INF/jars/")
                    || !entry.getName().endsWith(".jar")) continue;
                File output = new File(destination, new File(entry.getName()).getName());
                if (!output.isFile() || output.length() != entry.getSize()) {
                    Files.createDirectories(destination.toPath());
                    try (InputStream input = jar.getInputStream(entry)) {
                        Files.copy(input, output.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                }
                paths.add(output.getAbsolutePath());
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Could not extract nested libraries from " + outerJar, exception);
        }
        return paths;
    }

    private boolean isEnabled(File jar) {
        String jarName = jar.getName();
        String id = jarName.substring(0, jarName.length() - ".jar".length());
        File disabled = new File(jar.getParentFile(), id + ".disabled");
        return !disabled.exists();
    }

    private boolean allow(JsonArray rules) {
        String os = System.getProperty("os.name").toLowerCase().contains("win") ? "windows" : System.getProperty("os.name").toLowerCase().contains("mac") ? "osx" : "linux";
        boolean allowed = false;
        for (JsonElement r : rules) {
            JsonObject o = r.getAsJsonObject();
            String action = o.get("action").getAsString();
            if (o.has("os")) {
                String name = o.getAsJsonObject("os").get("name").getAsString();
                if (!name.equals(os)) continue;
            }
            allowed = "allow".equals(action);
        }
        return allowed;
    }

    private File findLoaderJar() {
        return com.hitboy.launcher.LauncherPaths.findLoaderJar(MinecraftLauncher.class);
    }
    private String findJava(String minecraftVersion, Consumer<String> log) {
        String configuredJava = System.getenv("HITBOY_JAVA");
        if (configuredJava != null && !configuredJava.isBlank()) {
            return configuredJava;
        }

        int requiredFeature = requiresJava21(minecraftVersion) ? 21 : 17;
        int pathFeature = tryReadJavaFeature("java");
        if (pathFeature >= requiredFeature) {
            return "java";
        }

        String discoveredJava = findInstalledJava(requiredFeature);
        if (discoveredJava != null) {
            log.accept("PATH Java " + (pathFeature < 0 ? "could not be detected" : pathFeature)
                + "; automatically selected Java " + tryReadJavaFeature(discoveredJava)
                + " from " + discoveredJava + ".");
            return discoveredJava;
        }
        return "java";
    }

    private String findInstalledJava(int requiredFeature) {
        LinkedHashSet<File> candidates = new LinkedHashSet<>();
        String executableName = System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("win")
            ? "java.exe" : "java";
        candidates.add(new File(System.getProperty("java.home"), "bin" + File.separator + executableName));

        String javaHome = System.getenv("JAVA_HOME");
        if (javaHome != null && !javaHome.isBlank()) {
            candidates.add(new File(javaHome, "bin" + File.separator + executableName));
        }
        if (executableName.equals("java.exe")) {
            String programFiles = System.getenv("ProgramFiles");
            if (programFiles == null || programFiles.isBlank()) {
                programFiles = "C:\\Program Files";
            }
            for (String vendor : new String[]{"Microsoft", "Java", "Eclipse Adoptium", "Amazon Corretto"}) {
                File vendorDirectory = new File(programFiles, vendor);
                File[] installations = vendorDirectory.listFiles(File::isDirectory);
                if (installations == null) {
                    continue;
                }
                for (File installation : installations) {
                    candidates.add(new File(installation, "bin" + File.separator + executableName));
                }
            }
        }

        File selected = null;
        int selectedFeature = -1;
        for (File candidate : candidates) {
            if (!candidate.isFile()) {
                continue;
            }
            int feature = tryReadJavaFeature(candidate.getAbsolutePath());
            if (feature >= requiredFeature && feature > selectedFeature) {
                selected = candidate;
                selectedFeature = feature;
            }
        }
        return selected == null ? null : selected.getAbsolutePath();
    }

    private int tryReadJavaFeature(String javaExe) {
        try {
            return readJavaFeature(javaExe);
        } catch (IOException e) {
            return -1;
        }
    }

    private void verifyJavaRuntime(String javaExe, String minecraftVersion, Consumer<String> log) throws IOException {
        int javaFeature = readJavaFeature(javaExe);
        int requiredFeature = requiresJava21(minecraftVersion) ? 21 : 17;
        if (javaFeature < requiredFeature) {
            throw new IOException("Minecraft " + minecraftVersion + " requires Java " + requiredFeature
                + "+; selected Java runtime is " + javaFeature + ". Set HITBOY_JAVA to a compatible executable.");
        }
        log.accept("Using Java " + javaFeature + " from " + javaExe + ".");
    }

    private int readJavaFeature(String javaExe) throws IOException {
        Process process = new ProcessBuilder(javaExe, "-version").redirectErrorStream(true).start();
        String versionOutput;
        try (InputStream input = process.getInputStream()) {
            versionOutput = new String(input.readAllBytes());
        }
        try {
            if (process.waitFor() != 0) {
                throw new IOException("Java command failed: " + javaExe);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while checking Java runtime", e);
        }
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("version \\\"(?:1\\.)?(\\d+)").matcher(versionOutput);
        if (!matcher.find()) {
            throw new IOException("Could not determine the Java version from: " + javaExe);
        }
        return Integer.parseInt(matcher.group(1));
    }

    private boolean requiresJava21(String version) {
        String[] parts = version.split("\\.");
        if (parts.length < 2) {
            return false;
        }
        try {
            int major = Integer.parseInt(parts[0]);
            int minor = Integer.parseInt(parts[1]);
            int patch = parts.length > 2 ? Integer.parseInt(parts[2].replaceAll("\\D.*$", "")) : 0;
            return major > 1 || (major == 1 && (minor > 20 || (minor == 20 && patch >= 5)));
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
