package com.bulmeong.basecamp.seller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.camp.service.CampsiteService;
import com.bulmeong.basecamp.campingcar.service.AdminService;
import com.bulmeong.basecamp.common.dto.RestResponseDto;
import com.bulmeong.basecamp.common.util.Utils;
import com.bulmeong.basecamp.store.service.StoreService;

@RestController
@RequestMapping("/api/seller")
public class SellerRestController {
    @Autowired
    private Utils utils;
    @Autowired
    private StoreService storeService;

    @Autowired
    private CampsiteService campsiteService;

    @Autowired
    private AdminService adminService;

    @RequestMapping("getSellerDtoByAccountInfo")
    public RestResponseDto getStoreDtoByAccountInfo(
            @RequestParam("account_id") String account_id, 
            @RequestParam("account_pw") String account_pw,
            @RequestParam("seller_type") String seller_type
            ){
        RestResponseDto restResponseDto = new RestResponseDto();

        if(seller_type.equals("Store")){
            restResponseDto.add("sellerDto", storeService.getStoreDtoByAccountInfo(account_id, account_pw));
        }else if(seller_type.equals("Campsite")){
            restResponseDto.add("sellerDto", campsiteService.getCampsiteDtoByAccountInfo(account_id, account_pw));
        }else if(seller_type.equals("Caravan")){
            restResponseDto.add("sellerDto", adminService.getSellerByIdAndPw(account_id, account_pw));
        }

        return restResponseDto;
    }

    @RequestMapping("logOut")
    public RestResponseDto logOut() {
        RestResponseDto restResponseDto = new RestResponseDto();
        utils.logOut();
        return restResponseDto;
    }

}
