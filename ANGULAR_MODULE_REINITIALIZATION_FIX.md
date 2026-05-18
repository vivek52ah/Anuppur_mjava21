# Angular Module Reinitialization Fix

## Problem Identified
After login, buttons and navigation were not working. The root cause was **Angular module reinitialization** when multiple routing files were loaded.

### What Was Happening
1. `AdminRouting.js` created the `dms` module with all configurations including the `$httpProvider` interceptor
2. `SystemAdminRouting.js` then created a NEW `dms` module, which **reinitializes** the module
3. When a module is reinitialized, all previous configurations (including the $httpProvider interceptor) are lost
4. Without the interceptor, AJAX calls couldn't resolve relative URLs correctly
5. This caused navigation and API calls to fail

### The Angular Module Pattern
In AngularJS, when you call:
```javascript
angular.module('dms', ['ngRoute', ...])  // WITH dependencies array
```
It **creates a new module** and overwrites any existing one.

When you call:
```javascript
angular.module('dms')  // WITHOUT dependencies array
```
It **retrieves the existing module** without reinitializing it.

## Solution Implemented

### 1. AdminRouting.js (PRIMARY MODULE CREATOR)
- Keeps the full module creation with all dependencies
- Keeps all configurations (Idle/Keepalive, $httpProvider interceptor)
- This is the FIRST routing file loaded, so it initializes everything

### 2. SystemAdminRouting.js (SECONDARY - GETS EXISTING MODULE)
- Changed from: `var dms = angular.module('dms', ['ngRoute', ...])`
- Changed to: `var dms = angular.module('dms')`
- Removed duplicate Idle/Keepalive configuration
- Removed duplicate $httpProvider interceptor configuration
- Now just adds routes to the existing module

### 3. SuperAdminRouting.js (SECONDARY - GETS EXISTING MODULE)
- Changed from: `var dms = angular.module('dms', ['ngRoute', ...])`
- Changed to: `var dms = angular.module('dms')`
- Removed duplicate Idle/Keepalive configuration
- Removed duplicate $httpProvider interceptor configuration
- Now just adds routes to the existing module

## Files Modified
1. `src/main/resources/static/angular/admin/AdminRouting.js`
2. `src/main/resources/static/angular/systemAdmin/SystemAdminRouting.js`
3. `src/main/resources/static/angular/superAdmin/SuperAdminRouting.js`

## Deployment
All files have been copied to `target/classes/` for immediate deployment without requiring a Maven rebuild.

## Expected Behavior After Fix
✅ Navigation menu items work without logout
✅ AJAX calls resolve correctly (no 404 errors)
✅ Session persists across page navigation
✅ All buttons and links function properly
✅ Back button works correctly

## Technical Details
The $httpProvider interceptor in AdminRouting.js:
- Intercepts all $http requests
- Checks if URL matches API pattern (fetch, add, edit, delete, save, upload, etc.)
- Prepends the correct controller path from `window.__BASE_HREF_AJAX_BASE`
- This is set by `base-href-fix.js` based on the current page URL

This interceptor is now preserved throughout the application lifecycle because the module is no longer reinitialized.
