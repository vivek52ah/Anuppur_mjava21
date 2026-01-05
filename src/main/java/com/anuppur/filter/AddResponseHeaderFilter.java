package com.anuppur.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

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
		//httpServletResponse.setHeader("Content‐Security‐Policy", "script‐src 'self'");
		httpServletResponse.setHeader("Content-Security-Policy", "script-src 'self'; style-src 'self'; font-src 'self'; form-action 'self'; object-src 'none'; connect-src 'self'; img-src 'self';");


		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// ...
	}

}
