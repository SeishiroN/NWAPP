@echo off
setlocal enabledelayedexpansion
color 0E

echo ========================================
echo  EJECUTAR PRUEBAS - VERSION CORREGIDA
echo ========================================
echo.

cd /d "D:\Codificacion\Androidd\NWAPP"

echo [PASO 1/5] Limpiando compilaciones anteriores...
call gradlew.bat clean --quiet
if errorlevel 1 (
    echo [ERROR] Fallo al limpiar el proyecto
    goto error
)
echo [OK] Limpieza completada
echo.

echo [PASO 2/5] Compilando el proyecto principal...
call gradlew.bat assembleDebug --quiet
if errorlevel 1 (
    echo [ERROR] Fallo al compilar el proyecto principal
    echo.
    echo Ejecutando con mas detalles...
    call gradlew.bat assembleDebug --stacktrace
    goto error
)
echo [OK] Proyecto principal compilado
echo.

echo [PASO 3/5] Compilando tests unitarios...
call gradlew.bat compileDebugUnitTestKotlin --quiet
if errorlevel 1 (
    echo [ERROR] Fallo al compilar las pruebas unitarias
    echo.
    echo Ejecutando con mas detalles...
    call gradlew.bat compileDebugUnitTestKotlin --stacktrace
    goto error
)
echo [OK] Tests compilados correctamente
echo.

echo [PASO 4/5] Ejecutando pruebas unitarias...
echo ========================================
call gradlew.bat testDebugUnitTest --console=plain --info
set TEST_RESULT=!errorlevel!
echo ========================================
echo.

echo [PASO 5/5] Generando reporte...
if exist "app\build\reports\tests\testDebugUnitTest\index.html" (
    echo [OK] Reporte generado exitosamente
    echo.
    if !TEST_RESULT! EQU 0 (
        echo ========================================
        echo  TODAS LAS PRUEBAS PASARON! ^_^
        echo ========================================
    ) else (
        echo ========================================
        echo  ALGUNAS PRUEBAS FALLARON!
        echo ========================================
        echo.
        echo Revisa el reporte HTML para ver detalles.
    )
    echo.
    set /p abrir="Deseas abrir el reporte HTML? (S/N): "
    if /i "!abrir!"=="S" (
        start "" "app\build\reports\tests\testDebugUnitTest\index.html"
    )
) else (
    echo [ADVERTENCIA] No se genero el reporte HTML
)

goto end

:error
echo.
echo ========================================
echo  OCURRIO UN ERROR
echo ========================================
echo.
echo Por favor revisa los mensajes de error arriba.
echo.
echo Posibles soluciones:
echo 1. Ejecuta: File ^> Sync Project with Gradle Files en Android Studio
echo 2. Verifica que tienes conexion a internet (para descargar dependencias)
echo 3. Ejecuta: gradlew.bat --refresh-dependencies
echo.

:end
echo.
pause

