package com.backend.trawisa;

import com.backend.trawisa.config.LoggingConfiguration;

import com.backend.trawisa.security.properties.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
public class TrawisaApplication {


	public static void main(String[] args) {


		SpringApplication.run(TrawisaApplication.class, args);

	}

}
