$test = 'userRepository.findById(work.getUserAssignee().orElse(null)).orElse(null).getEmailId()'
$pattern = '(\.findById\()([^)]+?)\.orElse\(null\)(\)\.orElse\(null\))'
$result = [regex]::Replace($test, $pattern, '$1$2$3')
Write-Host "Input:  $test"
Write-Host "Output: $result"
Write-Host "Changed: $($test -ne $result)"

# Try alternative pattern
$pattern2 = 'findById\(([^)]+?)\.orElse\(null\)\)\.orElse\(null\)'
$m = [regex]::Match($test, $pattern2)
Write-Host "Pattern2 match: $($m.Success) - '$($m.Value)'"
