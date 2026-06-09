package com.anuppur.handler;

import java.io.IOException;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class DMSAuthenticationSuccessHandler implements
AuthenticationSuccessHandler {

	public static final Logger logger = LoggerFactory.getLogger(DMSAuthenticationSuccessHandler.class);
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			Authentication authentication) throws IOException, ServletException {

	
		
		String remoteAddr = httpServletRequest.getHeader("X-Forwarded-For");
		if (remoteAddr == null || "".equals(remoteAddr)) {
			remoteAddr = httpServletRequest.getRemoteAddr();
		}
		
		
		String targetUrl = "";
		Collection<? extends GrantedAuthority> authorities = authentication
				.getAuthorities();
		
		

		for (GrantedAuthority authority : authorities) {

			String role = authority.getAuthority();
			  
			logger.info("Authentication.....");

		    if (role == null) {
		        // You can return or handle the null case based on your logic
		        return;  // or return some appropriate response here
		    }

		    if ("ROLE_SYSTEM_ADMIN".equals(role)
		    		|| "ROLE_ADMIN".equals(role)
		    		|| "ROLE_DEPARTMENT".equals(role)
		    		|| "ROLE_DEPT_DISTRICT".equals(role)
		    		|| "ROLE_AGENCY_ADMIN".equals(role)
		    		|| "ROLE_DM".equals(role)
		    		|| "ROLE_CEO".equals(role)
		    		|| "ROLE_AREA_OFFICER".equals(role)) {
				targetUrl = "/systemAdmin/home#/manageOngoingWorks";
				break;
			} 
		     
				
			/*
			 * else if( DMSConstants.ROLE_DEPARTMENT.equals(role) ) { targetUrl =
			 * "/systemAdmin/home#/manageImplAgency"; break; }
			 */
		   
			else {
				targetUrl = "/403";
			}
		}
		redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetUrl);
	}

	public RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}
}

