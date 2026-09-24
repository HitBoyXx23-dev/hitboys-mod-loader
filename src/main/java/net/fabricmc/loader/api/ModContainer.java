package net.fabricmc.loader.api;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import net.fabricmc.loader.api.metadata.ModMetadata;

public interface ModContainer {
    ModMetadata getMetadata();

    List<Path> getRootPaths();

    default Optional<Path> findPath(String file) {
        for (Path root : getRootPaths()) {
            Path path = root.resolve(file);
            if (Files.exists(path)) return Optional.of(path);
        }
        return Optional.empty();
    }

    default Optional<ModContainer> getContainingMod() {
        return Optional.empty();
    }

    default Collection<ModContainer> getContainedMods() {
        return Collections.emptyList();
    }

    @Deprecated
    default Path getRootPath() {
        return getRootPaths().get(0);
    }
}
