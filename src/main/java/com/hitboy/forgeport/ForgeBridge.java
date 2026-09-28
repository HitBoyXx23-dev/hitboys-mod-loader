package com.hitboy.forgeport;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Template copied into every Forge mod that port.exe converts to NeoForge (renamed into the mod's own
 * package). It stands in for the Forge 26 APIs whose NeoForge versions work differently. It only uses
 * reflection, so the ported mod needs neither HitBoy nor anything else at runtime.
 */
public final class ForgeBridge {
    private ForgeBridge() {
    }

    /**
     * Forge's {@code BusGroup.register(lookup, listener)}: every {@code @SubscribeEvent} method of
     * {@code listener} (an object, or a class for static methods) becomes a NeoForge listener. Forge 26
     * listeners may return {@code true} to cancel the event; that becomes {@code setCanceled(true)}.
     */
    public static Collection<Object> register(Object busGroup, MethodHandles.Lookup lookup, Object listener) {
        Class<?> type = listener instanceof Class ? (Class<?>) listener : listener.getClass();
        Object target = listener instanceof Class ? null : listener;
        for (Method method : type.getDeclaredMethods()) {
            if (method.getParameterCount() != 1 || !subscribed(method)) continue;
            if ((target == null) != Modifier.isStatic(method.getModifiers())) continue;
            method.setAccessible(true);
            Class<?> eventType = method.getParameterTypes()[0];
            Consumer<Object> consumer = event -> {
                try {
                    if (Boolean.TRUE.equals(method.invoke(target, event))) cancel(event);
                } catch (InvocationTargetException failure) {
                    throw failure.getCause() instanceof RuntimeException runtime ? runtime : new RuntimeException(failure.getCause());
                } catch (ReflectiveOperationException failure) {
                    throw new RuntimeException(failure);
                }
            };
            try {
                Class<?> busType = Class.forName("net.neoforged.bus.api.IEventBus");
                busType.getMethod("addListener", Class.class, Consumer.class).invoke(busFor(eventType), eventType, consumer);
            } catch (ReflectiveOperationException failure) {
                throw new IllegalStateException("Could not register " + method + " with NeoForge", failure);
            }
        }
        return List.of();
    }

    /**
     * Called by the generated {@code @Mod} entry class instead of the mod's own constructor. Forge creates mods
     * once Minecraft is running; NeoForge creates them earlier. A mod whose constructor fails because Minecraft
     * is not running yet is created at NeoForge's client-setup step instead.
     */
    public static void construct(Class<?> modClass) {
        try {
            instantiate(modClass);
        } catch (Throwable failure) {
            if (minecraftRunning(modClass)) throw failure instanceof RuntimeException runtime ? runtime : new RuntimeException(failure);
            System.out.println("[HitBoy port] " + modClass.getName() + " needs Minecraft to be running; creating it at client setup instead.");
            try {
                Class<?> setup = Class.forName("net.neoforged.fml.event.lifecycle.FMLClientSetupEvent", true, modClass.getClassLoader());
                Consumer<Object> later = event -> instantiate(modClass);
                Class.forName("net.neoforged.bus.api.IEventBus").getMethod("addListener", Class.class, Consumer.class)
                    .invoke(busFor(setup), setup, later);
            } catch (ReflectiveOperationException unavailable) {
                throw new IllegalStateException("Could not create " + modClass.getName(), failure);
            }
        }
    }

    private static void instantiate(Class<?> modClass) {
        try {
            var constructor = modClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        } catch (InvocationTargetException failure) {
            throw failure.getCause() instanceof RuntimeException runtime ? runtime : new RuntimeException(failure.getCause());
        } catch (ReflectiveOperationException failure) {
            throw new RuntimeException(failure);
        }
    }

    private static boolean minecraftRunning(Class<?> modClass) {
        try {
            Class<?> minecraft = Class.forName("net.minecraft.client.Minecraft", false, modClass.getClassLoader());
            return minecraft.getMethod("getInstance").invoke(null) != null;
        } catch (ReflectiveOperationException | LinkageError | RuntimeException unavailable) {
            return true; // not a client or not loadable: keep the original failure
        }
    }

    /** Forge's {@code MinecraftForge.registerConfigScreen(factory)}: NeoForge's IConfigScreenFactory extension point. */
    public static void registerConfigScreen(Function<Object, Object> factory) {
        try {
            Object container = activeContainer();
            Class<?> point = Class.forName("net.neoforged.neoforge.client.gui.IConfigScreenFactory");
            InvocationHandler handler = (proxy, method, arguments) -> {
                if (method.getName().equals("createScreen")) return factory.apply(arguments[arguments.length - 1]);
                if (method.isDefault()) return InvocationHandler.invokeDefault(proxy, method, arguments);
                if (method.getName().equals("hashCode")) return System.identityHashCode(proxy);
                if (method.getName().equals("equals")) return proxy == arguments[0];
                return "HitBoy config screen";
            };
            Object extension = Proxy.newProxyInstance(point.getClassLoader(), new Class<?>[] {point}, handler);
            Class.forName("net.neoforged.fml.ModContainer").getMethod("registerExtensionPoint", Class.class, Class.forName("net.neoforged.fml.IExtensionPoint"))
                .invoke(container, point, extension);
        } catch (ReflectiveOperationException | RuntimeException failure) {
            System.err.println("[HitBoy port] Could not register the config screen: " + failure);
        }
    }

    private static boolean subscribed(Method method) {
        for (var annotation : method.getAnnotations()) {
            if (annotation.annotationType().getName().equals("net.neoforged.bus.api.SubscribeEvent")) return true;
        }
        return false;
    }

    /** Mod-lifecycle events go to the mod's own bus, everything else to NeoForge's game bus. */
    private static Object busFor(Class<?> eventType) throws ReflectiveOperationException {
        Class<?> modBusEvent = Class.forName("net.neoforged.fml.event.IModBusEvent");
        if (modBusEvent.isAssignableFrom(eventType)) {
            return Class.forName("net.neoforged.fml.ModContainer").getMethod("getEventBus").invoke(activeContainer());
        }
        return Class.forName("net.neoforged.neoforge.common.NeoForge").getField("EVENT_BUS").get(null);
    }

    private static Object activeContainer() throws ReflectiveOperationException {
        Class<?> context = Class.forName("net.neoforged.fml.ModLoadingContext");
        return context.getMethod("getActiveContainer").invoke(context.getMethod("get").invoke(null));
    }

    private static void cancel(Object event) throws ReflectiveOperationException {
        Class.forName("net.neoforged.bus.api.ICancellableEvent").getMethod("setCanceled", boolean.class).invoke(event, true);
    }
}
