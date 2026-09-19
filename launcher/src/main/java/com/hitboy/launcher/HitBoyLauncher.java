package com.hitboy.launcher;

import com.hitboy.loader.EventBus;
import com.hitboy.loader.ModManager;
import com.google.gson.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

/**
 * HitBoy's Mod Loader - launcher entry point (GUI + CLI).
 * Double-click: persistent GUI. CLI: --version etc.
 */
public class HitBoyLauncher {

    private String gameDirectory;
    private String minecraftVersion;
    private String nativeModsDirectory;
    private String mappingsFile;
    private boolean installOfficialPatch;
    private boolean mixedCompatibility;
    private String officialGameDirectory;

    public static void main(String[] args) {
        if (args.length > 0 && isCompatibilityCommand(args[0])) {
            int exitCode = new com.hitboy.loader.compat.CompatibilityCli().execute(args, System.out, System.err);
            if (exitCode != 0) System.exit(exitCode);
            return;
        }
        if (args.length == 0) {
            SwingUtilities.invokeLater(() -> {
                try { new com.hitboy.launcher.ui.HitBoyLauncherFrame().setVisible(true); }
                catch (Exception e) { new HitBoyLauncher().showLauncherGUI(); }
            });
            return;
        }
        if (Arrays.asList(args).contains("--help") || Arrays.asList(args).contains("-h")) {
            printHelp();
            return;
        }
        HitBoyLauncher launcher = new HitBoyLauncher();
        launcher.parseArgs(args);
        if (launcher.installOfficialPatch) {
            launcher.installOfficialPatch();
            return;
        }
        launcher.launch(false);
    }

    private static boolean isCompatibilityCommand(String value) {
        return value.equalsIgnoreCase("inspect") || value.equalsIgnoreCase("verify") || value.equalsIgnoreCase("port");
    }

    private void showLauncherGUI() {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        JFrame f = new JFrame("HitBoy's Mod Loader v1.0.0");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(560, 420);
        f.setLocationRelativeTo(null);
        try {
            Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png"));
            if (icon != null) f.setIconImage(icon);
        } catch (Exception ignored) {}

        JPanel root = new JPanel(new BorderLayout(10,10));
        root.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        JLabel title = new JLabel("HitBoy's Mod Loader");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        JLabel sub = new JLabel("Custom Native Mod Loader  \u2022  No Fabric/Forge");
        sub.setForeground(Color.GRAY);
        header.add(title, BorderLayout.NORTH);
        header.add(sub, BorderLayout.SOUTH);
        root.add(header, BorderLayout.NORTH);

        // Form
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6); c.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<String> verBox = new JComboBox<>(new String[]{"1.21.11", "1.20.1", "1.16.5"});
        verBox.setSelectedItem("1.21.11");
        JTextField dirField = new JTextField(NavigationContext.defaultGameDirectory());
        JTextField modsField = new JTextField(dirField.getText() + File.separator + "native_mods");
        dirField.addActionListener(e -> modsField.setText(dirField.getText() + File.separator + "native_mods"));

        c.gridx=0; c.gridy=0; form.add(new JLabel("Minecraft Version:"), c);
        c.gridx=1; c.weightx=1; form.add(verBox, c);
        c.gridx=0; c.gridy=1; c.weightx=0; form.add(new JLabel("Game Directory:"), c);
        c.gridx=1; c.weightx=1; form.add(dirField, c);
        c.gridx=0; c.gridy=2; form.add(new JLabel("Mods Directory:"), c);
        c.gridx=1; form.add(modsField, c);

        JPanel mid = new JPanel(new BorderLayout(6,6));
        mid.add(form, BorderLayout.NORTH);
        JTextArea log = new JTextArea(8, 40);
        log.setEditable(false); log.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        log.setText("Ready. Click Launch to start HitBoy's Mod Loader.\nMods are loaded from native_mods/ with topological sort.\n");
        mid.add(new JScrollPane(log), BorderLayout.CENTER);
        root.add(mid, BorderLayout.CENTER);

        // Buttons
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton openMods = new JButton("Open Mods Folder");
        JButton launch = new JButton("LAUNCH");
        launch.setBackground(new Color(88,166,255)); launch.setForeground(Color.WHITE);
        launch.setFont(new Font("Segoe UI", Font.BOLD, 14)); launch.setPreferredSize(new Dimension(120,36));
        btns.add(openMods); btns.add(launch);
        root.add(btns, BorderLayout.SOUTH);

        openMods.addActionListener(e -> {
            try { Desktop.getDesktop().open(new File(modsField.getText())); }
            catch (Exception ex) {
                try { Files.createDirectories(Paths.get(modsField.getText())); Desktop.getDesktop().open(new File(modsField.getText())); }
                catch (Exception ex2) { JOptionPane.showMessageDialog(f, ex2.getMessage()); }
            }
        });

        launch.addActionListener(e -> {
            launch.setEnabled(false); log.append("\n=== Launching ===\n");
            new Thread(() -> {
                try {
                    HitBoyLauncher launcher = new HitBoyLauncher();
                    launcher.minecraftVersion = (String) verBox.getSelectedItem();
                    launcher.gameDirectory = dirField.getText();
                    launcher.nativeModsDirectory = modsField.getText();
                    launcher.mappingsFile = launcher.gameDirectory + File.separator + "mappings.json";
                    NavigationContext.setGameDirectory(launcher.gameDirectory);
                    NavigationContext.setMinecraftVersion(launcher.minecraftVersion);
                    NavigationContext.setNativeModsDirectory(launcher.nativeModsDirectory);
                    NavigationContext.setMappingsFile(launcher.mappingsFile);
                    // redirect System.out to log
                    PrintStream ps = new PrintStream(new OutputStream() {
                        public void write(int b) { SwingUtilities.invokeLater(() -> log.append(String.valueOf((char)b))); }
                        public void write(byte[] b, int off, int len) { SwingUtilities.invokeLater(() -> log.append(new String(b, off, len))); }
                    }, true);
                    PrintStream oldOut = System.out, oldErr = System.err;
                    System.setOut(ps); System.setErr(ps);
                    launcher.launch(true);
                    System.setOut(oldOut); System.setErr(oldErr);
                    log.append("\nDone. Check console or .hitboys-modloader/logs.\n");
                } catch (Exception ex) { log.append("Error: " + ex.getMessage() + "\n"); ex.printStackTrace(); }
                finally { SwingUtilities.invokeLater(() -> launch.setEnabled(true)); }
            }).start();
        });

        f.setContentPane(root);
        f.setVisible(true);
    }

    private String username = System.getenv("HITBOY_USERNAME") != null ? System.getenv("HITBOY_USERNAME") : "Notch";
    private boolean downloadOnly = false;
    private void parseArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--version": if (i+1 < args.length) minecraftVersion = args[++i]; break;
                case "--game-dir": if (i+1 < args.length) gameDirectory = args[++i]; break;
                case "--mods-dir": if (i+1 < args.length) nativeModsDirectory = args[++i]; break;
                case "--mappings": if (i+1 < args.length) mappingsFile = args[++i]; break;
                case "--username": if (i+1 < args.length) username = args[++i]; break;
                case "--download-only": downloadOnly = true; break;
                case "--install-official-patch": installOfficialPatch = true; break;
                case "--mixed-compatibility": mixedCompatibility = true; break;
                case "--official-game-dir": if (i+1 < args.length) officialGameDirectory = args[++i]; break;
                default: break;
            }
        }
        if (System.getenv("HITBOY_DOWNLOAD_ONLY") != null) downloadOnly = true;
        if (minecraftVersion == null) minecraftVersion = com.hitboy.launcher.minecraft.SupportedMinecraftVersions.DEFAULT_VERSION;
        if (gameDirectory == null) gameDirectory = NavigationContext.defaultGameDirectory();
        if (nativeModsDirectory == null) nativeModsDirectory = gameDirectory + File.separator + "native_mods";
        if (mappingsFile == null) mappingsFile = gameDirectory + File.separator + "mappings.json";
        NavigationContext.setGameDirectory(gameDirectory);
        NavigationContext.setMinecraftVersion(minecraftVersion);
        NavigationContext.setNativeModsDirectory(nativeModsDirectory);
        NavigationContext.setMappingsFile(mappingsFile);
    }

    private static void printHelp() {
        System.out.println("HitBoy's Mod Loader v1.0.0");
        System.out.println("Usage: HitBoysModLoader.exe [options]");
        System.out.println("       HitBoysModLoader.exe inspect <mod.jar>");
        System.out.println("       HitBoysModLoader.exe verify <instance-or-mods-directory>");
        System.out.println("       HitBoysModLoader.exe port <mod.jar> [--minecraft <version>] [--output <directory>]");
        System.out.println("  --version <ver>   Verified Minecraft version (1.21.11, 1.20.1, or 1.16.5; default 1.21.11)");
        System.out.println("  --game-dir <dir>  Game directory");
        System.out.println("  --mods-dir <dir>  Mods directory");
        System.out.println("  --mappings <file> Mojang mappings");
        System.out.println("  --install-official-patch  Install the 1.21.11-HitBoy profile for the official Launcher");
        System.out.println("  --official-game-dir <dir> Official Minecraft directory (default: %APPDATA%\\.minecraft)");
        System.out.println("No args = GUI mode (persistent window)");
    }

    private void installOfficialPatch() {
        try {
            File gameDirectory = officialGameDirectory == null
                ? com.hitboy.launcher.minecraft.OfficialPatchInstaller.defaultOfficialGameDirectory()
                : new File(officialGameDirectory);
            File loaderJar = findLoaderJar();
            com.hitboy.launcher.minecraft.OfficialPatchInstaller.install(
                gameDirectory, loaderJar, minecraftVersion, mixedCompatibility
            );
            System.out.println("Installed HitBoy official profile \"" + minecraftVersion + "-HitBoy\" in " + gameDirectory + ".");
            System.out.println("Select it in the official Minecraft Launcher; it uses your signed-in Microsoft account.");
        } catch (Exception e) {
            System.err.println("Could not install the official HitBoy profile: " + e.getMessage());
            System.exit(1);
        }
    }

    public void launch(boolean guiMode) {
        System.out.println("=== HitBoy's Mod Loader v1.0.0 ===");
        System.out.println("Version: " + minecraftVersion + " | Game dir: " + gameDirectory);
        try {
            if (!com.hitboy.launcher.minecraft.SupportedMinecraftVersions.isSupported(minecraftVersion)) {
                throw new IllegalArgumentException(
                    "Unsupported Minecraft version " + minecraftVersion + ". HitBoy native hooks are verified for: "
                        + com.hitboy.launcher.minecraft.SupportedMinecraftVersions.displayList()
                );
            }
            setupDirectories();
            Map<String,String> mappings = loadMappings();
            initHitBoyCore(mappings);
            if (downloadOnly) {
                System.out.println("Download-only: verifying " + minecraftVersion + " ...");
                new com.hitboy.launcher.minecraft.VersionManager().ensureVersion(minecraftVersion, s -> System.out.println(s));
                System.out.println("Download complete. Now click LAUNCH.");
                return;
            }
            launchMinecraft(guiMode);
            System.out.println("=== HitBoy's Mod Loader complete ===");
            if (guiMode) System.out.println("Window stays open - close to exit.");
        } catch (Exception e) {
            System.err.println("Fatal: " + e.getMessage());
            e.printStackTrace();
            if (guiMode) JOptionPane.showMessageDialog(null, e.getMessage(), "Launch Failed", JOptionPane.ERROR_MESSAGE);
            else System.exit(1);
        }
    }
    public void launch() { launch(false); }

    private void setupDirectories() throws IOException {
        Files.createDirectories(Paths.get(gameDirectory));
        Files.createDirectories(Paths.get(nativeModsDirectory));
        File mf = new File(mappingsFile);
        if (!mf.exists()) { createDefaultMappings(mf); System.out.println("Created mappings: " + mappingsFile); }
    }
    private void createDefaultMappings(File f) throws IOException {
        JsonObject o = new JsonObject(); o.addProperty("minecraft", minecraftVersion);
        JsonArray a = new JsonArray(); a.add("minecraft:client|Minecraft"); a.add("minecraft:render|Render");
        a.add("minecraft:tick|Tick"); a.add("minecraft:run|Run"); o.add("mappings", a);
        try (FileWriter w = new FileWriter(f)) { new GsonBuilder().setPrettyPrinting().create().toJson(o,w); }
    }
    private Map<String,String> loadMappings() throws IOException {
        Map<String,String> m = new HashMap<>(); File file = new File(mappingsFile);
        if (!file.exists()) return m;
        String content = new String(Files.readAllBytes(file.toPath()));
        JsonObject json = JsonParser.parseString(content).getAsJsonObject();
        if (json.has("mappings")) for (JsonElement e : json.getAsJsonArray("mappings")) {
            String s = e.getAsString(); int p = s.indexOf('|'); if (p>0) m.put(s.substring(0,p), s.substring(p+1));
        }
        System.out.println("Loaded "+m.size()+" mappings");
        return m;
    }
    private void initHitBoyCore(Map<String,String> mappings) {
        System.out.println("Initializing HitBoy's Mod Loader core...");
        System.setProperty("hitboy.mappings", mappings.toString());
        System.setProperty("hitboy.game-version", minecraftVersion);
        System.setProperty("hitboy.game-directory", gameDirectory);
        EventBus eb = new EventBus(); ModManager mm = new ModManager(eb);
        NavigationContext.setEventBus(eb); NavigationContext.setModManager(mm);
        System.out.println("Core initialized");
    }
    private void loadMods() throws Exception {
        ModManager mm = NavigationContext.getModManager();
        System.out.println("Scanning mods: " + nativeModsDirectory);
        mm.scanAndLoadMods(nativeModsDirectory);
        System.out.println("Loaded "+ mm.getLoadedModCount() +" mods");
        for (ModManager.ModInfo mi : mm.getLoadedMods()) System.out.println(" - " + mi.name + " v" + mi.version);
        if (mm.getLoadedModCount()==0) System.out.println("No mods found - place JARs with hitboy.json in native_mods/");
    }
    private void launchMinecraft(boolean guiMode) {
        System.out.println("Launching Minecraft " + minecraftVersion + " with HitBoy's Mod Loader as " + username + "...");
        try {
            // Ensure the official version files are available.
            System.out.println("Verifying Minecraft " + minecraftVersion + " ... (first run downloads ~200MB)");
            new com.hitboy.launcher.minecraft.VersionManager().ensureVersion(minecraftVersion, s -> System.out.println(s));
            // 2) Launch real Minecraft with ASM agent + mods
            int ram = 2048;
            try { String r = System.getenv("HITBOY_RAM"); if (r!=null) ram=Integer.parseInt(r); } catch(Exception ignored){}
            new com.hitboy.launcher.minecraft.MinecraftLauncher().launch(minecraftVersion, username, ram, s -> System.out.println(s));
        } catch (Exception e) {
            System.err.println("Minecraft launch failed: " + e.getMessage());
            e.printStackTrace();
            if (guiMode) javax.swing.JOptionPane.showMessageDialog(null, "Minecraft failed: " + e.getMessage() + "\nCheck log pane. Did you click Download/Verify? Needs internet first run.", "Launch Failed", javax.swing.JOptionPane.ERROR_MESSAGE);
            // Fallback demo so exe doesn't just silently close
            System.out.println("[FALLBACK] Demo NativeLoader launch (no vanilla jar)");
            try {
                File loaderJar = findLoaderJar();
                if (loaderJar != null) {
                    List<String> cmd = new ArrayList<>();
                    cmd.add(findJava());
                    cmd.add("-javaagent:" + loaderJar.getAbsolutePath() + "=" + mappingsFile);
                    cmd.add("-cp"); cmd.add(buildClasspath(loaderJar));
                    cmd.add("com.hitboy.loader.NativeLoader");
                    ProcessBuilder pb = new ProcessBuilder(cmd);
                    pb.directory(new File(gameDirectory)); pb.inheritIO(); pb.start().waitFor();
                }
            } catch (Exception ex) { System.err.println("Fallback failed: " + ex.getMessage()); }
        }
    }
    private String findJava() {
        String configuredJava = System.getenv("HITBOY_JAVA");
        return configuredJava == null || configuredJava.isBlank() ? "java" : configuredJava;
    }
    private File findLoaderJar() {
        return LauncherPaths.findLoaderJar(HitBoyLauncher.class);
    }
    private String buildClasspath(File loaderJar) {
        StringBuilder cp=new StringBuilder();
        File appDir = loaderJar == null ? null : loaderJar.getParentFile();
        if (appDir != null && appDir.exists()) {
            File[] jars=appDir.listFiles((d,n)->n.endsWith(".jar"));
            if(jars!=null) for(File j:jars) cp.append(j.getAbsolutePath()).append(File.pathSeparator);
        }
        if(loaderJar!=null && !cp.toString().contains(loaderJar.getName())) cp.append(loaderJar.getAbsolutePath()).append(File.pathSeparator);
        File launcherJar=new File("launcher/target/hitboys-mod-loader-launcher-1.0.0-SNAPSHOT.jar");
        if(launcherJar.exists()&&!cp.toString().contains("launcher")) cp.append(launcherJar.getAbsolutePath()).append(File.pathSeparator);
        File modsDir=new File(nativeModsDirectory);
        if(modsDir.exists()) { File[] jars=modsDir.listFiles((d,n)->n.endsWith(".jar")); if(jars!=null) for(File j:jars) cp.append(j.getAbsolutePath()).append(File.pathSeparator); }
        cp.append(".");
        return cp.toString();
    }
}
