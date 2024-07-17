package com.bulmeong.basecamp.campingcar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.campingcar.dto.LocationDto;
import com.bulmeong.basecamp.campingcar.dto.RentalCompanyDto;
import com.bulmeong.basecamp.campingcar.service.AdminService;
import com.bulmeong.basecamp.common.util.ImageUtil;
import com.bulmeong.basecamp.common.util.Utils;


@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired Utils utils;
    @Autowired AdminService adminService;

// 판매자 회원가입 
    @RequestMapping("nfRegisterPage")
    public String nfRegisterPage(Model model) {

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
// 판매자페이지 main
    @RequestMapping("main")
    public String main(){
        
        return "admin/main";
    }
// admin_main에 sub_category_쓰는 방식
    @RequestMapping(value = "/carRegister", method = RequestMethod.POST)
    public String reservationStatus() {
        return "admin/carRegister";
    }

// 차량등록 insert 
    @RequestMapping("carRegisterProgress")
    public String carRegisterProgress() {

        return "redirect:/admin/main";
    }




















    
// // 내가 만든 main용 
//     @RequestMapping("maintest")
//     public String maintest() {
//         return "admin/maintest";
//     }
}
