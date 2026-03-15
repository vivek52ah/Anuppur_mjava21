# Block Filter Issue - Root Cause & Fix

## The Problem

You selected **"Kotma"** block in the UI, but the response returned data with **"Jaithari"** block. This proves the block filter was not being applied.

## Root Cause

The `blockIdList` was being created from the request parameter but was **NOT being passed** to the repository methods.

### Flow Diagram (BEFORE FIX):

```
UI: Select "Kotma" block
    ↓
Controller: Extract blockId from request ✓
    ↓
Service: Convert blockId to blockIdList ✓
    ↓
Service: Call repository method WITHOUT blockIdList ✗
    ↓
Repository: No blockId filter applied ✗
    ↓
Database: Returns ALL works (no filtering)
    ↓
Response: Returns "Jaithari" block (wrong block)
```

### Flow Diagram (AFTER FIX):

```
UI: Select "Kotma" block
    ↓
Controller: Extract blockId from request ✓
    ↓
Service: Convert blockId to blockIdList ✓
    ↓
Service: Call repository method WITH blockIdList ✓
    ↓
Repository: Apply blockId filter ✓
    ↓
Database: Returns only works from "Kotma" block
    ↓
Response: Returns "Kotma" block (correct block)
```

## The Fix

### File: `CommonServiceImpl.java`

**Line 609 - ROLE_DEPARTMENT:**
```java
// BEFORE (WRONG)
works = workRepositoryCustomImpl.fetchAllWorksByDivision(pageable, workNo, workTypeList, fyList,
        districtIds, statusList, agencyList, null, priorityList, headList,
        vsList, divisionCode, departmentRemark);  // ← blockIdList missing

// AFTER (CORRECT)
works = workRepositoryCustomImpl.fetchAllWorksByDivision(pageable, workNo, workTypeList, fyList,
        districtIds, statusList, agencyList, null, priorityList, headList,
        vsList, divisionCode, departmentRemark, blockIdList);  // ← blockIdList added
```

**Line 614 - ROLE_DISTRICT:**
```java
// BEFORE (WRONG)
works = workRepositoryCustomImpl.fetchAllWorksByDistrict(pageable, workNo, workTypeList, fyList,
        districtCode, agencyList, divisionIds, districtIds, workSubTypeIdInt, statusList, priorityList, headList, vsList, departmentRemark);  // ← blockIdList missing

// AFTER (CORRECT)
works = workRepositoryCustomImpl.fetchAllWorksByDistrict(pageable, workNo, workTypeList, fyList,
        districtCode, agencyList, divisionIds, districtIds, workSubTypeIdInt, statusList, priorityList, headList, vsList, departmentRemark, blockIdList);  // ← blockIdList added
```

## Why This Happened

1. The repository methods (`fetchAllWorksByDivision` and `fetchAllWorksByDistrict`) already had:
   - `blockIdList` parameter in their signature
   - Filter logic to apply the block filter

2. But the calls in `CommonServiceImpl` were not passing `blockIdList`

3. This is a classic case of incomplete refactoring - the parameter was added to the method signature but the callers were not updated

## Result

Now when you select "Kotma" block:
- The blockId is extracted from the request
- Converted to blockIdList
- Passed to the repository method
- Filter is applied in the database query
- Only works from "Kotma" block are returned
