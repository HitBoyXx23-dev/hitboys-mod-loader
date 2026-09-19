package com.hitboy.loader.api;

import java.util.Set;

public interface HitBoyTransformer {
    String getId();
    int getOrder();
    Set<String> getTargets();
    byte[] transform(String className, byte[] bytecode, HitBoyTransformContext context) throws Exception;
}
