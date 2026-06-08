# Expenditure Tracker - Testing Guide

## Quick Test Procedure

### Step 1: Prepare
- Build and deploy the application with the fixes
- Log in as a user with work progress entry permissions
- Navigate to a work that has status 10 or 11

### Step 2: Enter Expense Data
1. Go to **Edit Work Progress** page
2. Scroll to the **Expenditure** section (Step 5)
3. Fill in the three expense fields:
   - **Expenditure in Last Financial Year (In Lacs)**: Enter a value (e.g., 100)
   - **Expenditure in Current Financial Year (In Lacs)**: Enter a value (e.g., 50)
   - **Expenditure in this Month (In Lacs)**: Enter a value (e.g., 10)

### Step 3: Submit
1. Click the **Submit** button
2. Wait for the success message to appear
3. Verify the message says "Work saved successfully!"

### Step 4: Verify Expenditure Tracker
1. Look at the **Expenditure Tracker** table on the same page
2. Verify that the table shows the values you entered (NOT zeros)
3. The table should display:
   - Your entered values in the corresponding columns
   - Calculated totals if applicable

### Step 5: Verify Data Persistence
1. Refresh the page (F5)
2. Verify that:
   - The form fields still contain the values you entered
   - The Expenditure Tracker table still shows the same values
   - No data was lost

### Step 6: Verify Database
1. Check the database tables:
   - `work_progress` table should have the expense values
   - `expenses_cost` table should have records for the work

## Expected Results

### Before Fix
- Form fields were empty when loading the page
- Expenditure Tracker showed all zeros
- Data was not saved to the database

### After Fix
- Form fields are populated with previously saved values
- Expenditure Tracker displays the entered values
- Data is saved to both `work_progress` and `expenses_cost` tables
- Values persist after page reload

## Troubleshooting

### Issue: Form fields still empty after page load
- **Cause**: Backend not returning `expensessCurrentFy` value
- **Solution**: Verify that line 4520 in `CommonServiceImpl.java` is uncommented

### Issue: Expenditure Tracker still shows zeros
- **Cause**: `createWorkProExpensesData()` not being called
- **Solution**: Verify that lines 3151 and 3263 in `CommonController.js` are updated

### Issue: Data not saving to database
- **Cause**: Backend endpoint not receiving the data
- **Solution**: Check browser console for JavaScript errors and backend logs for exceptions

## Browser Console Checks

Open the browser's Developer Tools (F12) and check the Console tab:

1. **No JavaScript errors** should appear
2. **Network tab** should show:
   - POST to `/addWorkProgress` → 200 OK
   - POST to `/addWorkProExpensesData` → 200 OK
3. **Response** should contain `successMessage: "Work saved successfully!"`

## Database Verification

Run these SQL queries to verify data is being saved:

```sql
-- Check work_progress table
SELECT workId, expensessCurrentFy, expensessUptoMarch, totalExpensess 
FROM work_progress 
WHERE workId = [YOUR_WORK_ID];

-- Check expenses_cost table
SELECT workId, expensessCurrentFy, expensessUptoMarch, totalExpensess, month, year 
FROM expenses_cost 
WHERE workId = [YOUR_WORK_ID];
```

Both tables should have records with the values you entered.

## Contact Support

If issues persist after applying the fixes, check:
1. Application logs for exceptions
2. Browser console for JavaScript errors
3. Network tab for failed API calls
4. Database for data integrity
