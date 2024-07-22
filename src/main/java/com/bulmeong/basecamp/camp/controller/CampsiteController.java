package com.bulmeong.basecamp.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.camp.dto.CampsiteBankDto;
import com.bulmeong.basecamp.camp.dto.CampsiteDto;
import com.bulmeong.basecamp.camp.service.CampsiteService;
import com.bulmeong.basecamp.common.util.Utils;

@RequestMapping("camp")
@Controller
public class CampsiteController {
    @Autowired
    private CampsiteService campsiteService;

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
    public String registerUser(Model model){
        String id = String.format("%02d", campsiteService.newCampsiteID());
        model.addAttribute("campsiteDto", new CampsiteDto());
        model.addAttribute("bankDto", new CampsiteBankDto());
        model.addAttribute("newCampsiteID", id);
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





    @RequestMapping("/registerUserProcess")
    public String registerUserProcess(@ModelAttribute CampsiteDto campsiteDto, @ModelAttribute CampsiteBankDto bankDto, @RequestParam("profileImage")MultipartFile profileImage){
        campsiteService.insertCampsite(campsiteDto, bankDto, profileImage);
        return "store/registerComplete";
    }

    @RequestMapping("/registerCampProcess")
    public String registerCampProcess(CampsiteDto campsiteDto, @RequestParam("mainImage")MultipartFile[] mainImage, @RequestParam("mapImage")MultipartFile mapImage, @RequestParam("campsite_category") String[] categories){
        campsiteService.updateCampsite(campsiteDto, categories, mainImage, mapImage);
        return "camp/main";
    }

    
}
