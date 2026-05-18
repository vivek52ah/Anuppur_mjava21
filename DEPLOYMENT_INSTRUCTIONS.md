# Deployment Instructions

## Status
✅ All fixes have been applied and copied to `target/classes/`
✅ Ready for immediate deployment

## What Was Changed

### Modified Files (3 total)
1. `src/main/resources/static/angular/admin/AdminRouting.js`
2. `src/main/resources/static/angular/systemAdmin/SystemAdminRouting.js`
3. `src/main/resources/static/angular/superAdmin/SuperAdminRouting.js`

### Change Summary
- **AdminRouting.js**: Creates the Angular module (PRIMARY)
- **SystemAdminRouting.js**: Gets existing module instead of recreating it
- **SuperAdminRouting.js**: Gets existing module instead of recreating it

## Deployment Steps

### Option 1: Quick Deploy (Recommended - No Maven Rebuild)
1. Stop the running application
2. Files are already copied to `target/classes/`
3. Restart the application
4. Test the fix (see Testing section below)

### Option 2: Full Maven Rebuild
1. Stop the running application
2. Run: `mvn clean install`
3. Start the application
4. Test the fix (see Testing section below)

### Option 3: Manual Deployment
1. Stop the running application
2. Copy files from `src/main/resources/` to `target/classes/`:
   ```
   src/main/resources/static/angular/admin/AdminRouting.js
   → target/classes/static/angular/admin/AdminRouting.js
   
   src/main/resources/static/angular/systemAdmin/SystemAdminRouting.js
   → target/classes/static/angular/systemAdmin/SystemAdminRouting.js
   
   src/main/resources/static/angular/superAdmin/SuperAdminRouting.js
   → target/classes/static/angular/superAdmin/SuperAdminRouting.js
   ```
3. Restart the application
4. Test the fix (see Testing section below)

## Testing the Fix

### Step 1: Login
1. Open browser to `http://localhost:8085/anuppur/`
2. Login with valid credentials
3. Verify dashboard loads with data

### Step 2: Test Navigation
1. Click "View Works List" in the sidebar
2. Verify page loads without logout
3. Click "Reports" in the sidebar
4. Verify page loads without logout

### Step 3: Verify AJAX Calls
1. Open browser DevTools (Press F12)
2. Go to Network tab
3. Click on any menu item
4. Look for API calls (e.g., `fetchUserList`, `fetchWorkType`)
5. Verify they show **200 status** (not 404)
6. Verify URLs are correct (e.g., `/anuppur/systemAdmin/fetchUserList`)

### Step 4: Test Session Persistence
1. Navigate between different pages
2. Verify you stay logged in
3. Verify session cookie is present in DevTools (Application tab → Cookies)

### Step 5: Test Back Button
1. Navigate to a page
2. Click browser back button
3. Verify it goes back without logging out

### Step 6: Check Console for Errors
1. Open browser console (F12 → Console tab)
2. Look for JavaScript errors
3. Expected warnings (non-blocking):
   - `$compile:tpload` - Angular template loading
   - `$http_INVALID_FILE_CHARSET_ENCODING` - File encoding
4. Should NOT see:
   - 404 errors for API calls
   - "Cannot read property of undefined" errors
   - Module reinitialization errors

## Verification Checklist

### Before Deployment
- [ ] All three routing files are updated in `src/main/resources/`
- [ ] All three routing files are copied to `target/classes/`
- [ ] `base-href-fix.js` is in place
- [ ] `application-local.properties` has correct settings:
  - `server.servlet.context-path=/anuppur/`
  - `server.servlet.session.cookie.secure=false`

### After Deployment
- [ ] Application starts without errors
- [ ] Login works
- [ ] Dashboard loads with data
- [ ] Navigation menu items work
- [ ] AJAX calls show 200 status
- [ ] Session persists across pages
- [ ] Back button works
- [ ] No JavaScript errors in console

## Rollback Instructions (If Needed)

If something goes wrong, you can rollback:

1. Stop the application
2. Restore original files from git:
   ```
   git checkout src/main/resources/static/angular/admin/AdminRouting.js
   git checkout src/main/resources/static/angular/systemAdmin/SystemAdminRouting.js
   git checkout src/main/resources/static/angular/superAdmin/SuperAdminRouting.js
   ```
3. Run Maven rebuild: `mvn clean install`
4. Restart the application

## Troubleshooting

### Issue: Still getting 404 errors on AJAX calls
**Solution**: 
1. Clear browser cache (Ctrl+Shift+Delete)
2. Hard refresh (Ctrl+F5)
3. Check that files are in `target/classes/`
4. Verify `base-href-fix.js` is loaded (check Network tab)

### Issue: Navigation still redirects to home page
**Solution**:
1. Check browser console for JavaScript errors
2. Verify `AdminRouting.js` is loaded first
3. Verify `SystemAdminRouting.js` is loaded after
4. Check that `window.__BASE_HREF_AJAX_BASE` is set (type in console)

### Issue: Session still lost on navigation
**Solution**:
1. Check `application-local.properties` for correct settings
2. Verify `server.servlet.session.cookie.secure=false`
3. Clear browser cookies and login again
4. Check that session cookie is being set (DevTools → Application → Cookies)

### Issue: Module reinitialization errors in console
**Solution**:
1. Verify `SystemAdminRouting.js` uses `angular.module('dms')` (no dependencies)
2. Verify `SuperAdminRouting.js` uses `angular.module('dms')` (no dependencies)
3. Check that `AdminRouting.js` is loaded first
4. Clear browser cache and hard refresh

## Support

If you encounter any issues:
1. Check the console for error messages
2. Review the COMPLETE_SOLUTION_GUIDE.md for technical details
3. Verify all files are in place
4. Try clearing browser cache and restarting the application

## Summary

This fix resolves the post-login navigation issues by ensuring the Angular module is created once and reused throughout the application lifecycle, preserving the $httpProvider interceptor that enables AJAX URL fixing.

**Expected Result**: Full functionality after login with working navigation, AJAX calls, and session persistence.
