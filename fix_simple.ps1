# Simple targeted fixes

$filePath = "c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java"
$content = Get-Content -Path $filePath -Raw -Encoding UTF8

# Fix 1: Null-safe workStatusName assignment
$content = $content -replace 'workStatusName = workStatusRepository\.findById\(Long\.valueOf\(workStatusId\)\)\.orElse\(null\)\.getWorkStatusNameE\(\);', 'WorkStatus ws = workStatusRepository.findById(Long.valueOf(workStatusId)).orElse(null); if (ws != null) { workStatusName = ws.getWorkStatusNameE(); }'

# Fix 2: Replace workStatusId String with Long conversion in method calls
$content = $content -replace ', workStatusId, financialYearId,', ', (workStatusId != null ? Long.valueOf(workStatusId) : null), financialYearId,'

# Fix 3: Fix missing repository methods
$content = $content -replace 'findByWorkHeadContainingAndStatusNotIn', 'findByWorkHeadAndStatusNotIn'
$content = $content -replace 'findBySchemeContainingAndStatusNotIn', 'findBySchemeAndStatusNotIn'

# Fix 4: Fix Work Optional
$content = $content -replace '(Work wentityWork = workRepository\.findByIdAndStatusNotIn)', '$1'
$content = $content -replace 'wentityWork = workRepository\.findByIdAndStatusNotIn\(([^;]+);', 'wentityWork = workRepository.findByIdAndStatusNotIn($1.orElse(null);'

# Fix 5: Fix remaining getter.orElse patterns
$content = $content -replace '\.findById\(([a-zA-Z]+)\.getWorkSubStatusId\(\)\.orElse\(null\)\)', '.findById($1.getWorkSubStatusId()).orElse(null)'
$content = $content -replace '\.findById\(([a-zA-Z]+)\.getDivisionId\(\)\.orElse\(null\)\)', '.findById($1.getDivisionId()).orElse(null)'

$content | Set-Content -Path $filePath -Encoding UTF8 -NoNewline
Write-Host "Fixed! Size: $($content.Length)"
