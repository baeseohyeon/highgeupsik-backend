package com.highgeupsik.backend.api.config;

import com.highgeupsik.backend.api.utils.UrlGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UrlGeneratorConfig {

    @Bean
    public UrlGenerator urlGenerator() {
        return new UrlGenerator();
    }
}
