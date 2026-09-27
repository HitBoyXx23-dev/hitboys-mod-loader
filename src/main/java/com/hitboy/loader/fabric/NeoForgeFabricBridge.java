package com.hitboy.loader.fabric;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Turns Fabric mod JARs into one NeoForge mod JAR, so a NeoForge game can run them (Minecraft 26.x, where
 * both use Mojang's names). The converted JAR keeps the mods' classes and resources and adds:
 * <ul>
 *   <li>a {@code META-INF/neoforge.mods.toml} listing each mod and its Mixin configs,</li>
 *   <li>their access wideners rewritten as a NeoForge access transformer,</li>
 *   <li>a generated {@code @Mod} class per mod that runs its Fabric entrypoints through HitBoy.</li>
 * </ul>
 */
final class NeoForgeFabricBridge {
    private NeoForgeFabricBridge() {
    }

    /** NeoForge mod ids allow only [a-z0-9_] and must start with a letter; Fabric ids also allow '-'. */
    static String modId(String fabricId) {
        String id = fabricId.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9_]", "_");
        if (id.isEmpty() || !Character.isLetter(id.charAt(0))) id = "f_" + id;
        return id.length() > 64 ? id.substring(0, 64) : id;
    }

    /**
     * Writes every Fabric mod into one NeoForge JAR. NeoForge loads each JAR as a Java module, and Java
     * does not allow two modules to share a package, which Fabric mods (Fabric API's modules above all)
     * often do. One JAR can hold several mods, so each Fabric mod is still its own NeoForge mod.
     */
    static void convertAll(List<FabricMod> mods, Path output) throws IOException {
        Files.createDirectories(output.getParent());
        Path temporary = output.resolveSibling(output.getFileName() + ".tmp");
        StringBuilder accessTransformer = new StringBuilder();
        java.util.Map<FabricMod, List<String>> activeConfigs = new java.util.LinkedHashMap<>();
        try (ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(temporary))) {
            Set<String> written = new HashSet<>();
            put(out, written, "META-INF/MANIFEST.MF", manifest());
            for (FabricMod mod : mods) {
                String id = modId(mod.getId());
                String entryClass = "com/hitboy/bridge/" + id + "/FabricEntry";
                put(out, written, entryClass + ".class", entryClass(id, mod.getId(), entryClass));
                try (ZipFile source = new ZipFile(mod.sourceJar.toFile())) {
                    String accessWidener = mod.accessWidener();
                    if (accessWidener != null && source.getEntry(accessWidener) != null) {
                        accessTransformer.append("# ").append(mod.getId()).append('\n')
                            .append(toAccessTransformer(read(source, source.getEntry(accessWidener))));
                    }
                    Set<String> mixinConfigs = new HashSet<>(mod.mixinConfigs());
                    List<String> active = new java.util.ArrayList<>();
                    for (String config : mod.mixinConfigs()) {
                        String library = libraryTarget(source, config);
                        if (library == null) {
                            active.add(config);
                        } else {
                            System.out.println("[HitBoy Fabric] " + mod.getName() + ": leaving out Mixin config " + config
                                + ", it changes the library class " + library.replace('/', '.') + ", which NeoForge does not let mods change.");
                        }
                    }
                    activeConfigs.put(mod, active);
                    Enumeration<? extends ZipEntry> entries = source.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry entry = entries.nextElement();
                        String name = entry.getName();
                        if (entry.isDirectory() || skipped(name)) continue;
                        byte[] data = read(source, entry);
                        put(out, written, name, mixinConfigs.contains(name) ? softenMixinConfig(data) : data);
                    }
                }
            }
            boolean hasAccessTransformer = accessTransformer.length() > 0;
            if (hasAccessTransformer) {
                put(out, written, "META-INF/accesstransformer.cfg", accessTransformer.toString().getBytes(StandardCharsets.UTF_8));
            }
            put(out, written, "META-INF/neoforge.mods.toml", modsToml(mods, activeConfigs, hasAccessTransformer).getBytes(StandardCharsets.UTF_8));
        }
        Files.move(temporary, output, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * The first Mixin target of {@code config} that is a library class rather than a Minecraft class, or null.
     * On NeoForge, libraries (DataFixerUpper, Brigadier, ...) sit on the plain classpath, where Mixins
     * cannot reach; Minecraft and mods do not, so a class the system class loader can find is a library.
     */
    static String libraryTarget(ZipFile source, String config) {
        try {
            ZipEntry entry = source.getEntry(config);
            if (entry == null) return null;
            com.google.gson.JsonObject json = com.google.gson.JsonParser.parseString(
                new String(read(source, entry), StandardCharsets.UTF_8)).getAsJsonObject();
            String prefix = json.has("package") ? json.get("package").getAsString().replace('.', '/') + "/" : "";
            for (String list : new String[] {"mixins", "client", "server"}) {
                if (!json.has(list) || !json.get(list).isJsonArray()) continue;
                for (com.google.gson.JsonElement name : json.getAsJsonArray(list)) {
                    ZipEntry mixin = source.getEntry(prefix + name.getAsString().replace('.', '/') + ".class");
                    if (mixin == null) continue;
                    for (String target : mixinTargets(read(source, mixin))) {
                        if (isLibraryClass(target)) return target;
                    }
                }
            }
        } catch (IOException | RuntimeException unreadable) {
            return null;
        }
        return null;
    }

    private static final String MINECRAFT_JAR = jarOf(ClassLoader.getSystemResource("net/minecraft/client/Minecraft.class"));

    /** On the plain classpath but not in Minecraft's own JAR (NeoForge also lists vanilla Minecraft there). */
    private static boolean isLibraryClass(String internalName) {
        java.net.URL resource = ClassLoader.getSystemResource(internalName + ".class");
        if (resource == null) return false;
        String jar = jarOf(resource);
        return jar == null || !jar.equals(MINECRAFT_JAR);
    }

    private static String jarOf(java.net.URL resource) {
        if (resource == null) return null;
        String text = resource.toString();
        int separator = text.indexOf("!/");
        return separator < 0 ? text : text.substring(0, separator);
    }

    /** Internal names from a Mixin class's {@code @Mixin(value = ..., targets = ...)}. */
    private static List<String> mixinTargets(byte[] mixinClass) {
        List<String> targets = new java.util.ArrayList<>();
        org.objectweb.asm.tree.ClassNode node = new org.objectweb.asm.tree.ClassNode();
        new org.objectweb.asm.ClassReader(mixinClass).accept(node, org.objectweb.asm.ClassReader.SKIP_CODE);
        List<org.objectweb.asm.tree.AnnotationNode> annotations = new java.util.ArrayList<>();
        if (node.invisibleAnnotations != null) annotations.addAll(node.invisibleAnnotations);
        if (node.visibleAnnotations != null) annotations.addAll(node.visibleAnnotations);
        for (org.objectweb.asm.tree.AnnotationNode annotation : annotations) {
            if (!annotation.desc.equals("Lorg/spongepowered/asm/mixin/Mixin;") || annotation.values == null) continue;
            for (int index = 0; index + 1 < annotation.values.size(); index += 2) {
                Object value = annotation.values.get(index + 1);
                if (!(value instanceof List)) continue;
                for (Object item : (List<?>) value) {
                    if (item instanceof Type) targets.add(((Type) item).getInternalName());
                    else if (item instanceof String) targets.add(((String) item).replace('.', '/'));
                }
            }
        }
        return targets;
    }

    private static boolean skipped(String name) {
        String upper = name.toUpperCase(Locale.ROOT);
        if (upper.equals("META-INF/MANIFEST.MF") || upper.startsWith("META-INF/JARS/")) return true;
        if (upper.equals("META-INF/NEOFORGE.MODS.TOML") || upper.equals("META-INF/MODS.TOML")) return true;
        if (upper.equals("META-INF/ACCESSTRANSFORMER.CFG") || upper.endsWith("MODULE-INFO.CLASS")) return true;
        return upper.startsWith("META-INF/") && (upper.endsWith(".SF") || upper.endsWith(".RSA") || upper.endsWith(".DSA") || upper.endsWith(".EC"));
    }

    private static byte[] manifest() throws IOException {
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().putValue("Automatic-Module-Name", "hitboy.fabric.mods");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        manifest.write(bytes);
        return bytes.toByteArray();
    }

    static String modsToml(List<FabricMod> mods, java.util.Map<FabricMod, List<String>> activeConfigs, boolean accessTransformer) {
        StringBuilder toml = new StringBuilder();
        toml.append("modLoader=\"javafml\"\n");
        toml.append("loaderVersion=\"[1,)\"\n");
        toml.append("license=").append(quote(mods.isEmpty() ? "All Rights Reserved" : licenseOf(mods.get(0)))).append('\n');
        for (FabricMod mod : mods) {
            toml.append("\n[[mods]]\n");
            toml.append("modId=").append(quote(modId(mod.getId()))).append('\n');
            toml.append("version=").append(quote(mod.getVersion().getFriendlyString())).append('\n');
            toml.append("displayName=").append(quote(mod.getName())).append('\n');
            toml.append("description=").append(quote(mod.getDescription() + " (Fabric mod, run by HitBoy's Mod Loader)")).append('\n');
        }
        for (FabricMod mod : mods) {
            for (String config : activeConfigs.getOrDefault(mod, mod.mixinConfigs())) toml.append("\n[[mixins]]\nconfig=").append(quote(config)).append('\n');
        }
        if (accessTransformer) toml.append("\n[[accessTransformers]]\nfile=\"META-INF/accesstransformer.cfg\"\n");
        return toml.toString();
    }

    private static String licenseOf(FabricMod mod) {
        if (!mod.json.has("license")) return "All Rights Reserved";
        var license = mod.json.get("license");
        if (license.isJsonPrimitive()) return license.getAsString();
        if (license.isJsonArray() && license.getAsJsonArray().size() > 0) return license.getAsJsonArray().get(0).getAsString();
        return "All Rights Reserved";
    }

    private static String quote(String value) {
        StringBuilder quoted = new StringBuilder("\"");
        for (char c : value.toCharArray()) {
            switch (c) {
                case '"': quoted.append("\\\""); break;
                case '\\': quoted.append("\\\\"); break;
                case '\n': quoted.append("\\n"); break;
                case '\r': break;
                case '\t': quoted.append("\\t"); break;
                default:
                    if (c < 0x20) break;
                    quoted.append(c);
            }
        }
        return quoted.append('"').toString();
    }

    /**
     * Fabric API Mixins whose job NeoForge already does, and which break when both run: Fabric API
     * freezes the built-in registries itself after mod setup, but NeoForge has frozen them already; and so on.
     */
    private static final Set<String> NEOFORGE_CONFLICTS = Set.of(
        "net.fabricmc.fabric.mixin.registry.sync.client.MinecraftMixin",
        "net.fabricmc.fabric.mixin.registry.sync.MainMixin",
        // NeoForge enables every mod JAR's resources itself; Fabric API's own pack auto-enabling expects its setup.
        "net.fabricmc.fabric.mixin.resource.PackRepositoryMixin");

    /**
     * NeoForge patches some of the Minecraft methods Fabric mods inject into, so an injection can find no
     * target. On Fabric that is fatal ("required" configs, "defaultRequire": 1); inside NeoForge, HitBoy skips
     * such an injection instead (Mixin still logs it), so the rest of the mod keeps working.
     */
    static byte[] softenMixinConfig(byte[] config) {
        try {
            com.google.gson.JsonObject json = com.google.gson.JsonParser.parseString(
                new String(config, StandardCharsets.UTF_8)).getAsJsonObject();
            json.addProperty("required", false);
            com.google.gson.JsonObject injectors = json.has("injectors") && json.get("injectors").isJsonObject()
                ? json.getAsJsonObject("injectors") : new com.google.gson.JsonObject();
            injectors.addProperty("defaultRequire", 0);
            json.add("injectors", injectors);
            String prefix = json.has("package") ? json.get("package").getAsString() + "." : "";
            for (String list : new String[] {"mixins", "client", "server"}) {
                if (!json.has(list) || !json.get(list).isJsonArray()) continue;
                com.google.gson.JsonArray kept = new com.google.gson.JsonArray();
                for (com.google.gson.JsonElement name : json.getAsJsonArray(list)) {
                    if (NEOFORGE_CONFLICTS.contains(prefix + name.getAsString())) {
                        System.out.println("[HitBoy Fabric] Leaving out Mixin " + prefix + name.getAsString() + ": NeoForge already does this.");
                    } else {
                        kept.add(name);
                    }
                }
                json.add(list, kept);
            }
            return json.toString().getBytes(StandardCharsets.UTF_8);
        } catch (RuntimeException unreadable) {
            return config;
        }
    }

    /**
     * Fields that NeoForge's built-in coremod (NeoForgeCoreMod, ReplaceFieldWithGetterAccess) turns into
     * getter calls. That coremod requires the fields to stay private, so widening them would stop the game.
     */
    private static final Set<String> NEOFORGE_GETTER_FIELDS = Set.of(
        "net.minecraft.world.level.biome.Biome.climateSettings",
        "net.minecraft.world.level.biome.Biome.specialEffects",
        "net.minecraft.world.level.biome.Biome.attributes",
        "net.minecraft.world.level.levelgen.structure.Structure.settings",
        "net.minecraft.world.level.block.FlowerPotBlock.potted");

    /**
     * Fabric access widener -> NeoForge access transformer. Access transformers only widen, so "mutable"
     * and "extendable" map to their widening forms (removing final).
     */
    static String toAccessTransformer(byte[] widener) {
        StringBuilder out = new StringBuilder();
        boolean header = true;
        for (String raw : new String(widener, StandardCharsets.UTF_8).split("\\R")) {
            int comment = raw.indexOf('#');
            String line = (comment >= 0 ? raw.substring(0, comment) : raw).trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split("\\s+");
            if (header) {
                header = false;
                if (parts[0].equals("accessWidener") || parts[0].equals("classTweaker")) continue;
            }
            if (parts.length < 3) continue;
            String access = parts[0].startsWith("transitive-") ? parts[0].substring("transitive-".length()) : parts[0];
            String kind = parts[1];
            String owner = parts[2].replace('/', '.');
            String member = parts.length >= 5 ? parts[3] : null;
            String descriptor = parts.length >= 5 ? parts[4] : null;
            String modifier;
            switch (access) {
                case "accessible": modifier = "public"; break;
                case "extendable": modifier = kind.equals("method") ? "protected-f" : "public-f"; break;
                case "mutable": modifier = "public-f"; break;
                default: continue; // e.g. classTweaker's inject-interface
            }
            switch (kind) {
                case "class": out.append(modifier).append(' ').append(owner).append('\n'); break;
                case "method":
                    if (member != null) out.append(modifier).append(' ').append(owner).append(' ').append(member).append(descriptor).append('\n');
                    break;
                case "field":
                    if (member != null && NEOFORGE_GETTER_FIELDS.contains(owner + "." + member)) break;
                    if (member != null) out.append(modifier).append(' ').append(owner).append(' ').append(member).append('\n');
                    break;
                default: break;
            }
        }
        return out.toString();
    }

    /** {@code @Mod("id") public final class FabricEntry { FabricEntry() { FabricRuntime.constructBridged("fabric-id", FabricEntry.class); } }} */
    private static byte[] entryClass(String id, String fabricId, String className) {
        ClassWriter writer = new ClassWriter(0);
        writer.visit(Opcodes.V17, Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL | Opcodes.ACC_SUPER, className, null, "java/lang/Object", null);
        AnnotationVisitor annotation = writer.visitAnnotation("Lnet/neoforged/fml/common/Mod;", true);
        annotation.visit("value", id);
        annotation.visitEnd();
        MethodVisitor constructor = writer.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        constructor.visitCode();
        constructor.visitVarInsn(Opcodes.ALOAD, 0);
        constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        constructor.visitLdcInsn(fabricId);
        constructor.visitLdcInsn(Type.getObjectType(className));
        constructor.visitMethodInsn(Opcodes.INVOKESTATIC, "com/hitboy/loader/fabric/FabricRuntime", "constructBridged",
            "(Ljava/lang/String;Ljava/lang/Class;)V", false);
        constructor.visitInsn(Opcodes.RETURN);
        constructor.visitMaxs(2, 1);
        constructor.visitEnd();
        writer.visitEnd();
        return writer.toByteArray();
    }

    private static byte[] read(ZipFile source, ZipEntry entry) throws IOException {
        try (InputStream input = source.getInputStream(entry)) {
            return input.readAllBytes();
        }
    }

    private static void put(ZipOutputStream out, Set<String> written, String name, byte[] data) throws IOException {
        if (!written.add(name)) return;
        out.putNextEntry(new ZipEntry(name));
        out.write(data);
        out.closeEntry();
    }
}
