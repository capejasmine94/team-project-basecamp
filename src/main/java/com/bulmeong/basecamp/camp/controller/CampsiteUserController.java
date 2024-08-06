package com.bulmeong.basecamp.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bulmeong.basecamp.camp.dto.CampsiteOrderDto;
import com.bulmeong.basecamp.camp.service.CampsiteService;
import com.bulmeong.basecamp.common.util.Utils;

@Controller
@RequestMapping("camp")
public class CampsiteUserController {
    @Autowired
    private CampsiteService service;
    @Autowired
    private Utils utils;

    @RequestMapping("/main")
    public String mainPage() {
        utils.setSession("redirectAfterLogin",utils.currentUrl());
        utils.setSession("campsiteList", service.campsiteList());
        utils.setSession("category", service.categories());
        return "camp/user/main";
    }
    @RequestMapping("")
    public String redirectMain1() {
        return "redirect:/camp/main";
    }
    @RequestMapping("/")
    public String redirectMain2() {
        return "redirect:/camp/main";
    }

    @RequestMapping("/campsite")
    public String campsitePage(@RequestParam("campsite_id") int campsite_id) {
        utils.setSession("redirectAfterLogin",utils.currentUrl());
        utils.setSession("campsiteUser", service.campsiteInfoForUser(campsite_id));
        return "camp/user/showCampsite";
    }

    @RequestMapping("/reservation")
    public String reservationPage(@RequestParam(name="area_id",defaultValue = "0") int area_id) {
        utils.setSession("redirectAfterLogin",utils.currentUrl());
        utils.setModel("selectArea", area_id);
        return "camp/user/reservationPage_1";
    }

    @RequestMapping("/reservationCheck")
    public String reservationCheck(
        @RequestParam(name="area_id",defaultValue = "0") int area_id,
        @RequestParam("adult") int adult,
        @RequestParam("kid") int kid,
        @RequestParam("car") int car
    ) {
        utils.setSession("redirectAfterLogin",utils.currentUrl());
        utils.setModel("adult", adult);
        utils.setModel("kid", kid);
        utils.setModel("car", car);
        utils.setSession("curArea", service.areaInfo(area_id));
        return "camp/user/reservationPage_2";
    }

    @RequestMapping("/finalReservationProcess")
    public String finalReservationProcess(CampsiteOrderDto campsiteOrderDto,
        @RequestParam(defaultValue = "",name="car_number") String[] carNumbers,
        @RequestParam(defaultValue = "0", name= "useMileage") int useMileage,
        @RequestParam("number") int number) {
        // 예약 번호
        campsiteOrderDto.setReservation_code(utils.randomCode(12));
        service.registerOrder(campsiteOrderDto, useMileage, carNumbers);
        utils.setSession("order", service.getOrderByCode(campsiteOrderDto.getReservation_code()));
        utils.setSession("pointNumber", number);
        return "redirect:/camp/reservationComplete";
    }

    @RequestMapping("/reservationComplete")
    public String reservationComplete() {
        return "camp/user/reservationComplete";
    }
}
