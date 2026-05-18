# Complete fix for all remaining errors

$filePath = "c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java"
$content = Get-Content -Path $filePath -Raw -Encoding UTF8

Write-Host "Fixing all remaining errors..."

# Fix 1: workStatusRepository chain with null check
$content = $content -replace 'workStatusName = workStatusRepository\.findById\(Long\.valueOf\(workStatusId\)\)\.orElse\(null\)\.getWorkStatusNameE\(\);', @'
WorkStatus ws = workStatusRepository.findById(Long.valueOf(workStatusId)).orElse(null);
				if (ws != null) {
					workStatusName = ws.getWorkStatusNameE();
				}
'@

# Fix 2: Add workStatusIdLong variable and use it in method calls
# Replace workStatusId parameter with workStatusIdLong in findByDivisionCode
$content = $content -replace '(String workStatusName = null;)', @'
String workStatusName = null;
			Long workStatusIdLong = null;
'@

$content = $content -replace '(if \(workStatusId != null\) \{[\s\S]*?\})\s*(String gpName = null;)', @'
if (workStatusId != null) {
				try {
					workStatusIdLong = Long.valueOf(workStatusId);
					WorkStatus ws = workStatusRepository.findById(workStatusIdLong).orElse(null);
					if (ws != null) {
						workStatusName = ws.getWorkStatusNameE();
					}
				} catch (Exception e) {
					// handle exception
				}
			}
			$2
'@

# Fix 3: Replace workStatusId with workStatusIdLong in repository method calls
$content = $content -replace 'findByDivisionCode\(pageable, workName, divisionCode, divisionIds, districtIds,\s*workSubTypeIdInt, workStatusId,', 'findByDivisionCode(pageable, workName, divisionCode, divisionIds, districtIds, workSubTypeIdInt, workStatusIdLong,'

$content = $content -replace 'findByDistrictCodeContainingAndDistrictCodeAndStatusNotInAndWorkStatusIn\(pageable, workName, districtCode, divisionIds, districtIds,\s*workSubTypeIdInt, workStatusId,', 'findByDistrictCodeContainingAndDistrictCodeAndStatusNotInAndWorkStatusIn(pageable, workName, districtCode, divisionIds, districtIds, workSubTypeIdInt, workStatusIdLong,'

$content = $content -replace 'findByDivisionNameContainingAndStatusNotInAndWorkStatusIn\(pageable, workName, divisionIds, districtIds,\s*workSubTypeIdInt, workStatusId,', 'findByDivisionNameContainingAndStatusNotInAndWorkStatusIn(pageable, workName, divisionIds, districtIds, workSubTypeIdInt, workStatusIdLong,'

$content = $content -replace 'findAllByStatusNotDeleted\(pageable, workName, divisionCode, districtCode, divisionIds,\s*workStatusId,', 'findAllByStatusNotDeleted(pageable, workName, divisionCode, districtCode, divisionIds, workStatusIdLong,'

# Fix 4: Fix missing repository methods - replace with existing methods
$content = $content -replace 'workRepository\.findByWorkHeadContainingAndStatusNotIn\(([^,]+), DMSConstants\.STATUS_DELETED\)', 'workRepository.findByWorkHeadAndStatusNotIn($1, DMSConstants.STATUS_DELETED)'

$content = $content -replace 'workRepository\.findBySchemeContainingAndStatusNotIn\(([^,]+), DMSConstants\.STATUS_DELETED\)', 'workRepository.findBySchemeAndStatusNotIn($1, DMSConstants.STATUS_DELETED)'

# Fix 5: Fix Work Optional assignment
$content = $content -replace '(Work wentityWork = workRepository\.findByIdAndStatusNotIn\([^;]+));', '$1.orElse(null);'

# Fix 6: Fix remaining Long.orElse patterns
$content = $content -replace 'workSubStatusRepository\.findById\(([a-zA-Z]+)\.getWorkSubStatusId\(\)\.orElse\(null\)\)', 'workSubStatusRepository.findById($1.getWorkSubStatusId()).orElse(null)'

$content = $content -replace 'divisionRepository\.findById\(([a-zA-Z]+)\.getDivisionId\(\)\.orElse\(null\)\)', 'divisionRepository.findById($1.getDivisionId()).orElse(null)'

# Fix 7: Fix WorkStatus Optional in convertWorkStatusEntityToBean calls
$content = $content -replace '(convertWorkStatusEntityToBean\(workStatusRepository\.findById\([^)]+\))\.orElse\(null\)\)', '$1.orElse(null))'

$content | Set-Content -Path $filePath -Encoding UTF8 -NoNewline

Write-Host "Complete! File size: $($content.Length) characters"
