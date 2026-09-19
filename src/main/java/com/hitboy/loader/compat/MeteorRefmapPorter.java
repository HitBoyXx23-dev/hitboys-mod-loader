package com.hitboy.loader.compat;

import com.hitboy.loader.mixin.HitBoyIntermediaryRemapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.List;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public final class MeteorRefmapPorter {
    private MeteorRefmapPorter() {}

    public static void main(String[] args) throws Exception {
        if (args.length != 2) throw new IllegalArgumentException("Usage: MeteorRefmapPorter <game-version> <jar>");
        HitBoyIntermediaryRemapper remapper = new HitBoyIntermediaryRemapper(args[0]);
        URI jarUri = URI.create("jar:" + Path.of(args[1]).toAbsolutePath().toUri());
        try (FileSystem fileSystem = FileSystems.newFileSystem(jarUri, Map.of())) {
            Path refmap = fileSystem.getPath("/meteor-client-refmap.json");
            JsonElement content = JsonParser.parseString(Files.readString(refmap, StandardCharsets.UTF_8));
            JsonObject converted = remapValues(content, remapper).getAsJsonObject();
            addShadowMappings(fileSystem, converted.getAsJsonObject("mappings"), remapper);
            Files.writeString(refmap, new Gson().toJson(converted), StandardCharsets.UTF_8);
            Files.deleteIfExists(fileSystem.getPath("/fabric.mod.json"));
            Files.deleteIfExists(fileSystem.getPath("/meteor-client-indigo.mixins.json"));
        }
    }

    private static void addShadowMappings(FileSystem fileSystem, JsonObject mappings,
        HitBoyIntermediaryRemapper remapper) throws Exception {
        Path root = fileSystem.getPath("/meteordevelopment/meteorclient/mixin");
        try (java.util.stream.Stream<Path> paths = Files.walk(root)) {
            for (Path path : paths.filter(value -> value.toString().endsWith(".class")).toList()) {
                ClassNode node = new ClassNode();
                new ClassReader(Files.readAllBytes(path)).accept(node, ClassReader.SKIP_DEBUG);
                String target = mixinTarget(node.visibleAnnotations);
                if (target == null) target = mixinTarget(node.invisibleAnnotations);
                if (target == null) continue;
                JsonObject classMappings = mappings.has(node.name) ? mappings.getAsJsonObject(node.name) : new JsonObject();
                boolean annotationsChanged = false;
                for (FieldNode field : node.fields) {
                    if (!hasAnnotation(field.visibleAnnotations, "Lorg/spongepowered/asm/mixin/Shadow;")
                        && !hasAnnotation(field.invisibleAnnotations, "Lorg/spongepowered/asm/mixin/Shadow;")) continue;
                    String mapped = remapper.mapFieldName(target, field.name, field.desc);
                    classMappings.addProperty(field.name, mapped + ":" + field.desc);
                }
                for (MethodNode method : node.methods) {
                    annotationsChanged |= remapBareAnnotationValues(method.visibleAnnotations, remapper);
                    annotationsChanged |= remapBareAnnotationValues(method.invisibleAnnotations, remapper);
                    addBareMappings(method.visibleAnnotations, classMappings, remapper);
                    addBareMappings(method.invisibleAnnotations, classMappings, remapper);
                    if (!hasAnnotation(method.visibleAnnotations, "Lorg/spongepowered/asm/mixin/Shadow;")
                        && !hasAnnotation(method.invisibleAnnotations, "Lorg/spongepowered/asm/mixin/Shadow;")) continue;
                    String mapped = remapper.mapMethodName(target, method.name, method.desc);
                    classMappings.addProperty(method.name, mapped + method.desc);
                }
                mappings.add(node.name, classMappings);
                if (annotationsChanged) {
                    ClassWriter writer = new ClassWriter(0);
                    node.accept(writer);
                    Files.write(path, writer.toByteArray());
                }
            }
        }
    }

    private static boolean remapBareAnnotationValues(List<AnnotationNode> annotations,
        HitBoyIntermediaryRemapper remapper) {
        if (annotations == null) return false;
        boolean changed = false;
        for (AnnotationNode annotation : annotations) changed |= remapBareValues(annotation.values, remapper);
        return changed;
    }

    @SuppressWarnings("unchecked")
    private static boolean remapBareValues(List<?> values, HitBoyIntermediaryRemapper remapper) {
        if (values == null) return false;
        List<Object> mutable = (List<Object>) values;
        boolean changed = false;
        for (int index = 0; index < mutable.size(); index++) {
            Object value = mutable.get(index);
            if (value instanceof String && ((String) value).matches("(?:method|field)_\\d+")) {
                String mapped = remapper.remapIntermediaryText((String) value);
                mutable.set(index, mapped);
                changed |= !mapped.equals(value);
            } else if (value instanceof AnnotationNode) {
                changed |= remapBareValues(((AnnotationNode) value).values, remapper);
            } else if (value instanceof List<?>) {
                changed |= remapBareValues((List<?>) value, remapper);
            }
        }
        return changed;
    }

    private static void addBareMappings(List<AnnotationNode> annotations, JsonObject mappings,
        HitBoyIntermediaryRemapper remapper) {
        if (annotations == null) return;
        for (AnnotationNode annotation : annotations) addBareMappingValues(annotation.values, mappings, remapper);
    }

    private static void addBareMappingValues(List<?> values, JsonObject mappings, HitBoyIntermediaryRemapper remapper) {
        if (values == null) return;
        for (Object value : values) {
            if (value instanceof String) {
                String text = (String) value;
                if (text.matches("(?:method|field)_\\d+")) mappings.addProperty(text, remapper.remapIntermediaryText(text));
            } else if (value instanceof AnnotationNode) {
                addBareMappingValues(((AnnotationNode) value).values, mappings, remapper);
            } else if (value instanceof List<?>) {
                addBareMappingValues((List<?>) value, mappings, remapper);
            }
        }
    }

    private static String mixinTarget(List<AnnotationNode> annotations) {
        if (annotations == null) return null;
        for (AnnotationNode annotation : annotations) {
            if (!"Lorg/spongepowered/asm/mixin/Mixin;".equals(annotation.desc) || annotation.values == null) continue;
            for (int index = 0; index < annotation.values.size() - 1; index += 2) {
                if (!"value".equals(annotation.values.get(index))) continue;
                List<?> values = (List<?>) annotation.values.get(index + 1);
                if (!values.isEmpty() && values.get(0) instanceof Type) return ((Type) values.get(0)).getInternalName();
            }
        }
        return null;
    }

    private static boolean hasAnnotation(List<AnnotationNode> annotations, String descriptor) {
        if (annotations == null) return false;
        for (AnnotationNode annotation : annotations) if (descriptor.equals(annotation.desc)) return true;
        return false;
    }

    private static JsonElement remapValues(JsonElement element, HitBoyIntermediaryRemapper remapper) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            return new com.google.gson.JsonPrimitive(remapper.remapIntermediaryText(element.getAsString()));
        }
        if (element.isJsonArray()) {
            JsonArray result = new JsonArray();
            for (JsonElement child : element.getAsJsonArray()) result.add(remapValues(child, remapper));
            return result;
        }
        if (element.isJsonObject()) {
            JsonObject result = new JsonObject();
            for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
                result.add(entry.getKey(), remapValues(entry.getValue(), remapper));
            }
            return result;
        }
        return element;
    }
}
