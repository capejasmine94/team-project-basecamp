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
import com.bulmeong.basecamp.campingcar.dto.RentalExternalInspectionDto;
import com.bulmeong.basecamp.campingcar.dto.ReservationDto;
import com.bulmeong.basecamp.campingcar.service.CampingcarService;
import com.bulmeong.basecamp.campingcar.service.PartnerCampingCarService;
import com.bulmeong.basecamp.user.dto.UserDto;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("campingcar")
public class CampingcarController {

    @Autowired
    private CampingcarService campingcarService;

    @Autowired
    private PartnerCampingCarService partnerCampingCarService;

    @RequestMapping("main")
    public String main(){


        return "campingcar/main";

    }

    @RequestMapping("campingCarDetailPage")
    public String campingCarDetailPage(@RequestParam("id") int id, Model model) {

        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);

        return "campingcar/campingCarDetailPage";
    }

    @RequestMapping("dRules")
    public String dRules(@RequestParam("id") int id, Model model) {

        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);
        return "campingcar/dRules";
    }

    @RequestMapping("dCarInfo")
    public String dCarInfo(@RequestParam("id") int id, Model model) {

        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);
        
        List<ProductDetailImgDto> detailImgDto = campingcarService.getProductDetailImgByProductId(id);
        model.addAttribute("detailImgDto", detailImgDto);
        return "campingcar/dCarInfo";
    }

    @RequestMapping("dCarOption")
    public String dCarOption(@RequestParam("id") int id, Model model) {

        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);

        List<BasicFacilitiesDto> facilities = campingcarService.getBasicFacilitiesByProductId(id);
        model.addAttribute("facilities", facilities);

        return "campingcar/dCarOption";
    }

    @RequestMapping("dRentalCondition")
    public String dRentalCondition(@RequestParam("id") int id, Model model) {

        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);

        return "campingcar/dRentalCondition";
    }

    @RequestMapping("dReviews") 
    public String dReviews(@RequestParam("id") int id, Model model) {

        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);

        return "campingcar/dReviews";
    }


    @RequestMapping("dCancelPolicy")
    public String dCancelPolicy(@RequestParam("id") int id, Model model) {

        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);
        return "campingcar/dCancelPolicy";
    }

    @RequestMapping("reservationInfo")
    public String reservationInfo(@RequestParam("id") int id, HttpSession session, Model model){

        UserDto sessionUserInfo =(UserDto)session.getAttribute("sessionUserInfo");
        // 사용자가 세션에 있는지 확인 
        if(sessionUserInfo != null) {
            boolean isRentUser = campingcarService.isRentUser(sessionUserInfo.getId());
            model.addAttribute("isRentUser", isRentUser);
        } else {
            model.addAttribute("isRentUser", false);
        }

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
    @RequestMapping("reservationProcess")
    public String rentUserInfoProcess(HttpSession session, RentUserDto rentUser,
                                     @RequestParam("driveImage")MultipartFile driveImage, ReservationDto reservationDto,
                                     Model model) {

        UserDto sessionUserInfo = (UserDto)session.getAttribute("sessionUserInfo");
        int userPk = sessionUserInfo.getId();
        rentUser.setUser_id(userPk);

        String basecamp_rentUser = rentalShoot(driveImage);
        
        rentUser.setDriver_license_image(basecamp_rentUser);

        campingcarService.registeRentUser(rentUser,reservationDto);

        Map<String,Object> reservationConfirm = campingcarService.getReservationDetails(reservationDto.getRent_user_id(), reservationDto.getId());
        model.addAttribute("reservationConfirm", reservationConfirm);

        return "campingcar/reservationConfirmation";
    }

    @RequestMapping("existingRentUserReservationProcess")
    public String existingRentUserReservationProcess(HttpSession session, ReservationDto reservationDto, Model model) {

        UserDto sessionUserInfo = (UserDto)session.getAttribute("sessionUserInfo");
        int rentUserPk = campingcarService.getExistingByRentUserId(sessionUserInfo.getId());
        reservationDto.setRent_user_id(rentUserPk);

        campingcarService.existingRentUserReservation(reservationDto);

        Map<String,Object> reservationConfirm = campingcarService.getReservationDetails(reservationDto.getRent_user_id(), reservationDto.getId());
        model.addAttribute("reservationConfirm", reservationConfirm);

        return "campingcar/reservationConfirmation";
    }

    @RequestMapping("rentUseageHistory")
    public String rentUseageHistory(HttpSession session, Model model) { 
        UserDto sessionUserInfo = (UserDto)session.getAttribute("sessionUserInfo");
        int rentUserPk = campingcarService.getExistingByRentUserId(sessionUserInfo.getId());
        
        List<Map<String,Object>> userHistory = campingcarService.getUseageHistroyAllByRentUserId(rentUserPk);
        model.addAttribute("userHistory", userHistory);

        return "campingcar/rentUseageHistory";
    }







    @RequestMapping("carExteriorInteriorShoot")
    public String carExteriorInteriorShoot() {

        return "campingcar/carExteriorInteriorShoot";
    }

    @RequestMapping("rentShootProcess")
    public String rentShootProcess(@RequestParam("front_view") MultipartFile frontView,
                                    @RequestParam("passenger_front_view") MultipartFile passengerFrontView,
                                    @RequestParam("passenger_rear_view") MultipartFile passengerRearView,
                                    @RequestParam("rear_view") MultipartFile rearView,
                                    @RequestParam("driver_rear_view") MultipartFile driverRearView,
                                    @RequestParam("driver_front_view") MultipartFile driverFrontView,
                                    Model model) {

        String frontViewImg = rentalShoot(frontView);
        String passengerFrontViewImg = rentalShoot(passengerFrontView);
        String passengerRearViewImg = rentalShoot(passengerRearView);
        String rearViewImg = rentalShoot(rearView);
        String driverRearViewImg = rentalShoot(driverRearView);
        String driverFrontViewImg = rentalShoot(driverFrontView);

        RentalExternalInspectionDto rentalExternalInspectionDto = new RentalExternalInspectionDto();
        rentalExternalInspectionDto.setFront_view(frontViewImg);
        rentalExternalInspectionDto.setPassenger_front_view(passengerFrontViewImg);
        rentalExternalInspectionDto.setPassenger_rear_view(passengerRearViewImg);
        rentalExternalInspectionDto.setRear_view(rearViewImg);
        rentalExternalInspectionDto.setDriver_rear_view(driverRearViewImg);
        rentalExternalInspectionDto.setDriver_front_view(driverFrontViewImg);

        campingcarService.registerRentShoot(rentalExternalInspectionDto);

    model.addAttribute("message", "파일이 성공적으로 업로드되었습니다.");
    return "redirect:/campingcar/myRentalHistory";
    }

    
    public String rentalShoot(MultipartFile newImage) {
        String rootPath = "C:/basecampeImage_rentuser/";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String todayPath = sdf.format(new Date());

        File todayFolderForCreate = new File(rootPath + todayPath);
        if(!todayFolderForCreate.exists()) {
            todayFolderForCreate.mkdirs();
        }

        String originalFilename = newImage.getOriginalFilename();

        String uuid = UUID.randomUUID().toString();
        long currentTime = System.currentTimeMillis();

        String filename = uuid + "_" + currentTime;
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));

        filename += ext;

        try {
            newImage.transferTo(new File(rootPath + todayPath + filename));
        }catch(Exception e) {
            e.printStackTrace();
        }
        String newName = todayPath + filename;
        return newName;
    }


}
