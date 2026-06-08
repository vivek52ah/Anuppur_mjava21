# Sanction Details - Quick Reference Guide

## ✅ YES - All Fields ARE Visible in Edit Work

The Sanction Details section contains **ALL** the tender-related fields you mentioned:
- ✅ Tender Called Date
- ✅ Tender Received Date
- ✅ LoA Issued Date
- ✅ Work Order Issued Date
- ✅ Re-Tender Date

## 📍 Where to Find Them

**Path**: Edit Work → Step 3 (Sanction Details) → Select Work Status

## 🔄 Work Status Flow

```
┌─────────────────────────────────────────────────────────────┐
│                    SANCTION DETAILS (Step 3)                │
│                                                             │
│  Select Work Status:                                        │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐     │
│  │   Tender     │  │   Tender     │  │     LoA      │     │
│  │   Called     │→ │  Received    │→ │   Issued     │     │
│  │  (Status 3)  │  │  (Status 4)  │  │  (Status 7)  │     │
│  └──────────────┘  └──────────────┘  └──────────────┘     │
│         ↓                  ↓                  ↓             │
│   Enter Date 1        Enter Date 2      Enter Date 3       │
│                                                             │
│  ┌──────────────┐  ┌──────────────┐                        │
│  │    Work      │  │   Re-Tender  │                        │
│  │   Order      │  │              │                        │
│  │  (Status 8)  │  │  (Status 14) │                        │
│  └──────────────┘  └──────────────┘                        │
│         ↓                  ↓                                │
│   Enter Date 4        Enter Date 5                         │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

## 📋 Field Details

### Status 3: Tender Called
```
Field: Tender Called Date
Type: Date Picker (DD/MM/YYYY)
Required: YES (*)
Visible: When Status = 3
```

### Status 4: Tender Received
```
Field: Tender Received Date
Type: Date Picker (DD/MM/YYYY)
Required: YES (*)
Visible: When Status = 4
```

### Status 7: LoA Issued
```
Field: LoA Issued Date
Type: Date Picker (DD/MM/YYYY)
Required: YES (*)
Visible: When Status = 7
```

### Status 8: Work Order Issued
```
Fields:
  1. Work Order Date (Required *)
  2. Security Deposit (BG or FDR)
  3. BG/FDR Start Date
  4. Work Order File Upload (Required *)
  5. Technical Drawing Upload (Required *)
  6. Tender File Upload (Required *)
  7. Agreement File Upload (Required *)

Type: Date Picker + Radio + File Upload
Visible: When Status = 8
```

### Status 14: Re-Tender
```
Fields:
  1. Re-Tender Date (Required *)
  2. Re-Tender Count (Read-only)

Type: Date Picker + Number
Visible: When Status = 14
```

## 🎯 Step-by-Step Usage

### Step 1: Navigate to Edit Work
1. Go to Edit Work page
2. Click on **Step 3 - Sanction Details** tab

### Step 2: Select Work Status
Click on one of the status buttons:
- **Tender Called** (Status 3)
- **Tender Received** (Status 4)
- **LoA Issued** (Status 7)
- **Work Order Issued** (Status 8)
- **Re-Tender** (Status 14)

### Step 3: Fill in the Date Field
The corresponding date field will appear. Enter the date in DD/MM/YYYY format.

### Step 4: Upload Files (if Status 8)
If you selected "Work Order Issued", upload the required files:
- Work Order
- Technical Drawing
- Tender
- Agreement

### Step 5: Submit
Click the Submit button to save the data.

## ⚠️ Important Notes

1. **Sequential Requirement**: 
   - You must fill dates in order: Tender Called → Tender Received → LoA Issued → Work Order
   - Later date fields are disabled until previous dates are filled

2. **Required Fields** (marked with *):
   - All date fields are required
   - File uploads are required when isTenders = 1

3. **Visibility**:
   - Fields only appear when the corresponding Work Status is selected
   - If you don't see a field, check if the correct Work Status is selected

4. **File Upload**:
   - Maximum file size: Check application settings
   - Supported formats: PDF, DOC, DOCX, XLS, XLSX, JPG, PNG

## 🔍 Verification Checklist

- [ ] Can see "Sanction Details" tab in Step 3
- [ ] Can see Work Status buttons (Tender Called, Tender Received, etc.)
- [ ] Can click on each status button
- [ ] Date field appears when status is selected
- [ ] Can enter date in DD/MM/YYYY format
- [ ] Can upload files for Status 8
- [ ] Can submit the form
- [ ] Data is saved to database

## 📞 If Fields Are Not Visible

### Possible Causes:
1. **Wrong Tab**: Make sure you're on Step 3 (Sanction Details)
2. **Wrong Role**: Some roles may have view-only access
3. **Work Status Not Selected**: Click on a Work Status button first
4. **Browser Cache**: Clear browser cache and refresh
5. **JavaScript Error**: Check browser console (F12) for errors

### Solutions:
1. Verify you're on the correct tab
2. Check your user role permissions
3. Select a Work Status from the buttons
4. Clear cache: Ctrl+Shift+Delete
5. Check browser console for errors

## 📁 Related Files

- **Template**: `src/main/resources/templates/common/work/editTender.html`
- **Main Page**: `src/main/resources/templates/common/editWork.html`
- **Controller**: `src/main/resources/static/angular/common/CommonController.js`

## 🎓 Summary

**YES, all the fields you mentioned ARE visible in the Sanction Details section:**

| Field | Status | Visible | Required |
|-------|--------|---------|----------|
| Tender Called Date | 3 | ✅ | ✅ |
| Tender Received Date | 4 | ✅ | ✅ |
| LoA Issued Date | 7 | ✅ | ✅ |
| Work Order Issued Date | 8 | ✅ | ✅ |
| Re-Tender Date | 14 | ✅ | ✅ |

They are just **hidden by default** and appear when you select the corresponding Work Status.
