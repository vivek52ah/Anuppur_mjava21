$file = 'c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java'
$lines = [System.IO.File]::ReadAllLines($file, [System.Text.Encoding]::UTF8)
$changed = 0

for ($i = 0; $i -lt $lines.Length; $i++) {
    $line = $lines[$i]
    $newLine = $line

    # STEP 1: Remove ALL .orElse(null) from INSIDE findById() arguments
    # Keep removing until no more matches (handles nested cases)
    $prev = ''
    while ($prev -ne $newLine) {
        $prev = $newLine
        # Remove .orElse(null) that appears before the closing ) of findById(
        $newLine = [regex]::Replace($newLine, '(\.findById\([^()]*?)\.orElse\(null\)(\))', '$1$2')
    }

    # STEP 2: Remove ALL .orElse(null) from INSIDE other repository method calls that take entity args
    # e.g. findByDistrictAndEnabled(pageable, districtRepository.findById(x).orElse(null), short)
    # These are already handled by step 1 if they use findById

    # STEP 3: Ensure every .findById(...) result has .orElse(null)
    # Only add if not already present
    $prev = ''
    while ($prev -ne $newLine) {
        $prev = $newLine
        $newLine = [regex]::Replace($newLine, '(\.findById\([^()]+\))(?!\.orElse)', '$1.orElse(null)')
    }

    # STEP 4: Remove duplicate .orElse(null)
    while ($newLine -match '\.orElse\(null\)\.orElse\(null\)') {
        $newLine = $newLine.Replace('.orElse(null).orElse(null)', '.orElse(null)')
    }

    # STEP 5: Fix primitive long - Long.parseLong(x).orElse(null) -> Long.parseLong(x)
    if ($newLine -match 'Long\.parseLong\([^)]+\)\.orElse\(null\)') {
        $newLine = [regex]::Replace($newLine, '(Long\.parseLong\([^)]+\))\.orElse\(null\)', '$1')
    }

    if ($newLine -ne $line) {
        $lines[$i] = $newLine
        $changed++
    }
}

[System.IO.File]::WriteAllLines($file, $lines, [System.Text.Encoding]::UTF8)
Write-Host "Lines changed: $changed"
