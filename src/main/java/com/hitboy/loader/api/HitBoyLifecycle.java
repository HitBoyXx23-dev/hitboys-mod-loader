package com.hitboy.loader.api;

public interface HitBoyLifecycle {
    default void onDiscover(HitBoyContext context) {}
    default void onConstruct(HitBoyContext context) {}
    default void onRegister(HitBoyContext context) {}
    default void onClientInitialize(HitBoyContext context) {}
    default void onServerInitialize(HitBoyContext context) {}
    default void onWorldLoad(HitBoyContext context) {}
    default void onShutdown(HitBoyContext context) {}
}
