package com.hitboy.launcher.minecraft;

import com.google.gson.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.function.Consumer;

public class VersionManager {
    private static final String MANIFEST_URL = "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json";
    private File baseDir() {
        String d = com.hitboy.launcher.NavigationContext.getGameDirectory();
        if (d == null) d = com.hitboy.launcher.NavigationContext.defaultGameDirectory();
        return new File(d);
    }

    public void ensureVersion(String version, Consumer<String> log) throws Exception {
        File verDir = new File(baseDir(), "versions" + File.separator + version);
        File jsonFile = new File(verDir, version + ".json");
        File jarFile = new File(verDir, version + ".jar");
        verDir.mkdirs();
        JsonObject verJson;
        if (!jsonFile.exists() || !jarFile.exists()) {
            log.accept("Fetching manifest...");
            JsonObject manifest = fetchJson(MANIFEST_URL);
            JsonArray versions = manifest.getAsJsonArray("versions");
            String url = null;
            for (JsonElement e : versions) {
                JsonObject o = e.getAsJsonObject();
                if (version.equals(o.get("id").getAsString())) { url = o.get("url").getAsString(); break; }
            }
            if (url == null) throw new IOException("Version not found: " + version);
            log.accept("Downloading version json: " + url);
            verJson = fetchJson(url);
            // save json
            try (FileWriter w = new FileWriter(jsonFile)) { new GsonBuilder().setPrettyPrinting().create().toJson(verJson, w); }
            // download client jar
            String clientUrl = verJson.getAsJsonObject("downloads").getAsJsonObject("client").get("url").getAsString();
            log.accept("Downloading client jar...");
            download(clientUrl, jarFile);
        } else {
            log.accept("Version " + version + " metadata and client JAR are cached; verifying dependencies.");
            verJson = JsonParser.parseString(new String(Files.readAllBytes(jsonFile.toPath()))).getAsJsonObject();
        }
        log.accept("Verifying libraries...");
        downloadLibraries(verJson, log);
        log.accept("Verifying assets...");
        downloadAssets(verJson, log);
        log.accept("Extracting natives...");
        extractNatives(verJson, verDir, log);
    }

    private void downloadLibraries(JsonObject verJson, Consumer<String> log) throws Exception {
        JsonArray libs = verJson.getAsJsonArray("libraries");
        for (JsonElement el : libs) {
            JsonObject lib = el.getAsJsonObject();
            // check rules
            if (lib.has("rules") && !allow(lib.getAsJsonArray("rules"))) continue;
            if (!lib.has("downloads")) continue;
            JsonObject art = lib.getAsJsonObject("downloads").getAsJsonObject("artifact");
            if (art == null) continue;
            String path = art.get("path").getAsString();
            String url = art.get("url").getAsString();
            File out = new File(baseDir(), "libraries" + File.separator + path.replace("/", File.separator));
            if (!out.exists()) {
                out.getParentFile().mkdirs();
                log.accept("  lib " + path);
                download(url, out);
            }
        }
    }

    private void downloadAssets(JsonObject verJson, Consumer<String> log) throws Exception {
        String assetIndexUrl = verJson.getAsJsonObject("assetIndex").get("url").getAsString();
        String assetId = verJson.getAsJsonObject("assetIndex").get("id").getAsString();
        File idxFile = new File(baseDir(), "assets" + File.separator + "indexes" + File.separator + assetId + ".json");
        idxFile.getParentFile().mkdirs();
        if (!idxFile.exists()) download(assetIndexUrl, idxFile);
        JsonObject idx = JsonParser.parseString(new String(Files.readAllBytes(idxFile.toPath()))).getAsJsonObject().getAsJsonObject("objects");
        java.util.List<String[]> missing = new java.util.ArrayList<>();
        for (String key : idx.keySet()) {
            String hash = idx.getAsJsonObject(key).get("hash").getAsString();
            String sub = hash.substring(0, 2);
            File out = new File(baseDir(), "assets" + File.separator + "objects" + File.separator + sub + File.separator + hash);
            if (!out.exists()) missing.add(new String[] {"https://resources.download.minecraft.net/" + sub + "/" + hash, out.getPath()});
        }
        if (missing.isEmpty()) return;
        log.accept("Downloading " + missing.size() + " assets...");
        // Thousands of small files: download in parallel, otherwise a first launch takes many minutes.
        java.util.concurrent.ExecutorService pool = java.util.concurrent.Executors.newFixedThreadPool(16);
        java.util.concurrent.atomic.AtomicInteger done = new java.util.concurrent.atomic.AtomicInteger();
        java.util.List<java.util.concurrent.Future<?>> tasks = new java.util.ArrayList<>();
        for (String[] asset : missing) {
            tasks.add(pool.submit(() -> {
                download(asset[0], new File(asset[1]));
                int count = done.incrementAndGet();
                if (count % 500 == 0 || count == missing.size()) log.accept("  assets " + count + "/" + missing.size());
                return null;
            }));
        }
        try {
            for (java.util.concurrent.Future<?> task : tasks) task.get();
        } finally {
            pool.shutdownNow();
        }
    }

    private void extractNatives(JsonObject verJson, File verDir, Consumer<String> log) throws Exception {
        File nativesDir = new File(verDir, "natives");
        nativesDir.mkdirs();
        JsonArray libs = verJson.getAsJsonArray("libraries");
        for (JsonElement el : libs) {
            JsonObject lib = el.getAsJsonObject();
            if (lib.has("rules") && !allow(lib.getAsJsonArray("rules"))) continue;
            if (!lib.has("downloads")) continue;
            JsonObject downloads = lib.getAsJsonObject("downloads");
            if (!downloads.has("classifiers")) continue;
            JsonObject classifiers = downloads.getAsJsonObject("classifiers");
            String key = findNativesKey(classifiers);
            if (key == null) continue;
            JsonObject art = classifiers.getAsJsonObject(key);
            String path = art.get("path").getAsString();
            String url = art.get("url").getAsString();
            File jar = new File(baseDir(), "libraries" + File.separator + path.replace("/", File.separator));
            if (!jar.exists()) { jar.getParentFile().mkdirs(); download(url, jar); }
            // extract dll/so
            log.accept("  natives " + key);
            try (java.util.jar.JarFile jf = new java.util.jar.JarFile(jar)) {
                var entries = jf.entries();
                while (entries.hasMoreElements()) {
                    var e = entries.nextElement();
                    if (e.getName().endsWith(".dll") || e.getName().endsWith(".so") || e.getName().endsWith(".dylib")) {
                        File out = new File(nativesDir, new File(e.getName()).getName());
                        try (InputStream in = jf.getInputStream(e)) { Files.copy(in, out.toPath(), StandardCopyOption.REPLACE_EXISTING); }
                    }
                }
            }
        }
    }

    private String findNativesKey(JsonObject classifiers) {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win") && classifiers.has("natives-windows")) return "natives-windows";
        if (os.contains("win") && classifiers.has("natives-windows-64")) return "natives-windows-64";
        if (os.contains("mac") && classifiers.has("natives-macos")) return "natives-macos";
        if (os.contains("mac") && classifiers.has("natives-osx")) return "natives-osx";
        if (classifiers.has("natives-linux")) return "natives-linux";
        return null;
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

    private JsonObject fetchJson(String url) throws Exception {
        HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
        c.setRequestProperty("User-Agent", "HitBoysModLoader/1.0");
        try (InputStream in = c.getInputStream()) { return JsonParser.parseString(new String(in.readAllBytes())).getAsJsonObject(); }
    }
    private void download(String url, File out) throws Exception {
        if (out.isFile() && out.length() > 0) {
            return;
        }
        Files.createDirectories(out.toPath().getParent());
        Path temporary = out.toPath().resolveSibling(out.getName() + ".download");
        HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
        c.setRequestProperty("User-Agent", "HitBoysModLoader/1.0");
        try (InputStream in = c.getInputStream()) {
            Files.copy(in, temporary, StandardCopyOption.REPLACE_EXISTING);
        }
        try {
            Files.move(temporary, out.toPath(), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (AtomicMoveNotSupportedException exception) {
            Files.move(temporary, out.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
