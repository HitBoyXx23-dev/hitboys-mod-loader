package net.fabricmc.loader.api.metadata;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import net.fabricmc.loader.api.Version;

public interface ModMetadata {
    String getType();

    String getId();

    Collection<String> getProvides();

    Version getVersion();

    String getName();

    String getDescription();

    Optional<String> getIconPath(int size);

    boolean containsCustomValue(String key);

    CustomValue getCustomValue(String key);

    Map<String, CustomValue> getCustomValues();
}
