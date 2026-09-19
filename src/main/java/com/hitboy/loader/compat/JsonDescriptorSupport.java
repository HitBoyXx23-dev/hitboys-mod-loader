package com.hitboy.loader.compat;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

final class JsonDescriptorSupport {
    private JsonDescriptorSupport() {
    }

    static JsonObject read(JarFile jar, String entryName) throws IOException {
        JarEntry entry = jar.getJarEntry(entryName);
        if (entry == null) throw new IOException("Missing " + entryName);
        try (InputStream input = jar.getInputStream(entry)) {
            try {
                return JsonParser.parseString(new String(input.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
            } catch (RuntimeException exception) {
                throw new IOException("Invalid " + entryName + ": " + exception.getMessage(), exception);
            }
        }
    }

    static String requiredString(JsonObject json, String key, String descriptor) throws IOException {
        if (!json.has(key) || !json.get(key).isJsonPrimitive()) {
            throw new IOException(descriptor + " requires string property " + key);
        }
        String value = json.get(key).getAsString().trim();
        if (value.isEmpty()) throw new IOException(descriptor + " property " + key + " cannot be empty");
        return value;
    }

    static List<String> strings(JsonElement value) {
        List<String> result = new ArrayList<>();
        if (value == null) return result;
        if (value.isJsonPrimitive()) {
            result.add(value.getAsString());
        } else if (value.isJsonArray()) {
            for (JsonElement element : value.getAsJsonArray()) {
                if (element.isJsonPrimitive()) result.add(element.getAsString());
                else if (element.isJsonObject() && element.getAsJsonObject().has("config")) {
                    result.add(element.getAsJsonObject().get("config").getAsString());
                }
            }
        }
        return result;
    }

    static List<String> entrypoints(JsonElement value) {
        List<String> result = new ArrayList<>();
        if (value == null) return result;
        JsonArray values = value.isJsonArray() ? value.getAsJsonArray() : new JsonArray();
        if (!value.isJsonArray()) values.add(value);
        for (JsonElement element : values) {
            if (element.isJsonPrimitive()) result.add(element.getAsString());
            else if (element.isJsonObject() && element.getAsJsonObject().has("value")) {
                result.add(element.getAsJsonObject().get("value").getAsString());
            }
        }
        return result;
    }
}
