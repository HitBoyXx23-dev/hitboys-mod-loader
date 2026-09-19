package com.hitboy.loader.compat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ContentAddressedCacheTest {
    @TempDir Path temporaryDirectory;

    @Test
    void cacheKeyTracksInputs() throws Exception {
        Path source = temporaryDirectory.resolve("mod.jar");
        Files.writeString(source, "one");
        ContentAddressedCache cache = new ContentAddressedCache();
        String first = cache.key(source, "1.21.11", "1", "21");
        assertEquals(first, cache.key(source, "1.21.11", "1", "21"));
        assertNotEquals(first, cache.key(source, "1.21.11", "2", "21"));
        Files.writeString(source, "two");
        assertNotEquals(first, cache.key(source, "1.21.11", "1", "21"));
    }
}
