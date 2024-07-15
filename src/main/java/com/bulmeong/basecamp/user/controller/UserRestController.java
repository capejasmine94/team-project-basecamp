package com.bulmeong.basecamp.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.common.dto.RestResponseDto;
import com.bulmeong.basecamp.common.util.Utils;

@RestController
public class UserRestController {
    @Autowired
    Utils utils;

    public RestResponseDto checkLogin() {
        return null;
    }
}
