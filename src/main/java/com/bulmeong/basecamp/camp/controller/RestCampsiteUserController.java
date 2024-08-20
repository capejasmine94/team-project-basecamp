package com.bulmeong.basecamp.camp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.camp.dto.CampsiteAreaPointDto;
import com.bulmeong.basecamp.camp.dto.CampsiteOrderDto;
import com.bulmeong.basecamp.camp.service.CampsiteService;
import com.bulmeong.basecamp.campingcar.dto.RestResponseDto;
import com.bulmeong.basecamp.common.util.Utils;
import com.bulmeong.basecamp.seller.dto.ReadyResponse;
import com.bulmeong.basecamp.seller.service.KakaoPayService;
import com.bulmeong.basecamp.user.dto.UserDto;

@RestController
@RequestMapping("api/camp/")
public class RestCampsiteUserController {
    @Autowired
    private CampsiteService service;
    @Autowired
    private KakaoPayService kakaoPayService;
    @Autowired
    private Utils utils;

    private UserDto sessionUserDto() {
        UserDto info = utils.getSession("sessionUserInfo");
        return info;
    }
    @RequestMapping("selectArea")
    public RestResponseDto selectArea(@RequestParam("area_id") String area_id) {
        RestResponseDto result = new RestResponseDto();
        result.add("curAreaUser", service.areaInfo(Integer.parseInt(area_id)));
        result.add("campsiteUser",utils.getSession("campsiteUser"));
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

    @RequestMapping("saveReservationData")
    public RestResponseDto saveReservationData() {
        RestResponseDto result = new RestResponseDto();
        utils.setSession("redirectAfterLogin", "/camp/reservation");
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

    @RequestMapping("isAlreadyOrderPointList")
    public RestResponseDto isAlreadyOrderPointList(@RequestParam("area_id") int area_id, 
    @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start, 
    @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
        RestResponseDto result = new RestResponseDto();
        List<Integer> list = new ArrayList<>();
        List<CampsiteAreaPointDto> points = service.alreadyOrderedPointListByAreaId(area_id, start,end);
        for(CampsiteAreaPointDto p : points){
            list.add(p.getId());
        }
        result.add("isAlreadyOrdered", list);
        return result;
    }

    @RequestMapping("selectOrder")
    public RestResponseDto selectOrder(@RequestParam("order_id") int order_id) {
        RestResponseDto result = new RestResponseDto();
        result.add("selectOrder", service.getOrderById(order_id));
        return result;
    }

    @RequestMapping("selectOrderByUser")
    public RestResponseDto selectOrderByUser() {
        RestResponseDto result = new RestResponseDto();
        UserDto user = sessionUserDto();
        System.out.print("USER : ");
        System.out.println(user);
        result.add("orderByUser", user == null ? null : service.getOrderByUserId(user.getId()));
        System.out.print("orderByUser : ");
        System.out.println(result.getData().get("orderByUser"));
        return result;
    }

    @RequestMapping("selectOrderByCode")
    public RestResponseDto selectOrderByCode(@RequestParam("reservCode") String reservCode) {
        RestResponseDto result = new RestResponseDto();
        result.add("orderByCode", service.getOrderByCode(reservCode));
        return result;
    }

    @RequestMapping("searchCampsite")
    public RestResponseDto searchCampsite(@RequestParam("searchWord") String searchWord, @RequestParam("category") String[] category) {
        RestResponseDto result = new RestResponseDto();
        result.add("campsiteList", service.searchCampsite(searchWord,category));
        return result;
    }

    @PostMapping("payReady")
    public RestResponseDto payReady(CampsiteOrderDto campsiteOrderDto,
        @RequestParam(defaultValue = "",name="car_number") String[] car_number,
        @RequestParam(defaultValue = "0", name= "useMileage") int useMileage,
        @RequestParam("number") int number
    ) {
        System.out.print("Received Order: ");
        System.out.println(campsiteOrderDto);

        System.out.print("Received car_number: ");
        System.out.println(car_number);

        System.out.print("Received useMileage: ");
        System.out.println(useMileage);

        System.out.print("Received number: ");
        System.out.println(number);
        
        ReadyResponse readyResponse = kakaoPayService.readyPayment(campsiteOrderDto);
        utils.setSession("orderDto", campsiteOrderDto);
        utils.setSession("carNumbers", car_number);
        utils.setSession("useMileage", useMileage);
        utils.setSession("number", number);
        utils.setSession("tid", readyResponse.getTid());

        RestResponseDto result = new RestResponseDto();
        result.add("readyResponse", readyResponse);
        return result;
    }
}
