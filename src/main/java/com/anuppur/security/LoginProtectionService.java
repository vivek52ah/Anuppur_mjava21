package com.anuppur.security;

import java.time.Duration;
import java.time.Instant;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Service;

@Service
public class LoginProtectionService {

    private final ConcurrentHashMap<String, Counter> loginRequestsByIp = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Counter> loginRequestsByUser = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, FailureState> failuresByUser = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Counter> resetRequestsByIp = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Counter> resetRequestsByUser = new ConcurrentHashMap<>();

    private final int loginRequestsPerMinute;
    private final int loginRequestsPerUserPerMinute;
    private final int maxAuthenticationFailures;
    private final Duration accountLockDuration;
    private final int resetRequestsPerWindow;
    private final Duration resetWindow;

    public LoginProtectionService(
            @Value("${security.login.max-requests-per-ip-per-minute:30}") int loginRequestsPerMinute,
            @Value("${security.login.max-requests-per-user-per-minute:10}") int loginRequestsPerUserPerMinute,
            @Value("${security.login.max-failures:5}") int maxAuthenticationFailures,
            @Value("${security.login.lock-minutes:15}") long accountLockMinutes,
            @Value("${security.password-reset.max-requests-per-window:5}") int resetRequestsPerWindow,
            @Value("${security.password-reset.window-minutes:15}") long resetWindowMinutes) {
        this.loginRequestsPerMinute = loginRequestsPerMinute;
        this.loginRequestsPerUserPerMinute = loginRequestsPerUserPerMinute;
        this.maxAuthenticationFailures = maxAuthenticationFailures;
        this.accountLockDuration = Duration.ofMinutes(accountLockMinutes);
        this.resetRequestsPerWindow = resetRequestsPerWindow;
        this.resetWindow = Duration.ofMinutes(resetWindowMinutes);
    }

    public void checkLoginAllowed(String clientIp, String username) {
        String userKey = normalize(username);
        assertNotLocked(userKey);
        Instant now = Instant.now();
        enforceWindow(loginRequestsByIp, normalize(clientIp), loginRequestsPerMinute,
                Duration.ofMinutes(1), now);
        enforceWindow(loginRequestsByUser, userKey, loginRequestsPerUserPerMinute,
                Duration.ofMinutes(1), now);
    }

    public void recordAuthenticationFailure(String username) {
        String userKey = normalize(username);
        if (userKey.isEmpty()) {
            return;
        }
        failuresByUser.compute(userKey, (key, existing) -> {
            FailureState state = existing == null ? new FailureState() : existing;
            synchronized (state) {
                Instant now = Instant.now();
                if (state.lockedUntil != null && state.lockedUntil.isAfter(now)) {
                    return state;
                }
                state.failures++;
                if (state.failures >= maxAuthenticationFailures) {
                    state.lockedUntil = now.plus(accountLockDuration);
                    state.failures = 0;
                }
                return state;
            }
        });
    }

    public void recordAuthenticationSuccess(String username) {
        failuresByUser.remove(normalize(username));
        loginRequestsByUser.remove(normalize(username));
    }

    public void assertNotLocked(String username) {
        FailureState state = failuresByUser.get(normalize(username));
        if (state == null) {
            return;
        }
        synchronized (state) {
            Instant now = Instant.now();
            if (state.lockedUntil != null && state.lockedUntil.isAfter(now)) {
                throw new LockedException("Too many failed attempts. Please try again later.");
            }
            if (state.lockedUntil != null) {
                failuresByUser.remove(normalize(username), state);
            }
        }
    }

    public boolean allowPasswordResetRequest(String clientIp, String mobileNo) {
        Instant now = Instant.now();
        return tryWindow(resetRequestsByIp, normalize(clientIp), resetRequestsPerWindow, resetWindow, now)
                && tryWindow(resetRequestsByUser, normalize(mobileNo), resetRequestsPerWindow, resetWindow, now);
    }

    @Scheduled(fixedDelayString = "${security.login.cleanup-millis:300000}")
    public void removeExpiredEntries() {
        Instant now = Instant.now();
        loginRequestsByIp.entrySet().removeIf(entry -> entry.getValue().expired(now));
        loginRequestsByUser.entrySet().removeIf(entry -> entry.getValue().expired(now));
        resetRequestsByIp.entrySet().removeIf(entry -> entry.getValue().expired(now));
        resetRequestsByUser.entrySet().removeIf(entry -> entry.getValue().expired(now));
        failuresByUser.entrySet().removeIf(entry -> entry.getValue().expired(now));
    }

    private void enforceWindow(ConcurrentHashMap<String, Counter> counters, String key, int maximum,
            Duration duration, Instant now) {
        if (!tryWindow(counters, key, maximum, duration, now)) {
            throw new LockedException("Too many attempts. Please try again later.");
        }
    }

    private boolean tryWindow(ConcurrentHashMap<String, Counter> counters, String key, int maximum,
            Duration duration, Instant now) {
        Counter counter = counters.compute(key, (ignored, existing) -> {
            if (existing == null || existing.expired(now)) {
                return new Counter(now.plus(duration));
            }
            return existing;
        });
        synchronized (counter) {
            if (counter.count >= maximum) {
                return false;
            }
            counter.count++;
            return true;
        }
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
    }

    private static final class Counter {
        private int count;
        private final Instant expiresAt;

        private Counter(Instant expiresAt) {
            this.expiresAt = expiresAt;
        }

        private boolean expired(Instant now) {
            return !expiresAt.isAfter(now);
        }
    }

    private static final class FailureState {
        private int failures;
        private Instant lockedUntil;

        private boolean expired(Instant now) {
            return lockedUntil != null && !lockedUntil.isAfter(now);
        }
    }
}
