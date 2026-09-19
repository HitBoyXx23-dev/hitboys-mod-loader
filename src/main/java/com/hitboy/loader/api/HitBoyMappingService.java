package com.hitboy.loader.api;

public interface HitBoyMappingService {
    String mapClass(String sourceNamespace, String targetNamespace, String className);
    String mapMethod(String sourceNamespace, String targetNamespace, String owner, String name, String descriptor);
    String mapField(String sourceNamespace, String targetNamespace, String owner, String name, String descriptor);
    String mapDescriptor(String sourceNamespace, String targetNamespace, String descriptor);
}
