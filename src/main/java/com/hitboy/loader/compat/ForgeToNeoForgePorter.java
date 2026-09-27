package com.hitboy.loader.compat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * port.exe's Forge -> NeoForge converter (Minecraft 26.x). It renames the Forge APIs that have a NeoForge
 * equivalent, rewrites the few that work differently (Forge 26's event bus, config screens, the
 * environment check) to a small bridge class copied into the mod, and turns mods.toml into
 * neoforge.mods.toml. Afterwards every NeoForge and Minecraft class, method, and field the mod uses is
 * checked against NeoForge's real API ({@link NeoForgeApiIndex}); if anything is missing, no JAR is
 * written and the report lists exactly what is missing.
 */
public final class ForgeToNeoForgePorter {
    /** Forge class (exact name, or package prefix ending in '/') -> NeoForge class or package. Longest match wins. */
    private static final Map<String, String> CLASSES = new LinkedHashMap<>();

    static {
        CLASSES.put("net/minecraftforge/fml/common/Mod$EventBusSubscriber", "net/neoforged/fml/common/EventBusSubscriber");
        CLASSES.put("net/minecraftforge/fml/common/Mod", "net/neoforged/fml/common/Mod");
        CLASSES.put("net/minecraftforge/api/distmarker/", "net/neoforged/api/distmarker/");
        CLASSES.put("net/minecraftforge/eventbus/api/listener/SubscribeEvent", "net/neoforged/bus/api/SubscribeEvent");
        CLASSES.put("net/minecraftforge/fml/event/lifecycle/", "net/neoforged/fml/event/lifecycle/");
        CLASSES.put("net/minecraftforge/fml/event/config/", "net/neoforged/fml/event/config/");
        CLASSES.put("net/minecraftforge/fml/loading/FMLEnvironment", "net/neoforged/fml/loading/FMLEnvironment");
        CLASSES.put("net/minecraftforge/fml/loading/FMLPaths", "net/neoforged/fml/loading/FMLPaths");
        CLASSES.put("net/minecraftforge/fml/ModList", "net/neoforged/fml/ModList");
        CLASSES.put("net/minecraftforge/fml/ModContainer", "net/neoforged/fml/ModContainer");
        CLASSES.put("net/minecraftforge/fml/ModLoadingContext", "net/neoforged/fml/ModLoadingContext");
        CLASSES.put("net/minecraftforge/fml/config/ModConfig", "net/neoforged/fml/config/ModConfig");
        CLASSES.put("net/minecraftforge/common/ForgeConfigSpec", "net/neoforged/neoforge/common/ModConfigSpec");
        CLASSES.put("net/minecraftforge/client/event/", "net/neoforged/neoforge/client/event/");
        CLASSES.put("net/minecraftforge/event/", "net/neoforged/neoforge/event/");
        CLASSES.put("net/minecraftforge/common/util/", "net/neoforged/neoforge/common/util/");
        CLASSES.put("net/minecraftforge/client/settings/", "net/neoforged/neoforge/client/settings/");
        CLASSES.put("net/minecraftforge/registries/DeferredRegister", "net/neoforged/neoforge/registries/DeferredRegister");
        // Its calls are rewritten to the bridge below; what is left are variable types, which become Object.
        CLASSES.put("net/minecraftforge/eventbus/api/bus/BusGroup", "java/lang/Object");
    }

    private static final String BRIDGE_TEMPLATE = "com/hitboy/forgeport/ForgeBridge";
    private static final String BUS_GROUP = "net/minecraftforge/eventbus/api/bus/BusGroup";
    private static final String FORGE_MAIN = "net/minecraftforge/common/MinecraftForge";

    public record Result(boolean ported, Path output, List<String> blockers, List<String> notes) {
    }

    public Result port(Path forgeJar, Path outputDirectory, String minecraftVersion) throws IOException {
        List<String> blockers = new ArrayList<>();
        List<String> notes = new ArrayList<>();
        NeoForgeApiIndex api = NeoForgeApiIndex.load(minecraftVersion);
        if (api == null) {
            blockers.add("Forge -> NeoForge porting is available for Minecraft 26.3 only (no NeoForge " + minecraftVersion + " API index).");
            return new Result(false, null, blockers, notes);
        }
        try (ZipFile jar = new ZipFile(forgeJar.toFile())) {
            if (jar.getEntry("META-INF/mods.toml") == null) {
                blockers.add("Not a Forge mod: META-INF/mods.toml is missing.");
                return new Result(false, null, blockers, notes);
            }
            String toml = text(jar, "META-INF/mods.toml");
            Map<String, byte[]> classes = new LinkedHashMap<>();
            Map<String, byte[]> resources = new LinkedHashMap<>();
            for (Enumeration<? extends ZipEntry> entries = jar.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = entries.nextElement();
                if (entry.isDirectory()) continue;
                String name = entry.getName();
                String upper = name.toUpperCase();
                if (upper.startsWith("META-INF/") && (upper.endsWith(".SF") || upper.endsWith(".RSA") || upper.endsWith(".DSA"))) continue;
                if (name.endsWith(".class") && !name.startsWith("META-INF/")) classes.put(name.substring(0, name.length() - 6), bytes(jar, entry));
                else resources.put(name, bytes(jar, entry));
            }

            String bridge = bridgeName(classes);
            Set<String> ownClasses = new LinkedHashSet<>(classes.keySet());
            ownClasses.add(bridge);
            Map<String, byte[]> ported = new LinkedHashMap<>();
            for (Map.Entry<String, byte[]> entry : classes.entrySet()) {
                ported.put(entry.getKey(), convertClass(entry.getValue(), bridge, blockers));
            }
            ported.put(bridge, relocatedBridge(bridge));

            Set<String> missing = new TreeSet<>();
            for (Map.Entry<String, byte[]> entry : ported.entrySet()) {
                if (entry.getKey().equals(bridge)) continue;
                check(entry.getKey(), entry.getValue(), api, ownClasses, missing);
                checkListeners(entry.getValue(), missing);
            }
            blockers.addAll(missing);
            if (!blockers.isEmpty()) return new Result(false, null, blockers, notes);

            String manifestMixins = mixinConfigsFromManifest(resources.get("META-INF/MANIFEST.MF"));
            String neoToml = neoForgeToml(toml, manifestMixins, resources.containsKey("META-INF/accesstransformer.cfg"), notes);
            Files.createDirectories(outputDirectory);
            String fileName = forgeJar.getFileName().toString().replaceAll("(?i)\\.jar$", "");
            Path output = outputDirectory.resolve(fileName.replace("forge", "neoforge").equals(fileName) ? fileName + "-neoforge.jar" : fileName.replace("forge", "neoforge") + ".jar");
            Path temporary = output.resolveSibling(output.getFileName() + ".tmp");
            try (ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(temporary))) {
                for (Map.Entry<String, byte[]> entry : resources.entrySet()) {
                    if (entry.getKey().equals("META-INF/mods.toml")) continue;
                    out.putNextEntry(new ZipEntry(entry.getKey()));
                    out.write(entry.getValue());
                    out.closeEntry();
                }
                out.putNextEntry(new ZipEntry("META-INF/neoforge.mods.toml"));
                out.write(neoToml.getBytes(StandardCharsets.UTF_8));
                out.closeEntry();
                for (Map.Entry<String, byte[]> entry : ported.entrySet()) {
                    out.putNextEntry(new ZipEntry(entry.getKey() + ".class"));
                    out.write(entry.getValue());
                    out.closeEntry();
                }
            }
            Files.move(temporary, output, StandardCopyOption.REPLACE_EXISTING);
            return new Result(true, output, blockers, notes);
        }
    }

    // ---- class conversion ----

    private static String mapClass(String name) {
        String best = null;
        for (String key : CLASSES.keySet()) {
            boolean matches = key.endsWith("/") ? name.startsWith(key) : (name.equals(key) || name.startsWith(key + "$"));
            if (matches && (best == null || key.length() > best.length())) best = key;
        }
        return best == null ? name : CLASSES.get(best) + name.substring(best.length());
    }

    private static final Remapper FORGE_TO_NEOFORGE = new Remapper() {
        @Override
        public String map(String internalName) {
            return mapClass(internalName);
        }

        @Override
        public String mapMethodName(String owner, String name, String descriptor) {
            if (owner.startsWith("net/minecraftforge/client/event/ScreenEvent$MouseScrolled")) {
                if (name.equals("getDeltaX")) return "getScrollDeltaX";
                if (name.equals("getDeltaY")) return "getScrollDeltaY";
            }
            return name;
        }
    };

    private byte[] convertClass(byte[] original, String bridge, List<String> blockers) {
        // 1. Forge APIs that work differently: redirect them to the bridge (still in Forge names).
        ClassNode node = new ClassNode();
        new ClassReader(original).accept(node, 0);
        for (MethodNode method : node.methods) {
            for (AbstractInsnNode instruction : method.instructions.toArray()) {
                if (instruction instanceof FieldInsnNode field && field.getOpcode() == Opcodes.GETSTATIC
                    && field.owner.equals(BUS_GROUP) && field.name.equals("DEFAULT")) {
                    method.instructions.set(field, new InsnNode(Opcodes.ACONST_NULL));
                } else if (instruction instanceof MethodInsnNode call && call.owner.equals(BUS_GROUP) && call.name.equals("register")
                    && call.desc.equals("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/Object;)Ljava/util/Collection;")) {
                    method.instructions.set(call, new MethodInsnNode(Opcodes.INVOKESTATIC, bridge, "register",
                        "(Ljava/lang/Object;Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/Object;)Ljava/util/Collection;", false));
                } else if (instruction instanceof MethodInsnNode call && call.owner.equals(FORGE_MAIN) && call.name.equals("registerConfigScreen")
                    && call.desc.equals("(Ljava/util/function/Function;)V")) {
                    method.instructions.set(call, new MethodInsnNode(Opcodes.INVOKESTATIC, bridge, "registerConfigScreen", "(Ljava/util/function/Function;)V", false));
                } else if (instruction instanceof FieldInsnNode field && field.getOpcode() == Opcodes.GETSTATIC
                    && field.owner.equals("net/minecraftforge/fml/loading/FMLEnvironment") && field.name.equals("dist")) {
                    method.instructions.set(field, new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraftforge/fml/loading/FMLEnvironment",
                        "getDist", "()Lnet/minecraftforge/api/distmarker/Dist;", false));
                }
            }
        }
        for (MethodNode method : node.methods) {
            for (AbstractInsnNode instruction : method.instructions.toArray()) {
                String owner = instruction instanceof MethodInsnNode call ? call.owner + "." + call.name
                    : instruction instanceof FieldInsnNode field ? field.owner + "." + field.name : null;
                if (owner != null && owner.startsWith(BUS_GROUP + ".")) {
                    blockers.add("Forge event-bus call HitBoy's porter cannot convert yet: " + owner.replace('/', '.') + " (used in " + node.name.replace('/', '.') + ")");
                }
            }
        }
        // Forge's @EventBusSubscriber(bus = ...) and @SubscribeEvent options do not exist on NeoForge.
        dropAnnotationValues(node.visibleAnnotations, "Lnet/minecraftforge/fml/common/Mod$EventBusSubscriber;", Set.of("modid", "value"));
        for (MethodNode method : node.methods) {
            dropAnnotationValues(method.visibleAnnotations, "Lnet/minecraftforge/eventbus/api/listener/SubscribeEvent;", Set.of());
        }
        ClassWriter intermediate = new ClassWriter(0);
        node.accept(intermediate);

        // 2. Rename everything else.
        ClassWriter writer = new ClassWriter(0);
        new ClassReader(intermediate.toByteArray()).accept(new ClassRemapper(writer, FORGE_TO_NEOFORGE), 0);
        return writer.toByteArray();
    }

    private static void dropAnnotationValues(List<AnnotationNode> annotations, String descriptor, Set<String> keep) {
        if (annotations == null) return;
        for (AnnotationNode annotation : annotations) {
            if (!annotation.desc.equals(descriptor) || annotation.values == null) continue;
            List<Object> kept = new ArrayList<>();
            for (int index = 0; index + 1 < annotation.values.size(); index += 2) {
                if (keep.contains((String) annotation.values.get(index))) {
                    kept.add(annotation.values.get(index));
                    kept.add(annotation.values.get(index + 1));
                }
            }
            annotation.values = kept.isEmpty() ? null : kept;
        }
    }

    /** The bridge goes into the mod's own package (from its @Mod class), so two ported mods never share a package. */
    private static String bridgeName(Map<String, byte[]> classes) {
        for (Map.Entry<String, byte[]> entry : classes.entrySet()) {
            if (new String(entry.getValue(), StandardCharsets.ISO_8859_1).contains("Lnet/minecraftforge/fml/common/Mod;")) {
                ClassNode node = new ClassNode();
                new ClassReader(entry.getValue()).accept(node, ClassReader.SKIP_CODE);
                if (node.visibleAnnotations != null && node.visibleAnnotations.stream().anyMatch(a -> a.desc.equals("Lnet/minecraftforge/fml/common/Mod;"))) {
                    int slash = entry.getKey().lastIndexOf('/');
                    return (slash < 0 ? "" : entry.getKey().substring(0, slash + 1)) + "HitBoyForgeBridge";
                }
            }
        }
        return "hitboy/forgeport/HitBoyForgeBridge";
    }

    private static byte[] relocatedBridge(String bridge) throws IOException {
        byte[] template;
        try (InputStream input = ForgeToNeoForgePorter.class.getResourceAsStream("/" + BRIDGE_TEMPLATE + ".class")) {
            if (input == null) throw new IOException("HitBoy's Forge bridge template is missing");
            template = input.readAllBytes();
        }
        ClassWriter writer = new ClassWriter(0);
        new ClassReader(template).accept(new ClassRemapper(writer, new Remapper() {
            @Override
            public String map(String internalName) {
                return internalName.equals(BRIDGE_TEMPLATE) ? bridge : internalName;
            }
        }), 0);
        return writer.toByteArray();
    }

    // ---- verification ----

    /** Records every NeoForge/Minecraft class and member the class uses, and anything still from Forge. */
    private static void check(String className, byte[] bytes, NeoForgeApiIndex api, Set<String> ownClasses, Set<String> missing) {
        String where = className.replace('/', '.');
        Remapper recorder = new Remapper() {
            @Override
            public String map(String internalName) {
                if (internalName.startsWith("net/minecraftforge/")) {
                    missing.add("Forge API with no NeoForge equivalent in HitBoy's porter: " + internalName.replace('/', '.') + " (used in " + where + ")");
                } else if ((internalName.startsWith("net/neoforged/") || internalName.startsWith("net/minecraft/")) && !api.hasClass(internalName)) {
                    missing.add("Not in NeoForge " + api.minecraftVersion() + ": class " + internalName.replace('/', '.') + " (used in " + where + ")");
                }
                return internalName;
            }

            @Override
            public String mapMethodName(String owner, String name, String descriptor) {
                if (checked(owner, ownClasses) && api.hasClass(owner) && !api.hasMethod(owner, name, descriptor)) {
                    missing.add("Not in NeoForge " + api.minecraftVersion() + ": method " + owner.replace('/', '.') + "." + name + descriptor + " (used in " + where + ")");
                }
                return name;
            }

            @Override
            public String mapFieldName(String owner, String name, String descriptor) {
                if (checked(owner, ownClasses) && api.hasClass(owner) && !api.hasField(owner, name)) {
                    missing.add("Not in NeoForge " + api.minecraftVersion() + ": field " + owner.replace('/', '.') + "." + name + " (used in " + where + ")");
                }
                return name;
            }
        };
        new ClassReader(bytes).accept(new ClassRemapper(new ClassVisitor(Opcodes.ASM9) { }, recorder), 0);
    }

    private static boolean checked(String owner, Set<String> ownClasses) {
        return !owner.startsWith("[") && !ownClasses.contains(owner)
            && (owner.startsWith("net/neoforged/") || owner.startsWith("net/minecraft/"));
    }

    /** NeoForge registers static @SubscribeEvent methods itself and requires them to return void. */
    private static void checkListeners(byte[] bytes, Set<String> missing) {
        ClassNode node = new ClassNode();
        new ClassReader(bytes).accept(node, ClassReader.SKIP_CODE);
        boolean subscriber = node.visibleAnnotations != null
            && node.visibleAnnotations.stream().anyMatch(a -> a.desc.equals("Lnet/neoforged/fml/common/EventBusSubscriber;"));
        if (!subscriber) return;
        for (MethodNode method : node.methods) {
            boolean listener = method.visibleAnnotations != null
                && method.visibleAnnotations.stream().anyMatch(a -> a.desc.equals("Lnet/neoforged/bus/api/SubscribeEvent;"));
            if (listener && (method.access & Opcodes.ACC_STATIC) != 0 && !method.desc.endsWith(")V")) {
                missing.add("Cancelling static event listener (NeoForge needs these to return void): "
                    + node.name.replace('/', '.') + "." + method.name);
            }
        }
    }

    // ---- metadata ----

    private static String neoForgeToml(String toml, String manifestMixins, boolean accessTransformer, List<String> notes) {
        StringBuilder out = new StringBuilder();
        String section = "";
        List<String> block = new ArrayList<>();
        for (String line : (toml + "\n").split("\\R", -1)) {
            String trimmed = line.trim();
            if (trimmed.startsWith("[")) {
                flush(out, section, block, notes);
                section = trimmed;
                block = new ArrayList<>();
            }
            block.add(line);
        }
        flush(out, section, block, notes);
        String result = out.toString().replaceAll("(?m)^\\s*loaderVersion\\s*=.*$", "loaderVersion=\"[1,)\"");
        if (manifestMixins != null) {
            for (String config : manifestMixins.split(",")) {
                if (!config.isBlank()) result += "\n[[mixins]]\nconfig=\"" + config.trim() + "\"\n";
            }
        }
        if (accessTransformer && !result.contains("[[accessTransformers]]")) {
            result += "\n[[accessTransformers]]\nfile=\"META-INF/accesstransformer.cfg\"\n";
        }
        return result;
    }

    /** Copies one TOML section; drops the dependency on "forge" and turns Forge's "mandatory" into NeoForge's "type". */
    private static void flush(StringBuilder out, String section, List<String> block, List<String> notes) {
        String joined = String.join("\n", block);
        if (section.startsWith("[[dependencies") && Pattern.compile("(?m)^\\s*modId\\s*=\\s*[\"']forge[\"']").matcher(joined).find()) {
            notes.add("Removed the dependency on Forge (NeoForge takes its place).");
            return;
        }
        if (section.startsWith("[[dependencies")) {
            Matcher mandatory = Pattern.compile("(?m)^\\s*mandatory\\s*=\\s*(true|false)\\s*$").matcher(joined);
            joined = mandatory.replaceAll(match -> "type=\"" + (match.group(1).equals("true") ? "required" : "optional") + "\"");
        }
        if (!joined.isEmpty()) out.append(joined).append('\n');
    }

    private static String mixinConfigsFromManifest(byte[] manifest) throws IOException {
        if (manifest == null) return null;
        return new Manifest(new java.io.ByteArrayInputStream(manifest)).getMainAttributes().getValue(new Attributes.Name("MixinConfigs"));
    }

    private static String text(ZipFile jar, String name) throws IOException {
        return new String(bytes(jar, jar.getEntry(name)), StandardCharsets.UTF_8);
    }

    private static byte[] bytes(ZipFile jar, ZipEntry entry) throws IOException {
        try (InputStream input = jar.getInputStream(entry)) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            input.transferTo(out);
            return out.toByteArray();
        }
    }
}
