package com.hitboy.loader;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;

public class EventBus {

    private final Map<String, CopyOnWriteArrayList<Delegate>> delegates = new ConcurrentHashMap<>();
    private final Map<Class<?>, CopyOnWriteArrayList<EventHandler>> handlers = new ConcurrentHashMap<>();
    
    @SuppressWarnings("unchecked")
    public void register(Object listener) {
        Class<?> cl = listener.getClass();
        for (Method method : cl.getMethods()) {
            if (method.isAnnotationPresent(NativeModEvent.class)) {
                String declaredEventName = method.getAnnotation(NativeModEvent.class).value().trim();
                if (declaredEventName.isEmpty()) {
                    throw new IllegalArgumentException(
                        "HitBoy mod event handler must declare a non-empty event name: "
                            + cl.getName() + "#" + method.getName()
                    );
                }
                if (method.getParameterCount() > 1) {
                    throw new IllegalArgumentException(
                        "HitBoy mod event handler must accept zero or one argument: "
                            + cl.getName() + "#" + method.getName()
                    );
                }
                String eventName = declaredEventName.toLowerCase(Locale.ROOT);
                Delegate delegate = new Delegate(listener, method);
                delegates.computeIfAbsent(eventName, k -> new CopyOnWriteArrayList<>()).add(delegate);
                Class<?>[] paramTypes = method.getParameterTypes();
                if (paramTypes.length > 0) {
                    handlers.computeIfAbsent(paramTypes[0], k -> new CopyOnWriteArrayList<>())
                        .add(new EventHandler(listener, method));
                } else {
                    handlers.computeIfAbsent(Object.class, k -> new CopyOnWriteArrayList<>())
                        .add(new EventHandler(listener, method));
                }
            }
        }
        System.out.println("EventBus registered " + listener.getClass().getSimpleName() + " delegates=" + delegates.keySet());
    }
    
    public void post(Object event) {
        if (event == null) return;
        Class<?> eventClass = event.getClass();
        List<EventHandler> eventHandlers = handlers.get(eventClass);
        if (eventHandlers != null) {
            for (EventHandler handler : eventHandlers) {
                handler.invoke(event, eventClass.getName());
            }
        }
        // fallback: also dispatch to handlers with Object param
        List<EventHandler> objectHandlers = handlers.get(Object.class);
        if (objectHandlers != null && !Object.class.equals(eventClass)) {
            for (EventHandler h : objectHandlers) {
                // avoid double invoke if already invoked via exact class
                if (eventHandlers != null && eventHandlers.contains(h)) continue;
                h.invoke(event, eventClass.getName());
            }
        }
        // Also check by event name using simpleName lowercased
        String key = eventClass.getSimpleName().toLowerCase(Locale.ROOT);
        List<Delegate> ds = delegates.get(key);
        if (ds != null) {
            for (Delegate d : ds) {
                d.invoke(event, key);
            }
        }
    }

    // Named event dispatch used by GameAgent.
    public void postEvent(String eventName, Object payload) {
        if (eventName == null) return;
        String key = eventName.toLowerCase(Locale.ROOT);
        List<Delegate> ds = delegates.get(key);
        if (ds == null) return;
        // Dispatch only to handlers explicitly registered for this event name.
        // Do NOT also fall back to the generic "handlers by parameter type" map here:
        // every native mod handler declares an Object parameter to stay
        // obfuscation-agnostic, so that map is shared across unrelated event names
        // (e.g. "render" and "hud"). Invoking it from postEvent would call a mod's
        // "render" handler with the "hud" payload (or vice versa), corrupting
        // GameContext and causing HUD elements to intermittently fail their type
        // checks and flicker.
        for (Delegate d : ds) {
            d.invoke(payload != null ? payload : eventName, eventName);
        }
    }
    
    @SuppressWarnings("unchecked")
    public <T> void subscribe(T listener) { register(listener); }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public @interface NativeModEvent {
        String value();
    }
    
    private static class Delegate {
        final Object instance; final Method method;
        Delegate(Object i, Method m) { instance=i; method=m; }
        public void invoke(Object event, String eventName) {
            try {
                if (method.getParameterCount()==0) method.invoke(instance);
                else method.invoke(instance, event);
            } catch (ReflectiveOperationException | IllegalArgumentException e) {
                reportHandlerFailure(eventName, instance, method, e);
            }
        }
    }
    private static class EventHandler {
        final Object instance; final Method method;
        EventHandler(Object i, Method m) { instance=i; method=m; }
        public void invoke(Object event, String eventName) {
            try {
                if (method.getParameterCount()==0) method.invoke(instance);
                else method.invoke(instance, event);
            } catch (ReflectiveOperationException | IllegalArgumentException e) {
                reportHandlerFailure(eventName, instance, method, e);
            }
        }
    }

    private static void reportHandlerFailure(String eventName, Object instance, Method method, Exception exception) {
        Throwable cause = exception instanceof InvocationTargetException
            && ((InvocationTargetException) exception).getCause() != null
            ? ((InvocationTargetException) exception).getCause()
            : exception;
        System.err.println(
            "HitBoy mod event handler failed [event=" + eventName
                + ", modClass=" + instance.getClass().getName()
                + ", method=" + method.getName() + "]: " + cause
        );
        cause.printStackTrace(System.err);
    }
}
