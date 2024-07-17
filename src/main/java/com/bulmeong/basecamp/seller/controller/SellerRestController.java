package com.bulmeong.basecamp.seller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.campingcar.service.AdminService;
import com.bulmeong.basecamp.common.dto.RestResponseDto;
import com.bulmeong.basecamp.store.service.StoreService;

@RestController
@RequestMapping("/api/seller")
public class SellerRestController {

    @Autowired
    private StoreService storeService;

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
            //여기 수정(캠핑장)
            restResponseDto.add("sellerDto", storeService.getStoreDtoByAccountInfo(account_id, account_pw));
        }else if(seller_type.equals("Caravan")){
            //여기 수정(캠핑카)
            restResponseDto.add("sellerDto", adminService.getSellerByIdAndPw(account_id, account_pw));
        }

        return restResponseDto;
    }

}
