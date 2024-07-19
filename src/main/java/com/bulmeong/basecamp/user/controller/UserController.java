package com.bulmeong.basecamp.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("common")
public class UserController {

    @GetMapping("basecampPublicPage")
    public String publicPage() {

        return "/common/basecampPublicPage";
    }

    @GetMapping("basecampLoginPage")
    public String loginPage() {

        return "/common/basecampLoginPage";
    }

    @GetMapping("basecampMyInfoPage")
    public String myInfoPage() {

        return "/common/basecampMyInfoPage";
    }
}
