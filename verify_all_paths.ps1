# Comprehensive Path Verification Script

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Path Verification Report" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$templatesPath = "src\main\resources\templates"

# Check key files
$keyFiles = @(
    "fragments\header.html",
    "fragments\footer.html",
    "systemAdmin\systemAdminHome.html",
    "admin\adminHome.html",
    "login.html"
)

$allGood = $true

foreach ($file in $keyFiles) {
    $fullPath = Join-Path $templatesPath $file
    
    if (Test-Path $fullPath) {
        Write-Host "Checking: $file" -ForegroundColor Yellow
        
        $content = Get-Content $fullPath -Raw
        
        # Count paths with leading slash (correct)
        $correctPaths = ([regex]::Matches($content, 'th:(src|href)="@\{/')).Count
        
        # Count paths without leading slash (incorrect)
        $incorrectPaths = ([regex]::Matches($content, 'th:(src|href)="@\{[^/]')).Count
        
        # Check for hardcoded relative paths
        $hardcodedPaths = ([regex]::Matches($content, '(src|href)="\.\.')).Count
        
        Write-Host "  Correct Thymeleaf paths: $correctPaths" -ForegroundColor Green
        
        if ($incorrectPaths -gt 0) {
            Write-Host "  Incorrect Thymeleaf paths: $incorrectPaths" -ForegroundColor Red
            $allGood = $false
        } else {
            Write-Host "  No incorrect Thymeleaf paths" -ForegroundColor Green
        }
        
        if ($hardcodedPaths -gt 0) {
            Write-Host "  Hardcoded relative paths: $hardcodedPaths" -ForegroundColor Red
            $allGood = $false
        } else {
            Write-Host "  No hardcoded relative paths" -ForegroundColor Green
        }
        
        Write-Host ""
    } else {
        Write-Host "  File not found: $fullPath" -ForegroundColor Red
        $allGood = $false
        Write-Host ""
    }
}

Write-Host "========================================" -ForegroundColor Cyan

if ($allGood) {
    Write-Host "ALL PATHS ARE CORRECT!" -ForegroundColor Green
    Write-Host ""
    Write-Host "Next steps:" -ForegroundColor Yellow
    Write-Host "1. Restart your application in Eclipse" -ForegroundColor White
    Write-Host "2. Clear browser cache (Ctrl+Shift+Delete)" -ForegroundColor White
    Write-Host "3. Login at: http://localhost:8085/anuppur/login" -ForegroundColor White
} else {
    Write-Host "SOME ISSUES FOUND - See details above" -ForegroundColor Red
}

Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
