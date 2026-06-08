package com.anuppur.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/**/*")
public class AddResponseHeaderFilter implements Filter {

	public AddResponseHeaderFilter() {

	}

	public void destroy() {
		// ...
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Set‐Cookie", "SameSite=strict");
		// Temporarily disable CSP to test if it's causing the issue
		// httpServletResponse.setHeader("Content-Security-Policy", ...);

		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// ...
	}

}
