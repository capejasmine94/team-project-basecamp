package com.bulmeong.basecamp.campingcar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("admin")
public class AdminController {

// 판매자페이지 main
    @RequestMapping("main")
    public String main(){
        
        return "admin/main";
    }
// admin_main에 sub_category_쓰는 방식
    @RequestMapping(value = "/carRegister", method = RequestMethod.POST)
    public String reservationStatus() {
        return "admin/carRegister";
    }




















    
// // 내가 만든 main용 
//     @RequestMapping("maintest")
//     public String maintest() {
//         return "admin/maintest";
//     }
}
