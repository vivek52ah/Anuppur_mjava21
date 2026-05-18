$file = 'c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java'
$content = [System.IO.File]::ReadAllText($file, [System.Text.Encoding]::UTF8)
$original = $content

# The core problem: findById(expr.orElse(null)) or findById(expr.orElse(null)).orElse(null)
# where expr is a Long getter/valueOf/parseLong
# Fix: remove .orElse(null) from the ARGUMENT, keep/add it on the RESULT

# Pattern 1: findById(x.orElse(null)).orElse(null) -> findById(x).orElse(null)
$content = [regex]::Replace($content, '(\.findById\()([^)]+?)\.orElse\(null\)(\)\.orElse\(null\))', '$1$2$3')

# Pattern 2: findById(x.orElse(null)) without result .orElse -> findById(x).orElse(null)
$content = [regex]::Replace($content, '(\.findById\()([^)]+?)\.orElse\(null\)(\))(?!\.orElse)', '$1$2$3.orElse(null)')

# Clean up any double .orElse(null)
$content = $content.Replace('.orElse(null).orElse(null)', '.orElse(null)')

# Fix Long.valueOf(x).orElse(null) inside findById - Long.valueOf returns Long not Optional
$content = [regex]::Replace($content, '(\.findById\(Long\.valueOf\([^)]+\))\.orElse\(null\)(\))', '$1$2')

# Fix Long.parseLong(x).orElse(null) - primitive
$content = [regex]::Replace($content, '(Long\.parseLong\([^)]+\))\.orElse\(null\)', '$1')

# Ensure findById(Long.valueOf(x)) has .orElse(null) on result
$content = [regex]::Replace($content, '(\.findById\(Long\.valueOf\([^)]+\)\))(?!\.orElse)', '$1.orElse(null)')

# Clean up again
$content = $content.Replace('.orElse(null).orElse(null)', '.orElse(null)')

if ($content -ne $original) {
    [System.IO.File]::WriteAllText($file, $content, [System.Text.Encoding]::UTF8)
    Write-Host "File updated."
} else {
    Write-Host "No changes."
}
