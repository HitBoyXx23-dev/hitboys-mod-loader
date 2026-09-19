# Production operations

## Release checklist

1. Build the loader, native bundled mods, Java launcher, and Rust executable
   using the commands in [README.md](README.md).
2. Confirm `dist\HitBoysModLoader.exe` and
   `dist\hitboys-mod-loader-patch-1.0.0-SNAPSHOT.jar` are shipped together.
3. Run the standalone launcher with `--download-only` for Minecraft 1.21.11.
4. Launch a real 1.21.11 client and confirm the log reports the loaded native
   mods and expected agent hooks.
5. Install the `1.21.11-HitBoy` profile in a disposable official Launcher
   directory before publishing an update.

## Upgrades

The standalone launcher refreshes only its managed bundled JARs:

- `fullbright-1.0.0.jar`
- `hitboy-clock-1.0.0.jar`
- `hitboy-status-1.0.0.jar`
- `hitboy-flight-menu-1.0.0.jar`

It does not replace other mod JARs. Close Minecraft before upgrading so Java
does not lock the loader or mod files.

The official profile installer copies the HitBoy patch JAR into the official
Minecraft `libraries\com\hitboy\hitboys-mod-loader-patch\...` location and
seeds bundled mods only when the official profile's `native_mods` directory is
empty.

## Mod support policy

`hitboy.json` is the native mod descriptor. Invalid descriptors fail with
actionable errors:

- Required `name`, `version`, or `mainClass` fields are missing or empty.
- `dependsOn` is not a string array.
- Two JARs use the same mod name.
- A dependency is missing, disabled, or circular.
- The main class is not annotated with `@NativeMod`.
- Descriptor `name`/`version` differs from `@NativeMod`.

A JAR without `hitboy.json` is skipped as incompatible. This permits users to
keep unrelated JARs in the directory without stopping Minecraft, but it does
not provide Fabric, Forge, or NeoForge compatibility.

## Operational boundaries

- Do not modify Mojang client JARs or distribute modified Minecraft files.
  HitBoy patches classes only in the running JVM.
- Use the official `1.21.11-HitBoy` profile for authenticated online play.
- Do not use the standalone offline launcher for servers requiring a Microsoft
  account.
- Back up worlds before testing a new Minecraft version or third-party native
  mod.
- Treat native mod JARs as executable code and install them only from trusted
  sources.

## Troubleshooting

| Symptom | Action |
|---|---|
| `missing hitboy.json` | The JAR is not a HitBoy native mod; remove it or port its source. |
| `requires missing mod` | Install the named dependency or remove it from `dependsOn`. |
| `requires disabled mod` | Remove the matching `.disabled` file. |
| `Circular HitBoy mod dependency` | Remove the cycle from the affected `dependsOn` lists. |
| Java 21+ requirement | Set `HITBOY_JAVA` to a compatible Java executable. |
| Official profile is absent | Restart the official Launcher after installation and select `1.21.11-HitBoy`. |
