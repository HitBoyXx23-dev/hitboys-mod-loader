package com.hitboy.loader.api;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HitBoyTransformerRegistryTest {
    @Test
    void appliesTransformersInOrder() throws Exception {
        HitBoyTransformerRegistry registry = new HitBoyTransformerRegistry();
        registry.register(transformer("second", 20, "B"));
        registry.register(transformer("first", 10, "A"));
        byte[] result = registry.transform("game.Class", new byte[0], new HitBoyTransformContext("1.21.11", "official", "runtime"));
        assertEquals("AB", new String(result, StandardCharsets.UTF_8));
    }

    @Test
    void rejectsConflictingOrderAndTarget() {
        HitBoyTransformerRegistry registry = new HitBoyTransformerRegistry();
        registry.register(transformer("one", 10, "A"));
        assertThrows(IllegalArgumentException.class, () -> registry.register(transformer("two", 10, "B")));
    }

    private HitBoyTransformer transformer(String id, int order, String suffix) {
        return new HitBoyTransformer() {
            public String getId() { return id; }
            public int getOrder() { return order; }
            public Set<String> getTargets() { return Set.of("game.Class"); }
            public byte[] transform(String className, byte[] bytecode, HitBoyTransformContext context) {
                return (new String(bytecode, StandardCharsets.UTF_8) + suffix).getBytes(StandardCharsets.UTF_8);
            }
        };
    }
}
