package com.backend.trawisa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("*"); // or specific origins
        corsConfig.addAllowedMethod("*"); // or specific methods
        corsConfig.addAllowedHeader("*"); // or specific headers
        // Add more CORS configurations as needed
        return corsConfig;
    }
}