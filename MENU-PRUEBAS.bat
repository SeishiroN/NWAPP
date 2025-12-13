@echo off
setlocal enabledelayedexpansion
color 0A

:menu
cls
echo ========================================
echo    MENU DE PRUEBAS UNITARIAS - NWAPP
echo ========================================
echo.
echo  Selecciona que pruebas ejecutar:
echo.
echo  [1] DatosTest                (5 pruebas - MAS FACIL)
echo  [2] ValidationUtilsTest     (28 pruebas - MUY FACIL)
echo  [3] AuthModelsTest           (5 pruebas - FACIL)
echo  [4] SignupViewModelBasicTest (6 pruebas - MODERADO)
echo  [5] FormularioRepositoryTest (4 pruebas - INTERMEDIO)
echo.
echo  [A] TODAS las pruebas      (48+ pruebas)
echo  [C] Compilar y ejecutar (RECOMENDADO si hay errores)
echo  [D] Diagnosticar problemas
echo  [R] Ver ultimo reporte HTML
echo  [S] Sincronizar Gradle
echo.
echo  [0] Salir
echo.
echo ========================================
echo.
set /p opcion="Ingresa tu opcion: "

if "%opcion%"=="1" goto prueba1
if "%opcion%"=="2" goto prueba2
if "%opcion%"=="3" goto prueba3
if "%opcion%"=="4" goto prueba4
if "%opcion%"=="5" goto prueba5
if /i "%opcion%"=="A" goto todas
if /i "%opcion%"=="C" goto compilar
if /i "%opcion%"=="D" goto diagnosticar
if /i "%opcion%"=="R" goto reporte
if /i "%opcion%"=="S" goto sync
if "%opcion%"=="0" goto salir
goto menu

:prueba1
cls
echo Ejecutando: DatosTest (La mas facil)
echo ========================================
cd /d "D:\Codificacion\Androidd\NWAPP"
call gradlew.bat testDebugUnitTest --tests "cl.duoc.nwapp.model.DatosTest" --console=plain
goto fin

:prueba2
cls
echo Ejecutando: ValidationUtilsTest
echo ========================================
cd /d "D:\Codificacion\Androidd\NWAPP"
call gradlew.bat testDebugUnitTest --tests "cl.duoc.nwapp.utils.ValidationUtilsTest" --console=plain
goto fin

:prueba3
cls
echo Ejecutando: AuthModelsTest
echo ========================================
cd /d "D:\Codificacion\Androidd\NWAPP"
call gradlew.bat testDebugUnitTest --tests "cl.duoc.nwapp.data.model.AuthModelsTest" --console=plain
goto fin

:prueba4
cls
echo Ejecutando: SignupViewModelBasicTest
echo ========================================
cd /d "D:\Codificacion\Androidd\NWAPP"
call gradlew.bat testDebugUnitTest --tests "cl.duoc.nwapp.viewmodel.SignupViewModelBasicTest" --console=plain
goto fin

:prueba5
cls
echo Ejecutando: FormularioRepositoryTest
echo ========================================
cd /d "D:\Codificacion\Androidd\NWAPP"
call gradlew.bat testDebugUnitTest --tests "cl.duoc.nwapp.repository.FormularioRepositoryTest" --console=plain
goto fin

:todas
cls
echo Ejecutando: TODAS las pruebas unitarias
echo ========================================
cd /d "D:\Codificacion\Androidd\NWAPP"
call gradlew.bat testDebugUnitTest --console=plain
goto fin

:compilar
cls
echo Compilando y ejecutando pruebas (Metodo completo)
echo ========================================
cd /d "D:\Codificacion\Androidd\NWAPP"
call ejecutar-pruebas-corregido.bat
goto menu

:diagnosticar
cls
echo Diagnosticando problemas...
echo ========================================
cd /d "D:\Codificacion\Androidd\NWAPP"
call diagnostico.bat
goto menu

:reporte
cd /d "D:\Codificacion\Androidd\NWAPP"
if exist "app\build\reports\tests\testDebugUnitTest\index.html" (
    echo Abriendo reporte HTML...
    start "" "app\build\reports\tests\testDebugUnitTest\index.html"
) else (
    echo.
    echo [ERROR] No se encontro el reporte HTML
    echo Primero debes ejecutar las pruebas
)
pause
goto menu

:sync
cls
echo Sincronizando Gradle...
echo ========================================
cd /d "D:\Codificacion\Androidd\NWAPP"
call gradlew.bat --refresh-dependencies
echo.
echo Sincronizacion completada!
pause
goto menu

:fin
echo.
echo ========================================
echo       PRUEBAS COMPLETADAS
echo ========================================
echo.
if exist "app\build\reports\tests\testDebugUnitTest\index.html" (
    echo [OK] Reporte generado exitosamente
    echo.
    set /p abrir="Deseas abrir el reporte HTML? (S/N): "
    if /i "!abrir!"=="S" (
        start "" "app\build\reports\tests\testDebugUnitTest\index.html"
    )
)
echo.
pause
goto menu

:salir
echo.
echo Saliendo...
exit /b 0

