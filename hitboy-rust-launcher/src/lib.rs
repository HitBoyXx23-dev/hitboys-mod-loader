use std::fs;
use std::io::Cursor;
use std::path::{Path, PathBuf};

pub const LAUNCHER_JAR_NAME: &str = "hitboys-mod-loader-patch-1.0.0-SNAPSHOT.jar";
const EMBEDDED_LAUNCHER_JAR: &[u8] = include_bytes!("../../dist/hitboys-mod-loader-patch-1.0.0-SNAPSHOT.jar");

pub fn launcher_jar() -> Result<PathBuf, String> {
    let executable = std::env::current_exe().map_err(|error| error.to_string())?;
    if let Some(directory) = executable.parent() {
        let adjacent = directory.join(LAUNCHER_JAR_NAME);
        if adjacent.is_file() {
            return Ok(adjacent);
        }
    }
    let cache = dirs::data_local_dir().or_else(dirs::home_dir).unwrap_or(PathBuf::from("."))
        .join("HitBoysModLoader").join("runtime");
    fs::create_dir_all(&cache).map_err(|error| error.to_string())?;
    let destination = cache.join(LAUNCHER_JAR_NAME);
    if fs::read(&destination).ok().as_deref() != Some(EMBEDDED_LAUNCHER_JAR) {
        fs::write(&destination, EMBEDDED_LAUNCHER_JAR).map_err(|error| error.to_string())?;
    }
    Ok(destination)
}

/// Oldest Java the HitBoy runtime accepts. Java 25 runs every supported Minecraft from 1.20.1 to 26.x.
const REQUIRED_JAVA: u32 = 25;

pub fn java_executable() -> Result<PathBuf, String> {
    if let Some(configured) = std::env::var_os("HITBOY_JAVA") {
        let path = PathBuf::from(configured);
        if path.is_file() { return Ok(path); }
    }
    let mut candidates = Vec::new();
    if let Some(java_home) = std::env::var_os("JAVA_HOME") {
        candidates.push(PathBuf::from(java_home).join("bin").join(java_name()));
    }
    candidates.push(PathBuf::from("java"));
    let mut roots = vec![private_java_root()];
    if let Some(app_data) = std::env::var_os("APPDATA") {
        roots.push(PathBuf::from(app_data).join(".minecraft").join("runtime"));
    }
    if let Some(program_files) = std::env::var_os("ProgramFiles") {
        let root = PathBuf::from(program_files);
        for vendor in ["Microsoft", "Eclipse Adoptium", "Java", "Amazon Corretto", "Zulu"] {
            roots.push(root.join(vendor));
        }
    }
    for root in roots {
        find_all_java(&root, 0, &mut candidates);
    }
    // Pick the newest runtime: the official launcher's .minecraftuntime often also holds Java 8 or 16,
    // which cannot run HitBoy.
    let best = candidates.into_iter()
        .filter_map(|path| java_feature(&path).map(|feature| (feature, path)))
        .max_by_key(|(feature, _)| *feature);
    match best {
        Some((feature, path)) if feature >= REQUIRED_JAVA => Ok(path),
        _ => install_private_java(),
    }
}

fn java_name() -> &'static str { if cfg!(windows) { "java.exe" } else { "java" } }

fn private_java_root() -> PathBuf {
    dirs::data_local_dir()
        .or_else(dirs::home_dir)
        .unwrap_or(PathBuf::from("."))
        .join("HitBoysModLoader")
        .join("runtime")
        .join("java")
}

/// Major Java version reported by `java -version`, e.g. 25 or 8.
fn java_feature(java: &Path) -> Option<u32> {
    let mut command = std::process::Command::new(java);
    command.arg("-version");
    #[cfg(windows)]
    {
        use std::os::windows::process::CommandExt;
        command.creation_flags(0x0800_0000); // CREATE_NO_WINDOW
    }
    let output = command.output().ok()?;
    if !output.status.success() { return None; }
    let text = String::from_utf8_lossy(&output.stderr);
    let version = text.split('"').nth(1)?;
    let mut parts = version.split(|c: char| c == '.' || c == '-' || c == '+' || c == '_');
    let first: u32 = parts.next()?.parse().ok()?;
    if first == 1 { parts.next()?.parse().ok() } else { Some(first) }
}

fn find_all_java(directory: &Path, depth: usize, found: &mut Vec<PathBuf>) {
    if depth > 8 || !directory.is_dir() { return; }
    let direct = directory.join("bin").join(java_name());
    if direct.is_file() {
        found.push(direct);
        return;
    }
    if let Ok(entries) = fs::read_dir(directory) {
        for entry in entries.flatten() {
            if entry.file_type().map(|kind| kind.is_dir()).unwrap_or(false) {
                find_all_java(&entry.path(), depth + 1, found);
            }
        }
    }
}

fn find_java(directory: &Path) -> Option<PathBuf> {
    let mut found = Vec::new();
    find_all_java(directory, 0, &mut found);
    found.into_iter().next()
}

fn install_private_java() -> Result<PathBuf, String> {
    let runtime = private_java_root();
    if let Some(path) = find_java(&runtime).filter(|path| java_feature(path).unwrap_or(0) >= REQUIRED_JAVA) {
        return Ok(path);
    }
    fs::create_dir_all(&runtime).map_err(|error| error.to_string())?;
    let url = if cfg!(target_arch = "aarch64") {
        "https://api.adoptium.net/v3/binary/latest/25/ga/windows/aarch64/jre/hotspot/normal/eclipse"
    } else {
        "https://api.adoptium.net/v3/binary/latest/25/ga/windows/x64/jre/hotspot/normal/eclipse"
    };
    let response = reqwest::blocking::Client::new()
        .get(url)
        .header("User-Agent", "HitBoysModLoader/1.0")
        .send()
        .map_err(|error| format!("Java download failed: {error}"))?
        .error_for_status()
        .map_err(|error| format!("Java download failed: {error}"))?;
    let bytes = response.bytes().map_err(|error| format!("Java download failed: {error}"))?;
    let mut archive = zip::ZipArchive::new(Cursor::new(bytes))
        .map_err(|error| format!("Downloaded Java archive is invalid: {error}"))?;
    for index in 0..archive.len() {
        let mut entry = archive.by_index(index).map_err(|error| error.to_string())?;
        let Some(relative) = entry.enclosed_name().map(|path| path.to_path_buf()) else {
            continue;
        };
        let output = runtime.join(relative);
        if entry.is_dir() {
            fs::create_dir_all(&output).map_err(|error| error.to_string())?;
        } else {
            if let Some(parent) = output.parent() {
                fs::create_dir_all(parent).map_err(|error| error.to_string())?;
            }
            let mut file = fs::File::create(&output).map_err(|error| error.to_string())?;
            std::io::copy(&mut entry, &mut file).map_err(|error| error.to_string())?;
        }
    }
    find_java(&runtime).ok_or_else(|| "Java downloaded, but its executable was not found.".to_string())
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn embedded_launcher_is_available_without_distribution_files() {
        let jar = launcher_jar().unwrap();
        let bytes = fs::read(jar).unwrap();
        assert_eq!(bytes.as_slice(), EMBEDDED_LAUNCHER_JAR);
        assert!(bytes.starts_with(b"PK"));
    }

    #[test]
    fn discovers_a_working_java_runtime() {
        let java = java_executable().unwrap();
        assert!(std::process::Command::new(java).arg("-version").output().unwrap().status.success());
    }
}
