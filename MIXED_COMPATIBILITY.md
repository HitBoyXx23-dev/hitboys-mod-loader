# Mixed compatibility: Fabric mods on HitBoy

HitBoy's mixed-compatibility mode runs **Fabric mods next to HitBoy mods**, with
no Fabric Loader installed. Put the Fabric mod JARs (and Fabric API, if a mod
needs it) in the same mods folder as your HitBoy mods.

| | Status |
|---|---|
| Fabric mods on Minecraft **1.21.11** | Supported |
| Fabric API | Supported: the real Fabric API JAR runs as ordinary Fabric mods |
| Fabric mods on Minecraft 26.x | Not yet (they are skipped with a message) |
| Forge / NeoForge mods | Not yet (they are skipped, never crash the game) |

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

## Tested mods (Minecraft 1.21.11)

Each set was loaded into a single-player world, and the player joined with no
Mixin or entrypoint errors:

| Mods | Versions |
|---|---|
| Fabric API | 0.141.6+1.21.11 (all 49 modules) |
| Sodium + Iris | Sodium 0.8.7, Iris 1.10.7 |
| Lithium | 0.21.4 |
| FerriteCore | 8.2.0 |
| Krypton | 0.2.10 |
| ImmediatelyFast | 1.14.3 |

Mods must still be compatible **with each other**, exactly as on Fabric. For
example, Iris 1.10.7 needs **Sodium 0.8.7**. Newer Sodium builds (0.8.13 and
later) changed a method Iris patches, so that pairing fails on real Fabric too.
Use the versions a mod's own page lists.

## How it works

1. **Discovery.** HitBoy reads each JAR's `fabric.mod.json`, unpacks bundled
   `META-INF/jars` (Fabric API ships 49 modules this way), keeps the newest copy
   of each mod id, and skips mods whose required dependencies are missing, with a
   message naming the missing mod.
2. **Remapping.** Fabric mods are compiled against Fabric's *intermediary*
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

- Minecraft 1.21.11 only for Fabric mods so far. On 26.x Fabric mods are skipped.
- Forge and NeoForge mods are not run yet.
- Fabric mods' server-only entrypoints are not called; HitBoy is a client loader.
- Anything a Fabric mod does through Fabric Loader *internals*
  (`net.fabricmc.loader.impl`) rather than its public API is not provided.
  Such mods will report a missing class in the log.
