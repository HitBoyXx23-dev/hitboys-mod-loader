package com.hitboy.loader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class OptionalMixinRuntime {
    public static final String ENABLED_PROPERTY = "hitboy.mixin.enabled";
    private static final java.util.Set<String> PUBLISHED = java.util.concurrent.ConcurrentHashMap.newKeySet();

    private OptionalMixinRuntime() {}

    public static void initialize(Path... modsDirectories) {
        List<String> configurations = new ArrayList<>();
        for (Path modsDirectory : modsDirectories) {
            if (modsDirectory != null) configurations.addAll(discoverConfigurations(modsDirectory));
        }
        if (configurations.isEmpty()) return;
        String gameVersion = System.getProperty("hitboy.game-version", "1.21.11");
        boolean unobfuscated = com.hitboy.loader.mixin.HitBoyIntermediaryRemapper.isUnobfuscated();
        if (!unobfuscated && OptionalMixinRuntime.class.getResource("/mappings/" + gameVersion + "-intermediary.tiny") == null) {
            System.out.println("Mixin mods are built for Minecraft 1.21.11; skipping their Mixins on " + gameVersion
                + ": " + configurations);
            return;
        }
        if ("false".equalsIgnoreCase(System.getProperty(ENABLED_PROPERTY))) {
            throw new IllegalStateException(
                "Mixins are required by installed mods but Mixin support was explicitly disabled. "
                    + "Configurations: " + configurations
            );
        }
        try {
            System.setProperty("mixin.service", "com.hitboy.loader.mixin.HitBoyMixinService");
            Class<?> bootstrap = Class.forName("org.spongepowered.asm.launch.MixinBootstrap");
            bootstrap.getMethod("init").invoke(null);
            org.spongepowered.asm.mixin.transformer.IMixinTransformer transformer =
                com.hitboy.loader.mixin.HitBoyMixinService.createTransformer();
            try {
                Class.forName("com.llamalad7.mixinextras.MixinExtrasBootstrap")
                    .getMethod("init").invoke(null);
            } catch (ClassNotFoundException ignored) {
            }
            org.spongepowered.asm.mixin.MixinEnvironment.getDefaultEnvironment().setSide(
                "SERVER".equals(System.getProperty("hitboy.environment"))
                    ? org.spongepowered.asm.mixin.MixinEnvironment.Side.SERVER
                    : org.spongepowered.asm.mixin.MixinEnvironment.Side.CLIENT);
            if (!unobfuscated) {
                // Obfuscated builds: translate intermediary names in Mixin targets. 26.x needs nothing.
                org.spongepowered.asm.mixin.MixinEnvironment.getDefaultEnvironment().getRemappers().add(
                    new com.hitboy.loader.mixin.HitBoyIntermediaryRemapper(
                        System.getProperty("hitboy.game-version", "1.21.11")));
            }
            java.lang.reflect.Method phase = org.spongepowered.asm.mixin.MixinEnvironment.class
                .getDeclaredMethod("gotoPhase", org.spongepowered.asm.mixin.MixinEnvironment.Phase.class);
            phase.setAccessible(true);
            phase.invoke(null, org.spongepowered.asm.mixin.MixinEnvironment.Phase.DEFAULT);
            Class<?> mixins = Class.forName("org.spongepowered.asm.mixin.Mixins");
            Class<?> source = Class.forName("org.spongepowered.asm.mixin.extensibility.IMixinConfigSource");
            mixins.getMethod("addConfigurations", String[].class, source)
                .invoke(null, configurations.toArray(String[]::new), null);
            GameAgent.registerTransformer(new java.lang.instrument.ClassFileTransformer() {
                @Override
                public byte[] transform(ClassLoader loader, String name, Class<?> redefining,
                    java.security.ProtectionDomain domain, byte[] bytes) {
                    if (name == null || name.startsWith("java/") || name.startsWith("jdk/")
                        || name.startsWith("org/spongepowered/") || name.startsWith("org/objectweb/asm/")
                        || (name.startsWith("com/hitboy/loader/") && !name.startsWith("com/hitboy/loader/fixtures/"))) return null;
                    try {
                        byte[] transformed = transformer.transformClassBytes(name.replace('/', '.'), name.replace('/', '.'), bytes);
                        publishGeneratedReferences(transformer, transformed);
                        return transformed;
                    } catch (Throwable failure) {
                        System.err.println("HitBoy Mixin transformation failed for " + name);
                        failure.printStackTrace();
                        return new byte[0];
                    }
                }
            });
            System.out.println("HitBoy optional Mixin runtime initialized with " + configurations.size() + " configurations.");
        } catch (ClassNotFoundException exception) {
            throw new IllegalStateException(
                "Mixin support is enabled, but the Sponge Mixin engine is not on the HitBoy classpath.", exception
            );
        } catch (ReflectiveOperationException exception) {
            Throwable cause = exception instanceof InvocationTargetException && exception.getCause() != null
                ? exception.getCause() : exception;
            throw new IllegalStateException("HitBoy could not initialize the optional Mixin runtime: " + cause, cause);
        }
    }

    private static void publishGeneratedReferences(
        org.spongepowered.asm.mixin.transformer.IMixinTransformer transformer, byte[] bytes) {
        if (bytes == null) return;
        java.util.Set<String> names = new java.util.HashSet<>();
        new org.objectweb.asm.ClassReader(bytes).accept(new org.objectweb.asm.ClassVisitor(org.objectweb.asm.Opcodes.ASM9) {
            @Override
            public org.objectweb.asm.MethodVisitor visitMethod(int access, String name, String descriptor,
                String signature, String[] exceptions) {
                return new org.objectweb.asm.MethodVisitor(org.objectweb.asm.Opcodes.ASM9) {
                    @Override public void visitTypeInsn(int opcode, String type) { collect(type); }
                    @Override public void visitFieldInsn(int opcode, String owner, String field, String desc) { collect(owner); }
                    @Override public void visitMethodInsn(int opcode, String owner, String method, String desc, boolean itf) { collect(owner); }
                    private void collect(String type) {
                        // Mixin generates its synthetic classes and copies of mixins' anonymous inner
                        // classes ("Target$Anonymous$<hash>") on demand; publish them before use.
                        if (type.startsWith("org/spongepowered/asm/synthetic/") || type.contains("$Anonymous$")) names.add(type);
                    }
                };
            }
        }, org.objectweb.asm.ClassReader.SKIP_DEBUG | org.objectweb.asm.ClassReader.SKIP_FRAMES);
        for (String name : names) {
            if (!PUBLISHED.add(name)) continue;
            byte[] generated = transformer.generateClass(
                org.spongepowered.asm.mixin.MixinEnvironment.getDefaultEnvironment(), name.replace('/', '.'));
            GameAgent.publishGeneratedClass(name, generated);
        }
    }

    public static List<String> discoverConfigurations(Path modsDirectory) {
        List<String> configurations = new ArrayList<>();
        if (!Files.isDirectory(modsDirectory)) return configurations;
        try (DirectoryStream<Path> jars = Files.newDirectoryStream(modsDirectory, "*.jar")) {
            List<Path> paths = new ArrayList<>();
            for (Path path : jars) if (!ActiveMods.isSkipped(path)) paths.add(path);
            paths.sort(Comparator.comparing(path -> path.getFileName().toString(), String.CASE_INSENSITIVE_ORDER));
            for (Path path : paths) readConfigurations(path, configurations);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not scan Mixin configurations in " + modsDirectory, exception);
        }
        return List.copyOf(configurations);
    }

    private static void readConfigurations(Path path, List<String> configurations) throws IOException {
        try (JarFile jar = new JarFile(path.toFile())) {
            JarEntry descriptor = jar.getJarEntry("hitboy.json");
            if (descriptor == null) return;
            try (InputStream input = jar.getInputStream(descriptor)) {
                JsonObject json = JsonParser.parseString(new String(input.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
                if (!json.has("mixins")) return;
                JsonElement mixins = json.get("mixins");
                if (mixins.isJsonPrimitive()) configurations.add(mixins.getAsString());
                if (mixins.isJsonArray()) {
                    for (JsonElement element : mixins.getAsJsonArray()) {
                        if (element.isJsonPrimitive()) configurations.add(element.getAsString());
                        if (element.isJsonObject() && element.getAsJsonObject().has("config")) {
                            configurations.add(element.getAsJsonObject().get("config").getAsString());
                        }
                    }
                }
            }
        }
    }
}
