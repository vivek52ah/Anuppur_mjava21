# Implementation Plan: Department-wise Works Report

## Overview

Implement the Department-wise Works Report feature by adding a new bean, service method, controller endpoints, and Thymeleaf template, then modifying the existing `manageOngoingWorks` detail page to accept URL-based pre-filtering.

## Tasks

- [x] 1. Create `DepartmentWiseReportRowBean`
  - Add `DepartmentWiseReportRowBean.java` in `com.anuppur.bean` with fields: `departmentId`, `departmentName`, `financialYearId`, `financialYearName`, `totalWorks`, `completedWorks`, `ongoingWorks`, `notStartedWorks` plus getters/setters
  - _Requirements: 5.4_

- [x] 2. Implement aggregation query and service method
  - [x] 2.1 Add `getDepartmentWiseReport(List<Long> departmentIds, List<Long> financialYearIds)` to `CommonService` interface
    - _Requirements: 5.1, 5.2, 5.3_
  - [x] 2.2 Implement the method in `CommonServiceImpl` using a native SQL query with `GROUP BY d.id, fy.id` and CASE-based conditional COUNTs for each work status flag
    - When `financialYearIds` is null/empty, restrict to `MAX(id)` from `mst_financial_year WHERE enabled = 1`
    - Parse raw `Object[]` results into `DepartmentWiseReportRowBean` instances
    - Catch `Exception`, log it, and rethrow as a runtime exception for the controller to handle
    - _Requirements: 5.2, 5.3, 5.4, 5.5_
  - [ ]* 2.3 Write property test for count decomposition invariant (Property 1)
    - **Property 1: `completedWorks + ongoingWorks + notStartedWorks <= totalWorks` for every returned row**
    - **Validates: Requirements 1.1, 5.4**
  - [ ]* 2.4 Write property test for department filter narrows result (Property 3)
    - **Property 3: every row's `departmentId` is contained in the supplied filter list**
    - **Validates: Requirements 3.3, 5.3**
  - [ ]* 2.5 Write property test for financial year filter narrows result (Property 4)
    - **Property 4: every row's `financialYearId` is contained in the supplied filter list**
    - **Validates: Requirements 4.3, 5.3**
  - [ ]* 2.6 Write property test for default financial year is latest (Property 5)
    - **Property 5: when no `financialYearIds` supplied, all rows have `financialYearId` equal to max enabled FY id**
    - **Validates: Requirements 1.2, 4.4, 5.2**

- [x] 3. Add controller endpoints in `CommonController`
  - [x] 3.1 Add `GET /departmentWiseWorksReport` page-view mapping that returns a `ModelAndView` for the new template
    - _Requirements: 1.1_
  - [x] 3.2 Add `GET /fetchDepartmentWiseReport` data API mapping that accepts optional comma-separated `departmentIds` and `financialYearIds` params, converts them via the existing `convertToList()` helper, calls `getDepartmentWiseReport()`, and returns `List<DepartmentWiseReportRowBean>` as JSON
    - Return HTTP 400 on `NumberFormatException` from `convertToList()`
    - Return HTTP 500 with `{"errorMessage": "..."}` on service exceptions
    - _Requirements: 5.1, 5.2, 5.3, 5.5_

- [x] 4. Checkpoint — ensure bean, service, and controller compile cleanly
  - Ensure all tests pass, ask the user if questions arise.

- [x] 5. Modify `manageOngoingWorks` to accept URL filter parameters
  - [x] 5.1 Add `departmentId` as an accepted `@RequestParam` (optional) on the existing `GET /manageOngoingWorks` controller mapping and pass it through to the model
    - _Requirements: 6.1, 6.2_
  - [x] 5.2 Update `manageOngoingWorks.html` to read `departmentId`, `financialYearId`, and `workStatus` from the URL query string on page load (via AngularJS `$location.search()` or hidden inputs) and pre-populate the corresponding filter controls before the initial data fetch
    - _Requirements: 6.1, 6.2, 6.3, 6.4, 6.5, 6.6, 6.7_
  - [ ]* 5.3 Write property test for detail report filter round-trip (Property 7)
    - **Property 7: every work record returned by `fetchWorksList` matches the `departmentId`, `financialYearId`, and `workStatus` passed as parameters**
    - **Validates: Requirements 6.2, 6.3, 6.4, 6.5, 6.6**

- [x] 6. Create `departmentWiseWorksReport.html` Thymeleaf template
  - [x] 6.1 Scaffold the page following the `manageOngoingWorks.html` structure: include the same Bootstrap 3 / bootstrap-select / AngularJS / DataTables dependencies and the shared layout fragments
    - _Requirements: 1.1, 3.1, 4.1_
  - [x] 6.2 Add the Department Name `selectpicker` multiselect dropdown populated via the existing `fetchAllDepartment` endpoint, with live-search enabled
    - _Requirements: 3.1, 3.2, 3.5_
  - [x] 6.3 Add the Financial Year `selectpicker` multiselect dropdown populated via the existing `fetchFinancialYear` endpoint, with live-search enabled; default selection to the latest year on load
    - _Requirements: 4.1, 4.2, 4.4, 4.5_
  - [x] 6.4 Implement the AngularJS controller that calls `/fetchDepartmentWiseReport` with the selected filter values and binds the response to the table
    - When no department is selected, send no `departmentIds` param (returns all departments)
    - When no financial year is selected, send no `financialYearIds` param (backend defaults to latest FY)
    - _Requirements: 1.2, 3.3, 3.4, 4.3, 4.4_
  - [x] 6.5 Render the summary table with columns S.No, Department Name, Financial Year, Total Works, Completed Work, Ongoing Work, Not Started Work; each count cell must be an `<a>` tag with `href` pointing to `/manageOngoingWorks?departmentId=...&financialYearId=...&workStatus=...`
    - Total Works link uses no `workStatus` param (shows all)
    - Zero counts must still render as hyperlinks
    - _Requirements: 1.1, 1.3, 1.4, 1.5, 2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 2.7, 2.8, 2.9_
  - [ ]* 6.6 Write unit test verifying zero-count cells still render as `<a>` elements (Property 2)
    - **Property 2: a count of 0 still produces an anchor element with correct href**
    - **Validates: Requirements 2.9**
  - [ ]* 6.7 Write unit test verifying hyperlink URLs encode correct `departmentId`, `financialYearId`, and `workStatus` parameters (Property 6)
    - **Property 6: href for each status cell contains the correct query parameters for that row**
    - **Validates: Requirements 2.5, 2.6, 2.7, 2.8, 6.1**

- [x] 7. Wire navigation entry point
  - Add a menu link or navigation entry for `/departmentWiseWorksReport` in the appropriate sidebar/nav fragment so the page is reachable
  - _Requirements: 1.1_

- [x] 8. Final checkpoint — ensure all tests pass
  - Ensure all tests pass, ask the user if questions arise.

## Notes

- Tasks marked with `*` are optional and can be skipped for a faster MVP
- Each task references specific requirements for traceability
- Property tests use jqwik; annotate each with `// Feature: department-wise-works-report, Property N: <text>`
- The `convertToList()` helper already exists in `CommonController` — reuse it for parsing comma-separated ID params
- Work status flags: `flag=1` Completed, `flag=2` Ongoing, `flag=3` Not Started (confirm against live data before finalising the query)
