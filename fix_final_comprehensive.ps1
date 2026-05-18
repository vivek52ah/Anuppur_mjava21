# Final comprehensive Spring Data 3.x migration fix
# This script fixes patterns where .orElse(null) is incorrectly placed

$filePath = "c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java"

# Read the file content
$content = Get-Content -Path $filePath -Raw -Encoding UTF8

Write-Host "Original file size: $($content.Length) characters"

# Fix Pattern 1: .findById(Long.valueOf(x).orElse(null)) -> .findById(Long.valueOf(x)).orElse(null)
$count = 0
while ($content -match '\.findById\(Long\.valueOf\(([^)]+)\)\.orElse\(null\)\)') {
    $content = $content -replace '\.findById\(Long\.valueOf\(([^)]+)\)\.orElse\(null\)\)', '.findById(Long.valueOf($1)).orElse(null)'
    $count++
    if ($count -gt 100) { break }
}
Write-Host "Fixed $count occurrences of Long.valueOf().orElse(null) inside findById"

# Fix Pattern 2: .findById(something.getSomething().orElse(null)) -> .findById(something.getSomething()).orElse(null)
$count = 0
while ($content -match '\.findById\(([a-zA-Z]+)\.get([A-Z][a-zA-Z]+)\(\)\.orElse\(null\)\)') {
    $content = $content -replace '\.findById\(([a-zA-Z]+)\.get([A-Z][a-zA-Z]+)\(\)\.orElse\(null\)\)', '.findById($1.get$2()).orElse(null)'
    $count++
    if ($count -gt 100) { break }
}
Write-Host "Fixed $count occurrences of getter().orElse(null) inside findById"

# Fix Pattern 3: .findById(id).orElse(null) where id is primitive long -> .findById(Long.valueOf(id)).orElse(null)
$content = $content -replace 'workSubDelayResonRepository\.findById\(id\)\.orElse\(null\)', 'workSubDelayResonRepository.findById(Long.valueOf(id)).orElse(null)'
Write-Host "Fixed primitive long id parameter"

# Fix Pattern 4: .findById(Long.parseLong(x).orElse(null)) -> .findById(Long.parseLong(x)).orElse(null)
$count = 0
while ($content -match '\.findById\(Long\.parseLong\(([^)]+)\)\.orElse\(null\)\)') {
    $content = $content -replace '\.findById\(Long\.parseLong\(([^)]+)\)\.orElse\(null\)\)', '.findById(Long.parseLong($1)).orElse(null)'
    $count++
    if ($count -gt 100) { break }
}
Write-Host "Fixed $count occurrences of Long.parseLong().orElse(null) inside findById"

# Fix Pattern 5: countByStatusNotIn(DMSConstants.STATUS_DELETED) -> countByStatusNotIn(java.util.List.of(DMSConstants.STATUS_DELETED))
$content = $content -replace 'countByStatusNotIn\(DMSConstants\.STATUS_DELETED\)', 'countByStatusNotIn(java.util.List.of(DMSConstants.STATUS_DELETED))'
Write-Host "Fixed countByStatusNotIn parameter type"

# Fix Pattern 6: findByStatusNotInAndWorkStatusIn(pageable, DMSConstants.STATUS_DELETED, workStatus) 
# -> findByStatusNotInAndWorkStatusIn(pageable, java.util.List.of(DMSConstants.STATUS_DELETED), java.util.List.of(workStatus))
$content = $content -replace 'findByStatusNotInAndWorkStatusIn\(pageable,\s*DMSConstants\.STATUS_DELETED,\s*([^)]+)\)', 'findByStatusNotInAndWorkStatusIn(pageable, java.util.List.of(DMSConstants.STATUS_DELETED), java.util.List.of($1))'
Write-Host "Fixed findByStatusNotInAndWorkStatusIn parameter types"

# Fix Pattern 7: Add .orElse(null) to findById calls that don't have it and are assigned to non-Optional types
# This is complex, so we'll handle specific cases

# Fix Pattern 8: locationPointsRepository.save(List) -> locationPointsRepository.saveAll(List)
$content = $content -replace 'locationPointsRepository\.save\(locationPointsList\)', 'locationPointsRepository.saveAll(locationPointsList)'
Write-Host "Fixed locationPointsRepository.save to saveAll"

# Fix Pattern 9: new PageRequest(page, size) -> PageRequest.of(page, size)
$content = $content -replace 'new PageRequest\(([^,]+),\s*([^)]+)\)', 'PageRequest.of($1, $2)'
Write-Host "Fixed PageRequest constructor to PageRequest.of()"

# Write the fixed content back
$content | Set-Content -Path $filePath -Encoding UTF8 -NoNewline

Write-Host ""
Write-Host "File updated successfully!"
Write-Host "New file size: $($content.Length) characters"
