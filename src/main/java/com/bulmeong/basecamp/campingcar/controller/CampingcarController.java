package com.bulmeong.basecamp.campingcar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bulmeong.basecamp.campingcar.dto.CarTypeDto;
import com.bulmeong.basecamp.campingcar.dto.LocationDto;
import com.bulmeong.basecamp.campingcar.service.AdminService;

@Controller
@RequestMapping("campingcar")
public class CampingcarController {

    @Autowired 
    private AdminService adminService;


    @RequestMapping("main")
    public String main(Model model){

        // 모달_지역 선택 
        List<LocationDto> regions = adminService.getLocationAll();
        model.addAttribute("regions", regions);
        // 모달_캠핑카유형 선텍 
        List<CarTypeDto> carType = adminService.getCarTypeAll();
        model.addAttribute("carType", carType);

        // // 캠핑카 등록 list 출력
        // List<Map<String,Object>> campingcarList = adminService.getCampingCarAll();
        // model.addAttribute("campingcarList", campingcarList);
        

        return "campingcar/main";
    }


}
