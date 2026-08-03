package com.anuppur.security;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.LockedException;

class LoginProtectionServiceTest {

    @Test
    void limitsRequestsPerIpAndPerUser() {
        LoginProtectionService service = new LoginProtectionService(2, 2, 5, 15, 2, 15);

        service.checkLoginAllowed("127.0.0.1", "user@example.com");
        service.checkLoginAllowed("127.0.0.1", "user@example.com");

        assertThatThrownBy(() -> service.checkLoginAllowed("127.0.0.1", "user@example.com"))
                .isInstanceOf(LockedException.class);
    }

    @Test
    void temporarilyLocksUsernameAfterLimitedFailuresAndSuccessClearsFailures() {
        LoginProtectionService service = new LoginProtectionService(30, 10, 3, 15, 5, 15);

        service.recordAuthenticationFailure("user@example.com");
        service.recordAuthenticationFailure("user@example.com");
        service.recordAuthenticationSuccess("user@example.com");
        service.assertNotLocked("user@example.com");

        service.recordAuthenticationFailure("user@example.com");
        service.recordAuthenticationFailure("user@example.com");
        service.recordAuthenticationFailure("user@example.com");
        assertThatThrownBy(() -> service.assertNotLocked("user@example.com"))
                .isInstanceOf(LockedException.class)
                .hasMessageContaining("Too many failed attempts");
    }

    @Test
    void rateLimitsPasswordResetWithoutAccountLookupSignal() {
        LoginProtectionService service = new LoginProtectionService(30, 10, 5, 15, 1, 15);

        org.assertj.core.api.Assertions.assertThat(
                service.allowPasswordResetRequest("127.0.0.1", "9999999999")).isTrue();
        org.assertj.core.api.Assertions.assertThat(
                service.allowPasswordResetRequest("127.0.0.1", "9999999999")).isFalse();
    }
}
