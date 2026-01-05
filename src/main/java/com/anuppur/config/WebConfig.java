package com.anuppur.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.resource.ContentVersionStrategy;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.resource.VersionResourceResolver;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

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
	    registry.addInterceptor(localeChangeInterceptor());
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		VersionResourceResolver versionResourceResolver = new VersionResourceResolver()
				.addVersionStrategy(new ContentVersionStrategy(), "/**");

		registry.addResourceHandler("/**/assets/**", "/**/css/**", "/**/js/**", "/**/img/**", "/**/angular/**", "/**/fonts/**", "/**/Buttons-1.5.1/**", "/**/DataTables-1.10.16/**", "/**/JSZip-2.5.0/**", "/**/dhs/**")
		.addResourceLocations("classpath:/static/")
		.setCachePeriod(60 * 60 * 24 * 365) /* one year */
		.resourceChain(true)
		.addResolver(versionResourceResolver);
		 registry.addResourceHandler("/js/leaflet/images/**")
         .addResourceLocations("classpath:/static/js/leaflet/images/")
         .setCachePeriod(60 * 60 * 24 * 365) // 1 year cache period
         .resourceChain(true)
         .addResolver(versionResourceResolver);
	}

	@Bean
	public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
		return new ResourceUrlEncodingFilter();
	}
	
	
	 @Override
	    public void configurePathMatch(PathMatchConfigurer configurer) {
	        configurer.setUseSuffixPatternMatch(false);
	    }
	
	

	
}
