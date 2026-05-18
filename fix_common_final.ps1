$file = 'c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java'
$lines = [System.IO.File]::ReadAllLines($file, [System.Text.Encoding]::UTF8)
$changed = 0

for ($i = 0; $i -lt $lines.Length; $i++) {
    $line = $lines[$i]

    # Fix 1: repository.findById(x) NOT followed by .orElse — add .orElse(null)
    # Match: .findById(anything) at end of expression (followed by ; or ) or . but NOT .orElse)
    if ($line -match '\.findById\(' -and $line -notmatch '\.findById\(.*\)\.orElse') {
        # Replace .findById(x) with .findById(x).orElse(null)
        # But only if the closing ) is on the same line
        $newLine = [regex]::Replace($line, '(\.findById\([^)]+\))(?!\.orElse)', '$1.orElse(null)')
        if ($newLine -ne $line) {
            $lines[$i] = $newLine
            $changed++
        }
    }

    # Fix 2: bean.getXxx().orElse(null) inside findById args — remove the wrong .orElse(null)
    # Pattern: findById(something.getXxx().orElse(null)) -> findById(something.getXxx())
    if ($line -match 'findById\(.*\.get[A-Z].*\.orElse\(null\)\)') {
        $newLine = [regex]::Replace($line, '(findById\([^)]*\.get[A-Z][a-zA-Z]*\(\))\.orElse\(null\)(\))', '$1$2')
        if ($newLine -ne $line) {
            $lines[$i] = $newLine
            $changed++
        }
    }

    # Fix 3: Long.parseLong(x).orElse(null) — primitive, remove .orElse(null)
    if ($line -match 'Long\.parseLong\(.*\)\.orElse\(null\)') {
        $newLine = [regex]::Replace($line, '(Long\.parseLong\([^)]+\))\.orElse\(null\)', '$1')
        if ($newLine -ne $line) {
            $lines[$i] = $newLine
            $changed++
        }
    }

    # Fix 4: Double .orElse(null).orElse(null) cleanup
    if ($line -match '\.orElse\(null\)\.orElse\(null\)') {
        $lines[$i] = $line.Replace('.orElse(null).orElse(null)', '.orElse(null)')
        $changed++
    }

    # Fix 5: new PageRequest(a, b) -> PageRequest.of(a, b)
    if ($line -match 'new PageRequest\(') {
        $newLine = [regex]::Replace($line, 'new PageRequest\(([^,)]+),\s*([^)]+)\)', 'PageRequest.of($1, $2)')
        if ($newLine -ne $line) {
            $lines[$i] = $newLine
            $changed++
        }
    }

    # Fix 6: locationPointsRepository.save(list) -> saveAll
    if ($line -match 'locationPointsRepository\.save\(') {
        $newLine = $line.Replace('locationPointsRepository.save(locationPointsList)', 'locationPointsRepository.saveAll(locationPointsList)')
        if ($newLine -ne $line) {
            $lines[$i] = $newLine
            $changed++
        }
    }
}

[System.IO.File]::WriteAllLines($file, $lines, [System.Text.Encoding]::UTF8)
Write-Host "Lines changed: $changed"
