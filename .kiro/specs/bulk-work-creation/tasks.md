# Implementation Plan: Bulk Work Creation

## Overview

Implement the Excel-based bulk work creation workflow as a new `BulkWorkController` under `/systemAdmin/bulkWork`, backed by `BulkWorkService`. Uses Apache POI for template generation and parsing, existing JPA repositories for master data and persistence, and Thymeleaf for the upload page.

## Tasks

- [x] 1. Create DTOs and MasterDataCache
  - [x] 1.1 Create `BulkWorkRowBean` in `com.anuppur.bean`
    - All 25 fields as `String` plus `int rowNumber`; generate getters/setters
    - _Requirements: 1.3, 3.5_
  - [x] 1.2 Create `RowErrorBean` in `com.anuppur.bean`
    - Fields: `int rowNumber`, `String columnName`, `String errorMessage`; generate getters/setters
    - _Requirements: 6.1, 6.2_
  - [x] 1.3 Create `BulkUploadResultBean` in `com.anuppur.bean`
    - Fields: `int totalRows`, `int successCount`, `int failureCount`, `String message`, `List<RowErrorBean> errors`, `String validationReportBase64`; generate getters/setters
    - _Requirements: 6.1, 6.2, 6.3, 6.4_
  - [x] 1.4 Create `MasterDataCache` class in `com.anuppur.service` (package-private)
    - All name-to-id maps and `existingWorkNos` set as defined in the design document
    - _Requirements: 4.2, 4.3, 4.4, 4.5, 4.6, 4.7, 4.8, 4.9, 4.10, 4.14_

- [x] 2. Create `BulkWorkService` interface and skeleton impl
  - [x] 2.1 Create `BulkWorkService` interface in `com.anuppur.service`
    - Declare `byte[] generateTemplate()` and `BulkUploadResultBean processUpload(MultipartFile file, String username)`
    - _Requirements: 1.1, 3.1_
  - [x] 2.2 Create `BulkWorkServiceImpl` in `com.anuppur.service`
    - Annotate `@Service`, `@Transactional`; inject all existing JPA repositories for master data and `WorkRepository`
    - Leave method bodies as stubs for now
    - _Requirements: 5.1, 5.5_

- [x] 3. Implement `ExcelTemplateGenerator`
  - [x] 3.1 Create `ExcelTemplateGenerator` class in `com.anuppur.service` (not a Spring bean)
    - Method `byte[] generate(MasterDataCache cache)` using `XSSFWorkbook`
    - Sheet 1 (`Data Entry`): frozen header row with all 25 columns in the order from Requirement 1.3
    - Sheet 2 (`Reference Data`): one column per lookup populated from cache maps
    - _Requirements: 1.3, 1.4_
  - [x] 3.2 Wire `ExcelTemplateGenerator` into `BulkWorkServiceImpl.generateTemplate()`
    - Load `MasterDataCache` from repositories, call generator, return bytes
    - _Requirements: 1.1, 1.2_

- [x] 4. Implement `ExcelParser` and file-level guards
  - [x] 4.1 Create `ExcelParser` class in `com.anuppur.service` (not a Spring bean)
    - Method `List<BulkWorkRowBean> parse(InputStream is)` using `XSSFWorkbook`
    - Read sheet 0, skip header row, map each cell to the corresponding `BulkWorkRowBean` field by column index; all values as strings
    - _Requirements: 3.5_
  - [x] 4.2 Implement file-level guard checks in `BulkWorkServiceImpl.processUpload()`
    - File size > 5 MB: return error bean with message from Requirement 3.2
    - Unparseable file: catch exception, return error bean (Requirement 3.6)
    - Zero data rows: return error bean (Requirement 3.3)
    - More than 500 rows: return error bean (Requirement 3.4)
    - _Requirements: 3.2, 3.3, 3.4, 3.6_

- [x] 5. Implement `RowValidator`
  - [x] 5.1 Create `RowValidator` class in `com.anuppur.service` (not a Spring bean)
    - Method `List<RowErrorBean> validate(BulkWorkRowBean row, MasterDataCache cache)`
    - Rule 1: mandatory blank checks for Work Name, Financial Year Name, Scheme Name, Work Type Name, Work Category Name, Work Status Name, Implementation Agency Name, District Name, Block Name, Estimated Amount
    - Rule 2–10: lookup existence checks for each master data field using the cache maps
    - Rule 11: Estimated Amount must be positive numeric
    - Rule 12: multifunded fund amounts required when Multifunded Status is Y
    - Rule 13: Start Date format DD/MM/YYYY
    - Rule 14: Work No duplicate check against `existingWorkNos`
    - Use exact error message strings from Requirements 4.1–4.14
    - _Requirements: 4.1, 4.2, 4.3, 4.4, 4.5, 4.6, 4.7, 4.8, 4.9, 4.10, 4.11, 4.12, 4.13, 4.14_

- [x] 6. Implement `WorkMapper`
  - [x] 6.1 Create `WorkMapper` class in `com.anuppur.service` (not a Spring bean)
    - Method `Work map(BulkWorkRowBean row, MasterDataCache cache, String username)`
    - Resolve all name-based fields to IDs using cache maps per the field mapping table in the design document
    - Set `status = "A"` and `createdBy = username`
    - Parse numeric fields to `BigDecimal`; parse `startDate` string as-is
    - _Requirements: 5.1, 5.2, 5.3, 5.4_

- [x] 7. Implement `ValidationReportGenerator`
  - [x] 7.1 Create `ValidationReportGenerator` class in `com.anuppur.service` (not a Spring bean)
    - Method `String generateBase64Report(List<RowErrorBean> errors)` using `XSSFWorkbook`
    - Create a workbook with columns: Row Number, Column Name, Error Message
    - Write one row per `RowErrorBean`, serialize to bytes, Base64-encode and return
    - _Requirements: 6.2_

- [x] 8. Wire processing pipeline in `BulkWorkServiceImpl.processUpload()`
  - [x] 8.1 After guard checks, load `MasterDataCache` from all JPA repositories (name→id maps + existing work nos)
    - _Requirements: 4.2–4.14, 5.2_
  - [x] 8.2 Call `ExcelParser.parse()`, then for each row call `RowValidator.validate()`
    - Collect valid rows and error beans separately
    - _Requirements: 3.5, 4.1–4.14_
  - [x] 8.3 Call `WorkMapper.map()` for each valid row, then `WorkRepository.saveAll()` for the full valid list
    - _Requirements: 5.1, 5.5_
  - [x] 8.4 Assemble `BulkUploadResultBean`: set counts, message, and call `ValidationReportGenerator` if any errors
    - All-success message: "All [N] work records created successfully"
    - All-fail message: "No records were created. Please review the validation report"
    - _Requirements: 6.1, 6.2, 6.3, 6.4_
  - [x] 8.5 Add SLF4J logging of username, timestamp, total rows, success count, failure count
    - _Requirements: 6.5_

- [x] 9. Checkpoint — Ensure the service compiles and all guard-check paths return correct beans before proceeding to the controller.

- [x] 10. Create `BulkWorkController`
  - [x] 10.1 Create `BulkWorkController` in `com.anuppur.controller`
    - Annotate `@RestController`, `@RequestMapping("/systemAdmin/bulkWork")`, `@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")`
    - Extend `BaseController` (matching existing pattern)
    - Inject `BulkWorkService`
    - _Requirements: 1.5, 2.1_
  - [x] 10.2 Implement `GET /uploadPage` handler
    - Return `ModelAndView` for view name `systemAdmin/bulkWorkUpload`
    - _Requirements: 2.1_
  - [x] 10.3 Implement `GET /downloadTemplate` handler
    - Call `bulkWorkService.generateTemplate()`, return `ResponseEntity<byte[]>` with Content-Type `application/vnd.openxmlformats-officedocument.spreadsheetml.sheet` and `Content-Disposition: attachment; filename="BulkWorkCreationTemplate.xlsx"`
    - _Requirements: 1.1, 1.2_
  - [x] 10.4 Implement `POST /upload` handler
    - Accept `@RequestParam("file") MultipartFile file`
    - Retrieve authenticated username via `DMSUtil.getUserDetail().getUsername()`
    - Delegate to `bulkWorkService.processUpload(file, username)` and return `ResponseEntity<BulkUploadResultBean>`
    - _Requirements: 3.1, 5.3_

- [x] 11. Create Thymeleaf view `bulkWorkUpload.html`
  - [x] 11.1 Create `src/main/resources/templates/systemAdmin/bulkWorkUpload.html`
    - Extend the existing system admin layout/fragment
    - Display a link to `GET /systemAdmin/bulkWork/downloadTemplate` labelled "Download Template"
    - Display a file input (`accept=".xlsx"`) and a submit button labelled "Upload and Create Works"
    - Add client-side validation: if selected file does not end in `.xlsx`, show inline message "Only .xlsx files are accepted" and prevent submission
    - _Requirements: 2.2, 2.3, 2.4, 2.5_
  - [x] 11.2 Display upload result area in the view
    - After form submission (AJAX or page reload), show total rows, success count, failure count, and per-row error list from the JSON response
    - If `validationReportBase64` is present, render a download link for the validation report
    - _Requirements: 6.1, 6.2_

- [x] 12. Configure Spring Security and navigation
  - [x] 12.1 Add `/systemAdmin/bulkWork/**` to the permitted URL patterns for `ROLE_SYSTEM_ADMIN` in the existing Spring Security configuration class
    - _Requirements: 1.5, 2.1_
  - [x] 12.2 Add a navigation link "Bulk Work Upload" pointing to `/systemAdmin/bulkWork/uploadPage` on the System Admin home page (existing Thymeleaf template)
    - _Requirements: 2.1_

- [x] 13. Final checkpoint — Ensure the application compiles, the upload page loads, template download works, and a sample upload with mixed valid/invalid rows returns the correct JSON response.

- [x] 14. Add Excel in-cell dropdown validation to template
  - [x] 14.1 Update `ExcelTemplateGenerator` to add Apache POI `DataValidation` on each dropdown column in the `Data Entry` sheet
    - For each dropdown column (Type of Work, Financial Year, Sub Work Type, Executive Agency, District, Block, Gram Panchayat, Vidhan Sabha, Financial Head, Work Status, Work Priority), create a `DVConstraint` using `createExplicitListConstraint` with values from the corresponding Reference Data column
    - Apply `DataValidationHelper.createValidation()` on the full column range (rows 2–501) so user sees a dropdown arrow in each cell
    - Set `showErrorBox = true` with error title "Invalid Value" and message "Please select a value from the dropdown list"
    - Note: Apache POI explicit list constraint has a 255-char limit; if a lookup list exceeds this, use a formula-based constraint referencing the Reference Data sheet column instead (e.g., `'Reference Data'!$A$2:$A$100`)
  - [x] 14.2 Add `Is Tender` column dropdown validation with values `1` and `0`
    - Apply same `DataValidation` pattern on column index 5 (Is Tender) with explicit list `["1", "0"]`

- [x] 15. TS and AS document upload support
  - [x] 15.1 Add two new columns to `ExcelTemplateGenerator` Data Entry sheet: `TS Document Path` (col 23) and `AS Document Path` (col 24)
    - These columns will hold a relative file path or filename that the user pre-uploads to the server, OR leave blank if no document
    - Add a comment/note in the header cell explaining: "Enter the filename of the pre-uploaded document, or leave blank"
  - [x] 15.2 Add a new endpoint `POST /systemAdmin/bulkWork/uploadDocument` in `BulkWorkController`
    - Accepts a `MultipartFile` and returns a JSON `{ "fileName": "stored-uuid-filename.pdf" }`
    - Stores the file in the existing document upload directory (same path used by single work creation)
    - User uploads TS/AS files one by one before filling the Excel, then puts the returned filename in the Excel column
  - [x] 15.3 Add `tsDocumentPath` and `asDocumentPath` fields to `BulkWorkRowBean`
    - Update `ExcelParser` to read columns 23 and 24 into these fields
  - [x] 15.4 Update `WorkMapper` to set `tsDocument` and `asDocument` fields on the `Work` entity from `BulkWorkRowBean.tsDocumentPath` and `asDocumentPath` (if non-blank)
  - [x] 15.5 Update `bulkWorkUpload.html` to add a "Pre-upload Document" section above the main upload form
    - A file input for selecting a TS/AS document (PDF/image)
    - An "Upload Document" button that POSTs to `/systemAdmin/bulkWork/uploadDocument` via AJAX and displays the returned filename so the user can copy it into the Excel

- [x] 16. Final checkpoint — Verify template downloads with working in-cell dropdowns, document pre-upload returns a filename, and a full bulk upload with TS/AS filenames persists correctly.
