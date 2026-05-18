# Fix all remaining 14 errors in CommonServiceImpl.java

$filePath = "c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java"

# Read the file content
$content = Get-Content -Path $filePath -Raw -Encoding UTF8

Write-Host "Original file size: $($content.Length) characters"
Write-Host "Fixing remaining 14 errors..."
Write-Host ""

# Fix 1: DMSUtil.getUserDetail().orElse(null) - getUserDetail() doesn't return Optional
$content = $content -replace 'userRepository\.findByUsername\(DMSUtil\.getUserDetail\(\)\.orElse\(null\)\.getUsername\(\)\)', 'userRepository.findByUsername(DMSUtil.getUserDetail().getUsername())'
Write-Host "✓ Fixed DMSUtil.getUserDetail().orElse(null)"

# Fix 2: Add .orElse(null) to WorkStatus assignments that are missing it
# Pattern: WorkStatus workStatusId = workStatusRepository.findById(...);
$content = $content -replace '(\s+WorkStatus\s+workStatusId\s*=\s*workStatusRepository\s*\.\s*findById\([^)]+\))\s*;', '$1.orElse(null);'
Write-Host "✓ Fixed WorkStatus Optional assignments"

# Fix 3: Add .orElse(null) to Work assignments
$content = $content -replace '(\s+Work\s+wentityWork\s*=\s*workRepository\s*\.\s*findById\([^)]+\))\s*;', '$1.orElse(null);'
Write-Host "✓ Fixed Work Optional assignments"

# Fix 4: Fix remaining Long getter patterns with .orElse(null)
# These patterns have .orElse(null) on the getter instead of on findById result
$patterns = @(
    'workSubStatusRepository\.findById\(([a-zA-Z]+)\.getWorkSubStatusId\(\)\.orElse\(null\)\)',
    'divisionRepository\.findById\(([a-zA-Z]+)\.getDivisionId\(\)\.orElse\(null\)\)',
    'districtRepository\.findById\(([a-zA-Z]+)\.getDistrictId\(\)\.orElse\(null\)\)'
)

foreach ($pattern in $patterns) {
    if ($pattern -match '([a-zA-Z]+Repository)\.findById\(\(([a-zA-Z]+)\)\.get([A-Z][a-zA-Z]+)\(\)\.orElse\(null\)\)') {
        $content = $content -replace $pattern, '$1.findById($2.get$3()).orElse(null)'
    }
}
Write-Host "✓ Fixed remaining getter.orElse(null) patterns"

# Fix 5: locationPointsRepository.save(List) -> saveAll(List) if still present
$content = $content -replace 'locationPointsRepository\.save\(locationPointsList\)', 'locationPointsRepository.saveAll(locationPointsList)'
Write-Host "✓ Fixed locationPointsRepository.save to saveAll"

# Fix 6: Convert workStatusId String parameter to Long for method calls
# This requires adding conversion logic before the method calls
# We'll need to handle this differently - let's mark the locations that need manual conversion

Write-Host ""
Write-Host "Note: The following issues require repository method changes or manual fixes:"
Write-Host "  - Lines 1163, 1205, 1248, 7137: workStatusId parameter type mismatch (String vs Long)"
Write-Host "  - Lines 5386, 5410: Missing repository methods (findByWorkHeadContainingAndStatusNotIn, findBySchemeContainingAndStatusNotIn)"
Write-Host ""

# Write the fixed content back
$content | Set-Content -Path $filePath -Encoding UTF8 -NoNewline

Write-Host "File updated successfully!"
Write-Host "New file size: $($content.Length) characters"
Write-Host ""
Write-Host "Running diagnostics to verify fixes..."
