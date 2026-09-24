package net.fabricmc.loader.api;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import net.fabricmc.api.EnvType;

/** The Fabric Loader API, implemented by HitBoy's Fabric runtime. */
public interface FabricLoader {
    static FabricLoader getInstance() {
        return com.hitboy.loader.fabric.FabricRuntime.loader();
    }

    <T> List<T> getEntrypoints(String key, Class<T> type);

    Optional<ModContainer> getModContainer(String id);

    Collection<ModContainer> getAllMods();

    boolean isModLoaded(String id);

    boolean isDevelopmentEnvironment();

    EnvType getEnvironmentType();

    Object getGameInstance();

    MappingResolver getMappingResolver();

    Path getGameDir();

    @Deprecated
    default File getGameDirectory() {
        return getGameDir().toFile();
    }

    Path getConfigDir();

    @Deprecated
    default File getConfigDirectory() {
        return getConfigDir().toFile();
    }

    String[] getLaunchArguments(boolean sanitize);
}
