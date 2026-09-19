# HitBoy Official Patch profile

The **1.21.11-HitBoy** profile is a local profile for the official Minecraft
Launcher. It inherits the official Minecraft 1.21.11 release instead of
copying or modifying its client JAR, libraries, assets, worlds, or account
data. Like Fabric, it adds a loader library to an inherited profile rather
than patching Mojang's client JAR on disk. At startup the HitBoy patch JAR adds
HitBoy's agent and creates a dedicated HitBoy home with native mods at:

```text
%APPDATA%\.minecraft\.hitboys-modloader\
%APPDATA%\.minecraft\.hitboys-modloader\native_mods\
```

## Install

1. Start Minecraft 1.21.11 once in the official Minecraft Launcher so the
   base version is installed.
2. Open HitBoy's Mod Loader and choose **Settings** >
   **Install Official Launcher Profile**, or run:

   ```powershell
   java -jar .\dist\hitboys-mod-loader-patch-1.0.0-SNAPSHOT.jar `
     --install-official-patch
   ```

3. Restart the official Minecraft Launcher if it is already open, select
   **1.21.11-HitBoy**, and launch it.

The official Launcher remains responsible for Microsoft sign-in and passes its
authenticated session to Minecraft. This means the profile can join normal
online servers when the server accepts its client-side mods.

The installed patch JAR is:

```text
%APPDATA%\.minecraft\libraries\com\hitboy\hitboys-mod-loader-patch\1.0.0-SNAPSHOT\hitboys-mod-loader-patch-1.0.0-SNAPSHOT.jar
```

## Limits

- HitBoy does not bypass Microsoft authentication, server authentication, or
  server-side mod requirements.
- Servers may reject any modified client, and anti-cheat/server rules still
  apply.
- The profile currently targets Minecraft 1.21.11 and requires Java 21+.
- The standalone launcher only offers versions with verified native hooks:
  Minecraft 1.21.11 and 1.20.1. Other versions must be mapped and tested
  before being added.
- Removing `versions\1.21.11-HitBoy\` and
  `libraries\com\hitboy\hitboys-mod-loader-patch\` from the official Minecraft
  directory removes the profile. Do not delete any other files from the
  official game directory.
