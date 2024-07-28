package com.bulmeong.basecamp.campingcar.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.campingcar.dto.RentalCompanyDto;
import com.bulmeong.basecamp.campingcar.dto.RestResponseDto;
import com.bulmeong.basecamp.campingcar.service.PartnerCampingCarService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("api/campingcar")
public class RestCampingCarController {

    @Autowired 
    private PartnerCampingCarService partnerCampingCarService;

    @RequestMapping("campingcarList")
    public RestResponseDto campingcarList() {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");
        
        // 사용자: 캠핑카 등록 list 출력
        List<Map<String,Object>> campingcarList = partnerCampingCarService.getCampingCarAll();
        restResponseDto.add("campingcarList", campingcarList);

        return restResponseDto;   
    }

    @RequestMapping("campingCarManagement")
    public RestResponseDto campingCarManagement(HttpSession session) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");
        RentalCompanyDto rentalCompanyDto =(RentalCompanyDto)session.getAttribute("sessionCaravanInfo");

        // 판매자 : 캠핑카 등록 현황 및 수정 List 출력
        List<Map<String,Object>> registeredCampersList = partnerCampingCarService.getCampingcarBySellerId(rentalCompanyDto.getId());
        restResponseDto.add("registeredCampersList", registeredCampersList);
        
        return restResponseDto;
    }
}
