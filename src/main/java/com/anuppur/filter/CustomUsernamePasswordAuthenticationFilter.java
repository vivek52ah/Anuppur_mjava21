package com.anuppur.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;

import com.anuppur.constants.DMSConstants;
import com.anuppur.service.impl.UserDetailsServiceImpl;

public class CustomUsernamePasswordAuthenticationFilter extends OncePerRequestFilter {

	private final UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String captchaText = request.getParameter("captchaText");
		HttpSession session = request.getSession();

		String captcha = (String) session.getAttribute(DMSConstants.CAPTCHA_LOGIN);

		if (captchaText != null) {

			captchaText = captchaText.toUpperCase();

		}

		if (captcha == null || (captcha != null && !captcha.equals(captchaText))) {
			userDetailsService.setCaptchaSuccess(false);
			// session.setAttribute("isCaptchaSuccess", false);
		} else {
			userDetailsService.setCaptchaSuccess(true);
			// session.setAttribute("isCaptchaSuccess", true);
		}

		/*
		 * String loginType = request.getParameter("loginType");
		 * 
		 * if (StringUtils.isNotBlank(loginType)) {
		 * userDetailsService.setLoginType(loginType);
		 * 
		 * }
		 */
		super.doFilter(request, response, filterChain);
	}

	public CustomUsernamePasswordAuthenticationFilter(UserDetailsServiceImpl userDetailsService) {
		super();
		this.userDetailsService = userDetailsService;
	}
}
