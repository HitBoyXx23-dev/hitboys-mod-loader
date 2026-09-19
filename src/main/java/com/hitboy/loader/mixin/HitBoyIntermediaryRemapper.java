package com.hitboy.loader.mixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

public final class HitBoyIntermediaryRemapper implements IRemapper {
    private static final Pattern DESCRIPTOR_CLASS = Pattern.compile("L([^;]+);");
    private final Map<String, String> classes = new HashMap<>();
    private final Map<String, String> reverseClasses = new HashMap<>();
    private final Map<String, String> methods = new HashMap<>();
    private final Map<String, String> fields = new HashMap<>();
    private final Map<String, String> memberNames = new HashMap<>();
    private final Map<String, String> methodNames = new HashMap<>();
    private final Map<String, String> fieldNames = new HashMap<>();
    private final Map<String, String> bareMethodSelectors = new HashMap<>();
    private final Map<String, String> bareFieldSelectors = new HashMap<>();
    private final Map<String, String> namedClasses = new HashMap<>();
    private final Map<String, String> namedMethods = new HashMap<>();
    private final Map<String, String> namedFields = new HashMap<>();
    private final Map<String, String> namedMethodFallbacks = new HashMap<>();
    private final Map<String, String> namedFieldFallbacks = new HashMap<>();

    public HitBoyIntermediaryRemapper(String gameVersion) {
        String resource = "/mappings/" + gameVersion + "-intermediary.tiny";
        try (InputStream input = HitBoyIntermediaryRemapper.class.getResourceAsStream(resource)) {
            if (input == null) throw new IllegalStateException("Missing HitBoy Mixin mappings for " + gameVersion);
            load(input);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not read HitBoy Mixin mappings for " + gameVersion, exception);
        }
        loadNamed(gameVersion);
    }

    private void loadNamed(String gameVersion) {
        String resource = "/mappings/" + gameVersion + "-named.tiny";
        try (InputStream input = HitBoyIntermediaryRemapper.class.getResourceAsStream(resource)) {
            if (input == null) return;
            List<String> lines = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8)).lines().toList();
            String officialOwner = null;
            for (String line : lines) {
                String[] parts = line.split("\\t", -1);
                if (line.startsWith("c\t") && parts.length >= 4) {
                    officialOwner = parts[1];
                    namedClasses.put(parts[3], parts[1]);
                    continue;
                }
                if (officialOwner == null || parts.length < 6 || !parts[0].isEmpty()) continue;
                if ("m".equals(parts[1])) {
                    namedMethods.put(memberKey(officialOwner, parts[5], parts[2]), parts[3]);
                    addFallback(namedMethodFallbacks, parts[5], parts[3]);
                }
                if ("f".equals(parts[1])) {
                    namedFields.put(memberKey(officialOwner, parts[5], parts[2]), parts[3]);
                    addFallback(namedFieldFallbacks, parts[5], parts[3]);
                }
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Could not read HitBoy named mappings for " + gameVersion, exception);
        }
    }

    private void load(InputStream input) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) lines.add(line);
        }
        for (String line : lines) {
            if (!line.startsWith("c\t")) continue;
            String[] parts = line.split("\t", -1);
            if (parts.length < 3) continue;
            classes.put(parts[2], parts[1]);
            reverseClasses.put(parts[1], parts[2]);
        }
        String officialOwner = null;
        String intermediaryOwner = null;
        for (String line : lines) {
            String[] parts = line.split("\t", -1);
            if (line.startsWith("c\t") && parts.length >= 3) {
                officialOwner = parts[1];
                intermediaryOwner = parts[2];
                continue;
            }
            if (officialOwner == null || parts.length < 5 || !parts[0].isEmpty()) continue;
            String kind = parts[1];
            if (!"m".equals(kind) && !"f".equals(kind)) continue;
            String officialDescriptor = parts[2];
            String officialName = parts[3];
            String intermediaryName = parts[4];
            String intermediaryDescriptor = remapDescriptor(officialDescriptor, reverseClasses);
            String key = memberKey(intermediaryOwner, intermediaryName, intermediaryDescriptor);
            if ("m".equals(kind)) {
                methods.put(key, officialName);
                methodNames.put(intermediaryName, officialName);
                bareMethodSelectors.put(intermediaryName, officialName + officialDescriptor);
            } else {
                fields.put(key, officialName);
                fieldNames.put(intermediaryName, officialName);
                bareFieldSelectors.put(intermediaryName, officialName + ":" + officialDescriptor);
            }
            memberNames.put(intermediaryName, officialName);
        }
    }

    @Override
    public String mapMethodName(String owner, String name, String descriptor) {
        String mapped = methods.get(memberKey(owner, name, descriptor));
        if (mapped != null) return mapped;
        mapped = namedMethods.get(memberKey(owner, name, descriptor));
        if (mapped != null) return mapped;
        mapped = namedMethodFallbacks.get(name);
        if (mapped != null && !mapped.isEmpty()) return mapped;
        return methodNames.getOrDefault(name, name);
    }

    @Override
    public String mapFieldName(String owner, String name, String descriptor) {
        String mapped = fields.get(memberKey(owner, name, descriptor));
        if (mapped != null) return mapped;
        mapped = namedFields.get(memberKey(owner, name, descriptor));
        if (mapped != null) return mapped;
        mapped = namedFieldFallbacks.get(name);
        if (mapped != null && !mapped.isEmpty()) return mapped;
        return fieldNames.getOrDefault(name, name);
    }

    @Override
    public String map(String typeName) {
        String mapped = classes.get(typeName);
        if (mapped != null) return mapped;
        return namedClasses.getOrDefault(typeName, typeName);
    }

    @Override
    public String unmap(String typeName) {
        return reverseClasses.getOrDefault(typeName, typeName);
    }

    @Override
    public String mapDesc(String descriptor) {
        return remapDescriptor(remapDescriptor(descriptor, classes), namedClasses);
    }

    @Override
    public String unmapDesc(String descriptor) {
        return remapDescriptor(descriptor, reverseClasses);
    }

    public String remapIntermediaryText(String value) {
        Matcher method = Pattern.compile("^L([^;]+);([^(:]+)(\\(.*)$").matcher(value);
        if (method.matches()) {
            String owner = method.group(1);
            String descriptor = method.group(3);
            return "L" + map(owner) + ";" + mapMethodName(owner, method.group(2), descriptor) + mapDesc(descriptor);
        }
        Matcher field = Pattern.compile("^L([^;]+);([^(:]+):(.*)$").matcher(value);
        if (field.matches()) {
            String owner = field.group(1);
            String descriptor = field.group(3);
            return "L" + map(owner) + ";" + mapFieldName(owner, field.group(2), descriptor) + ":" + mapDesc(descriptor);
        }
        Matcher unqualifiedMethod = Pattern.compile("^([^(:]+)(\\(.*)$").matcher(value);
        if (unqualifiedMethod.matches()) {
            return methodNames.getOrDefault(unqualifiedMethod.group(1), unqualifiedMethod.group(1))
                + mapDesc(unqualifiedMethod.group(2));
        }
        Matcher unqualifiedField = Pattern.compile("^([^(:]+):(.*)$").matcher(value);
        if (unqualifiedField.matches()) {
            return fieldNames.getOrDefault(unqualifiedField.group(1), unqualifiedField.group(1))
                + ":" + mapDesc(unqualifiedField.group(2));
        }
        if (value.matches("method_\\d+")) return bareMethodSelectors.getOrDefault(value, value);
        if (value.matches("field_\\d+")) return bareFieldSelectors.getOrDefault(value, value);
        return Pattern.compile("net/minecraft/class_\\d+(?:\\$class_\\d+)*")
            .matcher(value).replaceAll(match -> Matcher.quoteReplacement(map(match.group())));
    }

    private static String memberKey(String owner, String name, String descriptor) {
        return owner + '\u0000' + name + '\u0000' + descriptor;
    }

    private static void addFallback(Map<String, String> fallbacks, String source, String target) {
        String existing = fallbacks.get(source);
        if (existing == null) fallbacks.put(source, target);
        else if (!existing.equals(target)) fallbacks.put(source, "");
    }

    private static String remapDescriptor(String descriptor, Map<String, String> mappings) {
        Matcher matcher = DESCRIPTOR_CLASS.matcher(descriptor);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String mapped = mappings.getOrDefault(matcher.group(1), matcher.group(1));
            matcher.appendReplacement(result, Matcher.quoteReplacement("L" + mapped + ";"));
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
