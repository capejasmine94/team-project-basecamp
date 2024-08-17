package com.bulmeong.basecamp.campingcar.controller;

import java.util.List;
import java.util.Map;

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
import com.bulmeong.basecamp.campingcar.dto.RentalReview;
import com.bulmeong.basecamp.campingcar.dto.ReservationDto;
import com.bulmeong.basecamp.campingcar.service.CampingcarService;
import com.bulmeong.basecamp.campingcar.service.PartnerCampingCarService;
import com.bulmeong.basecamp.common.util.ImageUtil;


import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("partner")
public class PartnerCampingCarController {

    @Autowired 
    PartnerCampingCarService partnerCampingCarService;
    
    @Autowired
    CampingcarService campingcarService;

// 판매자 회원가입 
    @RequestMapping("nfRegisterPage")
    public String nfRegisterPage(Model model) {
        // 회원가입_회사지역 cate List
        List<LocationDto> locationData = partnerCampingCarService.getLocationAll();
        model.addAttribute("locationData", locationData);
        
        return "partner/nfRegisterPage";
    }

// 판매자 회원가입 등록
    @RequestMapping("sellerRegisterProcess")
    public String sellerRegisterProcess(RentalCompanyDto rentalCompanyDto, 
                                        @RequestParam("profile_image") MultipartFile comp_profile_image
                                        ) {
        
        rentalCompanyDto.setComp_profile_image(ImageUtil.saveImageAndReturnLocation(comp_profile_image));
        
        partnerCampingCarService.registerSeller(rentalCompanyDto);

        return "redirect:/seller/login";
    }
    // 판매자 로그인 
    @RequestMapping("loginProcess")
    public String loginProcess() {

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
        
        return "partner/main";
    
    }
    // admin_main에 sub_category_쓰는 방식
    @RequestMapping("carRegister")
    public String carRegister(Model model, HttpSession session) {

    // 차량등록_캠핑카 유형 Category List
        List<CarTypeDto> carType = partnerCampingCarService.getCarTypeAll();
        model.addAttribute("carType", carType);
        
    // 차량등록_운전자 나이 Category List
        List<DriverAgeCondDto> driverAge = partnerCampingCarService.getDriverAgeAll(); 
        model.addAttribute("driverAge", driverAge);

    // 차량등록_운전 면허증 Category List
        List<DriverLicenseDto> driverLicense = partnerCampingCarService.getDriverLicenseAll();
        model.addAttribute("driverLicense", driverLicense);

    // 차량등록_운전자 경력 Category List
        List<DriverExperienceCondDto> driverExpericnece = partnerCampingCarService.getDriverExperienceAll();
        model.addAttribute("driverExpericnece", driverExpericnece);
        
    // 캠핑카 기본 보유 시설 Category List
        List<BasicFacilitiesDto> basicFacilities = partnerCampingCarService.getBasicFacilitiesAll();
        model.addAttribute("basicFacilities", basicFacilities);
        
        return "partner/carRegister";
    }

    // 차량등록 insert 
    @RequestMapping("carRegisterProgress")
    public String carRegisterProgress(CampingcarDto campingcarDto,@RequestParam("main_image")MultipartFile main_image
                                     ,@RequestParam("detailedImg") MultipartFile[] detailedImg
                                     ,@RequestParam(value = "basicFacilites_id") List<Integer> basicFacilites_id) {
        campingcarDto.setMain_img(ImageUtil.saveImageAndReturnLocation(main_image));

        partnerCampingCarService.registerCamping(campingcarDto,basicFacilites_id,detailedImg);

        return "redirect:/partner/main";
    }
    
    @RequestMapping("carManagement") 
    public String carManagement(){

        return "partner/carManagement";
    }
    
    @RequestMapping("peakSeason")
    public String peakSeason(){

        return "partner/peakSeason";
    }

    @RequestMapping("bookReservation")
    public String bookReservation(HttpSession session, Model model) {
        RentalCompanyDto rentalCompanyDto = (RentalCompanyDto) session.getAttribute("sessionCaravanInfo");

        List<Map<String,Object>> bookReservationList = partnerCampingCarService.getBookReservationAll(rentalCompanyDto.getId());
        model.addAttribute("bookReservationList", bookReservationList);
        return "partner/bookReservation";
    }

    @RequestMapping("reservation_approved")
    public String reservation_approved(ReservationDto reservationDto) {
    
        partnerCampingCarService.updateReservationProgress(reservationDto);
    
        return "redirect:/partner/bookReservation";
    }

    @RequestMapping("reviewManage")
    public String reviewManga(HttpSession session, Model model) {
        
        RentalCompanyDto rentalCompanyDto = (RentalCompanyDto) session.getAttribute("sessionCaravanInfo");
        int rentalPk = rentalCompanyDto.getId();

        List<Map<String,Object>> reviewCompany = partnerCampingCarService.reviewManagebyRentCompanyId(rentalPk);
        model.addAttribute("reviewCompany", reviewCompany);
        
        return "partner/reviewManage";
    }

    @RequestMapping("reviewRelyContentProcess")
    public String reviewRelyContentProcess(RentalReview params) {
        
        partnerCampingCarService.updateReviewReply(params);

        return "redirect:/partner/reviewManage";
    }

















    
// 내가 만든 main용 
    @RequestMapping("maintest")
    public String maintest() {
        return "partner/maintest";
    }
    @RequestMapping("maintest2")
    public String maintest2() {
        return "partner/maintest2";
    }
}


