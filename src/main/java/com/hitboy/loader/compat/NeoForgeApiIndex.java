package com.hitboy.loader.compat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

/**
 * The public API of one NeoForge release (Minecraft as NeoForge patches it, NeoForge, FML, and the event
 * bus), read from {@code /porting/neoforge-<version>-api.txt.gz}. port.exe checks every NeoForge and
 * Minecraft class, method, and field a ported JAR uses against it.
 */
final class NeoForgeApiIndex {
    private static final class Entry {
        String superName;
        String[] interfaces = new String[0];
        final Set<String> methods = new HashSet<>();
        final Set<String> fields = new HashSet<>();
    }

    private final Map<String, Entry> classes = new HashMap<>();
    private final String minecraftVersion;

    private NeoForgeApiIndex(String minecraftVersion) {
        this.minecraftVersion = minecraftVersion;
    }

    static NeoForgeApiIndex load(String minecraftVersion) throws IOException {
        InputStream resource = NeoForgeApiIndex.class.getResourceAsStream("/porting/neoforge-" + minecraftVersion + "-api.txt.gz");
        if (resource == null) return null;
        NeoForgeApiIndex index = new NeoForgeApiIndex(minecraftVersion);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(resource), StandardCharsets.UTF_8))) {
            Entry current = null;
            for (String line; (line = reader.readLine()) != null; ) {
                String[] parts = line.split(" ");
                switch (parts[0]) {
                    case "C" -> {
                        current = new Entry();
                        current.superName = parts[2].equals("-") ? null : parts[2];
                        if (!parts[3].equals("-")) current.interfaces = parts[3].split(",");
                        index.classes.put(parts[1], current);
                    }
                    case "M" -> current.methods.add(parts[1] + parts[2]);
                    case "F" -> current.fields.add(parts[1]);
                    default -> { }
                }
            }
        }
        return index;
    }

    String minecraftVersion() {
        return minecraftVersion;
    }

    boolean hasClass(String internalName) {
        return classes.containsKey(internalName);
    }

    /** True when the method exists on the class or a super type (JDK types are checked too; other libraries count as a match). */
    boolean hasMethod(String owner, String name, String descriptor) {
        return find(owner, name + descriptor, true, new HashSet<>());
    }

    boolean hasField(String owner, String name) {
        return find(owner, name, false, new HashSet<>());
    }

    private static boolean jdkHas(String owner, String member, boolean method) {
        try {
            Class<?> type = Class.forName(owner.replace('/', '.'), false, null);
            if (!method) {
                for (java.lang.reflect.Field field : type.getFields()) if (field.getName().equals(member)) return true;
                for (java.lang.reflect.Field field : type.getDeclaredFields()) if (field.getName().equals(member)) return true;
                return false;
            }
            for (java.lang.reflect.Method candidate : type.getMethods()) {
                if ((candidate.getName() + org.objectweb.asm.Type.getMethodDescriptor(candidate)).equals(member)) return true;
            }
            for (java.lang.reflect.Method candidate : type.getDeclaredMethods()) {
                if ((candidate.getName() + org.objectweb.asm.Type.getMethodDescriptor(candidate)).equals(member)) return true;
            }
            for (java.lang.reflect.Constructor<?> constructor : type.getDeclaredConstructors()) {
                if (("<init>" + org.objectweb.asm.Type.getConstructorDescriptor(constructor)).equals(member)) return true;
            }
            return false;
        } catch (ClassNotFoundException | LinkageError unknown) {
            return true;
        }
    }

    private boolean find(String owner, String member, boolean method, Set<String> seen) {
        if (owner == null || !seen.add(owner)) return false;
        Entry entry = classes.get(owner);
        if (entry == null) {
            // JDK types are checked against the running JDK; other libraries cannot be checked.
            return owner.startsWith("java/") || owner.startsWith("javax/") ? jdkHas(owner, member, method) : true;
        }
        if (method ? entry.methods.contains(member) : entry.fields.contains(member)) return true;
        if (entry.superName != null && find(entry.superName, member, method, seen)) return true;
        for (String type : entry.interfaces) if (find(type, member, method, seen)) return true;
        return false;
    }
}
