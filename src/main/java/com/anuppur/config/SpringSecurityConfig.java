package com.anuppur.config;

import java.util.Arrays;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.anuppur.filter.CaptchaAuthenticationFilter;

import com.anuppur.handler.DMSAuthenticationSuccessHandler;
import com.anuppur.service.impl.UserDetailsServiceImpl;
import com.anuppur.servlet.CaptchaGenServlet;
import com.anuppur.util.JwtFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SuppressWarnings("all")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.enable-csrf}")
    private boolean csrfEnabled;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private DMSAuthenticationSuccessHandler authenticationSuccessHandler;
    
    @Autowired
    private JwtFilter jwtFilter;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Define CSRF request matcher for enabled CSRF protection
    RequestMatcher csrfRequestMatcher = new RequestMatcher() {
        private AntPathRequestMatcher[] requestMatchers = {
            new AntPathRequestMatcher("/**/login/*"),
            new AntPathRequestMatcher("/**/forgotpassword/*"),
            new AntPathRequestMatcher("/**/api/*"),
            new AntPathRequestMatcher("/**/resetpassword/*"),
            new AntPathRequestMatcher("/**/registrationForm/*"),
            new AntPathRequestMatcher("/**/doSignUp/*")
        };

        @Override
        public boolean matches(HttpServletRequest request) {
            for (AntPathRequestMatcher rm : requestMatchers) {
                if (rm.matches(request)) {
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().requireCsrfProtectionMatcher(csrfRequestMatcher)
            .ignoringAntMatchers("/mobilelogin", "/captcha", "/forgotpassword", "/aboutUs", "/guidelines", "/contactUs") // Ignore CSRF for mobile login
            .and()
            .authorizeRequests()
            .antMatchers("/", "/captcha", "/forgotpassword").permitAll()
            .antMatchers("/aboutUs").permitAll()
            .antMatchers("/guidelines").permitAll()
            .antMatchers("/contactUs").permitAll()
            
          //  .antMatchers("/mobileLogin").authenticated()
            // Allow Swagger UI and API docs URLs
            .antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**").permitAll()
            
            // Swagger-specific URLs (secured with authentication)
           // .antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**").authenticated()
            
       
            // Other secure URLs
            .antMatchers("/systemAdmin/**", "/superAdmin/**", "/ceo/**").authenticated()
            .antMatchers("/mobile/**").authenticated() // Secure mobile APIs
            .and().formLogin().loginPage("/login")
            .successHandler(authenticationSuccessHandler).and().logout().permitAll().and()
            .exceptionHandling().accessDeniedPage("/403") // Access denied page for non-permitted users
            .authenticationEntryPoint((request, response, authException) -> {
                if (isApiRequest(request)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Unauthorized access");
                } else {
                    response.sendRedirect("/login"); // Redirect for web-based requests
                }
            })
            .accessDeniedHandler((request, response, accessDeniedException) -> {
                if (isApiRequest(request)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Forbidden access");
                } else {
                    response.sendRedirect("/access-denied"); // Redirect for web-based requests
                }
            }).and().sessionManagement()
            .maximumSessions(2).expiredUrl("/login?timeout");

        // Security headers
        http.headers().frameOptions().deny();
        http.headers().xssProtection();
        http.headers().defaultsDisabled().contentTypeOptions();
        http.sessionManagement().sessionFixation().migrateSession();
        http.headers().cacheControl();
        http.headers().httpStrictTransportSecurity().includeSubDomains(true).maxAgeInSeconds(31536000);
        http.headers().referrerPolicy(ReferrerPolicy.NO_REFERRER_WHEN_DOWNGRADE);

        // Content Security Policy (CSP)
        http.headers().contentSecurityPolicy(
        	    "default-src 'self'; " +
        	    "script-src 'self' 'unsafe-inline' 'unsafe-eval' https://cdn.canvasjs.com https://unpkg.com https://code.jquery.com https://cdnjs.cloudflare.com https://unpkg.com/leaflet@1.7.1/dist/leaflet.js; " +
        	    "style-src 'self' 'unsafe-inline' https://unpkg.com https://unpkg.com/leaflet@1.7.1/dist/leaflet.css; " +
        	    "style-src-elem 'self' 'unsafe-inline' https://unpkg.com https://unpkg.com/leaflet@1.7.1/dist/leaflet.css; " +
        	    "script-src-elem 'self' 'unsafe-inline' 'unsafe-eval' https://cdn.canvasjs.com https://unpkg.com https://code.jquery.com https://cdnjs.cloudflare.com https://unpkg.com/leaflet@1.7.1/dist/leaflet.js; " +
        	    "object-src 'none'; " +
        	    "form-action 'self'; " +
        	    "font-src 'self'; " +
        	    "media-src 'none'; " +
        	    "connect-src 'self' http://localhost:8060/jsonsigner/Sign; " +
        	    "img-src 'self' data: blob: https://*.google.com https://unpkg.com/leaflet@1.7.1/dist/images/ https://*.tile.openstreetmap.org; " + 
        	    "frame-src 'self'; " +
        	    "child-src 'self'; " +
        	    "report-uri /csp-violation-report-endpoint"
        	);

//        http.headers()
//        .contentSecurityPolicy("default-src 'self'; script-src 'self'; style-src 'self'; font-src 'self'; form-action 'self'; object-src 'none'; connect-src 'self'; img-src 'self';");

        // Additional headers
       // http.headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "domain"));
        
        
        // Add captcha filter before the authentication filter
        http.addFilterBefore(new CaptchaAuthenticationFilter("/login", "/login?error"),
                UsernamePasswordAuthenticationFilter.class);
        
        //http.addFilterBefore(new TokenValidationFilter("/mobileLogin"), UsernamePasswordAuthenticationFilter.class);
        // Add JWT authentication filter for mobile APIs
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
       // http.addFilterBefore(new InputSanitizationFilter(), UsernamePasswordAuthenticationFilter.class);


        // Enable CSRF protection if enabled in properties
        if (!csrfEnabled) {
            http.csrf().disable(); // to disable the CSRF protection
        }

        // Additional header settings for security
        http.headers().addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy", "default-src 'self'"))
            .addHeaderWriter(new StaticHeadersWriter("X-WebKit-CSP", "default-src 'self'"));
        http.headers().httpStrictTransportSecurity().maxAgeInSeconds(31536000).includeSubDomains(true);
    }
    private boolean isApiRequest(HttpServletRequest request) {
    	
        return  request.getRequestURI().startsWith("/anuppur/mobile/");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/angular/**", "/dhs/**");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

   

    @Bean
    public ServletRegistrationBean captchaServlet() {
        ServletRegistrationBean captcha = new ServletRegistrationBean(new CaptchaGenServlet(), "/captcha");
        return captcha;
    }

    // Register HttpSessionEventPublisher
    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }
    
    //Sumit Code CORS Origins
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://raman-coe.mapit.gov.in:8080")); // ✅ Allowed Origin
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
        config.setAllowCredentials(true); // ✅ For Cookies and Auth
        config.setMaxAge(3600L); // Optional: Cache preflight for 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Apply globally
        return source;
    }
}
