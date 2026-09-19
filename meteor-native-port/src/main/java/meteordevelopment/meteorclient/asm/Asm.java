/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.asm;

import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.asm.transformers.PacketInflaterTransformer;
import com.hitboy.loader.api.HitBoyPlatform;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.transformers.MixinClassWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * When mixins are just not good enough
 **/
public class Asm {
    public static Asm INSTANCE;

    private final Map<String, AsmTransformer> transformers = new HashMap<>();
    private final boolean export;

    public Asm(boolean export) {
        this.export = export;
    }

    public static void init() {
        if (INSTANCE != null) return;

        INSTANCE = new Asm(System.getProperty("meteor.asm.export") != null);
        INSTANCE.add(new PacketInflaterTransformer());
    }

    private void add(AsmTransformer transformer) {
        transformers.put(transformer.targetName, transformer);
    }

    public byte[] transform(String name, byte[] bytes) {
        AsmTransformer transformer = transformers.get(name);

        if (transformer != null) {
            ClassNode klass = new ClassNode();
            ClassReader reader = new ClassReader(bytes);
            reader.accept(klass, ClassReader.EXPAND_FRAMES);

            transformer.transform(klass);

            ClassWriter writer = new MixinClassWriter(reader, ClassWriter.COMPUTE_FRAMES);
            klass.accept(writer);
            bytes = writer.toByteArray();

            export(name, bytes);
        }

        return bytes;
    }

    private void export(String name, byte[] bytes) {
        if (export) {
            try {
                Path path = HitBoyPlatform.getGameDirectory().resolve(".meteor.asm.out").resolve(name.replace('.', '/') + ".class");
                new File(path.toUri()).getParentFile().mkdirs();
                Files.write(path, bytes);
            } catch (IOException e) {
                MeteorClient.LOG.error("Failed to export transformer '{}': ", name, e);
            }
        }
    }

}
