package com.hitboy.loader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class OptionalMixinRuntimeTest {
    @TempDir
    Path directory;

    @Test
    void discoversOnlyNativeMixinConfigurations() throws Exception {
        writeJar(directory.resolve("native.jar"), "hitboy.json",
            "{\"name\":\"Native\",\"version\":\"1\",\"mainClass\":\"sample.Main\",\"mixins\":[\"native.mixins.json\"]}");
        writeJar(directory.resolve("fabric.jar"), "fabric.mod.json",
            "{\"id\":\"fabric\",\"version\":\"1\",\"mixins\":[{\"config\":\"fabric.mixins.json\",\"environment\":\"client\"}]}");
        assertEquals(
            java.util.List.of("native.mixins.json"),
            OptionalMixinRuntime.discoverConfigurations(directory)
        );
    }

    private void writeJar(Path path, String descriptor, String content) throws Exception {
        Files.createDirectories(path.getParent());
        try (JarOutputStream output = new JarOutputStream(Files.newOutputStream(path))) {
            output.putNextEntry(new JarEntry(descriptor));
            output.write(content.getBytes(StandardCharsets.UTF_8));
            output.closeEntry();
        }
    }
}
