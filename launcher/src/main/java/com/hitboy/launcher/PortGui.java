package com.hitboy.launcher;

import com.hitboy.loader.compat.SourceProjectConverter;
import com.hitboy.loader.compat.CompatibilityCli;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;

public final class PortGui {
    private PortGui() {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PortGui::show);
    }

    private static void show() {
        JFrame frame = new JFrame("HitBoy Mod Porter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 430);
        frame.setLocationRelativeTo(null);
        JTextField source = new JTextField();
        JTextField output = new JTextField();
        JTextArea status = new JTextArea();
        status.setEditable(false);
        status.setLineWrap(true);
        JButton chooseSource = new JButton("Choose Mod");
        JButton chooseOutput = new JButton("Choose Output Folder");
        JButton port = new JButton("Port Mod");
        chooseSource.addActionListener(event -> choose(frame, source));
        chooseOutput.addActionListener(event -> choose(frame, output));
        port.addActionListener(event -> convert(frame, source, output, status, port));
        JPanel fields = new JPanel(new GridLayout(2, 1, 8, 8));
        fields.add(row(source, chooseSource));
        fields.add(row(output, chooseOutput));
        JPanel center = new JPanel(new BorderLayout(8, 8));
        center.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        center.add(fields, BorderLayout.NORTH);
        center.add(new JScrollPane(status), BorderLayout.CENTER);
        center.add(port, BorderLayout.SOUTH);
        frame.add(new JLabel(" Select a Fabric, Forge, or NeoForge source folder or JAR."), BorderLayout.NORTH);
        frame.add(center, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static JPanel row(JTextField field, JButton button) {
        JPanel panel = new JPanel(new BorderLayout(8, 0));
        panel.add(field, BorderLayout.CENTER);
        panel.add(button, BorderLayout.EAST);
        return panel;
    }

    private static void choose(JFrame frame, JTextField field) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            field.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private static void convert(JFrame frame, JTextField source, JTextField output, JTextArea status, JButton button) {
        if (source.getText().isBlank() || output.getText().isBlank()) {
            JOptionPane.showMessageDialog(frame, "Choose both folders.");
            return;
        }
        button.setEnabled(false);
        status.setText("Porting project...");
        new Thread(() -> {
            try {
                Path sourcePath = Path.of(source.getText());
                String message;
                if (java.nio.file.Files.isDirectory(sourcePath)) {
                    SourceProjectConverter.Result result = new SourceProjectConverter().convert(sourcePath, Path.of(output.getText()));
                    message = "Created: " + result.outputDirectory() + "\nDetected: " + result.sourceLoader()
                        + "\nAutomatic changes: " + result.changedFiles() + "\nManual items: " + result.manualItems().size()
                        + "\n\n" + String.join("\n", result.manualItems());
                } else {
                    ByteArrayOutputStream standard = new ByteArrayOutputStream();
                    ByteArrayOutputStream errors = new ByteArrayOutputStream();
                    int result = new CompatibilityCli().execute(new String[] {
                        "port", sourcePath.toString(), "--minecraft", "1.21.11", "--output", output.getText()
                    }, new PrintStream(standard), new PrintStream(errors));
                    message = standard.toString() + errors;
                    if (result != 0) message += "\nNo output was created because this mod needs unsupported loader APIs.";
                }
                String finalMessage = message;
                SwingUtilities.invokeLater(() -> status.setText(finalMessage));
            } catch (Exception exception) {
                SwingUtilities.invokeLater(() -> status.setText("Port failed: " + exception.getMessage()));
            } finally {
                SwingUtilities.invokeLater(() -> button.setEnabled(true));
            }
        }, "hitboy-porter").start();
    }
}
