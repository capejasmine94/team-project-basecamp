package com.bulmeong.basecamp.camp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("camp")
public class CampsiteUserController {
    @RequestMapping("unity")
    public String unity(){
        return "camp/unity";
    }
    @RequestMapping("")
    public String mainPage1() {
        return "camp/userMain";
    }
}
