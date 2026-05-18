# Fix final 12 errors

$filePath = "c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java"
$content = Get-Content -Path $filePath -Raw -Encoding UTF8

Write-Host "Fixing final 12 errors..."

# Fix 1: DMSUtil.getUserDetail().orElse(null) - remove .orElse(null)
$content = $content -replace 'DMSUtil\.getUserDetail\(\)\.orElse\(null\)', 'DMSUtil.getUserDetail()'

# Fix 2: WorkStatus Optional assignments - add .orElse(null)
$content = $content -replace '(\s+WorkStatus\s+workStatusId\s*=\s*workStatusRepository\s*\.\s*findById\([^)]+\))\s*;', '$1.orElse(null);'

# Fix 3: Fix findByWorkHeadAndStatusNotIn - it expects Long and List, but receives String and String
# Change back to a method that accepts String
$content = $content -replace 'findByWorkHeadAndStatusNotIn\(([^,]+), DMSConstants\.STATUS_DELETED\)', 'findByWorkHead($1)'

# Fix 4: Fix findBySchemeAndStatusNotIn - same issue
$content = $content -replace 'findBySchemeAndStatusNotIn\(([^,]+), DMSConstants\.STATUS_DELETED\)', 'findByScheme($1)'

# Fix 5: Fix remaining Long.orElse patterns
$content = $content -replace '\.findById\(([a-zA-Z]+)\.getWorkSubStatusId\(\)\.orElse\(null\)\)', '.findById($1.getWorkSubStatusId()).orElse(null)'
$content = $content -replace '\.findById\(([a-zA-Z]+)\.getDivisionId\(\)\.orElse\(null\)\)', '.findById($1.getDivisionId()).orElse(null)'

# Fix 6: locationPointsRepository.save -> saveAll
$content = $content -replace 'locationPointsRepository\.save\(locationPointsList\)', 'locationPointsRepository.saveAll(locationPointsList)'

# Fix 7: Fix remaining workStatusId String parameter in method calls
$content = $content -replace 'findByDistrictCodeContainingAndDistrictCodeAndStatusNotInAndWorkStatusIn\(pageable, workName, districtCode, divisionIds, districtIds,\s*workSubTypeIdInt, workStatusId,', 'findByDistrictCodeContainingAndDistrictCodeAndStatusNotInAndWorkStatusIn(pageable, workName, districtCode, divisionIds, districtIds, workSubTypeIdInt, (workStatusId != null ? Long.valueOf(workStatusId) : null),'

$content = $content -replace 'findAllByStatusNotDeleted\(pageable, workName, divisionCode, districtCode, divisionIds, workStatusId,', 'findAllByStatusNotDeleted(pageable, workName, divisionCode, districtCode, divisionIds, (workStatusId != null ? Long.valueOf(workStatusId) : null),'

$content | Set-Content -Path $filePath -Encoding UTF8 -NoNewline
Write-Host "Done! Size: $($content.Length)"
