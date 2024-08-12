package com.bulmeong.basecamp.camp.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.camp.dto.CampsiteAreaDto;
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

    private CampsiteDto sessionCampsiteDto() {
        Map<String,Object> info = utils.getSession("campsite");
        return (CampsiteDto)info.get("dto");
    }

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

    @RequestMapping("/area")
    public String manageArea() {
        return "camp/seller/area";
    }

    @RequestMapping("/reservation")
    public String manageReservation() {
        return "camp/seller/reservation";
    }

    @RequestMapping("/review")
    public String manageReview() {
        return "camp/seller/review";
    }

    @RequestMapping("/asset")
    public String manageAsset() {
        return "camp/seller/asset";
    }
    @RequestMapping("/statistics")
    public String manageStatistics() {
        return "camp/seller/statistics";
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
        @RequestParam(name="mapImage") MultipartFile mapImage, 
        @RequestParam(name="mainImage") MultipartFile[] mainImages, 
        @RequestParam("opentime_start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date opentime,
        @RequestParam("campCategory") String[] categories) {
        // 판매자 데이터 업데이트
        campsiteDto.setOpentime(opentime);
        service.registerCamp(campsiteDto, mapImage, mainImages, categories);
        // 세션 데이터 갱신
        utils.setSession("campsite", service.campsiteInfo(campsiteDto.getId()));
        return "camp/seller/main";      
    }

    @RequestMapping("/deleteAreaProcess")
    public String deleteAreaProcess(@RequestParam("id")int area_id) {
        service.deleteArea(area_id);
        CampsiteDto campsiteDto = sessionCampsiteDto();
        utils.setSession("campsite", service.campsiteInfo(campsiteDto.getId()));
        return "redirect:./area";   
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

    @RequestMapping("/registerAreaProcess")
    public String registerAreaProcess(CampsiteAreaDto areaDto,  @RequestParam("register_name") String name) {
        areaDto.setName(name);
        service.registerArea(areaDto);
        // 세션 데이터 갱신
        CampsiteDto campsiteDto = sessionCampsiteDto();
        utils.setSession("campsite", service.campsiteInfo(campsiteDto.getId()));
        return "redirect:./area";      
    }

    @RequestMapping("/updateAreaProcess")
    public String updateAreaProcess(
        CampsiteAreaDto areaDto, 
        @RequestParam("update_name") String name, 
        @RequestParam("mapImage") MultipartFile mapImage, 
        @RequestParam("mainImage") MultipartFile[] mainImages, 
        @RequestParam("areaCategory") String[] categories,
        @RequestParam(defaultValue = "", required = false, name="update_point_name") String[] update_point_name,
        @RequestParam(defaultValue = "", required = false, name="update_point_count") int[] update_point_count
        ) {
        // 판매자 데이터 업데이트
        areaDto.setName(name);
        service.updateArea(areaDto, mapImage, mainImages, categories);
        service.updatePoint(areaDto.getId(), update_point_name,update_point_count);
        return "redirect:./area";      
    }

    //============================================================================
}
