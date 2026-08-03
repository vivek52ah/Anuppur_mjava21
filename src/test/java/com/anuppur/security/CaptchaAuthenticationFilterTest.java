package com.anuppur.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.anuppur.constants.DMSConstants;
import com.anuppur.filter.CaptchaAuthenticationFilter;

class CaptchaAuthenticationFilterTest {

    @Test
    void acceptsSessionCaptchaOnlyOnce() throws Exception {
        CaptchaAuthenticationFilter filter = new CaptchaAuthenticationFilter(protection());
        MockHttpServletRequest request = loginRequest();
        request.getSession().setAttribute(DMSConstants.CAPTCHA_LOGIN, "654321");
        request.addParameter("captchaText", "654321");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(request, response, chain);

        assertThat(chain.getRequest()).isNotNull();
        assertThat(request.getSession().getAttribute(DMSConstants.CAPTCHA_LOGIN)).isNull();
    }

    @Test
    void rejectsMissingWrongAndReusedCaptcha() throws Exception {
        CaptchaAuthenticationFilter filter = new CaptchaAuthenticationFilter(protection());
        MockHttpServletRequest request = loginRequest();
        request.getSession().setAttribute(DMSConstants.CAPTCHA_LOGIN, "654321");
        request.addParameter("captchaText", "123456");
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, new MockFilterChain());

        assertThat(response.getRedirectedUrl()).isEqualTo("/login?error");
        assertThat(request.getSession().getAttribute(DMSConstants.CAPTCHA_LOGIN)).isNull();
    }

    private MockHttpServletRequest loginRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/login");
        request.setServletPath("/login");
        request.addParameter("username", "user@example.com");
        return request;
    }

    private LoginProtectionService protection() {
        return new LoginProtectionService(30, 10, 5, 15, 5, 15);
    }
}
