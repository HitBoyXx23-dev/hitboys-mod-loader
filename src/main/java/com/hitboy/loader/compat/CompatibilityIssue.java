package com.hitboy.loader.compat;

import java.util.Objects;

public final class CompatibilityIssue {
    public enum Severity { INFO, WARNING, ERROR }

    private final Severity severity;
    private final String code;
    private final String message;

    public CompatibilityIssue(Severity severity, String code, String message) {
        this.severity = Objects.requireNonNull(severity, "severity");
        this.code = Objects.requireNonNull(code, "code");
        this.message = Objects.requireNonNull(message, "message");
    }

    public Severity getSeverity() { return severity; }
    public String getCode() { return code; }
    public String getMessage() { return message; }
}
