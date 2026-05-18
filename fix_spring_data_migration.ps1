# Fix Spring Data 3.x migration issues in CommonServiceImpl.java

$filePath = "c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java"

# Read the file content
$content = Get-Content -Path $filePath -Raw -Encoding UTF8

Write-Host "Original file size: $($content.Length) characters"

# Pattern 1: Fix .findById(Long.valueOf(x).orElse(null)) -> .findById(Long.valueOf(x)).orElse(null)
$pattern1 = '\.findById\(Long\.valueOf\(([^)]+)\)\.orElse\(null\)\)'
$replacement1 = '.findById(Long.valueOf($1)).orElse(null)'
$content = $content -replace $pattern1, $replacement1
Write-Host "Fixed Pattern 1: Long.valueOf with incorrect orElse placement"

# Pattern 2: Fix .findById(Long.parseLong(x).orElse(null)) -> .findById(Long.parseLong(x)).orElse(null)
$pattern2 = '\.findById\(Long\.parseLong\(([^)]+)\)\.orElse\(null\)\)'
$replacement2 = '.findById(Long.parseLong($1)).orElse(null)'
$content = $content -replace $pattern2, $replacement2
Write-Host "Fixed Pattern 2: Long.parseLong with incorrect orElse placement"

# Pattern 3: Fix .findById(id).orElse(null) where id is primitive long parameter
# This needs to convert primitive long to Long object first
$pattern3 = 'workSubDelayResonRepository\.findById\(id\)\.orElse\(null\)'
$replacement3 = 'workSubDelayResonRepository.findById(Long.valueOf(id)).orElse(null)'
$content = $content -replace $pattern3, $replacement3
Write-Host "Fixed Pattern 3: primitive long id parameter"

# Pattern 4: Fix .findById(bean.getWorkSubDelayReasonId().orElse(null)) -> .findById(bean.getWorkSubDelayReasonId()).orElse(null)
$pattern4 = '\.findById\(bean\.getWorkSubDelayReasonId\(\)\.orElse\(null\)\)'
$replacement4 = '.findById(bean.getWorkSubDelayReasonId()).orElse(null)'
$content = $content -replace $pattern4, $replacement4
Write-Host "Fixed Pattern 4: bean getter with incorrect orElse placement"

# Pattern 5: Fix .findById(progress.getWork().orElse(null).getId()) -> progress.getWork() null check
$pattern5 = 'workRepository\.findById\(progress\.getWork\(\)\.orElse\(null\)\.getId\(\)\)'
$replacement5 = 'workRepository.findById(progress.getWork().getId()).orElse(null)'
$content = $content -replace $pattern5, $replacement5
Write-Host "Fixed Pattern 5: nested orElse with getId"

# Pattern 6: Fix .findById(work.getWorkStatus().orElse(null)) -> .findById(work.getWorkStatus()).orElse(null)
$pattern6 = 'workStatusRepository\.findById\(work\.getWorkStatus\(\)\.orElse\(null\)\)'
$replacement6 = 'workStatusRepository.findById(work.getWorkStatus()).orElse(null)'
$content = $content -replace $pattern6, $replacement6
Write-Host "Fixed Pattern 6: work.getWorkStatus with incorrect orElse placement"

# Pattern 7: Fix .findById(entity.getWorkStatus().orElse(null)) -> .findById(entity.getWorkStatus()).orElse(null)
$pattern7 = 'workStatusRepository\.findById\(entity\.getWorkStatus\(\)\.orElse\(null\)\)'
$replacement7 = 'workStatusRepository.findById(entity.getWorkStatus()).orElse(null)'
$content = $content -replace $pattern7, $replacement7
Write-Host "Fixed Pattern 7: entity.getWorkStatus with incorrect orElse placement"

# Pattern 8: Fix various bean getters with incorrect orElse placement
$pattern8 = 'workStatusRepository\.findById\(([a-zA-Z]+Bean)\.getWorkStatusId\(\)\.orElse\(null\)\)'
$replacement8 = 'workStatusRepository.findById($1.getWorkStatusId()).orElse(null)'
$content = $content -replace $pattern8, $replacement8
Write-Host "Fixed Pattern 8: bean.getWorkStatusId with incorrect orElse placement"

# Write the fixed content back
$content | Set-Content -Path $filePath -Encoding UTF8 -NoNewline

Write-Host "File updated successfully!"
Write-Host "New file size: $($content.Length) characters"
