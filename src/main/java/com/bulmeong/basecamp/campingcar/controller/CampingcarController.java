package com.bulmeong.basecamp.campingcar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bulmeong.basecamp.campingcar.dto.CarTypeDto;
import com.bulmeong.basecamp.campingcar.dto.LocationDto;
import com.bulmeong.basecamp.campingcar.service.PartnerCampingCarService;
import com.bulmeong.basecamp.common.util.Utils;

@Controller
@RequestMapping("campingcar")
public class CampingcarController {

    @Autowired 
    private PartnerCampingCarService partnerCampingCarService;

    @Autowired
    private Utils utils;


    @RequestMapping("main")
    public String main(Model model){
        utils.loginUser();
        
        // 모달_지역 선택 
        List<LocationDto> regions = partnerCampingCarService.getLocationAll();
        model.addAttribute("regions", regions);
        // 모달_캠핑카유형 선텍 
        List<CarTypeDto> carType = partnerCampingCarService.getCarTypeAll();
        model.addAttribute("carType", carType);

        return "campingcar/main";

    }
    @RequestMapping("modal")
    public String modal() {
        return "campingcar/modal";
    }


}
