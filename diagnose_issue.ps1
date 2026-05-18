# Diagnostic Script to Identify Frontend Issues

Write-Host ""
Write-Host "FRONTEND DIAGNOSTIC TOOL" -ForegroundColor Cyan
Write-Host "========================" -ForegroundColor Cyan
Write-Host ""

# Check if application is running
Write-Host "1. Checking if application is running..." -ForegroundColor Yellow
Write-Host ""

try {
    $response = Invoke-WebRequest -Uri "http://localhost:8085/anuppur/" -UseBasicParsing -TimeoutSec 5 -ErrorAction Stop
    Write-Host "   Application is RUNNING" -ForegroundColor Green
    Write-Host "   Status Code: $($response.StatusCode)" -ForegroundColor Green
} catch {
    Write-Host "   Application is NOT RUNNING or not accessible" -ForegroundColor Red
    Write-Host "   Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
    Write-Host "   Please start your application from Eclipse first!" -ForegroundColor Yellow
    exit
}

Write-Host ""
Write-Host "2. Testing static resource accessibility..." -ForegroundColor Yellow
Write-Host ""

# Test CSS files
$resources = @(
    @{Name="Bootstrap CSS"; Url="http://localhost:8085/anuppur/new-assets/css/bootstrap.min.css"},
    @{Name="FontAwesome CSS"; Url="http://localhost:8085/anuppur/new-assets/css/fontawesome.min.css"},
    @{Name="Style CSS"; Url="http://localhost:8085/anuppur/new-assets/css/style.css"},
    @{Name="jQuery JS"; Url="http://localhost:8085/anuppur/new-assets/js/jquery.min.js"},
    @{Name="Bootstrap JS"; Url="http://localhost:8085/anuppur/new-assets/js/bootstrap.min.js"},
    @{Name="Logo Image"; Url="http://localhost:8085/anuppur/new-assets/img/logo.png"}
)

$allPassed = $true

foreach ($resource in $resources) {
    try {
        $response = Invoke-WebRequest -Uri $resource.Url -UseBasicParsing -TimeoutSec 5 -ErrorAction Stop
        Write-Host "   $($resource.Name): OK (Status: $($response.StatusCode))" -ForegroundColor Green
    } catch {
        Write-Host "   $($resource.Name): FAILED" -ForegroundColor Red
        Write-Host "     URL: $($resource.Url)" -ForegroundColor Red
        Write-Host "     Error: $($_.Exception.Message)" -ForegroundColor Red
        $allPassed = $false
    }
}

Write-Host ""
Write-Host "3. Checking file existence..." -ForegroundColor Yellow
Write-Host ""

$files = @(
    "src\main\resources\static\new-assets\css\bootstrap.min.css",
    "src\main\resources\static\new-assets\css\fontawesome.min.css",
    "src\main\resources\static\new-assets\css\style.css",
    "src\main\resources\static\new-assets\js\jquery.min.js",
    "src\main\resources\static\new-assets\js\bootstrap.min.js"
)

foreach ($file in $files) {
    if (Test-Path $file) {
        $size = (Get-Item $file).Length
        $sizeKB = [math]::Round($size/1KB, 2)
        Write-Host "   $file ($sizeKB KB)" -ForegroundColor Green
    } else {
        Write-Host "   $file NOT FOUND" -ForegroundColor Red
        $allPassed = $false
    }
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

if ($allPassed) {
    Write-Host "ALL CHECKS PASSED!" -ForegroundColor Green
    Write-Host ""
    Write-Host "Your application is configured correctly." -ForegroundColor Green
    Write-Host ""
    Write-Host "If CSS is still not working in your browser:" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "1. Clear browser cache (Ctrl + Shift + Delete)" -ForegroundColor White
    Write-Host "2. Hard refresh (Ctrl + F5)" -ForegroundColor White
    Write-Host "3. Try Incognito mode (Ctrl + Shift + N)" -ForegroundColor White
    $correctUrl = "http://localhost:8085/anuppur/"
    Write-Host "4. Make sure you are accessing: $correctUrl" -ForegroundColor White
    Write-Host ""
    Write-Host "5. Open browser DevTools (F12) and check:" -ForegroundColor White
    Write-Host "   - Console tab for errors" -ForegroundColor White
    Write-Host "   - Network tab to see if CSS files are loading" -ForegroundColor White
} else {
    Write-Host "SOME CHECKS FAILED!" -ForegroundColor Red
    Write-Host ""
    Write-Host "Please fix the issues above and try again." -ForegroundColor Yellow
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
