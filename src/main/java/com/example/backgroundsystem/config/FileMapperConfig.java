package com.example.backgroundsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileMapperConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            registry.addResourceHandler("/image/**").addResourceLocations("file:D:/TempCenter/imageBlog/");
            registry.addResourceHandler("/music/**").addResourceLocations("file:D:/TempCenter/imageBlog/music/");
            registry.addResourceHandler("/note/**").addResourceLocations("file:D:/TempCenter/imageBlog/note/");
        } else {  //linux 和mac
            registry.addResourceHandler("/image/**").addResourceLocations("file:/www/wwwroot/imageBlog/");
            registry.addResourceHandler("/music/**").addResourceLocations("file:/www/wwwroot/imageBlog/music/");
            registry.addResourceHandler("/note/**").addResourceLocations("file:/www/wwwroot/imageBlog/note/");
        }
    }
}
