package com.bulmeong.basecamp.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.camp.service.CampsiteService;
import com.bulmeong.basecamp.campingcar.dto.RestResponseDto;
import com.bulmeong.basecamp.common.util.Utils;



@RestController
@RequestMapping("api/campsiteCenter/")
public class RestCampsiteSellerController {
    @Autowired
    private CampsiteService service;
    @Autowired
    private Utils utils;

    @RequestMapping("initSession")
    public RestResponseDto initSession() {
        RestResponseDto result = new RestResponseDto();
        result.add("campsiteInfo", utils.getSession("campsite"));
        result.add("category", service.categories());

        return result;
    }

}
