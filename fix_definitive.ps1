$file = 'c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java'
$content = [System.IO.File]::ReadAllText($file, [System.Text.Encoding]::UTF8)
$original = $content

# THE ROOT CAUSE:
# Previous scripts added .orElse(null) to EVERY getter call.
# Getters like work.getWorkType(), bean.getId(), etc. return Long (not Optional).
# So we have: repository.findById(work.getWorkType().orElse(null)).orElse(null)
# Which should be: repository.findById(work.getWorkType()).orElse(null)
#
# THE FIX: Remove .orElse(null) from inside findById() arguments.
# The pattern is always: .findById(EXPR.orElse(null)).orElse(null)
# We want:              .findById(EXPR).orElse(null)
#
# Key insight: inside findById(), the arg ends with .orElse(null))
# followed by .orElse(null) on the result.
# So the full pattern is: .orElse(null)).orElse(null)
# Fix: replace with just ).orElse(null)

# Step 1: Fix the double pattern: .orElse(null)).orElse(null) -> ).orElse(null)
$before = ($content.Split('.orElse(null)).orElse(null)').Length - 1)
$content = $content.Replace('.orElse(null)).orElse(null)', ').orElse(null)')
$after = ($content.Split('.orElse(null)).orElse(null)').Length - 1)
Write-Host "Step 1 - Double pattern fixes: $($before - $after)"

# Step 2: Fix remaining .orElse(null)) where the ) closes findById
# These are cases where findById(x.orElse(null)) has no result .orElse(null)
# Pattern: .orElse(null)) followed by something that is NOT .orElse
# We need to add .orElse(null) to the result AND remove from arg
# But first check if any remain
$remaining = [regex]::Matches($content, '\.orElse\(null\)\)(?!\.orElse)').Count
Write-Host "Step 2 - Remaining .orElse(null)) without result orElse: $remaining"

# Fix these: .orElse(null)) -> ).orElse(null)
# But only inside findById context - use the fact that these appear after getter calls
$content = [regex]::Replace($content, '(\.get[A-Z][a-zA-Z]*\(\))\.orElse\(null\)(\)\.orElse\(null\))', '$1$2')
$content = [regex]::Replace($content, '(\.get[A-Z][a-zA-Z]*\(\))\.orElse\(null\)(\))(?!\.orElse)', '$1$2.orElse(null)')

# Step 3: Fix primitive long cases - Long.parseLong().orElse(null)
$content = [regex]::Replace($content, '(Long\.parseLong\([^)]+\))\.orElse\(null\)', '$1')

# Step 4: Fix work.getWork().orElse(null) - getWork() returns Work entity not Optional
# These appear as: progress.getWork().orElse(null).getId()
$content = [regex]::Replace($content, '(\.getWork\(\))\.orElse\(null\)', '$1')

# Step 5: Fix districtRepository.findById(Long.parseLong(x)) - missing .orElse(null)
$content = [regex]::Replace($content, '(districtRepository\.findById\(Long\.parseLong\([^)]+\)\))(?!\.orElse)', '$1.orElse(null)')

# Step 6: Clean up any double .orElse(null)
$content = $content.Replace('.orElse(null).orElse(null)', '.orElse(null)')

if ($content -ne $original) {
    [System.IO.File]::WriteAllText($file, $content, [System.Text.Encoding]::UTF8)
    Write-Host "File updated successfully."
} else {
    Write-Host "No changes made."
}
