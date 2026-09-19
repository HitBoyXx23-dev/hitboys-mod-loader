#![windows_subsystem = "windows"]

use eframe::egui;
use std::path::PathBuf;
use std::process::Command;
use std::sync::mpsc::{channel, Receiver};
use hitboys_mod_loader::{java_executable, launcher_jar};

struct PortApp {
    source: String,
    output: String,
    status: String,
    running: bool,
    receiver: Option<Receiver<String>>,
}

impl PortApp {
    fn new() -> Self {
        Self {
            source: String::new(),
            output: dirs::home_dir()
                .unwrap_or(PathBuf::from("."))
                .join(".hitboys-modloader")
                .join("ported_mods")
                .to_string_lossy()
                .to_string(),
            status: "Choose a Fabric, Forge, NeoForge, or HitBoy mod source folder or JAR.".to_string(),
            running: false,
            receiver: None,
        }
    }

    fn start(&mut self) {
        if self.source.trim().is_empty() || self.output.trim().is_empty() {
            self.status = "Choose a mod and an output folder.".to_string();
            return;
        }
        let source = self.source.clone();
        let output = self.output.clone();
        let (sender, receiver) = channel();
        self.receiver = Some(receiver);
        self.running = true;
        self.status = "Inspecting and porting mod...".to_string();
        std::thread::spawn(move || {
            let message = run_port(&source, &output);
            let _ = sender.send(message);
        });
    }
}

impl eframe::App for PortApp {
    fn update(&mut self, context: &egui::Context, _frame: &mut eframe::Frame) {
        if let Some(receiver) = &self.receiver {
            if let Ok(message) = receiver.try_recv() {
                self.status = message;
                self.running = false;
                self.receiver = None;
            }
        }
        egui::CentralPanel::default().show(context, |ui| {
            ui.heading("HitBoy Mod Porter");
            ui.label("Convert supported Fabric, Forge, and NeoForge projects or JARs to HitBoy format.");
            ui.add_space(12.0);
            ui.label("Source mod:");
            ui.horizontal(|ui| {
                ui.text_edit_singleline(&mut self.source);
                if ui.button("Choose JAR").clicked() {
                    if let Some(path) = rfd::FileDialog::new().add_filter("Minecraft mod", &["jar"]).pick_file() {
                        self.source = path.to_string_lossy().to_string();
                    }
                }
                if ui.button("Choose Source Folder").clicked() {
                    if let Some(path) = rfd::FileDialog::new().pick_folder() {
                        self.source = path.to_string_lossy().to_string();
                    }
                }
            });
            ui.label("Output folder:");
            ui.horizontal(|ui| {
                ui.text_edit_singleline(&mut self.output);
                if ui.button("Choose").clicked() {
                    if let Some(path) = rfd::FileDialog::new().pick_folder() {
                        self.output = path.to_string_lossy().to_string();
                    }
                }
            });
            if ui.add_enabled(!self.running, egui::Button::new("Port Mod")).clicked() {
                self.start();
            }
            ui.separator();
            egui::ScrollArea::vertical().show(ui, |ui| {
                ui.add(egui::TextEdit::multiline(&mut self.status).desired_width(f32::INFINITY));
            });
        });
    }
}

fn run_port(source: &str, output: &str) -> String {
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
        viewport: egui::ViewportBuilder::default().with_inner_size([760.0, 520.0]),
        ..Default::default()
    };
    eframe::run_native("HitBoy Mod Porter", options, Box::new(|_| Box::new(PortApp::new())))
}
