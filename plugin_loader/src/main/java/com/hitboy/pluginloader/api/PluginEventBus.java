package com.hitboy.pluginloader.api;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A typed pub/sub bus for HitBoy plugin events. This is intentionally
 * separate from Bukkit's own event system: {@code HitBoysPluginLoader}
 * translates real Bukkit events into these simplified event types once, in
 * one bridge listener, and fans them out here -- so individual plugins never
 * register raw Bukkit {@code Listener}s or depend on Bukkit event class
 * signatures that can shift between server versions.
 */
public final class PluginEventBus {
    private static final Logger LOGGER = Logger.getLogger("HitBoysPluginLoader");

    private final Map<Class<?>, List<Consumer<Object>>> handlers = new ConcurrentHashMap<>();

    /** Registers a handler for a specific event type, e.g. {@code PlayerJoinEvent.class}. */
    @SuppressWarnings("unchecked")
    public <T> void subscribe(Class<T> eventType, Consumer<T> handler) {
        handlers
            .computeIfAbsent(eventType, key -> new CopyOnWriteArrayList<>())
            .add((Consumer<Object>) handler);
    }

    /** Dispatches an event instance to every handler subscribed to its exact class. */
    public void publish(Object event) {
        List<Consumer<Object>> subscribed = handlers.get(event.getClass());
        if (subscribed == null || subscribed.isEmpty()) {
            return;
        }
        for (Consumer<Object> handler : subscribed) {
            try {
                handler.accept(event);
            } catch (RuntimeException e) {
                LOGGER.log(Level.WARNING, "A HitBoy plugin handler threw an exception for "
                    + event.getClass().getSimpleName(), e);
            }
        }
    }
}
