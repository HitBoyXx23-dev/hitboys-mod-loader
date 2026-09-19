package com.hitboy.loader.fixtures;

import com.hitboy.loader.OptionalMixinRuntime;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public final class MixinRuntimeProbe {
    public static void main(String[] args) throws Exception {
        Path directory = Files.createTempDirectory("hitboy-mixin-probe-");
        Path descriptor = directory.resolve("fixture.jar");
        try {
            try (JarOutputStream jar = new JarOutputStream(Files.newOutputStream(descriptor))) {
                jar.putNextEntry(new JarEntry("hitboy.json"));
                jar.write("{\"mixins\":[\"hitboy-fixture.mixins.json\"]}".getBytes(StandardCharsets.UTF_8));
                jar.closeEntry();
            }
            System.setProperty(OptionalMixinRuntime.ENABLED_PROPERTY, "true");
            OptionalMixinRuntime.initialize(directory);
            Class<?> target = Class.forName("com.hitboy.loader.fixtures.MixinTarget");
            Object value = target.getMethod("value").invoke(target.getConstructor().newInstance());
            if (!"HitBoy Mixin applied".equals(value)) throw new AssertionError("Mixin was not applied: " + value);
            System.out.println(value);
        } finally {
            Files.deleteIfExists(descriptor);
            Files.deleteIfExists(directory);
        }
    }
}
