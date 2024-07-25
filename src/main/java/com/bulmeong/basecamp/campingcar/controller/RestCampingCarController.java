package com.bulmeong.basecamp.campingcar.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.campingcar.dto.RestResponseDto;
import com.bulmeong.basecamp.campingcar.service.AdminService;

@RestController
@RequestMapping("api/campingcar")
public class RestCampingCarController {

    @Autowired 
    private AdminService adminService;

    @RequestMapping("campingcarList")
    public RestResponseDto campingcarList() {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");
        
        // 캠핑카 등록 list 출력
        List<Map<String,Object>> campingcarList = adminService.getCampingCarAll();
        restResponseDto.add("campingcarList", campingcarList);

        return restResponseDto;   
    }
}
