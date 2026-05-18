# Fix Spring Data 3.x migration issues - Part 3
# Fix patterns where .orElse(null) is incorrectly on getter methods - more specific

$filePath = "c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java"

# Read the file content
$content = Get-Content -Path $filePath -Raw -Encoding UTF8

Write-Host "Original file size: $($content.Length) characters"

# Multiple specific patterns for different repositories

# Pattern 1: workSubDelayResonRepository.findById(something.getWorkSubDelayReasonId().orElse(null))
$pattern1 = 'workSubDelayResonRepository\.findById\(([^.]+)\.getWorkSubDelayReasonId\(\)\.orElse\(null\)\)'
$replacement1 = 'workSubDelayResonRepository.findById($1.getWorkSubDelayReasonId()).orElse(null)'
$content = $content -replace $pattern1, $replacement1
Write-Host "Fixed workSubDelayResonRepository patterns"

# Pattern 2: workSubTypeRepository.findById(something.getWorkSubtypeId().orElse(null))
$pattern2 = 'workSubTypeRepository\.findById\(([^.]+)\.getWorkSubtypeId\(\)\.orElse\(null\)\)'
$replacement2 = 'workSubTypeRepository.findById($1.getWorkSubtypeId()).orElse(null)'
$content = $content -replace $pattern2, $replacement2
Write-Host "Fixed workSubTypeRepository patterns"

# Pattern 3: financialYearRepository.findById(something.getFinancialYear().orElse(null))
$pattern3 = 'financialYearRepository\.findById\(([^.]+)\.getFinancialYear\(\)\.orElse\(null\)\)'
$replacement3 = 'financialYearRepository.findById($1.getFinancialYear()).orElse(null)'
$content = $content -replace $pattern3, $replacement3
Write-Host "Fixed financialYearRepository patterns"

# Pattern 4: schemeRepository.findById(something.getScheme*().orElse(null))
$pattern4 = 'schemeRepository\.findById\(([^.]+)\.get(Scheme[A-Za-z]*)\(\)\.orElse\(null\)\)'
$replacement4 = 'schemeRepository.findById($1.get$2()).orElse(null)'
$content = $content -replace $pattern4, $replacement4
Write-Host "Fixed schemeRepository patterns"

# Pattern 5: workTypeRepository.findById(something.getWorkType().orElse(null))
$pattern5 = 'workTypeRepository\.findById\(([^.]+)\.getWorkType\(\)\.orElse\(null\)\)'
$replacement5 = 'workTypeRepository.findById($1.getWorkType()).orElse(null)'
$content = $content -replace $pattern5, $replacement5
Write-Host "Fixed workTypeRepository patterns"

# Pattern 6: workCategoryRepository.findById(something.getWorkCategoryId().orElse(null))
$pattern6 = 'workCategoryRepository\.findById\(([^.]+)\.getWorkCategoryId\(\)\.orElse\(null\)\)'
$replacement6 = 'workCategoryRepository.findById($1.getWorkCategoryId()).orElse(null)'
$content = $content -replace $pattern6, $replacement6
Write-Host "Fixed workCategoryRepository patterns"

# Pattern 7: subCategoryRepository.findById(something.getCategorySubtypeId().orElse(null))
$pattern7 = 'subCategoryRepository\.findById\(([^.]+)\.getCategorySubtypeId\(\)\.orElse\(null\)\)'
$replacement7 = 'subCategoryRepository.findById($1.getCategorySubtypeId()).orElse(null)'
$content = $content -replace $pattern7, $replacement7
Write-Host "Fixed subCategoryRepository patterns"

# Pattern 8: workHeadRepository.findById(something.getWorkHead*().orElse(null))
$pattern8 = 'workHeadRepository\.findById\(([^.]+)\.get(WorkHead|Head[A-Za-z]*)\(\)\.orElse\(null\)\)'
$replacement8 = 'workHeadRepository.findById($1.get$2()).orElse(null)'
$content = $content -replace $pattern8, $replacement8
Write-Host "Fixed workHeadRepository patterns"

# Pattern 9: divisionRepository.findById(something.getDivisionCode().orElse(null))
$pattern9 = 'divisionRepository\.findById\(([^.]+)\.getDivisionCode\(\)\.orElse\(null\)\)'
$replacement9 = 'divisionRepository.findById($1.getDivisionCode()).orElse(null)'
$content = $content -replace $pattern9, $replacement9
Write-Host "Fixed divisionRepository patterns"

# Pattern 10: workPriorityRepository.findById(something.getWorkPriorityId().orElse(null))
$pattern10 = 'workPriorityRepository\.findById\(([^.]+)\.getWorkPriorityId\(\)\.orElse\(null\)\)'
$replacement10 = 'workPriorityRepository.findById($1.getWorkPriorityId()).orElse(null)'
$content = $content -replace $pattern10, $replacement10
Write-Host "Fixed workPriorityRepository patterns"

# Pattern 11: financialHeadRepository.findById(something.getFinancialHeadId().orElse(null))
$pattern11 = 'financialHeadRepository\.findById\(([^.]+)\.getFinancialHeadId\(\)\.orElse\(null\)\)'
$replacement11 = 'financialHeadRepository.findById($1.getFinancialHeadId()).orElse(null)'
$content = $content -replace $pattern11, $replacement11
Write-Host "Fixed financialHeadRepository patterns"

# Pattern 12: vidhanSabhaRepositorys.findById(something.getVidhanSabhaId().orElse(null))
$pattern12 = 'vidhanSabhaRepositorys\.findById\(([^.]+)\.getVidhanSabhaId\(\)\.orElse\(null\)\)'
$replacement12 = 'vidhanSabhaRepositorys.findById($1.getVidhanSabhaId()).orElse(null)'
$content = $content -replace $pattern12, $replacement12
Write-Host "Fixed vidhanSabhaRepositorys patterns"

# Pattern 13: implAgencyTypeRepository.findById(something.getImplAgencyType().orElse(null))
$pattern13 = 'implAgencyTypeRepository\.findById\(([^.]+)\.getImplAgencyType\(\)\.orElse\(null\)\)'
$replacement13 = 'implAgencyTypeRepository.findById($1.getImplAgencyType()).orElse(null)'
$content = $content -replace $pattern13, $replacement13
Write-Host "Fixed implAgencyTypeRepository patterns"

# Pattern 14: workRepository.findById(something.getId().orElse(null))
$pattern14 = 'workRepository\.findById\(([^.]+)\.getId\(\)\.orElse\(null\)\)'
$replacement14 = 'workRepository.findById($1.getId()).orElse(null)'
$content = $content -replace $pattern14, $replacement14
Write-Host "Fixed workRepository.getId patterns"

# Pattern 15: workRepository.findById(something.getWorkId().orElse(null))
$pattern15 = 'workRepository\.findById\(([^.]+)\.getWorkId\(\)\.orElse\(null\)\)'
$replacement15 = 'workRepository.findById($1.getWorkId()).orElse(null)'
$content = $content -replace $pattern15, $replacement15
Write-Host "Fixed workRepository.getWorkId patterns"

# Pattern 16: financialAgencyRepository.findById(something.getFinancialAgencyId().orElse(null))
$pattern16 = 'financialAgencyRepository\.findById\(([^.]+)\.getFinancialAgencyId\(\)\.orElse\(null\)\)'
$replacement16 = 'financialAgencyRepository.findById($1.getFinancialAgencyId()).orElse(null)'
$content = $content -replace $pattern16, $replacement16
Write-Host "Fixed financialAgencyRepository patterns"

# Write the fixed content back
$content | Set-Content -Path $filePath -Encoding UTF8 -NoNewline

Write-Host "File updated successfully!"
Write-Host "New file size: $($content.Length) characters"
