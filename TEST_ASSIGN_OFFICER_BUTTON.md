# Test Assign Officer Button - Debugging Guide

## Issue
The "Assign Officer" button in editTender.html is not opening the modal.

## Fixes Applied

### Fix #1: Enhanced openModal Function with Debugging
**File:** `src/main/resources/static/angular/common/CommonController.js`
**Line:** ~537

Added comprehensive debugging and error handling:
- Console logs to track function execution
- Better error messages with alerts
- Multiple fallback methods for opening modal
- Checks for jQuery, Bootstrap 5, and CSS fallback

### Fix #2: Safer Button Click Handler
**File:** `src/main/resources/templates/common/work/editTender.html`
**Line:** ~1560

Added null safety to button parameters:
```html
ng-click="openModal(workData.workId || null, workData.userAssignee || null)"
```

---

## How to Test

### Step 1: Rebuild the Application
```cmd
REBUILD_APPLICATION.bat
```
Wait for "BUILD SUCCESS"

### Step 2: Start the Application
```cmd
run.bat
```
Wait for "Started DmsAnuppurApplication"

### Step 3: Clear Browser Cache
- Press **Ctrl + Shift + Delete**
- Clear "Cached images and files"
- Click "Clear data"

### Step 4: Open Browser Console
- Press **F12**
- Go to "Console" tab
- Keep it open while testing

### Step 5: Test the Button

1. Login to the application
2. Navigate to Edit Work page
3. Go to "Sanction Details / Edit Tender" tab
4. Scroll down to "Assign Area Officer" section
5. Click the "Assign Officer" button

### Step 6: Check Console Output

You should see console logs like:
```
openModal called with workid: 123 userid: 456
Calling global window.openModal function
```

OR if using fallback:
```
openModal called with workid: 123 userid: 456
Using fallback modal opening method
Modal element found: [object HTMLDivElement]
Opening modal with jQuery
```

---

## Expected Behavior

✅ **Success:** Modal popup opens showing "Area Officers" list
✅ **Success:** Console shows debug messages
✅ **Success:** No errors in console

---

## Possible Issues and Solutions

### Issue 1: Console shows "openModal called" but nothing happens

**Possible Cause:** Modal element not found or jQuery not loaded

**Check:**
```javascript
// In browser console, type:
document.getElementById('exampleModal2')
// Should return: <div class="modal fade" id="exampleModal2"...>

typeof $
// Should return: "function"

typeof $.fn.modal
// Should return: "function"
```

**Solution:** Make sure you're on the correct tab (Sanction Details / Edit Tender)

---

### Issue 2: Console shows "Modal element #exampleModal2 not found"

**Possible Cause:** The modal HTML is not loaded in the current view

**Check:**
1. Make sure you're on the "Sanction Details / Edit Tender" tab
2. The modal is defined at the bottom of editTender.html
3. The tab content might not be loaded yet

**Solution:** 
- Click on another tab first, then come back to "Sanction Details / Edit Tender"
- Wait a few seconds for the page to fully load
- Try clicking the button again

---

### Issue 3: Button click does nothing, no console logs

**Possible Cause:** AngularJS not initialized or scope issue

**Check:**
```javascript
// In browser console, type:
angular.element(document.querySelector('[ng-controller="CommonController"]')).scope()
// Should return the scope object

// Check if workData exists:
angular.element(document.querySelector('[ng-controller="CommonController"]')).scope().workData
// Should return an object with workId
```

**Solution:**
1. Make sure the page is fully loaded
2. Check if there are any JavaScript errors in console (red text)
3. Try refreshing the page with Ctrl+F5

---

### Issue 4: Console shows "$ is not defined" or "$.fn.modal is not a function"

**Possible Cause:** jQuery or Bootstrap not loaded

**Check:**
```javascript
// In browser console:
typeof $
// Should return: "function"

typeof $.fn.modal
// Should return: "function"
```

**Solution:** Check if jQuery and Bootstrap JS are loaded in the page:
1. Open Network tab in DevTools (F12)
2. Refresh page
3. Look for jquery.js and bootstrap.js files
4. Make sure they loaded successfully (status 200)

---

### Issue 5: Modal opens but shows empty or no data

**Possible Cause:** DataTable not initialized or data not loaded

**Check:**
```javascript
// In browser console:
typeof t2
// Should return: "object" (DataTable instance)

typeof fetchUserList
// Should return: "function"
```

**Solution:** The modal should call `fetchUserList()` or `t2.draw()` automatically. If not:
1. Check console for errors
2. Make sure `loadUserList()` is called (it's in ng-init on the modal container)

---

## Manual Testing Commands

Open browser console (F12) and try these commands:

### Test 1: Check if modal element exists
```javascript
document.getElementById('exampleModal2')
```
**Expected:** Should return the modal div element

### Test 2: Check if jQuery is loaded
```javascript
typeof $ && typeof $.fn.modal
```
**Expected:** Should return "function"

### Test 3: Manually open modal with jQuery
```javascript
$('#exampleModal2').modal('show')
```
**Expected:** Modal should open

### Test 4: Check if openModal function exists
```javascript
typeof window.openModal
```
**Expected:** Should return "function"

### Test 5: Manually call openModal
```javascript
// Get the scope
var scope = angular.element(document.querySelector('[ng-controller="CommonController"]')).scope();
// Call openModal
scope.openModal(scope.workData.workId, scope.workData.userAssignee);
```
**Expected:** Modal should open

### Test 6: Check workData values
```javascript
var scope = angular.element(document.querySelector('[ng-controller="CommonController"]')).scope();
console.log('workId:', scope.workData.workId);
console.log('userAssignee:', scope.workData.userAssignee);
```
**Expected:** Should show the work ID and user assignee values

---

## Quick Fix if Modal Still Won't Open

If the modal still won't open after all fixes, try this temporary workaround:

### Option A: Use onclick instead of ng-click

Replace the button in editTender.html:
```html
<button type="button" class="btn btn-success"
    onclick="openModal(window.currentWorkId, window.currentUser)">
    Assign Officer
</button>
```

### Option B: Add a wrapper function

In editTender.html, add this script before the button:
```html
<script>
function openModalWrapper() {
    var scope = angular.element(document.querySelector('[ng-controller="CommonController"]')).scope();
    scope.$apply(function() {
        scope.openModal(scope.workData.workId, scope.workData.userAssignee);
    });
}
</script>

<button type="button" class="btn btn-success" onclick="openModalWrapper()">
    Assign Officer
</button>
```

---

## Summary

**Files Modified:**
1. `CommonController.js` - Enhanced openModal function with debugging
2. `editTender.html` - Added null safety to button click

**Next Steps:**
1. Rebuild application
2. Clear browser cache
3. Test with browser console open
4. Check console logs for debugging info
5. Report what you see in the console

---

## Success Criteria

✅ Button is visible
✅ Button is clickable (cursor changes to pointer)
✅ Console shows "openModal called with workid: X userid: Y"
✅ Modal popup appears
✅ Modal shows "Area Officers" title
✅ Modal shows user list table
✅ No errors in console

---

**After rebuild, test the button and check the browser console for debug messages!**
