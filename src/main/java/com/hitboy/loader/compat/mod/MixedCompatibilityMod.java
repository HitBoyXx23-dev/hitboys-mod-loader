package com.hitboy.loader.compat.mod;

import com.hitboy.loader.NativeMod;
import com.hitboy.loader.api.HitBoyClientInitializer;

@NativeMod(name = "HitBoy Mixed Compatibility", version = "1.0.0")
public final class MixedCompatibilityMod implements HitBoyClientInitializer {
    @Override
    public void onInitializeClient() {
        System.setProperty("hitboy.compatibility.bridge", "true");
        System.out.println("HitBoy mixed compatibility bridge enabled.");
    }
}
