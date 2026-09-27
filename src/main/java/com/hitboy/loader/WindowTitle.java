package com.hitboy.loader;

/**
 * Brands the game window's title bar. Every Minecraft version sets its title through
 * {@code GLFW.glfwSetWindowTitle} (26.x: {@code SDLVideo.SDL_SetWindowTitle}); HitBoy's agent passes that title through {@link #brand} first.
 */
public final class WindowTitle {
    private static volatile String last;

    private WindowTitle() {
    }

    /** The loader's display name: mixed mode and NeoForge/Forge bases are named separately. */
    public static String loaderName() {
        String name = com.hitboy.loader.fabric.FabricRuntime.enabled()
            ? "HitBoy's Mixed Compatible Mod Loader" : "HitBoy's Mod Loader";
        String base = System.getProperty("hitboy.base", "");
        if (HitBoyBranding.mixedEngine()) return name; // NeoForge only runs underneath; HitBoy is the loader shown
        if (base.equalsIgnoreCase("neoforge")) return name + " + NeoForge";
        if (base.equalsIgnoreCase("forge")) return name + " + Forge";
        if (base.equalsIgnoreCase("fabric")) return name + " + Fabric";
        return name;
    }

    public static CharSequence brand(CharSequence title) {
        String text = title == null ? "" : title.toString();
        if (HitBoyBranding.mixedEngine()) text = text.replace(" NeoForge", "");
        String name = loaderName();
        String branded = text.startsWith(name) ? text : text.isEmpty() ? name : name + " | " + text;
        if (!branded.equals(last)) {
            last = branded;
            System.out.println("Window title: " + branded);
        }
        return branded;
    }
}
