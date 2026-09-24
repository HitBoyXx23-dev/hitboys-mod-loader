package com.hitboy.loader.fabric;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.hitboy.loader.mixin.HitBoyIntermediaryRemapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Rewrites a production Fabric mod from Fabric's "intermediary" names to the names in the running
 * Minecraft JAR, the same job Fabric Loader's remapper does. Mixin reference maps are rewritten too,
 * and a generated hitboy.json lets HitBoy's Mixin and access-widener runtimes pick the mod up.
 */
final class FabricJarRemapper {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    private final HitBoyIntermediaryRemapper mappings;
    private final Remapper asmRemapper;

    FabricJarRemapper(HitBoyIntermediaryRemapper mappings) {
        this.mappings = mappings;
        this.asmRemapper = mappings == null ? null : new Remapper() {
            @Override
            public String map(String internalName) {
                // Mojang classes outside net/minecraft (such as com/mojang/blaze3d) keep their names but have
                // obfuscated inner classes ("VertexFormat$class_5596"); map() leaves unknown names as-is.
                return internalName.startsWith("net/minecraft/") || internalName.contains("$class_") ? mappings.map(internalName) : internalName;
            }

            @Override
            public String mapMethodName(String owner, String name, String descriptor) {
                // "comp_" names are record component accessors.
                return name.startsWith("method_") || name.startsWith("comp_") ? mappings.mapMethodName(owner, name, descriptor) : name;
            }

            @Override
            public String mapFieldName(String owner, String name, String descriptor) {
                return name.startsWith("field_") || name.startsWith("comp_") ? mappings.mapFieldName(owner, name, descriptor) : name;
            }

            @Override
            public String mapRecordComponentName(String owner, String name, String descriptor) {
                return name.startsWith("comp_") ? mappings.mapFieldName(owner, name, descriptor) : name;
            }
        };
    }

    void remap(FabricMod mod, Path output) throws IOException {
        Path temporary = output.resolveSibling(output.getFileName() + ".tmp");
        Files.createDirectories(output.getParent());
        String accessWidener = mod.accessWidener();
        try (JarFile input = new JarFile(mod.sourceJar.toFile());
             JarOutputStream out = new JarOutputStream(Files.newOutputStream(temporary))) {
            Enumeration<JarEntry> entries = input.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (entry.isDirectory() || skip(name)) continue;
                byte[] data;
                try (InputStream stream = input.getInputStream(entry)) {
                    data = stream.readAllBytes();
                }
                if (name.endsWith(".class")) data = remapClass(data);
                else if (isRefmap(name, data)) data = remapRefmap(data);
                write(out, name, data);
                // HitBoy's access-widener runtime reads *.accesswidener files; Fabric allows any name.
                if (name.equals(accessWidener) && !name.endsWith(".accesswidener")) write(out, name + ".accesswidener", data);
            }
            write(out, "hitboy.json", hitBoyDescriptor(mod).getBytes(StandardCharsets.UTF_8));
        }
        Files.move(temporary, output, StandardCopyOption.REPLACE_EXISTING);
    }

    private static boolean skip(String name) {
        String upper = name.toUpperCase(Locale.ROOT);
        if (upper.startsWith("META-INF/") && (upper.endsWith(".SF") || upper.endsWith(".RSA") || upper.endsWith(".DSA") || upper.endsWith(".EC"))) return true;
        // Bundled JARs are unpacked and remapped as their own mods; a leftover hitboy.json would be stale.
        return name.startsWith("META-INF/jars/") || name.equals("hitboy.json") || name.equals("META-INF/MANIFEST.MF");
    }

    private byte[] remapClass(byte[] data) {
        if (asmRemapper == null) return data;
        ClassNode node = new ClassNode();
        new ClassReader(data).accept(new ClassRemapper(node, asmRemapper), 0);
        if (isMixin(node)) remapMixinAnnotations(node);
        ClassWriter writer = new ClassWriter(0);
        node.accept(writer);
        return writer.toByteArray();
    }

    // ---- Mixin annotation strings ----
    // Mods built without a refmap name their targets in intermediary directly (for example
    // targets = "net/minecraft/class_327$class_11464$1" or method = "method_1234"). Fabric runs in
    // intermediary so that works there; HitBoy runs in Minecraft's own names, so rewrite them.

    private static boolean isMixin(ClassNode node) {
        for (List<AnnotationNode> annotations : Arrays.asList(node.visibleAnnotations, node.invisibleAnnotations)) {
            if (annotations == null) continue;
            for (AnnotationNode annotation : annotations) if (MIXIN.equals(annotation.desc)) return true;
        }
        return false;
    }

    private static final String MIXIN = "Lorg/spongepowered/asm/mixin/Mixin;";

    private void remapMixinAnnotations(ClassNode node) {
        remapAll(node.visibleAnnotations);
        remapAll(node.invisibleAnnotations);
        for (FieldNode field : node.fields) {
            remapAll(field.visibleAnnotations);
            remapAll(field.invisibleAnnotations);
        }
        for (MethodNode method : node.methods) {
            remapAll(method.visibleAnnotations);
            remapAll(method.invisibleAnnotations);
            if (method.visibleParameterAnnotations != null) for (List<AnnotationNode> list : method.visibleParameterAnnotations) remapAll(list);
            if (method.invisibleParameterAnnotations != null) for (List<AnnotationNode> list : method.invisibleParameterAnnotations) remapAll(list);
        }
    }

    private void remapAll(List<AnnotationNode> annotations) {
        if (annotations == null) return;
        for (AnnotationNode annotation : annotations) remapAnnotation(annotation);
    }

    private void remapAnnotation(AnnotationNode annotation) {
        if (annotation.values == null) return;
        boolean plainName = annotation.desc.equals("Lorg/spongepowered/asm/mixin/gen/Accessor;")
            || annotation.desc.equals("Lorg/spongepowered/asm/mixin/gen/Invoker;");
        for (int index = 1; index < annotation.values.size(); index += 2) {
            annotation.values.set(index, remapValue(annotation.values.get(index), plainName));
        }
    }

    private Object remapValue(Object value, boolean plainName) {
        if (value instanceof String) return remapString((String) value, plainName);
        if (value instanceof AnnotationNode) {
            remapAnnotation((AnnotationNode) value);
            return value;
        }
        if (value instanceof List) {
            List<Object> list = new ArrayList<>();
            for (Object entry : (List<?>) value) list.add(remapValue(entry, plainName));
            return list;
        }
        return value;
    }

    private String remapString(String value, boolean plainName) {
        if (plainName) {
            if (value.startsWith("method_") || value.startsWith("comp_")) return mappings.mapMethodName("", value, "");
            if (value.startsWith("field_")) return mappings.mapFieldName("", value, "");
            return value;
        }
        if (value.startsWith("net.minecraft.class_")) {
            return mappings.remapIntermediaryText(value.replace('.', '/')).replace('/', '.');
        }
        if (value.contains("class_") || value.contains("method_") || value.contains("field_") || value.contains("comp_")) {
            return mappings.remapIntermediaryText(value);
        }
        return value;
    }

    private static boolean isRefmap(String name, byte[] data) {
        if (!name.endsWith(".json") || name.contains("/")) return false;
        String text = new String(data, 0, Math.min(data.length, 256), StandardCharsets.UTF_8);
        return text.contains("\"mappings\"") && name.toLowerCase(Locale.ROOT).contains("refmap");
    }

    private byte[] remapRefmap(byte[] data) {
        if (mappings == null) return data;
        JsonObject refmap = JsonParser.parseString(new String(data, StandardCharsets.UTF_8)).getAsJsonObject();
        remapValues(refmap.get("mappings"));
        if (refmap.has("data") && refmap.get("data").isJsonObject()) {
            for (Map.Entry<String, JsonElement> context : refmap.getAsJsonObject("data").entrySet()) remapValues(context.getValue());
        }
        return GSON.toJson(refmap).getBytes(StandardCharsets.UTF_8);
    }

    private void remapValues(JsonElement element) {
        if (element == null || !element.isJsonObject()) return;
        for (Map.Entry<String, JsonElement> mixin : element.getAsJsonObject().entrySet()) {
            if (!mixin.getValue().isJsonObject()) continue;
            JsonObject references = mixin.getValue().getAsJsonObject();
            for (String key : references.keySet().toArray(new String[0])) {
                JsonElement value = references.get(key);
                if (value.isJsonPrimitive()) references.add(key, new JsonPrimitive(mappings.remapIntermediaryText(value.getAsString())));
            }
        }
    }

    private static String hitBoyDescriptor(FabricMod mod) {
        JsonObject descriptor = new JsonObject();
        descriptor.addProperty("id", "fabric." + mod.getId());
        descriptor.addProperty("name", mod.getName() + " (Fabric)");
        descriptor.addProperty("version", mod.getVersion().getFriendlyString());
        descriptor.addProperty("libraryOnly", true);
        JsonArray mixins = new JsonArray();
        for (String config : mod.mixinConfigs()) mixins.add(config);
        descriptor.add("mixins", mixins);
        return GSON.toJson(descriptor);
    }

    private static void write(JarOutputStream out, String name, byte[] data) throws IOException {
        out.putNextEntry(new JarEntry(name));
        out.write(data);
        out.closeEntry();
    }
}
