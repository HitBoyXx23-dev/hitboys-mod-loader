package com.hitboy.loader.fabric;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hitboy.loader.ActiveMods;
import com.hitboy.loader.GameAgent;
import com.hitboy.loader.mixin.HitBoyIntermediaryRemapper;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.fabricmc.loader.api.ModContainer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Runs Fabric mods inside HitBoy's mixed-compatibility mode: finds them (including bundled
 * META-INF/jars), checks dependencies, remaps them to the running Minecraft names, and calls their
 * entrypoints at the same points Fabric Loader does. Mixins and access wideners are applied by
 * HitBoy's existing runtimes through the remapped JARs.
 */
public final class FabricRuntime implements FabricLoader {
    /** Mod ids HitBoy itself satisfies. */
    private static final Set<String> BUILT_IN = new HashSet<>(Arrays.asList("fabricloader", "fabric-loader", "minecraft", "java", "mixinextras"));

    private static final FabricRuntime INSTANCE = new FabricRuntime();

    private final Map<String, FabricMod> mods = new LinkedHashMap<>();
    private final List<Path> libraries = new ArrayList<>();
    private HitBoyIntermediaryRemapper mappings;
    private Path gameDirectory;
    private String[] launchArguments = new String[0];
    private boolean clientStarted;

    private FabricRuntime() {
    }

    public static FabricLoader loader() {
        return INSTANCE;
    }

    public static boolean enabled() {
        String env = System.getenv("HITBOY_MIXED_COMPATIBILITY");
        return Boolean.getBoolean("hitboy.mixed-compatibility") || "1".equals(env) || "true".equalsIgnoreCase(env);
    }

    /**
     * Converts the Fabric mods in {@code modsDirectory} and returns the folder holding their runtime
     * JARs, or null when there is nothing to run.
     */
    public static Path prepare(Path modsDirectory, String gameVersion, String[] arguments) {
        return INSTANCE.prepareMods(modsDirectory, gameVersion, arguments);
    }

    private Path prepareMods(Path modsDirectory, String gameVersion, String[] arguments) {
        launchArguments = arguments == null ? new String[0] : arguments.clone();
        gameDirectory = Path.of(System.getProperty("hitboy.game-directory", ".")).toAbsolutePath().normalize();
        List<FabricMod> found = new ArrayList<>();
        try (DirectoryStream<Path> jars = Files.newDirectoryStream(modsDirectory, "*.jar")) {
            for (Path jar : jars) {
                if (ActiveMods.isSkipped(jar)) continue;
                discover(jar, found, 0);
            }
        } catch (IOException exception) {
            System.err.println("[HitBoy Fabric] Could not scan " + modsDirectory + ": " + exception);
            return null;
        }
        if (found.isEmpty()) return null;

        boolean unobfuscated = HitBoyIntermediaryRemapper.isUnobfuscated();
        boolean hasMappings = HitBoyIntermediaryRemapper.class.getResource("/mappings/" + gameVersion + "-intermediary.tiny") != null;
        if (!hasMappings && !unobfuscated) {
            System.out.println("[HitBoy Fabric] No name mappings for Minecraft " + gameVersion + "; skipping "
                + found.size() + " Fabric mod(s).");
            return null;
        }
        // 26.x ships real names and its Fabric mods are built against them, so they run unchanged.
        mappings = unobfuscated ? HitBoyIntermediaryRemapper.identity() : new HitBoyIntermediaryRemapper(gameVersion);

        for (FabricMod mod : found) {
            if (!mod.isClientCompatible()) {
                System.out.println("[HitBoy Fabric] Skipping server-only mod " + mod);
                continue;
            }
            FabricMod existing = mods.get(mod.getId());
            if (existing != null && existing.getVersion().compareTo(mod.getVersion()) >= 0) continue;
            mods.put(mod.getId(), mod);
        }
        dropModsWithMissingDependencies();

        Path cache = Path.of(System.getProperty("hitboy.home", gameDirectory.toString()), "cache", "fabric", gameVersion);
        FabricJarRemapper remapper = new FabricJarRemapper(unobfuscated ? null : mappings);
        for (FabricMod mod : mods.values()) {
            try {
                Path output = cache.resolve(mod.getId() + "-" + hash(mod.sourceJar) + ".jar");
                if (!Files.isRegularFile(output)) remapper.remap(mod, output);
                mod.runtimeJar = output;
            } catch (Exception exception) {
                System.err.println("[HitBoy Fabric] Could not prepare " + mod + ": " + exception);
            }
        }
        mods.values().removeIf(mod -> mod.runtimeJar == null);
        // The cache folder is read as a whole, so remove copies of older or removed mods.
        Set<Path> current = new HashSet<>();
        for (FabricMod mod : mods.values()) current.add(mod.runtimeJar.toAbsolutePath().normalize());
        try (DirectoryStream<Path> cached = Files.newDirectoryStream(cache, "*.jar")) {
            for (Path jar : cached) if (!current.contains(jar.toAbsolutePath().normalize())) Files.deleteIfExists(jar);
        } catch (IOException exception) {
            System.err.println("[HitBoy Fabric] Could not clean " + cache + ": " + exception);
        }
        for (Path library : libraries) GameAgent.appendJar(library);
        // Fabric runs client entrypoints once the session is stored (mods read the signed-in user).
        if (unobfuscated) {
            GameAgent.registerTransformer(new ClientStartHook("net/minecraft/client/Minecraft", "user", "Lnet/minecraft/client/User;"));
        } else {
            String minecraft = mappings.map("net/minecraft/class_310");
            String sessionField = mappings.mapFieldName("net/minecraft/class_310", "field_1726", "Lnet/minecraft/class_320;");
            GameAgent.registerTransformer(new ClientStartHook(minecraft, sessionField, "L" + mappings.map("net/minecraft/class_320") + ";"));
        }
        System.out.println("[HitBoy Fabric] Running " + mods.size() + " Fabric mod(s): " + mods.values());
        return mods.isEmpty() ? null : cache;
    }

    private void discover(Path jar, List<FabricMod> found, int depth) {
        try (JarFile file = new JarFile(jar.toFile())) {
            JarEntry descriptor = file.getJarEntry("fabric.mod.json");
            if (descriptor == null || file.getJarEntry("hitboy.json") != null) {
                if (depth > 0) libraries.add(jar);
                return;
            }
            JsonObject json;
            try (InputStream input = file.getInputStream(descriptor)) {
                json = JsonParser.parseString(new String(input.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
            }
            found.add(new FabricMod(json, jar));
            if (!json.has("jars")) return;
            Path nestedDirectory = Path.of(System.getProperty("hitboy.home", "."), "cache", "fabric-nested",
                jar.getFileName().toString().replaceAll("[^A-Za-z0-9._-]", "_"));
            for (var nested : json.getAsJsonArray("jars")) {
                String path = nested.getAsJsonObject().get("file").getAsString();
                JarEntry entry = file.getJarEntry(path);
                if (entry == null) continue;
                Path extracted = nestedDirectory.resolve(Path.of(path).getFileName().toString().replaceAll("[^A-Za-z0-9._-]", "_"));
                if (!Files.isRegularFile(extracted) || Files.size(extracted) != entry.getSize()) {
                    Files.createDirectories(nestedDirectory);
                    try (InputStream input = file.getInputStream(entry)) {
                        Files.copy(input, extracted, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
                discover(extracted, found, depth + 1);
            }
        } catch (Exception exception) {
            System.err.println("[HitBoy Fabric] Could not read " + jar.getFileName() + ": " + exception);
        }
    }

    private void dropModsWithMissingDependencies() {
        boolean changed = true;
        while (changed) {
            changed = false;
            for (FabricMod mod : new ArrayList<>(mods.values())) {
                for (String dependency : mod.requiredDependencies()) {
                    if (BUILT_IN.contains(dependency) || isModLoaded(dependency)) continue;
                    System.err.println("[HitBoy Fabric] Skipping " + mod + ": it needs \"" + dependency
                        + "\", which is not installed" + (dependency.startsWith("fabric") ? " (Fabric API is not supported yet)" : "") + ".");
                    mods.remove(mod.getId());
                    changed = true;
                    break;
                }
            }
        }
    }

    private static String hash(Path jar) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.update(Files.readAllBytes(jar));
        digest.update("hitboy-fabric-remap-5".getBytes(StandardCharsets.UTF_8));
        StringBuilder text = new StringBuilder();
        for (byte value : digest.digest()) text.append(String.format("%02x", value));
        return text.substring(0, 12);
    }

    // ---- entrypoints ----

    /** Fabric's "preLaunch" entrypoints, just before Minecraft's main method. */
    public static void preLaunch() {
        INSTANCE.invoke("preLaunch", net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint.class,
            entry -> entry.onPreLaunch());
    }

    /** Fabric's "main" and "client" entrypoints, from the Minecraft client constructor. */
    public static void onClientStart() {
        if (INSTANCE.clientStarted) return;
        INSTANCE.clientStarted = true;
        INSTANCE.invoke("main", net.fabricmc.api.ModInitializer.class, entry -> entry.onInitialize());
        INSTANCE.invoke("client", net.fabricmc.api.ClientModInitializer.class, entry -> entry.onInitializeClient());
    }

    private interface Call<T> {
        void run(T entrypoint) throws Exception;
    }

    private <T> void invoke(String key, Class<T> type, Call<T> call) {
        for (FabricMod mod : mods.values()) {
            for (String reference : mod.entrypoints(key)) {
                try {
                    call.run(type.cast(instantiate(reference, type)));
                } catch (Throwable failure) {
                    System.err.println("[HitBoy Fabric] " + mod.getName() + " failed in its \"" + key + "\" entrypoint " + reference + ": " + failure);
                    failure.printStackTrace();
                }
            }
        }
    }

    /**
     * Creates an entrypoint the way Fabric does: "pkg.Class" (new instance), "pkg.Class::field" (a
     * static field), or "pkg.Class::method" (a method adapted to the entrypoint interface).
     */
    private static Object instantiate(String reference, Class<?> type) throws Exception {
        String className = reference;
        String member = null;
        int separator = reference.indexOf("::");
        if (separator >= 0) {
            className = reference.substring(0, separator);
            member = reference.substring(separator + 2);
        }
        Class<?> owner = Class.forName(className, true, ClassLoader.getSystemClassLoader());
        if (member == null) {
            var constructor = owner.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        }
        try {
            var field = owner.getDeclaredField(member);
            field.setAccessible(true);
            return field.get(null);
        } catch (NoSuchFieldException notAField) {
            // fall through to a method reference
        }
        java.lang.reflect.Method target = null;
        for (java.lang.reflect.Method method : owner.getDeclaredMethods()) {
            if (method.getName().equals(member)) {
                target = method;
                break;
            }
        }
        if (target == null) throw new NoSuchMethodException(reference);
        target.setAccessible(true);
        final java.lang.reflect.Method method = target;
        final Object receiver = java.lang.reflect.Modifier.isStatic(method.getModifiers()) ? null : instantiate(className, type);
        return java.lang.reflect.Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] {type}, (proxy, called, arguments) -> {
            if (called.getDeclaringClass() == Object.class) {
                switch (called.getName()) {
                    case "hashCode": return System.identityHashCode(proxy);
                    case "equals": return proxy == arguments[0];
                    default: return reference;
                }
            }
            if (called.isDefault()) return java.lang.reflect.InvocationHandler.invokeDefault(proxy, called, arguments);
            return method.invoke(receiver, arguments);
        });
    }

    private static final class Container<T> implements net.fabricmc.loader.api.entrypoint.EntrypointContainer<T> {
        private final T entrypoint;
        private final ModContainer provider;
        private final String definition;

        Container(T entrypoint, ModContainer provider, String definition) {
            this.entrypoint = entrypoint;
            this.provider = provider;
            this.definition = definition;
        }

        @Override public T getEntrypoint() { return entrypoint; }
        @Override public ModContainer getProvider() { return provider; }
        @Override public String getDefinition() { return definition; }
    }

    /** Calls {@link #onClientStart()} in the Minecraft client constructor, right after the session is stored. */
    private static final class ClientStartHook implements ClassFileTransformer {
        private final String minecraftClass;
        private final String sessionField;
        private final String sessionDescriptor;

        ClientStartHook(String minecraftClass, String sessionField, String sessionDescriptor) {
            this.minecraftClass = minecraftClass;
            this.sessionField = sessionField;
            this.sessionDescriptor = sessionDescriptor;
        }

        @Override
        public byte[] transform(ClassLoader loader, String name, Class<?> redefined, ProtectionDomain domain, byte[] bytes) {
            if (!minecraftClass.equals(name)) return null;
            ClassNode node = new ClassNode();
            new ClassReader(bytes).accept(node, 0);
            boolean hooked = false;
            for (MethodNode method : node.methods) {
                if (!method.name.equals("<init>")) continue;
                AbstractInsnNode after = find(method, Opcodes.PUTFIELD, sessionField, sessionDescriptor);
                if (after == null) after = find(method, Opcodes.PUTSTATIC, null, "L" + minecraftClass + ";");
                if (after == null) continue;
                method.instructions.insert(after, new MethodInsnNode(Opcodes.INVOKESTATIC,
                    "com/hitboy/loader/fabric/FabricRuntime", "onClientStart", "()V", false));
                hooked = true;
            }
            if (!hooked) return null;
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            node.accept(writer);
            System.out.println("[HitBoy Fabric] Hooked client start: " + name);
            return writer.toByteArray();
        }

        private AbstractInsnNode find(MethodNode method, int opcode, String field, String descriptor) {
            for (AbstractInsnNode instruction : method.instructions.toArray()) {
                if (instruction.getOpcode() != opcode) continue;
                FieldInsnNode access = (FieldInsnNode) instruction;
                if (access.owner.equals(minecraftClass) && access.desc.equals(descriptor) && (field == null || access.name.equals(field))) {
                    return instruction;
                }
            }
            return null;
        }
    }

    // ---- FabricLoader ----

    @Override
    public <T> List<T> getEntrypoints(String key, Class<T> type) {
        List<T> entrypoints = new ArrayList<>();
        for (FabricMod mod : mods.values()) {
            for (String reference : mod.entrypoints(key)) {
                try {
                    entrypoints.add(type.cast(instantiate(reference, type)));
                } catch (Exception exception) {
                    System.err.println("[HitBoy Fabric] Could not create entrypoint " + reference + ": " + exception);
                }
            }
        }
        return entrypoints;
    }

    @Override
    public <T> List<net.fabricmc.loader.api.entrypoint.EntrypointContainer<T>> getEntrypointContainers(String key, Class<T> type) {
        List<net.fabricmc.loader.api.entrypoint.EntrypointContainer<T>> containers = new ArrayList<>();
        for (FabricMod mod : mods.values()) {
            for (String reference : mod.entrypoints(key)) {
                try {
                    containers.add(new Container<>(type.cast(instantiate(reference, type)), mod, reference));
                } catch (Exception exception) {
                    System.err.println("[HitBoy Fabric] Could not create entrypoint " + reference + ": " + exception);
                }
            }
        }
        return containers;
    }

    @Override
    public Optional<ModContainer> getModContainer(String id) {
        FabricMod mod = mods.get(id);
        if (mod != null) return Optional.of(mod);
        for (FabricMod candidate : mods.values()) if (candidate.getProvides().contains(id)) return Optional.of(candidate);
        return Optional.empty();
    }

    @Override public Collection<ModContainer> getAllMods() { return Collections.unmodifiableCollection(new ArrayList<>(mods.values())); }
    @Override public boolean isModLoaded(String id) { return getModContainer(id).isPresent(); }
    @Override public boolean isDevelopmentEnvironment() { return false; }
    @Override public EnvType getEnvironmentType() { return EnvType.CLIENT; }
    @Override public Object getGameInstance() { return com.hitboy.loader.GameContext.getMinecraft(); }
    @Override public MappingResolver getMappingResolver() { return resolver; }
    @Override public Path getGameDir() { return gameDirectory; }

    @Override
    public Path getConfigDir() {
        Path config = gameDirectory.resolve("config");
        try {
            Files.createDirectories(config);
        } catch (IOException ignored) {
            // Fabric mods create their own files; a missing folder surfaces as their own error
        }
        return config;
    }

    @Override public String[] getLaunchArguments(boolean sanitize) { return launchArguments.clone(); }

    /** HitBoy runs mods in Minecraft's own names, which Fabric calls the "official" namespace. */
    private final MappingResolver resolver = new MappingResolver() {
        @Override public Collection<String> getNamespaces() { return Arrays.asList("intermediary", "official"); }
        @Override public String getCurrentRuntimeNamespace() { return "official"; }

        @Override
        public String mapClassName(String namespace, String className) {
            if (!"intermediary".equals(namespace) || mappings == null) return className;
            return mappings.map(className.replace('.', '/')).replace('/', '.');
        }

        @Override
        public String unmapClassName(String targetNamespace, String className) {
            if (!"intermediary".equals(targetNamespace) || mappings == null) return className;
            return mappings.unmap(className.replace('.', '/')).replace('/', '.');
        }

        @Override
        public String mapFieldName(String namespace, String owner, String name, String descriptor) {
            if (!"intermediary".equals(namespace) || mappings == null) return name;
            return mappings.mapFieldName(owner.replace('.', '/'), name, descriptor);
        }

        @Override
        public String mapMethodName(String namespace, String owner, String name, String descriptor) {
            if (!"intermediary".equals(namespace) || mappings == null) return name;
            return mappings.mapMethodName(owner.replace('.', '/'), name, descriptor);
        }
    };
}
