$file = 'c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java'
$content = [System.IO.File]::ReadAllText($file, [System.Text.Encoding]::UTF8)
$original = $content

# The core problem: repository.findById(x) returns Optional<T>
# but code uses it as T directly (assignment, method arg, etc.)
# Fix: add .orElse(null) to ALL findById calls not already having it

# This regex matches any .findById(...) NOT followed by .orElse or .get or .isPresent or .map or .ifPresent
# We need to handle nested parens in the argument - use a simpler approach
# Match: .findById(simpleArg) where simpleArg has no nested parens
$pattern = '(\.findById\([^()]+\))(?!\.(orElse|get\(\)|isPresent|map|ifPresent|stream|filter|flatMap|or\())'
$content = [regex]::Replace($content, $pattern, '$1.orElse(null)')

# Fix remaining bean getter .orElse(null) - these are inside findById args
# Pattern: findById(something.getXxx().orElse(null)) -> findById(something.getXxx())
# The .orElse(null) was wrongly added to the argument, not the result
$pattern2 = '(\.findById\([^)]*\.get[A-Z][a-zA-Z]*\(\))\.orElse\(null\)(\)\.orElse\(null\))'
$content = [regex]::Replace($content, $pattern2, '$1$2')

# Fix double .orElse(null).orElse(null) that may have been created
$content = $content.Replace('.orElse(null).orElse(null)', '.orElse(null)')

# Fix Long.parseLong().orElse(null) - primitive
$content = [regex]::Replace($content, '(Long\.parseLong\([^)]+\))\.orElse\(null\)', '$1')

# Fix new PageRequest(page, size) -> PageRequest.of(page, size)
$content = [regex]::Replace($content, 'new PageRequest\(([^,)]+),\s*([^)]+)\)', 'PageRequest.of($1, $2)')

# Fix locationPointsRepository.save(list) -> saveAll
$content = $content.Replace('locationPointsRepository.save(locationPointsList)', 'locationPointsRepository.saveAll(locationPointsList)')

if ($content -ne $original) {
    [System.IO.File]::WriteAllText($file, $content, [System.Text.Encoding]::UTF8)
    Write-Host "File updated."
} else {
    Write-Host "No changes."
}
