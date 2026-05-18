# Manage Users Not Working - Diagnostic Guide

## Issue
After logging in, when you navigate to "Manage Users", the page is not working properly.

## Possible Causes

### 1. Page Not Loading (404 Error)
**URL**: `http://localhost:8085/anuppur/systemAdmin/manageusers`

**Check**:
- Open browser console (F12)
- Look for 404 errors
- Check if URL is correct

### 2. API Call Failing
The manage users page makes an AJAX call to fetch user data:
**API**: `http://localhost:8085/anuppur/systemAdmin/fetchUserList`

**Check**:
- Open browser console (F12) → Network tab
- Look for failed API calls (red entries)
- Check the response status code

### 3. JavaScript Errors
**Check**:
- Open browser console (F12) → Console tab
- Look for JavaScript errors (red text)
- Common errors:
  - `$ is not defined` - jQuery not loaded
  - `angular is not defined` - AngularJS not loaded
  - `DataTable is not a function` - DataTables plugin not loaded

### 4. Authorization Error (403 Forbidden)
**Check**:
- Your user role must be one of:
  - `ROLE_SYSTEM_ADMIN`
  - `ROLE_SU`
  - `ROLE_SAU`
  - `ROLE_DEPARTMENT`
  - `ROLE_CEO`

## How to Diagnose

### Step 1: Check Browser Console

1. **Open the application**: `http://localhost:8085/anuppur/`
2. **Login** with your credentials
3. **Open Developer Tools**: Press `F12`
4. **Navigate to Manage Users**
5. **Check Console tab** for errors
6. **Check Network tab** for failed requests

### Step 2: Check Terminal/Eclipse Console

Look for errors in your application logs:

**Common errors**:
```
java.lang.NullPointerException
org.springframework.security.access.AccessDeniedException
org.hibernate.exception.SQLGrammarException
```

### Step 3: Test API Directly

After logging in, test the API directly in browser:

```
http://localhost:8085/anuppur/systemAdmin/fetchUserList?iDisplayStart=0&iDisplayLength=10
```

**Expected**: JSON response with user data
**If 403**: Authorization issue - check user roles
**If 500**: Server error - check terminal logs
**If 404**: Routing issue - check controller mapping

## Common Issues and Solutions

### Issue 1: "Page is blank" or "Loading forever"

**Cause**: API call failing or returning error

**Solution**:
1. Check browser console for errors
2. Check Network tab for failed API calls
3. Check terminal for server errors
4. Verify database connection is working

### Issue 2: "Access Denied" or 403 Error

**Cause**: User doesn't have required role

**Solution**:
Check user roles in database:
```sql
USE dhs_anuppur;

SELECT u.username, r.role_code, r.role_name
FROM users u
JOIN user_role ur ON u.id = ur.id
JOIN role r ON ur.role_code = r.role_code
WHERE u.username = 'YOUR_USERNAME';
```

Required roles for Manage Users:
- `ROLE_SYSTEM_ADMIN`
- `ROLE_SU`
- `ROLE_SAU`
- `ROLE_DEPARTMENT`
- `ROLE_CEO`

### Issue 3: DataTable not loading

**Cause**: JavaScript libraries not loaded

**Solution**:
Check if these files are loading (Network tab):
- `/anuppur/js/jquery.min.js`
- `/anuppur/js/jquery.dataTables.min.js`
- `/anuppur/js/dataTables.bootstrap.min.js`
- `/anuppur/angular/angular.min.js`

### Issue 4: API returns empty data

**Cause**: No users in database or filter issue

**Solution**:
Check if users exist:
```sql
USE dhs_anuppur;

SELECT COUNT(*) as user_count
FROM users
WHERE status != 'DELETED';
```

## What to Share for Help

If the issue persists, share these details:

### 1. Browser Console Errors
```
Press F12 → Console tab → Copy all red errors
```

### 2. Network Tab Errors
```
Press F12 → Network tab → Find failed requests (red) → Copy:
- Request URL
- Status Code
- Response
```

### 3. Terminal/Eclipse Console Errors
```
Copy the last 50 lines from your application console
```

### 4. User Role Information
```sql
SELECT u.username, r.role_code, r.role_name
FROM users u
JOIN user_role ur ON u.id = ur.id
JOIN role r ON ur.role_code = r.role_code
WHERE u.username = 'YOUR_USERNAME';
```

## Quick Test Script

Run this in browser console after logging in:

```javascript
// Test if jQuery is loaded
console.log("jQuery version:", $.fn.jquery);

// Test if DataTables is loaded
console.log("DataTables loaded:", typeof $.fn.DataTable);

// Test if Angular is loaded
console.log("Angular loaded:", typeof angular);

// Test API call
fetch('/anuppur/systemAdmin/fetchUserList?iDisplayStart=0&iDisplayLength=10')
  .then(response => {
    console.log("API Status:", response.status);
    return response.json();
  })
  .then(data => console.log("API Data:", data))
  .catch(error => console.error("API Error:", error));
```

## URLs to Check

After logging in, these URLs should work:

1. **Home**: `http://localhost:8085/anuppur/systemAdmin/home`
2. **Manage Users Page**: `http://localhost:8085/anuppur/systemAdmin/manageusers`
3. **Fetch Users API**: `http://localhost:8085/anuppur/systemAdmin/fetchUserList`

## Next Steps

1. **Follow Step 1-3** in "How to Diagnose" section
2. **Share the errors** you find (console, network, terminal)
3. **Share your user role** from the SQL query
4. Based on the errors, I can provide specific fixes

## Related Files

- Controller: `src/main/java/com/anuppur/controller/SystemAdminController.java`
- Template: `src/main/resources/templates/superAdmin/manageUsers.html`
- Service: `src/main/java/com/anuppur/service/impl/SystemAdminServiceImpl.java`
