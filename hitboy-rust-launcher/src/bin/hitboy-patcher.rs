#![windows_subsystem = "windows"]

use eframe::egui;
use std::process::Command;
use std::sync::mpsc::{channel, Receiver};
use hitboys_mod_loader::{java_executable, launcher_jar};

struct PatcherApp {
    version: String,
    mixed_compatibility: bool,
    status: String,
    running: bool,
    receiver: Option<Receiver<String>>,
}

impl PatcherApp {
    fn new() -> Self {
        Self {
            version: "26.2".to_string(),
            mixed_compatibility: false,
            status: "Choose a Minecraft version and patch mode.".to_string(),
            running: false,
            receiver: None,
        }
    }

    fn install(&mut self) {
        let (sender, receiver) = channel();
        self.receiver = Some(receiver);
        self.running = true;
        self.status = "Installing HitBoy profile...".to_string();
        let version = self.version.clone();
        let mixed_compatibility = self.mixed_compatibility;
        std::thread::spawn(move || {
            let result = install_profile(&version, mixed_compatibility);
            let _ = sender.send(result);
        });
    }
}

impl eframe::App for PatcherApp {
    fn update(&mut self, context: &egui::Context, _frame: &mut eframe::Frame) {
        if let Some(receiver) = &self.receiver {
            if let Ok(message) = receiver.try_recv() {
                self.status = message;
                self.running = false;
                self.receiver = None;
            }
        }
        egui::CentralPanel::default().show(context, |ui| {
            ui.visuals_mut().panel_fill = egui::Color32::from_rgb(14, 14, 18);
            egui::Frame::none()
                .fill(egui::Color32::from_rgb(24, 24, 32))
                .rounding(10.0)
                .inner_margin(20.0)
                .show(ui, |ui| {
                    ui.heading(egui::RichText::new("HitBoy Normal Launcher Patcher").color(egui::Color32::WHITE));
                    ui.label("Create a HitBoy installation in the normal Minecraft Launcher.");
                    ui.add_space(12.0);
                    ui.label("Minecraft version:");
                    egui::ComboBox::from_id_source("patch_version")
                        .selected_text(&self.version)
                        .show_ui(ui, |ui| {
                            for version in ["26.2", "1.21.11", "1.20.1", "1.16.5"] {
                                ui.selectable_value(&mut self.version, version.to_string(), version);
                            }
                        });
                    ui.checkbox(&mut self.mixed_compatibility, "Install Mixed Compatibility patch");
                    ui.label(egui::RichText::new(
                        "Standard loads HitBoy-native mods. Mixed adds the compatibility bridge and still blocks unsupported foreign bytecode."
                    ).small().color(egui::Color32::GRAY));
                    ui.add_space(12.0);
                    let label = format!("Install {}-HitBoy Profile", self.version);
                    if ui.add_enabled(!self.running, egui::Button::new(label).min_size([260.0, 38.0].into())).clicked() {
                        self.install();
                    }
                });
            ui.add_space(10.0);
            egui::Frame::none()
                .fill(egui::Color32::from_rgb(20, 20, 26))
                .rounding(8.0)
                .inner_margin(12.0)
                .show(ui, |ui| {
                    ui.label("Status");
                    ui.add_sized(
                        [ui.available_width(), 120.0],
                        egui::TextEdit::multiline(&mut self.status).desired_width(f32::INFINITY)
                    );
                });
        });
    }
}

fn install_profile(version: &str, mixed_compatibility: bool) -> String {
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
        .arg("--install-official-patch")
        .arg("--version")
        .arg(version)
        .args(if mixed_compatibility { vec!["--mixed-compatibility"] } else { Vec::new() })
        .output()
    {
        Ok(result) => {
            let mut message = String::from_utf8_lossy(&result.stdout).to_string();
            message.push_str(&String::from_utf8_lossy(&result.stderr));
            message
        }
        Err(error) => format!("Could not start Java: {error}"),
    }
}

fn main() -> eframe::Result<()> {
    let options = eframe::NativeOptions {
        viewport: egui::ViewportBuilder::default().with_inner_size([700.0, 470.0]),
        ..Default::default()
    };
    eframe::run_native("HitBoy Normal Launcher Patcher", options, Box::new(|_| Box::new(PatcherApp::new())))
}
