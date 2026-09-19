package com.hitboy.loader.api;

import com.hitboy.loader.EventBus;
import java.nio.file.Path;
import java.util.Objects;

public final class HitBoyContext {
    private final String minecraftVersion;
    private final Path gameDirectory;
    private final EventBus eventBus;

    public HitBoyContext(String minecraftVersion, Path gameDirectory, EventBus eventBus) {
        this.minecraftVersion = Objects.requireNonNull(minecraftVersion, "minecraftVersion");
        this.gameDirectory = Objects.requireNonNull(gameDirectory, "gameDirectory");
        this.eventBus = Objects.requireNonNull(eventBus, "eventBus");
    }

    public String getMinecraftVersion() { return minecraftVersion; }
    public Path getGameDirectory() { return gameDirectory; }
    public EventBus getEventBus() { return eventBus; }
}
