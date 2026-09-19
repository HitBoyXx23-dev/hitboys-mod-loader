using System.Diagnostics;
using System.Runtime.InteropServices;

internal static class Program
{
    [DllImport("user32.dll", CharSet = CharSet.Unicode)]
    private static extern int MessageBoxW(nint window, string text, string title, uint type);

    private static int Main()
    {
        string executable = Environment.ProcessPath ?? "";
        string name = Path.GetFileNameWithoutExtension(executable).ToLowerInvariant();
        string directory = Path.GetDirectoryName(executable) ?? Environment.CurrentDirectory;
        string jar = Path.Combine(directory, "hitboys-mod-loader-patch-1.0.0-SNAPSHOT.jar");
        string mainClass = name == "port"
            ? "com.hitboy.launcher.PortGui"
            : name.Contains("compatibility")
                ? "com.hitboy.launcher.MixedCompatibilityGui"
                : "com.hitboy.launcher.HitBoyLauncher";

        if (!File.Exists(jar))
        {
            MessageBoxW(0, "Place this executable beside hitboys-mod-loader-patch-1.0.0-SNAPSHOT.jar.", "HitBoy's Mod Loader", 0x10);
            return 1;
        }

        try
        {
            ProcessStartInfo start = new()
            {
                FileName = "javaw.exe",
                UseShellExecute = false,
                WorkingDirectory = directory
            };
            start.ArgumentList.Add("-cp");
            start.ArgumentList.Add(jar);
            start.ArgumentList.Add(mainClass);
            Process.Start(start);
            return 0;
        }
        catch (Exception exception)
        {
            MessageBoxW(0, "Java could not be started.\n\n" + exception.Message, "HitBoy's Mod Loader", 0x10);
            return 1;
        }
    }
}
