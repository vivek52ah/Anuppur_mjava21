$file = 'c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java'
$content = [System.IO.File]::ReadAllText($file, [System.Text.Encoding]::UTF8)
$original = $content

# The problem: inside findById(...) arguments, bean getters like bean.getXxx() had
# .orElse(null) appended to them (they return Long, not Optional).
# Pattern: findById(  ...something.orElse(null)...  ).orElse(null)
# We need: findById(  ...something...  ).orElse(null)

# Fix: remove .orElse(null) from INSIDE findById() argument
# The argument is between findById( and the matching )
# Simple approach: find .orElse(null)) where the ) closes findById
# Pattern: .orElse(null)).orElse(null) -> ).orElse(null)
$content = $content.Replace('.orElse(null)).orElse(null)', ').orElse(null)')

# Also fix: .orElse(null).orElse(null) duplicates
$content = $content.Replace('.orElse(null).orElse(null)', '.orElse(null)')

# Fix primitive long.orElse(null) - these are inside findById args
# Pattern: Long.parseLong(x).orElse(null) inside findById
# findById(Long.parseLong(x).orElse(null)) -> findById(Long.parseLong(x))
$content = [regex]::Replace($content, '(findById\(Long\.parseLong\([^)]+\))\.orElse\(null\)(\))', '$1$2')

# Fix: work.getWorkStatus().orElse(null) - getWorkStatus() returns Long
# These appear as: workStatusRepository.findById(work.getWorkStatus().orElse(null))
# Already handled by the first fix above, but let's also handle standalone cases
# Pattern: .get[A-Z][a-zA-Z]*().orElse(null) where it's NOT the result of findById
# i.e., it appears INSIDE findById(...)
$content = [regex]::Replace($content, '(findById\([^)]*\w+\.[a-z][a-zA-Z]*\(\))\.orElse\(null\)(\)\.orElse\(null\))', '$1$2')

# Fix: .orElse(null) on Work type (not Optional<Work>)
# These are cases where findByIdAndStatusNotIn returns Optional<Work> correctly
# but the variable is Work type - already handled by previous scripts

# Fix: primitive long (not Long) - cannot call orElse
# Pattern: someVar.getId().orElse(null) where getId() returns primitive long
# These appear as: findById(entity.getId().orElse(null))
$content = [regex]::Replace($content, '(findById\([^)]*\.getId\(\))\.orElse\(null\)(\))', '$1$2')
$content = [regex]::Replace($content, '(findById\([^)]*\.getWorkId\(\))\.orElse\(null\)(\))', '$1$2')
$content = [regex]::Replace($content, '(findById\([^)]*\.getWorkStatus\(\))\.orElse\(null\)(\))', '$1$2')

if ($content -ne $original) {
    [System.IO.File]::WriteAllText($file, $content, [System.Text.Encoding]::UTF8)
    Write-Host "File updated."
} else {
    Write-Host "No changes."
}
