@echo off
REM Script para ejecutar las pruebas unitarias del proyecto NWAPP

echo ========================================
echo    EJECUTANDO PRUEBAS UNITARIAS
echo ========================================
echo.

echo [1/3] Sincronizando dependencias...
call gradlew.bat --refresh-dependencies

echo.
echo [2/3] Compilando proyecto...
call gradlew.bat assembleDebug

echo.
echo [3/3] Ejecutando pruebas unitarias...
call gradlew.bat test

echo.
echo ========================================
echo    PRUEBAS COMPLETADAS
echo ========================================
echo.
echo Para ver el reporte completo, abre:
echo app\build\reports\tests\testDebugUnitTest\index.html
echo.

pause

