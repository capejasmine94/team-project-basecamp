package com.bulmeong.basecamp.camp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CampsiteController {
    @RequestMapping("camp/unity")
    public String unity(){
        return "camp/unity";
    }
}
