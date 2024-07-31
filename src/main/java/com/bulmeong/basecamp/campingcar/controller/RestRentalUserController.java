package com.bulmeong.basecamp.campingcar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.campingcar.dto.CampingCarLikeDto;
import com.bulmeong.basecamp.campingcar.dto.CarTypeDto;
import com.bulmeong.basecamp.campingcar.dto.LocationDto;
import com.bulmeong.basecamp.campingcar.dto.RestRentUserResponseDto;
import com.bulmeong.basecamp.campingcar.service.CampingcarService;
import com.bulmeong.basecamp.campingcar.service.PartnerCampingCarService;
import com.bulmeong.basecamp.common.util.Utils;
import com.bulmeong.basecamp.user.dto.UserDto;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("api/rent-user")
public class RestRentalUserController {

    @Autowired
    private PartnerCampingCarService partnerCampingCarService;
    @Autowired
    private CampingcarService campingCarService;
    @Autowired
    private Utils utils;


// user 확인 작업
    @RequestMapping("getSessionUserId")
    public RestRentUserResponseDto getSessionUserId(HttpSession session) {
        utils.loginUser();
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

    // 좋아요 관련 
    @RequestMapping("like")
    public RestRentUserResponseDto like(CampingCarLikeDto campingCarLikeDto, HttpSession session) {
        utils.loginUser();
        RestRentUserResponseDto restRentUserResponseDto = new RestRentUserResponseDto();
        restRentUserResponseDto.setResult("success");

        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        campingCarLikeDto.setRent_user_id(userDto.getId());
        
        campingCarService.like(campingCarLikeDto);
        
        return restRentUserResponseDto;
    }

     // 좋아요 1번 이상 눌렀을 경우 취소
    @RequestMapping("unLike")
    public RestRentUserResponseDto unlike(CampingCarLikeDto campingCarLikeDto, HttpSession session) {
        utils.loginUser();
        RestRentUserResponseDto restRentUserResponseDto = new RestRentUserResponseDto();
        restRentUserResponseDto.setResult("success");
        
        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        campingCarLikeDto.setRent_user_id(userDto.getId());

        campingCarService.unLike(campingCarLikeDto);

        return restRentUserResponseDto;
    }

    // 등록된 차량 좋아요 누른 수 
    @RequestMapping("getTotalLikeCount")
    public RestRentUserResponseDto getTotalLikeCount(@RequestParam("product_id")int product_id){
        utils.loginUser();
        RestRentUserResponseDto restRentUserResponseDto = new RestRentUserResponseDto();
        restRentUserResponseDto.setResult("success");

        int count = campingCarService.getLikeTotalCountByProduct_id(product_id);

        restRentUserResponseDto.add("count", count);

        return restRentUserResponseDto;

    }
    // 회원이 등록된 차량에 좋아요를 눌른 수, count를 통해 회원이 좋아요 눌렀는지 확인
    @RequestMapping("isLiked")
    public RestRentUserResponseDto isLiked (CampingCarLikeDto campingCarLikeDto, HttpSession session) {
        utils.loginUser();

        RestRentUserResponseDto restRentUserResponseDto = new RestRentUserResponseDto();
        restRentUserResponseDto.setResult("success");

        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        campingCarLikeDto.setRent_user_id(userDto.getId());

        Boolean isLikedByUser = campingCarService.isLiked(campingCarLikeDto);
        restRentUserResponseDto.add("isLikedByUser", isLikedByUser);


        return restRentUserResponseDto;

    }
    @RequestMapping("getRegionsAll")
    public RestRentUserResponseDto getAllRegions() {
        utils.loginUser();

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
    
}
