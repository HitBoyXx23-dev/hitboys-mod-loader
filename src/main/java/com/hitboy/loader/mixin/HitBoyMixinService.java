package com.hitboy.loader.mixin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.launch.platform.container.ContainerHandleVirtual;
import org.spongepowered.asm.launch.platform.container.IContainerHandle;
import org.spongepowered.asm.logging.ILogger;
import org.spongepowered.asm.logging.LoggerAdapterConsole;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.IMixinTransformer;
import org.spongepowered.asm.mixin.transformer.IMixinTransformerFactory;
import org.spongepowered.asm.service.*;
import org.spongepowered.asm.util.ReEntranceLock;

public final class HitBoyMixinService implements IMixinService, IClassProvider, IClassBytecodeProvider {
    private static volatile IMixinTransformerFactory factory;
    private final ReEntranceLock lock = new ReEntranceLock(1);
    private final Map<String, ILogger> loggers = new ConcurrentHashMap<>();
    private final IContainerHandle container = new ContainerHandleVirtual("HitBoy");

    public static IMixinTransformer createTransformer() {
        if (factory == null) throw new IllegalStateException("Mixin did not offer its transformer factory to HitBoy.");
        return factory.createTransformer();
    }

    private ClassLoader loader() {
        return Thread.currentThread().getContextClassLoader();
    }

    @Override public String getName() { return "HitBoy"; }
    @Override public org.spongepowered.asm.service.IAdviceProvider getAdviceProvider() { return org.spongepowered.asm.service.IAdviceProvider.GENERIC; }
    @Override public org.spongepowered.asm.service.IFeatureValidator getFeatureValidator() { return org.spongepowered.asm.service.IFeatureValidator.ALLOW_ALL; }
    @Override public boolean isValid() { return true; }
    @Override public void prepare() {}
    @Override public MixinEnvironment.Phase getInitialPhase() { return MixinEnvironment.Phase.PREINIT; }
    @Override public void offer(IMixinInternal internal) {
        if (internal instanceof IMixinTransformerFactory) factory = (IMixinTransformerFactory) internal;
    }
    @Override public void init() {}
    @Override public void beginPhase() {}
    @Override public void checkEnv(Object bootstrap) {}
    @Override public ReEntranceLock getReEntranceLock() { return lock; }
    @Override public IClassProvider getClassProvider() { return this; }
    @Override public IClassBytecodeProvider getBytecodeProvider() { return this; }
    @Override public ITransformerProvider getTransformerProvider() { return null; }
    @Override public IClassTracker getClassTracker() { return null; }
    @Override public IMixinAuditTrail getAuditTrail() { return null; }
    @Override public Collection<String> getPlatformAgents() { return List.of(); }
    @Override public IContainerHandle getPrimaryContainer() { return container; }
    @Override public Collection<IContainerHandle> getMixinContainers() { return List.of(); }
    @Override public InputStream getResourceAsStream(String name) { return loader().getResourceAsStream(name); }
    @Override public String getSideName() { return System.getProperty("hitboy.environment", "CLIENT"); }
    @Override public MixinEnvironment.CompatibilityLevel getMinCompatibilityLevel() { return MixinEnvironment.CompatibilityLevel.JAVA_17; }
    @Override public MixinEnvironment.CompatibilityLevel getMaxCompatibilityLevel() { return MixinEnvironment.CompatibilityLevel.JAVA_25; }
    @Override public ILogger getLogger(String name) { return loggers.computeIfAbsent(name, LoggerAdapterConsole::new); }
    @Override public URL[] getClassPath() {
        if (loader() instanceof java.net.URLClassLoader) return ((java.net.URLClassLoader) loader()).getURLs();
        return java.util.Arrays.stream(System.getProperty("java.class.path").split(java.io.File.pathSeparator))
            .map(path -> {
                try { return new java.io.File(path).toURI().toURL(); }
                catch (java.net.MalformedURLException e) { throw new IllegalArgumentException(path, e); }
            }).toArray(URL[]::new);
    }
    @Override public Class<?> findClass(String name) throws ClassNotFoundException { return findClass(name, true); }
    @Override public Class<?> findClass(String name, boolean initialize) throws ClassNotFoundException {
        return Class.forName(name, initialize, loader());
    }
    @Override public Class<?> findAgentClass(String name, boolean initialize) throws ClassNotFoundException {
        return Class.forName(name, initialize, HitBoyMixinService.class.getClassLoader());
    }
    @Override public ClassNode getClassNode(String name) throws ClassNotFoundException, IOException {
        return getClassNode(name, true);
    }
    @Override public ClassNode getClassNode(String name, boolean runTransformers) throws ClassNotFoundException, IOException {
        return getClassNode(name, runTransformers, ClassReader.EXPAND_FRAMES);
    }
    @Override public ClassNode getClassNode(String name, boolean runTransformers, int flags) throws ClassNotFoundException, IOException {
        try (InputStream input = getResourceAsStream(name.replace('.', '/') + ".class")) {
            if (input == null) throw new ClassNotFoundException(name);
            ClassNode node = new ClassNode();
            new ClassReader(input).accept(node, flags);
            return node;
        }
    }
}
