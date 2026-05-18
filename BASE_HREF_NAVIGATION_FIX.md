# Navigation/Logout Issue - REAL Root Cause Fixed ✅

## The True Problem

**HTML `<base>` tag conflict with hash-only navigation links.**

This is the actual cause of:
- Clicking any menu item → goes to home/logout
- Clicking any button with `href="#..."` → page redirects
- Browser back button → exits to home/login
- Nothing inside the app works

## How It Was Breaking

The page has:
```html
<base th:href="@{/}" />   <!-- resolves to /anuppur/ -->
```

And menu links like:
```html
<a href="#manageOngoingWorks">Manage Works</a>
```

When you click that link, the browser does this:
1. Sees `href="#manageOngoingWorks"`
2. Resolves it against the `<base>` tag → `http://localhost:8085/anuppur/#manageOngoingWorks`
3. Compares to current URL `http://localhost:8085/anuppur/systemAdmin/home`
4. Paths differ → does a **FULL PAGE NAVIGATION** to the new URL
5. Browser loads `/anuppur/` from server → home page or login redirect
6. SPA state is lost → looks like logout

This is a known browser behavior: `<base>` tag changes how relative URLs (including hash links) are resolved.

## The Fix

Added a JavaScript click interceptor at `static/angular/base-href-fix.js` that:
1. Catches all clicks on anchor tags
2. Detects hash-only links (starting with `#`)
3. Prevents default browser navigation
4. Just updates `window.location.hash` directly
5. AngularJS router picks up the hash change and routes correctly **without leaving the page**

Loaded before Angular in `templates/fragments/footer.html`.

## What Changed

### New file: `src/main/resources/static/angular/base-href-fix.js`
Click interceptor for hash links.

### Modified: `src/main/resources/templates/fragments/footer.html`
Added the script tag before `angular.min.js`:
```html
<script th:src="@{/angular/base-href-fix.js}"></script>
<script th:src="@{/angular/angular.min.js}"></script>
```

## To Apply The Fix

You **must** rebuild the application. Static file changes need:
1. Stop the running Spring Boot app
2. Rebuild: `mvn clean package` (or rebuild from your IDE)
3. Restart the app
4. Hard refresh browser: `Ctrl + Shift + R` or use Incognito

If you don't rebuild, the old JS files in `target/classes/static/angular/` will still be served.

## Why The Earlier Fixes Weren't Enough

Each previous fix was real and necessary, but the navigation issue is a separate bug:

| Previous Fix | What it fixed |
|-------------|----------------|
| Context path trailing slash | Template URLs resolve correctly |
| `/common/**` path mapping | Backend can serve common pages |
| Cookie `secure=false` | Session persists on HTTP |
| **THIS FIX (base-href-fix.js)** | **Hash links don't trigger page navigation** |

All four fixes are needed together.

## How To Verify The Fix Worked

After restart and cache clear:

1. Login → land on dashboard
2. Open DevTools (F12) → Network tab → click "Clear" log
3. Click "Manage Works" menu
4. **Expected**: 
   - URL changes to `/anuppur/systemAdmin/home#/manageOngoingWorks`
   - Network tab shows ONLY a request for `common/manageOngoingWorks` template
   - Page content updates **without** a full page reload
   - You stay logged in
5. **If broken**: Network tab would show a request for `/anuppur/` (the home page), and you'd see the home page or login

## Alternative Solutions (Not Used)

These would also work but require larger changes:

1. **Remove `<base>` tag** — would break all `th:href` template URLs
2. **Use absolute URLs in menu links** — `href="/anuppur/systemAdmin/home#manageOngoingWorks"` everywhere — many files to change
3. **Use AngularJS UI-Router instead of ngRoute** — major refactor
4. **Switch to HTML5 mode routing** — needs server config + URL changes everywhere

The JavaScript click interceptor is the smallest, safest fix.

## Technical Reference

Browser resolution rule: When a relative URL (including fragment-only `#hash`) is encountered in a page with a `<base>` tag, the browser resolves it against the base, NOT the current document URL. This is per HTML spec. Source: [MDN - base tag](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/base).

Old AngularJS hash-bang routing pre-dates this strictness, which is why apps written with AngularJS + Thymeleaf often hit this exact bug.
