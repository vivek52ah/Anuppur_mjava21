package com.anuppur.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.anuppur.bean.ForgotPasswordBean;
import com.anuppur.constants.DMSConstants;
import com.anuppur.security.LoginProtectionService;
import com.anuppur.security.PasswordResetService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotPasswordController {

    private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);
    private static final String RESET_MOBILE = "PASSWORD_RESET_MOBILE";
    private static final String RESET_TOKEN = "PASSWORD_RESET_TOKEN";
    private static final String GENERIC_RESPONSE =
            "If an account matches the supplied details, a password reset OTP has been sent to the registered contact.";

    private final PasswordResetService passwordResetService;
    private final LoginProtectionService loginProtectionService;

    public ForgotPasswordController(PasswordResetService passwordResetService,
            LoginProtectionService loginProtectionService) {
        this.passwordResetService = passwordResetService;
        this.loginProtectionService = loginProtectionService;
    }

    @RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
    public String viewForgotPassword(Model model) {
        model.addAttribute("forgotPasswordBean", new ForgotPasswordBean());
        return "forgotpassword";
    }

    @RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
    public String requestPasswordReset(@ModelAttribute("forgotPasswordBean") ForgotPasswordBean form,
            Model model, HttpServletRequest request) {
        if (!validMobile(form.getMobileNo())) {
            model.addAttribute("error", "Please enter a valid 10-digit mobile number.");
            return "forgotpassword";
        }
        if (!consumeCaptcha(request, DMSConstants.CAPTCHA_RESET)) {
            model.addAttribute("error", "Invalid or expired CAPTCHA.");
            return "forgotpassword";
        }

        HttpSession session = request.getSession(true);
        session.setAttribute(RESET_MOBILE, form.getMobileNo());
        session.removeAttribute(RESET_TOKEN);
        if (loginProtectionService.allowPasswordResetRequest(request.getRemoteAddr(), form.getMobileNo())) {
            passwordResetService.requestReset(form.getMobileNo());
        }
        logger.info("Password reset request accepted");
        return "redirect:/verify-reset-otp";
    }

    @RequestMapping(value = "/verify-reset-otp", method = RequestMethod.GET)
    public String viewVerifyOtp(Model model, HttpServletRequest request) {
        if (request.getSession(false) == null
                || request.getSession(false).getAttribute(RESET_MOBILE) == null) {
            return "redirect:/forgotpassword";
        }
        model.addAttribute("message", GENERIC_RESPONSE);
        return "verifyresetotp";
    }

    @RequestMapping(value = "/verify-reset-otp", method = RequestMethod.POST)
    public String verifyOtp(@RequestParam("otp") String otp, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String mobileNo = session == null ? null : (String) session.getAttribute(RESET_MOBILE);
        if (mobileNo == null) {
            return "redirect:/forgotpassword";
        }
        Optional<String> resetToken = passwordResetService.verifyOtpAndIssueToken(mobileNo, otp);
        if (resetToken.isEmpty()) {
            model.addAttribute("message", GENERIC_RESPONSE);
            model.addAttribute("error", "Invalid or expired OTP.");
            return "verifyresetotp";
        }
        session.setAttribute(RESET_TOKEN, resetToken.get());
        return "redirect:/set-new-password";
    }

    @RequestMapping(value = "/set-new-password", method = RequestMethod.GET)
    public String viewSetNewPassword(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String token = session == null ? null : (String) session.getAttribute(RESET_TOKEN);
        if (token == null) {
            return "redirect:/forgotpassword";
        }
        model.addAttribute("resetToken", token);
        return "resetpasswordbyotp";
    }

    @RequestMapping(value = "/complete-password-reset", method = RequestMethod.POST)
    public String completePasswordReset(@RequestParam("resetToken") String submittedToken,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String sessionToken = session == null ? null : (String) session.getAttribute(RESET_TOKEN);
        if (!sameToken(sessionToken, submittedToken)) {
            return "redirect:/forgotpassword";
        }
        if (!password.equals(confirmPassword) || !passwordResetService.isStrongPassword(password)) {
            model.addAttribute("resetToken", sessionToken);
            model.addAttribute("error",
                    "Password must be 8-64 characters and include upper, lower, number and special character.");
            return "resetpasswordbyotp";
        }
        if (!passwordResetService.completeReset(sessionToken, password)) {
            session.removeAttribute(RESET_TOKEN);
            model.addAttribute("error", "Reset link expired or already used. Please start again.");
            model.addAttribute("forgotPasswordBean", new ForgotPasswordBean());
            return "forgotpassword";
        }
        session.removeAttribute(RESET_TOKEN);
        session.removeAttribute(RESET_MOBILE);
        return "redirect:/login?passwordResetSuccess";
    }

    private boolean consumeCaptcha(HttpServletRequest request, String attribute) {
        HttpSession session = request.getSession(false);
        String expected = session == null ? null : (String) session.getAttribute(attribute);
        if (session != null) {
            session.removeAttribute(attribute);
        }
        String submitted = request.getParameter("captchaText");
        return expected != null && submitted != null && MessageDigest.isEqual(
                expected.getBytes(StandardCharsets.UTF_8),
                submitted.trim().getBytes(StandardCharsets.UTF_8));
    }

    private boolean validMobile(String mobileNo) {
        return mobileNo != null && mobileNo.matches("[6-9][0-9]{9}");
    }

    private boolean sameToken(String expected, String submitted) {
        return expected != null && submitted != null && MessageDigest.isEqual(
                expected.getBytes(StandardCharsets.UTF_8), submitted.getBytes(StandardCharsets.UTF_8));
    }
}
