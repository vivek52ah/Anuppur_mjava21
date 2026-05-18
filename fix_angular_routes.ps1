# Fix Angular routing template URLs

Write-Host "Fixing Angular routing template URLs..." -ForegroundColor Cyan

$file = "src\main\resources\static\angular\systemAdmin\SystemAdminRouting.js"
$content = Get-Content $file -Raw

# SystemAdmin routes - add systemAdmin/ prefix
$systemAdminRoutes = @(
    "manageRoleDesgntn",
    "manageSchemeDesgntn",
    "manageDesgntnScheme",
    "manageWorkCategory",
    "manageMLA",
    "addWorkCatForm",
    "addMLAForm",
    "manageSchemes",
    "addSchemeForm",
    "manageusers",
    "addUserForm",
    "manageAgencyUsers",
    "addUserAgencyFrom",
    "addWorkFacility",
    "manageWorkFacility",
    "addWorkSubType",
    "manageWorkSubtype",
    "addImplAgencyy",
    "manageImplAgencyy",
    "addFinancialYear",
    "manageDepartmentUser",
    "addDepartmentUser",
    "manageDistricts",
    "manageBlock",
    "manageGrampanchayat",
    "addDistrict",
    "addBlock",
    "addGrampanchayat",
    "managePendingUsers",
    "manageWorkSubTypes",
    "addWorkSubTypes"
)

# Common routes - add common/ prefix
$commonRoutes = @(
    "manageOngoingWorks",
    "departmentWiseWorksReport",
    "photoUpdateReport",
    "dmRemarkWiseReport",
    "agencyWiseReport",
    "schemeWiseReport",
    "yearWiseReport",
    "reports",
    "inspectionReport",
    "workExpenditureReport",
    "segmentWiseRport",
    "schemeYearWiseReport",
    "divisionReport",
    "drawingStatusReport",
    "asIssuedReport",
    "physicalPercentageWiseReport",
    "generatePptReports"
)

$changes = 0

# Fix SystemAdmin routes
foreach ($route in $systemAdminRoutes) {
    $pattern = "templateUrl:\s*'$route'"
    $replacement = "templateUrl: 'systemAdmin/$route'"
    if ($content -match $pattern) {
        $content = $content -replace $pattern, $replacement
        $changes++
        Write-Host "Fixed: $route -> systemAdmin/$route" -ForegroundColor Green
    }
}

# Fix Common routes
foreach ($route in $commonRoutes) {
    $pattern = "templateUrl:\s*'$route'"
    $replacement = "templateUrl: 'common/$route'"
    if ($content -match $pattern) {
        $content = $content -replace $pattern, $replacement
        $changes++
        Write-Host "Fixed: $route -> common/$route" -ForegroundColor Green
    }
}

# Fix dynamic routes with parameters (SystemAdmin)
$content = $content -replace "return 'editThisRoleDesignation/", "return 'systemAdmin/editThisRoleDesignation/"
$content = $content -replace "return 'editThisSchemeDesignation/", "return 'systemAdmin/editThisSchemeDesignation/"
$content = $content -replace "return 'editThisDesignationScheme/", "return 'systemAdmin/editThisDesignationScheme/"
$content = $content -replace "return 'editWorkCatForm/", "return 'systemAdmin/editWorkCatForm/"
$content = $content -replace "return 'editMLAForm/", "return 'systemAdmin/editMLAForm/"
$content = $content -replace "return 'editSchemeForm/", "return 'systemAdmin/editSchemeForm/"
$content = $content -replace "return 'editUserForm/", "return 'systemAdmin/editUserForm/"
$content = $content -replace "return 'editWorkFacility/", "return 'systemAdmin/editWorkFacility/"
$content = $content -replace "return 'editWorkSubType/", "return 'systemAdmin/editWorkSubType/"
$content = $content -replace "return 'editImplAgencyy/", "return 'systemAdmin/editImplAgencyy/"
$content = $content -replace "return 'editDepartmentUser/", "return 'systemAdmin/editDepartmentUser/"
$content = $content -replace "return 'editDistrict/", "return 'systemAdmin/editDistrict/"
$content = $content -replace "return 'editBlock/", "return 'systemAdmin/editBlock/"
$content = $content -replace "return 'editGrampanchayat/", "return 'systemAdmin/editGrampanchayat/"
$content = $content -replace "return 'ApproveUser/", "return 'systemAdmin/ApproveUser/"
$content = $content -replace "return 'editWorkSubTypes/", "return 'systemAdmin/editWorkSubTypes/"

Write-Host ""
Write-Host "Saving changes..." -ForegroundColor Cyan
Set-Content -Path $file -Value $content -NoNewline

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "Angular routing fix complete!" -ForegroundColor Green
Write-Host "Total changes: $changes" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Green
Write-Host ""
Write-Host "Next: Restart application and clear browser cache" -ForegroundColor Yellow
