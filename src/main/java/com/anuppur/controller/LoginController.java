package com.anuppur.controller;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.CacheControl;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	// admin-login

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String viewLogin(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "timeout", required = false) String timeout,
			@RequestParam(value = "resetPassword", required = false) String resetPassword,
			@RequestParam(value = "register", required = false) String register,
			@RequestParam(value = "alreadyVerified", required = false) String alreadyVerified,
			@RequestParam(value = "verificationSuccess", required = false) String verificationSuccess,
			@RequestParam(value = "verificationError", required = false) String verificationError,
			HttpServletRequest request, Model model) {
		if (error != null)
			// model.addAttribute("error", "Your username or password is invalid.");
			model.addAttribute("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));

		if (logout != null)
			model.addAttribute("message", "You've been logged out successfully.");

		if (timeout != null) {
			model.addAttribute("error", "Session expired. Please login again.");
		}
		if (resetPassword != null) {
			model.addAttribute("message", "New password has been sent to your registered mobile no. !");
		}
		if (register != null) {
			model.addAttribute("message", "Your account has been created. ");
		}
		if (alreadyVerified != null) {
			model.addAttribute("error", "Email Already Verified.");
		}
		if (verificationSuccess != null) {
			model.addAttribute("message",
					"Email Verification Successful. Once Admin approves your account, You will get email notification.");
		}
		if (verificationError != null) {
			model.addAttribute("error", "Invalid URL.");
		}

		return "login";
	}

	// customize the error message
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";

		if (exception instanceof AuthenticationServiceException) {
			error = exception.getMessage();
		} else if (exception instanceof BadCredentialsException) {
			error = "Invalid user name or password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = exception.getMessage();
		}

		return error;
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView viewErrorPage(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("error/403");
		return modelAndView;

	}

	@RequestMapping(value = "citizenshipRequest", method = RequestMethod.GET)
	public ModelAndView viewSignUpForm(HttpServletRequest request, Model model, HttpServletResponse response) {
		logger.info("Displaying Sign up page");
		String headerValue = CacheControl.maxAge(10, TimeUnit.SECONDS).getHeaderValue();
		response.addHeader("Cache-Control", headerValue);
		ModelAndView modelAndView = new ModelAndView("citizenshipRequest");
		return modelAndView;
	}

	@RequestMapping(value = "aboutUs", method = RequestMethod.GET)
	public ModelAndView aboutUs(HttpServletRequest request, Model model, HttpServletResponse response) {
		logger.info("Displaying aboutUs Page");
		String headerValue = CacheControl.maxAge(10, TimeUnit.SECONDS).getHeaderValue();
		response.addHeader("Cache-Control", headerValue);
		ModelAndView modelAndView = new ModelAndView("aboutus");
		return modelAndView;
	}

	@RequestMapping(value = "guidelines", method = RequestMethod.GET)
	public ModelAndView guidelines(HttpServletRequest request, Model model, HttpServletResponse response) {
		logger.info("Displaying guidelines Page");
		String headerValue = CacheControl.maxAge(10, TimeUnit.SECONDS).getHeaderValue();
		response.addHeader("Cache-Control", headerValue);
		ModelAndView modelAndView = new ModelAndView("guidelines");
		return modelAndView;
	}

	@RequestMapping(value = "contactUs", method = RequestMethod.GET)
	public ModelAndView contactUs(HttpServletRequest request, Model model, HttpServletResponse response) {
		logger.info("Displaying contactUs Page");
		String headerValue = CacheControl.maxAge(10, TimeUnit.SECONDS).getHeaderValue();
		response.addHeader("Cache-Control", headerValue);
		ModelAndView modelAndView = new ModelAndView("contactUs");
		return modelAndView;
	}

}
