package com.bulmeong.basecamp.seller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bulmeong.basecamp.camp.dto.CampsiteDto;
import com.bulmeong.basecamp.camp.service.CampsiteService;
import com.bulmeong.basecamp.campingcar.dto.RentalCompanyDto;
import com.bulmeong.basecamp.campingcar.service.PartnerCampingCarService;
import com.bulmeong.basecamp.store.dto.StoreDto;
import com.bulmeong.basecamp.store.service.StoreService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private StoreService storeService;
    @Autowired
    private PartnerCampingCarService partnerCampingCarService;
    @Autowired
    private CampsiteService campsiteService;

    @RequestMapping("sellerJoinType")
    public String sellerJoinType(){
        return "seller/sellerJoinType";
    }

    @RequestMapping("sellerType")
    public String sellerType(@RequestParam("selectOption") String selectOption){
        if(selectOption.equals("camp")){
            return "redirect:/camp/registerUser";
        }else if(selectOption.equals("store")){
            return "redirect:/storeCenter/storeRegister";
        }else{
            //여기 수정(캠핑카)
            return "redirect:/partner/nfRegisterPage";
        }
    }

    @RequestMapping("login")
    public String login(){
        return "seller/login";
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
            CampsiteDto campsiteDto = campsiteService.getCampsiteDtoByAccountInfo(account_id, account_pw);
            session.setAttribute("campsite", campsiteDto);
    
            return "redirect:/campsiteCenter/main";
        }else{
            //여기 수정(캠핑카)
            RentalCompanyDto rentalCompanyDto = partnerCampingCarService.getSellerByIdAndPw(account_id, account_pw);
            session.setAttribute("sessionCaravanInfo", rentalCompanyDto);
            System.out.println("11111111111"+  rentalCompanyDto);
    
            return "redirect:/partner/main";
        }
    }
}
