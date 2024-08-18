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
        //초기화
        setPage();

        //세션 체크
        if(utils.getSession("campsite") == null) {
            response.sendRedirect("/seller/login");
            return false;
        }

        //마무리
        return true;
    }
   
    private void setPage() {
        String pageDetail = utils.getPageDetail();
        utils.setModel("page", pageInfo(pageDetail));
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

            case "campsite":
                pageDetail = "캠핑장 관리";
                isButtonActive = true;
                break;

            case "area":
                pageDetail = "구역 관리";
                isButtonActive = true;
                break;
            
            case "asset":
                pageDetail = "재무 관리";
                isButtonActive = true;
                break;

            case "statistics":
                pageDetail = "통계";
                break;

            case "review":
                pageDetail = "리뷰 관리";
                break;

            case "account":
                pageDetail = "계정 관리";
                isButtonActive = true;
                break;

            case "reservation":
                pageDetail = "예약 조회";
                break;
        }
        resultMap.put("title", pageDetail);
        resultMap.put("buttonActivated", isButtonActive ? "T" : "F");
        return resultMap;
    }
}
