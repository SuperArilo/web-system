package com.example.backgroundsystem.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Adapter implements WebMvcConfigurer {
    final
    tokenInterceptor tokenInterceptor;
    final
    AdminInterceptor adminInterceptor;
    public Adapter(AdminInterceptor adminInterceptor, tokenInterceptor tokenInterceptor) {
        this.adminInterceptor = adminInterceptor;
        this.tokenInterceptor = tokenInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/api/user/check");
        registry.addInterceptor(adminInterceptor).addPathPatterns("/api/album/set","/api/album/del");
    }
}
