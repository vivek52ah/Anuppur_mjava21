# Fix Spring Data 3.x migration issues - Part 2
# Fix patterns where .orElse(null) is incorrectly on getter methods

$filePath = "c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java"

# Read the file content
$content = Get-Content -Path $filePath -Raw -Encoding UTF8

Write-Host "Original file size: $($content.Length) characters"

# Pattern: Fix .findById(something.getSomething().orElse(null)) -> .findById(something.getSomething()).orElse(null)
# This pattern matches any getter method followed by .orElse(null) inside findById
$pattern = '\.findById\(([^.]+\.[a-zA-Z]+\([^)]*\))\.orElse\(null\)\)'
$replacement = '.findById($1).orElse(null)'

$count = 0
while ($content -match $pattern) {
    $content = $content -replace $pattern, $replacement
    $count++
    if ($count -gt 200) {
        Write-Host "Safety limit reached - stopping to prevent infinite loop"
        break
    }
}

Write-Host "Fixed $count occurrences of getter.orElse(null) pattern"

# Write the fixed content back
$content | Set-Content -Path $filePath -Encoding UTF8 -NoNewline

Write-Host "File updated successfully!"
Write-Host "New file size: $($content.Length) characters"
