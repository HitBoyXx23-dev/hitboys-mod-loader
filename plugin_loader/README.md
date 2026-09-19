# HitBoy's Plugin Loader

A custom server-side plugin ecosystem for Spigot/Paper/Purpur servers. This is
a **first-phase seed**, not a full custom server core: it deliberately runs as
a normal plugin on top of Bukkit/Paper/Purpur (rather than re-implementing a
server from scratch, which is not realistic to build here) and exposes its
own small, stable `HitBoyPlugin` API so plugin authors don't have to chase
Bukkit API changes across Minecraft versions.

Roadmap note: this is the seed of "HitBoy's Plugin Loader" as a long-term
Spigot/Paper/Purpur-style ecosystem, not full parity with those projects yet.

## How it works

1. Drop `HitBoysPluginLoader.jar` (built from this module) into your
   Spigot/Paper/Purpur server's `plugins/` folder, like any other plugin.
2. On first start it creates `plugins/HitBoysPluginLoader/plugins/` -- this is
   where **HitBoy plugins** go (a different folder from the server's own
   `plugins/`, so the two systems don't get mixed up).
3. Each HitBoy plugin is a plain jar containing a `plugin.json` at its root
   and a main class implementing `com.hitboy.pluginloader.api.HitBoyPlugin`.
4. `/hitboyplugins` (alias `/hbp`) lists loaded plugins; `/hitboyplugins
   reload` reloads them all without restarting the server.

## Why this design

Raw Minecraft server internals (like the client) are obfuscated and differ
release to release, which is why the client-side loader needs verified,
version-specific ASM hooks. Bukkit/Spigot/Paper deliberately expose a stable,
**unobfuscated public API** that has stayed source-compatible since roughly
1.8, specifically so plugins don't break every update. HitBoy's Plugin Loader
takes advantage of that: it converts real Bukkit events into its own
simplified event types in one bridge listener (`BukkitEventBridge`), so a
HitBoy plugin never touches Bukkit types directly and stays portable across
whichever Bukkit-based server version you run it on.

## `plugin.json` format

```json
{
  "name": "HelloPlugin",
  "version": "1.0.0",
  "main": "com.hitboy.example.HelloPlugin",
  "description": "Optional human-readable description.",
  "author": "Optional author name"
}
```

`name`, `version`, and `main` are required.

## The `HitBoyPlugin` API

```java
public final class HelloPlugin implements HitBoyPlugin {
    @Override
    public void onEnable(PluginContext context) {
        context.logger().info("Hello from " + context.pluginName());

        context.events().subscribe(PlayerJoinEvent.class, event -> {
            event.setJoinMessage("Welcome, " + event.playerName() + "!");
        });

        context.commands().register("hbhello", "Says hello.", (senderName, args) -> {
            context.logger().info("Hello requested by " + senderName);
            return true;
        });
    }
}
```

- `PluginContext` gives you a logger, a per-plugin data folder, an event bus,
  and a command registry -- no Bukkit imports required.
- Available events today (`com.hitboy.pluginloader.api.events`):
  `PlayerJoinEvent`, `PlayerQuitEvent`, `PlayerChatEvent`, `BlockBreakEvent`.
  More can be added the same way: extend `BukkitEventBridge` to translate one
  more Bukkit event into a new simplified event type.
- `CommandRegistry.register(name, description, handler)` registers a command
  directly with the server's command map -- no `plugin.yml` command block
  needed for HitBoy plugins.

## Building

```powershell
# Build the loader itself
cd plugin_loader
mvn clean install

# Build the example plugin against it
cd examples\hello-plugin
mvn clean package
```

Output: `plugin_loader/target/HitBoysPluginLoader.jar` and
`plugin_loader/examples/hello-plugin/target/hello-plugin.jar`.

## Compatibility

Compiled against the publicly-hosted PaperMC 1.20.1 API (Paper's API is a
strict superset of Spigot/Bukkit's), but the event and command hooks used
here (`PlayerJoinEvent`, `PlayerQuitEvent`, `AsyncPlayerChatEvent`,
`BlockBreakEvent`, `Server#commandMap`) have existed with the same
signatures since Bukkit 1.8, so a build of this jar is expected to load on
Spigot/Paper/Purpur servers across that whole range. It has not been
tested against a live server in this environment (no server process
available here) -- validate on your own server before relying on it in
production.

## Known limitations (roadmap)

- No plugin dependency resolution (a HitBoy plugin can't declare "load
  after X" yet).
- No permission-node integration yet (commands run for any sender).
- Only 4 event types are bridged so far; more Bukkit events can be added to
  `BukkitEventBridge` following the same pattern.
- Not tested against a running server (sandbox has no Minecraft server
  process available) -- please test in a real environment before production
  use.
