package com.bulmeong.basecamp.campingcar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bulmeong.basecamp.common.util.Utils;

@Controller
@RequestMapping("campingcar")
public class CampingcarController {

    @Autowired
    private Utils utils;


    @RequestMapping("main")
    public String main(){
        utils.loginUser();

        return "campingcar/main";

    }
    @RequestMapping("modal")
    public String modal() {
        return "campingcar/modal";
    }


}
