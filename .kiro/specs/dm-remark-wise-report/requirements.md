# Requirements Document

## Introduction

DM Remark-wise Report ek summary report page hai jo works ko Issue Type (DepartmentRemarks ka `departmentRemarkName`) aur Department Name (DepartmentMaster ka `name`) ke combination ke hisaab se group karke dikhata hai. Har row mein ek "Total Works" count hoga jo ek hyperlink hoga — click karne par us specific Issue Type + Department combination ki sirf active works ki detail list khulegi. Page par do multiselect dropdown filters honge: Department Name aur Issue Type.

## Glossary

- **Report_Page**: DM Remark-wise Report ka summary page jo Issue Type aur Department ke combination ke hisaab se active works ka count dikhata hai.
- **Issue_Type**: `department_remarks` table ke `department_remark_name` field ki unique value — yeh ek category/type hai jo kisi work par lagaya gaya remark type batata hai.
- **Department**: Implementation Agency — `mst_department` table mein stored organizational unit, `name` field se identify hota hai.
- **Active_Work**: Woh work record jiska `enabled = 1` ho (ya active status ho) `department_remarks` table mein.
- **Total_Works**: Ek specific Issue Type + Department combination ke liye active works ka count, jo hyperlink ke roop mein display hota hai.
- **Detail_Report**: Filtered works list page jo ek specific Issue Type + Department combination ki active works dikhata hai.
- **Multiselect_Dropdown**: Bootstrap-select (`selectpicker`) component jo multiple values simultaneously select karne deta hai.
- **DepartmentRemarks**: `department_remarks` table ki entity jisme `departmentRemarkName`, `depertmentMasterId`, `workId`, aur `enabled` fields hain.
- **DepartmentMaster**: `mst_department` table ki entity jisme department ka `name` aur `enabled` field hai.

## Requirements

### Requirement 1: DM Remark-wise Summary Table Display

**User Story:** As a user, I want to see a summary table grouped by Issue Type and Department Name with active work counts, so that I can quickly identify how many active works each department has for each issue type.

#### Acceptance Criteria

1. THE Report_Page SHALL display a table with the following columns in order: S.No., Issue Type, Department Name, Total Works.
2. WHEN the Report_Page loads, THE Report_Page SHALL populate each row with one unique combination of Issue Type and Department Name along with the count of active works for that combination.
3. THE Report_Page SHALL display the `departmentRemarkName` value from `DepartmentRemarks` in the Issue Type column.
4. THE Report_Page SHALL display the `name` value from `DepartmentMaster` in the Department Name column.
5. WHEN no active works exist for a particular Issue Type + Department combination, THE Report_Page SHALL NOT display a row for that combination.
6. THE Report_Page SHALL display S.No. as a sequential serial number starting from 1 for each row in the current filtered result.

### Requirement 2: Total Works Hyperlink aur Detail Report Navigation

**User Story:** As a user, I want to click on the Total Works count to see the detailed list of active works for that Issue Type and Department combination, so that I can drill down into specific work records.

#### Acceptance Criteria

1. THE Report_Page SHALL render the Total Works count as a clickable hyperlink for each row.
2. WHEN a user clicks the Total Works hyperlink, THE Detail_Report SHALL display only active works (`enabled = 1`) for that specific Issue Type and Department combination.
3. WHEN the Total Works count is greater than 0, THE Report_Page SHALL render it as an active hyperlink.
4. THE Detail_Report SHALL receive the selected `departmentRemarkName` (Issue Type) and `departmentId` as parameters to filter the works list.

### Requirement 3: Department Name Multiselect Filter

**User Story:** As a user, I want to filter the report by one or more departments, so that I can focus on specific departments only.

#### Acceptance Criteria

1. THE Report_Page SHALL display a Department Name multiselect dropdown filter using the bootstrap-select (`selectpicker`) component.
2. WHEN the Report_Page loads, THE Report_Page SHALL populate the Department Name filter with all active departments fetched from the `DepartmentMaster` table (where `enabled = 1`).
3. WHEN a user selects one or more departments and applies the filter, THE Report_Page SHALL display only rows matching the selected departments.
4. WHEN no department is selected in the filter, THE Report_Page SHALL display rows for all departments.
5. THE Department_Name_Filter SHALL support live search within the dropdown options.

### Requirement 4: Issue Type Multiselect Filter

**User Story:** As a user, I want to filter the report by one or more issue types, so that I can focus on specific remark categories.

#### Acceptance Criteria

1. THE Report_Page SHALL display an Issue Type multiselect dropdown filter using the bootstrap-select (`selectpicker`) component.
2. WHEN the Report_Page loads, THE Report_Page SHALL populate the Issue Type filter with all unique `departmentRemarkName` values fetched from the `DepartmentRemarks` table.
3. WHEN a user selects one or more issue types and applies the filter, THE Report_Page SHALL display only rows matching the selected issue types.
4. WHEN no issue type is selected in the filter, THE Report_Page SHALL display rows for all issue types.
5. THE Issue_Type_Filter SHALL support live search within the dropdown options.

### Requirement 5: Backend API — Report Data Aggregation

**User Story:** As a developer, I want a backend API that returns Issue Type + Department wise active work counts, so that the Report_Page can display accurate aggregated data.

#### Acceptance Criteria

1. THE Report_Page SHALL fetch aggregated data from a dedicated backend endpoint that accepts optional `departmentIds` (list) and `issueTypes` (list of `departmentRemarkName` strings) as filter parameters.
2. WHEN the backend endpoint receives a request with no filter parameters, THE Backend_API SHALL return aggregated active work counts for all Issue Type + Department combinations.
3. WHEN the backend endpoint receives `departmentIds` and/or `issueTypes` filter parameters, THE Backend_API SHALL return aggregated counts filtered to the specified departments and issue types.
4. THE Backend_API SHALL return for each row: `departmentRemarkName` (Issue Type), department name, and active work count (count of distinct `workId` where `enabled = 1`).
5. IF a database error occurs during aggregation, THEN THE Backend_API SHALL return an appropriate HTTP error response with a descriptive message.

### Requirement 6: Backend API — Filter Dropdown Data

**User Story:** As a developer, I want backend endpoints that provide dropdown data for Department Name and Issue Type filters, so that the frontend can populate the multiselect dropdowns.

#### Acceptance Criteria

1. THE Backend_API SHALL provide an endpoint to fetch all active departments (`enabled = 1`) from `DepartmentMaster`, returning `id` and `name` for each department.
2. THE Backend_API SHALL provide an endpoint to fetch all unique `departmentRemarkName` values from `DepartmentRemarks` table for the Issue Type filter dropdown.
3. WHEN the department list endpoint is called, THE Backend_API SHALL return departments sorted alphabetically by `name`.
4. WHEN the issue type list endpoint is called, THE Backend_API SHALL return unique `departmentRemarkName` values sorted alphabetically.
5. IF no data is found for either dropdown, THEN THE Backend_API SHALL return an empty list with HTTP 200 status.

### Requirement 7: Detail Report — Active Works List

**User Story:** As a user, I want to see the list of active works when I click on a Total Works hyperlink, so that I can review individual work details for that Issue Type and Department.

#### Acceptance Criteria

1. THE Detail_Report SHALL accept `departmentRemarkName` (Issue Type) and `departmentId` as filter parameters.
2. WHEN `departmentRemarkName` and `departmentId` are provided, THE Detail_Report SHALL display only active works (`enabled = 1` in `department_remarks`) matching both parameters.
3. THE Detail_Report SHALL display relevant work details for each active work in the filtered list.
4. IF no active works are found for the given parameters, THEN THE Detail_Report SHALL display an appropriate empty state message.
