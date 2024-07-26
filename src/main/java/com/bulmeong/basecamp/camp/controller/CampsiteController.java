package com.bulmeong.basecamp.camp.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
public class CampsiteController {
    @Autowired
    private CampsiteService campsiteService;
    @Autowired
    private Utils utils;

    @RequestMapping("unity")
    public String unity(){
        return "camp/unity";
    }

    @RequestMapping("")
    public String mainPageByEmpty(){
        utils.setSession("campsiteCategory", campsiteService.getCampsiteCategory());
        CampsiteDto dto = utils.getSession("campsite");
        utils.setSession("selectCategory", campsiteService.getCampsiteCategoriesByCampsiteId(dto.getId()));
        return "redirect:/camp/main";
    }

    @RequestMapping("/")
    public String mainPageBySlash(){
        utils.setSession("campsiteCategory", campsiteService.getCampsiteCategory());
        CampsiteDto dto = utils.getSession("campsite");
        utils.setSession("selectCategory", campsiteService.getCampsiteCategoriesByCampsiteId(dto.getId()));
        return "redirect:/camp/main";
    }

    @RequestMapping("/main")
    public String mainPage(){
        utils.setSession("campsiteCategory", campsiteService.getCampsiteCategory());
        CampsiteDto dto = utils.getSession("campsite");
        utils.setSession("selectCategory", campsiteService.getCampsiteCategoriesByCampsiteId(dto.getId()));
        return "camp/main";
    }

    @RequestMapping("/register")
    public String registerUser(Model model){
        String id = String.format("%02d", campsiteService.newCampsiteID());
        model.addAttribute("campsiteDto", new CampsiteDto());
        model.addAttribute("bankDto", new CampsiteBankDto());
        model.addAttribute("newCampsiteID", id);
        return "camp/registerUser";
    }

    @RequestMapping("/registerCamp")
    public String registerCampPage(){
        utils.setSession("campsiteCategory", campsiteService.getCampsiteCategory());
        return "camp/registerCamp";
    }

    @RequestMapping("/manageCamp")
    public String manageCampPage(){

        return  "camp/manageCamp";
    }
    
    
    @RequestMapping("/manageArea")
    public String manageAreaPage(Model model){
        CampsiteDto campsiteDto = utils.getSession("campsite");
        model.addAttribute("areaCategory", campsiteService.getAreaCategory());
        model.addAttribute("areaInfoList", campsiteService.getAreaList(campsiteDto.getId()));
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
    public String registerUserProcess(
        @ModelAttribute CampsiteDto campsiteDto, 
        @ModelAttribute CampsiteBankDto bankDto, 
        @RequestParam("profileImage")MultipartFile profileImage
    ){
        campsiteService.insertCampsite(campsiteDto, bankDto, profileImage);
        return "store/registerComplete";
    }

    @RequestMapping("/registerCampProcess")
    public String registerCampProcess(
            CampsiteDto campsiteDto, 
            @RequestParam("mainImage") MultipartFile[] mainImage, 
            @RequestParam("mapImage") MultipartFile mapImage, 
            @RequestParam("campsite_category") String[] categories, 
            @RequestParam("opentime_start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date opentime,
            @RequestParam("manner_start") String mannerStartStr,
            @RequestParam("manner_end") String mannerEndStr,
            @RequestParam("check_in") String checkInStr,
            @RequestParam("check_out") String checkOutStr) {
        
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        try {
            Date mannerStart = timeFormat.parse(mannerStartStr);
            Date mannerEnd = timeFormat.parse(mannerEndStr);
            Date checkIn = timeFormat.parse(checkInStr);
            Date checkOut = timeFormat.parse(checkOutStr);
            
            campsiteDto.setManner_start(mannerStart);
            campsiteDto.setManner_end(mannerEnd);
            campsiteDto.setCheck_in(checkIn);
            campsiteDto.setCheck_out(checkOut);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid time format";
        }
        
        campsiteDto.setOpentime(opentime);
        campsiteService.updateCampsite(campsiteDto, categories, mainImage, mapImage);
        utils.setSession("campsite", campsiteService.getCampsiteDtoById(campsiteDto));
        return "camp/main";
    }

    @RequestMapping("/updateCampProcess")
    public String updateCampProcess(
            CampsiteDto campsiteDto, 
            @RequestParam("mainImage") MultipartFile[] mainImage, 
            @RequestParam("mapImage") MultipartFile mapImage, 
            @RequestParam("campsite_category") String[] categories, 
            @RequestParam("opentime_start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date opentime,
            @RequestParam("manner_start") String mannerStartStr,
            @RequestParam("manner_end") String mannerEndStr,
            @RequestParam("check_in") String checkInStr,
            @RequestParam("check_out") String checkOutStr) {
        
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        try {
            Date mannerStart = timeFormat.parse(mannerStartStr);
            Date mannerEnd = timeFormat.parse(mannerEndStr);
            Date checkIn = timeFormat.parse(checkInStr);
            Date checkOut = timeFormat.parse(checkOutStr);
            
            campsiteDto.setManner_start(mannerStart);
            campsiteDto.setManner_end(mannerEnd);
            campsiteDto.setCheck_in(checkIn);
            campsiteDto.setCheck_out(checkOut);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid time format";
        }
        
        campsiteDto.setOpentime(opentime);
        campsiteService.updateCampsite(campsiteDto, categories, mainImage, mapImage);
        utils.setSession("campsite", campsiteService.getCampsiteDtoById(campsiteDto));
        return "camp/main";
    }

     @RequestMapping("updateAreaProcess")
    public String updateArea(
        CampsiteAreaDto areaDto,
        @RequestParam("area_category") String[] categories, 
        @RequestParam("mainImage") MultipartFile[] mainImage, 
        @RequestParam("mapImage") MultipartFile mapImage
    ) {
        campsiteService.updateArea(areaDto, categories,mainImage,mapImage);
        return "redirect:./manageArea";
    }

    @RequestMapping("resv")
    public String userMain(){
        return "camp/userMain";
    }
}
