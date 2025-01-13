package com.saintkream.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /chatimages/** URL 패턴 매핑
        registry.addResourceHandler("/chatimages/**")
                .addResourceLocations(
                        "file:" + System.getProperty("user.dir") + "/src/main/resources/static/chatimages/")
                .setCachePeriod(3600); // 캐시 기간 1시간

        // /uploads/** URL 패턴 매핑
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/src/main/resources/static/uploads/")
                .setCachePeriod(3600); // 캐시 기간 1시간

        // /images/** URL 패턴 매핑
        registry.addResourceHandler("/images/**")
        .addResourceLocations("file:" + System.getProperty("user.dir") + "/src/main/resources/static/images/")
        .setCachePeriod(3600); // 캐시 기간 1시간
    }
}
