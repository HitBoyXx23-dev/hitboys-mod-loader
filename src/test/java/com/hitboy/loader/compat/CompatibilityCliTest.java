package com.hitboy.loader.compat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CompatibilityCliTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void inspectsAndVerifiesNativeMods() throws Exception {
        Path modsDirectory = temporaryDirectory.resolve("native_mods");
        Files.createDirectories(modsDirectory);
        Path jar = modsDirectory.resolve("sample.jar");
        writeJar(jar, "hitboy.json", "{\"id\":\"sample\",\"name\":\"Sample\",\"version\":\"1.0.0\",\"mainClass\":\"sample.Main\"}");
        ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();
        ByteArrayOutputStream errorBytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputBytes, true, StandardCharsets.UTF_8);
        PrintStream error = new PrintStream(errorBytes, true, StandardCharsets.UTF_8);
        CompatibilityCli cli = new CompatibilityCli();

        assertEquals(0, cli.execute(new String[]{"inspect", jar.toString()}, output, error));
        assertEquals(0, cli.execute(new String[]{"verify", temporaryDirectory.toString()}, output, error));
        String text = outputBytes.toString(StandardCharsets.UTF_8);
        assertTrue(text.contains("Source loader: HITBOY"));
        assertTrue(text.contains("Discovered mods: 1"));
        assertTrue(text.contains("Compatibility: PASS"));
    }

    @Test
    void blocksFabricPortWhenCompatibilityIsIncomplete() throws Exception {
        Path jar = temporaryDirectory.resolve("fabric.jar");
        writeJar(jar, "fabric.mod.json", "{\"schemaVersion\":1,\"id\":\"foreign\",\"name\":\"Foreign\",\"version\":\"1.0.0\",\"entrypoints\":{\"client\":[\"foreign.Client\"]}}");
        ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();
        ByteArrayOutputStream errorBytes = new ByteArrayOutputStream();

        int exitCode = new CompatibilityCli().execute(
            new String[]{"port", jar.toString(), "--output", temporaryDirectory.resolve("cache").toString()},
            new PrintStream(outputBytes, true, StandardCharsets.UTF_8),
            new PrintStream(errorBytes, true, StandardCharsets.UTF_8)
        );

        assertEquals(1, exitCode);
        assertTrue(outputBytes.toString(StandardCharsets.UTF_8).contains("Compatibility: BLOCKED"));
        assertTrue(errorBytes.toString(StandardCharsets.UTF_8).contains("Port blocked"));
    }

    private void writeJar(Path path, String descriptor, String content) throws Exception {
        try (JarOutputStream output = new JarOutputStream(Files.newOutputStream(path))) {
            output.putNextEntry(new JarEntry(descriptor));
            output.write(content.getBytes(StandardCharsets.UTF_8));
            output.closeEntry();
        }
    }
}
