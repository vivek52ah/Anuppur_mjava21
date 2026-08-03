package com.anuppur.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Embedded-container settings that complement the HTTP security filter chain. */
@Configuration
public class ServerHardeningConfig {

    @Bean
    WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatSecurityCustomizer() {
        return factory -> factory.addConnectorCustomizers(connector -> {
            connector.setAllowTrace(false);
            connector.setProperty("serverRemoveAppProvidedValues", "true");
            connector.setProperty("xpoweredBy", "false");
        });
    }
}
