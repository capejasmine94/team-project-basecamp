package com.bulmeong.basecamp.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.common.dto.RestResponseDto;
import com.bulmeong.basecamp.store.dto.StoreDto;
import com.bulmeong.basecamp.store.dto.StoreRestResponseDto;
import com.bulmeong.basecamp.store.service.StoreService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/store")
public class RestStoreController {

    @Autowired
    private StoreService storeService;

    @RequestMapping("getSessionStoreId")
    public RestResponseDto getSessionStoreId(HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        StoreDto sessionStore = (StoreDto)session.getAttribute("sessionStoreInfo");
        if(sessionStore != null){
            restResponseDto.add("id", sessionStore.getId());
        }else{
            restResponseDto.add("id", null);
        }

        return restResponseDto;
    }

    @RequestMapping("isExistStoreAccountId")
    public StoreRestResponseDto isExistAccountId(@RequestParam("account_id") String account_id){
        StoreRestResponseDto restResponseDto = new StoreRestResponseDto();

        restResponseDto.add("isExistAccountID", storeService.isExistAccountId(account_id));

        return restResponseDto;
    }

    @RequestMapping("isExistStoreName")
    public StoreRestResponseDto isExistStoreName(@RequestParam("name") String name){
        StoreRestResponseDto restResponseDto = new StoreRestResponseDto();

        restResponseDto.add("isExistName", storeService.isExistStoreName(name));

        return restResponseDto;
    }
    
    @RequestMapping("getProductSubcategoryDto")
    public RestResponseDto getProductSubcategoryDto(@RequestParam("category_id") int category_id){
        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.add("subcategoryDtoList", storeService.getProductSubcategoryByCategoryId(category_id));

        return restResponseDto;
    }

    // @RequestMapping("getSellerDtoByAccountInfo")
    // public RestResponseDto getStoreDtoByAccountInfo(
    //         @RequestParam("account_id") String account_id, 
    //         @RequestParam("account_pw") String account_pw,
    //         @RequestParam("seller_type") String seller_type
    //         ){
    //     RestResponseDto restResponseDto = new RestResponseDto();

    //     if(seller_type.equals("Store")){
    //         restResponseDto.add("storeDto", storeService.getStoreDtoByAccountInfo(account_id, account_pw));
    //     }else if(seller_type.equals("Campsite")){
    //         //여기 수정(캠핑장)
    //         restResponseDto.add("storeDto", storeService.getStoreDtoByAccountInfo(account_id, account_pw));
    //     }else if(seller_type.equals("Caravan")){
    //         //여기 수정(캠핑카)
    //         restResponseDto.add("storeDto", storeService.getStoreDtoByAccountInfo(account_id, account_pw));
    //     }

    //     return restResponseDto;
    // }
}
