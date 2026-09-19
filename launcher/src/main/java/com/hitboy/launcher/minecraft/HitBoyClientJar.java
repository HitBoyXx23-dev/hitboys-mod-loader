package com.hitboy.launcher.minecraft;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

final class HitBoyClientJar {
    private HitBoyClientJar() {
    }

    static File prepare(File original) throws IOException {
        File derived = new File(original.getParentFile(), original.getName().replace(".jar", "-hitboy.jar"));
        if (derived.isFile() && derived.lastModified() >= original.lastModified()) {
            return derived;
        }
        File temporary = new File(derived.getParentFile(), derived.getName() + ".download");
        try (JarInputStream input = new JarInputStream(new FileInputStream(original));
             JarOutputStream output = new JarOutputStream(new FileOutputStream(temporary))) {
            JarEntry entry;
            byte[] buffer = new byte[65536];
            while ((entry = input.getNextJarEntry()) != null) {
                if (isSignature(entry.getName())) {
                    continue;
                }
                JarEntry copy = new JarEntry(entry.getName());
                copy.setTime(entry.getTime());
                output.putNextEntry(copy);
                int read;
                while ((read = input.read(buffer)) != -1) {
                    output.write(buffer, 0, read);
                }
                output.closeEntry();
            }
        }
        java.nio.file.Files.move(temporary.toPath(), derived.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        return derived;
    }

    private static boolean isSignature(String name) {
        String upper = name.toUpperCase(Locale.ROOT);
        return upper.startsWith("META-INF/")
            && (upper.endsWith(".SF") || upper.endsWith(".RSA") || upper.endsWith(".DSA") || upper.endsWith(".EC"));
    }
}
