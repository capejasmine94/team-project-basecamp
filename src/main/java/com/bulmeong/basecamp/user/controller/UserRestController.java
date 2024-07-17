package com.bulmeong.basecamp.user.controller;

import com.bulmeong.basecamp.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
        result.add("needLogin", utils.isNeedLogin());
        return result;
    }

    @RequestMapping("getSessionUserId")
    public RestResponseDto getSessionUserId(HttpSession session) {
        RestResponseDto result = new RestResponseDto();
        result.setResult("success");

        if (utils.isNeedLogin()) {
            utils.loginUser(1);
        }
        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        result.add("userId", userDto.getId());

        return result;
    }
}
