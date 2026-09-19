package com.hitboy.loader.compat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModInspectorTest {
    @TempDir Path temporaryDirectory;

    @Test
    void readsNativeDescriptor() throws Exception {
        Path jar = jar("native.jar", Map.of("hitboy.json", "{\"id\":\"demo\",\"name\":\"Demo\",\"version\":\"1.0\",\"mainClass\":\"demo.Main\",\"type\":\"client\"}"));
        ModInspector.Inspection inspection = new ModInspector().inspect(jar);
        assertEquals(SourceLoader.HITBOY, inspection.getDescriptor().getSourceLoader());
        assertEquals(ModEnvironment.CLIENT, inspection.getDescriptor().getEnvironment());
        assertTrue(inspection.getReport().isCompatible());
    }

    @Test
    void readsFabricFeaturesAndBlocksUnsupportedRuntime() throws Exception {
        String metadata = "{\"schemaVersion\":1,\"id\":\"fabric-demo\",\"name\":\"Fabric Demo\",\"version\":\"2.0\",\"environment\":\"client\",\"entrypoints\":{\"client\":[\"demo.Client\"]},\"mixins\":[\"demo.mixins.json\"],\"accessWidener\":\"demo.accesswidener\",\"jars\":[{\"file\":\"META-INF/jars/lib.jar\"}]}";
        Path jar = jar("fabric.jar", Map.of("fabric.mod.json", metadata));
        ModInspector.Inspection inspection = new ModInspector().inspect(jar);
        assertEquals(SourceLoader.FABRIC, inspection.getDescriptor().getSourceLoader());
        assertEquals("demo.mixins.json", inspection.getDescriptor().getMixinConfigs().get(0));
        assertFalse(inspection.getReport().isCompatible());
    }

    @Test
    void readsForgeAndNeoForgeDescriptors() throws Exception {
        String toml = "modId=\"example\"\nversion=\"3.1\"\ndisplayName=\"Example Mod\"\n";
        assertEquals(SourceLoader.FORGE, new ModInspector().inspect(jar("forge.jar", Map.of("META-INF/mods.toml", toml))).getDescriptor().getSourceLoader());
        assertEquals(SourceLoader.NEOFORGE, new ModInspector().inspect(jar("neo.jar", Map.of("META-INF/neoforge.mods.toml", toml))).getDescriptor().getSourceLoader());
    }

    @Test
    void blocksNativeLibraries() throws Exception {
        Map<String, String> entries = new LinkedHashMap<>();
        entries.put("hitboy.json", "{\"name\":\"Native\",\"version\":\"1\",\"mainClass\":\"demo.Main\"}");
        entries.put("native/demo.dll", "binary");
        assertFalse(new ModInspector().inspect(jar("unsafe.jar", entries)).getReport().isCompatible());
    }

    private Path jar(String name, Map<String, String> entries) throws IOException {
        Path path = temporaryDirectory.resolve(name);
        try (JarOutputStream output = new JarOutputStream(java.nio.file.Files.newOutputStream(path))) {
            for (Map.Entry<String, String> entry : entries.entrySet()) {
                output.putNextEntry(new JarEntry(entry.getKey()));
                output.write(entry.getValue().getBytes(StandardCharsets.UTF_8));
                output.closeEntry();
            }
        }
        return path;
    }
}
