@echo off
echo ========================================
echo  PRUEBA 2: ValidationUtilsTest
echo ========================================
echo.
cd /d "D:\Codificacion\Androidd\NWAPP"
call gradlew.bat testDebugUnitTest --tests "cl.duoc.nwapp.utils.ValidationUtilsTest" --console=plain
echo.
if exist "app\build\reports\tests\testDebugUnitTest\index.html" (
    start "" "app\build\reports\tests\testDebugUnitTest\index.html"
)
pause

