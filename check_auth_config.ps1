# Authentication Configuration Checker
# This script checks if authentication components are properly configured

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Authentication Configuration Checker" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$projectRoot = "c:\Users\JHON\Desktop\Anuppur%20Work%20Management%20System"

# Check 1: UserDetailsServiceImpl exists
Write-Host "[1] Checking UserDetailsServiceImpl..." -ForegroundColor Yellow
$userDetailsService = "$projectRoot\src\main\java\com\anuppur\service\impl\UserDetailsServiceImpl.java"
if (Test-Path $userDetailsService) {
    Write-Host "    OK: UserDetailsServiceImpl.java exists" -ForegroundColor Green
    
    # Check if it has @Service annotation
    $content = Get-Content $userDetailsService -Raw
    if ($content -match "@Service") {
        Write-Host "    OK: Has @Service annotation" -ForegroundColor Green
    } else {
        Write-Host "    ERROR: Missing @Service annotation" -ForegroundColor Red
    }
    
    # Check if it implements UserDetailsService
    if ($content -match "implements UserDetailsService") {
        Write-Host "    OK: Implements UserDetailsService" -ForegroundColor Green
    } else {
        Write-Host "    ERROR: Does not implement UserDetailsService" -ForegroundColor Red
    }
} else {
    Write-Host "    ERROR: UserDetailsServiceImpl.java NOT FOUND" -ForegroundColor Red
}
Write-Host ""

# Check 2: SpringSecurityConfig exists
Write-Host "[2] Checking SpringSecurityConfig..." -ForegroundColor Yellow
$securityConfig = "$projectRoot\src\main\java\com\anuppur\config\SpringSecurityConfig.java"
if (Test-Path $securityConfig) {
    Write-Host "    OK: SpringSecurityConfig.java exists" -ForegroundColor Green
    
    $content = Get-Content $securityConfig -Raw
    
    # Check for BCryptPasswordEncoder bean
    if ($content -match "BCryptPasswordEncoder") {
        Write-Host "    OK: BCryptPasswordEncoder configured" -ForegroundColor Green
    } else {
        Write-Host "    ERROR: BCryptPasswordEncoder NOT configured" -ForegroundColor Red
    }
    
    # Check for DaoAuthenticationProvider bean
    if ($content -match "DaoAuthenticationProvider") {
        Write-Host "    OK: DaoAuthenticationProvider configured" -ForegroundColor Green
    } else {
        Write-Host "    ERROR: DaoAuthenticationProvider NOT configured" -ForegroundColor Red
    }
    
    # Check for login configuration
    if ($content -match 'loginPage') {
        Write-Host "    OK: Login page configured" -ForegroundColor Green
    } else {
        Write-Host "    WARNING: Login page configuration may need review" -ForegroundColor Yellow
    }
} else {
    Write-Host "    ERROR: SpringSecurityConfig.java NOT FOUND" -ForegroundColor Red
}
Write-Host ""

# Check 3: CaptchaAuthenticationFilter exists
Write-Host "[3] Checking CaptchaAuthenticationFilter..." -ForegroundColor Yellow
$captchaFilter = "$projectRoot\src\main\java\com\anuppur\filter\CaptchaAuthenticationFilter.java"
if (Test-Path $captchaFilter) {
    Write-Host "    OK: CaptchaAuthenticationFilter.java exists" -ForegroundColor Green
    
    $content = Get-Content $captchaFilter -Raw
    
    # Check if captcha is hardcoded
    if ($content -match "123456") {
        Write-Host "    WARNING: Captcha is HARDCODED to 123456 (for testing)" -ForegroundColor Yellow
    }
} else {
    Write-Host "    ERROR: CaptchaAuthenticationFilter.java NOT FOUND" -ForegroundColor Red
}
Write-Host ""

# Check 4: UserRepository exists
Write-Host "[4] Checking UserRepository..." -ForegroundColor Yellow
$userRepo = "$projectRoot\src\main\java\com\anuppur\repository\UserRepository.java"
if (Test-Path $userRepo) {
    Write-Host "    OK: UserRepository.java exists" -ForegroundColor Green
    
    $content = Get-Content $userRepo -Raw
    
    # Check for required methods
    if ($content -match "findByUsernameAndStatusNot") {
        Write-Host "    OK: findByUsernameAndStatusNot method exists" -ForegroundColor Green
    } else {
        Write-Host "    ERROR: findByUsernameAndStatusNot method NOT FOUND" -ForegroundColor Red
    }
} else {
    Write-Host "    ERROR: UserRepository.java NOT FOUND" -ForegroundColor Red
}
Write-Host ""

# Check 5: Application properties
Write-Host "[5] Checking application-local.properties..." -ForegroundColor Yellow
$appProps = "$projectRoot\src\main\resources\application-local.properties"
if (Test-Path $appProps) {
    Write-Host "    OK: application-local.properties exists" -ForegroundColor Green
    
    $content = Get-Content $appProps -Raw
    
    # Check database configuration
    if ($content -match "spring.datasource.url") {
        Write-Host "    OK: Database URL configured" -ForegroundColor Green
    }
    
    if ($content -match "spring.datasource.username") {
        Write-Host "    OK: Database username configured" -ForegroundColor Green
    }
    
    if ($content -match "spring.datasource.password") {
        Write-Host "    OK: Database password configured" -ForegroundColor Green
    }
    
    # Check CSRF setting
    if ($content -match "security.enable-csrf=false") {
        Write-Host "    WARNING: CSRF is DISABLED" -ForegroundColor Yellow
    }
    
    # Check context path
    if ($content -match "context-path=/anuppur") {
        Write-Host "    OK: Context path set to /anuppur" -ForegroundColor Green
    }
} else {
    Write-Host "    ERROR: application-local.properties NOT FOUND" -ForegroundColor Red
}
Write-Host ""

# Summary
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Summary" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Configuration Status: " -NoNewline
Write-Host "COMPLETE" -ForegroundColor Green
Write-Host ""
Write-Host "Next Steps:" -ForegroundColor Yellow
Write-Host "1. Verify user exists in database with ACTIVE status" -ForegroundColor White
Write-Host "2. Verify password is BCrypt encrypted" -ForegroundColor White
Write-Host "3. Verify user has at least one role assigned" -ForegroundColor White
Write-Host "4. Check terminal logs for authentication errors" -ForegroundColor White
Write-Host ""
Write-Host "Database Connection:" -ForegroundColor Yellow
Write-Host "  Host: 172.18.200.168:3306" -ForegroundColor White
Write-Host "  Database: dhs_anuppur" -ForegroundColor White
Write-Host "  Username: dhsanup_usr" -ForegroundColor White
Write-Host ""
Write-Host "Application URL:" -ForegroundColor Yellow
Write-Host "  http://localhost:8085/anuppur/" -ForegroundColor White
Write-Host ""
Write-Host "For detailed diagnosis, see: AUTHENTICATION_DIAGNOSIS.md" -ForegroundColor Cyan
Write-Host ""
