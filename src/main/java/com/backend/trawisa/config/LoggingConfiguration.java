package com.backend.trawisa.config;

import com.app.base.project.utils.Print;

import com.backend.trawisa.security.logger.RequestAndResponseLoggingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfiguration {
    @Bean
    public RequestAndResponseLoggingFilter requestResponseLoggingFilter() {
        return new RequestAndResponseLoggingFilter();
    }

}
