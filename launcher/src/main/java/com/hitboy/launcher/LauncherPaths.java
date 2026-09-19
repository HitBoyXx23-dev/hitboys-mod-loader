package com.hitboy.launcher;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

public final class LauncherPaths {
    private static final String PATCH_JAR = "hitboys-mod-loader-patch-1.0.0-SNAPSHOT.jar";

    private LauncherPaths() {
    }

    public static File findLoaderJar(Class<?> anchor) {
        File codeSource = codeSourceFile(anchor);
        if (codeSource != null && codeSource.isFile()) {
            File bundledJar = new File(codeSource.getParentFile(), PATCH_JAR);
            if (bundledJar.isFile()) {
                return bundledJar;
            }
        }

        File workingDirectory = new File(System.getProperty("user.dir"));
        File[] candidates = {
            new File(workingDirectory, "app" + File.separator + PATCH_JAR),
            new File(workingDirectory, PATCH_JAR),
            new File(workingDirectory, "target" + File.separator + PATCH_JAR),
            new File(workingDirectory, ".." + File.separator + "target" + File.separator + PATCH_JAR),
            new File(workingDirectory, "target" + File.separator + "hitboys-mod-loader-1.0.0-SNAPSHOT.jar")
        };
        for (File candidate : candidates) {
            if (candidate.isFile()) {
                return candidate;
            }
        }
        return null;
    }

    private static File codeSourceFile(Class<?> anchor) {
        CodeSource codeSource = anchor.getProtectionDomain().getCodeSource();
        if (codeSource == null) {
            return null;
        }
        try {
            return new File(codeSource.getLocation().toURI());
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
