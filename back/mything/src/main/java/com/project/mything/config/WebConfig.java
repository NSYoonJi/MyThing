package com.project.mything.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").
                allowedOrigins("http://j8b207.p.ssafy.io", "https://j8b207.p.ssafy.io", "http://localhost:3000")
                .allowedMethods("*")
                .allowCredentials(true);
    }
}
