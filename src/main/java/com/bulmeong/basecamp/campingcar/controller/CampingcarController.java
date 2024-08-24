package com.bulmeong.basecamp.campingcar.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import com.bulmeong.basecamp.campingcar.dto.RentalReview;
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
    public String campingCarDetailPage(@RequestParam("id") int id, Model model, HttpSession session) {
        UserDto sessionUserInfo = (UserDto) session.getAttribute("sessionUserInfo");
        model.addAttribute("sessionUserInfo", sessionUserInfo);
        System.out.println("유저"+ sessionUserInfo);

        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);
        System.out.println("디테일" + campingcarDetails);

        // 캠핑옵션 
        List<ProductDetailImgDto> detailImgDto = campingcarService.getProductDetailImgByProductId(id);
        model.addAttribute("detailImgDto", detailImgDto);

        System.out.println("캠핑옵션" + detailImgDto);

        // 차량옵션
        List<BasicFacilitiesDto> facilities = campingcarService.getBasicFacilitiesByProductId(id);
        model.addAttribute("facilities", facilities);
        System.out.println("차량옵션" + facilities);

        //리뷰 리스트
        List<Map<String,Object>> reviewData = campingcarService.getReviewAllbyCarId(id);
        model.addAttribute("reviewData", reviewData);
        System.out.println("reviewData"+reviewData);

        // 해당 차량의 리뷰 별점 평균 
        Double reviewAvg = campingcarService.getAvgByCarId(id);
        model.addAttribute("reviewAvg", reviewAvg);

        // 해당 차량의 리뷰 참여 인원 수
        int reivewCountBycar = campingcarService.getReviewByCountPersont(id);
        model.addAttribute("reivewCountBycar", reivewCountBycar);

        // 해당 차량의 각 별점 마다 인원수
        List<Map<String,Object>> ratings = campingcarService.ratingGroupBycar(id);
        model.addAttribute("ratings", ratings);
        System.out.println("별점인원원수" + ratings);

        return "campingcar/campingCarDetailPage";
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
        
        List<Map<String,Object>> rentuserHistoryData = campingcarService.getUseageHistroyAllByRentUserId(rentUserPk);
        model.addAttribute("rentuserHistoryData", rentuserHistoryData);

        return "campingcar/rentUseageHistory";
    }

    // 리뷰작성하기
    @RequestMapping("carReviewPage")
    public String carReviewPage(@RequestParam("id")int id, HttpSession session, Model model) {

        UserDto sessionUserInfo = (UserDto)session.getAttribute("sessionUserInfo");
        int rentUserPk = campingcarService.getExistingByRentUserId(sessionUserInfo.getId());

        Map<String,Object> reservationConfirm = campingcarService.getReservationDetails(rentUserPk, id);
        model.addAttribute("reservationConfirm", reservationConfirm);
;

        return "campingcar/carReviewPage";
    }
    // 리뷰작성Process
    @RequestMapping("carReviewProcess")
    public String carReviewProcess(RentalReview review) {

        campingcarService.registerReview(review);

        return "redirect:/campingcar/main";
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

    @RequestMapping("maintest")
    public String maintest() {
        return "/campingcar/maintest";
    }

    @RequestMapping("myLike")
    public String myLike(HttpSession session,Model model){
        UserDto sessionUserInfo = (UserDto)session.getAttribute("sessionUserInfo");
        int rentUserPk = campingcarService.getExistingByRentUserId(sessionUserInfo.getId());
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@"+ rentUserPk);
        List<Map<String,Object>> MyLikeList = campingcarService.getMyLikeList(rentUserPk);
        System.out.println(MyLikeList);
        model.addAttribute("MyLikeList", MyLikeList);

        return "campingcar/myLike";
    }

    @RequestMapping("searchResultsPage")
    public String searchResultsPage(@RequestParam(name = "location", required = false) String location,
    @RequestParam(name = "carTypes", required = false) List<String> carTypes,
    @RequestParam(name = "rentDate", required = false) String rentDate,
    @RequestParam(name = "returnDate", required = false) String returnDate,
    Model model) {
        Map<String,Object> map = new HashMap<>();
        map.put("location", location);
        map.put("carTypes", carTypes);
        if (rentDate != null && returnDate != null) {
            String formattedRentDate = rentDate + " 00:00:00";
            String formattedReturnDate = returnDate + " 23:59:59";
    
            map.put("rentDate", formattedRentDate);
            map.put("returnDate", formattedReturnDate);
        }


        List<Map<String,Object>> searchResultList = campingcarService.getSearchResultList(map);
        System.out.println("@@@@@@@@@@@@@@@@@@"+searchResultList);
        model.addAttribute("searchResultList", searchResultList);

        
        return "campingcar/searchResultsPage";
    }


}
