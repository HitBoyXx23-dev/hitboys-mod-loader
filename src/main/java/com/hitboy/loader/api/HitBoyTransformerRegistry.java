package com.hitboy.loader.api;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class HitBoyTransformerRegistry {
    private final List<HitBoyTransformer> transformers = new ArrayList<>();
    private final Map<String, HitBoyTransformer> byId = new HashMap<>();

    public synchronized void register(HitBoyTransformer transformer) {
        if (byId.putIfAbsent(transformer.getId(), transformer) != null) throw new IllegalArgumentException("Duplicate transformer id " + transformer.getId());
        for (HitBoyTransformer existing : transformers) {
            if (existing.getOrder() == transformer.getOrder() && existing.getTargets().stream().anyMatch(transformer.getTargets()::contains)) {
                throw new IllegalArgumentException("Transformer order conflict between " + existing.getId() + " and " + transformer.getId());
            }
        }
        transformers.add(transformer);
        transformers.sort(Comparator.comparingInt(HitBoyTransformer::getOrder).thenComparing(HitBoyTransformer::getId));
    }

    public byte[] transform(String className, byte[] bytecode, HitBoyTransformContext context) throws Exception {
        byte[] current = bytecode;
        for (HitBoyTransformer transformer : transformers) {
            if (transformer.getTargets().contains(className) || transformer.getTargets().contains("*")) {
                current = transformer.transform(className, current, context);
                if (current == null) throw new IllegalStateException("Transformer " + transformer.getId() + " returned null for " + className);
            }
        }
        return current;
    }

    public synchronized List<HitBoyTransformer> getTransformers() { return List.copyOf(transformers); }
}
