$file = 'c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java'
$content = [System.IO.File]::ReadAllText($file, [System.Text.Encoding]::UTF8)
$original = $content

# Step 1: Add .orElse(null) to ALL repository.findById(...) calls that don't already have it
# Pattern: <word>Repository.findById(<args>) NOT followed by .orElse
$pattern = '(\w+[Rr]epository\.findById\([^)]+\))(?!\.orElse)'
$content = [regex]::Replace($content, $pattern, '$1.orElse(null)')

# Step 2: Fix remaining bean getter .orElse(null) calls
# These are inside findById() argument lists: findById(bean.getXxx().orElse(null))
# Fix: findById(bean.getXxx().orElse(null)) -> findById(bean.getXxx())
# Pattern: findById( ... .get[A-Z][a-zA-Z]*().orElse(null) ... )
$pattern2 = '(findById\([^)]*\.get[A-Z][a-zA-Z]*\(\))\.orElse\(null\)(\))'
$content = [regex]::Replace($content, $pattern2, '$1$2')

# Step 3: Fix Long.parseLong().orElse(null) - primitive, can't call orElse
$content = [regex]::Replace($content, '(Long\.parseLong\([^)]+\))\.orElse\(null\)', '$1')

# Step 4: Fix new PageRequest(page, size) -> PageRequest.of(page, size)
$content = [regex]::Replace($content, 'new PageRequest\(([^,)]+),\s*([^)]+)\)', 'PageRequest.of($1, $2)')

# Step 5: Fix locationPointsRepository.save(list) -> saveAll
$content = $content.Replace('locationPointsRepository.save(locationPointsList)', 'locationPointsRepository.saveAll(locationPointsList)')

if ($content -ne $original) {
    [System.IO.File]::WriteAllText($file, $content, [System.Text.Encoding]::UTF8)
    Write-Host "File updated."
} else {
    Write-Host "No changes."
}
