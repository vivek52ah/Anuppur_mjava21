$file = 'c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java'
$content = [System.IO.File]::ReadAllText($file, [System.Text.Encoding]::UTF8)
$original = $content

# Pattern 1: repository.findById(bean.getXxx().orElse(null))
# The .orElse(null) was wrongly appended to bean getter calls (which return Long, not Optional)
# Fix: bean.getXxx().orElse(null) -> bean.getXxx()
# But we must NOT remove .orElse(null) from actual repository.findById(...).orElse(null) calls

# Strategy: find all occurrences of .orElse(null) that are NOT preceded by )
# i.e., the pattern is: someMethod().orElse(null) where someMethod is a getter on a bean
# The correct pattern is: repository.findById(id).orElse(null)

# Wrong pattern: .getXxx().orElse(null) inside findById(...)
# These look like: findById(bean.getSomething().orElse(null))
# Fix: findById(bean.getSomething())

# Use regex to fix: findById(expr.orElse(null)) -> findById(expr)
# where expr ends with .orElse(null)
$pattern = '(findById\([^)]+)\.orElse\(null\)(\))'
$replacement = '$1$2'

$newContent = [regex]::Replace($content, $pattern, $replacement)
if ($newContent -ne $content) {
    $content = $newContent
    Write-Host "Fixed findById patterns"
}

# Pattern 2: primitive long.orElse(null) - e.g. Long.parseLong(x).orElse(null)
# Fix: Long.parseLong(x).orElse(null) -> Long.parseLong(x)
$pattern2 = '(Long\.parseLong\([^)]+\))\.orElse\(null\)'
$replacement2 = '$1'
$newContent = [regex]::Replace($content, $pattern2, $replacement2)
if ($newContent -ne $content) {
    $content = $newContent
    Write-Host "Fixed Long.parseLong patterns"
}

# Pattern 3: Work findByIdAndStatusNotIn - already fixed to Optional, needs .orElse(null)
# This was: wentityWork = workRepository.findByIdAndStatusNotIn(...).orElse(null)
# which is correct - keep it

# Pattern 4: Any remaining .getXxx().orElse(null) that are NOT part of repository calls
# These are bean getter calls that return Long, not Optional
# Pattern: .<getter>().orElse(null) where getter starts with get and is NOT preceded by findById
$pattern3 = '(\.[a-z][a-zA-Z]+\(\))\.orElse\(null\)'
# Only fix if it's inside a findById() call argument
# More targeted: fix .get[A-Z][a-zA-Z]*().orElse(null)
$pattern4 = '(\.get[A-Z][a-zA-Z]*\(\))\.orElse\(null\)'
$replacement4 = '$1'
$newContent = [regex]::Replace($content, $pattern4, $replacement4)
if ($newContent -ne $content) {
    $content = $newContent
    Write-Host "Fixed bean getter .orElse(null) patterns"
}

# Pattern 5: WorkStatus Optional type mismatch
# workStatusRepository.findById(x) returns Optional<WorkStatus>
# Lines like: WorkStatus ws = workStatusRepository.findById(x).orElse(null) - these are CORRECT
# Lines like: WorkStatus ws = workStatusRepository.findById(x) - these need .orElse(null)
# The previous script may have left some without .orElse(null)
# Check for: WorkStatus \w+ = workStatusRepository.findById(\w+);
$pattern6 = '(workStatusRepository\.findById\([^)]+\))([^.])'
# Only add .orElse(null) if not already there
$newContent = [regex]::Replace($content, '(workStatusRepository\.findById\([^)]+\))(?!\.orElse)', '$1.orElse(null)')
if ($newContent -ne $content) {
    $content = $newContent
    Write-Host "Fixed workStatusRepository.findById patterns"
}

# Pattern 6: locationPointsRepository.save(list) -> saveAll
$content = $content.Replace('locationPointsRepository.save(locationPointsList)', 'locationPointsRepository.saveAll(locationPointsList)')

# Pattern 7: new PageRequest(page, size) -> PageRequest.of(page, size)
$pattern7 = 'new PageRequest\(([^,)]+),\s*([^)]+)\)'
$replacement7 = 'PageRequest.of($1, $2)'
$newContent = [regex]::Replace($content, $pattern7, $replacement7)
if ($newContent -ne $content) {
    $content = $newContent
    Write-Host "Fixed PageRequest constructor"
}

if ($content -ne $original) {
    [System.IO.File]::WriteAllText($file, $content, [System.Text.Encoding]::UTF8)
    Write-Host "File updated successfully."
} else {
    Write-Host "No changes made."
}
