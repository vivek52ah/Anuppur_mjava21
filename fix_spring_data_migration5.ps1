# Fix Spring Data 3.x migration issues - Part 5
# Fix final remaining patterns

$filePath = "c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java"

# Read the file content
$content = Get-Content -Path $filePath -Raw -Encoding UTF8

Write-Host "Original file size: $($content.Length) characters"

# Pattern 1: Fix Division assignment without .orElse(null)
$pattern1 = '(Division\s+division\s*=\s*divisionRepository\.findById\([^)]+\));'
$replacement1 = '$1.orElse(null);'
$content = $content -replace $pattern1, $replacement1
Write-Host "Fixed Division Optional assignment"

# Pattern 2: Fix Head repository with bean.getHeadEcpr2Id().orElse(null)
$pattern2 = 'headRepository\.findById\(bean\.getHeadEcpr2Id\(\)\.orElse\(null\)\)'
$replacement2 = 'headRepository.findById(bean.getHeadEcpr2Id()).orElse(null)'
$content = $content -replace $pattern2, $replacement2
Write-Host "Fixed headRepository.getHeadEcpr2Id pattern"

# Pattern 3: Fix Schemes repository with bean.getSchemeEcpr2Id().orElse(null)
$pattern3 = 'schemeRepository\.findById\(bean\.getSchemeEcpr2Id\(\)\.orElse\(null\)\)'
$replacement3 = 'schemeRepository.findById(bean.getSchemeEcpr2Id()).orElse(null)'
$content = $content -replace $pattern3, $replacement3
Write-Host "Fixed schemeRepository.getSchemeEcpr2Id pattern"

# Pattern 4: Fix WorkStatus assignments that still don't have .orElse(null)
# This catches the ones that were missed before
$pattern4 = '(\s+WorkStatus\s+\w+\s*=\s*workStatusRepository\s*\.\s*findById\([^)]+\))\s*;'
$replacement4 = '$1.orElse(null);'
$content = $content -replace $pattern4, $replacement4
Write-Host "Fixed remaining WorkStatus Optional assignments"

# Pattern 5: Fix Work assignment without .orElse(null)
$pattern5 = '(Work\s+\w+\s*=\s*workRepository\.findById\([^)]+\));'
$replacement5 = '$1.orElse(null);'
$content = $content -replace $pattern5, $replacement5
Write-Host "Fixed Work Optional assignments"

# Pattern 6: Fix the incorrect java.util.List.of() syntax in method calls
# The regex replacement created invalid syntax like: findByWorkHeadContainingAndStatusNotIn(String, java.util.List.of(DMSConstants.STATUS_DELETED))
# These methods don't exist - we need to use the correct method signature
$pattern6 = '\.findByWorkHeadContainingAndStatusNotIn\(([^,]+),\s*java\.util\.List\.of\(DMSConstants\.STATUS_DELETED\)\)'
$replacement6 = '.findByWorkHeadContainingAndStatusNotIn($1, DMSConstants.STATUS_DELETED)'
$content = $content -replace $pattern6, $replacement6
Write-Host "Fixed findByWorkHeadContainingAndStatusNotIn back to String parameter"

# Pattern 7: Fix findBySchemeContainingAndStatusNotIn
$pattern7 = '\.findBySchemeContainingAndStatusNotIn\(([^,]+),\s*java\.util\.List\.of\(DMSConstants\.STATUS_DELETED\)\)'
$replacement7 = '.findBySchemeContainingAndStatusNotIn($1, DMSConstants.STATUS_DELETED)'
$content = $content -replace $pattern7, $replacement7
Write-Host "Fixed findBySchemeContainingAndStatusNotIn back to String parameter"

# Pattern 8: Fix findBySorYearContainingAndStatusNotIn that was incorrectly changed
$pattern8 = '\.findBySorYearContainingAndStatusNotIn\(([^,]+),\s*java\.util\.List\.of\(DMSConstants\.STATUS_DELETED\)\)'
$replacement8 = '.findBySorYearContainingAndStatusNotIn($1, DMSConstants.STATUS_DELETED)'
$content = $content -replace $pattern8, $replacement8
Write-Host "Fixed findBySorYearContainingAndStatusNotIn back to String parameter"

# Pattern 9: Fix method calls that need List<Long> for workStatusIn parameter
# These are the ones with multiple Long parameters at the end
$pattern9 = '(findBy\w+StatusNotInAndWorkStatusIn)\(pageable,\s*java\.util\.List\.of\(DMSConstants\.STATUS_DELETED\),\s*java\.util\.List\.of\(([^)]+)\)\)'
$replacement9 = '$1(pageable, DMSConstants.STATUS_DELETED, $2)'
$content = $content -replace $pattern9, $replacement9
Write-Host "Fixed StatusNotInAndWorkStatusIn methods - reverting incorrect List wrapping"

# Pattern 10: Fix findByDivisionCode and similar methods that expect String for status parameter
# These methods have signature like: findByDivisionCode(Pageable, String, Long, Long, Long, Integer, Long, Long, Long, Long)
# The 7th parameter should be Long (workStatusId), not String
# We need to identify these specific calls and fix them individually

# Write the fixed content back
$content | Set-Content -Path $filePath -Encoding UTF8 -NoNewline

Write-Host "File updated successfully!"
Write-Host "New file size: $($content.Length) characters"
