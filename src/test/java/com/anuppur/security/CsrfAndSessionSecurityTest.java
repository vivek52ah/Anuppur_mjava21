package com.anuppur.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;

class CsrfAndSessionSecurityTest {

    @Test
    void spaHandlerAcceptsAngularCsrfHeader() {
        SpaCsrfTokenRequestHandler handler = new SpaCsrfTokenRequestHandler();
        CsrfToken token = new DefaultCsrfToken("X-XSRF-TOKEN", "_csrf", "expected-token");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("X-XSRF-TOKEN", "expected-token");

        assertThat(handler.resolveCsrfTokenValue(request, token)).isEqualTo("expected-token");
    }

    @Test
    void csrfCookieFilterForcesDeferredTokenCreation() throws Exception {
        CsrfToken token = mock(CsrfToken.class);
        when(token.getToken()).thenReturn("token");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute(CsrfToken.class.getName(), token);

        new CsrfCookieFilter().doFilter(request, new MockHttpServletResponse(), new MockFilterChain());

        verify(token).getToken();
    }

    @Test
    void everyRuntimeProfileUsesSecureServerSideSessionSettings() throws Exception {
        for (String profile : new String[] { "local", "test", "prod" }) {
            Properties properties = load("application-" + profile + ".properties");
            assertThat(properties.getProperty("security.enable-csrf")).isEqualTo("true");
            assertThat(properties.getProperty("server.servlet.session.timeout")).isEqualTo("30m");
            assertThat(properties.getProperty("server.servlet.session.cookie.http-only")).isEqualTo("true");
            assertThat(properties.getProperty("server.servlet.session.cookie.secure")).isEqualTo("true");
            assertThat(properties.getProperty("server.servlet.session.cookie.same-site")).isEqualTo("lax");
            assertThat(properties.getProperty("server.servlet.session.tracking-modes")).isEqualTo("cookie");
        }
    }

    private Properties load(String resourceName) throws Exception {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            assertThat(input).as(resourceName).isNotNull();
            properties.load(input);
        }
        return properties;
    }
}
