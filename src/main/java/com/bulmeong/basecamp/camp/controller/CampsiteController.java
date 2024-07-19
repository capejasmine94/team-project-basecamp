package com.bulmeong.basecamp.camp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bulmeong.basecamp.camp.dto.CampsiteBankDto;
import com.bulmeong.basecamp.camp.dto.CampsiteDto;

@RequestMapping("camp")
@Controller
public class CampsiteController {

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





    @RequestMapping("registerProcess")
    public String registerProcess(CampsiteDto campsiteDto, CampsiteBankDto bankDto){
        
        return "store/registerComplete";
    }
}
