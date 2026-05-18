# Fix remaining Spring Data 3.x migration issues

$filePath = "c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java"

# Read the file content
$content = Get-Content -Path $filePath -Raw -Encoding UTF8

Write-Host "Original file size: $($content.Length) characters"

# Fix remaining getter().orElse(null) patterns that are still incorrect
$patterns = @(
    @{Pattern = 'schemeRepository\.findById\(work\.getSchemeEcrp2\(\)\.orElse\(null\)\)'; Replacement = 'schemeRepository.findById(work.getSchemeEcrp2()).orElse(null)'},
    @{Pattern = 'userRepository\.findById\(DMSUtil\.getUserDetail\(\)\.orElse\(null\)\.getUsername\(\)\)'; Replacement = 'userRepository.findById(DMSUtil.getUserDetail().getUsername()).orElse(null)'},
    @{Pattern = 'headRepository\.findById\(bean\.getHeadEcpr2Id\(\)\.orElse\(null\)\)'; Replacement = 'headRepository.findById(bean.getHeadEcpr2Id()).orElse(null)'},
    @{Pattern = 'schemeRepository\.findById\(bean\.getSchemeEcpr2Id\(\)\.orElse\(null\)\)'; Replacement = 'schemeRepository.findById(bean.getSchemeEcpr2Id()).orElse(null)'},
    @{Pattern = 'workRepository\.findById\(progress\.getWork\(\)\.orElse\(null\)\.getId\(\)\)'; Replacement = 'workRepository.findById(progress.getWork().getId()).orElse(null)'}
)

foreach ($p in $patterns) {
    $content = $content -replace $p.Pattern, $p.Replacement
    Write-Host "Fixed pattern: $($p.Pattern)"
}

# Fix WorkStatus assignments without .orElse(null)
$content = $content -replace '(WorkStatus\s+\w+\s*=\s*workStatusRepository\.findById\([^)]+\));', '$1.orElse(null);'
Write-Host "Fixed WorkStatus Optional assignments"

# Fix Work assignments without .orElse(null)
$content = $content -replace '(Work\s+\w+\s*=\s*workRepository\.findById\([^)]+\));', '$1.orElse(null);'
Write-Host "Fixed Work Optional assignments"

# Fix findByIdAndStatusNotIn with String instead of List<String>
$content = $content -replace '\.findByIdAndStatusNotIn\(([^,]+),\s*DMSConstants\.STATUS_DELETED\)', '.findByIdAndStatusNotIn($1, java.util.List.of(DMSConstants.STATUS_DELETED))'
Write-Host "Fixed findByIdAndStatusNotIn parameter type"

# Fix locationPointsRepository.save(List) if it wasn't fixed before
$content = $content -replace 'locationPointsRepository\.save\(locationPointsList\)', 'locationPointsRepository.saveAll(locationPointsList)'
Write-Host "Fixed locationPointsRepository.save to saveAll"

# Write the fixed content back
$content | Set-Content -Path $filePath -Encoding UTF8 -NoNewline

Write-Host ""
Write-Host "File updated successfully!"
Write-Host "New file size: $($content.Length) characters"
