package com.bulmeong.basecamp.campingcar.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bulmeong.basecamp.campingcar.service.CampingcarService;
import com.bulmeong.basecamp.common.util.Utils;

@Controller
@RequestMapping("campingcar")
public class CampingcarController {

    @Autowired
    private Utils utils;

    @Autowired
    private CampingcarService campingcarService;

    @RequestMapping("main")
    public String main(){
        utils.loginUser();

        return "campingcar/main";

    }

    @RequestMapping("campingCarDetailPage")
    public String campingCarDetailPage(@RequestParam("id") int id, Model model) {
        utils.loginUser();
        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);

        return "campingcar/campingCarDetailPage";
    }



}
