package com.bulmeong.basecamp.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bulmeong.basecamp.common.util.Utils;

@RequestMapping("camp/")
@Controller
public class CampsiteController {

    //유틸 사용법 1 : 유틸을 선언한다
    @Autowired
    private Utils util;

    @RequestMapping("unity")
    public String unity(){
        //유틸 사용법 2 : 필요한 페이지에 로그인 유저를 추가한다 
        //유틸 사용법 3 : 세션에서 'sessionUserInfo'로 UserDto를 가져올 수 있다
        util.loginUser();

        return "camp/unity";
    }

    @RequestMapping("mainPage")
    public String mainPage(){
        return "camp/uploadTab";
    }
}
