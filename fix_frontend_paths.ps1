# PowerShell script to fix all Thymeleaf path issues in HTML files
# Adds leading slash to all th:src and th:href paths that are missing it

$templatesPath = "src\main\resources\templates"

# Get all HTML files recursively
$htmlFiles = Get-ChildItem -Path $templatesPath -Filter "*.html" -Recurse

$totalFiles = 0
$totalReplacements = 0

Write-Host "Starting frontend path fixes..." -ForegroundColor Cyan
Write-Host "Found $($htmlFiles.Count) HTML files to process" -ForegroundColor Yellow
Write-Host ""

foreach ($file in $htmlFiles) {
    $content = Get-Content -Path $file.FullName -Raw -Encoding UTF8
    $originalContent = $content
    $fileReplacements = 0
    
    # Fix th:src and th:href patterns - simple string replacement
    $replacements = @{
        'th:src="@{new-assets/' = 'th:src="@{/new-assets/'
        'th:src="@{angular/' = 'th:src="@{/angular/'
        'th:src="@{js/' = 'th:src="@{/js/'
        'th:src="@{css/' = 'th:src="@{/css/'
        'th:src="@{img/' = 'th:src="@{/img/'
        'th:src="@{assets/' = 'th:src="@{/assets/'
        'th:href="@{new-assets/' = 'th:href="@{/new-assets/'
        'th:href="@{angular/' = 'th:href="@{/angular/'
        'th:href="@{js/' = 'th:href="@{/js/'
        'th:href="@{css/' = 'th:href="@{/css/'
        'th:href="@{img/' = 'th:href="@{/img/'
        'th:href="@{assets/' = 'th:href="@{/assets/'
    }
    
    foreach ($find in $replacements.Keys) {
        $replace = $replacements[$find]
        $count = ([regex]::Matches($content, [regex]::Escape($find))).Count
        if ($count -gt 0) {
            $content = $content.Replace($find, $replace)
            $fileReplacements += $count
        }
    }
    
    # Only write if changes were made
    if ($content -ne $originalContent) {
        Set-Content -Path $file.FullName -Value $content -Encoding UTF8 -NoNewline
        $totalFiles++
        $totalReplacements += $fileReplacements
        
        $relativePath = $file.FullName.Replace((Get-Location).Path + "\", "")
        Write-Host "Fixed $fileReplacements paths in: $relativePath" -ForegroundColor Green
    }
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Frontend Path Fix Complete!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Files modified: $totalFiles" -ForegroundColor Yellow
Write-Host "Total paths fixed: $totalReplacements" -ForegroundColor Yellow
Write-Host ""
Write-Host "All Thymeleaf paths now have leading slashes." -ForegroundColor Green
Write-Host "Please restart your application from Eclipse." -ForegroundColor Cyan
