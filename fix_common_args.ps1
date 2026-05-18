$file = 'c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java'
$lines = [System.IO.File]::ReadAllLines($file, [System.Text.Encoding]::UTF8)
$changed = 0

for ($i = 0; $i -lt $lines.Length; $i++) {
    $line = $lines[$i]
    $newLine = $line

    # Remove .orElse(null) from INSIDE findById() argument
    # The argument is a Long value (getter, Long.valueOf, Long.parseLong, etc.)
    # Pattern: findById(someExpr.orElse(null)) -> findById(someExpr)
    # We match: findById( ... .orElse(null) ) where the ) closes findById
    # Use a loop to handle multiple occurrences per line
    $prev = ''
    while ($prev -ne $newLine) {
        $prev = $newLine
        # Match .orElse(null) that is followed by ) which closes findById
        # The key insight: inside findById(), the arg ends with .orElse(null))
        # So we look for: .orElse(null)) where the outer ) is the findById closing paren
        $newLine = [regex]::Replace($newLine, '(findById\([^()]*?)\.orElse\(null\)(\)\.orElse\(null\))', '$1$2')
        $newLine = [regex]::Replace($newLine, '(findById\([^()]*?)\.orElse\(null\)(\))', '$1$2')
    }

    # Clean up any double .orElse(null) that may result
    while ($newLine -match '\.orElse\(null\)\.orElse\(null\)') {
        $newLine = $newLine.Replace('.orElse(null).orElse(null)', '.orElse(null)')
    }

    if ($newLine -ne $line) {
        $lines[$i] = $newLine
        $changed++
    }
}

[System.IO.File]::WriteAllLines($file, $lines, [System.Text.Encoding]::UTF8)
Write-Host "Lines changed: $changed"
