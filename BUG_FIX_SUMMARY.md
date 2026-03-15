# Bug Fix Summary - Block Filter Not Being Passed to Repository Methods

## Root Cause Identified

### **CRITICAL: blockIdList Not Passed to Repository Methods**
- **Location**: `CommonServiceImpl.java` lines 606-614
- **Issue**: The `blockIdList` was being created and converted from the request parameter, but was NOT being passed to the repository methods for `ROLE_DEPARTMENT` and `ROLE_DISTRICT` users
- **Impact**: Block filter was completely ignored for these user roles, even though the UI was sending the filter
- **Evidence**: 
  - User selected "Kotma" block
  - Response returned data with "Jaithari" block (different block)
  - This proves the block filter was not being applied

### **Why This Happened:**
- `fetchAllWorksByDivision()` method signature already had `blockIdList` parameter
- `fetchAllWorksByDistrict()` method signature already had `blockIdList` parameter
- Both methods had the filter logic implemented
- **BUT** the calls in `CommonServiceImpl` were NOT passing `blockIdList` to these methods

## Issues Fixed

### 1. **Block Filter Not Passed to Repository - CRITICAL FIX**

**File**: `CommonServiceImpl.java` (Lines 606-614)

**Problem**: 
```java
// WRONG - blockIdList NOT passed to repository methods
if (r.contains("ROLE_DEPARTMENT")) {
    works = workRepositoryCustomImpl.fetchAllWorksByDivision(pageable, workNo, workTypeList, fyList,
            districtIds, statusList, agencyList, null, priorityList, headList,
            vsList, divisionCode, departmentRemark);  // ← blockIdList missing
}

else if (r.contains("ROLE_DISTRICT")) {
    works = workRepositoryCustomImpl.fetchAllWorksByDistrict(pageable, workNo, workTypeList, fyList,
            districtCode, agencyList, divisionIds, districtIds, workSubTypeIdInt, statusList, priorityList, headList, vsList, departmentRemark);  // ← blockIdList missing
}
```

**Solution**:
```java
// CORRECT - blockIdList now passed to repository methods
if (r.contains("ROLE_DEPARTMENT")) {
    works = workRepositoryCustomImpl.fetchAllWorksByDivision(pageable, workNo, workTypeList, fyList,
            districtIds, statusList, agencyList, null, priorityList, headList,
            vsList, divisionCode, departmentRemark, blockIdList);  // ← blockIdList added
}

else if (r.contains("ROLE_DISTRICT")) {
    works = workRepositoryCustomImpl.fetchAllWorksByDistrict(pageable, workNo, workTypeList, fyList,
            districtCode, agencyList, divisionIds, districtIds, workSubTypeIdInt, statusList, priorityList, headList, vsList, departmentRemark, blockIdList);  // ← blockIdList added
}
```

**Explanation**: 
- The `blockIdList` was being created from the request parameter (line 560-570)
- The repository methods already had the parameter and filter logic implemented
- But the calls were not passing `blockIdList`, so the filter was never applied
- This is why selecting "Kotma" block returned data from "Jaithari" block

### 2. **Filter Parameters Order - CRITICAL FIX**

**File**: `CommonController.java` (Line 742-760)

**Problem**: 
```java
// WRONG - workNameFilter passed as 3rd parameter (where workName should be)
WorkJson workJson = commonService.fetchWorksList(pageable,
    searchParameterWorkNo,
    workNameFilter,  // ← WRONG POSITION
    scheme,
    workTypeList,
    ...
```

**Solution**:
```java
// CORRECT - Parameters in proper order
WorkJson workJson = commonService.fetchWorksList(pageable,
    searchParameterWorkNo,
    null,  // workName parameter (not used, use workNameFilter instead)
    scheme,
    workTypeList,
    fyList,
    agencyList,
    blockId,
    workStatus,
    districtId,
    divisionId,
    searchByDivision,
    workSubTypeId,
    statusList,
    priorityList,
    headList,
    vsList,
    workNameFilter,  // ← CORRECT POSITION (18th parameter)
    departmentRemark
);
```

**Explanation**: 
- The method signature expects parameters in a specific order
- `workNameFilter` should be the 18th parameter, not the 3rd
- When passed in wrong order, all subsequent filters were misaligned
- This caused the query to filter on wrong fields, resulting in empty results

### 3. **Department Remarks Filter - Field Name Correction**

**File**: `WorkRepositoryCustomImpl.java` (Line 648)

**Problem**: 
```java
// WRONG - Field doesn't exist in DmRemarks entity
dmRemarksRoot.get("departmentRemarkName")
```

**Solution**:
```java
// CORRECT - Actual field name in DmRemarks entity
dmRemarksRoot.get("departmentRemarks")
```

**Explanation**: 
- The `DmRemarks` entity has a column `department_remarks` (maps to field `departmentRemarks`)
- The code was incorrectly referencing `departmentRemarkName` which belongs to a different entity (`DepartmentRemarks`)
- This caused Hibernate to throw an `IllegalArgumentException` when trying to build the query

### 4. **Block Name Filter - New Infrastructure**

**File**: `WorkRepositoryCustomImpl.java`

**Added**:
- New helper method `addBlockNameFilter()` (Line 654-662)
- Import for `Block` entity
- Block name filter logic using subquery to join with Block entity

**Implementation**:
```java
private Predicate addBlockNameFilter(CriteriaBuilder cb, CriteriaQuery<?> query, Root<Work> work, String blockNameFilter) {
    if (blockNameFilter != null && !blockNameFilter.isEmpty()) {
        Subquery<Long> blockSubquery = query.subquery(Long.class);
        Root<Block> blockRoot = blockSubquery.from(Block.class);
        blockSubquery.select(blockRoot.get("blockId"))
            .where(cb.like(cb.lower(blockRoot.get("blockName")), "%" + blockNameFilter.toLowerCase() + "%"));
        return work.get("blockId").in(blockSubquery);
    }
    return null;
}
```

**How it works**:
- Creates a subquery to find all Block IDs where blockName matches the filter (case-insensitive partial match)
- Returns a predicate that filters Work records where blockId is in the subquery results
- Follows the same pattern as the department remarks filter
- Ready to be used when UI sends blockNameFilter parameter

### 5. **NullPointerException in EmailServiceImpl**
Added null-safety check for email configuration properties.

### 6. **NullPointerException in SMSUtil**
Added null-safety checks in both SMS methods.

### 7. **Work List Not Loading - CRITICAL FIX**

#### Problem:
- `fetchAllWorksByDivision()` method was missing the `divisionId` parameter
- Query was filtering by `createdBy = username` instead of division
- No division filter was applied to the WHERE clause

#### Solution:
**Updated WorkRepositoryCustomImpl.fetchAllWorksByDivision():**
- Added `Long divisionId` parameter to method signature
- Added division filter to main query: `if (divisionId != null) { predicates.add(cb.equal(work.get("divisionId"), divisionId)); }`
- Added division filter to count query for consistency
- Updated CommonServiceImpl to pass `divisionCode` instead of `username`

**Code Changes:**
```java
// Before: Filtering by username (only showed works created by that user)
works = workRepositoryCustomImpl.fetchAllWorksByDivision(pageable, workNo, workTypeList, fyList,
        districtIds, statusList, agencyList, userEntity.getUsername(), priorityList, headList, vsList);

// After: Filtering by division (shows all works for the division)
works = workRepositoryCustomImpl.fetchAllWorksByDivision(pageable, workNo, workTypeList, fyList,
        districtIds, statusList, agencyList, null, priorityList, headList, vsList, divisionCode);
```

### 8. **CSP Header Blocking AJAX Requests**
Fixed Content Security Policy to allow AJAX requests to localhost.

## Files Modified

1. `CommonServiceImpl.java` - **CRITICAL FIX**
   - Added `blockIdList` parameter to `fetchAllWorksByDivision()` call (line 609)
   - Added `blockIdList` parameter to `fetchAllWorksByDistrict()` call (line 614)
   - Now block filter is properly passed to repository methods

2. `CommonController.java` - **CRITICAL FIX**
   - Fixed parameter order in fetchWorksList call
   - `workNameFilter` now passed as 18th parameter (correct position)
   - All other parameters now aligned correctly

3. `WorkRepositoryCustomImpl.java` - **CRITICAL FIX**
   - Fixed department remarks filter field name: `departmentRemarkName` → `departmentRemarks`
   - Added Block entity import
   - Added new `addBlockNameFilter()` helper method for block name filtering
   - Both main query and count query updated with blockId filter

4. `EmailServiceImpl.java` - Email null-safety check
5. `SMSUtil.java` - SMS null-safety checks
6. `SpringSecurityConfig.java` - Fixed CSP header

## Expected Result

After these fixes:

1. **Block Filter** - Now works correctly for ROLE_DEPARTMENT and ROLE_DISTRICT users
   - Selecting "Kotma" block will return only works from Kotma block
   - Filter is properly passed from UI → Controller → Service → Repository

2. **Work Name Filter** - Now passed in correct parameter position

3. **Department Remarks Filter** - Now works correctly without throwing exceptions

4. **Block Name Filter** - Infrastructure ready for future UI implementation

5. **Work List Loading** - All works display correctly based on user role and division

The work list will now properly filter by block, work name, and department remarks when these filters are applied.
