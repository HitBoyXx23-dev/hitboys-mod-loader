package com.hitboy.loader.fabric;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.fabricmc.loader.api.metadata.CustomValue;

/** fabric.mod.json "custom" values, backed by Gson. */
class FabricCustomValue implements CustomValue {
    final JsonElement element;

    FabricCustomValue(JsonElement element) {
        this.element = element;
    }

    static CustomValue of(JsonElement element) {
        if (element != null && element.isJsonObject()) return new ObjectValue(element.getAsJsonObject());
        if (element != null && element.isJsonArray()) return new ArrayValue(element.getAsJsonArray());
        return new FabricCustomValue(element);
    }

    @Override
    public CvType getType() {
        if (element == null || element.isJsonNull()) return CvType.NULL;
        if (element.isJsonObject()) return CvType.OBJECT;
        if (element.isJsonArray()) return CvType.ARRAY;
        if (element.getAsJsonPrimitive().isBoolean()) return CvType.BOOLEAN;
        if (element.getAsJsonPrimitive().isNumber()) return CvType.NUMBER;
        return CvType.STRING;
    }

    @Override public CvObject getAsObject() { throw wrongType(CvType.OBJECT); }
    @Override public CvArray getAsArray() { throw wrongType(CvType.ARRAY); }

    @Override
    public String getAsString() {
        if (getType() != CvType.STRING) throw wrongType(CvType.STRING);
        return element.getAsString();
    }

    @Override
    public Number getAsNumber() {
        if (getType() != CvType.NUMBER) throw wrongType(CvType.NUMBER);
        return element.getAsNumber();
    }

    @Override
    public boolean getAsBoolean() {
        if (getType() != CvType.BOOLEAN) throw wrongType(CvType.BOOLEAN);
        return element.getAsBoolean();
    }

    ClassCastException wrongType(CvType wanted) {
        return new ClassCastException("Custom value is " + getType() + ", not " + wanted);
    }

    static final class ObjectValue extends FabricCustomValue implements CvObject {
        ObjectValue(JsonObject object) { super(object); }

        private JsonObject object() { return element.getAsJsonObject(); }

        @Override public CvObject getAsObject() { return this; }
        @Override public int size() { return object().size(); }
        @Override public boolean containsKey(String key) { return object().has(key); }
        @Override public CustomValue get(String key) { return object().has(key) ? of(object().get(key)) : null; }

        @Override
        public Iterator<Map.Entry<String, CustomValue>> iterator() {
            List<Map.Entry<String, CustomValue>> entries = new ArrayList<>();
            for (Map.Entry<String, JsonElement> entry : object().entrySet()) {
                entries.add(new AbstractMap.SimpleImmutableEntry<>(entry.getKey(), of(entry.getValue())));
            }
            return entries.iterator();
        }
    }

    static final class ArrayValue extends FabricCustomValue implements CvArray {
        ArrayValue(JsonArray array) { super(array); }

        private JsonArray array() { return element.getAsJsonArray(); }

        @Override public CvArray getAsArray() { return this; }
        @Override public int size() { return array().size(); }
        @Override public CustomValue get(int index) { return of(array().get(index)); }

        @Override
        public Iterator<CustomValue> iterator() {
            List<CustomValue> values = new ArrayList<>();
            for (JsonElement value : array()) values.add(of(value));
            return values.iterator();
        }
    }
}
