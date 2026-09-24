package com.hitboy.loader.fabric;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.fabricmc.loader.api.SemanticVersion;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;

/** A lenient semantic version, like Fabric's: numeric components, optional -prerelease and +build. */
public final class FabricVersion implements SemanticVersion {
    private final String friendly;
    private final int[] components;
    private final String prerelease;
    private final String build;

    private FabricVersion(String friendly, int[] components, String prerelease, String build) {
        this.friendly = friendly;
        this.components = components;
        this.prerelease = prerelease;
        this.build = build;
    }

    public static FabricVersion parse(String text) throws VersionParsingException {
        if (text == null || text.isEmpty()) throw new VersionParsingException("Empty version");
        String core = text;
        String build = null;
        int plus = core.indexOf('+');
        if (plus >= 0) {
            build = core.substring(plus + 1);
            core = core.substring(0, plus);
        }
        String prerelease = null;
        int dash = core.indexOf('-');
        if (dash >= 0) {
            prerelease = core.substring(dash + 1);
            core = core.substring(0, dash);
        }
        List<Integer> parts = new ArrayList<>();
        for (String part : core.split("\\.")) {
            if (part.equals("x") || part.equals("X") || part.equals("*")) {
                parts.add(COMPONENT_WILDCARD);
                continue;
            }
            try {
                parts.add(Integer.parseInt(part));
            } catch (NumberFormatException notNumeric) {
                // Non-semantic versions such as "1.21.11-86" compare as text by their numeric prefix.
                parts.add(0);
            }
        }
        int[] values = parts.stream().mapToInt(Integer::intValue).toArray();
        return new FabricVersion(text, values, prerelease, build);
    }

    static Version parseLenient(String text) {
        try {
            return parse(text);
        } catch (VersionParsingException exception) {
            return new FabricVersion(text == null ? "" : text, new int[] {0}, null, null);
        }
    }

    @Override public String getFriendlyString() { return friendly; }
    @Override public int getVersionComponentCount() { return components.length; }
    @Override public int getVersionComponent(int pos) { return pos < components.length ? components[pos] : 0; }
    @Override public Optional<String> getPrereleaseKey() { return Optional.ofNullable(prerelease); }
    @Override public Optional<String> getBuildKey() { return Optional.ofNullable(build); }

    @Override
    public boolean hasWildcard() {
        for (int component : components) if (component == COMPONENT_WILDCARD) return true;
        return false;
    }

    @Override
    public int compareTo(Version other) {
        if (!(other instanceof SemanticVersion)) return friendly.compareTo(other.getFriendlyString());
        SemanticVersion semantic = (SemanticVersion) other;
        int count = Math.max(getVersionComponentCount(), semantic.getVersionComponentCount());
        for (int index = 0; index < count; index++) {
            int left = getVersionComponent(index);
            int right = semantic.getVersionComponent(index);
            if (left != right) return Integer.compare(left, right);
        }
        boolean leftPre = prerelease != null;
        boolean rightPre = semantic.getPrereleaseKey().isPresent();
        if (leftPre != rightPre) return leftPre ? -1 : 1;
        return leftPre ? prerelease.compareTo(semantic.getPrereleaseKey().get()) : 0;
    }

    @Override public String toString() { return friendly; }
}
