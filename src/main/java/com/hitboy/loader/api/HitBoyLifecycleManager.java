package com.hitboy.loader.api;

import java.util.ArrayList;
import java.util.List;

public final class HitBoyLifecycleManager {
    public enum Phase { DISCOVERY, CONSTRUCTION, REGISTRATION, CLIENT_INITIALIZATION, SERVER_INITIALIZATION, WORLD_LOAD, SHUTDOWN }
    private final List<HitBoyLifecycle> listeners = new ArrayList<>();
    private Phase lastPhase;

    public synchronized void register(HitBoyLifecycle listener) {
        if (lastPhase != null) throw new IllegalStateException("Lifecycle registration is closed after " + lastPhase);
        listeners.add(listener);
    }

    public synchronized void advance(Phase phase, HitBoyContext context) {
        if (lastPhase != null && phase.ordinal() <= lastPhase.ordinal()) throw new IllegalStateException("Invalid lifecycle transition from " + lastPhase + " to " + phase);
        for (HitBoyLifecycle listener : listeners) invoke(listener, phase, context);
        lastPhase = phase;
    }

    private void invoke(HitBoyLifecycle listener, Phase phase, HitBoyContext context) {
        switch (phase) {
            case DISCOVERY -> listener.onDiscover(context);
            case CONSTRUCTION -> listener.onConstruct(context);
            case REGISTRATION -> listener.onRegister(context);
            case CLIENT_INITIALIZATION -> listener.onClientInitialize(context);
            case SERVER_INITIALIZATION -> listener.onServerInitialize(context);
            case WORLD_LOAD -> listener.onWorldLoad(context);
            case SHUTDOWN -> listener.onShutdown(context);
        }
    }

    public synchronized Phase getLastPhase() { return lastPhase; }
}
