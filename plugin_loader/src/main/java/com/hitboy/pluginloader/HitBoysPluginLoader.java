package com.hitboy.pluginloader;

import com.hitboy.pluginloader.api.PluginEventBus;
import com.hitboy.pluginloader.bridge.BukkitCommandRegistry;
import com.hitboy.pluginloader.bridge.BukkitEventBridge;
import com.hitboy.pluginloader.bridge.PluginManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * HitBoy's Plugin Loader.
 *
 * This is a regular Bukkit/Paper/Purpur plugin (dropped into the server's
 * {@code plugins/} folder like any other) that itself hosts a second, custom
 * plugin ecosystem: jars with a {@code plugin.json} descriptor placed in
 * {@code plugins/HitBoysPluginLoader/plugins/}, loaded through
 * {@link PluginManager}. This gives HitBoy plugins a small, stable API
 * (see the {@code com.hitboy.pluginloader.api} package) instead of coupling
 * them directly to Bukkit's API, which does shift between major server
 * versions.
 *
 * This is a first-phase seed toward "HitBoy's Plugin Loader" as a
 * Spigot/Paper/Purpur-style ecosystem -- it deliberately reuses the
 * Bukkit/Paper API under the hood (rather than re-implementing server
 * internals) because that API has been stable across roughly 1.8 through
 * the current version, which is what makes broad version support realistic
 * here without per-version obfuscation mapping work.
 */
public final class HitBoysPluginLoader extends JavaPlugin {
    private PluginEventBus eventBus;
    private PluginManager pluginManager;

    @Override
    public void onEnable() {
        eventBus = new PluginEventBus();
        getServer().getPluginManager().registerEvents(new BukkitEventBridge(eventBus), this);

        BukkitCommandRegistry commandRegistry = new BukkitCommandRegistry(this, getLogger());

        File pluginsDir = new File(getDataFolder(), "plugins");
        File dataRoot = new File(getDataFolder(), "plugin_data");
        pluginManager = new PluginManager(getLogger(), pluginsDir, dataRoot, eventBus, commandRegistry);
        pluginManager.loadAll();

        getLogger().info("HitBoy's Plugin Loader is up -- " + pluginManager.loadedPlugins().size()
            + " plugin(s) loaded from " + pluginsDir.getPath());
    }

    @Override
    public void onDisable() {
        if (pluginManager != null) {
            pluginManager.disableAll();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            pluginManager.disableAll();
            pluginManager.loadAll();
            sender.sendMessage("Reloaded " + pluginManager.loadedPlugins().size() + " HitBoy plugin(s).");
            return true;
        }
        sender.sendMessage("HitBoy's Plugin Loader -- " + pluginManager.loadedPlugins().size() + " plugin(s) loaded:");
        for (PluginManager.LoadedPlugin loaded : pluginManager.loadedPlugins()) {
            sender.sendMessage(" - " + loaded.descriptor().name() + " v" + loaded.descriptor().version()
                + " by " + loaded.descriptor().author());
        }
        return true;
    }
}
