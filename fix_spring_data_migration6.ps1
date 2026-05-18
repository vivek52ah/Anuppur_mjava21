# Fix Spring Data 3.x migration issues - Part 6
# Fix final remaining errors

$filePath = "c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java"

# Read the file content
$content = Get-Content -Path $filePath -Raw -Encoding UTF8

Write-Host "Original file size: $($content.Length) characters"

# Pattern 1: Fix Division Optional assignment that was missed
$pattern1 = '(\s+Division\s+division\s*=\s*divisionRepository\.findById\([^;]+)\);'
$replacement1 = '$1).orElse(null);'
$content = $content -replace $pattern1, $replacement1
Write-Host "Fixed Division Optional assignment"

# Pattern 2: Fix Work Optional assignment
$pattern2 = '(\s+Work\s+\w+\s*=\s*workRepository\.findById\([^;]+)\);'
$replacement2 = '$1).orElse(null);'
$content = $content -replace $pattern2, $replacement2
Write-Host "Fixed Work Optional assignment"

# Pattern 3: Fix WorkStatus Optional assignments that are still missing .orElse(null)
$pattern3 = '(\s+WorkStatus\s+\w+\s*=\s*workStatusRepository\.findById\([^;]+)\);'
$replacement3 = '$1).orElse(null);'
$content = $content -replace $pattern3, $replacement3
Write-Host "Fixed WorkStatus Optional assignments"

# Pattern 4: Fix the workStatusId String to Long conversion issue
# Replace: workStatusRepository.findById(Long.valueOf(workStatusId)).orElse(null).getWorkStatusNameE()
# With safe null check
$pattern4 = 'workStatusName = workStatusRepository\.findById\(Long\.valueOf\(workStatusId\)\)\.orElse\(null\)\.getWorkStatusNameE\(\);'
$replacement4 = @'
WorkStatus ws = workStatusRepository.findById(Long.valueOf(workStatusId)).orElse(null);
				if (ws != null) {
					workStatusName = ws.getWorkStatusNameE();
				}
'@
$content = $content -replace $pattern4, $replacement4
Write-Host "Fixed workStatusName assignment with null check"

# Pattern 5: Convert workStatusId String parameter to Long for repository calls
# This is tricky - we need to add a conversion variable before the repository call
# For now, let's just replace the direct usage in method calls

# Pattern 6: Fix remaining Long.orElse(null) patterns on getters
$pattern6 = '([a-zA-Z]+Repository)\.findById\(([a-zA-Z]+)\.get([A-Z][a-zA-Z]+)\(\)\.orElse\(null\)\)'
$replacement6 = '$1.findById($2.get$3()).orElse(null)'
$content = $content -replace $pattern6, $replacement6
Write-Host "Fixed remaining getter.orElse(null) patterns"

# Write the fixed content back
$content | Set-Content -Path $filePath -Encoding UTF8 -NoNewline

Write-Host "File updated successfully!"
Write-Host "New file size: $($content.Length) characters"
