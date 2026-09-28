# HitBoy's Mixed Compatible Mod Loader

HitBoy ships two loaders:

| Loader | Runs | Get it |
|---|---|---|
| **HitBoy's Mod Loader** | HitBoy mods only | `hitboys-mod-loader.exe`, or the patcher's default install |
| **HitBoy's Mixed Compatible Mod Loader** | HitBoy mods **plus** Fabric, NeoForge, and Forge mods | `hitboy-mixed-mod-compatibility.exe`, or the patcher's **Install HitBoy's Mixed Compatible Mod Loader instead** option |

HitBoy's Mod Loader skips mods made for other loaders with a message such as
`Skipping FABRIC mod lithium.jar: HitBoy's Mod Loader runs HitBoy mods only. Use
HitBoy's Mixed Compatible Mod Loader for Fabric, NeoForge, and Forge mods.`

The two are separate installations (`<version>-HitBoy` and
`<version>-HitBoy-Mixed`), so both can be installed at once.

## What the mixed loader runs

| Minecraft | HitBoy | Fabric | NeoForge | Forge |
|---|---|---|---|---|
| **26.x** | Yes | Yes | Yes | Yes (ported when the game starts) |
| **1.21.11** | Yes | Yes | Yes | Yes (ported when the game starts) |

The mixed loader picks its engine each time the game starts:

- **No NeoForge or Forge mods in the folder:** HitBoy runs the game itself.
  Fabric mods get full support, including **Fabric API, Sodium, and Iris**.
- **NeoForge or Forge mods in the folder:** NeoForge runs underneath so their mods
  can run. HitBoy converts Fabric mods into NeoForge mods and ports Forge mods to
  NeoForge (with port.exe's converter). On 1.21.11, Fabric mods are first remapped
  from Fabric's intermediary names to the Mojang names NeoForge uses. Fabric API
  cannot run inside NeoForge yet, so in this case it is skipped, and so are the
  mods that need it, with a message.

Either way, the game is HitBoy's: the window title reads "HitBoy's Mixed
Compatible Mod Loader | Minecraft 26.3", the title screen reads "HitBoy's Mixed
Compatible Mod Loader (N mods loaded)", and the client brand is "hitboy". When
NeoForge runs underneath, its loading window, update notice, and launcher
installation are removed, and HitBoy's **Mods** button takes the place of
NeoForge's and lists every mod by loader.

Mods go in `%APPDATA%\.minecraft\mods` (official launcher) or
`%USERPROFILE%\.hitboys-modloader\native_mods` (standalone).

## Tested

**26.3, NeoForge underneath**, in one world: Fullbright (HitBoy), Lithium (Fabric),
Jade and AppleSkin (NeoForge), and Mouse Tweaks (Forge, ported at start), through
both the official-launcher installation and the standalone launcher.

**26.3, HitBoy engine**, in a world: Fabric API 0.161.0 (51 modules), Sodium
0.9.2, Iris 1.11.6, Lithium 0.26.1, and Fullbright.

**1.21.11, NeoForge underneath**, in a world: Fullbright (HitBoy), Lithium 0.21.4
(Fabric), Jade and AppleSkin (NeoForge), and Mouse Tweaks 2.30 (Forge, ported at
start). **1.21.11, HitBoy engine**, in a world: Fabric API 0.141.6, Lithium, and
Fullbright.

**1.21.11** (earlier releases), in a world: Fabric API 0.141.6 (49 modules), Sodium 0.8.7 + Iris
1.10.7, Lithium 0.21.4, FerriteCore 8.2.0, Krypton 0.2.10, ImmediatelyFast 1.14.3.

Mods must still be compatible **with each other**, exactly as on their own
loader: on 1.21.11 Iris 1.10.7 needs Sodium 0.8.7, and on 26.3 Iris 1.11.6 pairs
with Sodium 0.9.2. Use the versions a mod's own page lists.

## How Fabric mods run

1. **Discovery.** HitBoy reads each JAR's `fabric.mod.json`, unpacks bundled
   `META-INF/jars` (Fabric API ships its modules this way), keeps the newest copy
   of each mod id, and skips mods whose required dependencies are missing, with a
   message naming the missing mod.
2. **Names.** On 26.x mods already use Minecraft's real names and run unchanged.
   On 1.21.11 they use Fabric's *intermediary* names, so each mod is rewritten
   once and cached in `.hitboys-modloader\cache\fabric\<version>\` (references,
   Mixin refmaps, and Mixin annotation strings).
3. **Mixins** use Fabric's own Mixin fork plus MixinExtras (both bundled).
4. **Access wideners and class tweakers** are applied as classes load.
5. **Fabric Loader API** (`FabricLoader`, `ModContainer`, `ModMetadata`, `Version`,
   custom values, `MappingResolver`, entrypoint containers) is provided.
6. **Entrypoints** run where Fabric runs them (`preLaunch`, then `main` and
   `client` once the session is set), in `pkg.Class`, `::field`, and `::method` forms.

With NeoForge underneath, HitBoy instead writes the Fabric mods into one NeoForge
mod JAR (`.hitboys-modloader\cache\fabric-neoforge`), each with its own mod entry,
Mixins, and access widener as an access transformer. A Fabric hook whose target
NeoForge has changed is skipped and logged instead of stopping the game.

## Porting Forge mods (port.exe)

port.exe converts a Forge mod JAR into a NeoForge mod JAR for Minecraft 26.3 or
1.21.11 (the
mixed loader does the same automatically). It renames the Forge APIs that have a
NeoForge equivalent, adapts the ones that work differently (Forge's event bus
and cancelling listeners, config screens, the environment check) through a small
bridge class added to the mod, and rewrites `mods.toml` as `neoforge.mods.toml`.
Then it checks **every** NeoForge and Minecraft class, method, and field the mod
uses against NeoForge 26.3's real API; if anything is missing it writes no JAR
and lists what is missing.

- Ported and ran: Mouse Tweaks 2.31.
- Refused, with 23 listed reasons: Xaero's Minimap. Large Forge mods usually need
  their author's NeoForge build.

## Checking a mod

```powershell
port.exe <mod.jar>
```

It says how a mod runs (or ports a Forge mod) and lists what it needs, such as
Fabric API modules.

## Known limits

- NeoForge and Forge mods need Minecraft 26.x or 1.21.11.
- Forge creates mods once Minecraft is running, NeoForge earlier. A ported Forge
  mod that needs Minecraft in its constructor is created at NeoForge's client
  setup instead (logged as "creating it at client setup instead").
- Fabric API runs only when there are no NeoForge or Forge mods in the game.
- Forge mods run only if the converter can port them.
- With NeoForge underneath, HitBoy's own Mixin mods are skipped (NeoForge owns
  Mixin there). On 26.x, HitBoy Mixin mods built for 1.21.11 (such as Meteor
  Client HitBoy Edition) are skipped either way.
- Fabric mods' server-only entrypoints are not called; HitBoy is a client loader.
- Fabric Loader *internals* (`net.fabricmc.loader.impl`) are not provided; mods
  that use them report a missing class in the log.
- NeoForge 26.3 is still in beta. On the test PC it sometimes crashed natively
  while loading, with or without HitBoy.
