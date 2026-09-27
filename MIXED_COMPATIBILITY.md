# Mixed compatibility: Fabric, NeoForge, and Forge mods on HitBoy

HitBoy's mixed-compatibility mode runs **Fabric mods next to HitBoy mods**, with
no Fabric Loader installed. Put the Fabric mod JARs (and Fabric API, if a mod
needs it) in the same mods folder as your HitBoy mods.

| | Status |
|---|---|
| Fabric mods on Minecraft **1.21.11** | Supported (remapped from Fabric's intermediary names) |
| Fabric mods on Minecraft **26.3 / 26.2** | Supported (26.x uses Minecraft's real names, so no remapping) |
| Fabric API | Supported: the real Fabric API JAR runs as ordinary Fabric mods |
| NeoForge mods | Supported through the patcher's NeoForge option (runs the real NeoForge with HitBoy) |
| Fabric mods inside a NeoForge game (26.x) | Supported for Fabric mods that do not need Fabric API (HitBoy converts them automatically) |
| Fabric API inside NeoForge | Not yet: skipped with a log message, and so are mods that need it |
| Forge mods inside a NeoForge game | Port them first with port.exe (simple mods; see below) |
| NeoForge + Forge loaders in one game | Not possible (separate loaders); skipped with a log message |
| Fabric mods on the real Fabric Loader | Supported through the patcher's Fabric option (26.x recommended) |
| Forge mods | Supported through the patcher's Forge option (runs the real Forge with HitBoy) |


## All four loaders in one game (26.x)

**HitBoy's Mixed Compatible Mod Loader** runs HitBoy, Fabric, NeoForge, and Forge
mods together on Minecraft 26.x. Install it with the patcher's **Install
HitBoy's Mixed Compatible Mod Loader** option ("Minecraft 26.3/HitBoy's Mixed
Compatible Mod Loader" in the official launcher), or start
`hitboy-mixed-mod-compatibility.exe` (it installs what it needs on first start).

| Mods | How they run |
|---|---|
| HitBoy | Natively, by HitBoy |
| NeoForge | By NeoForge, which runs underneath as HitBoy's engine for them |
| Fabric | Converted to NeoForge mods by HitBoy when the game starts (Fabric API: not yet) |
| Forge | Ported to NeoForge with port.exe's converter when the game starts (mods it cannot port are skipped, with the reasons in the log) |

HitBoy is the loader the game shows, not NeoForge: the window title reads
"HitBoy's Mixed Compatible Mod Loader | Minecraft 26.3", the title screen reads
"HitBoy's Mixed Compatible Mod Loader (N mods loaded)", the client brand is
"hitboy", NeoForge's own loading window and update notice are off, and HitBoy's
**Mods** button takes the place of NeoForge's and lists every mod by loader.

Tested on 26.3 in one world with Fullbright (HitBoy), Lithium (Fabric), Jade and
AppleSkin (NeoForge), and Mouse Tweaks (Forge, ported at start), both through the
official launcher installation and through the standalone launcher.

On 1.21.11, mixed compatibility is HitBoy + Fabric mods (as below).

## Turning it on

- **Standalone:** start `hitboy-mixed-mod-compatibility.exe` instead of
  `hitboys-mod-loader.exe`. Mods go in `%USERPROFILE%\.hitboys-modloader\native_mods`.
- **Official Minecraft Launcher:** in `hitboy-patcher.exe`, tick
  **Install Mixed Compatibility patch** before installing. Mods go in
  `%APPDATA%\.minecraft\mods`.
- Advanced: set the environment variable `HITBOY_MIXED_COMPATIBILITY=1`, or pass
  `-Dhitboy.mixed-compatibility=true` to the game JVM.

Without mixed mode, Fabric mods in the folder are skipped and HitBoy mods run as
usual.

## NeoForge mods

The patcher's **Also install NeoForge** option runs NeoForge mods and HitBoy mods
in the same game:

1. It downloads NeoForge's official installer for your Minecraft version (the
   newest stable build, or the newest beta if there is no stable one yet) and
   runs it into `.minecraft`.
2. It adds a **"Minecraft `<version>`/HitBoy's Mod Loader + NeoForge"**
   installation to the official Minecraft Launcher.
3. Put NeoForge mods and HitBoy mods together in `.minecraft\mods`.

NeoForge runs NeoForge mods with its own loader. HitBoy is attached as a Java
agent: it loads HitBoy mods before NeoForge starts, adds the HitBoy title
branding and Mods button, and tells NeoForge to leave HitBoy's JARs alone so
NeoForge does not show a "not a valid mod file" warning for them.

Tested on Minecraft 26.3 with NeoForge 26.3.0.23-beta, Jade 26.3.1, AppleSkin
3.0.10, and a HitBoy mod: all loaded and the player joined a world.

NeoForge 26.3 is still in beta. On the test PC it sometimes crashed natively while
loading resources, **with or without HitBoy** (plain NeoForge crashed in 2 of 3
runs), so that crash comes from NeoForge's beta rather than from HitBoy.


## Fabric mods on the real Fabric Loader

Mixed mode (above) runs Fabric mods with HitBoy's own Fabric support. The
patcher's **Also install: Fabric** option instead installs the **real Fabric
Loader** (newest stable build from Fabric's servers) and adds a
**"Minecraft `<version>`/HitBoy's Mod Loader + Fabric"** installation that
runs Fabric Loader with HitBoy attached. Use it when a Fabric mod needs
something HitBoy's own Fabric support does not provide.

- Tested on 26.3 with Fabric Loader 0.19.5, Fabric API, Lithium, and a HitBoy
  mod, in a world.
- On **1.21.11**, Fabric Loader renames Minecraft's classes while the game runs,
  so HitBoy's in-game hooks (title screen, tick, HUD, keys) do not attach there
  yet; HitBoy mods load but receive no in-game events. Use mixed mode on 1.21.11.
- The window title shows "HitBoy's Mod Loader + Fabric" on every version.

## Fabric mods inside a NeoForge game (26.x)

On Minecraft 26.x, the "HitBoy's Mod Loader + NeoForge" installation also runs
**Fabric mods** from the same mods folder, so one game can hold HitBoy, NeoForge,
and Fabric mods (plus Forge mods ported with port.exe). HitBoy converts the Fabric
mods into one NeoForge mod JAR (in `.hitboys-modloader/cache/fabric-neoforge`):
each gets a NeoForge mod entry, its Mixins, and its access widener as an access
transformer, and HitBoy runs its Fabric entrypoints.

- Tested on 26.3 with Lithium next to Jade, AppleSkin, a ported Forge mod (Mouse
  Tweaks), and a HitBoy mod, in a world.
- NeoForge changes some of the Minecraft code Fabric mods hook into. A hook that
  no longer finds its target is skipped (and logged) instead of stopping the game.
- **Fabric API does not run inside NeoForge yet**: several of its hooks clash with
  NeoForge's own changes. HitBoy skips it, and the mods that need it, with a
  message such as `Skipping Sodium: it needs "fabric-block-getter-api-v2", part of
  Fabric API, which does not run inside NeoForge yet`. Use mixed mode for those.

## Porting Forge mods to NeoForge (port.exe)

port.exe converts a Forge mod JAR into a NeoForge mod JAR for Minecraft 26.3:
it renames the Forge APIs that have a NeoForge equivalent, adapts the ones that
work differently (Forge's event bus and cancelling listeners, config screens,
the environment check) through a small bridge class it adds to the mod, and
rewrites `mods.toml` as `neoforge.mods.toml`.

Then it checks **every** NeoForge and Minecraft class, method, and field the mod
uses against NeoForge 26.3's real API. If anything is missing, it writes no JAR
and lists exactly what is missing instead, so it never produces a mod that would
crash. The ported JAR runs on NeoForge by itself (it does not need HitBoy).

- Ported and ran on NeoForge 26.3: Mouse Tweaks 2.31 (Forge).
- Refused, with 23 listed reasons: Xaero's Minimap (it uses Forge APIs with no
  NeoForge counterpart in the porter yet). Large Forge mods usually need their
  author's NeoForge build.

## Forge mods

The patcher's **Also install: Forge** option works exactly like the NeoForge one:
it runs Forge's official installer (the recommended build for your Minecraft
version, or the latest when none is recommended yet), then adds a
**"Minecraft `<version>`/HitBoy's Mod Loader + Forge"** installation. Put Forge
mods and HitBoy mods together in `.minecraft\mods`.

Tested on Minecraft 26.3 with Forge 66.0.5, Xaero's Minimap 26.5.3, and Mouse
Tweaks 2.31, in a world.

## Tested mods

Each set was loaded into a single-player world, and the player joined with no
Mixin or entrypoint errors.

**Minecraft 26.3:**

| Mods | Versions |
|---|---|
| Fabric API | 0.161.0+26.3 (51 modules) |
| Sodium + Iris | Sodium 0.9.2, Iris 1.11.6 |
| Lithium | 0.26.1 |

**Minecraft 1.21.11:**

| Mods | Versions |
|---|---|
| Fabric API | 0.141.6+1.21.11 (all 49 modules) |
| Sodium + Iris | Sodium 0.8.7, Iris 1.10.7 |
| Lithium | 0.21.4 |
| FerriteCore | 8.2.0 |
| Krypton | 0.2.10 |
| ImmediatelyFast | 1.14.3 |

Mods must still be compatible **with each other**, exactly as on Fabric. For
example, on 1.21.11 Iris 1.10.7 needs **Sodium 0.8.7**, and on 26.3 Iris 1.11.6 pairs
with **Sodium 0.9.2**. Newer Sodium builds (0.8.13 and
later) changed a method Iris patches, so that pairing fails on real Fabric too.
Use the versions a mod's own page lists.

## How it works

1. **Discovery.** HitBoy reads each JAR's `fabric.mod.json`, unpacks bundled
   `META-INF/jars` (Fabric API ships 49 modules this way), keeps the newest copy
   of each mod id, and skips mods whose required dependencies are missing, with a
   message naming the missing mod.
2. **Remapping (1.21.11).** On 26.x mods already use Minecraft's real names
   and run unchanged. On 1.21.11, Fabric mods are compiled against Fabric's *intermediary*
   names (`class_310`, `method_1548`, ...). HitBoy runs Minecraft with its real
   names, so each mod is rewritten once and cached in
   `.hitboys-modloader\cache\fabric\<version>\`: class, method, field, and
   record-component references, Mixin refmaps, and Mixin annotation strings
   (targets, `@Inject(method = ...)`, `@At(target = ...)`, `@Accessor`,
   `@Invoker`). The cache is rebuilt automatically when a mod changes.
3. **Mixins.** HitBoy uses Fabric's own Mixin fork plus MixinExtras (both
   bundled), so mods get the same Mixin features they have on Fabric.
4. **Access wideners and class tweakers** are applied as classes load.
5. **Fabric Loader API.** `FabricLoader`, `ModContainer`, `ModMetadata`,
   `Version`, custom values, `MappingResolver`, and entrypoint containers are
   provided.
6. **Entrypoints** run where Fabric runs them: `preLaunch` before Minecraft
   starts, and `main` and `client` in the Minecraft client constructor once the
   session is set. `pkg.Class`, `pkg.Class::field`, and `pkg.Class::method`
   entrypoint forms are supported.

## Checking a mod

```powershell
port.exe <mod.jar>
```

For a Fabric mod, `port.exe` reports whether it runs in mixed mode. It does not
need porting. It lists what the mod needs (for example Fabric API modules), so
you know which other JARs to install.

## Known limits

- One game runs HitBoy mods plus **one** other loader's mods: Fabric (mixed mode),
  NeoForge, or Forge. NeoForge and Forge are two separate loaders that each
  replace Minecraft's startup, so they cannot share a game. Running Fabric mods
  inside a NeoForge or Forge game is not supported yet.
- A shared mods folder is safe: mods for a loader that is not running are
  skipped with a log line such as
  `Skipping FORGE mod X.jar: it cannot run in a neoforge game`, and the game
  still starts. Tested on 26.3 with Fabric API, AppleSkin (NeoForge), Mouse
  Tweaks (Forge), and a HitBoy mod in one folder, in both a Forge and a
  NeoForge game.
- On NeoForge and Forge, HitBoy's own Mixin mods are skipped: the base loader owns Mixin there.
- On NeoForge and Forge, HitBoy's button is labelled **HitBoy Mods** and sits in the
  top-left corner of the title screen, because the loader has its own Mods button next to Realms.
- On 26.x, HitBoy's own Mixin mods built for 1.21.11 (such as Meteor Client HitBoy
  Edition) are skipped; Fabric mods built for 26.x run normally.
- Fabric mods' server-only entrypoints are not called; HitBoy is a client loader.
- Anything a Fabric mod does through Fabric Loader *internals*
  (`net.fabricmc.loader.impl`) rather than its public API is not provided.
  Such mods will report a missing class in the log.
