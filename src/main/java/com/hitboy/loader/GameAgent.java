package com.hitboy.loader;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import java.lang.instrument.*;
import java.security.ProtectionDomain;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class GameAgent {
    private static Instrumentation instrumentation;

    public static void registerTransformer(ClassFileTransformer transformer) {
        if (instrumentation == null) throw new IllegalStateException("HitBoy agent must start before installing transformations.");
        instrumentation.addTransformer(transformer);
    }

    /**
     * The official Minecraft Launcher only puts its own libraries on the classpath, so enabled HitBoy mod
     * JARs are appended here; mixin configs and mod classes must be visible to the game class loader.
     */
    public static synchronized void appendHitBoyModsToClasspath(Path modsDirectory) {
        if (instrumentation == null || !Files.isDirectory(modsDirectory)) return;
        String classPath = System.getProperty("java.class.path", "");
        try (java.nio.file.DirectoryStream<Path> jars = Files.newDirectoryStream(modsDirectory, "*.jar")) {
            for (Path jar : jars) {
                String stem = jar.getFileName().toString().replaceFirst("\\.jar$", "");
                if (Files.exists(modsDirectory.resolve(stem + ".disabled"))) continue;
                if (classPath.contains(jar.toAbsolutePath().toString())) continue;
                JarFile file = new JarFile(jar.toFile());
                if (file.getJarEntry("hitboy.json") == null) {
                    file.close();
                    continue;
                }
                instrumentation.appendToSystemClassLoaderSearch(file);
                appendNestedJars(jar, stem);
            }
        } catch (java.io.IOException exception) {
            throw new IllegalStateException("Could not add HitBoy mods from " + modsDirectory + " to the classpath", exception);
        }
    }

    /** Mods such as Meteor bundle their libraries as META-INF/jars/*.jar; those must be on the classpath too. */
    private static void appendNestedJars(Path modJar, String stem) throws java.io.IOException {
        Path destination = Path.of(System.getProperty("hitboy.home", System.getProperty("hitboy.game-directory", ".")),
            "cache", "nested", stem);
        try (JarFile outer = new JarFile(modJar.toFile())) {
            java.util.Enumeration<JarEntry> entries = outer.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.isDirectory() || !entry.getName().startsWith("META-INF/jars/") || !entry.getName().endsWith(".jar")) continue;
                // The agent classpath API rejects some characters (such as '+') in file names.
                String safeName = Path.of(entry.getName()).getFileName().toString().replaceAll("[^A-Za-z0-9._-]", "_");
                Path output = destination.resolve(safeName);
                if (!Files.isRegularFile(output) || Files.size(output) != entry.getSize()) {
                    Files.createDirectories(destination);
                    try (java.io.InputStream input = outer.getInputStream(entry)) {
                        Files.copy(input, output, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    }
                }
                try {
                    instrumentation.appendToSystemClassLoaderSearch(new JarFile(output.toFile()));
                } catch (IllegalArgumentException rejected) {
                    System.err.println("HitBoy could not add bundled library " + output + " to the classpath");
                }
            }
        }
    }

    public static synchronized void publishGeneratedClass(String internalName, byte[] bytes) {
        if (instrumentation == null || bytes == null || bytes.length == 0) return;
        try {
            Path directory = Path.of(System.getProperty("hitboy.game-directory", "."), "cache", "generated-mixins");
            Files.createDirectories(directory);
            Path jar = directory.resolve(internalName.replace('/', '_') + ".jar");
            try (JarOutputStream output = new JarOutputStream(Files.newOutputStream(jar))) {
                output.putNextEntry(new JarEntry(internalName + ".class"));
                output.write(bytes);
                output.closeEntry();
            }
            instrumentation.appendToSystemClassLoaderSearch(new JarFile(jar.toFile()));
        } catch (Exception exception) {
            throw new IllegalStateException("Could not publish generated Mixin class " + internalName, exception);
        }
    }

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
        System.out.println("HitBoy's Mod Loader agent invoked args=" + args);
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> c, ProtectionDomain pd, byte[] buf) {
                if (className == null) return null;
                String dotted = className.replace('/', '.');
                if (dotted.startsWith("net.minecraft")) {
                    try { return transformMinecraftClass(dotted, buf, loader); } catch (Exception e) { e.printStackTrace(); }
                }
                if ("enn".equals(className)) {
                    try { return transformObfuscatedMinecraft1201(buf, loader); } catch (Exception e) { e.printStackTrace(); }
                }
                if ("gfj".equals(className)) {
                    try { return transformObfuscatedMinecraft12111(buf, loader); } catch (Exception e) { e.printStackTrace(); }
                }
                if ("djz".equals(className)) {
                    try { return transformObfuscatedMinecraft165(buf, loader); } catch (Exception e) { e.printStackTrace(); }
                }
                if ("giq".equals(className)) {
                    try { return transformObfuscatedHud12111(buf, loader); } catch (Exception e) { e.printStackTrace(); }
                }
                if ("gfk".equals(className)) {
                    try { return transformObfuscatedMouse12111(buf, loader); } catch (Exception e) { e.printStackTrace(); }
                }
                if ("gfi".equals(className)) {
                    try { return transformObfuscatedKeyboard12111(buf, loader); } catch (Exception e) { e.printStackTrace(); }
                }
                if ("eow".equals(className)) {
                    try { return transformObfuscatedHud1201(buf, loader); } catch (Exception e) { e.printStackTrace(); }
                }
                if ("dkv".equals(className)) {
                    try { return transformObfuscatedHud165(buf, loader); } catch (Exception e) { e.printStackTrace(); }
                }
                if ("gsd".equals(className) || isTitleMenuClass(buf)) {
                    try { return transformTitleMenuClass(dotted, buf, loader); } catch (Exception e) { e.printStackTrace(); }
                }
                if ("com/mojang/blaze3d/pipeline/RenderPipeline$Builder".equals(className)) {
                    return makeRenderPipelineBuilderAccessible(buf);
                }
                byte[] transformed = com.hitboy.loader.api.HitBoyPlatform.transformBytecode(dotted, buf);
                return transformed == buf ? null : transformed;
            }
        });
        System.out.println("HitBoy's Mod Loader transformers registered");
    }

    private static byte[] makeRenderPipelineBuilderAccessible(byte[] bytes) {
        ClassNode node = new ClassNode();
        new ClassReader(bytes).accept(node, 0);
        node.access = publicAccess(node.access);
        for (MethodNode method : node.methods) {
            if ("<init>".equals(method.name) || "withSnippet".equals(method.name)) {
                method.access = publicAccess(method.access);
            }
        }
        ClassWriter writer = new ClassWriter(0);
        node.accept(writer);
        return writer.toByteArray();
    }

    private static int publicAccess(int access) {
        return (access & ~(Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED)) | Opcodes.ACC_PUBLIC;
    }
    private static byte[] transformObfuscatedMouse12111(byte[] buf, ClassLoader loader) {
        ClassReader reader = new ClassReader(buf);
        ClassNode node = new ClassNode();
        reader.accept(node, 0);
        boolean changed = false;
        for (MethodNode method : node.methods) {
            if (!method.name.equals("a") || (!method.desc.equals("(D)V")
                && !method.desc.equals("(JDD)V") && !method.desc.equals("(JIII)V"))) {
                continue;
            }
            LabelNode continueLabel = new LabelNode();
            InsnList guard = new InsnList();
            if (method.desc.equals("(JIII)V")) {
                guard.add(new VarInsnNode(Opcodes.ILOAD, 4));
                guard.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/hitboy/loader/MinecraftHooks",
                    "shouldBlockMouseButton", "(I)Z", false));
            } else {
                guard.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/hitboy/loader/MinecraftHooks",
                    "shouldBlockMouseLook", "()Z", false));
            }
            guard.add(new JumpInsnNode(Opcodes.IFEQ, continueLabel));
            guard.add(new InsnNode(Opcodes.RETURN));
            guard.add(continueLabel);
            guard.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
            method.instructions.insertBefore(method.instructions.getFirst(), guard);
            changed = true;
            System.out.println("Hooked 1.21.11 modal mouse guard: gfk#a" + method.desc);
        }
        if (!changed) return null;
        ClassWriter writer = createClassWriter(reader, loader);
        node.accept(writer);
        return writer.toByteArray();
    }
    private static byte[] transformObfuscatedKeyboard12111(byte[] buf, ClassLoader loader) {
        ClassReader reader = new ClassReader(buf);
        ClassNode node = new ClassNode();
        reader.accept(node, 0);
        for (MethodNode method : node.methods) {
            if (!method.name.equals("a") || !method.desc.equals("(JILgzb;)V")) {
                continue;
            }
            InsnList hook = new InsnList();
            hook.add(new VarInsnNode(Opcodes.LLOAD, 1));
            hook.add(new VarInsnNode(Opcodes.ILOAD, 3));
            hook.add(new VarInsnNode(Opcodes.ALOAD, 4));
            hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/hitboy/loader/MinecraftHooks",
                "onKeyInput", "(JILjava/lang/Object;)V", false));
            LabelNode continueLabel = new LabelNode();
            hook.add(new VarInsnNode(Opcodes.ILOAD, 3));
            hook.add(new VarInsnNode(Opcodes.ALOAD, 4));
            hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/hitboy/loader/MinecraftHooks",
                "shouldBlockKeyInput", "(ILjava/lang/Object;)Z", false));
            hook.add(new JumpInsnNode(Opcodes.IFEQ, continueLabel));
            hook.add(new InsnNode(Opcodes.RETURN));
            hook.add(continueLabel);
            hook.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
            method.instructions.insertBefore(method.instructions.getFirst(), hook);
            ClassWriter writer = createClassWriter(reader, loader);
            node.accept(writer);
            System.out.println("Hooked 1.21.11 keyboard input: gfi#a(JILgzb;)V");
            return writer.toByteArray();
        }
        return null;
    }
    private static byte[] transformMinecraftClass(String cn, byte[] buf, ClassLoader loader) {
        try {
            ClassReader cr = new ClassReader(buf);
            ClassNode node = new ClassNode();
            cr.accept(node, 0);
            hookGameLoop(node);
            hookRender(node);
            hookKey(node);
            hookTitleScreen(node, false);
            hookClientBrand(node);
            if ("net/minecraft/client/gui/screens/TitleScreen".equals(node.name) && replaceTitleBranding(node)) {
                System.out.println("Replaced title branding: " + node.name);
            }
            ClassWriter cw = createClassWriter(cr, loader);
            node.accept(cw);
            return cw.toByteArray();
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
    /**
     * Reports the client brand "hitboy" (as Fabric reports "fabric"), so Minecraft treats the game as
     * modded and the title screen shows "Minecraft <version>/HitBoy's Mod Loader" even with no mods.
     */
    private static void hookClientBrand(ClassNode node) {
        if (!"net/minecraft/client/ClientBrandRetriever".equals(node.name)) return;
        for (MethodNode method : node.methods) {
            if (!"getClientModName".equals(method.name) || !"()Ljava/lang/String;".equals(method.desc)) continue;
            method.instructions.clear();
            method.tryCatchBlocks.clear();
            if (method.localVariables != null) method.localVariables.clear();
            method.instructions.add(new LdcInsnNode("hitboy"));
            method.instructions.add(new InsnNode(Opcodes.ARETURN));
            System.out.println("Set client brand: hitboy");
        }
    }
    private static byte[] transformTitleMenuClass(String cn, byte[] buf, ClassLoader loader) {
        ClassReader cr = new ClassReader(buf);
        ClassNode node = new ClassNode();
        cr.accept(node, 0);
        if (!hookTitleScreen(node, true)) return null;
        ClassWriter cw = createClassWriter(cr, loader);
        node.accept(cw);
        System.out.println("Hooked obfuscated title menu: " + cn);
        return cw.toByteArray();
    }
    private static byte[] transformObfuscatedMinecraft1201(byte[] buf, ClassLoader loader) {
        ClassReader cr = new ClassReader(buf);
        ClassNode node = new ClassNode();
        cr.accept(node, 0);
        for (MethodNode method : node.methods) {
            if (!method.name.equals("s") || !method.desc.equals("()V")) {
                continue;
            }
            InsnList hook = new InsnList();
            hook.add(new VarInsnNode(Opcodes.ALOAD, 0));
            hook.add(new MethodInsnNode(
                Opcodes.INVOKESTATIC,
                "com/hitboy/loader/MinecraftHooks",
                "onRenderTick",
                "(Ljava/lang/Object;)V",
                false
            ));
            method.instructions.insertBefore(method.instructions.getFirst(), hook);
            ClassWriter cw = createClassWriter(cr, loader);
            node.accept(cw);
            System.out.println("Hooked 1.20.1 client tick: enn#s");
            return cw.toByteArray();
        }
        return null;
    }
    private static byte[] transformObfuscatedMinecraft12111(byte[] buf, ClassLoader loader) {
        ClassReader cr = new ClassReader(buf);
        ClassNode node = new ClassNode();
        cr.accept(node, 0);
        for (MethodNode method : node.methods) {
            if (!method.name.equals("x") || !method.desc.equals("()V")) {
                continue;
            }
            InsnList hook = new InsnList();
            hook.add(new VarInsnNode(Opcodes.ALOAD, 0));
            hook.add(new MethodInsnNode(
                Opcodes.INVOKESTATIC,
                "com/hitboy/loader/MinecraftHooks",
                "onRenderTick",
                "(Ljava/lang/Object;)V",
                false
            ));
            method.instructions.insertBefore(method.instructions.getFirst(), hook);
            ClassWriter cw = createClassWriter(cr, loader);
            node.accept(cw);
            System.out.println("Hooked 1.21.11 client tick: gfj#x");
            return cw.toByteArray();
        }
        return null;
    }
    // Mapping verified against Mojang's official 1.16.5 client mappings
    // (net.minecraft.client.Minecraft -> djz, tick() -> q). See MOD_API.md
    // for the version-support matrix.
    private static byte[] transformObfuscatedMinecraft165(byte[] buf, ClassLoader loader) {
        ClassReader cr = new ClassReader(buf);
        ClassNode node = new ClassNode();
        cr.accept(node, 0);
        for (MethodNode method : node.methods) {
            if (!method.name.equals("q") || !method.desc.equals("()V")) {
                continue;
            }
            InsnList hook = new InsnList();
            hook.add(new VarInsnNode(Opcodes.ALOAD, 0));
            hook.add(new MethodInsnNode(
                Opcodes.INVOKESTATIC,
                "com/hitboy/loader/MinecraftHooks",
                "onRenderTick",
                "(Ljava/lang/Object;)V",
                false
            ));
            method.instructions.insertBefore(method.instructions.getFirst(), hook);
            ClassWriter cw = createClassWriter(cr, loader);
            node.accept(cw);
            System.out.println("Hooked 1.16.5 client tick: djz#q");
            return cw.toByteArray();
        }
        return null;
    }
    private static byte[] transformObfuscatedHud12111(byte[] buf, ClassLoader loader) {
        ClassReader cr = new ClassReader(buf);
        ClassNode node = new ClassNode();
        cr.accept(node, 0);
        for (MethodNode method : node.methods) {
            if (!method.name.equals("a") || !method.desc.equals("(Lgir;Lgez;)V")) {
                continue;
            }
            for (AbstractInsnNode instruction : method.instructions.toArray()) {
                if (instruction.getOpcode() != Opcodes.RETURN) {
                    continue;
                }
                InsnList hook = new InsnList();
                hook.add(new VarInsnNode(Opcodes.ALOAD, 1));
                hook.add(new MethodInsnNode(
                    Opcodes.INVOKESTATIC,
                    "com/hitboy/loader/MinecraftHooks",
                    "onHudRender",
                    "(Ljava/lang/Object;)V",
                    false
                ));
                method.instructions.insertBefore(instruction, hook);
            }
            ClassWriter cw = createClassWriter(cr, loader);
            node.accept(cw);
            System.out.println("Hooked 1.21.11 HUD render: giq#a");
            return cw.toByteArray();
        }
        return null;
    }
    // Mapping verified against Mojang's official 1.20.1 client mappings
    // (net.minecraft.client.gui.Gui -> eow, GuiGraphics -> eox,
    // render(GuiGraphics,float) -> a).
    private static byte[] transformObfuscatedHud1201(byte[] buf, ClassLoader loader) {
        ClassReader cr = new ClassReader(buf);
        ClassNode node = new ClassNode();
        cr.accept(node, 0);
        for (MethodNode method : node.methods) {
            if (!method.name.equals("a") || !method.desc.equals("(Leox;F)V")) {
                continue;
            }
            for (AbstractInsnNode instruction : method.instructions.toArray()) {
                if (instruction.getOpcode() != Opcodes.RETURN) {
                    continue;
                }
                InsnList hook = new InsnList();
                hook.add(new VarInsnNode(Opcodes.ALOAD, 1));
                hook.add(new MethodInsnNode(
                    Opcodes.INVOKESTATIC,
                    "com/hitboy/loader/MinecraftHooks",
                    "onHudRender",
                    "(Ljava/lang/Object;)V",
                    false
                ));
                method.instructions.insertBefore(instruction, hook);
            }
            ClassWriter cw = createClassWriter(cr, loader);
            node.accept(cw);
            System.out.println("Hooked 1.20.1 HUD render: eow#a");
            return cw.toByteArray();
        }
        return null;
    }
    // Mapping verified against Mojang's official 1.16.5 client mappings
    // (net.minecraft.client.gui.Gui -> dkv, render(PoseStack,float) -> a).
    private static byte[] transformObfuscatedHud165(byte[] buf, ClassLoader loader) {
        ClassReader cr = new ClassReader(buf);
        ClassNode node = new ClassNode();
        cr.accept(node, 0);
        for (MethodNode method : node.methods) {
            if (!method.name.equals("a")
                || !method.desc.equals("(Lcom/mojang/blaze3d/vertex/PoseStack;F)V")) {
                continue;
            }
            for (AbstractInsnNode instruction : method.instructions.toArray()) {
                if (instruction.getOpcode() != Opcodes.RETURN) {
                    continue;
                }
                InsnList hook = new InsnList();
                hook.add(new VarInsnNode(Opcodes.ALOAD, 1));
                hook.add(new MethodInsnNode(
                    Opcodes.INVOKESTATIC,
                    "com/hitboy/loader/MinecraftHooks",
                    "onHudRender165",
                    "(Ljava/lang/Object;)V",
                    false
                ));
                method.instructions.insertBefore(instruction, hook);
            }
            ClassWriter cw = createClassWriter(cr, loader);
            node.accept(cw);
            System.out.println("Hooked 1.16.5 HUD render: dkv#a");
            return cw.toByteArray();
        }
        return null;
    }
    private static ClassWriter createClassWriter(ClassReader reader, ClassLoader minecraftLoader) {
        ClassLoader resolver = minecraftLoader == null ? GameAgent.class.getClassLoader() : minecraftLoader;
        return new ResourceClassWriter(reader, resolver);
    }
    private static boolean isTitleMenuClass(byte[] buf) {
        final boolean[] foundSingleplayer = {false};
        final boolean[] foundMultiplayer = {false};
        new ClassReader(buf).accept(new ClassVisitor(Opcodes.ASM9) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor delegate = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new MethodVisitor(Opcodes.ASM9, delegate) {
                    @Override
                    public void visitLdcInsn(Object value) {
                        if ("menu.singleplayer".equals(value)) foundSingleplayer[0] = true;
                        if ("menu.multiplayer".equals(value)) foundMultiplayer[0] = true;
                        super.visitLdcInsn(value);
                    }
                };
            }
        }, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        return foundSingleplayer[0] && foundMultiplayer[0];
    }
    private static void hookGameLoop(ClassNode cn) {
        for (MethodNode mn : cn.methods) {
            if ((mn.access & (Opcodes.ACC_ABSTRACT | Opcodes.ACC_NATIVE)) != 0) continue; // no body to patch (26.x interfaces)
            // Only the client's own loop. Matching every tick/update/run broke unobfuscated 26.x, where those
            // names exist on hundreds of classes, including static methods.
            boolean clientTick = cn.name.equals("net/minecraft/client/Minecraft") && mn.name.equals("tick") && mn.desc.equals("()V");
            boolean mainThread = cn.name.startsWith("net/minecraft/client/main/Main$") && mn.name.equals("run");
            if ((clientTick || mainThread) && (mn.access & Opcodes.ACC_STATIC) == 0) {
                InsnList hook = new InsnList();
                hook.add(new VarInsnNode(Opcodes.ALOAD, 0));
                hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/hitboy/loader/MinecraftHooks", "onGameLoopStart", "(Ljava/lang/Object;)V", false));
                if (mn.instructions.getFirst() != null) mn.instructions.insertBefore(mn.instructions.getFirst(), hook);
                else mn.instructions.add(hook);
                System.out.println("Hooked loop: " + cn.name + "#" + mn.name);
                return;
            }
        }
    }
    private static void hookRender(ClassNode cn) {
        for (MethodNode mn : cn.methods) {
            if ((mn.access & (Opcodes.ACC_ABSTRACT | Opcodes.ACC_NATIVE)) != 0) continue; // no body to patch (26.x interfaces)
            boolean isRender = mn.name.equals("render") || mn.name.equals("draw") || mn.name.equals("func_71411_J");
            if (!isRender || (mn.access & Opcodes.ACC_STATIC) != 0) continue;
            if (cn.name.equals("net/minecraft/client/Minecraft") && (mn.desc.equals("(Z)V") || mn.desc.equals("()V") || mn.desc.equals("(F)V") || mn.desc.equals("(FJ)V"))) {
                InsnList hook = new InsnList();
                hook.add(new VarInsnNode(Opcodes.ALOAD, 0));
                hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/hitboy/loader/MinecraftHooks", "onRenderTick", "(Ljava/lang/Object;)V", false));
                if (mn.instructions.getFirst() != null) mn.instructions.insertBefore(mn.instructions.getFirst(), hook);
                else mn.instructions.add(hook);
                System.out.println("Hooked render: " + cn.name + "#" + mn.name + mn.desc);
                return;
            }
        }
    }
    private static void hookKey(ClassNode cn) {
        for (MethodNode mn : cn.methods) {
            if ((mn.access & (Opcodes.ACC_ABSTRACT | Opcodes.ACC_NATIVE)) != 0) continue; // no body to patch (26.x interfaces)
            String n = mn.name.toLowerCase();
            if ((n.equals("keypress") || n.equals("onkey") || n.equals("keypressed")) && cn.name.equals("net/minecraft/client/Minecraft")) {
                InsnList hook = new InsnList();
                if (mn.desc.contains("Ljava/lang/String")) {
                    hook.add(new VarInsnNode(Opcodes.ALOAD, 1));
                } else if (mn.desc.contains("I") && mn.desc.startsWith("(I")) {
                    hook.add(new VarInsnNode(Opcodes.ILOAD, 1));
                    hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/String", "valueOf", "(I)Ljava/lang/String;", false));
                } else {
                    hook.add(new LdcInsnNode(n));
                }
                hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/hitboy/loader/MinecraftHooks", "onKeyPress", "(Ljava/lang/String;)V", false));
                if (mn.instructions.getFirst() != null) mn.instructions.insertBefore(mn.instructions.getFirst(), hook);
                else mn.instructions.add(hook);
                System.out.println("Hooked key: " + cn.name + "#" + mn.name + mn.desc);
                return;
            }
        }
    }
    private static boolean hookTitleScreen(ClassNode cn, boolean isTitle) {
        isTitle = isTitle || cn.name.contains("TitleScreen") || cn.name.contains("GuiMainMenu") || cn.name.contains("MainMenuScreen") || cn.name.toLowerCase().contains("titlescreen");
        // Also handle obfuscated: check if class has string "TitleScreen" or has panorama rendering
        if (!isTitle) return false;
        if ("gsd".equals(cn.name)) {
            for (MethodNode method : cn.methods) {
                if (!method.name.equals("a") || !method.desc.equals("(Lgir;IIF)V")) continue;
                for (AbstractInsnNode instruction : method.instructions.toArray()) {
                    if (instruction.getOpcode() != Opcodes.RETURN) continue;
                    InsnList overlay = new InsnList();
                    overlay.add(new VarInsnNode(Opcodes.ALOAD, 0));
                    overlay.add(new VarInsnNode(Opcodes.ALOAD, 1));
                    overlay.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                        "com/hitboy/loader/ModMenuHelper", "drawClientIndicator",
                        "(Ljava/lang/Object;Ljava/lang/Object;)V", false));
                    method.instructions.insertBefore(instruction, overlay);
                }
            }
            boolean replacedBranding = replaceTitleBranding(cn);
            boolean replacedRealms = replaceRealmsButton(cn);
            for (MethodNode mn : cn.methods) {
                if ((mn.access & (Opcodes.ACC_ABSTRACT | Opcodes.ACC_NATIVE)) != 0) continue; // no body to patch (26.x interfaces)
                if (mn.name.equals("bg_") && mn.desc.equals("()V")) {
                    InsnList hook = new InsnList();
                    hook.add(new VarInsnNode(Opcodes.ALOAD, 0));
                    hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/hitboy/loader/ModMenuHelper", "injectModsButton", "(Ljava/lang/Object;)V", false));
                    for (AbstractInsnNode insn : mn.instructions.toArray()) {
                        if (insn.getOpcode() == Opcodes.RETURN) {
                            mn.instructions.insertBefore(insn, hook);
                            System.out.println("Hooked 1.21.11 TitleScreen mods button: gsd#bg_");
                            return true;
                        }
                    }
                }
            }
            return replacedBranding || replacedRealms;
        }
        // The 1.20.1 title screen's protected no-arg method builds all menu widgets.
        for (MethodNode mn : cn.methods) {
            if ((mn.access & (Opcodes.ACC_ABSTRACT | Opcodes.ACC_NATIVE)) != 0) continue; // no body to patch (26.x interfaces)
            if (!mn.desc.equals("()V")) continue;
            if (mn.name.equals("<init>") || mn.name.equals("<clinit>")) continue;
            boolean hasWidget = hasTitleMenuLabels(mn) || containsWidgetRegistration(mn);
            for (AbstractInsnNode insn : mn.instructions) {
                if (insn instanceof MethodInsnNode) {
                    MethodInsnNode mi = (MethodInsnNode) insn;
                    if (mi.name.contains("addRenderableWidget") || mi.name.contains("addWidget") || mi.desc.contains("Button") || mi.owner.contains("Button")) { hasWidget = true; break; }
                    if (mi.name.equals("m_77828_") || mi.name.equals("method_25429")) hasWidget = true; // obf addWidget
                }
            }
            if (hasWidget || mn.name.toLowerCase().contains("init")) {
                InsnList hook = new InsnList();
                hook.add(new VarInsnNode(Opcodes.ALOAD, 0));
                hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/hitboy/loader/ModMenuHelper", "injectModsButton", "(Ljava/lang/Object;)V", false));
                // insert before RETURN
                for (AbstractInsnNode insn : mn.instructions.toArray()) {
                    if (insn.getOpcode() == Opcodes.RETURN) { mn.instructions.insertBefore(insn, hook); System.out.println("Hooked TitleScreen mods button: " + cn.name + "#" + mn.name); return true; }
                }
                mn.instructions.add(hook);
                System.out.println("Hooked TitleScreen (fallback): " + cn.name + "#" + mn.name);
                return true;
            }
        }
        // ultimate fallback: first ()V method
        for (MethodNode mn : cn.methods) if ((mn.access & (Opcodes.ACC_ABSTRACT | Opcodes.ACC_NATIVE)) == 0 && mn.desc.equals("()V") && mn.instructions.size() > 5 && !mn.name.equals("<init>")) {
            InsnList hook = new InsnList();
            hook.add(new VarInsnNode(Opcodes.ALOAD, 0));
            hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/hitboy/loader/ModMenuHelper", "injectModsButton", "(Ljava/lang/Object;)V", false));
            mn.instructions.insertBefore(mn.instructions.getLast(), hook);
            System.out.println("Hooked TitleScreen ultimate: " + cn.name + "#" + mn.name);
            return true;
        }
        return false;
    }
    private static boolean replaceTitleBranding(ClassNode titleScreen) {
        boolean changed = false;
        for (MethodNode method : titleScreen.methods) {
            for (AbstractInsnNode instruction : method.instructions) {
                if (instruction instanceof LdcInsnNode) {
                    LdcInsnNode constant = (LdcInsnNode) instruction;
                    if ("menu.modded".equals(constant.cst)) {
                        constant.cst = "/HitBoy's Mod Loader";
                        changed = true;
                    }
                }
            }
        }
        return changed;
    }

    private static boolean replaceRealmsButton(ClassNode titleScreen) {
        boolean changed = false;
        for (MethodNode method : titleScreen.methods) {
            for (AbstractInsnNode instruction : method.instructions) {
                if (instruction instanceof LdcInsnNode
                    && "menu.online".equals(((LdcInsnNode) instruction).cst)) {
                    ((LdcInsnNode) instruction).cst = "Mods";
                    changed = true;
                }
            }
            if (method.name.equals("b") && method.desc.equals("(Lgje;)V")) {
                method.instructions.clear();
                method.tryCatchBlocks.clear();
                if (method.localVariables != null) method.localVariables.clear();
                method.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
                method.instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                    "com/hitboy/loader/ModMenuHelper", "openModsScreen", "(Ljava/lang/Object;)V", false));
                method.instructions.add(new InsnNode(Opcodes.RETURN));
                changed = true;
            }
        }
        if (changed) {
            System.out.println("Replaced 1.21.11 Realms button and title branding.");
        }
        return changed;
    }
    private static boolean hasTitleMenuLabels(MethodNode method) {
        boolean singleplayer = false;
        boolean multiplayer = false;
        for (AbstractInsnNode instruction : method.instructions) {
            if (instruction instanceof LdcInsnNode) {
                Object value = ((LdcInsnNode) instruction).cst;
                if ("menu.singleplayer".equals(value)) singleplayer = true;
                if ("menu.multiplayer".equals(value)) multiplayer = true;
            }
        }
        return singleplayer && multiplayer;
    }
    private static boolean containsWidgetRegistration(MethodNode method) {
        if ((method.access & Opcodes.ACC_PROTECTED) == 0) {
            return false;
        }
        int registrations = 0;
        for (AbstractInsnNode instruction : method.instructions) {
            if (!(instruction instanceof MethodInsnNode)) {
                continue;
            }
            MethodInsnNode invocation = (MethodInsnNode) instruction;
            if (invocation.name.equals("addRenderableWidget") || invocation.name.equals("addWidget")
                || invocation.name.equals("method_25429")
                || (invocation.name.equals("d") && invocation.desc.endsWith(")Leqt;"))) {
                registrations++;
            }
        }
        return registrations >= 2;
    }
}
