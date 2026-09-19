package com.hitboy.loader.compat;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class InstanceVerifierTest {
    @TempDir
    Path directory;

    @Test
    void requiresForeignModsToBePortedBeforeLaunch() throws Exception {
        writeJar(
            directory.resolve("foreign.jar"),
            "fabric.mod.json",
            "{\"id\":\"foreign\",\"name\":\"Foreign\",\"version\":\"1.0\",\"entrypoints\":{\"client\":[\"sample.Foreign\"]}}"
        );
        InstanceVerifier.Result result = new InstanceVerifier().verify(directory);
        assertFalse(result.getReport().isCompatible());
        assertTrue(result.getReport().getIssues().stream().anyMatch(issue -> "PORT_REQUIRED".equals(issue.getCode())));
    }

    private void writeJar(Path path, String descriptor, String content) throws Exception {
        try (JarOutputStream output = new JarOutputStream(Files.newOutputStream(path))) {
            output.putNextEntry(new JarEntry(descriptor));
            output.write(content.getBytes(StandardCharsets.UTF_8));
            output.closeEntry();
        }
    }
}
