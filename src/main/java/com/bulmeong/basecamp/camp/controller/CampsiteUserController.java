package com.bulmeong.basecamp.camp.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bulmeong.basecamp.camp.dto.CampsiteOrderDto;
import com.bulmeong.basecamp.camp.dto.CampsiteReviewDto;
import com.bulmeong.basecamp.camp.service.CampsiteService;
import com.bulmeong.basecamp.club.dto.ClubDto;
import com.bulmeong.basecamp.club.service.ClubService;
import com.bulmeong.basecamp.common.util.Utils;


@Controller
@RequestMapping("camp")
public class CampsiteUserController {
    @Autowired
    private CampsiteService service;
    @Autowired
    private Utils utils;
    @Autowired
    private ClubService clubService;

    @RequestMapping("/main")
    public String mainPage() {
        setOrder();
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
        utils.setModel("isAlreadyOrdered", service.isAlreadyOrdered(campsite_id));
        utils.setModel("isAlreadyReviewed", service.isAlreadyReviewed(campsite_id));
        System.out.println("Ordered : " + service.isAlreadyOrdered(campsite_id));
        System.out.println("Reviewed : " + service.isAlreadyOrdered(campsite_id));
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
        @RequestParam("car") int car,
        @RequestParam("selectDay_start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
        @RequestParam("selectDay_end_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
    ) {
        utils.setSession("redirectAfterLogin",utils.currentUrl());
        utils.setModel("adult", adult);
        utils.setModel("kid", kid);
        utils.setModel("car", car);
        utils.setSession("curArea", service.areaInfo(area_id));
        utils.setModel("startDate", service.alreadyOrderedPointListByAreaId(area_id, startDate, endDate));
        return "camp/user/reservationPage_2";
    }

    @RequestMapping("/myReservation")
    public String myReservation() {
        return "camp/user/myReservation";
    }

    @RequestMapping("/registerReviewProcess")
    public String registerReviewProcess(CampsiteReviewDto campsiteReviewDto) {
        service.registerReview(campsiteReviewDto);
        return "redirect:/camp/campsite?campsite_id=" + campsiteReviewDto.getCampsite_id();
    }

    @RequestMapping("/finalReservationProcess")
    public String finalReservationProcess(CampsiteOrderDto campsiteOrderDto,
        @RequestParam(defaultValue = "",name="car_number") String[] carNumbers,
        @RequestParam(defaultValue = "0", name= "useMileage") int useMileage,
        @RequestParam("number") int number) {
        // 예약 번호
        if(service.isAlreadyOrderedByPoint(campsiteOrderDto)){
            return "/rorre";
        }
        campsiteOrderDto.setReservation_code(utils.randomCode(12));
        service.registerOrder(campsiteOrderDto, useMileage, carNumbers);
        utils.setSession("order", service.getOrderByCode(campsiteOrderDto.getReservation_code()));
        utils.setSession("pointNumber", number);
        return "redirect:/camp/reservationComplete";
    }

    @RequestMapping("/reservationComplete")
    public String reservationComplete() {
        List<ClubDto> clubDtoList = clubService.findClubDtoList();
        List<ClubDto> limitedClubDtoList = clubDtoList.stream()
        .limit(10)
        .collect(Collectors.toList());
        utils.setModel("clubDtoList", limitedClubDtoList);
        return "camp/user/reservationComplete";
    }

    @GetMapping("/pay/completed")
    public String payCompleted(@RequestParam("pg_token") String pgToken) {
        CampsiteOrderDto campsiteOrderDto = utils.getSession("order");
        String[] carNumbers = utils.getSession("carNumbers");
        int useMileage = utils.getSession("useMileage");
        int number = utils.getSession("pointNumber");

        if (campsiteOrderDto != null) {
            campsiteOrderDto.setReservation_code(utils.randomCode(12));
            service.registerOrder(campsiteOrderDto, useMileage, carNumbers);
            utils.setSession("order", service.getOrderByCode(campsiteOrderDto.getReservation_code()));
            utils.setSession("pointNumber", number);
        }
        
        return "redirect:/camp/reservationComplete";
    }

    @RequestMapping("/unity")
    public String unity() {
        return "camp/unity";
    }

    private void setOrder() {
        Date now = new Date();
        List<CampsiteOrderDto> list = service.getOrderList();
        for(CampsiteOrderDto order : list) {
            if(order.getProgress().equals("예약 대기 중"))
            {
                if(order.getStart_date().getTime() <= now.getTime()){
                    service.updateOrder(order.getId());
                }
            }
        }
    }
}
