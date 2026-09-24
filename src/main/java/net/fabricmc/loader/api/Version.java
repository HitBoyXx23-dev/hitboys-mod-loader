package net.fabricmc.loader.api;

public interface Version extends Comparable<Version> {
    String getFriendlyString();

    static Version parse(String string) throws VersionParsingException {
        return com.hitboy.loader.fabric.FabricVersion.parse(string);
    }
}
