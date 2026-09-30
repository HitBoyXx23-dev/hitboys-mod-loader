# HitBoy's Mod Loader

## License

Original HitBoy code may be viewed, downloaded, compiled, installed, and used for personal, non-commercial purposes under `LICENSE`. Modification, redistribution, sublicensing, sale, and commercial use require written permission. Third-party and Meteor Client-derived files remain under their existing licenses, including the GNU GPL v3 where applicable.

## Downloads

- [HitBoy's Mod Loader](https://github.com/HitBoyXx23-dev/hitboys-mod-loader/releases/latest/download/hitboys-mod-loader.exe): standalone offline launcher for **HitBoy mods**
- [HitBoy's Mixed Compatible Mod Loader](https://github.com/HitBoyXx23-dev/hitboys-mod-loader/releases/latest/download/hitboy-mixed-mod-compatibility.exe): standalone offline launcher for HitBoy mods **plus Fabric, NeoForge, and Forge mods**
- [HitBoy Profile Patcher](https://github.com/HitBoyXx23-dev/hitboys-mod-loader/releases/latest/download/hitboy-patcher.exe): adds either loader to the official Minecraft Launcher, like the Fabric installer
- [HitBoy Mod Porter](https://github.com/HitBoyXx23-dev/hitboys-mod-loader/releases/latest/download/port.exe): ports Forge mods to NeoForge (26.3), checking every API it uses
- [HitBoy Mod Loader API](https://github.com/HitBoyXx23-dev/hitboys-mod-loader/releases/latest/download/hitboys-mod-loader-api.jar) (for mod developers)
- [Meteor Client HitBoy Edition for Minecraft 1.21.11](https://github.com/HitBoyXx23-dev/hitboys-mod-loader/releases/latest/download/meteor-client-hitboy-edition-1.21.11-86.jar)

Running a server? The server plugin loaders now live in their own repository:
[HitBoyXx23-dev/hitboys-plugin-loader](https://github.com/HitBoyXx23-dev/hitboys-plugin-loader).

HitBoy's Mod Loader is a client-side native Java mod loader for Minecraft. It
starts Minecraft with a dedicated patch JAR, loads native mod JARs, and
provides a small stable API in `com.hitboy.loader`. The title screen shows
`Minecraft <version>/HitBoy's Mod Loader` whenever HitBoy is running, even with
no mods installed. The game window's title bar starts with
**HitBoy's Mod Loader** (or **HitBoy's Mixed Compatible Mod Loader**).

## Two loaders

| Loader | Runs |
|---|---|
| **HitBoy's Mod Loader** | HitBoy mods only. Mods made for other loaders are skipped with a message. |
| **HitBoy's Mixed Compatible Mod Loader** | HitBoy mods plus mods made for Fabric, NeoForge, and Forge, together in one game (see below). |

## Ways to play

| Path | Use | Account |
|---|---|---|
| `hitboys-mod-loader.exe` | Standalone HitBoy's Mod Loader with its own game folder. | Offline username only. |
| `hitboy-mixed-mod-compatibility.exe` | Standalone HitBoy's Mixed Compatible Mod Loader (same game folder). | Offline username only. |
| `hitboy-patcher.exe` | Installs "Minecraft `<version>`/HitBoy's Mod Loader" (or, with the mixed option, "Minecraft `<version>`/HitBoy's Mixed Compatible Mod Loader") into `.minecraft`. | Whatever account the official Minecraft Launcher is signed in with. |

The standalone launchers do not offer Microsoft sign-in or online play. To play
through the official Minecraft Launcher, install a profile with the patcher.
HitBoy does not bypass Microsoft authentication, server rules, server-side mod
requirements, or anti-cheat.

Like Fabric, the patcher does not edit Mojang's client JAR. It creates an
inherited `<version>-HitBoy` version profile, an empty placeholder JAR, a
`launcher_profiles.json` entry, and adds
`hitboys-mod-loader-patch-1.0.0-SNAPSHOT.jar` as a local library. See
[OFFICIAL_PATCH.md](OFFICIAL_PATCH.md).

## Fabric, NeoForge, and Forge mods (HitBoy's Mixed Compatible Mod Loader)

**HitBoy's Mixed Compatible Mod Loader** runs mods made for other loaders next to
HitBoy mods:

- **Minecraft 26.x:** HitBoy, Fabric, NeoForge, and Forge mods in one game. With
  no NeoForge or Forge mods, HitBoy runs the game itself and Fabric mods get full
  support, including Fabric API, Sodium, and Iris. With NeoForge or Forge mods,
  NeoForge runs underneath (the game still shows HitBoy's Mixed Compatible Mod
  Loader), Fabric mods are converted, and Forge mods are ported when the game
  starts. If any original JAR cannot be prepared, launch stops with the exact
  reason instead of silently running only part of the requested mod set. Fabric
  API cannot currently be combined with NeoForge or Forge mods.
- **Minecraft 1.21.11:** the same: HitBoy, Fabric, NeoForge, and Forge mods, with
  Fabric API, Sodium, and Iris when there are no NeoForge or Forge mods.

port.exe converts simple Forge mods to NeoForge by hand, checking every API they
use. See [MIXED_COMPATIBILITY.md](MIXED_COMPATIBILITY.md).

## Quick start

1. Start `hitboys-mod-loader.exe`. It uses the newest Java on the PC and
   downloads a private Java 25 runtime the first time if none is new enough.
2. The standalone game folder is `%USERPROFILE%\.hitboys-modloader`.
3. Put HitBoy mod JARs in `%USERPROFILE%\.hitboys-modloader\native_mods`.
   Nothing is installed automatically; optional mods are on the releases page.

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
Set-Location launcher
mvn --batch-mode --no-transfer-progress clean package
Set-Location ..\hitboy-rust-launcher
cargo test --release
cargo build --release
Copy-Item .\target\release\hitboys-mod-loader.exe, .\target\release\hitboy-patcher.exe, .\target\release\port.exe ..\dist\ -Force
Copy-Item .\target\release\hitboys-mod-loader.exe ..\dist\hitboy-mixed-mod-compatibility.exe -Force
```

The release files end up in `dist\`. Every exe embeds
`hitboys-mod-loader-patch-1.0.0-SNAPSHOT.jar`, which is both the launcher's
Java entry point and the official Launcher's Java-agent/bootstrap library.
`hitboy-mixed-mod-compatibility.exe` is the main launcher under another name;
it switches to mixed mode from its file name.

## Supported scope and limits

- In-game hooks (tick, HUD, keyboard, title screen) are verified for
  Minecraft **1.21.11** (default), **1.20.1**, and **1.16.5**.
- **26.3** and **26.2** start with HitBoy's bootstrap, branding, and mod loading,
  but the in-game hooks are not ported to 26.x yet, so mods that rely on
  tick/HUD/key events will not receive them there.
- Minecraft **1.8.9** and **1.12.2** are not supported: Mojang does not publish
  official mappings before 1.14.4.
- Other Minecraft releases are blocked until they are mapped and tested.
- HitBoy's Mod Loader runs HitBoy mods only. Fabric, NeoForge, and Forge mods need
  HitBoy's Mixed Compatible Mod Loader ([MIXED_COMPATIBILITY.md](MIXED_COMPATIBILITY.md)).
- Native mods run in the client JVM. Install only trusted JARs.
