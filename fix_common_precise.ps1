$file = 'c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java'
$lines = [System.IO.File]::ReadAllLines($file, [System.Text.Encoding]::UTF8)
$changed = 0

for ($i = 0; $i -lt $lines.Length; $i++) {
    $line = $lines[$i]
    $newLine = $line

    # RULE 1: Remove .orElse(null) from INSIDE findById() argument
    # Pattern: findById(expr.orElse(null)) -> findById(expr)
    # This handles: findById(work.getXxx().orElse(null)) or findById(bean.getXxx().orElse(null))
    while ($newLine -match 'findById\([^)]*\.orElse\(null\)\)') {
        $newLine = [regex]::Replace($newLine, '(findById\([^)]*?)\.orElse\(null\)(\))', '$1$2')
    }

    # RULE 2: Ensure findById(...) result has .orElse(null) - but only if not already there
    # and only if the findById is NOT inside another findById argument
    if ($newLine -match '\.findById\(' -and $newLine -notmatch '\.findById\([^)]+\)\.orElse') {
        $newLine = [regex]::Replace($newLine, '(\.findById\([^)]+\))(?!\.orElse)', '$1.orElse(null)')
    }

    # RULE 3: Fix double .orElse(null)
    while ($newLine -match '\.orElse\(null\)\.orElse\(null\)') {
        $newLine = $newLine.Replace('.orElse(null).orElse(null)', '.orElse(null)')
    }

    # RULE 4: Fix primitive long - Long.parseLong().orElse(null) -> Long.parseLong()
    if ($newLine -match 'Long\.parseLong\([^)]+\)\.orElse\(null\)') {
        $newLine = [regex]::Replace($newLine, '(Long\.parseLong\([^)]+\))\.orElse\(null\)', '$1')
    }

    # RULE 5: Fix .orElse(null) on Work type (findByIdAndStatusNotIn returns Optional<Work>)
    # This is already correct - keep it

    if ($newLine -ne $line) {
        $lines[$i] = $newLine
        $changed++
    }
}

[System.IO.File]::WriteAllLines($file, $lines, [System.Text.Encoding]::UTF8)
Write-Host "Lines changed: $changed"
