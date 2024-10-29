package com.backend.todolist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Adjust to your API path
                .allowedOrigins("http://localhost:3000") // Allow your frontend origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") // Allow necessary methods
                .allowedHeaders("*") // Allow all headers or specify as needed
                .allowCredentials(true); // Allow credentials if needed
    }
}
