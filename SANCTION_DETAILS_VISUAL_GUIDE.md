# Sanction Details - Visual Guide

## Application Navigation

```
┌─────────────────────────────────────────────────────────────────┐
│                        EDIT WORK PAGE                           │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Step 1: Work Details                                           │
│  Step 2: DM Remarks                                             │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ Step 3: SANCTION DETAILS ← YOU ARE HERE                │   │
│  └─────────────────────────────────────────────────────────┘   │
│  Step 4: Contractor's Details                                  │
│  Step 5: Work Progress Details                                 │
│  Step 6: CC Details                                            │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## Sanction Details Section Layout

```
┌──────────────────────────────────────────────────────────────────┐
│                    SANCTION DETAILS (Step 3)                     │
├──────────────────────────────────────────────────────────────────┤
│                                                                  │
│  Tender And Agreement Details                                   │
│  ┌────────────────────────────────────────────────────────────┐ │
│  │                                                            │ │
│  │  Work Status Selection:                                   │ │
│  │  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐    │ │
│  │  │ Tender   │ │ Tender   │ │   LoA    │ │  Work    │    │ │
│  │  │ Called   │ │ Received │ │ Issued   │ │ Order    │    │ │
│  │  │(Status 3)│ │(Status 4)│ │(Status 7)│ │(Status 8)│    │ │
│  │  └──────────┘ └──────────┘ └──────────┘ └──────────┘    │ │
│  │  ┌──────────┐                                            │ │
│  │  │Re-Tender │                                            │ │
│  │  │(Status14)│                                            │ │
│  │  └──────────┘                                            │ │
│  │                                                            │ │
│  │  ─────────────────────────────────────────────────────   │ │
│  │                                                            │ │
│  │  [Date Field Appears Here Based on Selected Status]      │ │
│  │                                                            │ │
│  │  ─────────────────────────────────────────────────────   │ │
│  │                                                            │ │
│  │  [Submit Button]                                         │ │
│  │                                                            │ │
│  └────────────────────────────────────────────────────────────┘ │
│                                                                  │
└──────────────────────────────────────────────────────────────────┘
```

## Status-Based Field Display

### When Status 3 is Selected (Tender Called)
```
┌─────────────────────────────────────────┐
│  Work Status: Tender Called (Status 3)  │
├─────────────────────────────────────────┤
│                                         │
│  Tender Called Date *                   │
│  ┌─────────────────────────────────┐   │
│  │ DD/MM/YYYY        [📅 Calendar] │   │
│  └─────────────────────────────────┘   │
│                                         │
│  [Submit]                               │
│                                         │
└─────────────────────────────────────────┘
```

### When Status 4 is Selected (Tender Received)
```
┌─────────────────────────────────────────┐
│ Work Status: Tender Received (Status 4) │
├─────────────────────────────────────────┤
│                                         │
│  Tender Received Date *                 │
│  ┌─────────────────────────────────┐   │
│  │ DD/MM/YYYY        [📅 Calendar] │   │
│  └─────────────────────────────────┘   │
│                                         │
│  [Submit]                               │
│                                         │
└─────────────────────────────────────────┘
```

### When Status 7 is Selected (LoA Issued)
```
┌─────────────────────────────────────────┐
│   Work Status: LoA Issued (Status 7)    │
├─────────────────────────────────────────┤
│                                         │
│  LoA Issued Date *                      │
│  ┌─────────────────────────────────┐   │
│  │ DD/MM/YYYY        [📅 Calendar] │   │
│  └─────────────────────────────────┘   │
│                                         │
│  [Submit]                               │
│                                         │
└─────────────────────────────────────────┘
```

### When Status 8 is Selected (Work Order Issued)
```
┌──────────────────────────────────────────────────┐
│ Work Status: Work Order Issued (Status 8)        │
├──────────────────────────────────────────────────┤
│                                                  │
│  Work Order Date *                               │
│  ┌────────────────────────────────────────────┐ │
│  │ DD/MM/YYYY              [📅 Calendar]     │ │
│  └────────────────────────────────────────────┘ │
│                                                  │
│  Security Deposit                                │
│  ○ BG (selected)    ○ FDR                        │
│                                                  │
│  BG/FDR Start Date                               │
│  ┌────────────────────────────────────────────┐ │
│  │ DD/MM/YYYY              [📅 Calendar]     │ │
│  └────────────────────────────────────────────┘ │
│                                                  │
│  Upload Work Order *                             │
│  [Choose File]                                   │
│                                                  │
│  Upload Technical Drawing *                      │
│  [Choose File]                                   │
│                                                  │
│  Upload Tender *                                 │
│  [Choose File]                                   │
│                                                  │
│  Upload Agreement *                              │
│  [Choose File]                                   │
│                                                  │
│  [Submit]                                        │
│                                                  │
└──────────────────────────────────────────────────┘
```

### When Status 14 is Selected (Re-Tender)
```
┌─────────────────────────────────────────┐
│   Work Status: Re-Tender (Status 14)    │
├─────────────────────────────────────────┤
│                                         │
│  Re-Tender Date *                       │
│  ┌─────────────────────────────────┐   │
│  │ DD/MM/YYYY        [📅 Calendar] │   │
│  └─────────────────────────────────┘   │
│                                         │
│  Re-Tender Count                        │
│  ┌─────────────────────────────────┐   │
│  │ [Read-only number]              │   │
│  └─────────────────────────────────┘   │
│                                         │
│  [Submit]                               │
│                                         │
└─────────────────────────────────────────┘
```

## Complete Workflow

```
START
  │
  ├─→ Navigate to Edit Work Page
  │
  ├─→ Click on Step 3 (Sanction Details)
  │
  ├─→ Select Work Status:
  │   │
  │   ├─→ Status 3 (Tender Called)
  │   │   └─→ Enter Tender Called Date
  │   │       └─→ Submit
  │   │
  │   ├─→ Status 4 (Tender Received)
  │   │   └─→ Enter Tender Received Date
  │   │       └─→ Submit
  │   │
  │   ├─→ Status 7 (LoA Issued)
  │   │   └─→ Enter LoA Issued Date
  │   │       └─→ Submit
  │   │
  │   ├─→ Status 8 (Work Order Issued)
  │   │   ├─→ Enter Work Order Date
  │   │   ├─→ Select Security Deposit (BG/FDR)
  │   │   ├─→ Enter BG/FDR Start Date
  │   │   ├─→ Upload Work Order File
  │   │   ├─→ Upload Technical Drawing
  │   │   ├─→ Upload Tender File
  │   │   ├─→ Upload Agreement File
  │   │   └─→ Submit
  │   │
  │   └─→ Status 14 (Re-Tender)
  │       ├─→ Enter Re-Tender Date
  │       ├─→ View Re-Tender Count (read-only)
  │       └─→ Submit
  │
  └─→ END
```

## Field Visibility Matrix

```
┌──────────────────┬───────┬───────┬───────┬───────┬────────┐
│ Field            │ Stat3 │ Stat4 │ Stat7 │ Stat8 │ Stat14 │
├──────────────────┼───────┼───────┼───────┼───────┼────────┤
│ Tender Called    │  ✅   │       │       │       │        │
│ Tender Received  │       │  ✅   │       │       │        │
│ LoA Issued       │       │       │  ✅   │       │        │
│ Work Order       │       │       │       │  ✅   │        │
│ Security Deposit │       │       │       │  ✅   │        │
│ BG/FDR Start     │       │       │       │  ✅   │        │
│ File Uploads     │       │       │       │  ✅   │        │
│ Re-Tender Date   │       │       │       │       │  ✅    │
│ Re-Tender Count  │       │       │       │       │  ✅    │
└──────────────────┴───────┴───────┴───────┴───────┴────────┘
```

## Data Flow Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    USER INTERFACE                           │
│  (Edit Work → Step 3 → Sanction Details)                   │
└────────────────────┬────────────────────────────────────────┘
                     │
                     ↓
┌─────────────────────────────────────────────────────────────┐
│              ANGULAR CONTROLLER                             │
│  (CommonController.js)                                      │
│  - workDataTender object                                    │
│  - createTenderAgreementData() function                     │
└────────────────────┬────────────────────────────────────────┘
                     │
                     ↓
┌─────────────────────────────────────────────────────────────┐
│              BACKEND API ENDPOINT                           │
│  POST /addWorkTenderAgreementDetls                          │
└────────────────────┬────────────────────────────────────────┘
                     │
                     ↓
┌─────────────────────────────────────────────────────────────┐
│              BACKEND SERVICE                                │
│  (CommonServiceImpl.java)                                    │
│  - addWorkTenderAgreementDetls() method                     │
└────────────────────┬────────────────────────────────────────┘
                     │
                     ↓
┌─────────────────────────────────────────────────────────────┐
│              DATABASE                                       │
│  - work_tender table                                        │
│  - document_upload table (for files)                        │
└─────────────────────────────────────────────────────────────┘
```

## Required vs Optional Fields

```
Status 3 (Tender Called):
  ✓ Tender Called Date (REQUIRED)

Status 4 (Tender Received):
  ✓ Tender Received Date (REQUIRED)

Status 7 (LoA Issued):
  ✓ LoA Issued Date (REQUIRED)

Status 8 (Work Order Issued):
  ✓ Work Order Date (REQUIRED if isTenders=1)
  ○ Security Deposit (OPTIONAL - defaults to BG)
  ○ BG/FDR Start Date (OPTIONAL)
  ✓ Work Order File (REQUIRED if isTenders=1)
  ✓ Technical Drawing (REQUIRED if isTenders=1)
  ✓ Tender File (REQUIRED if isTenders=1)
  ✓ Agreement File (REQUIRED if isTenders=1)

Status 14 (Re-Tender):
  ✓ Re-Tender Date (REQUIRED)
  ○ Re-Tender Count (READ-ONLY)
```

## Browser Console Verification

To verify fields are present, open Developer Tools (F12) and check:

```javascript
// Check if Angular model exists
console.log($scope.workDataTender);

// Check if form is valid
console.log($scope.workTenderForm.$valid);

// Check specific field values
console.log($scope.workDataTender.tenderCalledDate);
console.log($scope.workDataTender.tenderReceivedDate);
console.log($scope.workDataTender.loaIssuedDate);
console.log($scope.workDataTender.workOrderDate);
console.log($scope.workDataTender.reTenderDate);
```

## Summary

All 5 tender date fields are present and visible:

```
✅ Tender Called Date      (Status 3)
✅ Tender Received Date    (Status 4)
✅ LoA Issued Date         (Status 7)
✅ Work Order Issued Date  (Status 8)
✅ Re-Tender Date          (Status 14)

They are conditionally displayed based on the selected Work Status.
```
