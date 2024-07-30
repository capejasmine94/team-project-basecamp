package com.bulmeong.basecamp.user.controller;

import com.bulmeong.basecamp.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.common.dto.RestResponseDto;
import com.bulmeong.basecamp.common.util.Utils;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
    @Autowired
    private Utils utils;


    @RequestMapping("checkLogin")
    public RestResponseDto checkLogin() {
        RestResponseDto result = new RestResponseDto();
        result.setResult("success");
        boolean isLogined = utils.isNeedLogin();
        if(!isLogined) {
            result.add("needLogin", isLogined);
            result.add("userInfo", utils.getSession("sessionUserInfo"));
        }
        else
        result.add("needLogin", isLogined);
        return result;
    }

    @RequestMapping("getSessionUserId")
    public RestResponseDto getSessionUserId(HttpSession session) {
        RestResponseDto result = new RestResponseDto();
        result.setResult("success");

        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");

        if (userDto != null) {
            result.add("userId", userDto.getId());
        } else {
            result.add("userId", null);
        }

        return result;
    }
}
