# Implementation Plan: Photo Update Report

## Overview

Implement the Photo Update Report feature by adding a new bean, repository query, service method, controller endpoints, Thymeleaf template, AngularJS functions, and sidebar navigation entry. The implementation follows the same patterns as `departmentWiseWorksReport`.

## Tasks

- [x] 1. Create `PhotoUpdateReportRowBean`
  - Create `src/main/java/com/anuppur/bean/PhotoUpdateReportRowBean.java` with fields: `departmentId` (Long), `departmentName` (String), `workName` (String), `totalWorks` (long), `areaOfficerName` (String), `days` (int)
  - Add standard getters and setters for all fields
  - _Requirements: 1.1, 6.4_

- [x] 2. Add native query methods to `DocumentUploadWorkProgressRepository`
  - [x] 2.1 Add `fetchPhotoUpdateReportAll()` native query method
    - Use correlated subquery for `totalWorks` (MySQL 5.x compatible — no window functions)
    - Join `t_work`, `department_remarks`, `mst_department`, `users` (via `t_work.userAssignee`), `document_upload_workprogress_details`
    - Filter: `t_work.status = 'Active'`, `created_date >= DATE_SUB(CURRENT_DATE, INTERVAL 14 DAY)`
    - Use `CONCAT(IFNULL(u.first_name,''), ' ', IFNULL(u.last_name,''))` for area officer name
    - `ORDER BY d.name ASC`
    - _Requirements: 1.3, 1.4, 1.5, 1.6, 4.1, 5.1, 5.2, 6.2, 6.5_
  - [x] 2.2 Add `fetchPhotoUpdateReportByDept(@Param("departmentIds") List<Long> departmentIds)` native query method
    - Same query as above with additional `AND d.id IN (:departmentIds)` clause
    - _Requirements: 3.3, 6.1, 6.3_

- [x] 3. Add `getPhotoUpdateReport` to `CommonService` and `CommonServiceImpl`
  - [x] 3.1 Add method signature to `CommonService` interface
    - `List<PhotoUpdateReportRowBean> getPhotoUpdateReport(List<Long> departmentIds);`
    - _Requirements: 6.1_
  - [x] 3.2 Implement `getPhotoUpdateReport` in `CommonServiceImpl`
    - If `departmentIds` is null or empty, call `fetchPhotoUpdateReportAll()`; otherwise call `fetchPhotoUpdateReportByDept(departmentIds)`
    - Map each `Object[]` row to `PhotoUpdateReportRowBean`: index 0 → `departmentId`, 1 → `departmentName`, 2 → `workName`, 3 → `totalWorks`, 4 → `areaOfficerName`, 5 → `days`
    - Wrap in try/catch: log and re-throw as `RuntimeException` on error
    - _Requirements: 5.3, 5.4, 6.2, 6.3, 6.4_
  - [x]* 3.3 Write unit tests for `getPhotoUpdateReport` in `CommonServiceImpl`
    - Test: empty `departmentIds` → calls `fetchPhotoUpdateReportAll()`
    - Test: non-empty `departmentIds` → calls `fetchPhotoUpdateReportByDept()`
    - Test: repository throws exception → `RuntimeException` propagated
    - Test: `Object[]` row mapping — all 6 fields correctly assigned
    - _Requirements: 5.4, 6.2, 6.3_

- [x] 4. Add controller endpoints in `CommonController`
  - [x] 4.1 Add `GET /photoUpdateReport` page-view endpoint
    - Return `ModelAndView` pointing to `"common/photoUpdateReport"`
    - Follow the same pattern as `/departmentWiseWorksReport` endpoint (Line 649)
    - _Requirements: 1.2_
  - [x] 4.2 Add `GET /fetchPhotoUpdateReport` data API endpoint
    - Accept optional `@RequestParam String departmentIds`
    - Parse via existing `convertToList()` helper
    - Call `commonService.getPhotoUpdateReport(deptIdList)` and return `ResponseEntity.ok(result)`
    - Catch `NumberFormatException` → HTTP 400 with `{"errorMessage": "..."}`
    - Catch `Exception` → HTTP 500 with `{"errorMessage": "..."}`
    - _Requirements: 1.7, 3.3, 5.4, 6.1, 6.2, 6.3_

- [x] 5. Checkpoint — Ensure backend compiles and all tests pass
  - Ensure all tests pass, ask the user if questions arise.

- [x] 6. Create Thymeleaf template `common/photoUpdateReport.html`
  - Create `src/main/resources/templates/common/photoUpdateReport.html` as a partial view (same structure as `departmentWiseWorksReport.html`)
  - Include Bootstrap 3, bootstrap-select CSS/JS, and initialize `selectpicker` for `#photoUpdateDeptFilter`
  - Breadcrumb: Home → Photo Update Report, with Back button (`doTheBack()`)
  - Filter section: single multiselect `#photoUpdateDeptFilter` with `data-live-search="true"` and `data-actions-box="true"`, populated via `data-ng-repeat="dept in photoUpdateDeptOptions"`
  - Search and Reset buttons calling `fetchPhotoUpdateReportData()` and `resetPhotoUpdateFilters()` respectively
  - `data-ng-init="loadPhotoUpdateDeptOptions()"` on the card body
  - Table columns: S.No, Department Name, Work Name, Total Works, Area Officer Name, Days
  - `Total Works` cell: `<a class="count-link" data-ng-href="{{getPhotoUpdateDetailUrl(row)}}">{{row.totalWorks}}</a>`
  - Empty state row: `<tr data-ng-if="!photoUpdateReportRows || photoUpdateReportRows.length === 0"><td colspan="6">No data available</td></tr>`
  - _Requirements: 1.1, 1.7, 2.1, 2.2, 2.3, 2.4, 3.1, 3.5_

- [x] 7. Add AngularJS functions in `CommonController.js`
  - [x] 7.1 Add scope variables and `loadPhotoUpdateDeptOptions` function
    - Initialize `$scope.photoUpdateReportRows = []`
    - `loadPhotoUpdateDeptOptions`: call `getDepartmentMaster`, assign to `$scope.photoUpdateDeptOptions`, refresh selectpicker, then call `fetchPhotoUpdateReportData()`
    - _Requirements: 1.2, 3.2_
  - [x] 7.2 Add `fetchPhotoUpdateReportData` function
    - Read selected values from `$('#photoUpdateDeptFilter').val()`
    - Build `params.departmentIds` as comma-joined string if any selected
    - `$http.get('fetchPhotoUpdateReport', { params: params })` → assign `res.data` to `$scope.photoUpdateReportRows`
    - On error: log and set `$scope.photoUpdateReportRows = []`
    - _Requirements: 3.3, 3.4, 6.1, 6.2, 6.3_
  - [x] 7.3 Add `resetPhotoUpdateFilters` and `getPhotoUpdateDetailUrl` functions
    - `resetPhotoUpdateFilters`: clear `#photoUpdateDeptFilter` selectpicker, call `fetchPhotoUpdateReportData()`
    - `getPhotoUpdateDetailUrl(row)`: return `'#/manageOngoingWorks?departmentId=' + row.departmentId`
    - _Requirements: 2.1, 2.2, 2.4, 3.4_

- [x] 8. Add sidebar navigation entry in `systemAdminHome.html`
  - Add `<li>` nav item after the `departmentWiseWorksReport` entry
  - Use same role guard: `th:if="${roleName=='ROLE_SYSTEM_ADMIN' || roleName=='ROLE_DM' || roleName=='ROLE_DEPARTMENT'}"`
  - Link: `href="#photoUpdateReport"`, label: `Photo Update Report`
  - _Requirements: 1.2_

- [x] 9. Final checkpoint — Ensure all tests pass
  - Ensure all tests pass, ask the user if questions arise.

## Notes

- Tasks marked with `*` are optional and can be skipped for faster MVP
- The `totalWorks` correlated subquery must be MySQL 5.x compatible (no `OVER (PARTITION BY ...)`)
- `t_work.userAssignee` is the FK to `users` table (not `user_id`)
- `document_upload_workprogress_details.created_date` is the upload date column
- The department filter uses `DATE_SUB(CURRENT_DATE, INTERVAL 14 DAY)` to cover the last 15 days inclusive
- Property tests (Properties 1–8 from design) are covered by the unit tests in task 3.3
