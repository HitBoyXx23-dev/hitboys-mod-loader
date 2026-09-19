package com.hitboy.launcher.mods;

import java.io.File;
import java.nio.file.*;
import java.util.*;

public class ModRegistry {
    public static class Entry {
        public String id, name, version, description;
        public boolean enabled;
        public File jar;
    }

    public static List<Entry> scan(String modsDir) {
        List<Entry> out = new ArrayList<>();
        File dir = new File(modsDir);
        if (!dir.exists()) return out;
        File[] jars = dir.listFiles((d,n)->n.endsWith(".jar"));
        if (jars==null) return out;
        for (File jar : jars) {
            Entry e = new Entry();
            e.jar = jar; e.id = jar.getName().replace(".jar","");
            e.name = e.id; e.version="1.0.0"; e.description="HitBoy native mod";
            e.enabled = !new File(dir, e.id + ".disabled").exists();
            // Try to read hitboy.json inside the JAR.
            try (java.util.jar.JarFile jf = new java.util.jar.JarFile(jar)) {
                var je = jf.getJarEntry("hitboy.json");
                if (je != null) {
                    String json = new String(jf.getInputStream(je).readAllBytes());
                    if (json.contains("\"name\"")) {
                        int s=json.indexOf("\"name\""); s=json.indexOf('"', json.indexOf(':', s)+1)+1; int en=json.indexOf('"', s); if(en>s) e.name=json.substring(s,en);
                    }
                    if (json.contains("\"version\"")) {
                        int s=json.indexOf("\"version\""); s=json.indexOf('"', json.indexOf(':', s)+1)+1; int en=json.indexOf('"', s); if(en>s) e.version=json.substring(s,en);
                    }
                }
            } catch (Exception ignored) {}
            out.add(e);
        }
        return out;
    }

    public static void setEnabled(String id, boolean enabled) {
        String dir = com.hitboy.launcher.NavigationContext.getNativeModsDirectory();
        if (dir==null) dir="native_mods";
        File flag = new File(dir, id + ".disabled");
        try {
            if (enabled) { if (flag.exists()) flag.delete(); }
            else { flag.createNewFile(); }
        } catch (Exception ignored) {}
    }
}
