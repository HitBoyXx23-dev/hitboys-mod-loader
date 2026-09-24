package com.hitboy.loader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;

public class ModManager {

    private final EventBus eventBus;
    private final List<ModInfo> loadedMods = new ArrayList<>();
    private final Map<String, ModInfo> modMap = new HashMap<>();
    
    public ModManager(EventBus eventBus) {
        this.eventBus = eventBus;
    }
    
    public void scanAndLoadMods(String modsDirectory) throws Exception {
        Path modsDir = Paths.get(modsDirectory);
        if (!Files.isDirectory(modsDir)) {
            System.err.println("Mods directory not found: " + modsDirectory);
            return;
        }

        loadedMods.clear();
        modMap.clear();
        List<Path> jarFiles = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(modsDir, "*.jar")) {
            for (Path jar : stream) {
                if (ActiveMods.isSkipped(jar)) continue;
                jarFiles.add(jar);
            }
        }
        jarFiles.sort(Comparator.comparing(path -> path.getFileName().toString(), String.CASE_INSENSITIVE_ORDER));

        List<ModInfo> modInfos = new ArrayList<>();
        for (Path jar : jarFiles) {
            try {
                ModInfo info = readModMetadata(jar);
                if (info != null) {
                    modInfos.add(info);
                }
            } catch (IOException | RuntimeException invalid) {
                System.err.println("Skipping " + jar.getFileName() + ": " + invalid.getMessage());
            }
        }

        Map<String, ModInfo> byName = indexMods(modInfos);
        List<ModInfo> enabledMods = new ArrayList<>();
        for (ModInfo info : modInfos) {
            if (isEnabled(info.jarPath)) {
                enabledMods.add(info);
            } else {
                System.out.println("Skipping disabled mod: " + info.name);
            }
        }
        List<ModInfo> sortedMods = topologicalSort(enabledMods, byName);
        HitBoyClassLoader modClassLoader = new HitBoyClassLoader(sortedMods);

        for (ModInfo info : sortedMods) {
            try {
                loadMod(info, modClassLoader);
            } catch (Throwable failure) {
                // One broken mod should not stop Minecraft from starting.
                System.err.println("HitBoy could not load " + info.name + " (" + info.jarName + "): " + failure);
                failure.printStackTrace();
            }
        }

        System.out.println("Loaded " + loadedMods.size() + " mods");
    }
    
    private ModInfo readModMetadata(Path jarPath) throws IOException {
        try (JarFile jar = new JarFile(jarPath.toFile())) {
            JarEntry descriptor = jar.getJarEntry("hitboy.json");
            if (descriptor == null) {
                System.err.println(
                    "Skipping incompatible mod JAR (missing hitboy.json): " + jarPath.getFileName()
                );
                return null;
            }

            try (InputStream is = jar.getInputStream(descriptor)) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) > 0) {
                    baos.write(buffer, 0, len);
                }

                String json = baos.toString(java.nio.charset.StandardCharsets.UTF_8);
                ModInfo info = parseModJson(json, jarPath.getFileName().toString());
                info.jarPath = jarPath.toAbsolutePath();
                return info;
            }
        }
    }

    private ModInfo parseModJson(String json, String jarName) throws IOException {
        final JsonObject descriptor;
        try {
            descriptor = JsonParser.parseString(json).getAsJsonObject();
        } catch (RuntimeException e) {
            throw new IOException("Invalid hitboy.json in " + jarName + ": " + e.getMessage(), e);
        }

        ModInfo info = new ModInfo();
        info.jarName = jarName;

        info.name = requiredString(descriptor, "name", jarName);
        info.id = descriptor.has("id")
            ? requiredString(descriptor, "id", jarName)
            : normalizeId(info.name);
        info.version = requiredString(descriptor, "version", jarName);
        info.libraryOnly = descriptor.has("libraryOnly") && descriptor.get("libraryOnly").getAsBoolean();
        if (!info.libraryOnly) {
            info.mainClass = requiredString(descriptor, "mainClass", jarName);
        }
        if (descriptor.has("dependsOn")) {
            JsonElement dependencies = descriptor.get("dependsOn");
            if (!dependencies.isJsonArray()) {
                throw new IOException("Invalid hitboy.json in " + jarName + ": dependsOn must be an array.");
            }
            JsonArray dependencyArray = dependencies.getAsJsonArray();
            for (JsonElement dependency : dependencyArray) {
                if (!dependency.isJsonPrimitive() || !dependency.getAsJsonPrimitive().isString()) {
                    throw new IOException("Invalid hitboy.json in " + jarName + ": dependsOn entries must be strings.");
                }
                String dependencyName = dependency.getAsString().trim();
                if (dependencyName.isEmpty()) {
                    throw new IOException("Invalid hitboy.json in " + jarName + ": dependsOn entries cannot be empty.");
                }
                info.dependencies.add(dependencyName);
            }
        }
        return info;
    }

    private String requiredString(JsonObject descriptor, String property, String jarName) throws IOException {
        if (!descriptor.has(property) || !descriptor.get(property).isJsonPrimitive()
            || !descriptor.getAsJsonPrimitive(property).isString()) {
            throw new IOException("Invalid hitboy.json in " + jarName + ": " + property + " must be a non-empty string.");
        }
        String value = descriptor.get(property).getAsString().trim();
        if (value.isEmpty()) {
            throw new IOException("Invalid hitboy.json in " + jarName + ": " + property + " must be a non-empty string.");
        }
        return value;
    }

    private Map<String, ModInfo> indexMods(List<ModInfo> mods) {
        Map<String, ModInfo> byName = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        // Skip later duplicates instead of stopping the game: old installs can leave two copies of a mod.
        for (java.util.Iterator<ModInfo> iterator = mods.iterator(); iterator.hasNext(); ) {
            ModInfo mod = iterator.next();
            ModInfo existing = byName.get(mod.name);
            if (existing == null && !mod.id.equalsIgnoreCase(mod.name)) existing = byName.get(mod.id);
            if (existing != null) {
                System.err.println("Skipping " + mod.jarName + ": it is the same mod as " + existing.jarName
                    + " (\"" + mod.name + "\"). Delete one of them.");
                iterator.remove();
                continue;
            }
            byName.put(mod.name, mod);
            if (!mod.id.equalsIgnoreCase(mod.name)) byName.put(mod.id, mod);
        }
        return byName;
    }

    private List<ModInfo> topologicalSort(List<ModInfo> mods, Map<String, ModInfo> allMods) {
        List<ModInfo> result = new ArrayList<>();
        Map<String, VisitState> states = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        Set<String> enabledNames = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (ModInfo mod : mods) {
            enabledNames.add(mod.name);
        }
        for (ModInfo mod : mods) {
            visit(mod, result, states, allMods, enabledNames, new ArrayDeque<>());
        }
        return result;
    }

    private void visit(
        ModInfo mod,
        List<ModInfo> result,
        Map<String, VisitState> states,
        Map<String, ModInfo> allMods,
        Set<String> enabledNames,
        Deque<String> path
    ) {
        VisitState state = states.get(mod.name);
        if (state == VisitState.VISITED) {
            return;
        }
        if (state == VisitState.VISITING) {
            path.addLast(mod.name);
            throw new IllegalArgumentException("Circular HitBoy mod dependency: " + String.join(" -> ", path));
        }
        states.put(mod.name, VisitState.VISITING);
        path.addLast(mod.name);
        for (String dep : mod.dependencies) {
            ModInfo other = allMods.get(dep);
            if (other == null) {
                throw new IllegalArgumentException("Mod " + mod.name + " requires missing mod: " + dep);
            }
            if (!enabledNames.contains(other.name)) {
                throw new IllegalArgumentException("Mod " + mod.name + " requires disabled mod: " + other.name);
            }
            visit(other, result, states, allMods, enabledNames, path);
        }
        path.removeLast();
        states.put(mod.name, VisitState.VISITED);
        result.add(mod);
    }

    public List<ModInfo> getLoadedMods() { return java.util.Collections.unmodifiableList(loadedMods); }
    public int getLoadedModCount() { return loadedMods.size(); }
    
    private boolean isEnabled(Path jarPath) {
        String fileName = jarPath.getFileName().toString();
        int extension = fileName.lastIndexOf('.');
        String modId = extension > 0 ? fileName.substring(0, extension) : fileName;
        return !Files.exists(jarPath.resolveSibling(modId + ".disabled"));
    }

    private void loadMod(ModInfo info, ClassLoader modClassLoader) throws Exception {
        System.out.println("Loading mod: " + info.name + " v" + info.version);
        if (info.libraryOnly) {
            com.hitboy.loader.api.HitBoyPlatform.registerMod(
                info.id, info.name, info.version, java.util.Collections.emptyMap()
            );
            loadedMods.add(info);
            modMap.put(info.name, info);
            return;
        }
        Class<?> modClass = Class.forName(info.mainClass, true, modClassLoader);
        NativeMod annotation = modClass.getAnnotation(NativeMod.class);
        if (annotation == null) {
            throw new IllegalArgumentException(
                "Mod " + info.name + " mainClass must be annotated with @NativeMod: " + info.mainClass
            );
        }
        if (!info.name.equals(annotation.name()) || !info.version.equals(annotation.version())) {
            throw new IllegalArgumentException(
                "Mod " + info.jarName + " metadata must match @NativeMod name and version."
            );
        }
        com.hitboy.loader.api.HitBoyPlatform.registerMod(
            info.id, info.name, info.version, java.util.Collections.emptyMap()
        );
        Object instance = modClass.getDeclaredConstructor().newInstance();
        if (instance instanceof com.hitboy.loader.api.HitBoyClientInitializer) {
            ((com.hitboy.loader.api.HitBoyClientInitializer) instance).onInitializeClient();
        }
        eventBus.register(instance);
        loadedMods.add(info);
        modMap.put(info.name, info);
    }

    private String normalizeId(String name) {
        return name.toLowerCase(java.util.Locale.ROOT).replaceAll("[^a-z0-9_-]+", "-").replaceAll("^-|-$", "");
    }

    private enum VisitState {
        VISITING,
        VISITED
    }
    
    public EventBus getEventBus() {
        return eventBus;
    }
    
    public static class ModInfo {
        public String name;
        public String id;
        public String version;
        public String mainClass;
        public boolean libraryOnly;
        public List<String> dependencies = new ArrayList<>();
        public String jarName;
        public Path jarPath;
    }
}
