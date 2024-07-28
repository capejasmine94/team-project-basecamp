package com.bulmeong.basecamp.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bulmeong.basecamp.camp.service.CampsiteService;
import com.bulmeong.basecamp.common.util.Utils;


@Controller
@RequestMapping("camp")
public class CampsiteUserController {
    @Autowired
    private CampsiteService campsiteService;
    @Autowired
    private Utils utils;
    
    @RequestMapping("unity")
    public String unity(){
        return "camp/unity";
    }
    @RequestMapping("")
    public String mainPage1() {
        utils.setSession("allCategory", campsiteService.getAllCategory());
        return "camp/userMain";
    }
    @RequestMapping("main")
    public String mainPage2() {
        return "camp/userMain";
    }
    @RequestMapping("campsite")
    public String showCampsite() {
        return "camp/showCampsite";
    }
}
