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

    @RequestMapping("main")
    public String main() {
        return "rentcarSeller/main";
    }
}
