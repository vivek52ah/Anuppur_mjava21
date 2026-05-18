# Requirements Document

## Introduction

The Photo Update Report is a summary report page that displays works which had photo uploads (work progress images) in the last 15 days. Each row represents a department with its total count of active works that received photo uploads in that window. The total works count is a hyperlink that navigates to the existing `manageOngoingWorks` page pre-filtered by department. The page includes a multiselect dropdown filter for Department Name, following the same UI pattern used in `departmentWiseWorksReport`.

## Glossary

- **Report_Page**: The Photo Update Report summary page showing departments with recent photo upload activity.
- **Department**: An organizational unit stored in `mst_department`, linked to works via the `department_remarks` table.
- **Work**: An active project record in `t_work` where `status = 'Active'`.
- **Photo_Upload**: A work progress image record in `document_upload_workprogress_details` with a `created_date` timestamp.
- **Last_15_Days**: The date range from (current date − 14 days) to current date, inclusive.
- **Area_Officer**: The user assigned to a work, identified by `t_work.user_id` (FK → `users` table).
- **Days**: The number of days elapsed since the most recent photo upload for a given work, calculated as `CURRENT_DATE − MAX(created_date)`.
- **Total_Works**: The count of distinct active works for a department that had at least one photo upload in the last 15 days.
- **Multiselect_Dropdown**: A bootstrap-select (`selectpicker`) dropdown allowing selection of multiple values simultaneously.
- **Detail_Report**: The filtered works list page (`manageOngoingWorks`) showing individual work records pre-filtered by department.

## Requirements

### Requirement 1: Display Photo Update Report Summary Table

**User Story:** As a user, I want to see a summary table of departments whose works had photo uploads in the last 15 days, so that I can monitor photo update activity across departments.

#### Acceptance Criteria

1. THE Report_Page SHALL display a table with the following columns in order: S.No, Department Name, Work Name, Total Works, Area Officer Name, Days.
2. WHEN the Report_Page loads, THE Report_Page SHALL automatically fetch and display data for all departments with photo upload activity in the last 15 days.
3. THE Report_Page SHALL only include works where `t_work.status = 'Active'`.
4. THE Report_Page SHALL derive Department Name from `mst_department.name` via the `department_remarks` table linked to `t_work`.
5. THE Report_Page SHALL display the Area Officer Name from the `users` table using `t_work.user_id` (concatenation of `first_name` and `last_name`).
6. THE Report_Page SHALL display Days as the number of days since the most recent photo upload for each work, calculated as `DATEDIFF(CURRENT_DATE, MAX(created_date))` from `document_upload_workprogress_details`.
7. WHEN no works have photo uploads in the last 15 days, THE Report_Page SHALL display an empty table with a "No data available" message.

### Requirement 2: Total Works Hyperlink Navigation

**User Story:** As a user, I want to click on the Total Works count to see the list of those works, so that I can drill down into the specific works for a department.

#### Acceptance Criteria

1. THE Report_Page SHALL render the Total Works count as a hyperlink for each department row.
2. WHEN a user clicks the Total Works hyperlink, THE Detail_Report SHALL open the `manageOngoingWorks` page pre-filtered by the selected department.
3. WHEN Total Works count is 0, THE Report_Page SHALL still render the value as a hyperlink.
4. THE Detail_Report URL SHALL include `departmentId` as a query parameter matching the department of the clicked row.

### Requirement 3: Department Name Multiselect Filter

**User Story:** As a user, I want to filter the report by one or more departments, so that I can focus on specific departments.

#### Acceptance Criteria

1. THE Report_Page SHALL display a Department Name multiselect dropdown filter using the bootstrap-select (`selectpicker`) component, consistent with the `departmentWiseWorksReport` page.
2. WHEN the Report_Page loads, THE Report_Page SHALL populate the Department Name filter with all available departments fetched from the `getDepartmentMaster` endpoint.
3. WHEN a user selects one or more departments and clicks Search, THE Report_Page SHALL display only rows matching the selected departments.
4. WHEN no department is selected in the filter, THE Report_Page SHALL display rows for all departments with photo upload activity in the last 15 days.
5. THE Department_Name_Filter SHALL support live search within the dropdown options.

### Requirement 4: Active Works Filter

**User Story:** As a developer, I want the report to only include active works, so that completed or inactive works do not appear in the photo update report.

#### Acceptance Criteria

1. THE Backend_API SHALL only return works where `t_work.status = 'Active'`.
2. WHEN a work's status is not 'Active', THE Backend_API SHALL exclude that work from all counts and rows.

### Requirement 5: Data Source — Photo Uploads in Last 15 Days

**User Story:** As a developer, I want the report data to be sourced from work progress photo uploads in the last 15 days, so that the report reflects recent upload activity.

#### Acceptance Criteria

1. THE Backend_API SHALL source photo upload data from the `document_upload_workprogress_details` table using the `created_date` column.
2. THE Backend_API SHALL include only records where `created_date >= CURRENT_DATE - 14 DAYS` (i.e., within the last 15 days inclusive).
3. THE Backend_API SHALL group results by department, counting distinct active works that had at least one photo upload in the last 15 days.
4. IF a database error occurs during data retrieval, THEN THE Backend_API SHALL return an appropriate HTTP error response with a descriptive message.

### Requirement 6: Backend API for Photo Update Report Data

**User Story:** As a developer, I want a backend API endpoint that returns photo update report data, so that the Report_Page can display accurate aggregated data.

#### Acceptance Criteria

1. THE Backend_API SHALL expose a GET endpoint (e.g., `/fetchPhotoUpdateReport`) that accepts an optional `departmentIds` comma-separated filter parameter.
2. WHEN the endpoint receives a request with no `departmentIds` parameter, THE Backend_API SHALL return data for all departments with qualifying photo uploads.
3. WHEN the endpoint receives a `departmentIds` parameter, THE Backend_API SHALL return data filtered to the specified departments only.
4. THE Backend_API SHALL return for each row: department ID, department name, work name, total works count, area officer name, and days since last photo upload.
5. THE Backend_API SHALL return results ordered by department name ascending.
