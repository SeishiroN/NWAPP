@echo off
setlocal enabledelayedexpansion

echo ========================================
echo    EJECUTANDO PRUEBAS UNITARIAS
echo ========================================
echo.

cd /d "D:\Codificacion\Androidd\NWAPP"

echo [INFO] Ubicacion actual: %CD%
echo.

echo [1/2] Compilando proyecto...
call gradlew.bat compileDebugUnitTestKotlin
if errorlevel 1 (
    echo [ERROR] Fallo la compilacion
    pause
    exit /b 1
)

echo.
echo [2/2] Ejecutando pruebas unitarias...
echo.
call gradlew.bat testDebugUnitTest --info --stacktrace

echo.
echo ========================================
echo    RESULTADOS
echo ========================================
echo.

if exist "app\build\reports\tests\testDebugUnitTest\index.html" (
    echo [OK] Reporte generado exitosamente
    echo.
    echo Abriendo reporte en el navegador...
    start "" "app\build\reports\tests\testDebugUnitTest\index.html"
) else (
    echo [ADVERTENCIA] No se encontro el reporte HTML
)

echo.
echo Presiona cualquier tecla para continuar...
pause >nul

