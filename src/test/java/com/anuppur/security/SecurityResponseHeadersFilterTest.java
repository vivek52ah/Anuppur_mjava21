package com.anuppur.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletResponse;

class SecurityResponseHeadersFilterTest {

    @Test
    void htmlGetsNonceBasedCspAndAngularCspMode() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/anuppur/login");
        request.addHeader("Accept", "text/html");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = (servletRequest, servletResponse) -> {
            servletResponse.setContentType("text/html;charset=UTF-8");
            servletResponse.getWriter().write(
                    "<html><head><style>body{color:black}</style></head>"
                    + "<body style=\"margin:0\"><button onclick=\"return false\">Noop</button>"
                    + "<script>window.ready=true;</script></body></html>");
        };

        new SecurityResponseHeadersFilter().doFilter(request, response, chain);

        String policy = response.getHeader("Content-Security-Policy");
        String body = response.getContentAsString(StandardCharsets.UTF_8);
        assertThat(policy)
                .contains("script-src 'self' 'nonce-")
                .contains("script-src-attr 'unsafe-hashes' 'sha256-")
                .contains("style-src-elem 'self' 'unsafe-inline'")
                .contains("style-src-attr 'unsafe-inline'")
                .contains("img-src 'self' data: blob:")
                .doesNotContain("'unsafe-eval'", " https:;",
                        "connect-src *");
        assertThat(scriptDirective(policy)).doesNotContain("'unsafe-inline'");
        assertThat(body)
                .contains("<html ng-csp=\"no-unsafe-eval\">")
                .contains("d.currentScript.nonce")
                .contains("/^(script|style)$/i")
                .containsPattern("<script nonce=\"[^\"]+\">")
                .containsPattern("<style nonce=\"[^\"]+\">");
        assertThat(response.getHeaders("Content-Security-Policy")).hasSize(1);
        assertThat(response.getHeader("Content-Security-Policy-Report-Only")).isNull();
    }

    @Test
    void errorResponseDoesNotLeakServerOrSyntheticFilename() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/anuppur/error");
        request.addHeader("Accept", "application/json");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = (servletRequest, servletResponse) -> {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.setStatus(500);
            httpResponse.setHeader("Server", "nginx/1.28.1");
            httpResponse.setHeader("Content-Disposition", "f.txt");
            httpResponse.addHeader("X-Frame-Options", "SAMEORIGIN");
            httpResponse.addHeader("X-Frame-Options", "DENY");
        };

        new SecurityResponseHeadersFilter().doFilter(request, response, chain);

        assertThat(response.getHeader("Server")).isNull();
        assertThat(response.getHeader("Content-Disposition")).isNull();
        assertThat(response.getHeaders("X-Frame-Options")).containsExactly("DENY");
    }

    private String scriptDirective(String policy) {
        for (String directive : policy.split(";")) {
            if (directive.trim().startsWith("script-src ")) {
                return directive;
            }
        }
        return "";
    }
}
