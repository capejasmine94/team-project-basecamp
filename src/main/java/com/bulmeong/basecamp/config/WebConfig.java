package com.bulmeong.basecamp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bulmeong.basecamp.camp.interceptor.CampsiteInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private CampsiteInterceptor campsiteInterceptor;
    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        if(System.getProperty("os.name").charAt(0) == 'W'){
            registry.addResourceHandler("/images/**").addResourceLocations("file:///C://basecampImage/");
        } else if(System.getProperty("os.name").charAt(0) == 'M'){
            // * 들어가서 안된 걸 수도 있으니 문제 되면 삭제해보세요
            String userHome = System.getProperty("user.home");
            registry.addResourceHandler("/images/**").addResourceLocations("file://" + userHome + "/basecampImage/");
            // registry.addResourceHandler("/images/**").addResourceLocations("file:///Users/basecampImage/" ,"file:///Users/simgyujin/basecampImage/");
        } else if(System.getProperty("os.name").charAt(0) == 'L'){
            // * 들어가서 안된 걸 수도 있으니 문제 되면 삭제해보세요
            registry.addResourceHandler("/images/**").addResourceLocations("file:///basecampImage/");
        }
    }

    @RequestScope
    public HttpServletRequest httpServletRequest() {
        return new HttpServletRequestWrapper((HttpServletRequest) RequestContextHolder.getRequestAttributes().resolveReference(RequestAttributes.REFERENCE_REQUEST));
    }

     @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 특정 컨트롤러 경로에 대해 인터셉터를 등록합니다.
        registry.addInterceptor(campsiteInterceptor).addPathPatterns("/campsiteCenter/**")
        .excludePathPatterns("/campsiteCenter/registerUserProcess","/campsite/resv");

         // 공용네비 .addPathPatterns 본인 시작경로 추가
         registry.addInterceptor(sessionInterceptor)
                 .addPathPatterns("/user/**", "/secondhandProduct/**")
                 .excludePathPatterns("/user/login", "/user/register", "/user/registerProcess", "/user/loginProcess");
    }

}
