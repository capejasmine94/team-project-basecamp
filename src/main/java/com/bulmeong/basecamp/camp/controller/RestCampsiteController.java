package com.bulmeong.basecamp.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.camp.service.CampsiteService;
import com.bulmeong.basecamp.common.dto.RestResponseDto;

@RestController
@RequestMapping("/api/camp/")
public class RestCampsiteController {
    @Autowired
    private CampsiteService campsiteService;

    @RequestMapping("isExistAccount")
    public RestResponseDto isExistAccount(@RequestParam("account") String account) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");
        boolean isExistAccount = campsiteService.getCampsiteDtoByAccount(account) != null;
        restResponseDto.add("isExistAccount", isExistAccount);
        return restResponseDto;
    }

    
    @RequestMapping("isExistCampName")
    public RestResponseDto isExistCampName(@RequestParam("name") String name) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");
        boolean isExistCampName = campsiteService.getCampsiteDtoByName(name) != null;
        restResponseDto.add("isExistCampName", isExistCampName);
        return restResponseDto;
    }

    
}
