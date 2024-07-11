package com.bulmeong.basecamp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        if(System.getProperty("os.name").charAt(0) == 'W'){
            registry.addResourceHandler("/images/**").addResourceLocations("file:///C://basecampImage/");
        } else if(System.getProperty("os.name").charAt(0) == 'M'){
            // * 들어가서 안된 걸 수도 있으니 문제 되면 삭제해보세요
            registry.addResourceHandler("/images/**").addResourceLocations("file:///Users/basecampImage/");
        }
    }

    @RequestScope
    public HttpServletRequest httpServletRequest() {
        return new HttpServletRequestWrapper((HttpServletRequest) RequestContextHolder.getRequestAttributes().resolveReference(RequestAttributes.REFERENCE_REQUEST));
    }
}
