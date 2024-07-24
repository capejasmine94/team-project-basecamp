package com.bulmeong.basecamp.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.camp.dto.CampsiteDto;
import com.bulmeong.basecamp.camp.service.CampsiteService;
import com.bulmeong.basecamp.common.dto.RestResponseDto;
import com.bulmeong.basecamp.common.util.Utils;


@RestController
@RequestMapping("/api/camp/")
public class RestCampsiteController {
    @Autowired
    private CampsiteService campsiteService;
    @Autowired
    private Utils utils;

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

    @RequestMapping("checkNeedLogin")
    public RestResponseDto checkNeedLogin() {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");
        restResponseDto.add("needLogin", utils.getSession("campsite") == null);
        return restResponseDto;
    }
    
    @RequestMapping("getCampsite")
    public RestResponseDto getCampsite() {
        RestResponseDto result = new RestResponseDto();
        result.setResult("success");
        CampsiteDto campsiteDto = utils.getSession("campsite");
        result.add("cmapsite", campsiteDto);
        return result;
    }
    
}
