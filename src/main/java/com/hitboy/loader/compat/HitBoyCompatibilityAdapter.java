package com.hitboy.loader.compat;

import java.io.IOException;
import java.nio.file.Path;
import java.util.jar.JarFile;

public interface HitBoyCompatibilityAdapter {
    SourceLoader getSourceLoader();
    boolean supports(JarFile jar);
    HitBoyModDescriptor readDescriptor(Path path, JarFile jar) throws IOException;
    void analyze(HitBoyModDescriptor descriptor, JarFile jar, CompatibilityReport report) throws IOException;
}
