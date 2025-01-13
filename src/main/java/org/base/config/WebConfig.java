package org.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("null", "http://localhost:8080")
                        .allowedMethods("GET", "HEAD", "POST")  // 允许的请求方法
                        .allowCredentials(true)  // 允许携带凭证
                        .allowedHeaders("*");  // 允许所有请求头
            }
        };
    }
}
