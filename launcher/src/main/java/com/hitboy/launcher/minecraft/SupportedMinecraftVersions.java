package com.hitboy.launcher.minecraft;

import java.util.List;

public final class SupportedMinecraftVersions {
    public static final String DEFAULT_VERSION = "1.21.11";
    private static final List<String> VERSIONS = List.of("26.3", "26.2", DEFAULT_VERSION, "1.20.1", "1.16.5");

    private SupportedMinecraftVersions() {
    }

    public static boolean isSupported(String version) {
        return VERSIONS.contains(version);
    }

    public static List<String> all() {
        return VERSIONS;
    }

    public static String displayList() {
        return String.join(", ", VERSIONS);
    }
}
