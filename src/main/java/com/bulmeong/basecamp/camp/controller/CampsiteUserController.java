package com.bulmeong.basecamp.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bulmeong.basecamp.camp.dto.CampsiteAreaDto;
import com.bulmeong.basecamp.camp.service.CampsiteService;
import com.bulmeong.basecamp.common.util.Utils;
import org.springframework.web.bind.annotation.RequestMethod;



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
        utils.setSession("allCategory", campsiteService.getAllCategory());
        return "camp/userMain";
    }
    @RequestMapping("campsite")
    public String showCampsite(@RequestParam("id") int id) {
        utils.setSession("campsite", campsiteService.getCampsiteDtoById(id));
        return "camp/showCampsite";
    }
    @RequestMapping("reservation")
    public String reservation1(@RequestParam(name="id", defaultValue="0") String id) {
        utils.setSession("selectAreaInfo",  id == "0" ? null : campsiteService.getAreaInfo(Integer.parseInt(id)));
        return "camp/reservationPage_1";
    }
    @RequestMapping("payment")
    public String selectPoint(@RequestParam(name="id", defaultValue="0") String id) {
        return "camp/reservationPage_2";
    }
    @RequestMapping("paymentProcess")
    public String paymentProcess() {
        return "camp/payComplete";
    }
}
