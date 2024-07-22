package com.bulmeong.basecamp.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulmeong.basecamp.user.dto.*;
import com.bulmeong.basecamp.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class Utils {
    @Autowired
     private HttpServletRequest request;
    @Autowired
     private UserService userService;

    
    //=========================================================================================
    //   Utils (made by. jjh)
    //  사용법
    //  1. 사용할 Controller에 Autowired를 해주세요
    //  2. Autowired를 한 변수를 통해 필요한 기능을 불러와 사용합니다
    //  
    //  주의사항 : 실험적이니 버그가 발생할 수 있습니다. 버그가 있으면 문의해주세요
    //=========================================================================================


    
    //=========================================================================================
    //정규식 패턴을 수정하려면 여기를 건드시면 됩니다.
    //=========================================================================================
    //아이디
    private final String ID_PATTERN = "^[a-z0-9_]{1,19}$"; 
    //비밀번호
    private final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!#%*?&])[A-Za-z\\d@$!%*?&]{8,16}$"; 
    //이메일
    private final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; 
    //전화번호
    private final String PHONE_NUMBER_PATTERN = "^0(10|2|[3-9]\\d{1})[- ]?\\d{3,4}[- ]?\\d{4}$"; 



    /**세션에 오브젝트를 추가합니다. 컨트롤러에 세션을 선언할 필요가 없습니다.
     * @param name : 세션이름
     * @param object : 세션 오브젝트
     */
    public void setSession(String name, Object object) {
        request.getSession().setAttribute(name, object);
    }

     /**세션 안의 오브젝트를 가져옵니다. 원하는 형태로 바로 불러올 수 있습니다.
     * @param name : 세션이름
     * @return 세션의 오브젝트
     */
    public <T>T getSession(String name) {
        T data = (T)request.getSession().getAttribute(name);
        if(data == null)
            System.out.println("Utils - 세션에 오브젝트 형태가 다릅니다!!! : " + name);
        return data;
    }

    /** 로그인이 필요한지 여부를 알아냅니다. 결과가 true라면 로그인이 필요한 상태입니다. */
    public boolean isNeedLogin() {
        return request.getSession().getAttribute("sessionUserInfo") == null;
    }

    /** 유저 1로 즉시 로그인합니다. */
    public void loginUser() {
        UserDto params = new UserDto();
        params.setAccount("user1");
        params.setPassword("1234");
        UserDto userDto = userService.getUserByAccountAndPassword(params);
        request.getSession().setAttribute("sessionUserInfo", userDto);
    }

     /** 특정 숫자의 유저로 즉시 로그인합니다. 존재하지 않는 유저 숫자를 입력하면, 그 숫자의 가장 가까운 유저로 로그인합니다. */
    public void loginUser(int number) {
        number = Math.max(1, Math.min(number, 500));
        UserDto params = new UserDto();
        params.setAccount("user" + number);
        params.setPassword("1234");
        UserDto userDto = userService.getUserByAccountAndPassword(params);
        request.getSession().setAttribute("sessionUserInfo", userDto);
    }
    
    /** 로그아웃합니다. */
    public void logOut() {
        request.getSession().invalidate();
    }

    /** 현재 url의 페이지 이름을 가져옵니다.
     * @return localhost/user/login의 경우 login을 가져온다
    */
    public String getPageDetail() {
        
        String url = request.getRequestURL().toString();
         String[] parts = url.split("/");

         if (parts.length >= 5) {
             return parts[4];
         } else {
             return "URL에 세부 키워드가 없습니다.";
         }
    }

     /** 현재 url의 카테고리 이름을 가져옵니다.
     * @return localhost/user/login의 경우 user를 가져온다
    */
    public String getPageMain() {
        
        String url = request.getRequestURL().toString();
         String[] parts = url.split("/");

         if (parts.length >= 4) {
             return parts[3];
         } else {
             return "URL에 메인 키워드가 없습니다.";
         }
    }
}
