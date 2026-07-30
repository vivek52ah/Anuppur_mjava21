package com.anuppur.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.anuppur.security.SecureUploadInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final SecureUploadInterceptor secureUploadInterceptor;

	public WebConfig(SecureUploadInterceptor secureUploadInterceptor) {
		this.secureUploadInterceptor = secureUploadInterceptor;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("login");
		registry.addViewController("/login").setViewName("login");
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(secureUploadInterceptor);
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		addStaticHandler(registry, "/assets/**", "classpath:/static/assets/");
		addStaticHandler(registry, "/css/**", "classpath:/static/css/");
		addStaticHandler(registry, "/js/**", "classpath:/static/js/");
		addStaticHandler(registry, "/img/**", "classpath:/static/img/");
		addStaticHandler(registry, "/angular/**", "classpath:/static/angular/");
		addStaticHandler(registry, "/fonts/**", "classpath:/static/fonts/");
		addStaticHandler(registry, "/Buttons-1.5.1/**", "classpath:/static/Buttons-1.5.1/");
		addStaticHandler(registry, "/DataTables-1.10.16/**", "classpath:/static/DataTables-1.10.16/");
		addStaticHandler(registry, "/JSZip-2.5.0/**", "classpath:/static/JSZip-2.5.0/");
		addStaticHandler(registry, "/dhs/**", "classpath:/static/dhs/");
		addStaticHandler(registry, "/new-assets/**", "classpath:/static/new-assets/");
		addStaticHandler(registry, "/leaflet/**", "classpath:/static/leaflet/");

		registry.addResourceHandler("/js/leaflet/images/**")
				.addResourceLocations("classpath:/static/js/leaflet/images/")
				.setCachePeriod(0);
	}

	private void addStaticHandler(ResourceHandlerRegistry registry, String pattern, String location) {
		registry.addResourceHandler(pattern)
				.addResourceLocations(location)
				.setCachePeriod(0);
	}
}
