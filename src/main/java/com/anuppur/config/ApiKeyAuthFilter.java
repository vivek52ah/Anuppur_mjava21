package com.anuppur.config;
/*
 * package com.dms.config;
 * 
 * import org.springframework.stereotype.Component; import
 * javax.servlet.FilterChain; import jakarta.servlet.Filter; import
 * javax.servlet.FilterConfig; import jakarta.servlet.ServletException; import
 * javax.servlet.ServletRequest; import jakarta.servlet.ServletResponse; import
 * javax.servlet.http.HttpServletRequest; import java.io.IOException;
 * 
 * @Component public class ApiKeyAuthFilter implements Filter {
 * 
 * private static final String API_KEY_HEADER = "X-API-KEY"; private static
 * final String API_KEY = "your-actual-api-key"; // Replace with your actual API
 * key
 * 
 * @Override public void init(FilterConfig filterConfig) throws ServletException
 * { }
 * 
 * @Override public void doFilter(ServletRequest request, ServletResponse
 * response, FilterChain chain) throws IOException, ServletException {
 * HttpServletRequest httpRequest = (HttpServletRequest) request; String apiKey
 * = httpRequest.getHeader(API_KEY_HEADER);
 * 
 * if (API_KEY.equals(apiKey)) { chain.doFilter(request, response); } else {
 * throw new ServletException("Invalid API key"); } }
 * 
 * @Override public void destroy() { } }
 * 
 */