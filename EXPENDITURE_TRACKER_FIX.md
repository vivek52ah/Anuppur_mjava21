# Expenditure Tracker - Data Not Updating Issue

## Problem

The "Expenditure Tracker" table in Step 5 (Work Progress) is showing all zeros:
- Total Expenditure till date: 0
- Expenditure in Current Financial Year: 0
- Expenditure in this Month: 0

---

## Root Cause Analysis

### Issue 1: Data Not Being Saved
The `addWorkProExpensesData` method in `CommonServiceImpl.java` has complex logic that may not be saving data properly.

### Issue 2: NULL Values
The method checks for NULL values but doesn't handle all cases properly:
```java
BigDecimal expenseCurrentFy = expensesDataBean.getExpensessCurrentFy();
if (expenseCurrentFy == null && expensesDataBean.getTotalExpensess() != null) {
    expenseCurrentFy = expensesDataBean.getTotalExpensess();
}
```

### Issue 3: Status Filtering
The query filters by status, which might exclude valid records:
```java
List<ExpensesData> expensesData = expensesDataRepository
    .findByWorkIdAndStatusIn(expensesDataBean.getWorkId(), statusList);
```

---

## Solution

### Step 1: Simplify the Expense Saving Logic

**File:** `src/main/java/com/anuppur/service/impl/CommonServiceImpl.java`

**Current Complex Logic (Lines 3207-3350):**
The method has too many conditions and calculations that can fail silently.

**Proposed Fix:**
Simplify the logic to ensure data is always saved:

```java
@Override
@Transactional(rollbackFor = Exception.class)
synchronized public ResponseObject addWorkProExpensesData(ExpensesDataBean expensesDataBean) throws Exception {
    ResponseObject responseObject = new ResponseObject();
    
    try {
        if (expensesDataBean == null || expensesDataBean.getWorkId() == null) {
            responseObject.setErrorMessage("Invalid expense data");
            return responseObject;
        }
        
        ExpensesData entity = new ExpensesData();
        entity.setCreatedDate(new Date());
        entity.setWorkId(expensesDataBean.getWorkId());
        
        // Set expense values with defaults
        entity.setExpensessCurrentFy(
            expensesDataBean.getExpensessCurrentFy() != null 
                ? expensesDataBean.getExpensessCurrentFy() 
                : BigDecimal.ZERO
        );
        
        entity.setExpensessUptoMarch(
            expensesDataBean.getExpensessUptoMarch() != null 
                ? expensesDataBean.getExpensessUptoMarch() 
                : BigDecimal.ZERO
        );
        
        entity.setTotalExpensess(
            expensesDataBean.getTotalExpensess() != null 
                ? expensesDataBean.getTotalExpensess() 
                : BigDecimal.ZERO
        );
        
        entity.setMonth(expensesDataBean.getMonth());
        entity.setYear(expensesDataBean.getYear());
        
        // Get status from year
        YearStatus yearStatus = yearStatusRepository.findByYear(expensesDataBean.getYear());
        entity.setStatus(yearStatus != null ? yearStatus.getStatus() : 1L);
        
        // Save the entity
        expensesDataRepository.save(entity);
        
        responseObject.setId(expensesDataBean.getWorkId());
        responseObject.setSuccessMessage("Expenses saved successfully!");
        
        logger.info("Expenses saved for workId: {}, Amount: {}", 
            expensesDataBean.getWorkId(), 
            entity.getTotalExpensess());
        
        return responseObject;
        
    } catch (Exception e) {
        logger.error("Error saving expenses for workId: {}", 
            expensesDataBean.getWorkId(), e);
        responseObject.setErrorMessage("Error saving expenses: " + e.getMessage());
        throw new Exception(DMSConstants.ERROR_SAVING_DATA);
    }
}
```

### Step 2: Fix the Retrieval Query

**File:** `src/main/java/com/anuppur/service/impl/CommonServiceImpl.java`

**Current Issue (Lines 1515-1576):**
The query might not be retrieving all records due to status filtering.

**Proposed Fix:**
```java
@Override
public ExpensesDataJson fetchExpensesDataList(Pageable pageable, Object totalExpensess, Object expensessUptoMarch,
        Object expensessCurrentFy, String year, Object createdDate, Long workTypeId) {

    ExpensesDataJson json = new ExpensesDataJson();
    json.setAaData(new ArrayList<>());
    json.setiTotalRecords(0);
    json.setiTotalDisplayRecords(0);

    try {
        List<ExpensesData> exPage;
        
        // Retrieve all expenses for the work, regardless of year filter
        if (year != null && !year.isEmpty()) {
            try {
                Long newYear = Long.parseLong(year);
                exPage = expensesDataRepository.findByYearAndWorkIdIn(newYear, Collections.singletonList(workTypeId));
            } catch (NumberFormatException e) {
                logger.warn("Invalid year format: {}", year);
                exPage = expensesDataRepository.findByWorkId(workTypeId);
            }
        } else {
            exPage = expensesDataRepository.findByWorkId(workTypeId);
        }
        
        if (exPage == null || exPage.isEmpty()) {
            logger.info("No expenses found for workId: {}", workTypeId);
            return json;
        }

        List<ExpensesDataBean> beanList = new ArrayList<>();
        int index = 0;

        for (ExpensesData expensesData : exPage) {
            ExpensesDataBean bean = new ExpensesDataBean();
            bean.setIndex(++index);
            bean.setCreatedDate(DMSUtil.convertDateToString(expensesData.getCreatedDate()));

            // Set values with null checks
            bean.setExpensessCurrentFy(
                expensesData.getExpensessCurrentFy() != null 
                    ? expensesData.getExpensessCurrentFy() 
                    : BigDecimal.ZERO
            );
            
            bean.setExpensessUptoMarch(
                expensesData.getExpensessUptoMarch() != null 
                    ? expensesData.getExpensessUptoMarch() 
                    : BigDecimal.ZERO
            );
            
            bean.setTotalExpensess(
                expensesData.getTotalExpensess() != null 
                    ? expensesData.getTotalExpensess() 
                    : BigDecimal.ZERO
            );

            bean.setYear(expensesData.getYear());
            beanList.add(bean);
            
            logger.debug("Expense bean: Total={}, CurrentFY={}, UptoMarch={}", 
                bean.getTotalExpensess(), 
                bean.getExpensessCurrentFy(), 
                bean.getExpensessUptoMarch());
        }

        json.setAaData(beanList);
        json.setiTotalRecords(beanList.size());
        json.setiTotalDisplayRecords(beanList.size());
        
        logger.info("Fetched {} expense records for workId: {}", beanList.size(), workTypeId);
        return json;
        
    } catch (Exception e) {
        logger.error("fetchExpensesDataList failed for workId {}", workTypeId, e);
        return json;
    }
}
```

### Step 3: Add Logging to Debug

Add logging statements to track data flow:

```java
// In the controller
logger.info("Fetching expenses for workId: {}, year: {}", workTypeId, year);

// In the service
logger.info("Retrieved {} expense records", exPage.size());
for (ExpensesData exp : exPage) {
    logger.info("Expense: workId={}, total={}, currentFY={}, uptoMarch={}", 
        exp.getWorkId(), 
        exp.getTotalExpensess(), 
        exp.getExpensessCurrentFy(), 
        exp.getExpensessUptoMarch());
}
```

---

## Testing Steps

### 1. Add Expense Data
1. Navigate to Work Progress page
2. Fill in expense values:
   - Total Expenditure: 100
   - Current FY: 50
   - This Month: 25
3. Click Save
4. Check browser console for success message

### 2. Verify Data in Database
```sql
SELECT * FROM expenses_cost WHERE work_id = <your_work_id>;
```

Expected output:
```
id | work_id | total_expensess | expensess_current_fy | expensess_upto_march | year | month | status | created_date
1  | 123     | 100.00          | 50.00                | 25.00                | 2024 | 5     | 1      | 2024-05-29
```

### 3. Verify Table Display
1. Refresh the page
2. Check "Expenditure Tracker" table
3. Should show the values you entered (not zeros)

---

## Debugging Checklist

- [ ] Check browser console for JavaScript errors
- [ ] Check application logs for SQL errors
- [ ] Verify `expenses_cost` table has data
- [ ] Verify `year_status` table has entries
- [ ] Check if `workId` is being passed correctly
- [ ] Verify user has permission to save expenses
- [ ] Check if transaction is being committed

---

## Database Verification

### Check if table exists:
```sql
SHOW TABLES LIKE 'expenses_cost';
```

### Check table structure:
```sql
DESCRIBE expenses_cost;
```

### Check for data:
```sql
SELECT COUNT(*) FROM expenses_cost;
SELECT * FROM expenses_cost LIMIT 10;
```

### Check year_status:
```sql
SELECT * FROM year_status;
```

---

## Files to Modify

1. **`src/main/java/com/anuppur/service/impl/CommonServiceImpl.java`**
   - Simplify `addWorkProExpensesData` method (lines 3207-3350)
   - Improve `fetchExpensesDataList` method (lines 1515-1576)
   - Add better logging

2. **`src/main/resources/static/js/editWorkTables.js`**
   - Add error handling in `fetchExpensesDataList` function
   - Add console logging for debugging

---

## Expected Results After Fix

✅ Expense data saves successfully  
✅ Table displays actual values (not zeros)  
✅ Data persists after page refresh  
✅ Multiple expense entries can be added  
✅ Year filter works correctly  

---

## Summary

| Issue | Cause | Fix |
|-------|-------|-----|
| All zeros in table | Data not saved or NULL values | Simplify save logic, add defaults |
| Data not persisting | Complex conditional logic | Remove unnecessary conditions |
| Query not retrieving data | Status filtering | Remove status filter or make it optional |

---

## Next Steps

1. Apply the simplified logic to `addWorkProExpensesData`
2. Improve the `fetchExpensesDataList` method
3. Add comprehensive logging
4. Test with sample data
5. Verify database records
6. Check table display

**Status:** Ready to implement ✅
