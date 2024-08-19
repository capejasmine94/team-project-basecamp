package com.bulmeong.basecamp.campingcar.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.campingcar.dto.RentalCompanyDto;
import com.bulmeong.basecamp.campingcar.dto.RentalPeakPriceDto;
import com.bulmeong.basecamp.campingcar.dto.RestResponseDto;
import com.bulmeong.basecamp.campingcar.mapper.PartnerCampingCarSqlMapper;
import com.bulmeong.basecamp.campingcar.service.PartnerCampingCarService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("api/campingcar")
public class RestCampingCarController {

    @Autowired 
    private PartnerCampingCarService partnerCampingCarService;

    @Autowired PartnerCampingCarSqlMapper partnerCampingCarSqlMapper;

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
    @RequestMapping("campingCarListForCalendar") 
    public RestResponseDto campingCarListForCalendar(HttpSession session) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");
        RentalCompanyDto rentalCompanyDto =(RentalCompanyDto)session.getAttribute("sessionCaravanInfo");
        
        List<Map<String,Object>> carList = partnerCampingCarService.getcampingCarListForCalendar(rentalCompanyDto.getId());
        restResponseDto.add("carList", carList);

        return restResponseDto;
    }

    @RequestMapping("addRentalPeakDate")
    public RestResponseDto addRentalPeakDate(@RequestBody RentalPeakPriceDto rentalPeakPriceDto, HttpSession session) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        
        // 현재 세션에서 판매자 정보 가져오기
        RentalCompanyDto rentalCompanyDto = (RentalCompanyDto) session.getAttribute("sessionCaravanInfo");

        // 해당 판매자의 차량 목록 가져오기
        List<Map<String, Object>> carList = partnerCampingCarService.getcampingCarListForCalendar(rentalCompanyDto.getId());

        // 각 차량에 대해 성수기 기간 추가
        for (Map<String, Object> car : carList) {
            int productId = (Integer) car.get("product_id");

            // 성수기 날짜 업데이트
            partnerCampingCarService.updateRentalPeakDates(productId, rentalPeakPriceDto.getPeak_start_date(), rentalPeakPriceDto.getPeak_end_date());
        }

        System.out.println("Received restResponseDto: " + restResponseDto);

        return restResponseDto;
    }
}
