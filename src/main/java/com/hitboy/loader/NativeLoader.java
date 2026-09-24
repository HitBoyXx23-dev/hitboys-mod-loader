package com.hitboy.loader;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import com.hitboy.loader.compat.InstanceVerifier;

public class NativeLoader {
    private static EventBus GLOBAL_BUS;
    private static ModManager GLOBAL_MANAGER;
    public static EventBus getEventBus() { return GLOBAL_BUS; }
    public static ModManager getModManager() { return GLOBAL_MANAGER; }

    public static void main(String[] args) throws Exception {
        System.out.println("HitBoy's Mod Loader v1.0.0 - Bootstrap");
        String mappingsPath = System.getProperty("hitboy.mappings", "mappings.json");
        Map<String, String> mappings = loadMappings(mappingsPath);
        EventBus eventBus = new EventBus();
        GLOBAL_BUS = eventBus;
        ModManager modManager = new ModManager(eventBus);
        GLOBAL_MANAGER = modManager;
        String gameDirectory = System.getProperty("hitboy.game-directory", ".hitboys-modloader");
        String hitBoyHome = System.getProperty("hitboy.home", gameDirectory);
        String modsDir = System.getProperty("hitboy.mods-dir");
        if (modsDir == null || modsDir.isEmpty()) {
            modsDir = hitBoyHome + File.separator + "native_mods";
        }
        File md = new File(modsDir);
        // Fallback search for the configured profile and development location.
        if (!md.exists() || Objects.requireNonNullElse(md.list(), new String[0]).length == 0) {
            String[] fallbacks = {
                hitBoyHome + File.separator + "native_mods",
                gameDirectory + File.separator + "native_mods",
                "native_mods"
            };
            for (String f : fallbacks) {
                File alt = new File(f);
                if (alt.exists() && alt.list() != null && alt.list().length > 0) { modsDir = alt.getAbsolutePath(); md = alt; break; }
            }
        }
        if (!md.exists()) md.mkdirs();
        System.out.println("Scanning mods: " + modsDir + " exists=" + md.exists());
        new InstanceVerifier().requireCompatibleHitBoyMods(md.toPath());
        GameAgent.appendHitBoyModsToClasspath(md.toPath());
        OptionalAccessWidenerRuntime.initialize(md.toPath());
        OptionalMixinRuntime.initialize(md.toPath());
        modManager.scanAndLoadMods(modsDir);
        String gameVersion = System.getProperty("hitboy.game-version", "1.21.11");
        String gameDir = gameDirectory;
        System.out.println("Loader initialized with " + modManager.getLoadedModCount() + " mods for " + gameVersion);

        // If we were launched as wrapper for Minecraft, delegate to real Minecraft main
        String mcMain = System.getProperty("hitboy.minecraft.main", "net.minecraft.client.main.Main");
        if (!mcMain.isEmpty()) {
            String mcArgsStr = System.getProperty("hitboy.minecraft.args", "");
            String[] mcArgs = mcArgsStr.isEmpty() ? args : mcArgsStr.split("\u001F");
            System.out.println("Bootstrapping Minecraft main: " + mcMain + " mods=" + modManager.getLoadedModCount());
            // Post init event so mods can do setup
            eventBus.post(new Object() { public String toString(){return "HitBoyInit";}});
            Class<?> mcClass = Class.forName(mcMain, true, Thread.currentThread().getContextClassLoader());
            java.lang.reflect.Method m = mcClass.getMethod("main", String[].class);
            m.invoke(null, (Object) mcArgs);
            return;
        }
        // standalone demo
        testEventBus(eventBus);
    }

    private static void testEventBus(EventBus bus) {
        bus.post(new Object());
        System.out.println("Test event posted - run via MinecraftLauncher for real game");
    }
    
    @SuppressWarnings("unchecked")
    private static Map<String, String> loadMappings(String mappingFile) throws IOException {
        Map<String, String> mappings = new HashMap<>();
        Path path = Paths.get(mappingFile);
        if (!Files.exists(path)) return mappings;
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) continue;
            String[] parts = line.split("=");
            if (parts.length == 2) mappings.put(parts[0].trim(), parts[1].trim());
        }
        return mappings;
    }
}
