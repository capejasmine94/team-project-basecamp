package com.bulmeong.basecamp.store.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.bulmeong.basecamp.store.dto.StoreDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class StoreCenterInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        HttpSession session = request.getSession();
        StoreDto sessionStoreInfo = (StoreDto)session.getAttribute("sessionStoreInfo");

        if(sessionStoreInfo == null){
            response.sendRedirect("/seller/login");
            return false;
        }

        return true;
    }
}
