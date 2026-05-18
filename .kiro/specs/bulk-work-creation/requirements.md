# Requirements Document

## Introduction

The Bulk Work Creation module enables System Admin users of the Anuppur project to create multiple work records simultaneously via an Excel-based upload workflow. The module provides a downloadable predefined Excel template, accepts completed uploads, validates each row against master data and business rules, and persists valid work records into the `t_work` table using the existing JPA/Hibernate stack. Invalid rows are reported back to the user with row-level error messages without blocking the creation of valid rows.

---

## Glossary

- **System_Admin**: A logged-in user with the `ROLE_SYSTEM_ADMIN` role accessing the module at `/systemAdmin`.
- **Bulk_Upload_Module**: The Spring Boot feature described in this document, accessible from the System Admin home page.
- **Excel_Template**: A predefined `.xlsx` file containing column headers that map to required `Work` entity fields.
- **Upload_Processor**: The server-side component that reads the uploaded Excel file, validates rows, and persists valid records.
- **Work_Record**: A single row in the `t_work` database table, represented by the `Work` JPA entity.
- **Master_Data**: Reference data stored in lookup tables (`mst_financial_year`, `mst_schemes`, `mst_work_type`, `mst_work_category`, `mst_work_subtype`, `mst_implementation_agency`, `mst_district`, `mst_block`, `mst_gram_panchayat`, `mst_work_head`, `mst_work_priority`, `mst_financial_head`, `mst_vidhan_sabha`).
- **Validation_Report**: An Excel file returned to the System_Admin listing each failed row number, column name, and error description.
- **Row**: A single data entry in the uploaded Excel file, corresponding to one Work_Record.

---

## Requirements

### Requirement 1: Excel Template Download

**User Story:** As a System_Admin, I want to download a predefined Excel template, so that I can fill in work details in the correct format before uploading.

#### Acceptance Criteria

1. THE Bulk_Upload_Module SHALL expose a GET endpoint at `/systemAdmin/bulkWork/downloadTemplate` that returns an `.xlsx` file named `BulkWorkCreationTemplate.xlsx`.
2. WHEN the System_Admin requests the template download, THE Bulk_Upload_Module SHALL return the file with HTTP Content-Type `application/vnd.openxmlformats-officedocument.spreadsheetml.sheet` and a `Content-Disposition: attachment` header.
3. THE Excel_Template SHALL contain exactly one header row with the following columns in order:
   - `Work Name` (text, mandatory)
   - `Work No` (text, optional)
   - `Financial Year Name` (text, mandatory — e.g., "2024-25")
   - `Scheme Name` (text, mandatory)
   - `Work Type Name` (text, mandatory)
   - `Work Category Name` (text, mandatory)
   - `Work Sub Type Name` (text, optional)
   - `Work Status Name` (text, mandatory)
   - `Work Priority Name` (text, optional)
   - `Work Head Name` (text, optional)
   - `Financial Head Name` (text, optional)
   - `Implementation Agency Name` (text, mandatory)
   - `District Name` (text, mandatory)
   - `Block Name` (text, mandatory)
   - `Gram Panchayat Name` (text, optional)
   - `Vidhan Sabha Name` (text, optional)
   - `Estimated Amount` (numeric, mandatory)
   - `Amount Released Till Date` (numeric, optional)
   - `Allocated Amount` (numeric, optional)
   - `Start Date` (text, format `DD/MM/YYYY`, optional)
   - `Multifunded Status` (text, values: `Y` or `N`, optional)
   - `Fund By State` (numeric, conditional — required when `Multifunded Status` is `Y`)
   - `Fund By NHM` (numeric, conditional — required when `Multifunded Status` is `Y`)
   - `Fund By ECRP2` (numeric, conditional — required when `Multifunded Status` is `Y`)
   - `Fund By Others` (numeric, conditional — required when `Multifunded Status` is `Y`)
4. THE Excel_Template SHALL include a second sheet named `Reference Data` containing the valid name values for each Master_Data lookup column, so that users can select correct names.
5. WHERE the System_Admin is not authenticated, THE Bulk_Upload_Module SHALL redirect the request to the login page.

---

### Requirement 2: Bulk Upload Page

**User Story:** As a System_Admin, I want a dedicated upload page in the System Admin section, so that I can access the template download link and submit the completed Excel file.

#### Acceptance Criteria

1. THE Bulk_Upload_Module SHALL expose a GET endpoint at `/systemAdmin/bulkWork/uploadPage` that renders a Thymeleaf view `systemAdmin/bulkWorkUpload`.
2. THE `systemAdmin/bulkWorkUpload` view SHALL display a link to download the Excel_Template.
3. THE `systemAdmin/bulkWorkUpload` view SHALL display a file input control that accepts only `.xlsx` files.
4. THE `systemAdmin/bulkWorkUpload` view SHALL display a submit button labelled "Upload and Create Works".
5. WHEN the System_Admin selects a file that does not have the `.xlsx` extension, THE Bulk_Upload_Module SHALL display an inline validation message "Only .xlsx files are accepted" before form submission.

---

### Requirement 3: File Upload and Parsing

**User Story:** As a System_Admin, I want to upload the completed Excel file, so that the system can read and process the work data I have entered.

#### Acceptance Criteria

1. THE Bulk_Upload_Module SHALL expose a POST endpoint at `/systemAdmin/bulkWork/upload` that accepts a `multipart/form-data` request containing the uploaded `.xlsx` file.
2. WHEN the uploaded file size exceeds 5 MB, THE Upload_Processor SHALL return an error response with the message "File size must not exceed 5 MB".
3. WHEN the uploaded file contains no data rows (only the header row or is empty), THE Upload_Processor SHALL return an error response with the message "The uploaded file contains no data rows".
4. WHEN the uploaded file contains more than 500 rows, THE Upload_Processor SHALL return an error response with the message "A maximum of 500 rows are allowed per upload".
5. WHEN the uploaded file is a valid `.xlsx` file with at least one data row, THE Upload_Processor SHALL parse each row and proceed to validation.
6. IF the uploaded file cannot be parsed as a valid `.xlsx` file, THEN THE Upload_Processor SHALL return an error response with the message "The uploaded file is not a valid Excel (.xlsx) file".

---

### Requirement 4: Row-Level Data Validation

**User Story:** As a System_Admin, I want the system to validate each row in the uploaded file, so that only correct and complete data is saved as work records.

#### Acceptance Criteria

1. WHEN a row has an empty value in a mandatory column (`Work Name`, `Financial Year Name`, `Scheme Name`, `Work Type Name`, `Work Category Name`, `Work Status Name`, `Implementation Agency Name`, `District Name`, `Block Name`, `Estimated Amount`), THE Upload_Processor SHALL mark that row as invalid with the message "Column '[Column Name]' is mandatory".
2. WHEN a row contains a `Financial Year Name` value that does not match any enabled record in `mst_financial_year`, THE Upload_Processor SHALL mark that row as invalid with the message "Financial Year '[value]' not found".
3. WHEN a row contains a `Scheme Name` value that does not match any enabled record in `mst_schemes`, THE Upload_Processor SHALL mark that row as invalid with the message "Scheme '[value]' not found".
4. WHEN a row contains a `Work Type Name` value that does not match any enabled record in `mst_work_type`, THE Upload_Processor SHALL mark that row as invalid with the message "Work Type '[value]' not found".
5. WHEN a row contains a `Work Category Name` value that does not match any enabled record in `mst_work_category`, THE Upload_Processor SHALL mark that row as invalid with the message "Work Category '[value]' not found".
6. WHEN a row contains a `Work Status Name` value that does not match any enabled record in `mst_work_status`, THE Upload_Processor SHALL mark that row as invalid with the message "Work Status '[value]' not found".
7. WHEN a row contains an `Implementation Agency Name` value that does not match any enabled record in `mst_implementation_agency`, THE Upload_Processor SHALL mark that row as invalid with the message "Implementation Agency '[value]' not found".
8. WHEN a row contains a `District Name` value that does not match any enabled record in `mst_district`, THE Upload_Processor SHALL mark that row as invalid with the message "District '[value]' not found".
9. WHEN a row contains a `Block Name` value that does not match any enabled record in `mst_block`, THE Upload_Processor SHALL mark that row as invalid with the message "Block '[value]' not found".
10. WHEN a row contains a non-empty `Gram Panchayat Name` value that does not match any enabled record in `mst_gram_panchayat`, THE Upload_Processor SHALL mark that row as invalid with the message "Gram Panchayat '[value]' not found".
11. WHEN a row contains a non-numeric or negative value in `Estimated Amount`, THE Upload_Processor SHALL mark that row as invalid with the message "Estimated Amount must be a positive numeric value".
12. WHEN a row has `Multifunded Status` set to `Y` and any of `Fund By State`, `Fund By NHM`, `Fund By ECRP2`, or `Fund By Others` is empty or non-numeric, THE Upload_Processor SHALL mark that row as invalid with the message "Fund amounts are required when Multifunded Status is Y".
13. WHEN a row contains a `Start Date` value that does not match the format `DD/MM/YYYY`, THE Upload_Processor SHALL mark that row as invalid with the message "Start Date must be in DD/MM/YYYY format".
14. WHEN a row contains a `Work No` value that already exists in the `t_work` table, THE Upload_Processor SHALL mark that row as invalid with the message "Work No '[value]' already exists".

---

### Requirement 5: Work Record Persistence

**User Story:** As a System_Admin, I want valid rows to be automatically saved as work records, so that I do not have to create each work manually.

#### Acceptance Criteria

1. WHEN a row passes all validations in Requirement 4, THE Upload_Processor SHALL create a new `Work` entity and persist it to the `t_work` table using the existing JPA repository.
2. THE Upload_Processor SHALL resolve each name-based column value to its corresponding database ID before persisting the `Work` entity (e.g., `Financial Year Name` → `financial_year` ID).
3. THE Upload_Processor SHALL set the `created_by` field of each persisted `Work` entity to the username of the currently authenticated System_Admin.
4. THE Upload_Processor SHALL set the `status` field of each persisted `Work` entity to `"A"` (Active) by default.
5. WHEN all rows in the uploaded file are processed, THE Upload_Processor SHALL persist all valid rows in a single database transaction, so that a failure in one row's persistence does not affect other valid rows.
6. WHILE processing a batch upload, THE Upload_Processor SHALL process invalid rows by skipping persistence and collecting errors, without rolling back already-persisted valid rows.

---

### Requirement 6: Upload Result Response

**User Story:** As a System_Admin, I want to see a clear summary after upload, so that I know how many records were created and which rows had errors.

#### Acceptance Criteria

1. WHEN the upload processing is complete, THE Bulk_Upload_Module SHALL return a JSON response containing: total rows processed, count of successfully created records, count of failed rows, and a list of row-level errors.
2. WHEN at least one row fails validation, THE Bulk_Upload_Module SHALL include in the response a downloadable Validation_Report as a Base64-encoded `.xlsx` attachment, listing row number, column name, and error message for each failed row.
3. WHEN all rows are valid and successfully persisted, THE Bulk_Upload_Module SHALL return a success response with the message "All [N] work records created successfully".
4. WHEN all rows fail validation, THE Bulk_Upload_Module SHALL return an error response with the message "No records were created. Please review the validation report" and include the Validation_Report.
5. THE Bulk_Upload_Module SHALL log each upload attempt with the System_Admin username, timestamp, total rows, success count, and failure count using the existing SLF4J logger.
