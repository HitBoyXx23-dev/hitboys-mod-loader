package com.hitboy.loader.fabric;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class NeoForgeFabricBridgeTest {
    @Test
    void removesFabricApiMixinAlreadyProvidedByBaseEngine() {
        String source = "{\"required\":true,\"package\":\"net.fabricmc.fabric.mixin.registry.sync\","
            + "\"client\":[\"client.MinecraftMixin\",\"client.OtherMixin\"],\"injectors\":{\"defaultRequire\":1}}";

        JsonObject result = JsonParser.parseString(new String(
            NeoForgeFabricBridge.softenMixinConfig(source.getBytes(StandardCharsets.UTF_8)),
            StandardCharsets.UTF_8
        )).getAsJsonObject();

        assertFalse(result.get("required").getAsBoolean());
        assertEquals(0, result.getAsJsonObject("injectors").get("defaultRequire").getAsInt());
        assertEquals(1, result.getAsJsonArray("client").size());
        assertEquals("client.OtherMixin", result.getAsJsonArray("client").get(0).getAsString());
    }
}
