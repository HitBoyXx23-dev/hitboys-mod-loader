@echo off
setlocal
set "ROOT=%~dp0"
set "JAR=%ROOT%dist\hitboys-mod-loader-patch-1.0.0-SNAPSHOT.jar"
if not exist "%JAR%" (
  echo HitBoy launcher JAR was not found.
  pause
  exit /b 1
)
start "" javaw -cp "%JAR%" com.hitboy.launcher.PortGui
if errorlevel 1 java -cp "%JAR%" com.hitboy.launcher.PortGui
endlocal
