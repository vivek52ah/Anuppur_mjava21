# Quick Controller Reference Guide

## 🚀 Quick Lookup

### Find Controller by URL

```
URL Pattern → Controller → Frontend File

/login → LoginController → login.html
/admin/* → AdminController → admin/*.html
/systemAdmin/* → SystemAdminController → systemAdmin/*.html
/superAdmin/* → SuperAdminController → superAdmin/*.html
/common/* → CommonController → common/*.html
/mobile/* → MobileApiController → JSON API (no frontend file)
```

---

## 📋 All Controllers at a Glance

### 1. LoginController
```
Purpose: Handle authentication and public pages
Base URL: /
Endpoints:
  GET /login → login.html
  GET /403 → error/403.html
  GET /citizenshipRequest → citizenshipRequest.html
  GET /aboutUs → aboutus.html
  GET /guidelines → guidelines.html
  GET /contactUs → contactUs.html
```

### 2. AdminController
```
Purpose: Handle admin role pages
Base URL: /admin/*
Endpoints:
  GET /admin/home → admin/adminHome.html
  GET /admin/dashboard → admin/dashboard.html
  GET /admin/workBookAndPhotos → admin/workBookAndPhotos.html
```

### 3. SystemAdminController
```
Purpose: Handle system admin role pages
Base URL: /systemAdmin/*
Endpoints: 50+ endpoints including:
  GET /systemAdmin/home → systemAdmin/systemAdminHome.html
  GET /systemAdmin/manageusers → systemAdmin/manageusers.html
  GET /systemAdmin/addUserForm → systemAdmin/addUserForm.html
  GET /systemAdmin/manageRoleDesgntn → systemAdmin/manageRoleDesgntn.html
  GET /systemAdmin/manageWorkCategory → systemAdmin/manageWorkCategory.html
  GET /systemAdmin/manageWorkType → systemAdmin/manageWorkSubtype.html
  GET /systemAdmin/manageWorkFacility → systemAdmin/manageWorkFacility.html
  GET /systemAdmin/manageImplAgencyy → systemAdmin/manageImplAgencyy.html
  GET /systemAdmin/manageMLA → systemAdmin/manageMLA.html
  GET /systemAdmin/manageSchemes → systemAdmin/manageSchemes.html
  GET /systemAdmin/manageDistricts → systemAdmin/manageDistricts.html
  GET /systemAdmin/manageBlock → systemAdmin/manageBlock.html
  GET /systemAdmin/manageGrampanchayat → systemAdmin/manageGrampanchayat.html
  ... and 37+ more
```

### 4. SuperAdminController
```
Purpose: Handle super admin role pages
Base URL: /superAdmin/*
Endpoints:
  GET /superAdmin/home → superAdmin/superAdminHome.html
  GET /superAdmin/dashboard → superAdmin/dashboard.html
  GET /superAdmin/manageusers → superAdmin/manageusers.html
```

### 5. CommonController
```
Purpose: Handle shared pages for all roles
Base URL: /common/* (or direct from other controllers)
Endpoints: 100+ endpoints including:
  GET /changepassword → common/changepassword.html
  GET /addNewWork → common/addNewWork.html
  GET /manageOngoingWorks → common/manageOngoingWorks.html
  GET /manageImplAgency → common/manageImplAgency.html
  GET /reports → common/reports.html
  GET /agencyWiseReport → common/agencyWiseReport.html
  GET /schemeWiseReport → common/schemeWiseReport.html
  GET /yearWiseReport → common/yearWiseReport.html
  GET /inspectionReport → common/inspectionReport.html
  GET /workExpenditureReport → common/workExpenditureReport.html
  ... and 90+ more
```

### 6. MobileApiController
```
Purpose: Handle mobile app API requests
Base URL: /mobile/*
Endpoints: 20+ API endpoints (returns JSON, no frontend file)
  GET /mobile/fetchWorkDetails/{id}
  GET /mobile/fetchWorks/{agencyId}
  GET /mobile/fetchLoggedInUser
  POST /mobile/addWorkProgress
  ... and 16+ more
```

---

## 🔍 How to Find What You Need

### I want to find the controller for a URL
```
1. Look at the URL path
2. Match it to the base URL pattern
3. Find the controller

Example:
URL: /anuppur/systemAdmin/manageusers
Path: /systemAdmin/manageusers
Pattern: /systemAdmin/*
Controller: SystemAdminController
```

### I want to find the frontend file for a controller
```
1. Find the controller method
2. Look for @RequestMapping(value = "...")
3. Look for new ModelAndView("...")
4. The template name maps to the file path

Example:
@RequestMapping(value = "/manageusers", method = RequestMethod.GET)
public ModelAndView manageUsersView(...) {
    return new ModelAndView("systemAdmin/manageusers");
}
File: src/main/resources/templates/systemAdmin/manageusers.html
```

### I want to find the controller for a frontend file
```
1. Look at the file path
2. Extract the template name
3. Search for that template name in controllers

Example:
File: templates/systemAdmin/manageusers.html
Template: systemAdmin/manageusers
Search: new ModelAndView("systemAdmin/manageusers")
Found in: SystemAdminController.manageUsersView()
```

---

## 📊 Statistics

| Metric | Count |
|--------|-------|
| Total Controllers | 6 |
| Total Endpoints | 200+ |
| Total Frontend Files | 100+ |
| LoginController Endpoints | 6 |
| AdminController Endpoints | 3 |
| SystemAdminController Endpoints | 50+ |
| SuperAdminController Endpoints | 3 |
| CommonController Endpoints | 100+ |
| MobileApiController Endpoints | 20+ |

---

## 🎯 Common Tasks

### Task: Add a new page for SystemAdmin
```
1. Create controller method in SystemAdminController
   @RequestMapping(value = "/newPage", method = RequestMethod.GET)
   public ModelAndView newPage(...) {
       return new ModelAndView("systemAdmin/newPage");
   }

2. Create frontend file
   src/main/resources/templates/systemAdmin/newPage.html

3. Access via URL
   http://localhost:8085/anuppur/systemAdmin/newPage
```

### Task: Find where "All Users" page is handled
```
1. URL: /systemAdmin/manageusers
2. Controller: SystemAdminController
3. Method: manageUsersView()
4. Frontend: systemAdmin/manageusers.html
5. API calls: fetchUserList, fetchUserType, etc.
```

### Task: Find all pages for a role
```
Admin Role:
  - AdminController: /admin/home, /admin/dashboard, /admin/workBookAndPhotos
  - CommonController: /changepassword, /reports, /manageOngoingWorks, etc.

SystemAdmin Role:
  - SystemAdminController: /systemAdmin/home, /systemAdmin/manageusers, etc.
  - CommonController: /changepassword, /reports, /manageOngoingWorks, etc.

SuperAdmin Role:
  - SuperAdminController: /superAdmin/home, /superAdmin/manageusers, etc.
  - CommonController: /changepassword, /reports, /manageOngoingWorks, etc.
```

---

## 🔗 Request Flow Summary

```
User clicks link
    ↓
Browser sends HTTP request to URL
    ↓
Spring Boot DispatcherServlet routes to controller
    ↓
Controller method executes
    ↓
Controller returns ModelAndView with template name
    ↓
Thymeleaf renders HTML template
    ↓
HTML sent to browser
    ↓
AngularJS initializes and loads data via AJAX
    ↓
User sees rendered page with data
```

---

## 📁 File Organization

```
Backend:
  src/main/java/com/anuppur/controller/
    ├── LoginController.java
    ├── AdminController.java
    ├── SystemAdminController.java
    ├── SuperAdminController.java
    ├── CommonController.java
    └── MobileApiController.java

Frontend:
  src/main/resources/templates/
    ├── login.html
    ├── error/403.html
    ├── admin/
    │   ├── adminHome.html
    │   ├── dashboard.html
    │   └── workBookAndPhotos.html
    ├── systemAdmin/
    │   ├── systemAdminHome.html
    │   ├── manageusers.html
    │   ├── addUserForm.html
    │   └── ... (30+ more files)
    ├── superAdmin/
    │   ├── superAdminHome.html
    │   ├── dashboard.html
    │   └── manageusers.html
    ├── common/
    │   ├── changepassword.html
    │   ├── addNewWork.html
    │   ├── manageOngoingWorks.html
    │   ├── reports.html
    │   └── ... (50+ more files)
    └── fragments/
        ├── header.html
        └── footer.html
```

---

## 🎓 Key Concepts

### Controller
- Handles HTTP requests
- Checks authentication and authorization
- Executes business logic
- Returns ModelAndView with template name

### Frontend File (HTML Template)
- Contains HTML structure
- Uses Thymeleaf for server-side rendering
- Uses AngularJS for client-side interactivity
- Makes AJAX calls to backend APIs

### Request Mapping
- `@RequestMapping("/path")` - Maps URL path to controller method
- `method = RequestMethod.GET` - HTTP method (GET, POST, etc.)
- `value = "/endpoint"` - Specific endpoint within the base path

### ModelAndView
- `new ModelAndView("template/name")` - Returns template to render
- Template name maps to file path: `template/name` → `templates/template/name.html`

### Thymeleaf
- Server-side template engine
- Renders HTML before sending to browser
- Supports dynamic content and expressions

### AngularJS
- Client-side framework
- Handles routing and data binding
- Makes AJAX calls to fetch data
- Updates DOM dynamically

---

## 💡 Tips

1. **To find a controller**: Look at the URL path and match it to `@RequestMapping`
2. **To find a frontend file**: Look for `new ModelAndView("...")` in the controller
3. **To find an API endpoint**: Look for `@RequestMapping` with `produces = "application/json"`
4. **To understand the flow**: Follow the request from URL → Controller → Template → Frontend
5. **To add a new page**: Create controller method → Create frontend file → Access via URL

---

## 🔗 Related Documentation

- `CONTROLLER_TO_FRONTEND_MAPPING.md` - Complete detailed mapping
- `CONTROLLER_FRONTEND_VISUAL_MAP.md` - Visual diagrams and flow charts
- `PROJECT_FLOW_FRONTEND_BACKEND.md` - Complete project flow explanation

---

## ✅ Verification Checklist

- [ ] Understand the 6 main controllers
- [ ] Know how to find a controller for a URL
- [ ] Know how to find a frontend file for a controller
- [ ] Understand the request flow
- [ ] Know the file organization
- [ ] Can explain how a page is rendered

---

**Last Updated**: May 15, 2026
**Status**: Complete and Ready for Reference
