# PowerShell script to fix Java 21 compatibility issues in controller files
# This script fixes deprecated Sort and PageRequest constructors

$controllerPath = "c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\controller"

# Get all Java files in controller directory
$javaFiles = Get-ChildItem -Path $controllerPath -Filter "*.java" -File

foreach ($file in $javaFiles) {
    Write-Host "Processing: $($file.Name)"
    
    $content = Get-Content -Path $file.FullName -Raw
    $originalContent = $content
    
    # Fix Sort constructor patterns
    # Pattern 1: new Sort(new Sort.Order(Direction.ASC, field)) -> Sort.by(Direction.ASC, field)
    $content = $content -replace 'new Sort\(new Sort\.Order\(Direction\.ASC,\s*([^)]+)\)\)', 'Sort.by(Direction.ASC, $1)'
    
    # Pattern 2: new Sort(new Sort.Order(Direction.DESC, field)) -> Sort.by(Direction.DESC, field)
    $content = $content -replace 'new Sort\(new Sort\.Order\(Direction\.DESC,\s*([^)]+)\)\)', 'Sort.by(Direction.DESC, $1)'
    
    # Fix PageRequest constructor
    # Pattern: new PageRequest(page, size, sort) -> PageRequest.of(page, size, sort)
    $content = $content -replace 'new PageRequest\(', 'PageRequest.of('
    
    # Only write if content changed
    if ($content -ne $originalContent) {
        Set-Content -Path $file.FullName -Value $content -NoNewline
        Write-Host "  Fixed Sort and PageRequest constructors" -ForegroundColor Green
    } else {
        Write-Host "  No changes needed" -ForegroundColor Gray
    }
}

Write-Host ""
Write-Host "All controller files processed!" -ForegroundColor Cyan
Write-Host "Please review the changes and run diagnostics to verify." -ForegroundColor Yellow
