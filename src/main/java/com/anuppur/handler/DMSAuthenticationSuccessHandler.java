package com.anuppur.handler;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.anuppur.constants.DMSConstants;

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
			  if(role.equals("ROLE_AREA_OFFICER")) {
				 // logger.info("i am in role butwy ");
			      // targetUrl = "/systemAdmin/home#/manageOngoingWorks"; // Example URL for AREA_OFFICER
			       redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetUrl);
			       return;
			       }
			       if(role.equals("ROLE_DEPARTMENT")) {
			    	   //logger.info("i am in role butwy ");
				       targetUrl = "/systemAdmin/home#/manageOngoingWorks"; // Example URL for AREA_OFFICER
				       redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetUrl);
				       return;
				       }
			       if(role.equals("ROLE_DM")) {
			    	   //logger.info("i am in role butwy ");
				       targetUrl = "/systemAdmin/home#/manageOngoingWorks"; // Example URL for AREA_OFFICER
				       redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetUrl);
				       return;
				       }
			       
			       if(role.equals("ROLE_CEO")) {
			    	   //logger.info("i am in role butwy ");
				       targetUrl = "/systemAdmin/home#/manageOngoingWorks"; // Example URL for AREA_OFFICER
				       redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetUrl);
				       return;
				       }
			       
			       if(role.equals("ROLE_SYSTEM_ADMIN")) {
			    	   targetUrl = "/systemAdmin/home#/manageOngoingWorks"; // Example URL for AREA_OFFICER
				       redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetUrl);
				       return;
			       }
		
		    if( DMSConstants.ROLE_SYSTEM_ADMIN.equals(role) || DMSConstants.ROLE_ADMIN.equals(role) || DMSConstants.ROLE_DEPARTMENT.equals(role)|| DMSConstants.ROLE_DEPT_DISTRICT.equals(role) || DMSConstants.ROLE_AGENCY_ADMIN.equals(role) || DMSConstants.ROLE_DM.equals(role) || DMSConstants.ROLE_CEO.equals(role) ) {
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
