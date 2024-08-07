package com.bulmeong.basecamp.camp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.camp.dto.CampsiteAreaPointDto;
import com.bulmeong.basecamp.camp.dto.CampsiteDto;
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

    private CampsiteDto sessionCampsiteDto() {
        Map<String,Object> info = utils.getSession("campsite");
        return (CampsiteDto)info.get("dto");
    }

    @RequestMapping("initSession")
    public RestResponseDto initSession() {
        RestResponseDto result = new RestResponseDto();
        result.add("campsiteInfo", utils.getSession("campsite"));
        result.add("category", service.categories());

        return result;
    }

    @RequestMapping("/registerPointProcess")
    public RestResponseDto registerPointProcess(
        @RequestParam("point_name") String name, 
        @RequestParam("point_count") String point_count,
        @RequestParam("area_id") String area_param) {
        RestResponseDto result = new RestResponseDto();
        int count = Integer.parseInt(point_count);
        CampsiteAreaPointDto pointDto = new CampsiteAreaPointDto();
        int area_id = Integer.parseInt(area_param);
        pointDto.setArea_id(area_id);
        pointDto.setName(name);
        service.registerPoint(pointDto, count, area_id);
        // 세션 데이터 갱신
        CampsiteDto campsiteDto = sessionCampsiteDto();
        utils.setSession("campsite", service.campsiteInfo(campsiteDto.getId()));
        result.add("campsite", service.campsiteInfo(campsiteDto.getId()));
        return result;      
    }
    @RequestMapping("/deletePointProcess")
    public RestResponseDto registerPointProcess(@RequestParam("point_id") String point_id) {
        RestResponseDto result = new RestResponseDto();
        service.deletePoint(Integer.parseInt(point_id));
        // 세션 데이터 갱신
        CampsiteDto campsiteDto = sessionCampsiteDto();
        utils.setSession("campsite", service.campsiteInfo(campsiteDto.getId()));
        result.add("campsite", service.campsiteInfo(campsiteDto.getId()));
        return result;      
    }
    
    @RequestMapping("selectOrder")
    public RestResponseDto selectOrder(@RequestParam("order_id") String order_id) {
        RestResponseDto result = new RestResponseDto();
        result.add("curOrder", service.getOrderById(Integer.parseInt(order_id)));
        return result;
    }
}
