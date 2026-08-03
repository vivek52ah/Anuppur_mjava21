package com.anuppur.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.anuppur.entity.Users;
import com.anuppur.repository.UserRepository;
import com.anuppur.service.NotificationService;

class PasswordResetServiceTest {

    @Test
    void unknownAndKnownRequestsDoNotReturnAccountExistence() {
        Fixture fixture = new Fixture();
        when(fixture.users.findByMobileNoAndStatusNot("9999999999", "Deleted")).thenReturn(null);

        fixture.service.requestReset("9999999999");

        verify(fixture.notifications, never()).sendPasswordResetOtp(any(), any(), any());
    }

    @Test
    void otpAndResetTokenAreSingleUse() {
        Fixture fixture = new Fixture();
        Users user = new Users();
        user.setId(7L);
        user.setStatus("Active");
        user.setMobileNo("9876543210");
        user.setEmailId("user@example.com");
        when(fixture.users.findByMobileNoAndStatusNot("9876543210", "Deleted")).thenReturn(user);
        when(fixture.users.findById(7L)).thenReturn(Optional.of(user));
        when(fixture.encoder.encode("Strong@123")).thenReturn("encoded");

        fixture.service.requestReset("9876543210");
        ArgumentCaptor<String> otpCaptor = ArgumentCaptor.forClass(String.class);
        verify(fixture.notifications).sendPasswordResetOtp(
                org.mockito.ArgumentMatchers.eq("user@example.com"),
                org.mockito.ArgumentMatchers.eq("9876543210"), otpCaptor.capture());

        String token = fixture.service.verifyOtpAndIssueToken("9876543210", otpCaptor.getValue()).orElseThrow();
        assertThat(fixture.service.completeReset(token, "Strong@123")).isTrue();
        assertThat(fixture.service.completeReset(token, "Strong@123")).isFalse();
        assertThat(user.getPassword()).isEqualTo("encoded");
    }

    @Test
    void expiredOtpAndWeakPasswordsAreRejected() {
        Fixture fixture = new Fixture(0);
        Users user = new Users();
        user.setId(7L);
        user.setMobileNo("9876543210");
        when(fixture.users.findByMobileNoAndStatusNot("9876543210", "Deleted")).thenReturn(user);
        fixture.service.requestReset("9876543210");
        ArgumentCaptor<String> otpCaptor = ArgumentCaptor.forClass(String.class);
        verify(fixture.notifications).sendPasswordResetOtp(any(), any(), otpCaptor.capture());

        assertThat(fixture.service.verifyOtpAndIssueToken("9876543210", otpCaptor.getValue())).isEmpty();
        assertThat(fixture.service.isStrongPassword("password")).isFalse();
        assertThat(fixture.service.isStrongPassword("Strong@123")).isTrue();
    }

    private static class Fixture {
        private final UserRepository users = mock(UserRepository.class);
        private final NotificationService notifications = mock(NotificationService.class);
        private final PasswordEncoder encoder = mock(PasswordEncoder.class);
        private final PasswordResetService service;

        private Fixture() {
            this(5);
        }

        private Fixture(long otpMinutes) {
            service = new PasswordResetService(users, notifications, encoder, otpMinutes, 10, 5);
        }
    }
}
