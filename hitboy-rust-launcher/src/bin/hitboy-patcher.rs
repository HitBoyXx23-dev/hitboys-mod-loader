#![windows_subsystem = "windows"]

use eframe::egui;
use std::path::PathBuf;
use std::process::Command;
use std::sync::mpsc::{channel, Receiver};
use hitboys_mod_loader::{java_executable, launcher_jar};

const VERSIONS: [&str; 5] = ["26.3", "26.2", "1.21.11", "1.20.1", "1.16.5"];

struct PatcherApp {
    version: String,
    minecraft_dir: String,
    mixed_compatibility: bool,
    status: String,
    running: bool,
    receiver: Option<Receiver<String>>,
}

fn default_minecraft_dir() -> String {
    std::env::var_os("APPDATA")
        .map(PathBuf::from)
        .or_else(dirs::home_dir)
        .unwrap_or(PathBuf::from("."))
        .join(".minecraft")
        .to_string_lossy()
        .to_string()
}

/// Prevents a console window from flashing open when the installer spawns java.exe.
#[cfg(windows)]
fn no_window(command: &mut Command) -> &mut Command {
    use std::os::windows::process::CommandExt;
    const CREATE_NO_WINDOW: u32 = 0x0800_0000;
    command.creation_flags(CREATE_NO_WINDOW)
}

#[cfg(not(windows))]
fn no_window(command: &mut Command) -> &mut Command {
    command
}

impl PatcherApp {
    fn new() -> Self {
        Self {
            version: VERSIONS[0].to_string(),
            minecraft_dir: default_minecraft_dir(),
            mixed_compatibility: false,
            status: "Choose a Minecraft version and your .minecraft folder, then click Install.".to_string(),
            running: false,
            receiver: None,
        }
    }

    fn install(&mut self) {
        let (sender, receiver) = channel();
        self.receiver = Some(receiver);
        self.running = true;
        self.status = format!("Installing {}-HitBoy into {} ...", self.version, self.minecraft_dir);
        let version = self.version.clone();
        let minecraft_dir = self.minecraft_dir.clone();
        let mixed_compatibility = self.mixed_compatibility;
        std::thread::spawn(move || {
            let result = install_profile(&version, &minecraft_dir, mixed_compatibility);
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
                    ui.heading(egui::RichText::new("HitBoy Profile Patcher").color(egui::Color32::WHITE));
                    ui.label("Installs HitBoy into .minecraft as a profile for the official Minecraft Launcher, like the Fabric installer.");
                    ui.add_space(12.0);
                    ui.label("Minecraft version:");
                    egui::ComboBox::from_id_source("patch_version")
                        .selected_text(&self.version)
                        .show_ui(ui, |ui| {
                            for version in VERSIONS {
                                ui.selectable_value(&mut self.version, version.to_string(), version);
                            }
                        });
                    ui.label("Install location (.minecraft):");
                    ui.horizontal(|ui| {
                        ui.add(egui::TextEdit::singleline(&mut self.minecraft_dir).desired_width(440.0));
                        if ui.button("Reset").clicked() {
                            self.minecraft_dir = default_minecraft_dir();
                        }
                    });
                    ui.checkbox(&mut self.mixed_compatibility, "Install Mixed Compatibility patch");
                    ui.label(egui::RichText::new(
                        "Standard loads HitBoy-native mods. Mixed adds the compatibility bridge and still blocks unsupported foreign bytecode."
                    ).small().color(egui::Color32::GRAY));
                    ui.add_space(12.0);
                    let label = format!("Install {}-HitBoy", self.version);
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

fn install_command(java: &std::path::Path, jar: &std::path::Path, version: &str, minecraft_dir: &str, mixed_compatibility: bool) -> Command {
    let mut command = Command::new(java);
    command
        .arg("-jar")
        .arg(jar)
        .arg("--install-official-patch")
        .arg("--version")
        .arg(version)
        .arg("--official-game-dir")
        .arg(minecraft_dir);
    if mixed_compatibility {
        command.arg("--mixed-compatibility");
    }
    command
}

fn install_profile(version: &str, minecraft_dir: &str, mixed_compatibility: bool) -> String {
    let jar = match launcher_jar() {
        Ok(path) => path,
        Err(error) => return format!("Could not prepare embedded launcher: {error}"),
    };
    let java = match java_executable() {
        Ok(path) => path,
        Err(error) => return error,
    };
    let mut command = install_command(&java, &jar, version, minecraft_dir, mixed_compatibility);
    no_window(&mut command);
    match command.output() {
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
        viewport: egui::ViewportBuilder::default().with_inner_size([700.0, 500.0]).with_icon(
            eframe::icon_data::from_png_bytes(include_bytes!("../../HitBoy.png"))
                .expect("embedded HitBoy logo must be a valid PNG")
        ),
        ..Default::default()
    };
    eframe::run_native("HitBoy Profile Patcher", options, Box::new(|_| Box::new(PatcherApp::new())))
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn install_command_targets_the_chosen_minecraft_directory() {
        let command = install_command(
            std::path::Path::new("java"),
            std::path::Path::new("patch.jar"),
            "26.3",
            "C:\\Users\\Player\\AppData\\Roaming\\.minecraft",
            false,
        );
        let arguments: Vec<_> = command.get_args().collect();
        assert_eq!(
            arguments,
            [
                "-jar",
                "patch.jar",
                "--install-official-patch",
                "--version",
                "26.3",
                "--official-game-dir",
                "C:\\Users\\Player\\AppData\\Roaming\\.minecraft",
            ]
            .iter()
            .map(std::ffi::OsStr::new)
            .collect::<Vec<_>>()
        );
    }
}
