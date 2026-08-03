package com.anuppur.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class LoginAndPasswordResetContractTest {

    private static final Path ROOT = Path.of("src", "main");

    @Test
    void loginAndForgotPasswordDoNotContainHardcodedCaptcha() throws Exception {
        String login = Files.readString(ROOT.resolve("resources/templates/login.html"));
        String forgot = Files.readString(ROOT.resolve("resources/templates/forgotpassword.html"));
        String filter = Files.readString(ROOT.resolve("java/com/anuppur/filter/CaptchaAuthenticationFilter.java"));

        assertThat(login).doesNotContain("value=\"123456\"");
        assertThat(forgot).doesNotContain("value=\"123456\"");
        assertThat(filter).doesNotContain("constantCaptcha", "userCaptcha = \"123456\"");
        assertThat(login).contains("@{/captcha?type=login}");
        assertThat(forgot).contains("@{/captcha?type=reset}");
    }

    @Test
    void resetFlowUsesOtpThenNewPasswordInsteadOfSendingPassword() throws Exception {
        String controller = Files.readString(
                ROOT.resolve("java/com/anuppur/controller/ForgotPasswordController.java"));
        String resetService = Files.readString(
                ROOT.resolve("java/com/anuppur/security/PasswordResetService.java"));

        assertThat(controller).contains("GENERIC_RESPONSE", "/verify-reset-otp", "/complete-password-reset");
        assertThat(resetService).contains("otpChallenges", "resetGrants", "resetGrants.remove");
        assertThat(resetService).doesNotContain("setPassword(encodedPassword)", "generatePassword()");
    }
}
