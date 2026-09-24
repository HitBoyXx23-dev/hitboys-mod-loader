package net.fabricmc.loader.api.entrypoint;

import net.fabricmc.loader.api.ModContainer;

/** An entrypoint together with the mod that declared it. */
public interface EntrypointContainer<T> {
    T getEntrypoint();

    ModContainer getProvider();

    default String getDefinition() {
        return "";
    }
}
