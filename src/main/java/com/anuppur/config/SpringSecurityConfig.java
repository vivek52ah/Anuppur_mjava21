package com.anuppur.config;

import java.util.List;
import java.util.Locale;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.anuppur.filter.CaptchaAuthenticationFilter;
import com.anuppur.handler.DMSAuthenticationSuccessHandler;
import com.anuppur.security.CsrfCookieFilter;
import com.anuppur.security.DMSPasswordEncoder;
import com.anuppur.security.SpaCsrfTokenRequestHandler;
import com.anuppur.service.impl.UserDetailsServiceImpl;
import com.anuppur.util.JwtFilter;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@SuppressWarnings("all")
public class SpringSecurityConfig {

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

    @Bean
    public PasswordEncoder dmsPasswordEncoder() {
        return new DMSPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(dmsPasswordEncoder());
        return provider;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain staticResourcesFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher(PathRequest.toStaticResources().atCommonLocations())
            .authorizeHttpRequests(authz -> authz.anyRequest().permitAll())
            .csrf(AbstractHttpConfigurer::disable)
            .requestCache(AbstractHttpConfigurer::disable)
            .securityContext(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CookieCsrfTokenRepository csrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        csrfTokenRepository.setCookieCustomizer(cookie -> cookie
                .path("/")
                .secure(true)
                .sameSite("Lax"));

        // Disable CORS for login page - it's a same-origin request
        http.cors(cors -> cors.disable());
        
        http
            .authenticationProvider(daoAuthenticationProvider())
            .csrf(csrf -> csrf
                .csrfTokenRepository(csrfTokenRepository)
                .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler())
                // Bearer-token mobile calls do not use browser session cookies.
                .ignoringRequestMatchers("/mobile/**", "/mobilelogin", "/mobilelogin/**"))
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/login", "/error", "/captcha", "/forgotpassword", "/resetpassword", "/aboutUs", "/guidelines", "/contactUs",
                    "/mobilelogin", "/mobilelogin/**").permitAll()
                .requestMatchers(
                    "/awms.apk", "/awms_production.apk",
                    "/new-assets/**", "/assets/**", "/css/**", "/js/**",
                    "/img/**", "/fonts/**", "/angular/**", "/images/**",
                    "/Buttons-1.5.1/**", "/DataTables-1.10.16/**",
                    "/JSZip-2.5.0/**", "/dhs/**", "/webjars/**"
                ).permitAll()
                .requestMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .requestMatchers("/systemAdmin/**", "/superAdmin/**", "/ceo/**").authenticated()
                .requestMatchers("/mobile/**").authenticated()
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler))
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID", "XSRF-TOKEN")
                .logoutSuccessUrl("/login?logout")
                .permitAll())
            .exceptionHandling(exception -> exception
                .accessDeniedPage("/403")
                .authenticationEntryPoint((request, response, authException) -> {
                    if (isAjaxOrApiRequest(request)) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("text/plain;charset=UTF-8");
                        response.getWriter().write("Unauthorized access");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/login");
                    }
                }))
            .sessionManagement(session -> session
                .sessionFixation(fixation -> fixation.migrateSession())
                .invalidSessionUrl("/login?timeout")
                .maximumSessions(2)
                .expiredUrl("/login?timeout"))
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.deny())
                .httpStrictTransportSecurity()
                    .includeSubDomains(true)
                    .maxAgeInSeconds(31536000)
                .and()
                .referrerPolicy(referrer -> referrer.policy(ReferrerPolicy.NO_REFERRER_WHEN_DOWNGRADE))
                .addHeaderWriter(new StaticHeadersWriter("X-Content-Type-Options", "nosniff"))
                .addHeaderWriter(new StaticHeadersWriter("X-XSS-Protection", "1; mode=block")));

        http.addFilterBefore(new CaptchaAuthenticationFilter("/login", "/login?error"),
                UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);

        return http.build();
    }

    /** Detect mobile API and Angular/jQuery XHR (template + data) requests. */
    private boolean isAjaxOrApiRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (uri != null && uri.contains("/mobile/")) {
            return true;
        }
        if (uri != null && uri.contains("/mobilelogin")) {
            return true;
        }
        if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
            return true;
        }
        String accept = request.getHeader("Accept");
        return accept != null && accept.contains("application/json");
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(daoAuthenticationProvider())
            .userDetailsService(userDetailsService)
            .passwordEncoder(dmsPasswordEncoder())
            .and()
            .build();
    }

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
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:4200",
                "http://localhost:8080",
                "http://localhost:8085",
                "http://127.0.0.1:8085",
                "http://raman-coe.mapit.gov.in:8080",
                "https://raman-coe.mp.gov.in"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
