@echo off
setlocal
cd /d "%~dp0"
powershell.exe -NoLogo -NoProfile -ExecutionPolicy Bypass -File "%~dp0start-frontend-dev.ps1"
if errorlevel 1 (
  echo.
  echo Startup failed. Review the error above.
  pause
)
