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

pub fn java_executable() -> Result<PathBuf, String> {
    if let Some(configured) = std::env::var_os("HITBOY_JAVA") {
        let path = PathBuf::from(configured);
        if path.is_file() { return Ok(path); }
    }
    if let Some(java_home) = std::env::var_os("JAVA_HOME") {
        let path = PathBuf::from(java_home).join("bin").join(java_name());
        if path.is_file() { return Ok(path); }
    }
    if command_works("java") { return Ok(PathBuf::from("java")); }
    let mut roots = Vec::new();
    if let Some(app_data) = std::env::var_os("APPDATA") {
        roots.push(PathBuf::from(app_data).join(".minecraft").join("runtime"));
    }
    if let Some(program_files) = std::env::var_os("ProgramFiles") {
        let root = PathBuf::from(program_files);
        roots.push(root.join("Microsoft"));
        roots.push(root.join("Eclipse Adoptium"));
        roots.push(root.join("Java"));
    }
    for root in roots {
        if let Some(path) = find_java(&root, 0) { return Ok(path); }
    }
    install_private_java()
}

fn java_name() -> &'static str { if cfg!(windows) { "java.exe" } else { "java" } }

fn command_works(command: &str) -> bool {
    std::process::Command::new(command).arg("-version").output()
        .map(|result| result.status.success()).unwrap_or(false)
}

fn find_java(directory: &Path, depth: usize) -> Option<PathBuf> {
    if depth > 8 || !directory.is_dir() { return None; }
    let direct = directory.join("bin").join(java_name());
    if direct.is_file() { return Some(direct); }
    for entry in fs::read_dir(directory).ok()?.flatten() {
        if entry.file_type().ok()?.is_dir() {
            if let Some(path) = find_java(&entry.path(), depth + 1) { return Some(path); }
        }
    }
    None
}

fn install_private_java() -> Result<PathBuf, String> {
    let runtime = dirs::data_local_dir()
        .or_else(dirs::home_dir)
        .unwrap_or(PathBuf::from("."))
        .join("HitBoysModLoader")
        .join("runtime")
        .join("java");
    if let Some(path) = find_java(&runtime, 0) {
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
    find_java(&runtime, 0).ok_or_else(|| "Java downloaded, but its executable was not found.".to_string())
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
