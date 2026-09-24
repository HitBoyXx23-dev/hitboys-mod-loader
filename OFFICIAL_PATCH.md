# HitBoy Profile Patcher

`hitboy-patcher.exe` installs HitBoy into your normal `.minecraft` folder the
same way the Fabric installer does. It adds a `<version>-HitBoy` installation
to the official Minecraft Launcher that inherits the vanilla release instead of
copying or modifying Mojang's client JAR, libraries, assets, worlds, or account
data.

Supported versions: **26.3**, **26.2**, **1.21.11**, **1.20.1**, **1.16.5**.

## Install

1. Run the official Minecraft Launcher at least once so `.minecraft` and
   `launcher_profiles.json` exist.
2. Open `hitboy-patcher.exe`, choose a Minecraft version, check the install
   location (default `%APPDATA%\.minecraft`), and click **Install**. Or run:

   ```powershell
   java -jar .\dist\hitboys-mod-loader-patch-1.0.0-SNAPSHOT.jar `
     --install-official-patch --version 26.3
   ```

   Add `--official-game-dir <dir>` to target a different `.minecraft` folder.

3. Restart the official Minecraft Launcher, select the **Minecraft &lt;version&gt;/HitBoy's Mod Loader**
   installation, and play. The launcher downloads the vanilla base version
   automatically if it is not installed yet.

## What gets written

Like Fabric, the patcher only adds files:

```text
.minecraft\versions\<version>-HitBoy\<version>-HitBoy.json   inherited version profile
.minecraft\versions\<version>-HitBoy\<version>-HitBoy.jar    empty placeholder JAR
.minecraft\libraries\com\hitboy\hitboys-mod-loader-patch\1.0.0-SNAPSHOT\hitboys-mod-loader-patch-1.0.0-SNAPSHOT.jar
.minecraft\launcher_profiles.json                              "Minecraft <version>/HitBoy's Mod Loader" entry
.minecraft\mods\                                               HitBoy mods
.minecraft\.hitboys-modloader\                                 HitBoy data
```

The version profile does not override the Java runtime, so the official
Launcher uses the one the vanilla version asks for (Java 21 for 1.21.x,
Java 25 for 26.x).

## Limits

- HitBoy does not bypass Microsoft authentication, server authentication, or
  server-side mod requirements. Servers may reject any modified client, and
  anti-cheat/server rules still apply.
- To uninstall, delete the **Minecraft &lt;version&gt;/HitBoy's Mod Loader** installation in the
  official Launcher, then remove `versions\<version>-HitBoy\` and
  `libraries\com\hitboy\hitboys-mod-loader-patch\`. Do not delete any other
  files from the game directory.
