package com.bulmeong.basecamp.config;

import com.bulmeong.basecamp.user.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        UserDto sessionUserInfo = (UserDto) session.getAttribute("sessionUserInfo");

        if (sessionUserInfo == null) {
            // 로그인 페이지와 회원가입 페이지로의 요청은 세션 확인 없이 통과
            String requestURI = request.getRequestURI();
            if (requestURI.equals("/common/basecampLoginPage") || requestURI.equals("/common/basecampSignPage")
                    || requestURI.equals("/common/signProcess") || requestURI.equals("/common/loginProcess")
                    || requestURI.equals("/common/basecampPublicPage")) {
                return true;
            }
            // 요청 페이지 세션 저장 : 프로젝트 끝나면 지우는게 좋을수도
            session.setAttribute("redirectAfterLogin", requestURI);
            response.sendRedirect("/common/basecampLoginPage");
            return false;
        }
        return true;
    }
}