package com.hitboy.launcher.ui;

import com.hitboy.launcher.NavigationContext;
import com.hitboy.launcher.minecraft.VersionManager;
import com.hitboy.launcher.minecraft.MinecraftLauncher;
import com.hitboy.launcher.mods.ModRegistry;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.*;
import java.util.List;

public class HitBoyLauncherFrame extends JFrame {
    private CardLayout cards = new CardLayout();
    private JPanel content;
    private String current = "HOME";
    private JTextArea logArea;
    private JComboBox<String> versionBox;
    private JTextField nameField;
    private JSlider ramSlider;
    private JPanel modsGrid;

    public HitBoyLauncherFrame() {
        NavigationContext.initializeDefaults();
        setTitle("HitBoy's Mod Loader");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1080, 680);
        setLocationRelativeTo(null);
        setIconImage(createIcon());

        // Root
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(16,16,20));

        // Navigation sidebar
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(new Color(22,22,28));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(14,12,14,12));

        JLabel brand = new JLabel("HITBOY'S");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 22));
        brand.setForeground(new Color(88,166,255));
        JLabel sub = new JLabel("MOD LOADER  v1.0.0");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        sub.setForeground(new Color(140,140,160));
        sidebar.add(brand); sidebar.add(sub);
        sidebar.add(Box.createVerticalStrut(18));

        sidebar.add(navItem("  \u25A3  HOME", "HOME", true));
        sidebar.add(navItem("  \u2630  MODS", "MODS", false));
        sidebar.add(navItem("  \u2699  SETTINGS", "SETTINGS", false));
        sidebar.add(Box.createVerticalGlue());
        JLabel foot = new JLabel("HITBOY'S MOD LOADER");
        foot.setForeground(new Color(100,100,120)); foot.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        sidebar.add(foot);

        // Content cards
        content = new JPanel(cards);
        content.setBackground(new Color(14,14,18));
        content.add(buildHome(), "HOME");
        content.add(buildMods(), "MODS");
        content.add(buildSettings(), "SETTINGS");

        root.add(sidebar, BorderLayout.WEST);
        root.add(content, BorderLayout.CENTER);
        setContentPane(root);
    }

    private JPanel navItem(String text, String key, boolean active) {
        JPanel p = new JPanel(new BorderLayout());
        p.setMaximumSize(new Dimension(220, 38));
        p.setBackground(active ? new Color(38,38,48) : new Color(22,22,28));
        p.setBorder(new EmptyBorder(8,10,8,10));
        JLabel l = new JLabel(text);
        l.setForeground(active ? Color.WHITE : new Color(180,180,190));
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        p.add(l, BorderLayout.CENTER);
        p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        p.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { switchTo(key); }
            public void mouseEntered(MouseEvent e) { if (!current.equals(key)) p.setBackground(new Color(30,30,40)); }
            public void mouseExited(MouseEvent e) { if (!current.equals(key)) p.setBackground(new Color(22,22,28)); }
        });
        p.putClientProperty("navKey", key);
        return p;
    }

    private void switchTo(String key) {
        current = key; cards.show(content, key);
        // repaint sidebar active states
        for (Component c : ((JPanel)getContentPane().getComponent(0)).getComponents()) {
            if (c instanceof JPanel) {
                Object k = ((JPanel)c).getClientProperty("navKey");
                if (k != null) {
                    boolean a = k.equals(key);
                    c.setBackground(a ? new Color(38,38,48) : new Color(22,22,28));
                    JLabel lbl = (JLabel)((JPanel)c).getComponent(0);
                    lbl.setForeground(a ? Color.WHITE : new Color(180,180,190));
                }
            }
        }
        if ("MODS".equals(key)) refreshMods();
    }

    private JPanel buildHome() {
        JPanel p = new JPanel(new BorderLayout(10,10));
        p.setBackground(new Color(14,14,18));
        p.setBorder(new EmptyBorder(16,16,16,16));

        // Top launch banner
        JPanel banner = new JPanel(new BorderLayout());
        banner.setBackground(new Color(24,24,32));
        banner.setBorder(new EmptyBorder(16,16,16,16));
        JLabel title = new JLabel("Ready to launch");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20)); title.setForeground(Color.WHITE);
        JLabel desc = new JLabel("Native mods • ASM launcher agent • verified Minecraft runtime");
        desc.setForeground(new Color(160,160,180));
        JButton bigLaunch = new JButton("LAUNCH  \u25B6");
        bigLaunch.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bigLaunch.setBackground(new Color(88,166,255)); bigLaunch.setForeground(Color.WHITE);
        bigLaunch.setFocusPainted(false); bigLaunch.setPreferredSize(new Dimension(160,44));
        banner.add(title, BorderLayout.NORTH); banner.add(desc, BorderLayout.CENTER); banner.add(bigLaunch, BorderLayout.EAST);

        // Center: version + account + log
        JPanel center = new JPanel(new GridLayout(1,2,12,0));
        center.setBackground(new Color(14,14,18));

        JPanel left = new JPanel(new GridBagLayout());
        left.setBackground(new Color(24,24,32)); left.setBorder(new EmptyBorder(12,12,12,12));
        GridBagConstraints c = new GridBagConstraints(); c.insets=new Insets(6,6,6,6); c.fill=GridBagConstraints.HORIZONTAL; c.weightx=1;
        nameField = new JTextField("Notch"); nameField.setBackground(new Color(16,16,20)); nameField.setForeground(Color.WHITE); nameField.setCaretColor(Color.WHITE);
        versionBox = new JComboBox<>(new String[]{"1.21.11", "1.20.1", "1.16.5"});
        versionBox.setSelectedItem("1.21.11");
        JButton dlBtn = new JButton("Download / Verify");
        c.gridx=0; c.gridy=0; left.add(new JLabel("Username (offline):"){{setForeground(Color.WHITE);}}, c);
        c.gridy=1; left.add(nameField, c);
        c.gridy=2; left.add(new JLabel("Version:"){{setForeground(Color.WHITE);}}, c);
        c.gridy=3; left.add(versionBox, c);
        c.gridy=4; left.add(dlBtn, c);
        JLabel hint = new JLabel("<html><small>Downloads vanilla jar + libraries + natives<br/>from Mojang manifest</small></html>");
        hint.setForeground(new Color(140,140,160)); c.gridy=5; left.add(hint, c);

        JPanel right = new JPanel(new BorderLayout());
        right.setBackground(new Color(24,24,32)); right.setBorder(new EmptyBorder(8,8,8,8));
        logArea = new JTextArea(14, 30); logArea.setEditable(false); logArea.setBackground(new Color(16,16,20)); logArea.setForeground(new Color(180,220,180)); logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        logArea.setText("HitBoy's Mod Loader ready.\nPlace mods in native_mods/ (hitboy.json).\n");
        right.add(new JLabel("Log:"){{setForeground(Color.WHITE);}}, BorderLayout.NORTH);
        right.add(new JScrollPane(logArea), BorderLayout.CENTER);

        center.add(left); center.add(right);
        p.add(banner, BorderLayout.NORTH); p.add(center, BorderLayout.CENTER);

        dlBtn.addActionListener(e -> new Thread(() -> {
            String ver = (String) versionBox.getSelectedItem();
            log("Downloading " + ver + " ...");
            try { new VersionManager().ensureVersion(ver, s -> log(s)); log("Verified " + ver); }
            catch (Exception ex) { log("Error: " + ex.getMessage()); ex.printStackTrace(); }
        }).start());

        bigLaunch.addActionListener(e -> doLaunch());

        return p;
    }

    private JPanel buildMods() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(14,14,18)); p.setBorder(new EmptyBorder(12,12,12,12));
        JLabel h = new JLabel("Mods  •  click to toggle");
        h.setForeground(Color.WHITE); h.setFont(new Font("Segoe UI", Font.BOLD, 14));
        p.add(h, BorderLayout.NORTH);
        modsGrid = new JPanel(new GridLayout(0,2,10,10));
        modsGrid.setBackground(new Color(14,14,18));
        JScrollPane sp = new JScrollPane(modsGrid); sp.setBorder(null); sp.getViewport().setBackground(new Color(14,14,18));
        p.add(sp, BorderLayout.CENTER);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(new Color(14,14,18));
        JButton open = new JButton("Open Mods Folder"); JButton refresh = new JButton("Refresh");
        bottom.add(open); bottom.add(refresh);
        open.addActionListener(e -> { try{ Desktop.getDesktop().open(new File(NavigationContext.getNativeModsDirectory()!=null?NavigationContext.getNativeModsDirectory():"native_mods")); } catch(Exception ex){ log(ex.getMessage()); }});
        refresh.addActionListener(e -> refreshMods());
        p.add(bottom, BorderLayout.SOUTH);
        refreshMods();
        return p;
    }

    private JPanel buildSettings() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(14,14,18)); p.setBorder(new EmptyBorder(12,12,12,12));
        JLabel h = new JLabel("Settings"); h.setForeground(Color.WHITE); h.setFont(new Font("Segoe UI", Font.BOLD, 14));
        p.add(h, BorderLayout.NORTH);
        JPanel form = new JPanel(new GridBagLayout()); form.setBackground(new Color(24,24,32)); form.setBorder(new EmptyBorder(12,12,12,12));
        GridBagConstraints c = new GridBagConstraints(); c.insets=new Insets(8,8,8,8); c.fill=GridBagConstraints.HORIZONTAL; c.weightx=1;
        ramSlider = new JSlider(512, 8192, 2048); ramSlider.setMajorTickSpacing(1024); ramSlider.setPaintTicks(true); ramSlider.setPaintLabels(true); ramSlider.setBackground(new Color(24,24,32)); ramSlider.setForeground(Color.WHITE);
        JTextField javaField = new JTextField("java (resolved from PATH; set HITBOY_JAVA to override)");
        javaField.setBackground(new Color(16,16,20)); javaField.setForeground(Color.WHITE);
        c.gridx=0; c.gridy=0; form.add(new JLabel("RAM (MB):"){{setForeground(Color.WHITE);}}, c);
        c.gridy=1; form.add(ramSlider, c);
        c.gridy=2; form.add(new JLabel("Java Executable:"){{setForeground(Color.WHITE);}}, c);
        c.gridy=3; form.add(javaField, c);
        c.gridy=4; form.add(new JLabel("<html><small>Uses <code>java</code> from PATH by default.<br/>Minecraft 1.16.5 works with Java 8-16; 1.20.1 requires Java 17+; 1.21.11 requires Java 21+.</small></html>"){{setForeground(new Color(140,140,160));}}, c);
        JButton installOfficialProfile = new JButton("Install Official Launcher Profile");
        c.gridy=5; form.add(installOfficialProfile, c);
        installOfficialProfile.addActionListener(e -> new Thread(() -> {
            try {
                File loaderJar = com.hitboy.launcher.LauncherPaths.findLoaderJar(HitBoyLauncherFrame.class);
                File officialGameDirectory =
                    com.hitboy.launcher.minecraft.OfficialPatchInstaller.defaultOfficialGameDirectory();
                com.hitboy.launcher.minecraft.OfficialPatchInstaller.install(
                    officialGameDirectory,
                    loaderJar
                );
                log("Installed official profile 1.21.11-HitBoy in " + officialGameDirectory);
            } catch (Exception ex) {
                log("Official profile install failed: " + ex.getMessage());
            }
        }).start());
        p.add(form, BorderLayout.CENTER);
        return p;
    }

    private void refreshMods() {
        if (modsGrid == null) return;
        modsGrid.removeAll();
        List<ModRegistry.Entry> mods = ModRegistry.scan(
            NavigationContext.getNativeModsDirectory()!=null?NavigationContext.getNativeModsDirectory():"native_mods"
        );
        if (mods.isEmpty()) {
            modsGrid.add(modCard("Fullbright HUD", "Always-on fullbright with FPS + XYZ", true, "fullbright-1.0.0"));
        } else {
            for (ModRegistry.Entry e : mods) modsGrid.add(modCard(e.name, e.description + " v" + e.version, e.enabled, e.id));
        }
        modsGrid.revalidate(); modsGrid.repaint();
    }

    private JPanel modCard(String name, String desc, boolean enabled, String id) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(24,24,32));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(enabled? new Color(88,166,255) : new Color(40,40,50), 1),
            new EmptyBorder(10,10,10,10)
        ));
        JLabel n = new JLabel(name); n.setForeground(Color.WHITE); n.setFont(new Font("Segoe UI", Font.BOLD, 13));
        JLabel d = new JLabel("<html><small>" + desc + "</small></html>"); d.setForeground(new Color(160,160,180));
        JPanel text = new JPanel(new BorderLayout()); text.setOpaque(false); text.add(n, BorderLayout.NORTH); text.add(d, BorderLayout.CENTER);
        JToggleButton tog = new JToggleButton(enabled ? "ON" : "OFF", enabled);
        tog.setBackground(enabled ? new Color(88,166,255) : new Color(40,40,50));
        tog.setForeground(Color.WHITE); tog.setFocusPainted(false);
        tog.addActionListener(e -> {
            boolean on = tog.isSelected(); tog.setText(on?"ON":"OFF"); tog.setBackground(on? new Color(88,166,255): new Color(40,40,50));
            card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(on? new Color(88,166,255): new Color(40,40,50),1), new EmptyBorder(10,10,10,10)));
            ModRegistry.setEnabled(id, on); log(name + " " + (on?"enabled":"disabled"));
        });
        card.add(text, BorderLayout.CENTER); card.add(tog, BorderLayout.EAST);
        return card;
    }

    private void doLaunch() {
        String ver = (String) versionBox.getSelectedItem();
        String user = nameField.getText().trim();
        if (user.isEmpty()) user = "Notch";
        final String u = user;
        log("Launching " + ver + " as " + u + " ...");
        new Thread(() -> {
            try {
                // ensure version downloaded
                new VersionManager().ensureVersion(ver, s -> log(s));
                int ram = ramSlider != null ? ramSlider.getValue() : 2048;
                new MinecraftLauncher().launch(ver, u, ram, s -> log(s));
                log("Minecraft exited.");
            } catch (Exception ex) { log("Launch failed: " + ex.getMessage()); ex.printStackTrace(); }
        }).start();
    }

    private void log(String s) {
        SwingUtilities.invokeLater(() -> { logArea.append(s + "\n"); logArea.setCaretPosition(logArea.getDocument().getLength()); });
        System.out.println(s);
    }

    private Image createIcon() {
        return Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png"));
    }
}
