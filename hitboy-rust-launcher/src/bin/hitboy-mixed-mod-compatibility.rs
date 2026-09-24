#![windows_subsystem = "windows"]

use eframe::egui;
use std::path::PathBuf;
use std::process::Command;
use std::sync::mpsc::{channel, Receiver};
use hitboys_mod_loader::{java_executable, launcher_jar};

struct MixedApp {
    mods: Vec<PathBuf>,
    output: String,
    status: String,
    running: bool,
    receiver: Option<Receiver<String>>,
}

impl MixedApp {
    fn new() -> Self {
        Self {
            mods: Vec::new(),
            output: dirs::home_dir()
                .unwrap_or(PathBuf::from("."))
                .join(".hitboys-modloader")
                .join("native_mods")
                .to_string_lossy()
                .to_string(),
            status: "Add mods from any supported source loader. Every mod is ported and verified first.".to_string(),
            running: false,
            receiver: None,
        }
    }

    fn prepare(&mut self) {
        if self.mods.is_empty() {
            self.status = "Add at least one mod JAR.".to_string();
            return;
        }
        let mods = self.mods.clone();
        let output = self.output.clone();
        let (sender, receiver) = channel();
        self.receiver = Some(receiver);
        self.running = true;
        std::thread::spawn(move || {
            let mut report = String::new();
            for path in mods {
                report.push_str(&format!("{}\n", path.display()));
                report.push_str(&run_port(&path, &output));
                report.push_str("\n\n");
            }
            let _ = sender.send(report);
        });
    }
}

impl eframe::App for MixedApp {
    fn update(&mut self, context: &egui::Context, _frame: &mut eframe::Frame) {
        if let Some(receiver) = &self.receiver {
            if let Ok(message) = receiver.try_recv() {
                self.status = message;
                self.running = false;
                self.receiver = None;
            }
        }
        egui::CentralPanel::default().show(context, |ui| {
            ui.heading("HitBoy Mixed Mod Compatibility");
            ui.label("Prepare supported Fabric, Forge, NeoForge, and native HitBoy mods for one HitBoy instance.");
            ui.horizontal(|ui| {
                if ui.button("Add Mod JARs").clicked() {
                    if let Some(paths) = rfd::FileDialog::new().add_filter("Minecraft mods", &["jar"]).pick_files() {
                        for path in paths {
                            if !self.mods.contains(&path) {
                                self.mods.push(path);
                            }
                        }
                    }
                }
                if ui.button("Clear").clicked() {
                    self.mods.clear();
                }
            });
            egui::ScrollArea::vertical().max_height(150.0).show(ui, |ui| {
                for path in &self.mods {
                    ui.label(path.display().to_string());
                }
            });
            ui.label("HitBoy mods folder:");
            ui.horizontal(|ui| {
                ui.text_edit_singleline(&mut self.output);
                if ui.button("Choose").clicked() {
                    if let Some(path) = rfd::FileDialog::new().pick_folder() {
                        self.output = path.to_string_lossy().to_string();
                    }
                }
            });
            if ui.add_enabled(!self.running, egui::Button::new("Port and Prepare Together")).clicked() {
                self.prepare();
            }
            ui.separator();
            egui::ScrollArea::vertical().show(ui, |ui| {
                ui.add(egui::TextEdit::multiline(&mut self.status).desired_width(f32::INFINITY));
            });
        });
    }
}

fn run_port(source: &PathBuf, output: &str) -> String {
    let jar = match launcher_jar() {
        Ok(path) => path,
        Err(error) => return format!("Could not prepare embedded launcher: {error}"),
    };
    let java = match java_executable() {
        Ok(path) => path,
        Err(error) => return error,
    };
    match Command::new(java)
        .arg("-jar")
        .arg(jar)
        .arg("port")
        .arg(source)
        .arg("--minecraft")
        .arg("1.21.11")
        .arg("--output")
        .arg(output)
        .output()
    {
        Ok(result) => {
            let mut text = String::from_utf8_lossy(&result.stdout).to_string();
            text.push_str(&String::from_utf8_lossy(&result.stderr));
            text
        }
        Err(error) => format!("Could not start the HitBoy porter: {error}"),
    }
}

fn main() -> eframe::Result<()> {
    let options = eframe::NativeOptions {
        viewport: egui::ViewportBuilder::default().with_inner_size([760.0, 560.0]).with_icon(
            eframe::icon_data::from_png_bytes(include_bytes!("../../HitBoy.png"))
                .expect("embedded HitBoy logo must be a valid PNG")
        ),
        ..Default::default()
    };
    eframe::run_native("HitBoy Mixed Mod Compatibility", options, Box::new(|_| Box::new(MixedApp::new())))
}
