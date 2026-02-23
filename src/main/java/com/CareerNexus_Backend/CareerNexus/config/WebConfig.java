package com.CareerNexus_Backend.CareerNexus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map /uploads/** to the physical directory ./static/uploads/
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:./static/uploads/");
    }
}
