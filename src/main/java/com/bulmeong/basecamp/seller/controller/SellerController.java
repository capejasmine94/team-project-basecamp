package com.bulmeong.basecamp.seller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bulmeong.basecamp.camp.dto.CampsiteBankDto;
import com.bulmeong.basecamp.camp.dto.CampsiteDto;

@Controller
@RequestMapping("/seller")
public class SellerController {
    @RequestMapping("registerCampsite")
    public String registerCampsite() {
        return "/camp/registerPage";
    }

    @RequestMapping("registerCampsiteProcess")
    public String registerCampsiteProcess(@RequestParam("sellerDto") CampsiteDto params1, @RequestParam("bankDto") CampsiteBankDto params2){
        return "/seller/registerComplete";
    }
}
