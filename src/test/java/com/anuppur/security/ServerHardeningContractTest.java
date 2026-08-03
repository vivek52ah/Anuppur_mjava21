package com.anuppur.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import com.anuppur.controller.SecureErrorController;
import com.anuppur.exception.GlobalExceptionHandler;
import com.anuppur.util.DMSUtil;

import jakarta.servlet.RequestDispatcher;

class ServerHardeningContractTest {

    private static final String INTERNAL_ADMIN_EXPRESSION =
            "hasAuthority('ROLE_SYSTEM_ADMIN') and ("
            + "hasIpAddress('127.0.0.0/8') or hasIpAddress('10.0.0.0/8') or "
            + "hasIpAddress('172.16.0.0/12') or hasIpAddress('192.168.0.0/16') or "
            + "hasIpAddress('::1'))";

    @Test
    void swaggerAndActuatorRuleRequiresBothSystemAdminAndInternalNetwork() {
        assertThat(decision("ROLE_SYSTEM_ADMIN", "127.0.0.1").isGranted()).isTrue();
        assertThat(decision("ROLE_DEPARTMENT", "127.0.0.1").isGranted()).isFalse();
        assertThat(decision("ROLE_SYSTEM_ADMIN", "203.0.113.10").isGranted()).isFalse();
    }

    @Test
    void productionAndAuditProfilesDisableSwaggerAndActuatorExposure() throws Exception {
        for (String profile : new String[] {"test", "prod"}) {
            Properties properties = load("application-" + profile + ".properties");
            assertThat(properties.getProperty("springdoc.api-docs.enabled")).isEqualTo("false");
            assertThat(properties.getProperty("springdoc.swagger-ui.enabled")).isEqualTo("false");
            assertThat(properties.getProperty("management.endpoints.web.exposure.exclude")).isEqualTo("*");
        }
    }

    @Test
    void genericDatabaseFailureDoesNotLeakSchemaOrTableDetails() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/work/7");
        SQLException databaseException = new SQLException(
                "Table 'secret_schema.financial_expenditure' doesn't exist");

        ResponseEntity<Map<String, Object>> response =
                new GlobalExceptionHandler().databaseFailure(databaseException, request);

        assertThat(response.getStatusCode().value()).isEqualTo(500);
        assertThat(response.getBody()).containsEntry("message", "The request could not be completed.");
        assertThat(response.getBody().toString())
                .doesNotContain("secret_schema", "financial_expenditure", "SQLException");
    }

    @Test
    void nonNumericIdReturnsCleanBadRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/work/not-a-number");
        ResponseEntity<Map<String, Object>> response =
                new GlobalExceptionHandler().badRequest(new NumberFormatException("not-a-number"), request);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).containsEntry("message", "Invalid request.");
        assertThat(response.getBody().toString()).doesNotContain("NumberFormatException");
        assertThatThrownBy(() -> DMSUtil.decryptParam("not-valid-%%%"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void traceIsRejectedBeforeApplicationProcessing() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("TRACE", "/login");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        new TraceMethodFilter().doFilter(request, response, chain);

        assertThat(response.getStatus()).isEqualTo(405);
        assertThat(chain.getRequest()).isNull();
        assertThat(response.getHeader("Allow")).doesNotContain("TRACE");
    }

    @Test
    void containerErrorFallbackIsGeneric() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/error");
        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 500);
        request.setAttribute(RequestDispatcher.ERROR_EXCEPTION,
                new SQLException("select * from hidden_schema.hidden_table"));

        ResponseEntity<Map<String, Object>> response = new SecureErrorController().error(request);

        assertThat(response.getStatusCode().value()).isEqualTo(500);
        assertThat(response.getBody().toString()).doesNotContain("hidden_schema", "hidden_table", "select");
    }

    private AuthorizationDecision decision(String authority, String remoteAddress) {
        WebExpressionAuthorizationManager manager =
                new WebExpressionAuthorizationManager(INTERNAL_ADMIN_EXPRESSION);
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(
                "user", "password", AuthorityUtils.createAuthorityList(authority));
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v3/api-docs");
        request.setRemoteAddr(remoteAddress);
        return manager.check(() -> authentication, new RequestAuthorizationContext(request));
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
