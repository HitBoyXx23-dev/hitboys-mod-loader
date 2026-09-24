package com.hitboy.loader;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import static org.junit.jupiter.api.Assertions.*;

class GameAgentTitleTest {
    @Test
    void keepsRealmsAndBrandsTitleWithoutLoadingGameClasses() throws Exception {
        ClassNode title = new ClassNode();
        title.version = Opcodes.V17;
        title.access = Opcodes.ACC_PUBLIC;
        title.name = "gsd";
        title.superName = "java/lang/Object";
        for (String name : new String[]{"b", "c"}) {
            MethodNode callback = new MethodNode(Opcodes.ACC_PRIVATE, name, "(Lgje;)V", null, null);
            callback.instructions.add(new InsnNode(Opcodes.RETURN));
            title.methods.add(callback);
        }
        MethodNode labels = new MethodNode(Opcodes.ACC_PUBLIC, "labels", "()V", null, null);
        for (String label : new String[]{"menu.online", "menu.modded"}) {
            labels.instructions.add(new LdcInsnNode(label));
            labels.instructions.add(new InsnNode(Opcodes.POP));
        }
        labels.instructions.add(new InsnNode(Opcodes.RETURN));
        title.methods.add(labels);
        ClassWriter input = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        title.accept(input);
        ClassLoader rejectingLoader = new ClassLoader(null) {
            @Override
            protected Class<?> loadClass(String name, boolean resolve) {
                throw new AssertionError("Transformer attempted to load " + name);
            }
        };
        Method transform = GameAgent.class.getDeclaredMethod("transformTitleMenuClass",
            String.class, byte[].class, ClassLoader.class);
        transform.setAccessible(true);
        byte[] output = (byte[]) transform.invoke(null, "gsd", input.toByteArray(), rejectingLoader);
        ClassNode result = new ClassNode();
        new ClassReader(output).accept(result, 0);
        MethodNode realms = result.methods.stream().filter(m -> m.name.equals("b")).findFirst().orElseThrow();
        MethodNode multiplayer = result.methods.stream().filter(m -> m.name.equals("c")).findFirst().orElseThrow();
        // Realms is kept; the Mods button is added beside it at runtime instead.
        assertEquals(Opcodes.RETURN, realms.instructions.getFirst().getOpcode());
        assertEquals(Opcodes.RETURN, multiplayer.instructions.getFirst().getOpcode());
        MethodNode updatedLabels = result.methods.stream().filter(m -> m.name.equals("labels")).findFirst().orElseThrow();
        assertTrue(java.util.Arrays.stream(updatedLabels.instructions.toArray()).anyMatch(i ->
            i instanceof LdcInsnNode && "menu.online".equals(((LdcInsnNode) i).cst)));
        assertTrue(java.util.Arrays.stream(updatedLabels.instructions.toArray()).anyMatch(i ->
            i instanceof LdcInsnNode && "/HitBoy's Mod Loader".equals(((LdcInsnNode) i).cst)));
    }
}
