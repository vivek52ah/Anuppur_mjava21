$f = 'c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System\src\main\java\com\anuppur\service\impl\CommonServiceImpl.java'
$c = [System.IO.File]::ReadAllText($f)
$m = [regex]::Matches($c, 'findById\([^)]+?\.orElse\(null\)\)')
Write-Host "Remaining bad patterns: $($m.Count)"
foreach ($match in $m) {
    Write-Host $match.Value
}
