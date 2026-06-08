package com.anuppur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;

// ✅ CHANGED: org.springframework.boot.web.support → org.springframework.boot.web.servlet.support
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaRepositories
@EnableAsync
@ServletComponentScan
@SpringBootApplication
public class DmsAnuppurApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DmsAnuppurApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DmsAnuppurApplication.class);
    }

}