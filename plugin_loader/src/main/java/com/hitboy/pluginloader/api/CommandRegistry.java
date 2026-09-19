package com.hitboy.pluginloader.api;

/**
 * Lets a {@link HitBoyPlugin} register its own commands without touching
 * Bukkit's {@code CommandMap} directly. The loader's bridge implementation
 * registers the command with the underlying server under the hood.
 */
public interface CommandRegistry {
    /**
     * Registers a new command. If a command with this name already exists
     * (from Bukkit itself or another plugin), it is registered under a
     * fallback prefix instead of failing, matching normal Bukkit behavior.
     */
    void register(String name, String description, CommandHandler handler);
}
