package com.bulmeong.basecamp.seller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bulmeong.basecamp.camp.dto.CampsiteBankDto;
import com.bulmeong.basecamp.camp.dto.CampsiteDto;
import com.bulmeong.basecamp.store.dto.StoreDto;
import com.bulmeong.basecamp.store.service.StoreService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private StoreService storeService;

    @RequestMapping("registerCampsite")
    public String registerCampsite() {
        return "/camp/registerPage";
    }

    @RequestMapping("registerCampsiteProcess")
    public String registerCampsiteProcess(@RequestParam("sellerDto") CampsiteDto params1, @RequestParam("bankDto") CampsiteBankDto params2){
        return "/seller/registerComplete";
    }

    @RequestMapping("login")
    public String login(){
        return "/seller/login";
    }

    @RequestMapping("loginProcess")
    public String loginProcess(
        @RequestParam("account_id") String account_id, 
        @RequestParam("account_pw") String account_pw, 
        @RequestParam("seller_type") String seller_type, 
        HttpSession session){
        
        if(seller_type.equals("Store")){
            StoreDto storeDto = storeService.getStoreDtoByAccountInfo(account_id, account_pw);
            session.setAttribute("sessionStoreInfo", storeDto);
    
            return "redirect:/storeCenter/dashboard";
        }else if(seller_type.equals("Campsite")){
            //여기 수정(캠핑장)
            StoreDto storeDto = storeService.getStoreDtoByAccountInfo(account_id, account_pw);
            session.setAttribute("sessionStoreInfo", storeDto);
    
            return "redirect:/storeCenter/dashboard";
        }else{
            //여기 수정(캠핑카)
            StoreDto storeDto = storeService.getStoreDtoByAccountInfo(account_id, account_pw);
            session.setAttribute("sessionStoreInfo", storeDto);
    
            return "redirect:/storeCenter/dashboard";
        }
    }
}
