# Diagnosis and Fixes for manageusers and addUserForm Issues

## Issue 1: manageusers Page Not Loading Data

### Root Cause:
The `manageUsers.html` template defines the DataTable initialization function as `initrrsTable`, but the SystemAdminController is looking for `initManageUsersTable`.

### Fix Applied:
Added alias in `manageUsers.html`:
```javascript
window.initrrsTable = initrrsTable;
window.initManageUsersTable = initrrsTable; // Alias for compatibility
```

### Additional Check Needed:
The backend query filters users by `createdBy` for ROLE_DEPARTMENT. This means you'll only see Area Officers that YOU created. If you haven't created any users yet, the table will be empty.

**To verify:**
1. Check if you have created any Area Officers
2. The query in `SuperAdminServiceImpl.java` line 169-197 filters by:
   - `designationId = 1` (Area Officer)
   - `createdBy = your username`
   - `status NOT IN ('Deleted', 'Pending Verification')`

---

## Issue 2: Submit Button Not Working in Add User Form

### Potential Root Causes:

#### A. Password Validation Disabling Submit Button
The form has JavaScript that disables the submit button until passwords match:
```javascript
function check_pass() {
    if (password && confirmPassword) {
        if (password == confirmPassword) {
            document.getElementById('submit').disabled = false; // Enable
        } else {
            document.getElementById('submit').disabled = true; // Disable
        }
    }
}
```

**Problem:** The button might be disabled initially or if passwords don't match.

#### B. Angular Form Validation
The form uses Angular validation with `data-ng-submit="addUser(userForm.$valid)"`. If the form is invalid, the submit won't trigger.

#### C. Missing Angular Controller Initialization
The form needs the SystemAdminController to be properly initialized with the `addUser` function.

---

## Fixes to Apply:

### Fix 1: Ensure Submit Button is Enabled by Default
