package com.hitboy.loader;

import com.hitboy.loader.mixin.HitBoyIntermediaryRemapper;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.instrument.ClassFileTransformer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public final class OptionalAccessWidenerRuntime {
    private OptionalAccessWidenerRuntime() {}

    public static void initialize(Path... modsDirectories) {
        for (Path modsDirectory : modsDirectories) initializeOne(modsDirectory);
    }

    private static void initializeOne(Path modsDirectory) {
        if (modsDirectory == null || !Files.isDirectory(modsDirectory)) return;
        // Created only when a mod ships an access widener: the mappings exist for 1.21.11 only, and
        // building them eagerly crashed every other version at startup even with no such mods.
        LazyRemapper remapper = new LazyRemapper(System.getProperty("hitboy.game-version", "1.21.11"));
        AccessRules rules = new AccessRules();
        try (var paths = Files.list(modsDirectory)) {
            for (Path path : paths.filter(value -> value.toString().endsWith(".jar") && !ActiveMods.isSkipped(value)).toList()) readJar(path, remapper, rules);
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load mod access wideners", exception);
        }
        if (rules.classes.isEmpty()) return;
        GameAgent.registerTransformer(new AccessTransformer(rules));
        System.out.println("HitBoy access widener registered for " + rules.classes.size() + " Minecraft classes");
    }

    private static void readJar(Path path, LazyRemapper remapper, AccessRules rules) {
        try (JarFile jar = new JarFile(path.toFile())) {
            if (jar.getJarEntry("hitboy.json") == null) return;
            for (JarEntry entry : jar.stream().filter(value -> value.getName().endsWith(".accesswidener")).toList()) {
                HitBoyIntermediaryRemapper mappings = remapper.get();
                if (mappings == null) {
                    System.out.println("Skipping access widener " + entry.getName() + " from " + path.getFileName()
                        + ": no HitBoy mappings for Minecraft " + remapper.gameVersion);
                    continue;
                }
                try (InputStream input = jar.getInputStream(entry)) {
                    read(input, mappings, rules);
                    System.out.println("Loaded access widener " + entry.getName() + " from " + path.getFileName());
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not read access widener from " + path.getFileName(), exception);
        }
    }

    private static final class LazyRemapper {
        final String gameVersion;
        private HitBoyIntermediaryRemapper remapper;
        private boolean attempted;

        LazyRemapper(String gameVersion) {
            this.gameVersion = gameVersion;
        }

        /** The remapper, or null when this Minecraft version has no bundled mappings. */
        HitBoyIntermediaryRemapper get() {
            if (!attempted) {
                attempted = true;
                if (HitBoyIntermediaryRemapper.class.getResource("/mappings/" + gameVersion + "-intermediary.tiny") != null) {
                    remapper = new HitBoyIntermediaryRemapper(gameVersion);
                }
            }
            return remapper;
        }
    }

    static void read(InputStream input, HitBoyIntermediaryRemapper remapper, AccessRules rules) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            String header = reader.readLine();
            String normalized = header == null ? "" : header.replace('\t', ' ').trim();
            // Fabric's newer "classTweaker" format is a superset of access wideners.
            if (!normalized.startsWith("accessWidener ") && !normalized.startsWith("classTweaker ")) {
                throw new IllegalArgumentException("Invalid access widener header");
            }
            String line;
            while ((line = reader.readLine()) != null) {
                int marker = line.indexOf('#');
                if (marker >= 0) line = line.substring(0, marker);
                line = line.trim();
                if (!line.isEmpty()) addDeclaration(line.split("\\s+"), remapper, rules);
            }
        }
    }

    private static void addDeclaration(String[] values, HitBoyIntermediaryRemapper remapper, AccessRules rules) {
        if (values.length < 3) throw new IllegalArgumentException("Invalid access widener declaration");
        String operation = values[0];
        // "transitive-accessible" and friends behave like the plain operation at runtime.
        if (operation.startsWith("transitive-")) operation = operation.substring("transitive-".length());
        // Class-tweaker-only directives (such as inject-interface) change nothing HitBoy needs at runtime.
        if (!operation.equals("accessible") && !operation.equals("extendable") && !operation.equals("mutable")) return;
        String kind = values[1];
        String owner = remapper.map(values[2]);
        ClassRule classRule = rules.classes.computeIfAbsent(owner, ignored -> new ClassRule());
        if ("class".equals(kind)) {
            classRule.apply(operation);
            return;
        }
        if (values.length < 5) throw new IllegalArgumentException("Invalid access widener member declaration");
        String descriptor = remapper.mapDesc(values[4]);
        if ("field".equals(kind)) {
            String name = remapper.mapFieldName(owner, values[3], descriptor);
            classRule.fields.computeIfAbsent(key(name, descriptor), ignored -> new MemberRule()).apply(operation);
        } else if ("method".equals(kind)) {
            String name = remapper.mapMethodName(owner, values[3], descriptor);
            classRule.methods.computeIfAbsent(key(name, descriptor), ignored -> new MemberRule()).apply(operation);
        }
    }

    private static String key(String name, String descriptor) {
        return name + '\u0000' + descriptor;
    }

    static final class AccessRules {
        private final Map<String, ClassRule> classes = new HashMap<>();
    }

    static final class ClassRule extends MemberRule {
        private final Map<String, MemberRule> fields = new HashMap<>();
        private final Map<String, MemberRule> methods = new HashMap<>();
    }

    static class MemberRule {
        private final Set<String> operations = new HashSet<>();

        void apply(String operation) {
            operations.add(operation);
        }

        int transform(int access) {
            if (operations.contains("accessible") || operations.contains("extendable")) {
                access = (access & ~(Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED)) | Opcodes.ACC_PUBLIC;
            }
            if (operations.contains("mutable") || operations.contains("extendable")) access &= ~Opcodes.ACC_FINAL;
            return access;
        }
    }

    private static final class AccessTransformer implements ClassFileTransformer {
        private final AccessRules rules;

        private AccessTransformer(AccessRules rules) {
            this.rules = rules;
        }

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> type, ProtectionDomain domain, byte[] bytes) {
            ClassRule rule = rules.classes.get(className);
            if (rule == null) return null;
            ClassNode node = new ClassNode();
            new ClassReader(bytes).accept(node, 0);
            node.access = rule.transform(node.access);
            for (FieldNode field : node.fields) {
                MemberRule member = rule.fields.get(key(field.name, field.desc));
                if (member != null) field.access = member.transform(field.access);
            }
            for (MethodNode method : node.methods) {
                MemberRule member = rule.methods.get(key(method.name, method.desc));
                if (member != null) method.access = member.transform(method.access);
            }
            ClassWriter writer = new ClassWriter(0);
            node.accept(writer);
            return writer.toByteArray();
        }
    }
}
