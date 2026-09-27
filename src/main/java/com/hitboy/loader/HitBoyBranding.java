package com.hitboy.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * HitBoy's Mixed Compatible Mod Loader runs NeoForge underneath (so NeoForge mods can run), but the
 * game presents itself as HitBoy's: HitBoy's agent routes NeoForge's branding through this class, which
 * replaces NeoForge's title-screen lines and client brand and turns off NeoForge's own loading window.
 */
public final class HitBoyBranding {
    public static final String MIXED_NAME = "HitBoy's Mixed Compatible Mod Loader";

    private HitBoyBranding() {
    }

    /** Mixed compatibility with NeoForge as the engine underneath. */
    public static boolean mixedEngine() {
        return "neoforge".equalsIgnoreCase(System.getProperty("hitboy.base", ""))
            && com.hitboy.loader.fabric.FabricRuntime.enabled();
    }

    /** NeoForge's title-screen lines, e.g. "NeoForge 26.3.0.23-beta (8 mods loaded)" -> "HitBoy's ... (8 mods loaded)". */
    public static List<String> rebrand(List<String> lines) {
        if (lines == null || !mixedEngine()) return lines;
        List<String> branded = new ArrayList<>();
        for (String line : lines) branded.add(line == null ? null : line.replaceAll("(?i)neoforge\\s+[^\\s(]+", MIXED_NAME));
        return branded;
    }

    /** NeoForge's client/server brand ("neoforge"). */
    public static String brand(String original) {
        return mixedEngine() ? "hitboy" : original;
    }

    /** NeoForge settings HitBoy switches off in mixed mode: its branded loading window and update notice. */
    public static boolean forceOff(Object configValue) {
        if (configValue == null || !mixedEngine()) return false;
        String name = configValue.toString().toUpperCase(Locale.ROOT);
        return name.equals("EARLY_WINDOW_CONTROL") || name.equals("VERSION_CHECK");
    }
}
