@echo off
color 0A
echo.
echo ================================================
echo   VERIFICACION RAPIDA - Prueba mas facil
echo ================================================
echo.
echo Ejecutando solo DatosTest (5 pruebas)...
echo.

cd /d "D:\Codificacion\Androidd\NWAPP"

call gradlew.bat clean testDebugUnitTest --tests "cl.duoc.nwapp.model.DatosTest" --console=plain

echo.
echo ================================================
if errorlevel 1 (
    echo   RESULTADO: Algunas pruebas fallaron
    echo.
    echo   Lee SOLUCION-ERRORES.md para mas ayuda
) else (
    echo   RESULTADO: TODAS LAS PRUEBAS PASARON!
    echo.
    echo   Las pruebas estan funcionando correctamente.
)
echo ================================================
echo.

if exist "app\build\reports\tests\testDebugUnitTest\index.html" (
    echo Abriendo reporte HTML...
    start "" "app\build\reports\tests\testDebugUnitTest\index.html"
)

echo.
pause

