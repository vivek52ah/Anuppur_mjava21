$file = 'c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java'
$content = [System.IO.File]::ReadAllText($file, [System.Text.Encoding]::UTF8)
$original = $content

# The pattern is: someExpr.orElse(null)).orElse(null)
# where the first .orElse(null) is INSIDE findById() arg (wrong)
# and the second .orElse(null) is on the findById() result (correct)
# Fix: remove the first .orElse(null), keep the second

# This pattern appears as: .findById(x.orElse(null)).orElse(null)
# Fix to: .findById(x).orElse(null)

# Use single-line regex with the specific pattern
$content = [regex]::Replace($content, '(\.findById\()([^)]+?)\.orElse\(null\)(\)\.orElse\(null\))', '$1$2$3')

# Also handle: .findById(x.orElse(null)) without result orElse (missing result orElse)
# Fix to: .findById(x).orElse(null)
$content = [regex]::Replace($content, '(\.findById\()([^)]+?)\.orElse\(null\)(\))(?!\.orElse)', '$1$2$3.orElse(null)')

# Clean duplicates
$content = $content.Replace('.orElse(null).orElse(null)', '.orElse(null)')

# Fix primitive: Long.parseLong(x).orElse(null) -> Long.parseLong(x)
$content = [regex]::Replace($content, '(Long\.parseLong\([^)]+\))\.orElse\(null\)', '$1')

# Fix: work.getWorkStatus() returns Long (primitive-like), not Optional
# Pattern: workStatusRepository.findById(work.getWorkStatus().orElse(null))
# Already handled by the first pattern above

if ($content -ne $original) {
    [System.IO.File]::WriteAllText($file, $content, [System.Text.Encoding]::UTF8)
    $count = ([regex]::Matches($original, '\.findById\([^)]+?\.orElse\(null\)\)')).Count
    Write-Host "File updated. Patterns found: $count"
} else {
    Write-Host "No changes made."
}
