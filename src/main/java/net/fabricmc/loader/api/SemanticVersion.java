package net.fabricmc.loader.api;

import java.util.Optional;

public interface SemanticVersion extends Version {
    int COMPONENT_WILDCARD = Integer.MIN_VALUE;

    int getVersionComponentCount();

    int getVersionComponent(int pos);

    Optional<String> getPrereleaseKey();

    Optional<String> getBuildKey();

    boolean hasWildcard();

    static SemanticVersion parse(String string) throws VersionParsingException {
        return com.hitboy.loader.fabric.FabricVersion.parse(string);
    }
}
