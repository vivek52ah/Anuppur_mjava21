# Fix Spring Data 3.x migration issues - Part 4
# Fix remaining specific patterns

$filePath = "c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java"

# Read the file content
$content = Get-Content -Path $filePath -Raw -Encoding UTF8

Write-Host "Original file size: $($content.Length) characters"

# Pattern 1: Fix remaining getSchemeEcrp2().orElse(null)
$pattern1 = 'schemeRepository\.findById\(work\.getSchemeEcrp2\(\)\.orElse\(null\)\)'
$replacement1 = 'schemeRepository.findById(work.getSchemeEcrp2()).orElse(null)'
$content = $content -replace $pattern1, $replacement1
Write-Host "Fixed getSchemeEcrp2 pattern"

# Pattern 2: Fix DMSUtil.getUserDetail().orElse(null).getUsername()
$pattern2 = 'DMSUtil\.getUserDetail\(\)\.orElse\(null\)\.getUsername\(\)'
$replacement2 = 'DMSUtil.getUserDetail().getUsername()'
$content = $content -replace $pattern2, $replacement2
Write-Host "Fixed getUserDetail pattern"

# Pattern 3: Fix WorkStatus assignments without .orElse(null)
# workStatusRepository.findById(...) returns Optional<WorkStatus>, need to add .orElse(null)
$pattern3 = '(WorkStatus\s+\w+\s*=\s*workStatusRepository\.findById\([^)]+\));'
$replacement3 = '$1.orElse(null);'
$content = $content -replace $pattern3, $replacement3
Write-Host "Fixed WorkStatus Optional assignments"

# Pattern 4: Fix multiline WorkStatus assignments
$pattern4 = '(WorkStatus\s+\w+\s*=\s*workStatusRepository\s*\.\s*findById\([^)]+\))\s*;'
$replacement4 = '$1.orElse(null);'
$content = $content -replace $pattern4, $replacement4
Write-Host "Fixed multiline WorkStatus Optional assignments"

# Pattern 5: Fix findByIdAndStatusNotIn with String instead of List<String>
$pattern5 = '\.findByIdAndStatusNotIn\(([^,]+),\s*DMSConstants\.STATUS_DELETED\)'
$replacement5 = '.findByIdAndStatusNotIn($1, java.util.List.of(DMSConstants.STATUS_DELETED))'
$content = $content -replace $pattern5, $replacement5
Write-Host "Fixed findByIdAndStatusNotIn parameter type"

# Pattern 6: Fix findByStatusNotInAndWorkStatusIn with String instead of List
$pattern6 = '\.findByStatusNotInAndWorkStatusIn\(pageable,\s*DMSConstants\.STATUS_DELETED,\s*([^)]+)\)'
$replacement6 = '.findByStatusNotInAndWorkStatusIn(pageable, java.util.List.of(DMSConstants.STATUS_DELETED), java.util.List.of($1))'
$content = $content -replace $pattern6, $replacement6
Write-Host "Fixed findByStatusNotInAndWorkStatusIn parameter types"

# Pattern 7: Fix other repository methods expecting List<String> but receiving String
# These need to be wrapped in java.util.List.of()
$pattern7 = '(findBy\w+StatusNotIn\w*)\(([^,]+),\s*DMSConstants\.STATUS_DELETED\)'
$replacement7 = '$1($2, java.util.List.of(DMSConstants.STATUS_DELETED))'
$content = $content -replace $pattern7, $replacement7
Write-Host "Fixed other StatusNotIn parameter types"

# Write the fixed content back
$content | Set-Content -Path $filePath -Encoding UTF8 -NoNewline

Write-Host "File updated successfully!"
Write-Host "New file size: $($content.Length) characters"
