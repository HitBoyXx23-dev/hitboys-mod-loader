package com.hitboy.loader;

import java.io.InputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

final class ResourceClassWriter extends ClassWriter {
    private final ClassLoader loader;
    private final Map<String, ClassReader> classes = new HashMap<>();

    ResourceClassWriter(ClassReader reader, ClassLoader loader) {
        super(reader, COMPUTE_MAXS | COMPUTE_FRAMES);
        this.loader = loader;
        classes.put(reader.getClassName(), reader);
    }

    @Override
    protected String getCommonSuperClass(String first, String second) {
        if (assignable(first, second, new HashSet<>())) return first;
        if (assignable(second, first, new HashSet<>())) return second;
        if (first.startsWith("[") && second.startsWith("[")) {
            String firstElement = component(first);
            String secondElement = component(second);
            if (firstElement != null && secondElement != null) {
                String common = getCommonSuperClass(firstElement, secondElement);
                return "[" + (common.startsWith("[") ? common : "L" + common + ";");
            }
        }
        if (first.startsWith("[") || second.startsWith("[")) return "java/lang/Object";
        if ((read(first).getAccess() & Opcodes.ACC_INTERFACE) != 0
            || (read(second).getAccess() & Opcodes.ACC_INTERFACE) != 0) return "java/lang/Object";
        String parent = read(first).getSuperName();
        while (parent != null) {
            if (assignable(parent, second, new HashSet<>())) return parent;
            parent = read(parent).getSuperName();
        }
        return "java/lang/Object";
    }

    private boolean assignable(String target, String source, Set<String> visited) {
        if (target.equals(source) || target.equals("java/lang/Object")) return true;
        if (!visited.add(source)) return false;
        if (source.startsWith("[")) {
            if (target.equals("java/lang/Cloneable") || target.equals("java/io/Serializable")) return true;
            if (!target.startsWith("[")) return false;
            String targetElement = component(target);
            String sourceElement = component(source);
            return targetElement != null && sourceElement != null
                && assignable(targetElement, sourceElement, new HashSet<>());
        }
        if (target.startsWith("[")) return false;
        ClassReader info = read(source);
        for (String contract : info.getInterfaces()) {
            if (assignable(target, contract, visited)) return true;
        }
        String parent = info.getSuperName();
        return parent != null && assignable(target, parent, visited);
    }

    private String component(String array) {
        String element = array.substring(1);
        if (element.startsWith("[")) return element;
        if (element.startsWith("L")) return element.substring(1, element.length() - 1);
        return null;
    }

    private ClassReader read(String name) {
        ClassReader cached = classes.get(name);
        if (cached != null) return cached;
        try (InputStream input = loader.getResourceAsStream(name + ".class")) {
            if (input == null) throw new IllegalStateException("Missing class hierarchy resource: " + name);
            ClassReader reader = new ClassReader(input);
            classes.put(name, reader);
            return reader;
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read class hierarchy: " + name, e);
        }
    }
}
