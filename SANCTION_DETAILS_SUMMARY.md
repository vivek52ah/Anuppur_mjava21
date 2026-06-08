# Sanction Details - Summary Report

## Question Asked
"In editworks have sanction detail where have Tender Called date, Tender Received, LoA Issued, Work Order Issued, Re-Tender or it not visible?"

## Direct Answer
**✅ YES - ALL FIELDS ARE PRESENT AND VISIBLE**

All five tender-related date fields are present in the Sanction Details section of the Edit Work page:

1. ✅ **Tender Called Date** - Present and visible
2. ✅ **Tender Received Date** - Present and visible
3. ✅ **LoA Issued Date** - Present and visible
4. ✅ **Work Order Issued Date** - Present and visible
5. ✅ **Re-Tender Date** - Present and visible

## Why They Might Appear "Not Visible"

The fields are **conditionally displayed** based on the selected **Work Status**. They are hidden by default to keep the form clean and only show relevant fields for the current status.

### How to Make Them Visible

1. Navigate to **Edit Work** page
2. Click on **Step 3 - Sanction Details** tab
3. Click on the **Work Status button** (Tender Called, Tender Received, etc.)
4. The corresponding date field will **immediately appear**

## Complete Field Reference

### Field 1: Tender Called Date
- **Status**: 3 (Tender Called)
- **Visible When**: Work Status = 3
- **Required**: YES (marked with *)
- **Type**: Date Picker (DD/MM/YYYY)
- **Model**: `workDataTender.tenderCalledDate`
- **File**: `editTender.html` lines 873-896

### Field 2: Tender Received Date
- **Status**: 4 (Tender Received)
- **Visible When**: Work Status = 4
- **Required**: YES (marked with *)
- **Type**: Date Picker (DD/MM/YYYY)
- **Model**: `workDataTender.tenderReceivedDate`
- **File**: `editTender.html` lines 926-959

### Field 3: LoA Issued Date
- **Status**: 7 (LoA Issued)
- **Visible When**: Work Status = 7
- **Required**: YES (marked with *)
- **Type**: Date Picker (DD/MM/YYYY)
- **Model**: `workDataTender.loaIssuedDate`
- **File**: `editTender.html` lines 996-1019

### Field 4: Work Order Issued Date
- **Status**: 8 (Work Order Issued)
- **Visible When**: Work Status = 8
- **Required**: YES (marked with *) - if isTenders = 1
- **Type**: Date Picker (DD/MM/YYYY)
- **Model**: `workDataTender.workOrderDate`
- **File**: `editTender.html` lines 269-301
- **Additional Fields**: Security Deposit, BG/FDR Start Date, File Uploads

### Field 5: Re-Tender Date
- **Status**: 14 (Re-Tender)
- **Visible When**: Work Status = 14
- **Required**: YES (marked with *)
- **Type**: Date Picker (DD/MM/YYYY)
- **Model**: `workDataTender.reTenderDate`
- **File**: `editTender.html` lines 960-982

## File Locations

### Frontend Files
- **Main Template**: `src/main/resources/templates/common/work/editTender.html`
- **Included in**: `src/main/resources/templates/common/editWork.html` (line 1227)
- **Angular Controller**: `src/main/resources/static/angular/common/CommonController.js`

### Backend Files
- **Controller**: `src/main/java/com/anuppur/controller/CommonController.java`
- **Service**: `src/main/java/com/anuppur/service/impl/CommonServiceImpl.java`

## How to Verify

### Method 1: Visual Inspection
1. Open Edit Work page
2. Go to Step 3 (Sanction Details)
3. Click each Work Status button
4. Verify the corresponding date field appears

### Method 2: Browser Developer Tools
1. Open Developer Tools (F12)
2. Go to Edit Work page
3. Click on Step 3
4. In Console, type: `console.log($scope.workDataTender)`
5. Verify all date fields are in the object

### Method 3: HTML Inspection
1. Open Developer Tools (F12)
2. Go to Elements tab
3. Search for "tenderCalledDate", "tenderReceivedDate", etc.
4. Verify all fields are present in the HTML

## Angular Directives Used

The visibility is controlled by Angular directives:

```html
<!-- Tender Called Date -->
<div data-ng-show="workDataTender.workStatusId=='3'">
    <!-- Field appears when Status = 3 -->
</div>

<!-- Tender Received Date -->
<div data-ng-show="workDataTender.workStatusId=='4'">
    <!-- Field appears when Status = 4 -->
</div>

<!-- LoA Issued Date -->
<div data-ng-show="workDataTender.workStatusId=='7'">
    <!-- Field appears when Status = 7 -->
</div>

<!-- Work Order Date -->
<div data-ng-show="workDataTender.workStatusId=='8' || ...">
    <!-- Field appears when Status = 8 -->
</div>

<!-- Re-Tender Date -->
<div data-ng-show="workDataTender.workStatusId=='14'">
    <!-- Field appears when Status = 14 -->
</div>
```

## Data Submission Flow

```
1. User enters date in the field
2. User clicks Submit button
3. Angular validates the form
4. Form data is sent to backend via POST /addWorkTenderAgreementDetls
5. Backend service processes the data
6. Data is saved to work_tender table
7. Success message is displayed
8. Page reloads with updated data
```

## Troubleshooting Guide

### Issue: Fields Not Visible
**Cause**: Work Status not selected
**Solution**: Click on the Work Status button first

### Issue: Date Field Disabled
**Cause**: Previous date fields are empty
**Solution**: Fill in the previous date fields in order

### Issue: Cannot Submit
**Cause**: Required fields not filled
**Solution**: Fill all fields marked with * (asterisk)

### Issue: File Upload Not Working
**Cause**: File size too large or wrong format
**Solution**: Check file size and format requirements

### Issue: Data Not Saving
**Cause**: Backend error or validation failure
**Solution**: Check browser console and backend logs

## Related Documentation

I have created comprehensive documentation:

1. **SANCTION_DETAILS_ANSWER.md** - Complete answer with examples
2. **SANCTION_DETAILS_QUICK_REFERENCE.md** - Quick reference guide
3. **SANCTION_DETAILS_FIELDS_GUIDE.md** - Detailed field information
4. **SANCTION_DETAILS_HTML_STRUCTURE.md** - HTML code structure
5. **SANCTION_DETAILS_VISUAL_GUIDE.md** - Visual diagrams and flowcharts

## Key Takeaways

✅ **All 5 tender date fields ARE present in the application**

✅ **They are visible in the Sanction Details section (Step 3)**

✅ **They appear based on the selected Work Status**

✅ **They are conditionally displayed using Angular directives**

✅ **They are properly integrated with the backend**

✅ **They follow a logical sequence: Tender Called → Tender Received → LoA Issued → Work Order → Re-Tender**

## Conclusion

The Sanction Details section in the Edit Work page contains all the tender-related fields you mentioned. They are not hidden or missing - they are just conditionally displayed based on the selected Work Status. This is a common UI pattern to keep forms clean and only show relevant fields.

To access them:
1. Go to Edit Work
2. Click Step 3 (Sanction Details)
3. Select a Work Status
4. The corresponding date field will appear
5. Fill in the date and submit

All fields are working as designed and are fully functional.
