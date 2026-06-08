# PowerShell script to fix the misplaced .orElse(null) pattern in CommonServiceImpl.java
# The bad pattern is: findById(somevalue.orElse(null)) which is wrong because
# .orElse(null) was misplaced INSIDE findById() instead of being applied to its result.
# 
# We need to fix patterns like:
#   findById(work.getUserAssignee().orElse(null)).getXXX()
# to:
#   findById(work.getUserAssignee()).orElse(null).getXXX()
#
# But the safest fix is: findById(work.getUserAssignee()).orElse(new Entity()) or use Optional properly
# The simplest, semantically equivalent fix that compiles is:
#   findById(work.getUserAssignee()).orElse(null).getXXX()
# This preserves original NPE behavior (will NPE if not found, same as findOne(id).getXXX())

$file = "c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java"

$content = Get-Content -Path $file -Raw

# Pattern 1: findById(SomeExpression.orElse(null))  →  findById(SomeExpression).orElse(null)
# We need to match findById(...) where the closing paren is BEFORE .orElse(null), then move .orElse(null) outside
# The challenge: matching balanced parentheses inside findById()

# Approach: find all findById( ... .orElse(null)) and shift .orElse(null) outside the closing paren
# Use regex to match findById\([^()]*(\([^()]*\))*[^()]*\.orElse\(null\)\)

# Since the inner expressions can have nested parens like Long.valueOf(x), we need a regex that handles 
# one level of nesting. Use: findById\(((?:[^()]|\([^()]*\))*)\.orElse\(null\)\)

$pattern1 = 'findById\(((?:[^()]|\([^()]*\))*)\.orElse\(null\)\)'
$replacement1 = 'findById($1).orElse(null)'

$newContent = [regex]::Replace($content, $pattern1, $replacement1)

if ($newContent -ne $content) {
    Set-Content -Path $file -Value $newContent -NoNewline
    Write-Host "Fixed misplaced .orElse(null) pattern in CommonServiceImpl.java" -ForegroundColor Green
    
    # Count how many changes we made
    $oldCount = ([regex]::Matches($content, $pattern1)).Count
    Write-Host "Number of fixes: $oldCount" -ForegroundColor Cyan
} else {
    Write-Host "No changes needed" -ForegroundColor Gray
}
