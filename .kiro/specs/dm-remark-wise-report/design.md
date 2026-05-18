# Design Document: DM Remark-wise Report

## Overview

DM Remark-wise Report ek summary report page hai jo `department_remarks` table ke data ko **Issue Type** (DepartmentMaster ka `name`) aur **Department Name** (Implementation Agency ka `impl_agency_name`) ke combination ke hisaab se group karke active works ka count dikhata hai.

Har row mein "Total Works" ek clickable hyperlink hoga jo existing works list page par navigate karega — filtered by `departmentMasterId` aur `implAgencyId`. Page par do multiselect dropdown filters honge: Issue Type aur Department Name (Impl Agency).

Yeh feature existing `departmentWiseWorksReport` aur `photoUpdateReport` patterns ko follow karta hai — same Spring MVC + Thymeleaf + AngularJS stack.

### Data Flow Summary

```
department_remarks.department_master_id → mst_department.id → mst_department.name  = Issue Type
department_remarks.work_id → t_work.implementation_agency → mst_implementation_agency.impl_agency_name = Department Name
```

---

## Architecture

```mermaid
graph TD
    Browser["Browser (AngularJS)"]
    Controller["CommonController.java"]
    Service["CommonServiceImpl.java"]
    Repo["DepartmentRemarksRepository.java"]
    DB[(MySQL DB)]

    Browser -->|GET /dmRemarkWiseReport| Controller
    Browser -->|GET /fetchDmRemarkWiseReport?deptMasterIds=&implAgencyIds=| Controller
    Controller --> Service
    Service --> Repo
    Repo -->|Native SQL Query| DB

    DB -->|Object[]| Repo
    Repo --> Service
    Service -->|List<DmRemarkWiseReportRowBean>| Controller
    Controller -->|JSON| Browser
```

**Component Responsibilities:**

- `CommonController` — HTTP endpoints, request param parsing, error handling
- `CommonServiceImpl` — filter logic, query routing (4 variants), Object[] → Bean mapping
- `DepartmentRemarksRepository` — 4 native SQL queries (all, by dept, by agency, by both)
- `DmRemarkWiseReportRowBean` — data transfer object
- `dmRemarkWiseReport.html` — Thymeleaf template with AngularJS bindings

---

## Components and Interfaces

### Bean: `DmRemarkWiseReportRowBean.java`

```java
package com.anuppur.bean;

public class DmRemarkWiseReportRowBean {
    private Long departmentMasterId;   // mst_department.id
    private String issueType;          // mst_department.name
    private Long implAgencyId;         // mst_implementation_agency.id
    private String implAgencyName;     // mst_implementation_agency.impl_agency_name
    private long totalWorks;           // COUNT(DISTINCT dr.work_id)
    // getters + setters
}
```

### Repository: `DepartmentRemarksRepository.java` (new queries)

4 query variants based on filter combination:

| Method | Filters |
|--------|---------|
| `fetchDmRemarkWiseReportAll()` | No filter |
| `fetchDmRemarkWiseReportByDept(deptMasterIds)` | Issue Type only |
| `fetchDmRemarkWiseReportByAgency(implAgencyIds)` | Dept Name only |
| `fetchDmRemarkWiseReportByDeptAndAgency(deptMasterIds, implAgencyIds)` | Both |

### Service: `CommonService` + `CommonServiceImpl`

New method signature:
```java
List<DmRemarkWiseReportRowBean> getDmRemarkWiseReport(List<Long> deptMasterIds, List<Long> implAgencyIds);
```

### Controller: `CommonController.java`

Two new endpoints:
- `GET /dmRemarkWiseReport` → `ModelAndView("common/dmRemarkWiseReport")`
- `GET /fetchDmRemarkWiseReport` → `ResponseEntity<List<DmRemarkWiseReportRowBean>>`

### Frontend: `dmRemarkWiseReport.html`

- Bootstrap 3 + bootstrap-select (selectpicker) multiselect dropdowns
- AngularJS scope variables: `issueTypeOptions`, `implAgencyOptions`, `dmRemarkWiseReportRows`
- Existing endpoints reused for dropdown data:
  - `GET /fetchDepartmentMaster` → Issue Type filter
  - `GET /fetchImplAgency` → Department Name filter

---

## Data Models

### Core SQL Query (base pattern)

```sql
SELECT
    dm.id                          AS departmentMasterId,
    dm.name                        AS issueType,
    mia.id                         AS implAgencyId,
    mia.impl_agency_name           AS implAgencyName,
    COUNT(DISTINCT dr.work_id)     AS totalWorks
FROM department_remarks dr
JOIN mst_department dm
    ON dm.id = dr.department_master_id
JOIN t_work w
    ON w.id = dr.work_id
    AND w.status = 'Active'
JOIN mst_implementation_agency mia
    ON mia.id = w.implementation_agency
WHERE dr.enabled = 1
GROUP BY
    dr.department_master_id,
    dm.name,
    w.implementation_agency,
    mia.impl_agency_name
ORDER BY
    dm.name,
    mia.impl_agency_name
```

**Filter variants:**
- `deptMasterIds` filter: `AND dr.department_master_id IN (:deptMasterIds)`
- `implAgencyIds` filter: `AND mia.id IN (:implAgencyIds)`
- Both filters: both conditions added

### Object[] Column Mapping (index → field)

| Index | Column | Bean Field |
|-------|--------|------------|
| 0 | `departmentMasterId` | `departmentMasterId` (Long) |
| 1 | `issueType` | `issueType` (String) |
| 2 | `implAgencyId` | `implAgencyId` (Long) |
| 3 | `implAgencyName` | `implAgencyName` (String) |
| 4 | `totalWorks` | `totalWorks` (long) |

### Table Relationships

```
department_remarks
  ├── department_master_id → mst_department.id (Issue Type source)
  └── work_id → t_work.id
                    └── implementation_agency → mst_implementation_agency.id (Dept Name source)
```

---

## Correctness Properties

*A property is a characteristic or behavior that should hold true across all valid executions of a system — essentially, a formal statement about what the system should do. Properties serve as the bridge between human-readable specifications and machine-verifiable correctness guarantees.*

### Property 1: Aggregation produces unique groups with correct count

*For any* set of `department_remarks` records (with `enabled=1` and linked to active works), the aggregation result SHALL contain exactly one row per unique `(departmentMasterId, implAgencyId)` combination, and each row's `totalWorks` SHALL equal `COUNT(DISTINCT work_id)` for that combination.

**Validates: Requirements 1.2, 5.4**

### Property 2: Field mapping correctness

*For any* result row returned by `getDmRemarkWiseReport`, the `issueType` field SHALL match `mst_department.name` for the corresponding `departmentMasterId`, and `implAgencyName` SHALL match `mst_implementation_agency.impl_agency_name` for the corresponding `implAgencyId`.

**Validates: Requirements 1.3, 1.4**

### Property 3: No zero-count rows in result

*For any* result set returned by `getDmRemarkWiseReport`, every row SHALL have `totalWorks > 0`. Combinations with no active works SHALL NOT appear.

**Validates: Requirements 1.5**

### Property 4: Filter correctness — applied filters constrain results

*For any* non-empty list of `deptMasterIds` and/or `implAgencyIds` passed as filters, every row in the result SHALL have its `departmentMasterId` in the provided `deptMasterIds` list (if provided) AND its `implAgencyId` in the provided `implAgencyIds` list (if provided).

**Validates: Requirements 3.3, 4.3, 5.3**

### Property 5: Empty filter returns all results

*For any* database state, calling `getDmRemarkWiseReport(null, null)` SHALL return the same set of rows as calling it with all possible `deptMasterIds` and `implAgencyIds` explicitly listed.

**Validates: Requirements 3.4, 4.4, 5.2**

### Property 6: Dropdown data — only enabled departments returned

*For any* set of `mst_department` records, the department filter dropdown endpoint SHALL return only records where `enabled = 1`, sorted alphabetically by `name`.

**Validates: Requirements 3.2, 6.1, 6.3**

### Property 7: Issue Type dropdown — unique values sorted alphabetically

*For any* set of `department_remarks` records, the Issue Type dropdown endpoint SHALL return all unique `departmentRemarkName` values with no duplicates, sorted alphabetically.

**Validates: Requirements 4.2, 6.2, 6.4**

---

## Error Handling

| Scenario | Handling |
|----------|----------|
| Invalid `deptMasterIds` / `implAgencyIds` (non-numeric) | `NumberFormatException` caught in controller → HTTP 400 with error message |
| Database error during aggregation | `Exception` caught in controller → HTTP 500 with error message |
| No data found for filters | Empty `List` returned → HTTP 200 with `[]` |
| `null` filter params | `convertToList()` returns empty list → "no filter" query variant used |

`convertToList()` utility (existing in `BaseController`) handles comma-separated string → `List<Long>` conversion.

---

## Testing Strategy

### Unit Tests (Example-based)

- `DmRemarkWiseReportRowBean` — getter/setter correctness
- `CommonServiceImpl.getDmRemarkWiseReport()` — verify correct query variant called for each filter combination (all 4 cases), with mocked repository
- `CommonController.fetchDmRemarkWiseReport()` — verify HTTP 400 on invalid params, HTTP 500 on service exception, HTTP 200 on success
- Frontend — verify column headers present, hyperlink URL contains correct params

### Property-Based Tests

PBT library: **jqwik** (Java property-based testing framework)

Each property test runs minimum 100 iterations.

**Property 1 test** — `Feature: dm-remark-wise-report, Property 1: Aggregation produces unique groups with correct count`
- Generate: random list of `(departmentMasterId, workId, implAgencyId, enabled)` tuples
- Call: `getDmRemarkWiseReport(null, null)` with mocked repo returning generated data
- Assert: no duplicate `(departmentMasterId, implAgencyId)` pairs; each `totalWorks` = distinct `workId` count for that group

**Property 3 test** — `Feature: dm-remark-wise-report, Property 3: No zero-count rows`
- Generate: random valid dataset
- Assert: all rows have `totalWorks > 0`

**Property 4 test** — `Feature: dm-remark-wise-report, Property 4: Filter correctness`
- Generate: random dataset + random subset of deptMasterIds and implAgencyIds
- Assert: all result rows have `departmentMasterId` in filter list and `implAgencyId` in filter list

**Property 6 test** — `Feature: dm-remark-wise-report, Property 6: Only enabled departments`
- Generate: random list of departments with mixed `enabled` values
- Assert: result contains only `enabled=1` departments, sorted by name

**Property 7 test** — `Feature: dm-remark-wise-report, Property 7: Unique sorted issue types`
- Generate: random list of `department_remarks` with repeated `departmentRemarkName` values
- Assert: result has no duplicates and is alphabetically sorted

### Integration Tests

- End-to-end: `GET /fetchDmRemarkWiseReport` with real DB returns valid JSON
- Verify existing `GET /fetchDepartmentMaster` and `GET /fetchImplAgency` endpoints work for dropdown population
