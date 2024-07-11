package com.bulmeong.basecamp.insta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bulmeong.basecamp.common.util.Utils;

@Controller
@RequestMapping("insta")
public class InstaController {
    @Autowired
    private Utils util;

    // 고객이 인스타 최초 접속시 나타나는 Page
    @RequestMapping("confirmPage")
    public String confirmPage(){
        util.loginUser();

        return "insta/confirmPage";
    }

}
