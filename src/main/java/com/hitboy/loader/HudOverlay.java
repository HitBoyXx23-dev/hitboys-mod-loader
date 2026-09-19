package com.hitboy.loader;

import java.lang.reflect.Method;

/**
 * Reflection-safe HUD drawing helpers for native mods on supported clients.
 */
public final class HudOverlay {
    private HudOverlay() {
    }

    /**
     * True for Minecraft 1.21.11's HUD render context: {@code GuiGraphics}
     * payload from the {@code gfj} (Minecraft) client's {@code giq#a} hook.
     */
    public static boolean isModern12111Context(Object guiGraphics, Object minecraft) {
        return guiGraphics != null
            && minecraft != null
            && "gir".equals(guiGraphics.getClass().getName())
            && "gfj".equals(minecraft.getClass().getName());
    }

    /**
     * True for Minecraft 1.20.1's HUD render context: {@code GuiGraphics}
     * payload from the {@code enn} (Minecraft) client's {@code eow#a} hook.
     * Mapping verified against Mojang's official 1.20.1 client mappings
     * (Gui -> eow, GuiGraphics -> eox, render(GuiGraphics,float) -> a).
     */
    public static boolean isModern1201Context(Object guiGraphics, Object minecraft) {
        return guiGraphics != null
            && minecraft != null
            && "eox".equals(guiGraphics.getClass().getName())
            && "enn".equals(minecraft.getClass().getName());
    }

    /**
     * True for any supported {@code GuiGraphics}-based HUD context (1.20.1
     * or 1.21.11). Prefer this over the version-specific checks unless a mod
     * genuinely needs to special-case one version's rendering.
     */
    public static boolean isModernContext(Object guiGraphics, Object minecraft) {
        return isModern12111Context(guiGraphics, minecraft) || isModern1201Context(guiGraphics, minecraft);
    }

    /**
     * True when the payload is Minecraft 1.16.5's HUD render context: a
     * {@code com.mojang.blaze3d.vertex.PoseStack} passed from {@code Gui#render}
     * on the {@code djz} (Minecraft) client. Mapping verified against Mojang's
     * official 1.16.5 client mappings.
     */
    public static boolean isLegacy165Context(Object poseStack, Object minecraft) {
        return poseStack != null
            && minecraft != null
            && "com.mojang.blaze3d.vertex.PoseStack".equals(poseStack.getClass().getName())
            && "djz".equals(minecraft.getClass().getName());
    }

    public static boolean drawPanel(Object guiGraphics, int left, int top, int right, int bottom, int color) {
        try {
            Method fill = guiGraphics.getClass().getMethod(
                "a", int.class, int.class, int.class, int.class, int.class
            );
            fill.invoke(guiGraphics, left, top, right, bottom, color);
            return true;
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }

    /** Maps each supported Minecraft client's obfuscated class name to its font field name. */
    private static final java.util.Map<String, String> FONT_FIELD_BY_CLIENT = java.util.Map.of(
        "gfj", "g",  // 1.21.11
        "enn", "h",  // 1.20.1
        "djz", "g"   // 1.16.5 (legacy PoseStack path also uses this map)
    );

    public static boolean drawText(
        Object guiGraphics, Object minecraft, String text, int x, int y, int color, boolean shadow
    ) {
        try {
            Object font = resolveFont(minecraft);
            if (font == null) {
                return false;
            }
            for (Method method : guiGraphics.getClass().getMethods()) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (!"a".equals(method.getName()) || parameterTypes.length != 6
                    || !parameterTypes[0].isAssignableFrom(font.getClass())
                    || parameterTypes[1] != String.class
                    || parameterTypes[2] != int.class
                    || parameterTypes[3] != int.class
                    || parameterTypes[4] != int.class
                    || parameterTypes[5] != boolean.class) {
                    continue;
                }
                method.invoke(guiGraphics, font, text, x, y, color, shadow);
                return true;
            }
            for (Method method : guiGraphics.getClass().getMethods()) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (!"a".equals(method.getName()) || parameterTypes.length != 5
                    || !parameterTypes[0].isAssignableFrom(font.getClass())
                    || parameterTypes[1] != String.class
                    || parameterTypes[2] != int.class
                    || parameterTypes[3] != int.class
                    || parameterTypes[4] != int.class) {
                    continue;
                }
                method.invoke(guiGraphics, font, text, x, y, color);
                return true;
            }
        } catch (ReflectiveOperationException e) {
            return false;
        }
        return false;
    }

    private static Object resolveFont(Object minecraft) throws ReflectiveOperationException {
        String fieldName = FONT_FIELD_BY_CLIENT.get(minecraft.getClass().getName());
        if (fieldName == null) {
            return null;
        }
        java.lang.reflect.Field fontField = minecraft.getClass().getDeclaredField(fieldName);
        fontField.setAccessible(true);
        return fontField.get(minecraft);
    }

    public static int getWidth(Object guiGraphics) {
        return getDimension(guiGraphics, "a");
    }

    public static int getHeight(Object guiGraphics) {
        return getDimension(guiGraphics, "b");
    }

    private static int getDimension(Object guiGraphics, String methodName) {
        try {
            Object value = guiGraphics.getClass().getMethod(methodName).invoke(guiGraphics);
            return value instanceof Number ? ((Number) value).intValue() : 0;
        } catch (ReflectiveOperationException e) {
            return 0;
        }
    }

    // --- Minecraft 1.16.5 (PoseStack-based Gui) helpers ---

    public static boolean drawPanel165(Object poseStack, int left, int top, int right, int bottom, int color) {
        try {
            Class<?> guiClass = Class.forName("dkv", true, poseStack.getClass().getClassLoader());
            Method fill = guiClass.getMethod(
                "a", poseStack.getClass(), int.class, int.class, int.class, int.class, int.class
            );
            fill.invoke(null, poseStack, left, top, right, bottom, color);
            return true;
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }

    public static boolean drawText165(
        Object poseStack, Object minecraft, String text, int x, int y, int color
    ) {
        try {
            java.lang.reflect.Field fontField = minecraft.getClass().getDeclaredField("g");
            fontField.setAccessible(true);
            Object font = fontField.get(minecraft);
            if (font == null) {
                return false;
            }
            Class<?> guiClass = Class.forName("dkv", true, poseStack.getClass().getClassLoader());
            for (Method method : guiClass.getMethods()) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (!"b".equals(method.getName()) || parameterTypes.length != 6
                    || !parameterTypes[0].isAssignableFrom(poseStack.getClass())
                    || !parameterTypes[1].isAssignableFrom(font.getClass())
                    || parameterTypes[2] != String.class
                    || parameterTypes[3] != int.class
                    || parameterTypes[4] != int.class
                    || parameterTypes[5] != int.class) {
                    continue;
                }
                method.invoke(null, poseStack, font, text, x, y, color);
                return true;
            }
        } catch (ReflectiveOperationException e) {
            return false;
        }
        return false;
    }

    public static int getWidth165(Object minecraft) {
        return getWindowDimension165(minecraft, "o");
    }

    public static int getHeight165(Object minecraft) {
        return getWindowDimension165(minecraft, "p");
    }

    private static int getWindowDimension165(Object minecraft, String methodName) {
        try {
            java.lang.reflect.Field windowField = minecraft.getClass().getDeclaredField("O");
            windowField.setAccessible(true);
            Object window = windowField.get(minecraft);
            if (window == null) {
                return 0;
            }
            Object value = window.getClass().getMethod(methodName).invoke(window);
            return value instanceof Number ? ((Number) value).intValue() : 0;
        } catch (ReflectiveOperationException e) {
            return 0;
        }
    }
}
