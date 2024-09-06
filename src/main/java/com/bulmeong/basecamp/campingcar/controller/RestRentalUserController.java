package com.bulmeong.basecamp.campingcar.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.campingcar.dto.CampingCarLikeDto;
import com.bulmeong.basecamp.campingcar.dto.CarTypeDto;
import com.bulmeong.basecamp.campingcar.dto.LocationDto;
import com.bulmeong.basecamp.campingcar.dto.RentUserDto;
import com.bulmeong.basecamp.campingcar.dto.RestRentUserResponseDto;
import com.bulmeong.basecamp.campingcar.service.CampingcarService;
import com.bulmeong.basecamp.campingcar.service.PartnerCampingCarService;
import com.bulmeong.basecamp.user.dto.UserDto;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("api/rent-user")
public class RestRentalUserController {

    @Autowired
    private PartnerCampingCarService partnerCampingCarService;
    @Autowired
    private CampingcarService campingcarService;


// user 확인 작업
    @RequestMapping("getSessionUserId")
    public RestRentUserResponseDto getSessionUserId(HttpSession session) {
        RestRentUserResponseDto restRentUserResponseDto = new RestRentUserResponseDto();
        restRentUserResponseDto.setResult("success");

        UserDto sessionUserInfo = (UserDto)session.getAttribute("sessionUserInfo");

        if(sessionUserInfo != null) {
            restRentUserResponseDto.add("id", sessionUserInfo.getId());
        }else {
            restRentUserResponseDto.add("id", null);
        }

        return restRentUserResponseDto;
        
    }

    // rentUser검증
    @RequestMapping("isRentUser")
    public RestRentUserResponseDto isRentUser(HttpSession session, UserDto userDto, RentUserDto rentUserDto, Model model) {
    
        RestRentUserResponseDto restRentUserResponseDto = new RestRentUserResponseDto();
        restRentUserResponseDto.setResult("success");
    
        UserDto sessionUserInfo = (UserDto)session.getAttribute("sessionUserInfo");
        if(sessionUserInfo != null) {
            boolean isRentUser = campingcarService.isRentUser(sessionUserInfo.getId());
            restRentUserResponseDto.add("isRentUser", isRentUser);
        } else {
            model.addAttribute("isRentUser", false);
        }
    
        return restRentUserResponseDto;
    }

    // 좋아요 관련 
    @RequestMapping("like")
    public RestRentUserResponseDto like(CampingCarLikeDto campingCarLikeDto, HttpSession session) {
        RestRentUserResponseDto restRentUserResponseDto = new RestRentUserResponseDto();
        restRentUserResponseDto.setResult("success");

        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        int rentUserPk = campingcarService.getExistingByRentUserId(userDto.getId());
        campingCarLikeDto.setRent_user_id(rentUserPk);
        
        campingcarService.like(campingCarLikeDto);
        
        return restRentUserResponseDto;
    }

     // 좋아요 1번 이상 눌렀을 경우 취소
    @RequestMapping("unLike")
    public RestRentUserResponseDto unlike(CampingCarLikeDto campingCarLikeDto, HttpSession session) {
        RestRentUserResponseDto restRentUserResponseDto = new RestRentUserResponseDto();
        restRentUserResponseDto.setResult("success");
        
        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        int rentUserPk = campingcarService.getExistingByRentUserId(userDto.getId());
        campingCarLikeDto.setRent_user_id(rentUserPk);

        campingcarService.unLike(campingCarLikeDto);

        return restRentUserResponseDto;
    }

    // 등록된 차량 좋아요 누른 수 
    @RequestMapping("getTotalLikeCount")
    public RestRentUserResponseDto getTotalLikeCount(@RequestParam("product_id")int product_id){
        RestRentUserResponseDto restRentUserResponseDto = new RestRentUserResponseDto();
        restRentUserResponseDto.setResult("success");

        int count = campingcarService.getLikeTotalCountByProduct_id(product_id);

        restRentUserResponseDto.add("count", count);

        return restRentUserResponseDto;

    }
    // 회원이 등록된 차량에 좋아요를 눌른 수, count를 통해 회원이 좋아요 눌렀는지 확인
    @RequestMapping("isLiked")
    public RestRentUserResponseDto isLiked (CampingCarLikeDto campingCarLikeDto, HttpSession session) {
        RestRentUserResponseDto restRentUserResponseDto = new RestRentUserResponseDto();
        restRentUserResponseDto.setResult("success");

        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        int rentUserPk = campingcarService.getExistingByRentUserId(userDto.getId());
        campingCarLikeDto.setRent_user_id(rentUserPk);

        Boolean isLikedByUser = campingcarService.isLiked(campingCarLikeDto);
        restRentUserResponseDto.add("isLikedByUser", isLikedByUser);


        return restRentUserResponseDto;

    }

    @RequestMapping("MyLikeList")
    public RestRentUserResponseDto MyLikeList(HttpSession session) {
        RestRentUserResponseDto restRentUserResponseDto = new RestRentUserResponseDto();
        restRentUserResponseDto.setResult("success");

        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        int rentUserPk = campingcarService.getExistingByRentUserId(userDto.getId());

        List<Map<String,Object>> MyLikeList = campingcarService.getMyLikeList(rentUserPk);
        restRentUserResponseDto.add("MyLikeList", MyLikeList);

        return restRentUserResponseDto;
    }
    
    @RequestMapping("getRegionsAll")
    public RestRentUserResponseDto getAllRegions() {
        RestRentUserResponseDto restRentUserResponseDto = new RestRentUserResponseDto();
        restRentUserResponseDto.setResult("success");

        // 모달_지역 선택 
        List<LocationDto> regions = partnerCampingCarService.getLocationAll();
        restRentUserResponseDto.add("allRegionsList", regions);

        return restRentUserResponseDto;
    }

    @RequestMapping("getCarTypeAll")
    public RestRentUserResponseDto getAllCarType () {

        RestRentUserResponseDto restRentUserResponseDto = new RestRentUserResponseDto();
        restRentUserResponseDto.setResult("success");

        // 모달_캠핑카유형 선텍 
        List<CarTypeDto> carType = partnerCampingCarService.getCarTypeAll();
        restRentUserResponseDto.add("allCarType", carType);
        

        return restRentUserResponseDto;
    }
    
    @RequestMapping("reservationData") 
        public RestRentUserResponseDto reservationList(@RequestParam("product_id")int product_id) {

            RestRentUserResponseDto restRentUserResponseDto = new RestRentUserResponseDto();
            restRentUserResponseDto.setResult("success");

            List<LocalDate> reservationList = campingcarService.getReservedDates(product_id);
            // System.out.println("리스트"+reservationList);

            // JSON으로 반환할 수 있도록 LocalDate를 문자열로 변환
            List<String> reservedDateStrings = reservationList.stream()
                .map(LocalDate::toString)
                .collect(Collectors.toList());

            restRentUserResponseDto.add("reservedDateStrings", reservedDateStrings);
            // System.out.println("dto" + restRentUserResponseDto);

        return restRentUserResponseDto;

    }

}
