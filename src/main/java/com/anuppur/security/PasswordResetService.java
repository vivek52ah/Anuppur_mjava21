package com.anuppur.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.Users;
import com.anuppur.repository.UserRepository;
import com.anuppur.service.NotificationService;

@Service
public class PasswordResetService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final PasswordEncoder passwordEncoder;
    private final Duration otpLifetime;
    private final Duration tokenLifetime;
    private final int maxOtpAttempts;
    private final ConcurrentHashMap<String, OtpChallenge> otpChallenges = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ResetGrant> resetGrants = new ConcurrentHashMap<>();

    public PasswordResetService(UserRepository userRepository, NotificationService notificationService,
            @Qualifier("dmsPasswordEncoder") PasswordEncoder passwordEncoder,
            @Value("${security.password-reset.otp-minutes:5}") long otpMinutes,
            @Value("${security.password-reset.token-minutes:10}") long tokenMinutes,
            @Value("${security.password-reset.max-otp-attempts:5}") int maxOtpAttempts) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.passwordEncoder = passwordEncoder;
        this.otpLifetime = Duration.ofMinutes(otpMinutes);
        this.tokenLifetime = Duration.ofMinutes(tokenMinutes);
        this.maxOtpAttempts = maxOtpAttempts;
    }

    /** Always returns normally so callers can keep the same response for known and unknown numbers. */
    public void requestReset(String mobileNo) {
        int otp = 100_000 + SECURE_RANDOM.nextInt(900_000);
        Users user = userRepository.findByMobileNoAndStatusNot(mobileNo, DMSConstants.STATUS_DELETED);
        if (user == null) {
            // Do comparable local work without creating an account-discovery side channel.
            digest(mobileNo + ':' + otp);
            return;
        }

        otpChallenges.put(mobileNo, new OtpChallenge(
                digest(Integer.toString(otp)), user.getId(), Instant.now().plus(otpLifetime), 0));
        notificationService.sendPasswordResetOtp(user.getEmailId(), user.getMobileNo(), Integer.toString(otp));
    }

    public synchronized Optional<String> verifyOtpAndIssueToken(String mobileNo, String submittedOtp) {
        if (mobileNo == null || submittedOtp == null) {
            return Optional.empty();
        }
        OtpChallenge challenge = otpChallenges.get(mobileNo);
        if (challenge == null || !challenge.expiresAt.isAfter(Instant.now())) {
            otpChallenges.remove(mobileNo);
            return Optional.empty();
        }

        boolean matches = MessageDigest.isEqual(
                challenge.otpDigest.getBytes(StandardCharsets.UTF_8),
                digest(submittedOtp.trim()).getBytes(StandardCharsets.UTF_8));
        if (!matches) {
            int failures = challenge.failures + 1;
            if (failures >= maxOtpAttempts) {
                otpChallenges.remove(mobileNo);
            } else {
                otpChallenges.put(mobileNo, new OtpChallenge(
                        challenge.otpDigest, challenge.userId, challenge.expiresAt, failures));
            }
            return Optional.empty();
        }

        // OTP is consumed before a reset grant is issued, making it single-use.
        otpChallenges.remove(mobileNo);
        String rawToken = randomToken();
        resetGrants.put(digest(rawToken), new ResetGrant(challenge.userId, Instant.now().plus(tokenLifetime)));
        return Optional.of(rawToken);
    }

    @Transactional
    public boolean completeReset(String rawToken, String newPassword) {
        if (!isStrongPassword(newPassword) || rawToken == null) {
            return false;
        }
        ResetGrant grant = resetGrants.remove(digest(rawToken));
        if (grant == null || !grant.expiresAt.isAfter(Instant.now())) {
            return false;
        }
        Users user = userRepository.findById(grant.userId).orElse(null);
        if (user == null || DMSConstants.STATUS_DELETED.equals(user.getStatus())) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setLastPasswordUpdatedOn(new Date());
        userRepository.save(user);
        return true;
    }

    public boolean isStrongPassword(String password) {
        return password != null && password.length() >= 8 && password.length() <= 64
                && password.chars().anyMatch(Character::isUpperCase)
                && password.chars().anyMatch(Character::isLowerCase)
                && password.chars().anyMatch(Character::isDigit)
                && password.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));
    }

    @Scheduled(fixedDelayString = "${security.password-reset.cleanup-millis:300000}")
    public void removeExpiredResetGrants() {
        Instant now = Instant.now();
        otpChallenges.entrySet().removeIf(entry -> !entry.getValue().expiresAt.isAfter(now));
        resetGrants.entrySet().removeIf(entry -> !entry.getValue().expiresAt.isAfter(now));
    }

    private String randomToken() {
        byte[] bytes = new byte[32];
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String digest(String value) {
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256")
                    .digest(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is unavailable", exception);
        }
    }

    private record ResetGrant(Long userId, Instant expiresAt) {
    }

    private record OtpChallenge(String otpDigest, Long userId, Instant expiresAt, int failures) {
    }
}
