package com.anuppur.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;

/**
 * ✅ UPDATED FOR SPRING BOOT 3.2.5 & JAVA 21
 * Migrated from Springfox (Swagger 2.0) to Springdoc OpenAPI (OpenAPI 3.0)
 * 
 * Springfox is deprecated and not compatible with Spring Boot 3.x
 * Springdoc OpenAPI is the recommended replacement for Spring Boot 3.x
 */
@Configuration
public class SwaggerConfig {
    private static final String BEARER_AUTH = "bearerAuth";
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Anuppur Work Management System API")
                        .version("1.0.0")
                        .description("API documentation for Anuppur Work Management System"))
                .addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH))
                .components(new Components()
                        .addSecuritySchemes(BEARER_AUTH, new SecurityScheme()
                                .name(BEARER_AUTH)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
