package com.hitboy.launcher;

import com.hitboy.loader.compat.CompatibilityCli;
import com.hitboy.loader.compat.ModInspector;
import com.hitboy.loader.compat.SourceLoader;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public final class MixedCompatibilityGui {
    private final DefaultListModel<File> mods = new DefaultListModel<>();
    private final JTextArea status = new JTextArea();
    private final JTextField output = new JTextField();

    private MixedCompatibilityGui() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MixedCompatibilityGui().show());
    }

    private void show() {
        JFrame frame = new JFrame("HitBoy Mixed Mod Compatibility");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(760, 520);
        frame.setLocationRelativeTo(null);
        output.setText(Path.of(System.getProperty("user.home"), ".hitboys-modloader", "native_mods").toString());

        JList<File> list = new JList<>(mods);
        status.setEditable(false);
        status.setLineWrap(true);
        status.setWrapStyleWord(true);

        JButton add = new JButton("Add Fabric, NeoForge, Forge, or HitBoy JARs");
        JButton remove = new JButton("Remove Selected");
        JButton chooseOutput = new JButton("Choose HitBoy Mods Folder");
        JButton prepare = new JButton("Port and Prepare Together");

        add.addActionListener(event -> addMods(frame));
        remove.addActionListener(event -> {
            List<File> selected = new ArrayList<>(list.getSelectedValuesList());
            selected.forEach(mods::removeElement);
        });
        chooseOutput.addActionListener(event -> chooseOutput(frame));
        prepare.addActionListener(event -> prepare(prepare));

        JPanel buttons = new JPanel(new GridLayout(2, 2, 8, 8));
        buttons.add(add);
        buttons.add(remove);
        buttons.add(chooseOutput);
        buttons.add(prepare);

        JPanel destination = new JPanel(new BorderLayout(8, 0));
        destination.add(new JLabel("HitBoy mods folder:"), BorderLayout.WEST);
        destination.add(output, BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout(8, 8));
        south.add(destination, BorderLayout.NORTH);
        south.add(buttons, BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(list), new JScrollPane(status));
        split.setResizeWeight(0.45);

        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        root.add(new JLabel("Every foreign mod is converted and verified before it can join the HitBoy instance."), BorderLayout.NORTH);
        root.add(split, BorderLayout.CENTER);
        root.add(south, BorderLayout.SOUTH);
        frame.setContentPane(root);
        frame.setVisible(true);
    }

    private void addMods(JFrame frame) {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            for (File file : chooser.getSelectedFiles()) {
                if (file.getName().toLowerCase().endsWith(".jar") && !mods.contains(file)) mods.addElement(file);
            }
        }
    }

    private void chooseOutput(JFrame frame) {
        JFileChooser chooser = new JFileChooser(output.getText());
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            output.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void prepare(JButton button) {
        if (mods.isEmpty() || output.getText().isBlank()) {
            status.setText("Add at least one mod and choose a HitBoy mods folder.");
            return;
        }
        button.setEnabled(false);
        status.setText("Inspecting and porting selected mods...\n");
        new Thread(() -> {
            StringBuilder report = new StringBuilder();
            int completed = 0;
            try {
                Files.createDirectories(Path.of(output.getText()));
                for (int index = 0; index < mods.size(); index++) {
                    File mod = mods.get(index);
                    ModInspector.Inspection inspection = new ModInspector().inspect(mod.toPath());
                    if (inspection.getDescriptor().getSourceLoader() == SourceLoader.HITBOY
                        && inspection.getReport().isCompatible()) {
                        Files.copy(mod.toPath(), Path.of(output.getText()).resolve(mod.getName()), StandardCopyOption.REPLACE_EXISTING);
                        report.append(mod.getName()).append("\nNative HitBoy mod installed.\n\n");
                        completed++;
                        continue;
                    }
                    ByteArrayOutputStream standard = new ByteArrayOutputStream();
                    ByteArrayOutputStream errors = new ByteArrayOutputStream();
                    int result = new CompatibilityCli().execute(new String[] {
                        "port", mod.getAbsolutePath(), "--minecraft", "1.21.11", "--output", output.getText()
                    }, new PrintStream(standard), new PrintStream(errors));
                    report.append(mod.getName()).append('\n').append(standard).append(errors).append('\n');
                    if (result == 0) completed++;
                }
                report.append("Prepared ").append(completed).append(" of ").append(mods.size())
                    .append(" mods. Blocked mods were not installed.");
            } catch (Exception exception) {
                report.append("Preparation failed: ").append(exception.getMessage());
            }
            String finalReport = report.toString();
            SwingUtilities.invokeLater(() -> {
                status.setText(finalReport);
                button.setEnabled(true);
            });
        }, "hitboy-mixed-compatibility").start();
    }
}
