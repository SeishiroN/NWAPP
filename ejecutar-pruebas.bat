@echo off
cd /d D:\Codificacion\Androidd\NWAPP
echo Ejecutando pruebas unitarias...
echo.
call gradlew.bat test --console=plain

