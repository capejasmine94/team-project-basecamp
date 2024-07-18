package com.bulmeong.basecamp.campingcar.controller;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.campingcar.dto.BasicFacilitiesDto;
import com.bulmeong.basecamp.campingcar.dto.CampingcarDto;
import com.bulmeong.basecamp.campingcar.dto.CarTypeDto;
import com.bulmeong.basecamp.campingcar.dto.DriverAgeCondDto;
import com.bulmeong.basecamp.campingcar.dto.DriverExperienceCondDto;
import com.bulmeong.basecamp.campingcar.dto.DriverLicenseDto;
import com.bulmeong.basecamp.campingcar.dto.LocationDto;
import com.bulmeong.basecamp.campingcar.dto.RentalCompanyDto;
import com.bulmeong.basecamp.campingcar.service.AdminService;
import com.bulmeong.basecamp.common.util.ImageUtil;
import com.bulmeong.basecamp.common.util.Utils;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired Utils utils;
    @Autowired AdminService adminService;

// 판매자 회원가입 
    @RequestMapping("nfRegisterPage")
    public String nfRegisterPage(Model model) {
        // 회원가입_회사지역 cate List
        List<LocationDto> locationData = adminService.getLocationAll();
        model.addAttribute("locationData", locationData);
        
        return "admin/nfRegisterPage";
    }

// 판매자 회원가입 등록
    @RequestMapping("sellerRegisterProcess")
    public String sellerRegisterProcess(RentalCompanyDto rentalCompanyDto, @RequestParam("profile_image") MultipartFile comp_profile_image) {
        
        rentalCompanyDto.setComp_profile_image(ImageUtil.saveImageAndReturnLocation(comp_profile_image));
        
        adminService.registerSeller(rentalCompanyDto);
        return "redirect:/seller/login";
    }
// 판매자 로그아웃 
    @RequestMapping("logoutProcess")
    public String logoutProcess(HttpSession session) {
        session.invalidate();
        return "redirect:/seller/login";
    }
// 판매자페이지 main
    @RequestMapping("main")
    public String main(HttpSession session, Model model){

        RentalCompanyDto rentalCompanyDto = (RentalCompanyDto) session.getAttribute("sessionCaravanInfo");
        model.addAttribute("rentalCompanyDto", rentalCompanyDto);
            return "admin/main";
    
    }
    // admin_main에 sub_category_쓰는 방식
    @RequestMapping("carRegister")
    public String carRegister(Model model, HttpSession session) {

    // 차량등록_캠핑카 유형 Category List
        List<CarTypeDto> carType = adminService.getCarTypeAll();
        model.addAttribute("carType", carType);
        
    // 차량등록_운전자 나이 Category List
        List<DriverAgeCondDto> driverAge = adminService.getDriverAgeAll(); 
        model.addAttribute("driverAge", driverAge);

    // 차량등록_운전 면허증 Category List
        List<DriverLicenseDto> driverLicense = adminService.getDriverLicenseAll();
        model.addAttribute("driverLicense", driverLicense);

    // 차량등록_운전자 경력 Category List
        List<DriverExperienceCondDto> driverExpericnece = adminService.getDriverExperienceAll();
        model.addAttribute("driverExpericnece", driverExpericnece);
        
    // 캠핑카 기본 보유 시설 Category List
        List<BasicFacilitiesDto> basicFacilities = adminService.getBasicFacilitiesAll();
        model.addAttribute("basicFacilities", basicFacilities);
        
        return "admin/carRegister";
    }

    // 차량등록 insert 
    @RequestMapping("carRegisterProgress")
    public String carRegisterProgress(CampingcarDto campingcarDto,@RequestParam("main_image")MultipartFile main_image
                                     ,@RequestParam(value = "basicFacilites_id") List<Integer> basicFacilites_id) {
            campingcarDto.setMain_img(ImageUtil.saveImageAndReturnLocation(main_image));
            adminService.registerCamping(campingcarDto,basicFacilites_id);
        return "redirect:/admin/main";
    }

    @RequestMapping("peakSeason")
    public String peakSeason(){

        return "admin/peakSeason";
    }


















    
// 내가 만든 main용 
    @RequestMapping("maintest")
    public String maintest() {
        return "admin/maintest";
    }
}
