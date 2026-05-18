$file = 'c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java'
$content = [System.IO.File]::ReadAllText($file, [System.Text.Encoding]::UTF8)
$original = $content

# The pattern: .findById(someExpr.orElse(null)).orElse(null)
# where someExpr is a getter call returning Long
# Fix: .findById(someExpr).orElse(null)

# Also: .findById(someExpr.orElse(null)) without result orElse
# Fix: .findById(someExpr).orElse(null)

# Use multiline regex with single-line mode to match across the argument
# The key: match .orElse(null) that appears BEFORE the closing ) of findById

# Pattern: findById(ANYTHING.orElse(null)).orElse(null)
# where ANYTHING doesn't contain ) 
$content = [regex]::Replace($content, '(\.findById\()([^)]+?)\.orElse\(null\)(\)\.orElse\(null\))', '$1$2$3')

# Pattern: findById(ANYTHING.orElse(null)) without result orElse
$content = [regex]::Replace($content, '(\.findById\()([^)]+?)\.orElse\(null\)(\))(?!\.orElse)', '$1$2$3.orElse(null)')

# Also fix: findById(ANYTHING.orElse(null)) in comments (lines starting with *)
# These are in commented-out code - just remove the .orElse(null) from arg
$content = [regex]::Replace($content, '(findById\()([^)]+?)\.orElse\(null\)(\))', '$1$2$3')

# Clean up any double .orElse(null)
$content = $content.Replace('.orElse(null).orElse(null)', '.orElse(null)')

# Fix: findById(x) without .orElse(null) on result - add it
# But only for non-commented lines
# This is already handled by previous scripts

if ($content -ne $original) {
    [System.IO.File]::WriteAllText($file, $content, [System.Text.Encoding]::UTF8)
    Write-Host "File updated."
} else {
    Write-Host "No changes."
}
