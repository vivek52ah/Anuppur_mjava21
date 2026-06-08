# Sanction Details - Tender Fields Guide

## Overview
The **Sanction Details** section (Step 3) in the Edit Work page contains all tender-related information. These fields are visible based on the **Work Status** selected.

## Location in Application
- **Page**: Edit Work
- **Tab**: Step 3 - "Sanction Details"
- **File**: `src/main/resources/templates/common/work/editTender.html`
- **Included in**: `src/main/resources/templates/common/editWork.html` (line 1227)

## Tender Date Fields and Their Visibility

### 1. **Tender Called Date**
- **Field Name**: `tenderCalledDate`
- **Visible When**: Work Status = **3** (Tender Called)
- **Required**: Yes (marked with *)
- **Location in HTML**: Line 873-896 in editTender.html
- **Input Type**: Date picker (DD/MM/YYYY format)
- **Disabled When**: 
  - Work Status is not 3
  - Tender Called Date is null
  - Tender Received Date is null

### 2. **Tender Received Date**
- **Field Name**: `tenderReceivedDate`
- **Visible When**: Work Status = **4** (Tender Received)
- **Required**: Yes (marked with *)
- **Location in HTML**: Line 926-959 in editTender.html
- **Input Type**: Date picker (DD/MM/YYYY format)
- **Disabled When**: 
  - Work Status is not 4
  - Tender Called Date is null
  - Tender Received Date is null

### 3. **LoA Issued Date** (Letter of Award)
- **Field Name**: `loaIssuedDate`
- **Visible When**: Work Status = **7** (LoA Issued)
- **Required**: Yes (marked with *)
- **Location in HTML**: Line 996-1019 in editTender.html
- **Input Type**: Date picker (DD/MM/YYYY format)
- **Disabled When**: 
  - Work Status is not 7
  - Tender Called Date is null
  - Tender Received Date is null
  - tenderUpdated = '8'

### 4. **Work Order Issued Date**
- **Field Name**: `workOrderDate`
- **Visible When**: Work Status = **8** (Work Order Issued)
- **Required**: Yes (marked with *) - Only if isTenders = 1
- **Location in HTML**: Line 269-301 in editTender.html
- **Input Type**: Date picker (DD/MM/YYYY format)
- **Disabled When**: 
  - Work Status is not 8
  - Tender Called Date is null
  - Tender Received Date is null
  - LoA Issued Date is null
  - isTenders != 1

### 5. **Re-Tender Date**
- **Field Name**: `reTenderDate`
- **Visible When**: Work Status = **14** (Re-Tender)
- **Required**: Yes (marked with *)
- **Location in HTML**: Line 960-982 in editTender.html
- **Input Type**: Date picker (DD/MM/YYYY format)
- **Disabled When**: Work Status is not 14

## Work Status Codes

| Status ID | Status Name | Fields Visible |
|-----------|-------------|-----------------|
| 3 | Tender Called | Tender Called Date |
| 4 | Tender Received | Tender Received Date |
| 7 | LoA Issued | LoA Issued Date |
| 8 | Work Order Issued | Work Order Date, BG/FDR Start Date, Security Deposit |
| 14 | Re-Tender | Re-Tender Date, Re-Tender Count |

## Additional Fields in Sanction Details

### Security Deposit (Visible for Status 8)
- **Field Name**: `secureAmtStatus`
- **Type**: Radio button (BG or FDR)
- **Default**: BG (selected)
- **Location**: Line 1030-1040 in editTender.html

### BG/FDR Start Date (Visible for Status 8)
- **Field Name**: `startDate`
- **Type**: Date picker
- **Location**: Line 1043-1080 in editTender.html
- **Disabled When**: 
  - Tender Called Date is null
  - Tender Received Date is null
  - LoA Issued Date is null

### Re-Tender Count (Visible for Status 8)
- **Field Name**: `count`
- **Type**: Number input
- **Disabled**: Yes (read-only)
- **Location**: Line 246-249 in editTender.html

## File Upload Fields

### 1. Work Order File Upload
- **Label**: "Upload Work Order"
- **Required**: Yes (if isTenders = 1)
- **Location**: Line 310-347 in editTender.html
- **Field Name**: `ldTdfFile`

### 2. Technical Drawing File Upload
- **Label**: "Upload Technical Drawing"
- **Required**: Yes (if isTenders = 1)
- **Location**: Line 350-398 in editTender.html
- **Field Name**: `dTTTFile`

### 3. Tender Upload File
- **Label**: "Upload Tender"
- **Required**: Yes (if isTenders = 1)
- **Location**: Line 401-449 in editTender.html
- **Field Name**: `ldTULFile`

### 4. Upload Agreement File
- **Label**: "Upload Agreement"
- **Required**: Yes (if isTenders = 1)
- **Location**: Line 452-500 in editTender.html
- **Field Name**: `ldUAFile`

## Visibility Logic

The fields are controlled by Angular `data-ng-show` and `data-ng-if` directives:

```html
<!-- Tender Called Date - Only visible when workStatusId = 3 -->
<div data-ng-show="workDataTender.workStatusId=='3'">
    <!-- Tender Called Date field -->
</div>

<!-- Tender Received Date - Only visible when workStatusId = 4 -->
<div data-ng-show="workDataTender.workStatusId=='4'">
    <!-- Tender Received Date field -->
</div>

<!-- LoA Issued Date - Only visible when workStatusId = 7 -->
<div data-ng-show="workDataTender.workStatusId=='7'">
    <!-- LoA Issued Date field -->
</div>

<!-- Work Order Date - Only visible when workStatusId = 8 -->
<div data-ng-show="workDataTender.workStatusId=='8' || workDataTender.workStatusId=='9' || ...">
    <!-- Work Order Date field -->
</div>

<!-- Re-Tender Date - Only visible when workStatusId = 14 -->
<div data-ng-show="workDataTender.workStatusId=='14'">
    <!-- Re-Tender Date field -->
</div>
```

## How to Access These Fields

1. **Navigate to Edit Work page**
2. **Click on Step 3 - "Sanction Details" tab**
3. **Select a Work Status** from the status buttons:
   - Tender Called
   - Tender Received
   - LoA Issued
   - Work Order Issued
   - Re-Tender
4. **The corresponding date field will appear**
5. **Enter the date and upload required files**
6. **Click Submit**

## Troubleshooting

### Fields Not Visible
- **Cause**: Work Status not selected or wrong status selected
- **Solution**: Click on the correct Work Status button to reveal the corresponding fields

### Date Fields Disabled
- **Cause**: Previous date fields are empty
- **Solution**: Fill in the previous date fields in order (Tender Called → Tender Received → LoA Issued → Work Order)

### File Upload Not Working
- **Cause**: File size too large or wrong format
- **Solution**: Check file size and format requirements

### Data Not Saving
- **Cause**: Required fields not filled or validation errors
- **Solution**: Check browser console for validation errors and fill all required fields (marked with *)

## Related Files

- **Frontend Template**: `src/main/resources/templates/common/work/editTender.html`
- **Main Edit Work Page**: `src/main/resources/templates/common/editWork.html`
- **Angular Controller**: `src/main/resources/static/angular/common/CommonController.js`
- **Backend Controller**: `src/main/java/com/anuppur/controller/CommonController.java`
- **Backend Service**: `src/main/java/com/anuppur/service/impl/CommonServiceImpl.java`

## Summary

All tender/sanction detail fields are present in the application and are visible based on the selected Work Status. The fields follow a logical sequence:

1. **Tender Called** (Status 3) → Enter Tender Called Date
2. **Tender Received** (Status 4) → Enter Tender Received Date
3. **LoA Issued** (Status 7) → Enter LoA Issued Date
4. **Work Order Issued** (Status 8) → Enter Work Order Date + Security Deposit details
5. **Re-Tender** (Status 14) → Enter Re-Tender Date

Each status has its own set of required fields and file uploads.
