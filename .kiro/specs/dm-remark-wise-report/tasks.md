# Implementation Plan: DM Remark-wise Report

## Overview

Existing `departmentWiseWorksReport` aur `photoUpdateReport` patterns follow karte hue Spring MVC + Thymeleaf + AngularJS stack mein DM Remark-wise Report implement karna hai. Bean → Repository → Service → Controller → HTML — yahi sequence follow hogi.

## Tasks

- [x] 1. Create `DmRemarkWiseReportRowBean.java`
  - `src/main/java/com/anuppur/bean/DmRemarkWiseReportRowBean.java` create karo
  - Fields: `departmentMasterId` (Long), `issueType` (String), `implAgencyId` (Long), `implAgencyName` (String), `totalWorks` (long)
  - `DepartmentWiseReportRowBean.java` pattern follow karo — plain getters/setters, no Lombok
  - _Requirements: 1.3, 1.4, 5.4_

- [x] 2. Add 4 native SQL queries in `DepartmentRemarksRepository.java`
  - [x] 2.1 `fetchDmRemarkWiseReportAll()` — no filter variant add karo
    - Base SQL: `department_remarks` JOIN `mst_department` JOIN `t_work` (status='Active') JOIN `mst_implementation_agency`, WHERE `dr.enabled = 1`, GROUP BY `(department_master_id, dm.name, w.implementation_agency, mia.impl_agency_name)`, ORDER BY `dm.name, mia.impl_agency_name`
    - SELECT: `dm.id, dm.name, mia.id, mia.impl_agency_name, COUNT(DISTINCT dr.work_id)`
    - Returns `List<Object[]>`, `nativeQuery = true`
    - _Requirements: 5.2_
  - [x] 2.2 `fetchDmRemarkWiseReportByDept(List<Long> deptMasterIds)` — Issue Type filter only
    - Base query mein `AND dr.department_master_id IN (:deptMasterIds)` add karo
    - `@Param("deptMasterIds")` annotation use karo
    - _Requirements: 5.3_
  - [x] 2.3 `fetchDmRemarkWiseReportByAgency(List<Long> implAgencyIds)` — Dept Name filter only
    - Base query mein `AND mia.id IN (:implAgencyIds)` add karo
    - `@Param("implAgencyIds")` annotation use karo
    - _Requirements: 5.3_
  - [x] 2.4 `fetchDmRemarkWiseReportByDeptAndAgency(List<Long> deptMasterIds, List<Long> implAgencyIds)` — both filters
    - Base query mein dono conditions add karo
    - _Requirements: 5.3_

- [x] 3. Add method signature in `CommonService.java`
  - `List<DmRemarkWiseReportRowBean> getDmRemarkWiseReport(List<Long> deptMasterIds, List<Long> implAgencyIds);` add karo
  - `DmRemarkWiseReportRowBean` import add karo
  - Existing `getDepartmentWiseReport` aur `getPhotoUpdateReport` signatures ke paas rakhna
  - _Requirements: 5.1, 5.2, 5.3_

- [x] 4. Implement `getDmRemarkWiseReport` in `CommonServiceImpl.java`
  - [x] 4.1 Method implement karo with 4-variant routing logic
    - `deptMasterIds` aur `implAgencyIds` dono empty → `fetchDmRemarkWiseReportAll()` call karo
    - Sirf `deptMasterIds` non-empty → `fetchDmRemarkWiseReportByDept()` call karo
    - Sirf `implAgencyIds` non-empty → `fetchDmRemarkWiseReportByAgency()` call karo
    - Dono non-empty → `fetchDmRemarkWiseReportByDeptAndAgency()` call karo
    - _Requirements: 5.2, 5.3_
  - [x] 4.2 `Object[]` → `DmRemarkWiseReportRowBean` mapping implement karo
    - Index mapping: `[0]`=departmentMasterId (Long), `[1]`=issueType (String), `[2]`=implAgencyId (Long), `[3]`=implAgencyName (String), `[4]`=totalWorks (long)
    - Existing `getDepartmentWiseReport` mein `convertToList()` pattern dekho aur same style follow karo
    - _Requirements: 1.3, 1.4, 5.4_
  - [ ]* 4.3 Write unit tests for `getDmRemarkWiseReport`
    - Mock `DepartmentRemarksRepository`, verify correct query variant called for each of 4 filter combinations
    - Verify `Object[]` → Bean mapping correctness
    - _Requirements: 5.2, 5.3_
  - [ ]* 4.4 Write property test for aggregation correctness
    - **Property 1: Aggregation produces unique groups with correct count**
    - **Validates: Requirements 1.2, 5.4**
  - [ ]* 4.5 Write property test for filter correctness
    - **Property 4: Filter correctness — applied filters constrain results**
    - **Validates: Requirements 3.3, 4.3, 5.3**

- [x] 5. Checkpoint — Ensure all tests pass
  - Ensure all tests pass, ask the user if questions arise.

- [x] 6. Add 2 endpoints in `CommonController.java`
  - [x] 6.1 `GET /dmRemarkWiseReport` — page view endpoint add karo
    - `ModelAndView("common/dmRemarkWiseReport")` return karo
    - Existing `/departmentWiseWorksReport` aur `/photoUpdateReport` endpoints ka pattern follow karo
    - _Requirements: 1.1_
  - [x] 6.2 `GET /fetchDmRemarkWiseReport` — data fetch endpoint add karo
    - `@RequestParam(required = false) String deptMasterIds` aur `String implAgencyIds` accept karo
    - `convertToList()` (BaseController utility) se parse karo
    - `commonService.getDmRemarkWiseReport(...)` call karo
    - `ResponseEntity<List<DmRemarkWiseReportRowBean>>` return karo
    - `NumberFormatException` → HTTP 400, `Exception` → HTTP 500
    - `DmRemarkWiseReportRowBean` import add karo
    - Existing `fetchDepartmentWiseReport` endpoint ka exact pattern follow karo
    - _Requirements: 5.1, 5.5_
  - [ ]* 6.3 Write unit tests for controller endpoints
    - HTTP 400 on invalid params, HTTP 500 on service exception, HTTP 200 on success test karo
    - _Requirements: 5.5_

- [x] 7. Create `dmRemarkWiseReport.html` Thymeleaf template
  - [x] 7.1 `src/main/resources/templates/common/dmRemarkWiseReport.html` create karo
    - `photoUpdateReport.html` structure follow karo — same Bootstrap 3 + bootstrap-select + AngularJS pattern
    - Breadcrumb: "DM Remark-wise Report"
    - _Requirements: 1.1_
  - [x] 7.2 Do multiselect dropdown filters add karo
    - Issue Type filter: `id="issueTypeFilter"`, `data-ng-init="loadIssueTypeOptions()"`, `GET /fetchDepartmentMaster` se populate karo
    - Department Name filter: `id="deptNameFilter"`, `data-ng-init="loadDeptNameOptions()"`, `GET /fetchImplAgency` se populate karo
    - Dono mein `data-live-search="true"` aur `data-actions-box="true"` add karo
    - _Requirements: 3.1, 3.2, 3.5, 4.1, 4.2, 4.5_
  - [x] 7.3 Summary table add karo
    - Columns: S.No., Issue Type, Department Name, Total Works
    - `data-ng-repeat="row in dmRemarkWiseReportRows track by $index"` use karo
    - Total Works column: `<a data-ng-href="{{getDmRemarkDetailUrl(row)}}">{{row.totalWorks}}</a>` — `departmentMasterId` aur `implAgencyId` params pass karo
    - Empty state: `data-ng-if="!dmRemarkWiseReportRows || dmRemarkWiseReportRows.length === 0"` row add karo
    - _Requirements: 1.1, 1.2, 1.5, 1.6, 2.1, 2.3_
  - [x] 7.4 AngularJS controller functions add karo (inline `<script>` mein)
    - `loadIssueTypeOptions()` — `GET /fetchDepartmentMaster` call karo, `$scope.issueTypeOptions` set karo
    - `loadDeptNameOptions()` — `GET /fetchImplAgency` call karo, `$scope.deptNameOptions` set karo
    - `fetchDmRemarkWiseReportData()` — selected filter values collect karo, `GET /fetchDmRemarkWiseReport?deptMasterIds=&implAgencyIds=` call karo, `$scope.dmRemarkWiseReportRows` set karo
    - `resetDmRemarkFilters()` — filters aur table data clear karo, `selectpicker('deselectAll')` call karo
    - `getDmRemarkDetailUrl(row)` — `/manageOngoingWorks?departmentId=X&implAgencyId=Y` URL return karo
    - _Requirements: 2.2, 2.4, 3.3, 3.4, 4.3, 4.4_

- [x] 8. Final Checkpoint — Ensure all tests pass
  - Ensure all tests pass, ask the user if questions arise.

## Notes

- Tasks marked with `*` are optional and can be skipped for faster MVP
- Property tests ke liye jqwik library use karo (design document mein specified)
- `convertToList()` utility `BaseController` mein already available hai — reuse karo
- Dropdown data ke liye existing `/fetchDepartmentMaster` aur `/fetchImplAgency` endpoints reuse karo, naye endpoints banana zaroori nahi
  