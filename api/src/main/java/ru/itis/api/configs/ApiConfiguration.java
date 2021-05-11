package ru.itis.api.configs;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackages = "ru.itis.api.utils")
public class ApiConfiguration {

}
