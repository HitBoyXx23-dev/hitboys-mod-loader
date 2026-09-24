package com.hitboy.loader.compat;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

public final class CompatibilityCli {
    private static final String ADAPTER_VERSION = "1";

    public int execute(String[] args, PrintStream output, PrintStream error) {
        if (args.length < 2) {
            error.println("Usage: hitboy <inspect|verify|port> <path> [--minecraft <version>] [--output <directory>]");
            return 2;
        }
        try {
            return switch (args[0].toLowerCase()) {
                case "inspect" -> inspect(Path.of(args[1]), output);
                case "verify" -> verify(Path.of(args[1]), output);
                case "port" -> port(Path.of(args[1]), option(args, "--minecraft", "1.21.11"), Path.of(option(args, "--output", "transformed-cache")), output, error);
                default -> {
                    error.println("Unknown command: " + args[0]);
                    yield 2;
                }
            };
        } catch (Exception exception) {
            error.println("HitBoy command failed: " + exception.getMessage());
            return 1;
        }
    }

    private int inspect(Path jar, PrintStream output) throws IOException {
        ModInspector.Inspection inspection = new ModInspector().inspect(jar);
        printDescriptor(inspection.getDescriptor(), output);
        printReport(inspection.getReport(), output);
        return inspection.getReport().isCompatible() ? 0 : 1;
    }

    private int verify(Path path, PrintStream output) throws IOException {
        Path modsDirectory = Files.isDirectory(path.resolve("native_mods")) ? path.resolve("native_mods") : path;
        InstanceVerifier.Result result = new InstanceVerifier().verify(modsDirectory);
        output.println("Verified directory: " + modsDirectory.toAbsolutePath().normalize());
        output.println("Discovered mods: " + result.getInspections().size());
        for (ModInspector.Inspection inspection : result.getInspections()) {
            HitBoyModDescriptor descriptor = inspection.getDescriptor();
            output.println(" - " + descriptor.getId() + " " + descriptor.getVersion() + " [" + descriptor.getSourceLoader() + "]");
        }
        printReport(result.getReport(), output);
        return result.getReport().isCompatible() ? 0 : 1;
    }

    private int port(Path source, String minecraftVersion, Path outputDirectory, PrintStream output, PrintStream error) throws IOException {
        if (Files.isDirectory(source)) {
            SourceProjectConverter.Result result = new SourceProjectConverter().convert(source, outputDirectory);
            output.println("Source loader: " + result.sourceLoader());
            output.println("Converted project: " + result.outputDirectory());
            output.println("Changed Java files: " + result.changedFiles());
            output.println("Manual compatibility items: " + result.manualItems().size());
            result.manualItems().forEach(item -> output.println(" - " + item));
            return 0;
        }
        ModInspector.Inspection inspection = new ModInspector().inspect(source);
        printDescriptor(inspection.getDescriptor(), output);
        if (inspection.getDescriptor().getSourceLoader() == SourceLoader.FABRIC) {
            for (HitBoyDependency dependency : inspection.getDescriptor().getDependencies()) {
                if (dependency.isRequired() && dependency.getId().startsWith("fabric-")) {
                    output.println("Needs Fabric API (" + dependency.getId() + "), which HitBoy does not provide yet.");
                    return 1;
                }
            }
            output.println("This Fabric mod does not need porting: HitBoy's mixed-compatibility mode runs Fabric mods");
            output.println("directly on Minecraft 1.21.11 (Mixins, access wideners, and Fabric Loader API included).");
            output.println("Put it in the mods folder and start hitboy-mixed-mod-compatibility.exe, or install the");
            output.println("profile with the patcher's \"Mixed Compatibility\" option. Mods that need Fabric API are not supported yet.");
            return 0;
        }
        if (!inspection.getReport().isCompatible()) {
            error.println("Port blocked because required compatibility features are not implemented.");
            return 1;
        }
        if (inspection.getDescriptor().getSourceLoader() != SourceLoader.HITBOY) {
            error.println("Port blocked because this foreign mod still requires a HitBoy compatibility adapter.");
            return 1;
        }
        String key = new ContentAddressedCache().key(source, minecraftVersion, ADAPTER_VERSION, System.getProperty("java.version"));
        Path destination = outputDirectory.resolve(minecraftVersion).resolve(key).resolve(source.getFileName());
        Files.createDirectories(destination.getParent());
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        output.println("Native HitBoy mod cached at: " + destination.toAbsolutePath().normalize());
        return 0;
    }

    private void printDescriptor(HitBoyModDescriptor descriptor, PrintStream output) {
        output.println("Mod: " + descriptor.getName() + " (" + descriptor.getId() + ")");
        output.println("Version: " + descriptor.getVersion());
        output.println("Source loader: " + descriptor.getSourceLoader());
        output.println("Environment: " + descriptor.getEnvironment());
        printValues("Entrypoints", descriptor.getEntrypoints(), output);
        if (!descriptor.getDependencies().isEmpty()) {
            output.println("Dependencies:");
            for (HitBoyDependency dependency : descriptor.getDependencies()) {
                output.println(" - " + dependency.getId() + " " + dependency.getVersionRange() + (dependency.isRequired() ? " required" : " optional"));
            }
        }
        printList("Mixins", descriptor.getMixinConfigs(), output);
        printList("Nested JARs", descriptor.getNestedJars(), output);
        if (descriptor.getAccessWidener() != null) output.println("Access widener: " + descriptor.getAccessWidener());
    }

    private void printValues(String label, Map<String, List<String>> values, PrintStream output) {
        if (values.isEmpty()) return;
        output.println(label + ":");
        values.forEach((type, entries) -> entries.forEach(entry -> output.println(" - " + type + ": " + entry)));
    }

    private void printList(String label, List<String> values, PrintStream output) {
        if (values.isEmpty()) return;
        output.println(label + ":");
        values.forEach(value -> output.println(" - " + value));
    }

    private void printReport(CompatibilityReport report, PrintStream output) {
        output.println("Compatibility: " + (report.isCompatible() ? "PASS" : "BLOCKED"));
        if (!report.getIssues().isEmpty()) output.println(report.format());
    }

    private String option(String[] args, String name, String defaultValue) {
        for (int index = 2; index + 1 < args.length; index++) {
            if (name.equals(args[index])) return args[index + 1];
        }
        return defaultValue;
    }
}
