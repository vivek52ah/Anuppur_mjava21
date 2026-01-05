package com.anuppur.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.anuppur.controller.MobileApiController;
import com.anuppur.controller.MobileController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(requestHandler -> 
                    requestHandler.declaringClass() != null &&(
                    requestHandler.declaringClass().equals(MobileApiController.class) ||  requestHandler.declaringClass().equals(MobileController.class) )
                )
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(new BasicAuth("basicAuth")))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(
                        Collections.singletonList(new springfox.documentation.service.SecurityReference(
                                "basicAuth", new springfox.documentation.service.AuthorizationScope[0])))
                .build();
    }
}
