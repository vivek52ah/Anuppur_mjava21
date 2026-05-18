# Comprehensive Spring Data 3.x migration fix
# This script fixes all findOne() -> findById().orElse(null) patterns in one pass

$filePath = "c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java"

# Read the file content
$content = Get-Content -Path $filePath -Raw -Encoding UTF8

Write-Host "Original file size: $($content.Length) characters"

# Replace all .findOne(id) with .findById(id).orElse(null)
$pattern = '\.findOne\(([^)]+)\)'
$replacement = '.findById($1).orElse(null)'
$content = $content -replace $pattern, $replacement
Write-Host "Replaced all .findOne() with .findById().orElse(null)"

# Write the fixed content back
$content | Set-Content -Path $filePath -Encoding UTF8 -NoNewline

Write-Host "File updated successfully!"
Write-Host "New file size: $($content.Length) characters"
Write-Host ""
Write-Host "Running diagnostics to check remaining errors..."
