package com.hitboy.loader;

public class MinecraftHooks {
    public static boolean shouldBlockMouseLook() {
        return ClientFeatureState.isEnabled("menu-open");
    }

    public static boolean shouldBlockKeyInput(int action, Object minecraftEvent) {
        if (!ClientFeatureState.isEnabled("menu-open") || action == 0) {
            return false;
        }
        int key = HitBoyKeyEvent.from(0L, action, minecraftEvent).getKey();
        return key != 344 && key != 256;
    }

    public static boolean shouldBlockMouseButton(int action) {
        return ClientFeatureState.isEnabled("menu-open") && action != 0;
    }

    public static void onGameLoopStart(Object minecraft) {
        String className = minecraft == null ? "" : minecraft.getClass().getName();
        if (!"enn".equals(className) && !"gfj".equals(className) && !"djz".equals(className)
            && !"net.minecraft.client.Minecraft".equals(className)) {
            return;
        }
        GameContext.setMinecraft(minecraft);
        ModMenuHelper.injectForCurrentScreen(minecraft);
        EventBus bus = NativeLoader.getEventBus();
        if (bus != null) {
            bus.postEvent("tick", minecraft);
            bus.postEvent("render", minecraft); // also force render for HUD when render hook misses obfuscated name
        }
    }
    public static void onRenderTick(Object minecraft) {
        GameContext.setMinecraft(minecraft);
        ModMenuHelper.injectForCurrentScreen(minecraft);
        try {
            Object mc = minecraft;
            for (String n : new String[]{"debugFPS","field_71470_ab","fps","bb"}) {
                try { java.lang.reflect.Field f = mc.getClass().getField(n); GameContext.setFps(((Number)f.get(mc)).intValue()); break; }
                catch (Exception ignored) { try{ java.lang.reflect.Field f=mc.getClass().getDeclaredField(n); f.setAccessible(true); GameContext.setFps(((Number)f.get(mc)).intValue()); break;} catch(Exception ignored2){} }
            }
        } catch (Exception ignored) {}
        EventBus bus = NativeLoader.getEventBus();
        if (bus != null) bus.postEvent("render", minecraft);
    }
    public static void onHudRender(Object guiGraphics) {
        EventBus bus = NativeLoader.getEventBus();
        if (bus != null) bus.postEvent("hud", guiGraphics);
    }
    // Fired from Gui#render(PoseStack, float) on Minecraft 1.16.5 (and other
    // pre-GuiGraphics releases sharing that rendering API). Kept as a distinct
    // event name from "hud" since the payload type (PoseStack) is unrelated to
    // the modern GuiGraphics payload; see HudOverlay's isLegacy165Context.
    public static void onHudRender165(Object poseStack) {
        EventBus bus = NativeLoader.getEventBus();
        if (bus != null) bus.postEvent("hud165", poseStack);
    }
    public static void onKeyPress(String key) {
        EventBus bus = NativeLoader.getEventBus();
        if (bus != null) bus.postEvent("keypress", key);
        if (bus != null) bus.postEvent("keystrokes", key);
    }

    public static void onKeyInput(long window, int action, Object minecraftEvent) {
        EventBus bus = NativeLoader.getEventBus();
        if (bus == null) {
            return;
        }
        HitBoyKeyEvent event = HitBoyKeyEvent.from(window, action, minecraftEvent);
        bus.postEvent("key", event);
        bus.postEvent("keypress", event);
    }
}
