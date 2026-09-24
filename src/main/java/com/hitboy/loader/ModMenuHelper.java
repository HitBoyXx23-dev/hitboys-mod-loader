package com.hitboy.loader;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public final class ModMenuHelper {
    private static final java.util.Map<Object, Object> INJECTED_BUTTONS = new WeakHashMap<>();

    private ModMenuHelper() {
    }

    public static void drawClientIndicator(Object screen, Object graphics) {
    }

    public static void injectModsButton(Object titleScreen) {
        // Re-add after the title screen rebuilds its widgets (for example on window resize).
        synchronized (INJECTED_BUTTONS) {
            Object existing = INJECTED_BUTTONS.get(titleScreen);
            if (existing != null && childrenOf(titleScreen).contains(existing)) {
                return;
            }
        }
        String label = "Mods";
        if (tryInjectNamedButton(titleScreen, label)
            || tryInjectObfuscated12111Button(titleScreen, label)
            || tryInjectObfuscated1201Button(titleScreen, label)) {
            return;
        }
        System.err.println("HitBoy's Mod Loader could not add the Mods button: no supported Minecraft GUI API was found.");
    }

    private static void remember(Object titleScreen, Object button) {
        synchronized (INJECTED_BUTTONS) {
            INJECTED_BUTTONS.put(titleScreen, button);
        }
    }

    /** The screen's widget list (Screen#children), or an empty list. */
    private static java.util.List<?> childrenOf(Object screen) {
        // Read the field from Minecraft's base Screen class only; subclasses reuse short obfuscated names.
        for (Class<?> type = screen.getClass(); type != null; type = type.getSuperclass()) {
            String field = "net.minecraft.client.gui.screens.Screen".equals(type.getName()) ? "children"
                : "gsb".equals(type.getName()) ? "d" : null;
            if (field == null) continue;
            try {
                Field children = type.getDeclaredField(field);
                children.setAccessible(true);
                Object value = children.get(screen);
                if (value instanceof java.util.List) return (java.util.List<?>) value;
            } catch (ReflectiveOperationException ignored) {
                // fall through
            }
        }
        return java.util.Collections.emptyList();
    }

    /**
     * Keeps the Realms button: finds the full-width button in the Realms row, shrinks it to the left
     * half, and returns bounds for the right half (x, y, width). Without a Realms button the Mods
     * button takes the whole row.
     */
    private static int[] boundsBesideRealms(Object screen, int width, int height,
        String getX, String getY, String getWidth, String setWidth) {
        int x = width / 2 - 100;
        int y = height / 4 + 96;
        for (Object widget : childrenOf(screen)) {
            try {
                Class<?> type = widget.getClass();
                if ((int) type.getMethod(getX).invoke(widget) != x || (int) type.getMethod(getY).invoke(widget) != y
                    || (int) type.getMethod(getWidth).invoke(widget) != 200) continue;
                type.getMethod(setWidth, int.class).invoke(widget, 98);
                return new int[] {x + 102, y, 98};
            } catch (ReflectiveOperationException | RuntimeException ignored) {
                // not a button with these accessors
            }
        }
        return new int[] {x, y, 200};
    }

    private static boolean tryInjectObfuscated12111Button(Object titleScreen, String label) {
        try {
            ClassLoader loader = titleScreen.getClass().getClassLoader();
            Class<?> componentClass = Class.forName("yh", true, loader);
            Class<?> onPressClass = Class.forName("gje$c", true, loader);
            Class<?> buttonClass = Class.forName("gje", true, loader);
            Object component = componentClass.getMethod("b", String.class).invoke(null, label);
            Object onPress = java.lang.reflect.Proxy.newProxyInstance(
                loader,
                new Class[]{onPressClass},
                (proxy, method, args) -> {
                    if (method.getDeclaringClass() != Object.class) {
                        openObfuscated12111ModsScreen(titleScreen);
                    }
                    return null;
                }
            );
            Object builder = buttonClass.getMethod("a", componentClass, onPressClass).invoke(null, component, onPress);
            int width = getIntField(titleScreen, "o");
            int height = getIntField(titleScreen, "p");
            int[] bounds = boundsBesideRealms(titleScreen, width, height, "aT_", "aU_", "aS_", "c");
            builder = builder.getClass()
                .getMethod("a", int.class, int.class, int.class, int.class)
                .invoke(builder, bounds[0], bounds[1], bounds[2], 20);
            Object button = builder.getClass().getMethod("a").invoke(builder);
            Method add = findCompatibleMethod(titleScreen.getClass(), "c", button.getClass());
            if (add == null) {
                return false;
            }
            add.invoke(titleScreen, button);
            remember(titleScreen, button);
            System.out.println("Injected 1.21.11 " + label + " button.");
            return true;
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }

    private static boolean tryInjectNamedButton(Object titleScreen, String label) {
        try {
            ClassLoader loader = titleScreen.getClass().getClassLoader();
            Class<?> buttonClass = Class.forName("net.minecraft.client.gui.components.Button", true, loader);
            Class<?> componentClass = Class.forName("net.minecraft.network.chat.Component", true, loader);
            Object component = componentClass.getMethod("literal", String.class).invoke(null, label);
            Class<?> onPressClass = Class.forName("net.minecraft.client.gui.components.Button$OnPress", true, loader);
            Object onPress = java.lang.reflect.Proxy.newProxyInstance(
                loader,
                new Class[]{onPressClass},
                (proxy, method, args) -> {
                    if (method.getDeclaringClass() != Object.class) {
                        openNamedModsScreen(titleScreen);
                    }
                    return null;
                }
            );
            Object builder = buttonClass.getMethod("builder", componentClass, onPressClass).invoke(null, component, onPress);
            int width = getIntField(titleScreen, "width", "field_22789", "g");
            int height = getIntField(titleScreen, "height", "field_22790", "h");
            int[] bounds = boundsBesideRealms(titleScreen, width, height, "getX", "getY", "getWidth", "setWidth");
            builder = builder.getClass()
                .getMethod("bounds", int.class, int.class, int.class, int.class)
                .invoke(builder, bounds[0], bounds[1], bounds[2], 20);
            Object button = builder.getClass().getMethod("build").invoke(builder);
            Method add = findMethod(titleScreen.getClass(), "addRenderableWidget", "method_25429", "addWidget");
            if (add == null) {
                return false;
            }
            add.invoke(titleScreen, button);
            remember(titleScreen, button);
            System.out.println("Injected native " + label + " button.");
            return true;
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }

    private static boolean tryInjectObfuscated1201Button(Object titleScreen, String label) {
        try {
            ClassLoader loader = titleScreen.getClass().getClassLoader();
            Class<?> componentClass = Class.forName("sw", true, loader);
            Class<?> onPressClass = Class.forName("epi$c", true, loader);
            Class<?> buttonClass = Class.forName("epi", true, loader);
            Object component = componentClass.getMethod("b", String.class).invoke(null, label);
            Object onPress = java.lang.reflect.Proxy.newProxyInstance(
                loader,
                new Class[]{onPressClass},
                (proxy, method, args) -> {
                    if (method.getDeclaringClass() != Object.class) {
                        openObfuscated1201ModsScreen(titleScreen);
                    }
                    return null;
                }
            );
            Method builderFactory = buttonClass.getMethod("a", componentClass, onPressClass);
            Object builder = builderFactory.invoke(null, component, onPress);
            builder = builder.getClass()
                .getMethod("a", int.class, int.class, int.class, int.class)
                .invoke(builder, getIntField(titleScreen, "g") - 106, 6, 100, 20);
            Object button = builder.getClass().getMethod("a").invoke(builder);
            Method add = findMethod(titleScreen.getClass(), "d");
            if (add == null) {
                return false;
            }
            add.invoke(titleScreen, button);
            System.out.println("Injected 1.20.1 " + label + " button.");
            return true;
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }

    private static void openNamedModsScreen(Object titleScreen) {
        try {
            ClassLoader loader = titleScreen.getClass().getClassLoader();
            Class<?> componentClass = Class.forName("net.minecraft.network.chat.Component", true, loader);
            Class<?> screenClass = Class.forName("net.minecraft.client.gui.screens.Screen", true, loader);
            Class<?> alertScreenClass = Class.forName("net.minecraft.client.gui.screens.AlertScreen", true, loader);
            Class<?> minecraftClass = Class.forName("net.minecraft.client.Minecraft", true, loader);
            Object title = componentClass.getMethod("literal", String.class).invoke(null, "Meteor Client - HitBoy's Mod Loader Edition");
            Object message = componentClass.getMethod("literal", String.class).invoke(null, installedModSummary());
            Object minecraft = minecraftClass.getMethod("getInstance").invoke(null);
            Runnable returnToTitle = () -> setNamedScreen(minecraft, screenClass, titleScreen);
            Constructor<?> constructor = alertScreenClass.getConstructor(Runnable.class, componentClass, componentClass);
            Object modsScreen = constructor.newInstance(returnToTitle, title, message);
            setNamedScreen(minecraft, screenClass, modsScreen);
        } catch (ReflectiveOperationException e) {
            System.err.println("HitBoy's Mod Loader could not open the named Mods screen: " + e.getMessage());
        }
    }

    public static void injectForCurrentScreen(Object minecraft) {
        if (minecraft == null) {
            return;
        }
        Object screen = getFieldValue(minecraft, "screen", "currentScreen", "field_1755", "x");
        if (screen == null) {
            return;
        }
        String className = screen.getClass().getName();
        if (!"gsd".equals(className) && !className.endsWith("TitleScreen")
            && !className.endsWith("GuiMainMenu") && !className.endsWith("MainMenuScreen")) {
            return;
        }
        injectModsButton(screen);
    }

    private static void setNamedScreen(Object minecraft, Class<?> screenClass, Object screen) {
        try {
            minecraft.getClass().getMethod("setScreen", screenClass).invoke(minecraft, screen);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Unable to switch the Minecraft screen", e);
        }
    }

    private static void openObfuscated1201ModsScreen(Object titleScreen) {
        try {
            ClassLoader loader = titleScreen.getClass().getClassLoader();
            Class<?> componentClass = Class.forName("sw", true, loader);
            Class<?> screenClass = Class.forName("euq", true, loader);
            Class<?> alertScreenClass = Class.forName("ete", true, loader);
            Object title = componentClass.getMethod("b", String.class).invoke(null, "HitBoy's Loaded Mods");
            Object message = componentClass.getMethod("b", String.class).invoke(null, installedModSummary());
            Object minecraft = minecraftInstance(loader);
            Runnable returnToTitle = () -> setScreen(minecraft, screenClass, titleScreen);
            Constructor<?> constructor = alertScreenClass.getConstructor(Runnable.class, componentClass, componentClass);
            Object modsScreen = constructor.newInstance(returnToTitle, title, message);
            setScreen(minecraft, screenClass, modsScreen);
            System.out.println("Opened 1.20.1 HitBoy's Loaded Mods screen: " + installedModSummary());
        } catch (ReflectiveOperationException e) {
            System.err.println("HitBoy's Mod Loader could not open the Mods screen: " + e.getMessage());
        }
    }

    public static void openModsScreen(Object titleScreen) {
        openObfuscated12111ModsScreen(titleScreen);
    }

    private static void openObfuscated12111ModsScreen(Object titleScreen) {
        try {
            ClassLoader loader = titleScreen.getClass().getClassLoader();
            Class<?> componentClass = Class.forName("yh", true, loader);
            Class<?> screenClass = Class.forName("gsb", true, loader);
            Class<?> alertScreenClass = Class.forName("gqv", true, loader);
            Object title = componentClass.getMethod("b", String.class).invoke(null, "HitBoy's Loaded Mods");
            Object message = componentClass.getMethod("b", String.class).invoke(null, installedModSummary());
            Object minecraft = minecraftInstance12111(loader);
            Runnable returnToTitle = () -> setScreen(minecraft, screenClass, titleScreen);
            Constructor<?> constructor = alertScreenClass.getConstructor(Runnable.class, componentClass, componentClass);
            Object modsScreen = constructor.newInstance(returnToTitle, title, message);
            setScreen(minecraft, screenClass, modsScreen);
            System.out.println("Opened 1.21.11 HitBoy's Loaded Mods screen: " + installedModSummary());
        } catch (ReflectiveOperationException e) {
            System.err.println("HitBoy's Mod Loader could not open the 1.21.11 Mods screen: " + e.getMessage());
        }
    }

    private static Object minecraftInstance(ClassLoader loader) throws ReflectiveOperationException {
        Class<?> minecraftClass = Class.forName("enn", true, loader);
        Field instance = minecraftClass.getDeclaredField("F");
        instance.setAccessible(true);
        return instance.get(null);
    }

    private static Object minecraftInstance12111(ClassLoader loader) throws ReflectiveOperationException {
        Class<?> minecraftClass = Class.forName("gfj", true, loader);
        Field instance = minecraftClass.getDeclaredField("A");
        instance.setAccessible(true);
        return instance.get(null);
    }

    private static void setScreen(Object minecraft, Class<?> screenClass, Object screen) {
        try {
            minecraft.getClass().getMethod("a", screenClass).invoke(minecraft, screen);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Unable to switch the Minecraft screen", e);
        }
    }

    private static String installedModSummary() {
        ModManager manager = NativeLoader.getModManager();
        if (manager != null) {
            StringBuilder summary = new StringBuilder();
            for (ModManager.ModInfo mod : manager.getLoadedMods()) {
                if (summary.length() > 0) {
                    summary.append('\n');
                }
                summary.append(mod.name).append(" v").append(mod.version);
            }
            if (summary.length() > 0) {
                return summary.toString();
            }
        }
        File[] jars = modsDirectory().listFiles((directory, name) -> name.endsWith(".jar"));
        if (jars == null || jars.length == 0) {
            return "No HitBoy mods installed.";
        }
        StringBuilder summary = new StringBuilder();
        for (File jar : jars) {
            if (summary.length() > 0) {
                summary.append('\n');
            }
            summary.append(jar.getName());
        }
        return summary.toString();
    }

    private static File modsDirectory() {
        String configured = System.getProperty("hitboy.mods-dir");
        if (configured != null && !configured.isEmpty()) {
            return new File(configured);
        }
        String gameDirectory = System.getProperty("hitboy.game-directory", ".hitboys-modloader");
        String hitBoyHome = System.getProperty("hitboy.home", gameDirectory);
        return new File(hitBoyHome, "native_mods");
    }

    private static int countMods() {
        ModManager manager = NativeLoader.getModManager();
        if (manager != null) {
            return manager.getLoadedModCount();
        }
        File[] jars = modsDirectory().listFiles((directory, name) -> name.endsWith(".jar"));
        return jars == null ? 0 : jars.length;
    }

    private static int getIntField(Object target, String... names) {
        for (String name : names) {
            for (Class<?> type = target.getClass(); type != null; type = type.getSuperclass()) {
                try {
                    Field field = type.getDeclaredField(name);
                    field.setAccessible(true);
                    return ((Number) field.get(target)).intValue();
                } catch (NoSuchFieldException | IllegalAccessException ignored) {
                    // Try the next field name or superclass.
                }
            }
        }
        return 100;
    }

    private static Object getFieldValue(Object target, String... names) {
        for (String name : names) {
            for (Class<?> type = target.getClass(); type != null; type = type.getSuperclass()) {
                try {
                    Field field = type.getDeclaredField(name);
                    field.setAccessible(true);
                    return field.get(target);
                } catch (NoSuchFieldException | IllegalAccessException ignored) {
                }
            }
        }
        return null;
    }

    private static Method findMethod(Class<?> type, String... names) {
        for (String name : names) {
            for (Class<?> current = type; current != null; current = current.getSuperclass()) {
                for (Method method : current.getDeclaredMethods()) {
                    if (name.equals(method.getName()) && method.getParameterCount() == 1) {
                        method.setAccessible(true);
                        return method;
                    }
                }
            }
        }
        return null;
    }

    private static Method findCompatibleMethod(Class<?> type, String name, Class<?> argumentType) {
        for (Class<?> current = type; current != null; current = current.getSuperclass()) {
            for (Method method : current.getDeclaredMethods()) {
                if (name.equals(method.getName()) && method.getParameterCount() == 1
                    && method.getReturnType() != void.class
                    && method.getParameterTypes()[0].isAssignableFrom(argumentType)) {
                    method.setAccessible(true);
                    return method;
                }
            }
        }
        return null;
    }
}
