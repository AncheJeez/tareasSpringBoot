# Script to run Spring Boot application with test data loaded
# PowerShell version for Windows

Write-Host "================================" -ForegroundColor Cyan
Write-Host "Spring Boot App with Test Data" -ForegroundColor Cyan
Write-Host "================================" -ForegroundColor Cyan
Write-Host ""

# Navigate to the demo directory
$scriptPath = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptPath

# Check if mvnw.cmd exists
if (-not (Test-Path ".\mvnw.cmd")) {
    Write-Host "ERROR: mvnw.cmd not found in $scriptPath" -ForegroundColor Red
    exit 1
}

Write-Host "[1/3] Building the project..." -ForegroundColor Yellow
& .\mvnw.cmd clean package -DskipTests -q
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Build failed" -ForegroundColor Red
    exit 1
}
Write-Host "Build completed" -ForegroundColor Green
Write-Host ""

Write-Host "[2/3] Loading test data..." -ForegroundColor Yellow
Write-Host "Starting Spring Boot application with dev profile (test data enabled)" -ForegroundColor Cyan
Write-Host ""

# Ejecución corregida utilizando el operador '--%' para pasar los argumentos literalmente a Maven
& .\mvnw.cmd spring-boot:run --% "-Dspring-boot.run.arguments=--spring.profiles.active=dev"

if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Application failed to start" -ForegroundColor Red
    exit 1
}