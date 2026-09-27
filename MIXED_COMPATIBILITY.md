# Mixed compatibility: Fabric and NeoForge mods on HitBoy

HitBoy's mixed-compatibility mode runs **Fabric mods next to HitBoy mods**, with
no Fabric Loader installed. Put the Fabric mod JARs (and Fabric API, if a mod
needs it) in the same mods folder as your HitBoy mods.

| | Status |
|---|---|
| Fabric mods on Minecraft **1.21.11** | Supported (remapped from Fabric's intermediary names) |
| Fabric mods on Minecraft **26.3 / 26.2** | Supported (26.x uses Minecraft's real names, so no remapping) |
| Fabric API | Supported: the real Fabric API JAR runs as ordinary Fabric mods |
| NeoForge mods | Supported through the patcher's NeoForge option (runs the real NeoForge with HitBoy) |
| Fabric + NeoForge in one game | Not yet |
| Forge mods | Not yet (skipped, never crash the game) |

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

- Fabric mods and NeoForge mods do not run in the same game yet (use one or the
  other alongside HitBoy mods). Forge mods are not run.
- On NeoForge, HitBoy's own Mixin mods are skipped: NeoForge owns Mixin there.
- On 26.x, HitBoy's own Mixin mods built for 1.21.11 (such as Meteor Client HitBoy
  Edition) are skipped; Fabric mods built for 26.x run normally.
- Fabric mods' server-only entrypoints are not called; HitBoy is a client loader.
- Anything a Fabric mod does through Fabric Loader *internals*
  (`net.fabricmc.loader.impl`) rather than its public API is not provided.
  Such mods will report a missing class in the log.
