package com.bulmeong.basecamp.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bulmeong.basecamp.common.util.Utils;

@RequestMapping("camp")
@Controller
public class CampsiteController {

    //유틸 사용법 1 : 유틸을 선언한다
    @Autowired
    private Utils utils;

    @RequestMapping("unity")
    public String unity(){
        return "camp/unity";
    }

    @RequestMapping("")
    public String mainPageByEmpty(){
        return "redirect:/camp/main";
    }

    @RequestMapping("/")
    public String mainPageBySlash(){
        return "redirect:/camp/main";
    }

    @RequestMapping("/main")
    public String mainPage(){
        return "camp/main";
    }

    @RequestMapping("/registerUser")
    public String registerUser(){
        return "camp/registerUser";
    }

    @RequestMapping("/registerCamp")
    public String registerCampPage(){
        return "camp/registerCamp";
    }

    @RequestMapping("/manageCamp")
    public String manageCampPage(){
        return "camp/manageCamp";
    }
    
    
    @RequestMapping("/manageArea")
    public String manageAreaPage(){
        return "camp/manageArea";
    }

    @RequestMapping("/manageReservation")
    public String manageReservationPage(){
        return "camp/manageReservation";
    }

    @RequestMapping("/manageAsset")
    public String manageAssetPage(){
        return "camp/manageAsset";
    }

    @RequestMapping("/statisticsCamp")
    public String statisticsPage(){
        return "camp/statisticsCamp";
    }

    @RequestMapping("/manageCampReview")
    public String reviewPage(){
        return "camp/manageCampReview";
    }

    @RequestMapping("/manageCampAccount")
    public String manageAccountPage(){
        return "camp/manageCampAccount";
    }
}
