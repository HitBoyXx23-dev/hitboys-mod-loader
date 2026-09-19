package com.hitboy.loader;

import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of IMinecraftClientWrapper.
 * Uses reflection to stay version-agnostic and compile without Minecraft jar.
 */
public class MinecraftClientWrapper implements IMinecraftClientWrapper {

    private final Object mc;
    
    public MinecraftClientWrapper(Object mc) {
        this.mc = mc;
    }
    
    @Override
    public long getTick() {
        return getLongField(new String[]{"tick", "worldTime", "field_71439_g"}, 0L);
    }
    
    @Override
    public int getFps() {
        // Try multiple possible fps field names across versions
        Object val = getFieldValue(new String[]{"fps", "field_78281_c", "frameRate", "debugFPS"});
        if (val instanceof Number) return ((Number) val).intValue();
        return GameContext.getFps() != 0 ? GameContext.getFps() : 60;
    }
    
    @Override
    public double getPlayerX() {
        Object player = getPlayerEntity();
        if (player != null) {
            Object v = getNestedField(player, new String[]{"posX", "field_70165_t", "x"});
            if (v instanceof Number) return ((Number) v).doubleValue();
        }
        return GameContext.getPlayerX();
    }
    
    @Override
    public double getPlayerY() {
        Object player = getPlayerEntity();
        if (player != null) {
            Object v = getNestedField(player, new String[]{"posY", "field_70163_u", "y"});
            if (v instanceof Number) return ((Number) v).doubleValue();
        }
        return GameContext.getPlayerY();
    }
    
    @Override
    public double getPlayerZ() {
        Object player = getPlayerEntity();
        if (player != null) {
            Object v = getNestedField(player, new String[]{"posZ", "field_70161_v", "z"});
            if (v instanceof Number) return ((Number) v).doubleValue();
        }
        return GameContext.getPlayerZ();
    }
    
    @Override
    public boolean isRendering() {
        Object screen = getFieldValue(new String[]{"currentScreen", "field_71462_r", "screen"});
        return screen == null;
    }
    
    @Override
    public Object getMinecraftInstance() {
        return mc;
    }
    
    @Override
    public Object getPlayerEntity() {
        Object v = getFieldValue(new String[]{"player", "field_71439_g", "thePlayer"});
        return v;
    }
    
    @Override
    public List<Object> getRenderedEntities() {
        return new ArrayList<>();
    }
    
    @Override
    public void beginRender() {}
    
    @Override
    public void endRender() {}
    
    @Override
    public float getPartialTicks() {
        Object timer = getFieldValue(new String[]{"timer", "field_71428_T", "renderPartialTicks"});
        if (timer != null) {
            // timer may be object with field renderPartialTicks
            Object v = getNestedField(timer, new String[]{"renderPartialTicks", "field_74286_b", "partialTicks"});
            if (v instanceof Number) return ((Number)v).floatValue();
            if (timer instanceof Number) return ((Number)timer).floatValue();
        }
        return 1.0f;
    }
    
    @Override
    public long getWorldTime() {
        return getTick();
    }
    
    @Override
    public boolean isPlayerOnGround() {
        Object player = getPlayerEntity();
        if (player != null) {
            Object v = getNestedField(player, new String[]{"onGround", "field_70122_E"});
            if (v instanceof Boolean) return (Boolean)v;
        }
        return false;
    }

    // --- reflection helpers ---
    private Object getFieldValue(String[] names) {
        return getNestedField(mc, names);
    }
    private long getLongField(String[] names, long def) {
        Object v = getFieldValue(names);
        return v instanceof Number ? ((Number)v).longValue() : def;
    }
    private Object getNestedField(Object target, String[] names) {
        if (target == null) return null;
        Class<?> c = target.getClass();
        for (String n : names) {
            try {
                java.lang.reflect.Field f = c.getField(n);
                f.setAccessible(true);
                return f.get(target);
            } catch (Exception ignored) {}
            try {
                java.lang.reflect.Field f = c.getDeclaredField(n);
                f.setAccessible(true);
                return f.get(target);
            } catch (Exception ignored) {}
        }
        // try superclasses
        Class<?> sc = c.getSuperclass();
        while (sc != null && sc != Object.class) {
            for (String n : names) {
                try {
                    java.lang.reflect.Field f = sc.getDeclaredField(n);
                    f.setAccessible(true);
                    return f.get(target);
                } catch (Exception ignored) {}
            }
            sc = sc.getSuperclass();
        }
        return null;
    }
}
