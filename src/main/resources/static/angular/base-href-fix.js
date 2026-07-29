/**
 * Base Href Fix for Hash-Only Anchor Links AND Relative AJAX URLs
 * 
 * Fixes the conflict between <base href="/anuppur/"> and:
 * 1. Hash-only navigation links (e.g., <a href="#manageOngoingWorks">)
 * 2. Relative AJAX URLs in jQuery/DataTables (e.g., "fetchUserList")
 * 3. Relative AJAX URLs in AngularJS $http (e.g., "fetchWorksList")
 */
(function() {
    'use strict';

    function readCookie(name) {
        var prefix = name + '=';
        var cookies = document.cookie ? document.cookie.split(';') : [];
        for (var i = 0; i < cookies.length; i++) {
            var cookie = cookies[i].trim();
            if (cookie.indexOf(prefix) === 0) {
                return decodeURIComponent(cookie.substring(prefix.length));
            }
        }
        return null;
    }

    function isSafeMethod(method) {
        return /^(GET|HEAD|OPTIONS|TRACE)$/i.test(method || 'GET');
    }

    function isSameOrigin(url) {
        var anchor = document.createElement('a');
        anchor.href = url || window.location.href;
        return anchor.protocol === window.location.protocol && anchor.host === window.location.host;
    }

    // Get the controller base path from current page URL
    // e.g., /anuppur/systemAdmin/home -> /anuppur/systemAdmin/
    var pagePath = window.location.pathname;
    var ajaxBase = pagePath.substring(0, pagePath.lastIndexOf('/') + 1);

    // === FIX 1: Hash-only link click interception ===
    document.addEventListener('click', function(e) {
        var target = e.target;
        while (target && target.nodeName !== 'A') {
            if (target === document.body) return;
            target = target.parentNode;
        }
        if (!target || target.nodeName !== 'A') return;

        var rawHref = target.getAttribute('href');
        if (!rawHref) return;
        if (e.ctrlKey || e.metaKey || e.shiftKey || e.altKey) return;
        if (rawHref.charAt(0) !== '#') return;
        if (rawHref === '#' || rawHref === '') return;

        e.preventDefault();
        e.stopPropagation();

        var hashValue = rawHref.substring(1);
        if (window.location.hash === '#' + hashValue) {
            window.location.hash = '';
            setTimeout(function() { window.location.hash = hashValue; }, 0);
        } else {
            window.location.hash = hashValue;
        }
    }, true);

    // === FIX 2: jQuery AJAX relative URL fix ===
    // jQuery is loaded before this script in footer.html
    if (typeof jQuery !== 'undefined') {
        jQuery.ajaxPrefilter(function(options) {
            if (options.url && !options.url.match(/^(\/|https?:\/\/|data:|#)/)) {
                // Compute base path dynamically on each request
                var currentPath = window.location.pathname;
                var base = currentPath.substring(0, currentPath.lastIndexOf('/') + 1);
                options.url = base + options.url;
            }

            if (!isSafeMethod(options.type) && isSameOrigin(options.url)) {
                var csrfToken = readCookie('XSRF-TOKEN');
                if (csrfToken) {
                    options.headers = options.headers || {};
                    options.headers['X-XSRF-TOKEN'] = csrfToken;
                }
            }
        });
    }

    // Protect dynamically-created same-origin POST forms (for example exports).
    document.addEventListener('submit', function(event) {
        var form = event.target;
        if (!form || form.nodeName !== 'FORM' || isSafeMethod(form.method)) return;
        if (!isSameOrigin(form.action)) return;
        if (form.querySelector('input[name="_csrf"]')) return;

        var csrfToken = readCookie('XSRF-TOKEN');
        if (csrfToken) {
            var input = document.createElement('input');
            input.type = 'hidden';
            input.name = '_csrf';
            input.value = csrfToken;
            form.appendChild(input);
        }
    }, true);

    // === FIX 3: AngularJS $http relative URL fix ===
    // Angular loads after this script, so we set up a config block
    // that will run when the Angular app bootstraps
    window.__BASE_HREF_AJAX_BASE = ajaxBase;

})();
