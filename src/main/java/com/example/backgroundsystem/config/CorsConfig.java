package com.example.backgroundsystem.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
//        config.addAllowedOriginPattern("http://localhost:8080");
//        config.addAllowedOriginPattern("https://web.postman.co");
//        config.addAllowedOriginPattern("https://superarilo.online");
//        config.addAllowedOriginPattern("https://www.superarilo.online");
//        config.addAllowedHeader("Access-Control-Allow-Headers");
//        config.addAllowedHeader("content-type");
//        config.addAllowedHeader("Access-Control-Allow-Origin");
//        config.addAllowedHeader("Access-Control-Allow-Headers");
//        config.addAllowedHeader("token");
//        config.addExposedHeader("Authorization");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        source.registerCorsConfiguration("/**", config);
        return new FilterRegistrationBean<>(new CorsFilter(source));
    }
}