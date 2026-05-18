# Controller to Frontend File Mapping

## 📋 Complete Mapping Guide

---

## 1️⃣ LoginController
**Path**: `src/main/java/com/anuppur/controller/LoginController.java`

| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/login` | GET | `login.html` | Login page |
| `/403` | GET | `error/403.html` | Access denied page |
| `/citizenshipRequest` | GET | `citizenshipRequest.html` | Sign up form |
| `/aboutUs` | GET | `aboutus.html` | About us page |
| `/guidelines` | GET | `guidelines.html` | Guidelines page |
| `/contactUs` | GET | `contactUs.html` | Contact us page |

---

## 2️⃣ AdminController
**Path**: `src/main/java/com/anuppur/controller/AdminController.java`
**Base URL**: `/admin/*`

| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/admin/home` | GET | `admin/adminHome.html` | Admin dashboard home |
| `/admin/dashboard` | GET | `admin/dashboard.html` | Admin dashboard |
| `/admin/workBookAndPhotos` | GET | `admin/workBookAndPhotos.html` | Work book and photos |

---

## 3️⃣ SystemAdminController
**Path**: `src/main/java/com/anuppur/controller/SystemAdminController.java`
**Base URL**: `/systemAdmin/*`

### Home & Dashboard
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/systemAdmin/home` | GET | `systemAdmin/systemAdminHome.html` | SystemAdmin dashboard home |

### User Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/systemAdmin/manageusers` | GET | `systemAdmin/manageusers.html` | Manage users list |
| `/systemAdmin/addUserForm` | GET | `systemAdmin/addUserForm.html` | Add new user form |
| `/systemAdmin/editUserForm/{id}` | GET | `systemAdmin/editUserForm.html` | Edit user form |
| `/systemAdmin/managePendingUsers` | GET | `systemAdmin/managePendingUsers.html` | Manage pending users |
| `/systemAdmin/ApproveUser/{id}` | GET | `systemAdmin/ApproveUser.html` | Approve user form |

### Role & Designation Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/systemAdmin/manageRoleDesgntn` | GET | `systemAdmin/manageRoleDesgntn.html` | Manage role designation |
| `/systemAdmin/manageSchemeDesgntn` | GET | `systemAdmin/manageSchemeDesgntn.html` | Manage scheme designation |
| `/systemAdmin/manageDesgntnScheme` | GET | `systemAdmin/manageDesgntnScheme.html` | Manage designation scheme |
| `/systemAdmin/editThisRoleDesignation/{id}` | GET | `systemAdmin/editThisRoleDesignation.html` | Edit role designation |
| `/systemAdmin/editThisSchemeDesignation/{id}` | GET | `systemAdmin/editThisSchemeDesignation.html` | Edit scheme designation |
| `/systemAdmin/editThisDesignationScheme/{id}` | GET | `systemAdmin/editThisDesignationScheme.html` | Edit designation scheme |

### Work Category Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/systemAdmin/manageWorkCategory` | GET | `systemAdmin/manageWorkCategory.html` | Manage work categories |
| `/systemAdmin/addWorkCatForm` | GET | `systemAdmin/addWorkCatForm.html` | Add work category form |
| `/systemAdmin/editWorkCatForm/{id}` | GET | `systemAdmin/editWorkCatForm.html` | Edit work category form |

### Work Type Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/systemAdmin/addWorkType` | GET | `systemAdmin/addWorkSubType.html` | Add work type form |
| `/systemAdmin/manageWorkType` | GET | `systemAdmin/manageWorkSubtype.html` | Manage work types |
| `/systemAdmin/editWorkType/{id}` | GET | `systemAdmin/editWorkSubType.html` | Edit work type form |
| `/systemAdmin/manageWorkSubTypes` | GET | `systemAdmin/manageWorkSubTypes.html` | Manage work sub types |
| `/systemAdmin/addWorkSubTypes` | GET | `systemAdmin/addWorkSubTypes.html` | Add work sub type form |
| `/systemAdmin/editWorkSubTypes/{id}` | GET | `systemAdmin/editWorkSubTypes.html` | Edit work sub type form |

### Work Facility Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/systemAdmin/addWorkFacility` | GET | `systemAdmin/addWorkFacility.html` | Add work facility form |
| `/systemAdmin/manageWorkFacility` | GET | `systemAdmin/manageWorkFacility.html` | Manage work facilities |
| `/systemAdmin/editWorkFacility/{id}` | GET | `systemAdmin/editWorkFacility.html` | Edit work facility form |

### Implementation Agency Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/systemAdmin/addImplAgencyy` | GET | `systemAdmin/addImplAgencyy.html` | Add implementation agency form |
| `/systemAdmin/manageImplAgencyy` | GET | `systemAdmin/manageImplAgencyy.html` | Manage implementation agencies |
| `/systemAdmin/editImplAgencyy/{id}` | GET | `systemAdmin/editImplAgencyy.html` | Edit implementation agency form |

### MLA Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/systemAdmin/manageMLA` | GET | `systemAdmin/manageMLA.html` | Manage MLAs |
| `/systemAdmin/addMLAForm` | GET | `systemAdmin/addMLAForm.html` | Add MLA form |
| `/systemAdmin/editMLAForm/{id}` | GET | `systemAdmin/editMLAForm.html` | Edit MLA form |

### Scheme Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/systemAdmin/manageSchemes` | GET | `systemAdmin/manageSchemes.html` | Manage schemes |
| `/systemAdmin/addSchemeForm` | GET | `systemAdmin/addSchemeForm.html` | Add scheme form |
| `/systemAdmin/editSchemeForm/{id}` | GET | `systemAdmin/editSchemeForm.html` | Edit scheme form |

### Department User Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/systemAdmin/manageDepartmentUser` | GET | `systemAdmin/manageDepartmentUser.html` | Manage department users |
| `/systemAdmin/addDepartmentUser` | GET | `systemAdmin/addDepartmentUser.html` | Add department user form |
| `/systemAdmin/editDepartmentUser/{id}` | GET | `systemAdmin/editDepartmentUser.html` | Edit department user form |

### District/Block/Grampanchayat Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/systemAdmin/manageDistricts` | GET | `systemAdmin/manageDistricts.html` | Manage districts |
| `/systemAdmin/addDistrict` | GET | `systemAdmin/addDistrict.html` | Add district form |
| `/systemAdmin/editDistrict/{id}` | GET | `systemAdmin/editDistrict.html` | Edit district form |
| `/systemAdmin/manageBlock` | GET | `systemAdmin/manageBlock.html` | Manage blocks |
| `/systemAdmin/addBlock` | GET | `systemAdmin/addBlock.html` | Add block form |
| `/systemAdmin/editBlock/{id}` | GET | `systemAdmin/editBlock.html` | Edit block form |
| `/systemAdmin/manageGrampanchayat` | GET | `systemAdmin/manageGrampanchayat.html` | Manage gram panchayats |
| `/systemAdmin/addGrampanchayat` | GET | `systemAdmin/addGrampanchayat.html` | Add gram panchayat form |
| `/systemAdmin/editGrampanchayat/{id}` | GET | `systemAdmin/editGrampanchayat.html` | Edit gram panchayat form |

### Agency Users Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/systemAdmin/manageAgencyUsers` | GET | `systemAdmin/manageAgencyUsers.html` | Manage agency users |
| `/systemAdmin/addUserAgencyFrom` | GET | `systemAdmin/addUserAgencyFrom.html` | Add agency user form |

### Reports
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/systemAdmin/agencyWiseReport` | GET | `common/agencyWiseReport.html` | Agency wise report |
| `/systemAdmin/schemeWiseReport` | GET | `common/schemeWiseReport.html` | Scheme wise report |
| `/systemAdmin/yearWiseReport` | GET | `common/yearWiseReport.html` | Year wise report |
| `/systemAdmin/reports` | GET | `common/reports.html` | Reports page |
| `/systemAdmin/inspectionReport` | GET | `common/inspectionReport.html` | Inspection report |
| `/systemAdmin/workExpenditureReport` | GET | `common/workExpenditureReport.html` | Work expenditure report |
| `/systemAdmin/segmentWiseRport` | GET | `common/segmentWiseRport.html` | Segment wise report |
| `/systemAdmin/schemeYearWiseReport` | GET | `common/schemeYearWiseReport.html` | Scheme year wise report |
| `/systemAdmin/divisionReport` | GET | `common/divisionReport.html` | Division report |
| `/systemAdmin/drawingStatusReport` | GET | `common/drawingStatusReport.html` | Drawing status report |
| `/systemAdmin/asIssuedReport` | GET | `common/asIssuedReport.html` | AS issued report |
| `/systemAdmin/physicalPercentageWiseReport` | GET | `common/physicalPercentageWiseReport.html` | Physical percentage wise report |
| `/systemAdmin/generatePptReports` | GET | `common/generatePptReports.html` | Generate PPT reports |

### Works Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/systemAdmin/manageOngoingWorks` | GET | `common/manageOngoingWorks.html` | Manage ongoing works |
| `/systemAdmin/departmentWiseWorksReport` | GET | `common/departmentWiseWorksReport.html` | Department wise works report |
| `/systemAdmin/photoUpdateReport` | GET | `common/photoUpdateReport.html` | Photo update report |
| `/systemAdmin/dmRemarkWiseReport` | GET | `common/dmRemarkWiseReport.html` | DM remark wise report |

---

## 4️⃣ SuperAdminController
**Path**: `src/main/java/com/anuppur/controller/SuperAdminController.java`
**Base URL**: `/superAdmin/*`

| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/superAdmin/home` | GET | `superAdmin/superAdminHome.html` | SuperAdmin dashboard home |
| `/superAdmin/dashboard` | GET | `superAdmin/dashboard.html` | SuperAdmin dashboard |
| `/superAdmin/manageusers` | GET | `superAdmin/manageusers.html` | Manage users |

---

## 5️⃣ CommonController
**Path**: `src/main/java/com/anuppur/controller/CommonController.java`
**Base URL**: `/common/*` (or direct from other controllers)

### Password Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/changepassword` | GET | `common/changepassword.html` | Change password page |
| `/userchangepassword` | GET | `common/userchangepassword.html` | User change password page |

### Work Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/addNewWork` | GET | `common/addNewWork.html` | Add new work form |
| `/editWork/{id}` | GET | `common/editWork.html` | Edit work form |
| `/viewWork/{id}` | GET | `common/viewWork.html` | View work details |
| `/viewWorkData/{id}` | GET | `common/viewWorkData.html` | View work data |
| `/editOngoingWork` | POST | `common/editOngoingWork.html` | Edit ongoing work |
| `/deleteWork/{id}` | GET | - | Delete work (API call) |
| `/manageOngoingWorks` | GET | `common/manageOngoingWorks.html` | Manage ongoing works |
| `/viewCompletedWork` | GET | `common/viewCompletedWork.html` | View completed works |

### Implementation Agency Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/manageImplAgency` | GET | `common/manageImplAgency.html` | Manage implementation agencies |
| `/addImplAgencyForm` | GET | `common/addImplAgencyForm.html` | Add implementation agency form |
| `/editImplAgencyForm/{id}` | GET | `common/editImplAgencyForm.html` | Edit implementation agency form |

### Head Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/addHeadForm` | GET | `common/addHeadForm.html` | Add head form |
| `/editHeadForm/{id}` | GET | `common/editHeadForm.html` | Edit head form |

### Scheme Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/addSchemeForm` | GET | `common/addSchemeForm.html` | Add scheme form |
| `/editSchemeForm/{id}` | GET | `common/editSchemeForm.html` | Edit scheme form |

### SOR Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/addSorForm` | GET | `common/addSorForm.html` | Add SOR form |
| `/editSorForm/{id}` | GET | `common/editSorForm.html` | Edit SOR form |

### Sub Engineer Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/manageSubEngg` | GET | `common/manageSubEngg.html` | Manage sub engineers |
| `/addSubEnggForm` | GET | `common/addSubEnggForm.html` | Add sub engineer form |
| `/editSubEnggForm/{id}` | GET | `common/editSubEnggForm.html` | Edit sub engineer form |

### Reports
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/subEnggPhotoUploadReport` | GET | `common/subEnggPhotoUploadReport.html` | Sub engineer photo upload report |
| `/implAgencyPhotoUploadReport` | GET | `common/implAgencyPhotoUploadReport.html` | Implementation agency photo upload report |
| `/agencyWiseReport` | GET | `common/agencyWiseReport.html` | Agency wise report |
| `/schemeWiseReport` | GET | `common/schemeWiseReport.html` | Scheme wise report |
| `/yearWiseReport` | GET | `common/yearWiseReport.html` | Year wise report |
| `/inspectionReport` | GET | `common/inspectionReport.html` | Inspection report |
| `/workExpenditureReport` | GET | `common/workExpenditureReport.html` | Work expenditure report |
| `/departmentWiseWorksReport` | GET | `common/departmentWiseWorksReport.html` | Department wise works report |
| `/photoUpdateReport` | GET | `common/photoUpdateReport.html` | Photo update report |
| `/dmRemarkWiseReport` | GET | `common/dmRemarkWiseReport.html` | DM remark wise report |
| `/reports` | GET | `common/reports.html` | Reports page |

### MLA Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/mlaRecommendedWorks` | GET | `common/mlaRecommendedWorks.html` | MLA recommended works |
| `/viewMlaRecommendedWorkDet/{id}` | GET | `common/viewMlaRecommendedWorkDet.html` | View MLA recommended work details |
| `/approveAndCreateWork/{id}` | GET | `common/approveAndCreateWork.html` | Approve and create work |

### Budget Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/manageBudgetDetails` | GET | `common/manageBudgetDetails.html` | Manage budget details |
| `/addFundForm` | GET | `common/addFundForm.html` | Add fund form |
| `/editFundForm/{id}` | GET | `common/editFundForm.html` | Edit fund form |
| `/viewFund/{id}` | GET | `common/viewFund.html` | View fund details |
| `/mlaFundReport` | GET | `common/mlaFundReport.html` | MLA fund report |

### SDR Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/manageSdr` | GET | `common/manageSdr.html` | Manage SDR |
| `/addSdrForm` | GET | `common/addSdrForm.html` | Add SDR form |
| `/editSdrForm/{id}` | GET | `common/editSdrForm.html` | Edit SDR form |

### Legacy Data Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/manageLegacyData` | GET | `common/manageLegacyData.html` | Manage legacy data |
| `/addLegacyWork` | GET | `common/addLegacyWork.html` | Add legacy work |
| `/editLegacyData/{id}` | GET | `common/editLegacyData.html` | Edit legacy data |

### AS Management
| Endpoint | Method | Frontend File | Purpose |
|----------|--------|---------------|---------|
| `/manageAsWorks` | GET | `common/manageAsWorks.html` | Manage AS works |
| `/manageAllParentAS` | GET | `common/manageAllParentAS.html` | Manage all parent AS |
| `/printSelectedAS/{parentAsId}` | GET | `common/printSelectedAS.html` | Print selected AS |
| `/printPreviewSelectedAS/{parentAsId}` | GET | `common/printPreviewSelectedAS.html` | Print preview selected AS |
| `/printPreviewGenerateAS/{workIds}` | GET | `common/printPreviewGenerateAS.html` | Print preview generate AS |

---

## 6️⃣ MobileApiController
**Path**: `src/main/java/com/anuppur/controller/MobileApiController.java`
**Base URL**: `/mobile/*`

| Endpoint | Method | Purpose |
|----------|--------|---------|
| `/mobile/fetchWorkDetails/{id}` | GET | Fetch work details for mobile app |
| `/mobile/fetchWorks/{agencyId}` | GET | Fetch works by agency for mobile app |
| `/mobile/fetchLoggedInUser` | GET | Fetch logged in user for mobile app |
| `/mobile/fetchWorkTenderAgreement/{Id}` | GET | Fetch work tender agreement for mobile app |
| `/mobile/downloadDocumentAS/{documentId}` | GET | Download AS document for mobile app |
| `/mobile/downloadDocumentTS/{documentId}` | GET | Download TS document for mobile app |
| `/mobile/fetchTSASDetails/{id}` | GET | Fetch TSAS details for mobile app |
| `/mobile/fetchWorkProgress/{id}` | GET | Fetch work progress for mobile app |
| `/mobile/fetchWorksByAreaOfficer/{userId}` | GET | Fetch works by area officer for mobile app |
| `/mobile/saveGeoTaging` | POST | Save geo tagging data for mobile app |
| `/mobile/getGeoTaggingForWork/{WorkId}` | GET | Get geo tagging for work for mobile app |
| `/mobile/addWorkProgress` | POST | Add work progress for mobile app |
| `/mobile/fetchProgressImagesList/{workId}` | GET | Fetch progress images list for mobile app |

---

## 📊 Summary

### Total Controllers: 6
1. **LoginController** - Authentication & public pages
2. **AdminController** - Admin role pages
3. **SystemAdminController** - System admin role pages
4. **SuperAdminController** - Super admin role pages
5. **CommonController** - Shared pages for all roles
6. **MobileApiController** - Mobile app API endpoints

### Total Frontend Files: 100+
- Login & public pages
- Admin pages
- SystemAdmin pages
- SuperAdmin pages
- Common pages (shared across roles)
- Error pages

### Request Flow
```
User Request
    ↓
Controller receives request
    ↓
Controller checks authentication & authorization
    ↓
Controller returns ModelAndView with template name
    ↓
Thymeleaf renders HTML template
    ↓
Frontend file (HTML) is sent to browser
    ↓
AngularJS initializes and loads data via AJAX
    ↓
User sees rendered page
```

---

## 🔗 How to Find Which Controller Handles a URL

1. **Check the URL path** - e.g., `/systemAdmin/manageusers`
2. **Find the controller** - Look for `@RequestMapping("/systemAdmin/*")` → SystemAdminController
3. **Find the method** - Look for `@RequestMapping(value = "/manageusers", method = RequestMethod.GET)`
4. **Find the template** - Look at `new ModelAndView("systemAdmin/manageusers")`
5. **Find the file** - Look in `src/main/resources/templates/systemAdmin/manageusers.html`

---

## 🎯 Example: "All Users" Page

```
URL: http://localhost:8085/anuppur/systemAdmin/manageusers
    ↓
SystemAdminController.java
    @RequestMapping(value = "/manageusers", method = RequestMethod.GET)
    public ModelAndView manageUsersView(HttpServletRequest request)
    ↓
Returns: new ModelAndView("systemAdmin/manageusers")
    ↓
Frontend File: src/main/resources/templates/systemAdmin/manageusers.html
    ↓
AngularJS loads and calls: $http.get('fetchUserList')
    ↓
Backend API: SystemAdminController.fetchUserList()
    ↓
Returns JSON data
    ↓
Frontend displays user list in table
```
