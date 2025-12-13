@echo off
echo ========================================
echo  PRUEBA 3: AuthModelsTest
echo ========================================
echo.
cd /d "D:\Codificacion\Androidd\NWAPP"
call gradlew.bat testDebugUnitTest --tests "cl.duoc.nwapp.data.model.AuthModelsTest" --console=plain
echo.
if exist "app\build\reports\tests\testDebugUnitTest\index.html" (
    start "" "app\build\reports\tests\testDebugUnitTest\index.html"
)
pause

