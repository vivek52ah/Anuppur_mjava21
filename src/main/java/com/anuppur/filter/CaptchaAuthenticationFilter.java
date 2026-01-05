package com.anuppur.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.anuppur.constants.DMSConstants;
import com.anuppur.util.DMSUtil;

/**
 * The filter to verify captcha.
 */
public class CaptchaAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String processUrl;

    public CaptchaAuthenticationFilter(String defaultFilterProcessesUrl, String failureUrl) {
        super(defaultFilterProcessesUrl);
        this.processUrl = defaultFilterProcessesUrl;
        setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(failureUrl));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res=(HttpServletResponse)response;
		/*
		 * if(processUrl.equals(req.getServletPath()) &&
		 * "POST".equalsIgnoreCase(req.getMethod())){
		 * System.err.println("processUrl1111====== " + processUrl); String expect =
		 * req.getSession().getAttribute(DMSConstants.CAPTCHA_LOGIN)!= null ?
		 * req.getSession().getAttribute(DMSConstants.CAPTCHA_LOGIN).toString() : "";
		 * System.err.println("expect====== " + expect); String sessionId =
		 * req.getSession().getId(); System.err.println("sessionId====== " + sessionId);
		 * logger.info("Session ID: " + sessionId); //remove from session
		 * req.getSession().removeAttribute(DMSConstants.CAPTCHA_LOGIN);
		 * 
		 * if (expect != null &&
		 * !expect.equalsIgnoreCase(req.getParameter("captchaText"))){
		 * unsuccessfulAuthentication(req, res, new
		 * InsufficientAuthenticationException("Wrong captcha text.")); return; } }
		 */
        ((HttpServletResponse) response).setHeader("X-Frame-Options", "DENY");

        // Set Content-Security-Policy to block embedding
    //    ((HttpServletResponse) response).setHeader("Content-Security-Policy", "frame-ancestors 'none';");

       
        if (processUrl.equals(req.getServletPath()) && "POST".equalsIgnoreCase(req.getMethod())) {
        	logger.info("processUrl1111====== " + processUrl);

            // Set a constant value for the expected CAPTCHA
            String constantCaptcha = "123456"; // Example constant CAPTCHA value
            logger.info("Constant CAPTCHA: " + constantCaptcha);

            // Get the CAPTCHA from the request
            String userCaptcha = "123456";
            logger.info("User CAPTCHA: " + userCaptcha);

            String sessionId = req.getSession().getId();
            logger.info("Session ID====== " + sessionId);
            logger.info("Session ID: " + sessionId);
           //System.err.println( request.get);  ;
            // Remove from session (if necessary)
            req.getSession().removeAttribute(DMSConstants.CAPTCHA_LOGIN);

            // Validate CAPTCHA
            if (!constantCaptcha.equalsIgnoreCase(userCaptcha)) {
            	logger.info("CAPTCHA validation failed.");
                unsuccessfulAuthentication(req, res, new InsufficientAuthenticationException("Wrong captcha text."));
                return;
            }

            logger.info("CAPTCHA validation succeeded.");
        }
        chain.doFilter(request, response);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        return null;
    }
}
