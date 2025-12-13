@echo off
color 0A
cls
echo.
echo ================================================
echo   PRUEBA SIMPLE - DatosTest
echo ================================================
echo.

cd /d "D:\Codificacion\Androidd\NWAPP"

echo Ejecutando las 5 pruebas mas faciles...
echo.

call gradlew.bat clean compileDebugUnitTestKotlin testDebugUnitTest --tests "cl.duoc.nwapp.model.DatosTest"

echo.
echo ================================================
if errorlevel 1 (
    echo   Hubo errores - Ver detalles arriba
) else (
    echo   EXITO! Todas las pruebas pasaron!
)
echo ================================================
echo.

if exist "app\build\reports\tests\testDebugUnitTest\index.html" (
    start "" "app\build\reports\tests\testDebugUnitTest\index.html"
)

pause

