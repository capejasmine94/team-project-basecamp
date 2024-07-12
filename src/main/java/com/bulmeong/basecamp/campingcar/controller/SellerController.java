package com.bulmeong.basecamp.campingcar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("rentcarSeller")
public class SellerController {

    @RequestMapping("productRegister")
    public String productRegister() {              

        return "rentcarSeller/productRegister";
    }
// 판매자페이지 main
    @RequestMapping("main")
    public String main(){
        
        return "rentcarSeller/main";
    }

// 내가 만든 main용 
    @RequestMapping("maintest")
    public String maintest() {
        return "rentcarSeller/maintest";
    }
// 파트너 유형 test용
    @RequestMapping("sellerTypeLoginpageTest")
    public String test() {
        return "rentcarSeller/sellerTypeLoginpageTest";
    }
}
