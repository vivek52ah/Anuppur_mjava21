# Fix all remaining errors in CommonServiceImpl.java

$filePath = "c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java"

$content = Get-Content -Path $filePath -Raw -Encoding UTF8

Write-Host "Original file size: $($content.Length) characters"

# Fix 1: DMSUtil.getUserDetail().orElse(null)
$content = $content -replace 'userRepository\.findByUsername\(DMSUtil\.getUserDetail\(\)\.orElse\(null\)\.getUsername\(\)\)', 'userRepository.findByUsername(DMSUtil.getUserDetail().getUsername())'
Write-Host "Fixed DMSUtil.getUserDetail pattern"

# Fix 2: WorkStatus assignments
$content = $content -replace '(\s+WorkStatus\s+workStatusId\s*=\s*workStatusRepository\s*\.\s*findById\([^)]+\))\s*;', '$1.orElse(null);'
Write-Host "Fixed WorkStatus assignments"

# Fix 3: Work assignments  
$content = $content -replace '(\s+Work\s+wentityWork\s*=\s*workRepository\s*\.\s*findById\([^)]+\))\s*;', '$1.orElse(null);'
Write-Host "Fixed Work assignments"

# Fix 4: locationPointsRepository
$content = $content -replace 'locationPointsRepository\.save\(locationPointsList\)', 'locationPointsRepository.saveAll(locationPointsList)'
Write-Host "Fixed locationPointsRepository"

$content | Set-Content -Path $filePath -Encoding UTF8 -NoNewline

Write-Host "File updated successfully!"
Write-Host "New file size: $($content.Length) characters"
