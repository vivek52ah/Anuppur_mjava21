# Requirements Document

## Introduction

The Department-wise Works Report is a summary report page that displays work counts grouped by department and financial year. Each count (Total Works, Completed, Ongoing, Not Started) is a hyperlink that navigates to a filtered detailed works list (similar to the existing manageOngoingWorks page). The page includes multiselect dropdown filters for Department Name and Financial Year, following the same UI pattern used in manageOngoingWorks.

## Glossary

- **Report_Page**: The Department-wise Works Report summary page showing aggregated work counts per department.
- **Department**: An organizational unit stored in `mst_department`, identified by name and ID.
- **Financial_Year**: A fiscal year period stored in the financial year master table (e.g., "2024-25").
- **Work**: A project/work record associated with a department and financial year, having a status.
- **Work_Status**: The current state of a work — Completed, Ongoing (in-progress), or Not Started.
- **Detail_Report**: The filtered works list page (based on manageOngoingWorks) showing individual work records for a selected department, financial year, and status.
- **Multiselect_Dropdown**: A bootstrap-select (`selectpicker`) dropdown allowing selection of multiple values simultaneously, as used in manageOngoingWorks.
- **Filter**: A user-applied constraint (Department Name or Financial Year) that narrows the rows shown in the Report_Page.

## Requirements

### Requirement 1: Display Department-wise Works Summary Table

**User Story:** As a user, I want to see a summary table of works grouped by department and financial year, so that I can quickly understand the work status distribution across departments.

#### Acceptance Criteria

1. THE Report_Page SHALL display a table with the following columns in order: S.No, Department Name, Financial Year, Total Works, Completed Work, Ongoing Work, Not Started Work.
2. WHEN the Report_Page loads, THE Report_Page SHALL populate each row with one department's aggregated work counts for the latest available financial year.
3. THE Report_Page SHALL display the Department Name as plain text in the Department Name column.
4. THE Report_Page SHALL display the financial year label (e.g., "2024-25") in the Financial Year column.
5. WHEN no works exist for a department in the selected financial year, THE Report_Page SHALL display 0 for Total Works, Completed Work, Ongoing Work, and Not Started Work.

### Requirement 2: Hyperlink Navigation to Filtered Detail Report

**User Story:** As a user, I want to click on any work count to see the detailed list of those works, so that I can drill down into specific work records.

#### Acceptance Criteria

1. THE Report_Page SHALL render the Total Works count as a hyperlink for each row.
2. THE Report_Page SHALL render the Completed Work count as a hyperlink for each row.
3. THE Report_Page SHALL render the Ongoing Work count as a hyperlink for each row.
4. THE Report_Page SHALL render the Not Started Work count as a hyperlink for each row.
5. WHEN a user clicks the Total Works hyperlink, THE Detail_Report SHALL display all works for that department and financial year regardless of status.
6. WHEN a user clicks the Completed Work hyperlink, THE Detail_Report SHALL display only works with Completed status for that department and financial year.
7. WHEN a user clicks the Ongoing Work hyperlink, THE Detail_Report SHALL display only works with Ongoing (in-progress) status for that department and financial year.
8. WHEN a user clicks the Not Started Work hyperlink, THE Detail_Report SHALL display only works with Not Started status for that department and financial year.
9. WHEN a count is 0, THE Report_Page SHALL still render the value as a hyperlink.

### Requirement 3: Department Name Multiselect Filter

**User Story:** As a user, I want to filter the report by one or more departments, so that I can focus on specific departments.

#### Acceptance Criteria

1. THE Report_Page SHALL display a Department Name multiselect dropdown filter using the bootstrap-select (`selectpicker`) component, consistent with the manageOngoingWorks page.
2. WHEN the Report_Page loads, THE Report_Page SHALL populate the Department Name filter with all available departments fetched from the `fetchAllDepartment` endpoint.
3. WHEN a user selects one or more departments and applies the filter, THE Report_Page SHALL display only rows matching the selected departments.
4. WHEN no department is selected in the filter, THE Report_Page SHALL display rows for all departments.
5. THE Department_Name_Filter SHALL support live search within the dropdown options.

### Requirement 4: Financial Year Multiselect Filter

**User Story:** As a user, I want to filter the report by one or more financial years, so that I can compare work status across different years.

#### Acceptance Criteria

1. THE Report_Page SHALL display a Financial Year multiselect dropdown filter using the bootstrap-select (`selectpicker`) component, consistent with the manageOngoingWorks page.
2. WHEN the Report_Page loads, THE Report_Page SHALL populate the Financial Year filter with all available financial years fetched from the `fetchFinancialYear` endpoint.
3. WHEN a user selects one or more financial years and applies the filter, THE Report_Page SHALL display only rows matching the selected financial years.
4. WHEN no financial year is selected in the filter, THE Report_Page SHALL default to showing data for the latest financial year.
5. THE Financial_Year_Filter SHALL support live search within the dropdown options.

### Requirement 5: Data Aggregation Backend API

**User Story:** As a developer, I want a backend API that returns department-wise work counts, so that the Report_Page can display accurate aggregated data.

#### Acceptance Criteria

1. THE Report_Page SHALL fetch aggregated data from a dedicated backend endpoint that accepts optional `departmentIds` and `financialYearIds` filter parameters.
2. WHEN the backend endpoint receives a request with no filter parameters, THE Backend_API SHALL return aggregated counts for all departments for the latest financial year.
3. WHEN the backend endpoint receives `departmentIds` and/or `financialYearIds` parameters, THE Backend_API SHALL return aggregated counts filtered to the specified departments and financial years.
4. THE Backend_API SHALL return for each row: department name, financial year label, total work count, completed work count, ongoing work count, and not-started work count.
5. IF a database error occurs during aggregation, THEN THE Backend_API SHALL return an appropriate HTTP error response with a descriptive message.

### Requirement 6: Detail Report Filtering via URL Parameters

**User Story:** As a developer, I want the Detail_Report page to accept department, financial year, and status filter parameters via URL, so that hyperlinks from the Report_Page open a pre-filtered view.

#### Acceptance Criteria

1. THE Detail_Report SHALL accept `departmentId`, `financialYearId`, and `workStatus` as URL query parameters.
2. WHEN `departmentId` is provided as a URL parameter, THE Detail_Report SHALL pre-filter the works list to the specified department.
3. WHEN `financialYearId` is provided as a URL parameter, THE Detail_Report SHALL pre-filter the works list to the specified financial year.
4. WHEN `workStatus` is provided as a URL parameter with value "COMPLETED", THE Detail_Report SHALL display only completed works.
5. WHEN `workStatus` is provided as a URL parameter with value "ONGOING", THE Detail_Report SHALL display only ongoing works.
6. WHEN `workStatus` is provided as a URL parameter with value "NOT_STARTED", THE Detail_Report SHALL display only not-started works.
7. WHEN `workStatus` is not provided or is empty, THE Detail_Report SHALL display all works for the given department and financial year.
