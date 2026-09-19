package com.hitboy.loader.compat;

import java.util.Objects;

public final class HitBoyDependency {
    private final String id;
    private final String versionRange;
    private final boolean required;

    public HitBoyDependency(String id, String versionRange, boolean required) {
        this.id = Objects.requireNonNull(id, "id");
        this.versionRange = versionRange == null || versionRange.isBlank() ? "*" : versionRange;
        this.required = required;
    }

    public String getId() {
        return id;
    }

    public String getVersionRange() {
        return versionRange;
    }

    public boolean isRequired() {
        return required;
    }
}
