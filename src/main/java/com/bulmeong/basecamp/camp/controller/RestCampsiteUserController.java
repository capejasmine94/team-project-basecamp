package com.bulmeong.basecamp.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.camp.service.CampsiteService;
import com.bulmeong.basecamp.campingcar.dto.RestResponseDto;
import com.bulmeong.basecamp.common.util.Utils;

@RestController

@RequestMapping("api/camp/")
public class RestCampsiteUserController {
    @Autowired
    private CampsiteService service;
    @Autowired
    private Utils utils;

    @RequestMapping("selectArea")
    public RestResponseDto selectArea(@RequestParam("area_id") String area_id) {
        RestResponseDto result = new RestResponseDto();
        result.add("curAreaUser", service.areaInfo(Integer.parseInt(area_id)));
        return result;
    }

    @RequestMapping("initSession")
    public RestResponseDto initSession() {
        RestResponseDto result = new RestResponseDto();
        result.add("campsiteList", service.campsiteList());
        return result;
    }

    @RequestMapping("initUser")
    public RestResponseDto initUser() {
        RestResponseDto result = new RestResponseDto();
        result.add("sessionUser", utils.getSession("sessionUserInfo"));
        return result;
    }

    @RequestMapping("initReservationInfo")
    public RestResponseDto initReservationInfo(@RequestParam("area_id") String area_id) {
        RestResponseDto result = new RestResponseDto();
        //세일 정보 불러오기
        //result.add("saleInfo",service.getSaleInfo(Integer.parseInt(area_id)));
        //utils.setModel("saleInfo", service.getSaleInfo(Integer.parseInt(area_id)));
        result.add("sessionUserInfo", utils.getSession("sessionUserInfo"));
        return result;
    }

    @RequestMapping("selectPoint")
    public RestResponseDto selectPoint(@RequestParam("point_id") String point_id, @RequestParam("number") String number) {
        RestResponseDto result = new RestResponseDto();
        result.add("pointList", service.pointByPointId(Integer.parseInt(point_id)));
        result.add("number", Integer.parseInt(number));
        return result;
    }
}
