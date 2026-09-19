package com.hitboy.loader.compat;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public final class SourceProjectConverter {
    public Result convert(Path source, Path outputRoot) throws IOException {
        source = source.toAbsolutePath().normalize();
        Metadata metadata = detect(source);
        Path output = outputRoot.toAbsolutePath().normalize().resolve(metadata.name + " HitBoy Edition");
        if (Files.exists(output)) throw new IOException("Output already exists: " + output);
        copy(source, output);
        List<String> manual = new ArrayList<>();
        int changed = rewrite(output, manual);
        writeDescriptor(output, metadata);
        List<String> unique = List.copyOf(new LinkedHashSet<>(manual));
        Files.write(output.resolve("hitboy-port-report.txt"), unique, StandardCharsets.UTF_8);
        return new Result(output, metadata.loader, changed, unique);
    }

    private Metadata detect(Path root) throws IOException {
        Path fabric = root.resolve("src/main/resources/fabric.mod.json");
        if (!Files.isRegularFile(fabric)) fabric = root.resolve("fabric.mod.json");
        if (Files.isRegularFile(fabric)) {
            JsonObject json = JsonParser.parseString(Files.readString(fabric)).getAsJsonObject();
            String id = json.get("id").getAsString();
            String name = json.has("name") ? json.get("name").getAsString() : id;
            String version = json.has("version") ? json.get("version").getAsString() : "1.0.0";
            return new Metadata(SourceLoader.FABRIC, id, name, version, findEntry(root, "ModInitializer"));
        }
        Path neo = root.resolve("src/main/resources/META-INF/neoforge.mods.toml");
        if (Files.isRegularFile(neo)) return toml(root, neo, SourceLoader.NEOFORGE);
        Path forge = root.resolve("src/main/resources/META-INF/mods.toml");
        if (Files.isRegularFile(forge)) return toml(root, forge, SourceLoader.FORGE);
        throw new IOException("No Fabric, Forge, or NeoForge metadata was found.");
    }

    private Metadata toml(Path root, Path file, SourceLoader loader) throws IOException {
        String text = Files.readString(file);
        String id = match(text, "(?m)^\\s*modId\\s*=\\s*[\"']([^\"']+)", root.getFileName().toString());
        String name = match(text, "(?m)^\\s*displayName\\s*=\\s*[\"']([^\"']+)", id);
        String version = match(text, "(?m)^\\s*version\\s*=\\s*[\"']([^\"']+)", "1.0.0");
        return new Metadata(loader, id, name, version, findEntry(root, "@Mod"));
    }

    private String findEntry(Path root, String marker) throws IOException {
        Path javaRoot = root.resolve("src/main/java");
        if (!Files.isDirectory(javaRoot)) javaRoot = root;
        if (!Files.isDirectory(javaRoot)) return null;
        try (Stream<Path> paths = Files.walk(javaRoot)) {
            for (Path path : paths.filter(value -> value.toString().endsWith(".java")).toList()) {
                String text = Files.readString(path);
                if (!text.contains(marker)) continue;
                String packageName = match(text, "(?m)^\\s*package\\s+([\\w.]+)", "");
                String className = match(text, "\\bclass\\s+(\\w+)", null);
                if (className != null) return packageName.isEmpty() ? className : packageName + "." + className;
            }
        }
        return null;
    }

    private void copy(Path source, Path output) throws IOException {
        try (Stream<Path> paths = Files.walk(source)) {
            for (Path path : paths.toList()) {
                Path relative = source.relativize(path);
                if (relative.getNameCount() > 0 && Set.of(".git", ".gradle", "build", "out", "run").contains(relative.getName(0).toString())) continue;
                Path target = output.resolve(relative);
                if (Files.isDirectory(path)) Files.createDirectories(target);
                else {
                    Files.createDirectories(target.getParent());
                    Files.copy(path, target, StandardCopyOption.COPY_ATTRIBUTES);
                }
            }
        }
    }

    private int rewrite(Path output, List<String> manual) throws IOException {
        Path root = output.resolve("src/main/java");
        if (!Files.isDirectory(root)) root = output;
        if (!Files.isDirectory(root)) return 0;
        int changed = 0;
        try (Stream<Path> paths = Files.walk(root)) {
            for (Path path : paths.filter(value -> value.toString().endsWith(".java")).toList()) {
                String old = Files.readString(path);
                String text = old.replace("import net.fabricmc.api.ModInitializer;", "import com.hitboy.loader.NativeMod;")
                    .replace("import net.fabricmc.api.ClientModInitializer;", "import com.hitboy.loader.NativeMod;")
                    .replace("import net.minecraftforge.fml.common.Mod;", "import com.hitboy.loader.NativeMod;")
                    .replace("import net.neoforged.fml.common.Mod;", "import com.hitboy.loader.NativeMod;")
                    .replaceAll("\\s+implements\\s+(ModInitializer|ClientModInitializer)", "");
                if (!old.equals(text)) {
                    Files.writeString(path, text, StandardCharsets.UTF_8);
                    changed++;
                }
                for (String api : List.of("net.fabricmc.fabric.api", "net.minecraftforge.", "net.neoforged.", "org.spongepowered.asm.mixin")) {
                    if (text.contains(api)) manual.add(output.relativize(path) + " requires an adapter for " + api);
                }
            }
        }
        return changed;
    }

    private void writeDescriptor(Path output, Metadata metadata) throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("id", metadata.id);
        json.addProperty("name", metadata.name + " HitBoy Edition");
        json.addProperty("version", metadata.version);
        json.addProperty("type", "client");
        json.addProperty("mainClass", metadata.entrypoint == null ? "REPLACE_WITH_ENTRYPOINT" : metadata.entrypoint);
        json.add("dependsOn", new com.google.gson.JsonArray());
        Path resources = Files.isDirectory(output.resolve("src/main/resources"))
            ? output.resolve("src/main/resources") : output;
        Path file = resources.resolve("hitboy.json");
        Files.createDirectories(file.getParent());
        Files.writeString(file, new GsonBuilder().setPrettyPrinting().create().toJson(json), StandardCharsets.UTF_8);
    }

    private String match(String text, String pattern, String fallback) {
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(pattern).matcher(text);
        return matcher.find() ? matcher.group(1) : fallback;
    }

    private record Metadata(SourceLoader loader, String id, String name, String version, String entrypoint) {}
    public record Result(Path outputDirectory, SourceLoader sourceLoader, int changedFiles, List<String> manualItems) {}
}
