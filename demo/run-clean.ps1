# Script to run Spring Boot application without test data
# PowerShell version for Windows

Write-Host "================================" -ForegroundColor Cyan
Write-Host "Spring Boot App (Clean Mode)" -ForegroundColor Cyan
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

Write-Host "[1/2] Building the project..." -ForegroundColor Yellow
& .\mvnw.cmd clean package -DskipTests -q
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Build failed" -ForegroundColor Red
    exit 1
}
Write-Host "Build completed" -ForegroundColor Green
Write-Host ""

Write-Host "[2/2] Starting application (clean mode - no test data)" -ForegroundColor Yellow
Write-Host ""

# Run with default profile (dataloader disabled)
& .\mvnw.cmd spring-boot:run

if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Application failed to start" -ForegroundColor Red
    exit 1
}
