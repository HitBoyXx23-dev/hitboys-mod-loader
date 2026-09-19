package com.hitboy.loader.mixin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.spongepowered.asm.service.IGlobalPropertyService;
import org.spongepowered.asm.service.IPropertyKey;

public final class HitBoyMixinProperties implements IGlobalPropertyService {
    private static final Map<IPropertyKey, Object> VALUES = new ConcurrentHashMap<>();
    private record Key(String name) implements IPropertyKey {}
    @Override public IPropertyKey resolveKey(String name) { return new Key(name); }
    @Override @SuppressWarnings("unchecked") public <T> T getProperty(IPropertyKey key) { return (T) VALUES.get(key); }
    @Override public void setProperty(IPropertyKey key, Object value) {
        if (value == null) VALUES.remove(key); else VALUES.put(key, value);
    }
    @Override public <T> T getProperty(IPropertyKey key, T fallback) {
        T value = getProperty(key);
        return value == null ? fallback : value;
    }
    @Override public String getPropertyString(IPropertyKey key, String fallback) {
        Object value = VALUES.get(key);
        return value == null ? fallback : value.toString();
    }
}
