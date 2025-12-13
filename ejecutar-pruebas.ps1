# Script para ejecutar pruebas unitarias en NWAPP
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   EJECUTANDO PRUEBAS UNITARIAS NWAPP  " -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Cambiar al directorio del proyecto
Set-Location -Path "D:\Codificacion\Androidd\NWAPP"

Write-Host "[1/3] Verificando Gradle..." -ForegroundColor Yellow
if (Test-Path ".\gradlew.bat") {
    Write-Host "✓ gradlew.bat encontrado" -ForegroundColor Green
} else {
    Write-Host "✗ gradlew.bat NO encontrado" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "[2/3] Limpiando compilaciones anteriores..." -ForegroundColor Yellow
& .\gradlew.bat clean

Write-Host ""
Write-Host "[3/3] Ejecutando pruebas unitarias..." -ForegroundColor Yellow
Write-Host ""
& .\gradlew.bat test --console=plain

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   PRUEBAS COMPLETADAS                 " -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Verificar si existe el reporte
$reportPath = "app\build\reports\tests\testDebugUnitTest\index.html"
if (Test-Path $reportPath) {
    Write-Host "✓ Reporte generado exitosamente" -ForegroundColor Green
    Write-Host ""
    Write-Host "Abriendo reporte HTML..." -ForegroundColor Yellow
    Start-Process $reportPath
} else {
    Write-Host "! No se pudo generar el reporte HTML" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "Presiona cualquier tecla para salir..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

