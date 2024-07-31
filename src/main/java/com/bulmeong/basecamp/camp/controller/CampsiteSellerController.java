package com.bulmeong.basecamp.camp.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.camp.dto.CampsiteBankDto;
import com.bulmeong.basecamp.camp.dto.CampsiteDto;
import com.bulmeong.basecamp.camp.service.CampsiteService;
import com.bulmeong.basecamp.common.util.Utils;

@RequestMapping("campsiteCenter")
@Controller
public class CampsiteSellerController {
    @Autowired
    private CampsiteService service;
    @Autowired
    private Utils utils;


    //============================================================================
    // 리퀘스트 구역
    //============================================================================
    @RequestMapping("/main")
    public String mainPage() {
        return "camp/seller/main";
    }
    @RequestMapping("")
    public String redirectMain() {
        return "redirect:/campsiteCenter/main";
    }

    @RequestMapping("/campsite")
    public String manageCampsite() {
        return "camp/seller/campsite";
    }

    @RequestMapping("/registerUser")
    public String registerUser() {
        utils.setModel("newCampsiteId", service.newCampsiteId());
        return "camp/seller/registerUser";
    }

    @RequestMapping("/registerCamp")
    public String registerCamp() {
        utils.setModel("newCampsiteId", service.newCampsiteId());
        return "camp/seller/registerCamp";
    }
    //============================================================================



    //============================================================================
    // 프로세스 구역
    //============================================================================
    @RequestMapping("/registerUserProcess")
    public String registerUserProcess(CampsiteDto campsiteDto, CampsiteBankDto bankDto, @RequestParam("profileImage") MultipartFile profileImage) {
        service.registerSeller(campsiteDto, bankDto, profileImage);
        return "redirect:/seller/registerComplete";      
    }

    @RequestMapping("/registerCampProcess")
    public String registerCampProcess(
        CampsiteDto campsiteDto, 
        @RequestParam("mapImage") MultipartFile mapImage, 
        @RequestParam("mainImage") MultipartFile[] mainImages, 
        @RequestParam("opentime_start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date opentime,
        @RequestParam("campCategory") String[] categories) {
        // 판매자 데이터 업데이트
        campsiteDto.setOpentime(opentime);
        service.registerCamp(campsiteDto, mapImage, mainImages, categories);
        // 세션 데이터 갱신
        utils.setSession("campsite", service.campsiteInfo(campsiteDto.getId()));
        return "camp/seller/main";      
    }

    @RequestMapping("/updateCampProcess")
    public String updateCampProcess(
        CampsiteDto campsiteDto, 
        @RequestParam("mapImage") MultipartFile mapImage, 
        @RequestParam("mainImage") MultipartFile[] mainImages, 
        @RequestParam("opentime_start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date opentime,
        @RequestParam("campCategory") String[] categories) {
        // 판매자 데이터 업데이트
        campsiteDto.setOpentime(opentime);
        service.updateCamp(campsiteDto, mapImage, mainImages, categories);
        return "redirect:./campsite";      
    }
    
    //============================================================================
}
