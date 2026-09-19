# HitBoy's Mod Loader

## License

Original HitBoy code may be viewed, downloaded, compiled, installed, and used for personal, non-commercial purposes under `LICENSE`. Modification, redistribution, sublicensing, sale, and commercial use require written permission. Third-party and Meteor Client-derived files remain under their existing licenses, including the GNU GPL v3 where applicable.

## Downloads

- [HitBoy Mod Loader](https://github.com/HitBoyXx23-dev/hitboys-mod-loader/releases/latest/download/hitboys-mod-loader.exe)
- [HitBoy Mixed Mod Loader](https://github.com/HitBoyXx23-dev/hitboys-mod-loader/releases/latest/download/hitboy-mixed-mod-compatibility.exe)
- [HitBoy Profile Patcher](https://github.com/HitBoyXx23-dev/hitboys-mod-loader/releases/latest/download/hitboy-patcher.exe)
- [HitBoy Mod Porter](https://github.com/HitBoyXx23-dev/hitboys-mod-loader/releases/latest/download/port.exe)
- [HitBoy Mod Loader API](https://github.com/HitBoyXx23-dev/hitboys-mod-loader/releases/latest/download/hitboys-mod-loader-api.jar)

HitBoy's Mod Loader is a client-side native Java mod loader for Minecraft
1.21.11. It starts Minecraft with a dedicated patch JAR, loads native mod JARs
from `native_mods`, and provides a small stable API in `com.hitboy.loader`.

## Supported launch paths

| Path | Use | Account support |
|---|---|---|
| `dist\HitBoysModLoader.exe` | Standalone HitBoy launcher, with its own game directory. | Offline username only. |
| `1.21.11-HitBoy` official profile | Patches the official Minecraft Launcher launch at process start. | Uses the signed-in Microsoft account from the official Launcher. |

The official profile is the appropriate route for legitimate online play. It
does not bypass Microsoft authentication, server rules, server-side mod
requirements, or anti-cheat.

Like Fabric, the official profile does not edit Mojang's client JAR. It creates
an inherited `1.21.11-HitBoy` version profile and adds
`hitboys-mod-loader-patch-1.0.0-SNAPSHOT.jar` as a local library. The patch JAR
starts HitBoy's bootstrap and Java agent before delegating to Minecraft's
official main class.

## Quick start

1. Use Java 21 or newer for Minecraft 1.21.11.
2. Start `dist\HitBoysModLoader.exe`.
3. The standalone profile is stored in
   `%USERPROFILE%\.hitboys-modloader`.
4. Put native mod JARs in
   `%USERPROFILE%\.hitboys-modloader\native_mods`.

For the official Launcher profile, follow [OFFICIAL_PATCH.md](OFFICIAL_PATCH.md).
It creates `%APPDATA%\.minecraft\.hitboys-modloader\` for HitBoy-owned data
and native mods while leaving the official client JAR unchanged.

## Native mod contract

Each native mod is a JAR that includes:

- `hitboy.json`
- a public no-argument entry class
- `@com.hitboy.loader.NativeMod` on that class
- matching `name` and `version` values in both the annotation and descriptor

See [native_mods/MOD_API.md](native_mods/MOD_API.md) for the full API and
[native_mods/BUNDLED_MODS.md](native_mods/BUNDLED_MODS.md) for included mods.

## Build a release

```powershell
mvn --batch-mode --no-transfer-progress clean install
.\native_mods\build-fullbright.ps1
Set-Location launcher
mvn --batch-mode --no-transfer-progress clean package
Set-Location ..\hitboy-rust-launcher
cargo test
cargo build --release
Copy-Item .\target\release\hitboys-mod-loader.exe ..\dist\HitBoysModLoader.exe -Force
```

The release directory is `dist\`. Distribute these two files together:

- `HitBoysModLoader.exe`
- `hitboys-mod-loader-patch-1.0.0-SNAPSHOT.jar`

The patch JAR is both the Rust launcher's Java entry point and the official
Launcher's Java-agent/bootstrap library.

## Supported scope and limits

- Minecraft **1.21.11** is the default target; **1.20.1** and **1.16.5** are
  also selectable, each with dedicated, mapping-verified hooks (see
  `native_mods/BUNDLED_MODS.md`).
- Minecraft **1.8.9** and **1.12.2** are not yet supported: Mojang does not
  publish official mappings before 1.14.4, and no community MCP/SRG mapping
  source could be reliably fetched to verify hooks for them. Rather than
  guess obfuscated bytecode targets (which can crash or corrupt a client),
  this stays on the roadmap until a real mapping source is available.
- Other Minecraft releases are intentionally blocked. A loader that blindly
  claims every release would run unverified bytecode patches and can crash or
  corrupt a client; each version must be explicitly mapped and tested first.
- Fabric, Forge, and NeoForge JARs cannot be loaded directly. Port source code
  to the HitBoy API instead.
- Native mods run in the client JVM. Install only trusted JARs.

## HitBoy's Plugin Loader (server-side)

A separate, server-side plugin ecosystem lives in `plugin_loader/` --
"HitBoy's Plugin Loader". It runs as a normal plugin on Spigot/Paper/Purpur
and hosts its own `plugin.json`-based plugins through a small, stable
`HitBoyPlugin` API, so plugin authors don't depend on raw Bukkit types. See
`plugin_loader/README.md` for the full contract, an example plugin, and its
current roadmap/limitations. This is a first-phase seed toward
Spigot/Paper/Purpur-style parity, not a complete implementation.
