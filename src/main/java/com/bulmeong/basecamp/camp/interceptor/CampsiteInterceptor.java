package com.bulmeong.basecamp.camp.interceptor;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.bulmeong.basecamp.common.util.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CampsiteInterceptor implements HandlerInterceptor {
    @Autowired
    private Utils utils;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 특정 함수를 호출
        String pageDetail = utils.getPageDetail();
        request.getSession().setAttribute("pageInfo", pageInfo(pageDetail));
        // 계속 진행하려면 true 반환, 요청을 중단하려면 false 반환
        return true;
    }

    private Map<String,String> pageInfo(String detail) {
        Map<String,String> resultMap = new HashMap<>();
        boolean isButtonActive = false;
        String pageDetail = "";
        switch (detail) {
            case "main":
                pageDetail = "메인 페이지";
                break;

            case "registerCamp":
                pageDetail = "캠핑장 등록";
                isButtonActive = true;
                break;

            case "manageCamp":
                pageDetail = "캠핑장 관리";
                isButtonActive = true;
                break;

            case "manageArea":
                pageDetail = "구역 관리";
                isButtonActive = true;
                break;
            
            case "manageAsset":
                pageDetail = "재무 관리";
                isButtonActive = true;
                break;

            case "statisticsCamp":
                pageDetail = "통계";
                break;

            case "manageCampReview":
                pageDetail = "리뷰 관리";
                break;

            case "manageCampAccount":
                pageDetail = "계정 관리";
                isButtonActive = true;
                break;

            case "manageReservation":
                pageDetail = "예약 관리";
                isButtonActive = true;
                break;
        }
        resultMap.put("pageDetail", pageDetail);
        resultMap.put("isButtonActive", isButtonActive ? "T" : "F");
        return resultMap;
    }
}
