package com.bulmeong.basecamp.campingcar.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.campingcar.dto.BasicFacilitiesDto;
import com.bulmeong.basecamp.campingcar.dto.DriverAgeCondDto;
import com.bulmeong.basecamp.campingcar.dto.DriverExperienceCondDto;
import com.bulmeong.basecamp.campingcar.dto.DriverLicenseDto;
import com.bulmeong.basecamp.campingcar.dto.ProductDetailImgDto;
import com.bulmeong.basecamp.campingcar.dto.RentUserDto;
import com.bulmeong.basecamp.campingcar.dto.ReservationDto;
import com.bulmeong.basecamp.campingcar.service.CampingcarService;
import com.bulmeong.basecamp.campingcar.service.PartnerCampingCarService;
import com.bulmeong.basecamp.common.util.Utils;
import com.bulmeong.basecamp.user.dto.UserDto;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("campingcar")
public class CampingcarController {

    @Autowired
    private Utils utils;

    @Autowired
    private CampingcarService campingcarService;

    @Autowired
    private PartnerCampingCarService partnerCampingCarService;

    @RequestMapping("main")
    public String main(){
        utils.loginUser();

        return "campingcar/main";

    }

    @RequestMapping("campingCarDetailPage")
    public String campingCarDetailPage(@RequestParam("id") int id, Model model) {
        utils.loginUser();
        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);

        return "campingcar/campingCarDetailPage";
    }

    @RequestMapping("dRules")
    public String dRules(@RequestParam("id") int id, Model model) {
        utils.loginUser();
        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);
        return "campingcar/dRules";
    }

    @RequestMapping("dCarInfo")
    public String dCarInfo(@RequestParam("id") int id, Model model) {
        utils.loginUser();
        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);
        
        List<ProductDetailImgDto> detailImgDto = campingcarService.getProductDetailImgByProductId(id);
        model.addAttribute("detailImgDto", detailImgDto);
        return "campingcar/dCarInfo";
    }

    @RequestMapping("dCarOption")
    public String dCarOption(@RequestParam("id") int id, Model model) {
        utils.loginUser();
        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);

        List<BasicFacilitiesDto> facilities = campingcarService.getBasicFacilitiesByProductId(id);
        model.addAttribute("facilities", facilities);

        return "campingcar/dCarOption";
    }

    @RequestMapping("dRentalCondition")
    public String dRentalCondition(@RequestParam("id") int id, Model model) {
        utils.loginUser();
        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);

        return "campingcar/dRentalCondition";
    }

    @RequestMapping("dReviews") 
    public String dReviews(@RequestParam("id") int id, Model model) {
        utils.loginUser();
        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);

        return "campingcar/dReviews";
    }


    @RequestMapping("dCancelPolicy")
    public String dCancelPolicy(@RequestParam("id") int id, Model model) {
        utils.loginUser();
        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);
        return "campingcar/dCancelPolicy";
    }

    @RequestMapping("reservationInfo")
    public String reservationInfo(@RequestParam("id") int id, Model model){
        utils.loginUser();

        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);
        
        List<BasicFacilitiesDto> facilities = campingcarService.getBasicFacilitiesByProductId(id);
        model.addAttribute("facilities", facilities);

        // 차량등록_운전자 나이 Category List
        List<DriverAgeCondDto> driverAge = partnerCampingCarService.getDriverAgeAll(); 
        model.addAttribute("driverAge", driverAge);

    // 차량등록_운전 면허증 Category List
        List<DriverLicenseDto> driverLicense = partnerCampingCarService.getDriverLicenseAll();
        model.addAttribute("driverLicense", driverLicense);

    // 차량등록_운전자 경력 Category List
        List<DriverExperienceCondDto> driverExpericnece = partnerCampingCarService.getDriverExperienceAll();
        model.addAttribute("driverExpericnece", driverExpericnece);
        return "campingcar/reservationInfo";




    }
    @RequestMapping("rentUserInfoProcess")
    public String rentUserInfoProcess(HttpSession session, RentUserDto rentUser,
                                     @RequestParam("driveImage")MultipartFile driveImage, ReservationDto reservationDto) {
        System.out.println("fdfdfdfd"+ reservationDto.getStart_date() + reservationDto.getEnd_date());
        utils.loginUser();                               

        UserDto sessionUserInfo = (UserDto)session.getAttribute("sessionUserInfo");
        int userPk = sessionUserInfo.getId();
        rentUser.setUser_id(userPk);

        String rootPath = "C:/basecampeImage_rentuser/";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String todayPath = sdf.format(new Date());

        File todayFolderForCreate = new File(rootPath+ todayPath);
        if(!todayFolderForCreate.exists()) {
            todayFolderForCreate.mkdirs();
        }

        String originalFilename = driveImage.getOriginalFilename();

        String uuid = UUID.randomUUID().toString();
        long currentTime = System.currentTimeMillis();

        String filename = uuid + "_" + currentTime;
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));

        filename += ext;

        try {
            driveImage.transferTo(new File(rootPath + todayPath + filename));
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        campingcarService.registeRentUser(rentUser,reservationDto);
        System.out.println("렌트고객 가입:" + rentUser);

        return "redirect:/campingcar/main";
    }
}
