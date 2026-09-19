package com.hitboy.loader;

import java.util.List;

/**
 * Cross-version abstraction layer for Minecraft client operations.
 * This interface isolates version-specific logic so mod code doesn't break
 * when switching between Minecraft versions (1.8.9, 1.12.2, 1.20+, etc.).
 */
public interface IMinecraftClientWrapper {

    /**
     * Get the current game tick count (version-agnostic).
     * @return current tick
     */
    long getTick();

    /**
     * Get the current FPS (frames per second).
     * @return current FPS value
     */
    int getFps();

    /**
     * Get the player's X coordinate.
     * @return player X position
     */
    double getPlayerX();

    /**
     * Get the player's Y coordinate.
     * @return player Y position
     */
    double getPlayerY();

    /**
     * Get the player's Z coordinate.
     * @return player Z position
     */
    double getPlayerZ();

    /**
     * Get whether the game is in render mode.
     * @return true if rendering
     */
    boolean isRendering();

    /**
     * Get the current Minecraft instance wrapper.
     * @return Minecraft instance
     */
    Object getMinecraftInstance();

    /**
     * Get the player entity (version-agnostic reference).
     * @return player object or null
     */
    Object getPlayerEntity();

    /**
     * Get the list of rendered entities in the world.
     * @return list of entity objects
     */
    List<Object> getRenderedEntities();

    /**
     * Handle version-specific rendering setup.
     * Should be called before rendering operations.
     */
    void beginRender();

    /**
     * Handle version-specific rendering cleanup.
     * Should be called after rendering operations.
     */
    void endRender();

    /**
     * Get the partial ticks for interpolated rendering.
     * @return partial tick delta (0.0 to 1.0)
     */
    float getPartialTicks();

    /**
     * Get the total time played in seconds.
     * @return time in seconds
     */
    long getWorldTime();

    /**
     * Check if the player is on the ground.
     * @return true if on ground
     */
    boolean isPlayerOnGround();
}