package com.hitboy.loader;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class HitBoyClassLoader extends ClassLoader {

    private final List<ModManager.ModInfo> loadedMods;
    
    public HitBoyClassLoader(List<ModManager.ModInfo> loadedMods) {
        super(HitBoyClassLoader.class.getClassLoader());
        this.loadedMods = loadedMods != null ? loadedMods : Collections.emptyList();
    }
    
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // The manager records the exact paths it scanned, so loading is independent of
        // the launcher working directory and any machine-specific installation path.
        for (ModManager.ModInfo mod : loadedMods) {
            try {
                if (mod.jarPath != null) {
                    byte[] b = loadClassFromJar(mod.jarPath, name);
                    if (b != null) return defineClass(name, b, 0, b.length);
                }
            } catch (IOException e) {
                throw new ClassNotFoundException("Unable to read mod JAR " + mod.jarPath, e);
            }
        }
        return super.findClass(name);
    }
    
    private byte[] loadClassFromJar(Path jarPath, String className) throws IOException {
        String resourceName = className.replace('.', '/') + ".class";
        try (JarFile jar = new JarFile(jarPath.toFile())) {
            java.util.zip.ZipEntry entry = jar.getEntry(resourceName);
            if (entry != null) {
                try (InputStream is = jar.getInputStream(entry)) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int len;
                    while ((len = is.read(buffer)) > 0) baos.write(buffer, 0, len);
                    return baos.toByteArray();
                }
            }
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry nested = entries.nextElement();
                if (nested.isDirectory() || !nested.getName().startsWith("META-INF/jars/")
                    || !nested.getName().endsWith(".jar")) continue;
                try (JarInputStream input = new JarInputStream(jar.getInputStream(nested))) {
                    JarEntry candidate;
                    while ((candidate = input.getNextJarEntry()) != null) {
                        if (!resourceName.equals(candidate.getName())) continue;
                        ByteArrayOutputStream output = new ByteArrayOutputStream();
                        input.transferTo(output);
                        return output.toByteArray();
                    }
                }
            }
        }
        return null;
    }
}
