package com.hitboy.loader;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class HitBoyBrandingTest {
    @AfterEach
    void resetProperties() {
        System.clearProperty("hitboy.base");
        System.clearProperty("hitboy.mixed-compatibility");
    }

    @Test
    void removesUnderlyingLoaderAndBetaBranding() {
        System.setProperty("hitboy.base", "neoforge");
        System.setProperty("hitboy.mixed-compatibility", "true");

        List<String> result = HitBoyBranding.rebrand(List.of(
            "NeoForge 21.11.12-beta (8 mods loaded)",
            "Minecraft 1.21.11 beta"
        ));

        assertEquals("HitBoy's Mixed Compatible Mod Loader (8 mods loaded)", result.get(0));
        assertFalse(String.join(" ", result).toLowerCase().contains("neoforge"));
        assertFalse(String.join(" ", result).toLowerCase().contains("beta"));
    }
}
