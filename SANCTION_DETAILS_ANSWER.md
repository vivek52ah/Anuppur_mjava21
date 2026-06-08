# Sanction Details - Complete Answer

## Question
"In editworks have sanction detail where have Tender Called date, Tender Received, LoA Issued, Work Order Issued, Re-Tender or it not visible?"

## Answer
**✅ YES - ALL FIELDS ARE VISIBLE AND PRESENT**

All the tender-related fields you mentioned are present in the Sanction Details section of the Edit Work page:

| Field | Status | Visible | Location |
|-------|--------|---------|----------|
| ✅ Tender Called Date | 3 | YES | editTender.html:873-896 |
| ✅ Tender Received Date | 4 | YES | editTender.html:926-959 |
| ✅ LoA Issued Date | 7 | YES | editTender.html:996-1019 |
| ✅ Work Order Issued Date | 8 | YES | editTender.html:269-301 |
| ✅ Re-Tender Date | 14 | YES | editTender.html:960-982 |

## Why They Might Not Be Visible

The fields are **conditionally visible** based on the selected **Work Status**. They are hidden by default and appear when you select the corresponding status.

### Visibility Logic
```
If you don't see the fields:
1. Make sure you're on Step 3 (Sanction Details) tab
2. Click on the Work Status button (Tender Called, Tender Received, etc.)
3. The corresponding date field will appear
```

## How to Access Them

### Step 1: Navigate to Edit Work
- Go to the Edit Work page
- Click on **Step 3 - Sanction Details** tab

### Step 2: Select Work Status
Click on one of these status buttons:
- **Tender Called** → Shows "Tender Called Date" field
- **Tender Received** → Shows "Tender Received Date" field
- **LoA Issued** → Shows "LoA Issued Date" field
- **Work Order Issued** → Shows "Work Order Date" field + Security Deposit + File Uploads
- **Re-Tender** → Shows "Re-Tender Date" field

### Step 3: Fill in the Date
Enter the date in DD/MM/YYYY format

### Step 4: Submit
Click Submit to save

## File Locations

### Frontend Template
- **Main File**: `src/main/resources/templates/common/work/editTender.html`
- **Included in**: `src/main/resources/templates/common/editWork.html` (line 1227)

### Backend
- **Controller**: `src/main/java/com/anuppur/controller/CommonController.java`
- **Service**: `src/main/java/com/anuppur/service/impl/CommonServiceImpl.java`

### Frontend Logic
- **Angular Controller**: `src/main/resources/static/angular/common/CommonController.js`
- **Function**: `createTenderAgreementData()`

## Complete Field List

### Tender Called (Status 3)
```
Field: Tender Called Date
Type: Date Picker
Required: YES
Format: DD/MM/YYYY
Model: workDataTender.tenderCalledDate
```

### Tender Received (Status 4)
```
Field: Tender Received Date
Type: Date Picker
Required: YES
Format: DD/MM/YYYY
Model: workDataTender.tenderReceivedDate
```

### LoA Issued (Status 7)
```
Field: LoA Issued Date
Type: Date Picker
Required: YES
Format: DD/MM/YYYY
Model: workDataTender.loaIssuedDate
Disabled When: Previous dates are null
```

### Work Order Issued (Status 8)
```
Fields:
1. Work Order Date (Required)
2. Security Deposit (BG or FDR)
3. BG/FDR Start Date
4. Work Order File Upload (Required)
5. Technical Drawing Upload (Required)
6. Tender File Upload (Required)
7. Agreement File Upload (Required)

Type: Date Picker + Radio + File Upload
Model: workDataTender.workOrderDate
Disabled When: Previous dates are null
```

### Re-Tender (Status 14)
```
Fields:
1. Re-Tender Date (Required)
2. Re-Tender Count (Read-only)

Type: Date Picker + Number
Model: workDataTender.reTenderDate
```

## HTML Code Snippets

### Tender Called Date
```html
<div data-ng-show="workDataTender.workStatusId=='3'">
    <label><span>Tender Called Date</span> <span class="aestrick">&#42;</span></label>
    <input type="text" class="form-control datetimepicker-input"
           data-ng-model="workDataTender.tenderCalledDate"
           placeholder="DD/MM/YYYY" />
</div>
```

### Tender Received Date
```html
<div data-ng-show="workDataTender.workStatusId=='4'">
    <label><span>Tender Received Date</span> <span class="aestrick">&#42;</span></label>
    <input type="text" class="form-control datetimepicker-input"
           data-ng-model="workDataTender.tenderReceivedDate"
           placeholder="DD/MM/YYYY" />
</div>
```

### LoA Issued Date
```html
<div data-ng-show="workDataTender.workStatusId=='7'">
    <label><span>LoA Issued Date</span> <span class="aestrick">&#42;</span></label>
    <input type="text" class="form-control datetimepicker-input"
           data-ng-model="workDataTender.loaIssuedDate"
           placeholder="DD/MM/YYYY" />
</div>
```

### Work Order Date
```html
<div data-ng-show="workDataTender.workStatusId=='8' || ...">
    <label><span>Work Order Date</span> <span class="aestrick">&#42;</span></label>
    <input type="text" class="form-control datetimepicker-input"
           data-ng-model="workDataTender.workOrderDate"
           placeholder="DD/MM/YYYY" />
</div>
```

### Re-Tender Date
```html
<div data-ng-show="workDataTender.workStatusId=='14'">
    <label><span>Re-Tender Date</span></label>
    <input type="text" class="form-control datetimepicker-input"
           data-ng-model="workDataTender.reTenderDate"
           placeholder="DD/MM/YYYY" />
</div>
```

## Verification

To verify all fields are present:

1. **Open Browser Developer Tools** (F12)
2. **Go to Edit Work page**
3. **Click on Step 3 - Sanction Details**
4. **Click on each Work Status button**
5. **Verify the corresponding date field appears**

### Expected Results
- ✅ Tender Called button → Tender Called Date field appears
- ✅ Tender Received button → Tender Received Date field appears
- ✅ LoA Issued button → LoA Issued Date field appears
- ✅ Work Order Issued button → Work Order Date field + other fields appear
- ✅ Re-Tender button → Re-Tender Date field appears

## Troubleshooting

### Fields Not Visible
**Cause**: Work Status not selected
**Solution**: Click on the Work Status button first

### Date Field Disabled
**Cause**: Previous date fields are empty
**Solution**: Fill in the previous date fields in order

### Cannot Submit
**Cause**: Required fields not filled
**Solution**: Fill all fields marked with * (asterisk)

### File Upload Not Working
**Cause**: File size too large or wrong format
**Solution**: Check file size and format requirements

## Summary

**All 5 tender date fields ARE present and visible in the Sanction Details section:**

1. ✅ **Tender Called Date** - Visible when Status = 3
2. ✅ **Tender Received Date** - Visible when Status = 4
3. ✅ **LoA Issued Date** - Visible when Status = 7
4. ✅ **Work Order Issued Date** - Visible when Status = 8
5. ✅ **Re-Tender Date** - Visible when Status = 14

They are just **hidden by default** and appear when you select the corresponding Work Status. This is by design to keep the form clean and only show relevant fields.

## Related Documentation

- `SANCTION_DETAILS_QUICK_REFERENCE.md` - Quick reference guide
- `SANCTION_DETAILS_FIELDS_GUIDE.md` - Detailed field information
- `SANCTION_DETAILS_HTML_STRUCTURE.md` - HTML code structure
