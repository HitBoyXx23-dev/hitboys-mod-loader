// Hide the console window on Windows: this is a GUI launcher, not a CLI tool,
// and a background console would flash on every launch otherwise. Debug logs
// still stream into the in-app Log tab via the existing log_rx channel.
#![windows_subsystem = "windows"]

use eframe::egui;
use serde::{Deserialize, Serialize};
use std::path::{PathBuf, Path};
use std::fs;
use std::sync::mpsc::{channel, Receiver};
use std::process::{Command, Stdio};
use std::io::{BufRead, BufReader};
use hitboys_mod_loader::{java_executable, launcher_jar};
#[cfg(test)]
use hitboys_mod_loader::LAUNCHER_JAR_NAME;


/// Prevents a java.exe console window from flashing open behind the GUI when
/// we spawn it as a child process. The launcher itself already runs without a
/// console (see `windows_subsystem = "windows"` above); without this flag,
/// Windows would still give the console-subsystem `java.exe` child its own
/// new console window.
#[cfg(windows)]
fn no_window(cmd: &mut Command) -> &mut Command {
    use std::os::windows::process::CommandExt;
    const CREATE_NO_WINDOW: u32 = 0x0800_0000;
    cmd.creation_flags(CREATE_NO_WINDOW)
}

#[cfg(not(windows))]
fn no_window(cmd: &mut Command) -> &mut Command {
    cmd
}

#[derive(Default)]
struct HitBoysModLoaderApp {
    mixed_compatibility: bool,
    version: String,
    username: String,
    ram_mb: u32,
    game_dir: String,
    mods: Vec<ModEntry>,
    log: String,
    tab: Tab,
    launching: bool,
    log_rx: Option<Receiver<String>>,
}

#[derive(Clone, PartialEq)]
enum Tab { Home, Mods, Settings }
impl Default for Tab { fn default() -> Self { Tab::Home } }

#[derive(Clone, Serialize, Deserialize)]
struct ModEntry {
    id: String,
    name: String,
    version: String,
    description: String,
    enabled: bool,
    jar: String,
}

fn default_game_dir() -> String {
    let home = dirs::home_dir().unwrap_or(PathBuf::from("."));
    let hitboy_directory = home.join(".hitboys-modloader");
    let legacy_directory = home.join(".hitboys-mod-loader");
    if !hitboy_directory.exists() && legacy_directory.is_dir() {
        if fs::rename(&legacy_directory, &hitboy_directory).is_err() {
            return legacy_directory.to_string_lossy().to_string();
        }
    }
    hitboy_directory.to_string_lossy().to_string()
}

#[cfg(test)]
fn packaged_app_directory_at(executable_directory: &Path) -> Option<PathBuf> {
    for distribution_root in [
        executable_directory.to_path_buf(),
        executable_directory.join("HitBoysModLoader"),
    ] {
        if distribution_root.join(LAUNCHER_JAR_NAME).is_file() {
            return Some(distribution_root);
        }
        let app_directory = distribution_root.join("app");
        if app_directory.join(LAUNCHER_JAR_NAME).is_file() {
            return Some(app_directory);
        }
    }
    None
}

fn mixed_compatibility_mode() -> bool {
    std::env::current_exe()
        .ok()
        .and_then(|path| path.file_stem().map(|name| name.to_string_lossy().to_lowercase()))
        .map(|name| name.contains("mixed"))
        .unwrap_or(false)
}

fn launcher_command(
    java: &Path,
    launcher_jar: &Path,
    version: &str,
    game_dir: &str,
    username: Option<&str>,
    download_only: bool,
) -> Command {
    let mut command = Command::new(java);
    command
        .arg("-jar")
        .arg(launcher_jar)
        .arg("--version")
        .arg(version)
        .arg("--game-dir")
        .arg(game_dir);
    if let Some(username) = username {
        command.arg("--username").arg(username);
    }
    if download_only {
        command.arg("--download-only");
    }
    command
}

impl HitBoysModLoaderApp {
    fn new() -> Self {
        let mixed_compatibility = mixed_compatibility_mode();
        let mut app = Self {
            mixed_compatibility,
            version: "1.21.11".to_string(),
            username: "Notch".to_string(),
            ram_mb: 2048,
            game_dir: default_game_dir(),
            mods: vec![],
            log: if mixed_compatibility {
                "HitBoy's Mixed Compatibility Mod Loader v1.0.0\nReady. Native and converted mods are managed together.\n".to_string()
            } else {
                "HitBoy's Mod Loader v1.0.0\nReady. Put HitBoy mods in native_mods; nothing is installed automatically.\n".to_string()
            },
            tab: Tab::Home,
            launching: false,
            log_rx: None,
        };
        app.refresh_mods();
        app
    }

    fn mods_dir(&self) -> PathBuf { PathBuf::from(&self.game_dir).join("native_mods") }

    fn refresh_mods(&mut self) {
        let dir = self.mods_dir();
        if !dir.exists() { let _ = fs::create_dir_all(&dir); }
        self.mods.clear();
        if let Ok(entries) = fs::read_dir(&dir) {
            for e in entries.flatten() {
                let p = e.path();
                if p.extension().map(|x| x=="jar").unwrap_or(false) {
                    let id = p.file_stem().unwrap().to_string_lossy().to_string();
                    let enabled = !dir.join(format!("{}.disabled", id)).exists();
                    let (name, version, desc) = read_mod_metadata(&p).unwrap_or((id.clone(), "Unknown".into(), "Fabric mod".into()));
                    self.mods.push(ModEntry{ id: id.clone(), name, version, description: desc, enabled, jar: p.to_string_lossy().to_string() });
                }
            }
        }
    }

}

fn read_mod_metadata(jar: &Path) -> Option<(String,String,String)> {
    let file = fs::File::open(jar).ok()?;
    let mut zip = zip::read::ZipArchive::new(file).ok()?;
    let metadata_name = if zip.by_name("fabric.mod.json").is_ok() { "fabric.mod.json" } else { "hitboy.json" };
    let mut f = zip.by_name(metadata_name).ok()?;
    let mut buf = String::new();
    use std::io::Read;
    f.read_to_string(&mut buf).ok()?;
    let v: serde_json::Value = serde_json::from_str(&buf).ok()?;
    Some((
        v.get("name")?.as_str()?.to_string(),
        v.get("version")?.as_str().unwrap_or("1.0.0").to_string(),
        v.get("description")?.as_str().unwrap_or("").to_string(),
    ))
}

impl eframe::App for HitBoysModLoaderApp {
    fn update(&mut self, ctx: &egui::Context, _frame: &mut eframe::Frame) {
        // Stream logs from background download/launch threads
        if let Some(rx) = &self.log_rx {
            while let Ok(line) = rx.try_recv() {
                if line == "--- Minecraft process finished ---" {
                    self.launching = false;
                }
                self.log.push_str(&line);
                self.log.push('\n');
            }
            ctx.request_repaint();
        }
        // Keep a compact, high-contrast launcher layout.
        let mut style = (*ctx.style()).clone();
        style.visuals.panel_fill = egui::Color32::from_rgb(14,14,18);
        ctx.set_style(style);

        // Sidebar
        egui::SidePanel::left("sidebar").exact_width(200.0).show(ctx, |ui| {
            ui.vertical(|ui| {
                ui.add_space(12.0);
                ui.label(egui::RichText::new("HITBOY'S").size(22.0).strong().color(egui::Color32::from_rgb(88,166,255)));
                let edition = if self.mixed_compatibility { "MIXED MOD LOADER  v1.0.0" } else { "MOD LOADER  v1.0.0" };
                ui.label(egui::RichText::new(edition).size(10.0).color(egui::Color32::from_rgb(140,140,160)));
                ui.add_space(16.0);
                let home_sel = self.tab == Tab::Home;
                if ui.add_sized([180.0,32.0], egui::SelectableLabel::new(home_sel, "  ◩  HOME")).clicked() { self.tab = Tab::Home; }
                let mods_sel = self.tab == Tab::Mods;
                if ui.add_sized([180.0,32.0], egui::SelectableLabel::new(mods_sel, format!("  ☰  MODS {} | MODULES 7", self.mods.len()))).clicked() { self.tab = Tab::Mods; self.refresh_mods(); }
                let set_sel = self.tab == Tab::Settings;
                if ui.add_sized([180.0,32.0], egui::SelectableLabel::new(set_sel, "  ⚙  SETTINGS")).clicked() { self.tab = Tab::Settings; }
                ui.with_layout(egui::Layout::bottom_up(egui::Align::LEFT), |ui| {
                    ui.label(egui::RichText::new("HITBOY'S MOD LOADER").size(11.0).color(egui::Color32::from_rgb(100,100,120)));
                });
            });
        });

        egui::CentralPanel::default().show(ctx, |ui| {
            match self.tab {
                Tab::Home => {
                    // Banner
                    egui::Frame::none().fill(egui::Color32::from_rgb(24,24,32)).rounding(8.0).inner_margin(egui::Margin::symmetric(16.0,16.0)).show(ui, |ui| {
                        ui.horizontal(|ui| {
                            ui.vertical(|ui| {
                                ui.label(egui::RichText::new("Ready to launch").size(20.0).strong().color(egui::Color32::WHITE));
                                let description = if self.mixed_compatibility {
                                    "Mixed compatibility launcher • port-first validation • HitBoy runtime"
                                } else {
                                    "Standalone offline launcher • HitBoy patch JAR • Java runtime"
                                };
                                ui.label(egui::RichText::new(description).color(egui::Color32::from_rgb(160,160,180)));
                            });
                            ui.with_layout(egui::Layout::right_to_left(egui::Align::Center), |ui| {
                                let btn = egui::Button::new(egui::RichText::new("LAUNCH  ▶").size(16.0).strong().color(egui::Color32::WHITE)).fill(egui::Color32::from_rgb(88,166,255));
                                if ui.add_sized([140.0,40.0], btn).clicked() && !self.launching {
                                    self.launching = true;
                                    let ver = self.version.clone();
                                    let user = self.username.clone();
                                    let ram = self.ram_mb;
                                    let game_dir = self.game_dir.clone();
                                    let mixed_compatibility = self.mixed_compatibility;
                                    let (tx, rx) = channel();
                                    self.log_rx = Some(rx);
                                    self.log += &format!("Launching {} as {} ({} MB) ...\n", ver, user, ram);
                                    let ver2 = ver.clone();
                                    let user2 = user.clone();
                                    let game_dir2 = game_dir.clone();
                                    std::thread::spawn(move || {
                                        let launcher_jar = match launcher_jar() {
                                            Ok(path) => path,
                                            Err(error) => {
                                                let _ = tx.send(format!("Could not prepare embedded launcher: {}", error));
                                                let _ = tx.send("--- Minecraft process finished ---".to_string());
                                                return;
                                            }
                                        };
                                        let java = match java_executable() {
                                            Ok(path) => path,
                                            Err(error) => {
                                                let _ = tx.send(error);
                                                let _ = tx.send("--- Minecraft process finished ---".to_string());
                                                return;
                                            }
                                        };
                                        let mut cmd = launcher_command(
                                            &java, &launcher_jar, &ver2, &game_dir2, Some(&user2), false
                                        );
                                        cmd.env("HITBOY_USERNAME", &user2);
                                        cmd.env("HITBOY_RAM", ram.to_string());
                                        if mixed_compatibility {
                                            cmd.env("HITBOY_MIXED_COMPATIBILITY", "1");
                                        }
                                        let _ = tx.send(format!("Starting Java launcher: {:?}", cmd));
                                        cmd.stdout(Stdio::piped()).stderr(Stdio::piped());
                                        no_window(&mut cmd);
                                        let mut child = match cmd.spawn() {
                                            Ok(c) => c,
                                            Err(e) => {
                                                let _ = tx.send(format!("Failed to spawn java: {}", e));
                                                let _ = tx.send("--- Minecraft process finished ---".to_string());
                                                return;
                                            }
                                        };
                                        let stdout = child.stdout.take().unwrap();
                                        let stderr = child.stderr.take().unwrap();
                                        let tx2 = tx.clone();
                                        std::thread::spawn(move || {
                                            for line in BufReader::new(stderr).lines().flatten() {
                                                let _ = tx2.send(line);
                                            }
                                        });
                                        for line in BufReader::new(stdout).lines().flatten() {
                                            let _ = tx.send(line);
                                        }
                                        let _ = child.wait();
                                        let _ = tx.send("--- Minecraft process finished ---".to_string());
                                    });
                                    self.log += &format!("Delegated to Java launcher. Live log streaming below...\n");
                                }
                            });
                        });
                    });
                    ui.add_space(12.0);
                    ui.columns(2, |cols| {
                        cols[0].group(|ui| {
                            ui.label("Username (offline):");
                            ui.text_edit_singleline(&mut self.username);
                            ui.label("Version:");
                            egui::ComboBox::from_id_source("ver").selected_text(&self.version).show_ui(ui, |ui| {
                                for v in ["26.3", "26.2", "1.21.11", "1.20.1", "1.16.5"] {
                                    ui.selectable_value(&mut self.version, v.to_string(), v);
                                }
                            });
                            if ui.button("Download / Verify").clicked() {
                                let ver = self.version.clone();
                                let game_dir = self.game_dir.clone();
                                self.log += &format!("Downloading {} ... (Mojang manifest ~200MB, live log below)\n", ver);
                                let (tx, rx) = channel();
                                self.log_rx = Some(rx);
                                std::thread::spawn(move || {
let launcher_jar = match launcher_jar() {
    Ok(path) => path,
    Err(error) => { let _ = tx.send(format!("Could not prepare embedded launcher: {}", error)); return; }
};
let java = match java_executable() {
    Ok(path) => path,
    Err(error) => { let _ = tx.send(error); return; }
};
let mut cmd = launcher_command(&java, &launcher_jar, &ver, &game_dir, None, true);
                                    cmd.env("HITBOY_DOWNLOAD_ONLY","1");
                                    let _ = tx.send(format!("Starting Java launcher: {:?}", cmd));
                                    cmd.stdout(Stdio::piped()).stderr(Stdio::piped());
                                    no_window(&mut cmd);
                                    let mut child = match cmd.spawn() {
                                        Ok(c) => c,
                                        Err(e) => { let _ = tx.send(format!("Failed to spawn java: {}", e)); return; }
                                    };
                                    let stdout = child.stdout.take().unwrap();
                                    let stderr = child.stderr.take().unwrap();
                                    let tx2 = tx.clone();
                                    std::thread::spawn(move || {
                                        for line in BufReader::new(stderr).lines().flatten() {
                                            let _ = tx2.send(line);
                                        }
                                    });
                                    for line in BufReader::new(stdout).lines().flatten() {
                                        let _ = tx.send(line);
                                    }
                                    let _ = child.wait();
                                    let _ = tx.send("--- Download thread finished ---".to_string());
                                });
                            }
                            ui.label(egui::RichText::new("Game dir:").small().color(egui::Color32::GRAY));
                            ui.label(egui::RichText::new(&self.game_dir).small());
                        });
                        cols[1].group(|ui| {
                            ui.label("Log:");
                            egui::ScrollArea::vertical().max_height(220.0).show(ui, |ui| {
                                ui.add_sized([ui.available_width(), 200.0], egui::TextEdit::multiline(&mut self.log).desired_width(f32::INFINITY).font(egui::TextStyle::Monospace));
                            });
                            if ui.button("Open game folder").clicked() {
                                let _ = std::process::Command::new("explorer").arg(&self.game_dir).spawn();
                            }
                        });
                    });
                },
                Tab::Mods => {
                    ui.heading(format!("Loaded Mods  •  {}", self.mods.len()));
                    ui.label(egui::RichText::new("Click toggle to enable/disable — creates <id>.disabled flag").small().color(egui::Color32::GRAY));
                    egui::ScrollArea::vertical().show(ui, |ui| {
                        egui::Grid::new("mods_grid").num_columns(2).spacing([10.0,10.0]).show(ui, |ui| {
                            let mut i=0;
                            for m in self.mods.iter_mut() {
                                egui::Frame::none().fill(egui::Color32::from_rgb(24,24,32)).rounding(6.0)
                                    .stroke(if m.enabled { egui::Stroke::new(1.0_f32, egui::Color32::from_rgb(88,166,255)) } else { egui::Stroke::new(1.0_f32, egui::Color32::from_rgb(40,40,50)) })
                                    .inner_margin(10.0).show(ui, |ui| {
                                        ui.vertical(|ui| {
                                            ui.horizontal(|ui| {
                                                ui.label(egui::RichText::new(&m.name).strong().color(egui::Color32::WHITE));
                                                let txt = if m.enabled {"ON"} else {"OFF"};
                                                let col = if m.enabled {egui::Color32::from_rgb(88,166,255)} else {egui::Color32::from_rgb(40,40,50)};
                                                if ui.add(egui::Button::new(txt).fill(col)).clicked() {
                                                    m.enabled = !m.enabled;
                                                    let flag = PathBuf::from(&self.game_dir).join("native_mods").join(format!("{}.disabled", m.id));
                                                    if m.enabled { let _ = fs::remove_file(flag); } else { let _ = fs::File::create(flag); }
                                                }
                                            });
                                            ui.label(egui::RichText::new(format!("{} v{} • {}", m.description, m.version, m.id)).small().color(egui::Color32::from_rgb(160,160,180)));
                                        });
                                    });
                                i+=1;
                                if i % 2 == 0 { ui.end_row(); }
                            }
                        });
                    });
                    if ui.button("Open Mods Folder").clicked() {
                        let _ = std::process::Command::new("explorer").arg(self.mods_dir()).spawn();
                    }
                    if ui.button("Refresh").clicked() { self.refresh_mods(); }
                },
                Tab::Settings => {
                    ui.heading("Settings");
                    ui.group(|ui| {
                        ui.label("RAM (MB):");
                        ui.add(egui::Slider::new(&mut self.ram_mb, 512..=8192).text("MB"));
                        ui.label("Game Directory:");
                        ui.text_edit_singleline(&mut self.game_dir);
                        ui.label(egui::RichText::new("Minecraft 1.21.11 needs Java 21+; 26.x needs Java 25+. The Java launcher uses PATH when compatible, otherwise checks installed JDKs. Set HITBOY_JAVA to override.").small().color(egui::Color32::GRAY));
                        ui.label(egui::RichText::new("This launcher is offline only. To add HitBoy to the official Minecraft Launcher's .minecraft folder, use HitBoy Profile Patcher.").small().color(egui::Color32::GRAY));
                    });
                }
            }
        });
    }
}

fn main() -> eframe::Result<()> {
    let title = if mixed_compatibility_mode() {
        "HitBoy's Mixed Compatibility Mod Loader"
    } else {
        "HitBoy's Mod Loader"
    };
    let opts = eframe::NativeOptions {
        viewport: egui::ViewportBuilder::default().with_inner_size([1080.0, 680.0]).with_icon(
            eframe::icon_data::from_png_bytes(include_bytes!("../HitBoy.png"))
                .expect("embedded HitBoy logo must be a valid PNG")
        ),
        ..Default::default()
    };
    eframe::run_native(title, opts, Box::new(|_cc| Box::new(HitBoysModLoaderApp::new())))
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn launch_command_passes_launcher_flags_after_the_jar() {
        let command = launcher_command(
            Path::new("java"),
            Path::new("hitboys-mod-loader-launcher.jar"),
            "1.20.1",
            "C:\\Games\\HitBoysModLoader",
            Some("Player"),
            false,
        );
        let arguments: Vec<_> = command.get_args().collect();
        assert_eq!(
            arguments,
            [
                "-jar",
                "hitboys-mod-loader-launcher.jar",
                "--version",
                "1.20.1",
                "--game-dir",
                "C:\\Games\\HitBoysModLoader",
                "--username",
                "Player",
            ]
            .iter()
            .map(std::ffi::OsStr::new)
            .collect::<Vec<_>>()
        );
        assert!(!arguments.iter().any(|argument| argument == &std::ffi::OsStr::new("-javaagent")));
    }

    #[test]
    fn download_command_preserves_selected_version() {
        let command = launcher_command(
            Path::new("java"),
            Path::new("hitboys-mod-loader-launcher.jar"),
            "1.20.1",
            "C:\\Games\\HitBoysModLoader",
            None,
            true,
        );
        let arguments: Vec<_> = command.get_args().collect();
        assert_eq!(arguments[0], std::ffi::OsStr::new("-jar"));
        assert_eq!(arguments[3], std::ffi::OsStr::new("1.20.1"));
        assert_eq!(arguments.last(), Some(&std::ffi::OsStr::new("--download-only")));
    }

    #[test]
    fn finds_launcher_beside_a_shipped_rust_executable() {
        let root = std::env::temp_dir().join(format!("hitboys-mod-loader-layout-{}", std::process::id()));
        let app = root.join("HitBoysModLoader").join("app");
        fs::create_dir_all(&app).unwrap();
        fs::File::create(app.join(LAUNCHER_JAR_NAME)).unwrap();

        assert_eq!(packaged_app_directory_at(&root), Some(app.clone()));

        fs::remove_dir_all(root).unwrap();
    }

    #[test]
    fn finds_patch_jar_beside_a_flat_distribution_executable() {
        let root = std::env::temp_dir().join(format!(
            "hitboys-mod-loader-flat-layout-{}",
            std::process::id()
        ));
        fs::create_dir_all(&root).unwrap();
        fs::File::create(root.join(LAUNCHER_JAR_NAME)).unwrap();

        assert_eq!(packaged_app_directory_at(&root), Some(root.clone()));

        fs::remove_dir_all(root).unwrap();
    }
}
