$file = 'c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java'
$content = [System.IO.File]::ReadAllText($file, [System.Text.Encoding]::UTF8)
$original = $content

# The pattern is: someGetter().orElse(null)).orElse(null)
# where the first .orElse(null) is on the getter (wrong - getter returns Long)
# and the second .orElse(null) is on findById result (correct)
# Fix: remove the first .orElse(null)

# Simple string replacement: .orElse(null)).orElse(null) -> ).orElse(null)
# This works because the pattern is always: getter().orElse(null)).orElse(null)
# The )) is: first ) closes getter(), second ) closes findById()
$content = $content.Replace('.orElse(null)).orElse(null)', ').orElse(null)')

# Also fix: .orElse(null)) without result orElse - these are in comments or missing result orElse
# Pattern: getter().orElse(null)) at end of findById arg
# After the above fix, check if any findById still missing .orElse(null)

# Fix: findById(x) without .orElse(null) - add it
# But we need to be careful not to add it where it already exists
# The remaining cases after the above fix should be:
# findById(someExpr)) - where someExpr had .orElse(null) removed
# These now look like: findById(someExpr).orElse(null) - already correct

# Clean up double .orElse(null)
$content = $content.Replace('.orElse(null).orElse(null)', '.orElse(null)')

if ($content -ne $original) {
    [System.IO.File]::WriteAllText($file, $content, [System.Text.Encoding]::UTF8)
    $count = ($original.Split('.orElse(null)).orElse(null)').Length - 1)
    Write-Host "File updated. Replacements: $count"
} else {
    Write-Host "No changes."
}
