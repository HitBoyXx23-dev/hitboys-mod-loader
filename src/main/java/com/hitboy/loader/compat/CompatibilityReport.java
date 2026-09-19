package com.hitboy.loader.compat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CompatibilityReport {
    private final List<CompatibilityIssue> issues = new ArrayList<>();

    public void add(CompatibilityIssue.Severity severity, String code, String message) {
        issues.add(new CompatibilityIssue(severity, code, message));
    }

    public List<CompatibilityIssue> getIssues() {
        return Collections.unmodifiableList(issues);
    }

    public boolean isCompatible() {
        return issues.stream().noneMatch(issue -> issue.getSeverity() == CompatibilityIssue.Severity.ERROR);
    }

    public String format() {
        StringBuilder output = new StringBuilder();
        for (CompatibilityIssue issue : issues) {
            if (output.length() > 0) output.append(System.lineSeparator());
            output.append(issue.getSeverity()).append(" [").append(issue.getCode()).append("] ").append(issue.getMessage());
        }
        return output.toString();
    }
}
