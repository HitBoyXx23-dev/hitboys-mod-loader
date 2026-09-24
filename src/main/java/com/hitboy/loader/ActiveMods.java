package com.hitboy.loader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Decides once which JARs in a mods folder every loader stage should ignore: disabled mods
 * ({@code <name>.disabled}) and duplicate copies of the same HitBoy mod. For duplicates the largest
 * JAR is kept, so a small leftover stub never wins over the real mod.
 */
public final class ActiveMods {
    private static final Map<Path, Set<Path>> SKIPPED = new ConcurrentHashMap<>();

    private ActiveMods() {
    }

    public static boolean isSkipped(Path jar) {
        Path directory = jar.toAbsolutePath().getParent();
        return directory != null && skipped(directory).contains(jar.toAbsolutePath().normalize());
    }

    private static Set<Path> skipped(Path directory) {
        return SKIPPED.computeIfAbsent(directory, ActiveMods::scan);
    }

    private static Set<Path> scan(Path directory) {
        Set<Path> skipped = new HashSet<>();
        List<Path> jars = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "*.jar")) {
            for (Path jar : stream) jars.add(jar.toAbsolutePath().normalize());
        } catch (IOException exception) {
            return skipped;
        }
        jars.sort(Comparator.comparingLong(ActiveMods::size).reversed());
        Map<String, Path> seen = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (Path jar : jars) {
            String stem = jar.getFileName().toString().replaceFirst("(?i)[.]jar$", "");
            if (Files.exists(jar.resolveSibling(stem + ".disabled"))) {
                skipped.add(jar);
                continue;
            }
            String[] identity = identity(jar);
            if (identity == null) continue;
            Path existing = null;
            for (String key : identity) if (existing == null) existing = seen.get(key);
            if (existing != null) {
                System.err.println("Skipping " + jar.getFileName() + ": it is the same mod as " + existing.getFileName()
                    + ". Delete one of them.");
                skipped.add(jar);
                continue;
            }
            for (String key : identity) seen.put(key, jar);
        }
        return skipped;
    }

    /** The mod's name and id from hitboy.json, or null for non-HitBoy JARs. */
    private static String[] identity(Path jar) {
        try (JarFile file = new JarFile(jar.toFile())) {
            JarEntry entry = file.getJarEntry("hitboy.json");
            if (entry == null) return null;
            try (InputStream input = file.getInputStream(entry)) {
                JsonObject json = JsonParser.parseString(new String(input.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
                String name = json.has("name") ? json.get("name").getAsString() : jar.getFileName().toString();
                String id = json.has("id") ? json.get("id").getAsString() : name.toLowerCase(Locale.ROOT);
                return new String[] {name, id};
            }
        } catch (IOException | RuntimeException invalid) {
            return null;
        }
    }

    private static long size(Path jar) {
        try {
            return Files.size(jar);
        } catch (IOException exception) {
            return 0;
        }
    }
}
