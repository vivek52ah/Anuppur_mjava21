package com.anuppur.security;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

/**
 * Owns the application security headers and adds a per-response CSP nonce to
 * server-rendered HTML. Keeping this in one filter avoids conflicting header
 * writers. Legacy jQuery widgets receive the current nonce when they create
 * script/style elements; inline JavaScript and unsafe-eval remain disabled.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class SecurityResponseHeadersFilter extends OncePerRequestFilter {

    static final String CSP_NONCE_ATTRIBUTE = SecurityResponseHeadersFilter.class.getName() + ".nonce";

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final Pattern SCRIPT_TAG = Pattern.compile("(?i)<script(?![^>]*\\bnonce\\s*=)([^>]*)>");
    private static final Pattern STYLE_TAG = Pattern.compile("(?i)<style(?![^>]*\\bnonce\\s*=)([^>]*)>");
    private static final Pattern HTML_TAG = Pattern.compile("(?i)<html(?![^>]*\\bng-csp\\b)([^>]*)>");
    private static final Pattern HEAD_TAG = Pattern.compile("(?i)<head([^>]*)>");
    private static final Pattern EVENT_HANDLER = Pattern.compile(
            "(?is)\\son[a-z]+\\s*=\\s*([\\\"'])(.*?)\\1");
    private static final String CSP_COMPATIBILITY_BOOTSTRAP = "<script nonce=\"%s\">"
            + "(function(d){var create=d.createElement,n=d.currentScript.nonce;"
            + "d.createElement=function(name){var el=create.apply(this,arguments);"
            + "if(typeof name==='string'&&/^(script|style)$/i.test(name)){el.setAttribute('nonce',n);}"
            + "return el;};})(document);</script>";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String nonce = createNonce();
        request.setAttribute(CSP_NONCE_ATTRIBUTE, nonce);
        SanitizingResponseWrapper sanitizedResponse = new SanitizingResponseWrapper(
                response, isDownloadRequest(request));

        if (!isHtmlNavigation(request)) {
            chain.doFilter(request, sanitizedResponse);
            finalizeHeaders(sanitizedResponse, nonce, Set.of());
            sanitizedResponse.commitDeferredHeaders();
            return;
        }

        ContentCachingResponseWrapper wrapper = new ContentCachingResponseWrapper(sanitizedResponse);
        chain.doFilter(request, wrapper);

        byte[] body = wrapper.getContentAsByteArray();
        Set<String> eventHandlerHashes = Set.of();
        if (isHtml(wrapper) && body.length > 0) {
            Charset charset = responseCharset(wrapper);
            String html = new String(body, charset);
            eventHandlerHashes = attributeHashes(EVENT_HANDLER, html);
            html = addNonce(SCRIPT_TAG, "script", html, nonce);
            html = addNonce(STYLE_TAG, "style", html, nonce);
            html = addCompatibilityBootstrap(html, nonce);
            html = HTML_TAG.matcher(html).replaceFirst(
                    Matcher.quoteReplacement("<html ng-csp=\"no-unsafe-eval\"") + "$1>");

            byte[] securedBody = html.getBytes(charset);
            wrapper.resetBuffer();
            wrapper.setContentLength(securedBody.length);
            wrapper.getOutputStream().write(securedBody);
        }

        finalizeHeaders(wrapper, nonce, eventHandlerHashes);
        sanitizedResponse.commitDeferredHeaders();
        wrapper.copyBodyToResponse();
    }

    private void finalizeHeaders(HttpServletResponse response, String nonce, Set<String> eventHandlerHashes) {
        response.setHeader("Content-Security-Policy", policy(nonce, eventHandlerHashes));
    }

    private String policy(String nonce, Set<String> eventHandlerHashes) {
        String scriptAttributes = eventHandlerHashes.isEmpty()
                ? "script-src-attr 'none'; "
                : "script-src-attr 'unsafe-hashes' " + String.join(" ", eventHandlerHashes) + "; ";

        return "default-src 'self'; base-uri 'self'; object-src 'none'; frame-ancestors 'none'; "
                + "form-action 'self'; script-src 'self' 'nonce-" + nonce + "' "
                + "https://maps.googleapis.com https://code.jquery.com https://cdn.datatables.net "
                + "https://cdnjs.cloudflare.com https://cdn.jsdelivr.net; "
                + scriptAttributes
                + "style-src 'self' 'nonce-" + nonce + "' https://fonts.googleapis.com "
                + "https://cdn.datatables.net https://cdnjs.cloudflare.com https://cdn.jsdelivr.net; "
                + "style-src-elem 'self' 'unsafe-inline' https://fonts.googleapis.com "
                + "https://cdn.datatables.net https://cdnjs.cloudflare.com https://cdn.jsdelivr.net; "
                + "style-src-attr 'unsafe-inline'; "
                + "font-src 'self' data: https://fonts.gstatic.com https://cdnjs.cloudflare.com; "
                + "img-src 'self' data: blob: https://maps.googleapis.com https://maps.gstatic.com "
                + "https://tile.openstreetmap.org https://api.tiles.mapbox.com; "
                + "connect-src 'self' https://maps.googleapis.com https://api.tiles.mapbox.com; "
                + "frame-src 'self' https://app.powerbi.com; worker-src 'self' blob:; "
                + "media-src 'self'; upgrade-insecure-requests";
    }

    private String addCompatibilityBootstrap(String html, String nonce) {
        Matcher matcher = HEAD_TAG.matcher(html);
        if (!matcher.find()) {
            return html;
        }
        String head = matcher.group();
        String bootstrap = String.format(CSP_COMPATIBILITY_BOOTSTRAP, nonce);
        return matcher.replaceFirst(Matcher.quoteReplacement(head + bootstrap));
    }

    private Set<String> attributeHashes(Pattern pattern, String html) {
        Set<String> hashes = new LinkedHashSet<>();
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            String handler = HtmlUtils.htmlUnescape(matcher.group(2)).trim();
            if (!handler.isEmpty()) {
                hashes.add("'sha256-" + sha256(handler) + "'");
            }
        }
        return hashes;
    }

    private String addNonce(Pattern pattern, String tagName, String html, String nonce) {
        Matcher matcher = pattern.matcher(html);
        StringBuffer secured = new StringBuffer(html.length() + 128);
        while (matcher.find()) {
            String replacement = "<" + tagName + " nonce=\"" + nonce + "\"" + matcher.group(1) + ">";
            matcher.appendReplacement(secured, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(secured);
        return secured.toString();
    }

    private boolean isHtmlNavigation(HttpServletRequest request) {
        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            return false;
        }
        String destination = request.getHeader("Sec-Fetch-Dest");
        if (destination != null && !destination.isBlank()) {
            return "document".equalsIgnoreCase(destination);
        }
        String accept = request.getHeader("Accept");
        return accept != null && accept.toLowerCase(Locale.ROOT).contains("text/html");
    }

    private boolean isHtml(HttpServletResponse response) {
        String contentType = response.getContentType();
        return contentType != null && contentType.toLowerCase(Locale.ROOT).startsWith("text/html");
    }

    private Charset responseCharset(HttpServletResponse response) {
        try {
            return Charset.forName(response.getCharacterEncoding());
        } catch (Exception ignored) {
            return StandardCharsets.UTF_8;
        }
    }

    private boolean isDownloadRequest(HttpServletRequest request) {
        String uri = request.getRequestURI().toLowerCase(Locale.ROOT);
        return uri.contains("/download") || uri.contains("/previewdocumentremarks/");
    }

    private String createNonce() {
        byte[] bytes = new byte[18];
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String sha256(String value) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                    .digest(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(digest);
        } catch (Exception exception) {
            throw new IllegalStateException("SHA-256 is unavailable", exception);
        }
    }

    private static final class SanitizingResponseWrapper extends HttpServletResponseWrapper {
        private final boolean downloadRequest;
        private String deferredContentDisposition;

        private SanitizingResponseWrapper(HttpServletResponse response, boolean downloadRequest) {
            super(response);
            this.downloadRequest = downloadRequest;
        }

        @Override
        public void setHeader(String name, String value) {
            if ("Server".equalsIgnoreCase(name)) {
                return;
            }
            if ("Content-Disposition".equalsIgnoreCase(name) && !downloadRequest) {
                deferredContentDisposition = value;
                return;
            }
            super.setHeader(name, value);
        }

        @Override
        public void addHeader(String name, String value) {
            if (isSingletonSecurityHeader(name)) {
                setHeader(name, value);
                return;
            }
            super.addHeader(name, value);
        }

        private void commitDeferredHeaders() {
            if (getStatus() < 400 && deferredContentDisposition != null) {
                super.setHeader("Content-Disposition", deferredContentDisposition);
            }
        }

        private boolean isSingletonSecurityHeader(String name) {
            return "Server".equalsIgnoreCase(name)
                    || "Content-Security-Policy".equalsIgnoreCase(name)
                    || "X-Frame-Options".equalsIgnoreCase(name)
                    || "X-Content-Type-Options".equalsIgnoreCase(name)
                    || "Strict-Transport-Security".equalsIgnoreCase(name)
                    || "Referrer-Policy".equalsIgnoreCase(name)
                    || "Permissions-Policy".equalsIgnoreCase(name);
        }
    }
}
