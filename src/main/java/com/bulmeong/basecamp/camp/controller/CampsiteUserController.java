package com.bulmeong.basecamp.camp.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bulmeong.basecamp.camp.dto.CampsiteOrderDto;
import com.bulmeong.basecamp.camp.service.CampsiteService;
import com.bulmeong.basecamp.common.util.Utils;
import com.bulmeong.basecamp.seller.dto.ApproveResponse;
import com.bulmeong.basecamp.seller.dto.ReadyResponse;
import com.bulmeong.basecamp.seller.service.KakaoPayService;
import com.bulmeong.basecamp.store.dto.StoreOrderDto;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("camp")
public class CampsiteUserController {
    @Autowired
    private CampsiteService service;
    @Autowired
    private KakaoPayService kakaoPayService;
    @Autowired
    private Utils utils;

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
        @RequestParam("car") int car
    ) {
        utils.setSession("redirectAfterLogin",utils.currentUrl());
        utils.setModel("adult", adult);
        utils.setModel("kid", kid);
        utils.setModel("car", car);
        utils.setSession("curArea", service.areaInfo(area_id));
        return "camp/user/reservationPage_2";
    }

    @RequestMapping("/myReservation")
    public String myReservation() {
        return "camp/user/myReservation";
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

   @PostMapping("/pay/ready")
    public @ResponseBody ReadyResponse payReady(
        CampsiteOrderDto campsiteOrderDto,
        @RequestParam(defaultValue = "",name="car_number") String[] carNumbers,
        @RequestParam(defaultValue = "0", name= "useMileage") int useMileage,
        @RequestParam("number") int number
        ){
        ReadyResponse readyResponse = kakaoPayService.readyPayment(campsiteOrderDto);
        utils.setSession("orderDto", campsiteOrderDto);
        utils.setSession("carNumbers", readyResponse);
        utils.setSession("useMileage", useMileage);
        utils.setSession("number", number);
        utils.setSession("tid", readyResponse.getTid());

        return readyResponse;
    }

    @GetMapping("/pay/completed")
    public String payCompleted(@RequestParam("pg_token") String pgToken) {
        String tid = utils.getSession("tid");
        
        CampsiteOrderDto campsiteOrderDto = utils.getSession("orderDto");
        String[] carNumbers = utils.getSession("carNumbers");
        int useMileage = utils.getSession("useMileage");
        int number = utils.getSession("number");

        if (campsiteOrderDto != null) {
            // 카카오 결제 요청하기
            //ApproveResponse approveResponse = kakaoPayService.payApprove(tid, pgToken, campsiteOrderDto.getId(), campsiteOrderDto.getCustomer_name());

            campsiteOrderDto.setReservation_code(utils.randomCode(12));
            service.registerOrder(campsiteOrderDto, useMileage, carNumbers);
            utils.setSession("order", service.getOrderByCode(campsiteOrderDto.getReservation_code()));
            utils.setSession("pointNumber", number);
        }
        
        return "redirect:/camp/user/reservationComplete";
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
