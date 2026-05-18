# Fix all dashboard and internal page paths

Write-Host "Fixing dashboard and internal page paths..." -ForegroundColor Cyan

$templatesPath = "src\main\resources\templates"

# Get all HTML files in admin, common, systemAdmin, superAdmin, and fragments folders
$htmlFiles = Get-ChildItem -Path $templatesPath -Filter "*.html" -Recurse | Where-Object {
    $_.FullName -match "\\(admin|common|systemAdmin|superAdmin|fragments)\\"
}

$totalFiles = 0
$totalReplacements = 0

Write-Host "Found $($htmlFiles.Count) internal page files to process" -ForegroundColor Yellow
Write-Host ""

foreach ($file in $htmlFiles) {
    $content = Get-Content -Path $file.FullName -Raw -Encoding UTF8
    $originalContent = $content
    $fileReplacements = 0
    
    # Fix th:src and th:href patterns
    $replacements = @{
        'th:src="@{css/' = 'th:src="@{/css/'
        'th:src="@{js/' = 'th:src="@{/js/'
        'th:src="@{img/' = 'th:src="@{/img/'
        'th:src="@{images/' = 'th:src="@{/images/'
        'th:src="@{assets/' = 'th:src="@{/assets/'
        'th:src="@{angular/' = 'th:src="@{/angular/'
        'th:src="@{fonts/' = 'th:src="@{/fonts/'
        'th:src="@{dhs/' = 'th:src="@{/dhs/'
        'th:src="@{new-assets/' = 'th:src="@{/new-assets/'
        'th:src="@{DataTables-' = 'th:src="@{/DataTables-'
        'th:src="@{Buttons-' = 'th:src="@{/Buttons-'
        'th:src="@{JSZip-' = 'th:src="@{/JSZip-'
        
        'th:href="@{css/' = 'th:href="@{/css/'
        'th:href="@{js/' = 'th:href="@{/js/'
        'th:href="@{img/' = 'th:href="@{/img/'
        'th:href="@{images/' = 'th:href="@{/images/'
        'th:href="@{assets/' = 'th:href="@{/assets/'
        'th:href="@{angular/' = 'th:href="@{/angular/'
        'th:href="@{fonts/' = 'th:href="@{/fonts/'
        'th:href="@{dhs/' = 'th:href="@{/dhs/'
        'th:href="@{new-assets/' = 'th:href="@{/new-assets/'
        'th:href="@{DataTables-' = 'th:href="@{/DataTables-'
        'th:href="@{Buttons-' = 'th:href="@{/Buttons-'
        'th:href="@{JSZip-' = 'th:href="@{/JSZip-'
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
Write-Host "Dashboard Path Fix Complete!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Files modified: $totalFiles" -ForegroundColor Yellow
Write-Host "Total paths fixed: $totalReplacements" -ForegroundColor Yellow
Write-Host ""
Write-Host "IMPORTANT: RESTART your application from Eclipse!" -ForegroundColor Red
Write-Host "Then clear browser cache and login again." -ForegroundColor Yellow
