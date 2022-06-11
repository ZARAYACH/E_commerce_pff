package com.Ecommerce.confige;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;
@Configuration
public class ResourceConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uplaodPath = Paths.get("./images/products");
        Path userImages = Paths.get("./images/users");
        String imgUplaod= uplaodPath.toFile().getAbsolutePath();
        String userimagess= userImages.toFile().getAbsolutePath();
        registry.addResourceHandler("/images/products/**").addResourceLocations("file:/"+imgUplaod+"/");
        registry.addResourceHandler("/images/users/**").addResourceLocations("file:/"+userimagess+"/");
        registry.addResourceHandler("/static/**").addResourceLocations("class:/static/");

    }
}
