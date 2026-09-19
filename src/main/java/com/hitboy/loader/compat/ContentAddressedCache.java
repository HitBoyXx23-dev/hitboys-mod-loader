package com.hitboy.loader.compat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class ContentAddressedCache {
    public String key(Path source, String minecraftVersion, String adapterVersion, String javaVersion) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            try (InputStream input = Files.newInputStream(source)) {
                byte[] buffer = new byte[65536];
                int read;
                while ((read = input.read(buffer)) != -1) digest.update(buffer, 0, read);
            }
            update(digest, minecraftVersion);
            update(digest, adapterVersion);
            update(digest, javaVersion);
            return java.util.HexFormat.of().formatHex(digest.digest());
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is unavailable", exception);
        }
    }

    private void update(MessageDigest digest, String value) {
        digest.update((byte) 0);
        digest.update(value.getBytes(StandardCharsets.UTF_8));
    }
}
