package com.hitboy.loader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ClientFeatureState {
    private static final Map<String, Boolean> FEATURES = new ConcurrentHashMap<>();

    private ClientFeatureState() {
    }

    public static boolean isEnabled(String feature) {
        return FEATURES.getOrDefault(normalize(feature), false);
    }

    public static void setEnabled(String feature, boolean enabled) {
        FEATURES.put(normalize(feature), enabled);
    }

    public static boolean toggle(String feature) {
        String key = normalize(feature);
        boolean enabled = !FEATURES.getOrDefault(key, false);
        FEATURES.put(key, enabled);
        return enabled;
    }

    private static String normalize(String feature) {
        if (feature == null || feature.trim().isEmpty()) {
            throw new IllegalArgumentException("Feature name cannot be empty.");
        }
        return feature.trim().toLowerCase(java.util.Locale.ROOT);
    }
}
