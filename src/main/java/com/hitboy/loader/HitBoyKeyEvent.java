package com.hitboy.loader;

import java.lang.reflect.Method;

public final class HitBoyKeyEvent {
    private final long window;
    private final int action;
    private final int key;
    private final int scanCode;
    private final int modifiers;

    private HitBoyKeyEvent(long window, int action, int key, int scanCode, int modifiers) {
        this.window = window;
        this.action = action;
        this.key = key;
        this.scanCode = scanCode;
        this.modifiers = modifiers;
    }

    static HitBoyKeyEvent from(long window, int action, Object minecraftEvent) {
        return new HitBoyKeyEvent(
            window,
            action,
            invokeInt(minecraftEvent, "a", "key", "keyCode"),
            invokeInt(minecraftEvent, "t", "scanCode"),
            invokeInt(minecraftEvent, "u", "modifiers")
        );
    }

    private static int invokeInt(Object target, String... names) {
        if (target == null) {
            return 0;
        }
        for (String name : names) {
            try {
                Method method = target.getClass().getMethod(name);
                Object value = method.invoke(target);
                if (value instanceof Number) {
                    return ((Number) value).intValue();
                }
            } catch (ReflectiveOperationException ignored) {
            }
        }
        return 0;
    }

    public long getWindow() {
        return window;
    }

    public int getAction() {
        return action;
    }

    public int getKey() {
        return key;
    }

    public int getScanCode() {
        return scanCode;
    }

    public int getModifiers() {
        return modifiers;
    }

    public boolean isPressed() {
        return action == 1;
    }
}
