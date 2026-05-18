# PowerShell script to fix Angular AJAX calls to include context path
# Adds getBaseUrl() + prefix to all $http calls that don't already have it

$angularPath = "src\main\resources\static\angular"

# Get all JS files in angular directory
$jsFiles = Get-ChildItem -Path $angularPath -Filter "*.js" -Recurse

$totalFiles = 0
$totalReplacements = 0

Write-Host "Starting Angular AJAX path fixes..." -ForegroundColor Cyan
Write-Host "Found $($jsFiles.Count) JavaScript files to process" -ForegroundColor Yellow
Write-Host ""

foreach ($file in $jsFiles) {
    $content = Get-Content -Path $file.FullName -Raw -Encoding UTF8
    $originalContent = $content
    $fileReplacements = 0
    
    # Pattern 1: $http.get('path') -> $http.get(getBaseUrl() + '/path')
    # Only fix if path starts with single quote and doesn't already have getBaseUrl()
    if ($content -match '\$http\.get\(''[^/]' -and $content -notmatch 'getBaseUrl\(\)') {
        $pattern = '\$http\.get\(''([^'']+)''\)'
        $replacement = '$http.get(getBaseUrl() + ''/$1'')'
        $matches = [regex]::Matches($content, $pattern)
        if ($matches.Count -gt 0) {
            $content = $content -replace $pattern, $replacement
            $fileReplacements += $matches.Count
        }
    }
    
    # Pattern 2: $http.post('path') -> $http.post(getBaseUrl() + '/path')
    if ($content -match '\$http\.post\(''[^/]' -and $content -notmatch 'getBaseUrl\(\)') {
        $pattern = '\$http\.post\(''([^'']+)''\)'
        $replacement = '$http.post(getBaseUrl() + ''/$1'')'
        $matches = [regex]::Matches($content, $pattern)
        if ($matches.Count -gt 0) {
            $content = $content -replace $pattern, $replacement
            $fileReplacements += $matches.Count
        }
    }
    
    # Pattern 3: $http.put('path') -> $http.put(getBaseUrl() + '/path')
    if ($content -match '\$http\.put\(''[^/]' -and $content -notmatch 'getBaseUrl\(\)') {
        $pattern = '\$http\.put\(''([^'']+)''\)'
        $replacement = '$http.put(getBaseUrl() + ''/$1'')'
        $matches = [regex]::Matches($content, $pattern)
        if ($matches.Count -gt 0) {
            $content = $content -replace $pattern, $replacement
            $fileReplacements += $matches.Count
        }
    }
    
    # Pattern 4: $http.delete('path') -> $http.delete(getBaseUrl() + '/path')
    if ($content -match '\$http\.delete\(''[^/]' -and $content -notmatch 'getBaseUrl\(\)') {
        $pattern = '\$http\.delete\(''([^'']+)''\)'
        $replacement = '$http.delete(getBaseUrl() + ''/$1'')'
        $matches = [regex]::Matches($content, $pattern)
        if ($matches.Count -gt 0) {
            $content = $content -replace $pattern, $replacement
            $fileReplacements += $matches.Count
        }
    }
    
    # Only write if changes were made
    if ($content -ne $originalContent) {
        Set-Content -Path $file.FullName -Value $content -Encoding UTF8 -NoNewline
        $totalFiles++
        $totalReplacements += $fileReplacements
        
        $relativePath = $file.FullName.Replace((Get-Location).Path + "\", "")
        Write-Host "Fixed $fileReplacements AJAX calls in: $relativePath" -ForegroundColor Green
    }
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Angular AJAX Path Fix Complete!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Files modified: $totalFiles" -ForegroundColor Yellow
Write-Host "Total AJAX calls fixed: $totalReplacements" -ForegroundColor Yellow
Write-Host ""
Write-Host "All AJAX calls now use getBaseUrl() for context path." -ForegroundColor Green
Write-Host "Please restart your application from Eclipse." -ForegroundColor Cyan
