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

    @RequestMapping("getStoreDtoByAccountInfo")
    public StoreRestResponseDto getStoreDtoByAccountInfo(@RequestParam("account_id") String account_id, @RequestParam("account_pw") String account_pw){
        StoreRestResponseDto restResponseDto = new StoreRestResponseDto();

        restResponseDto.add("storeDto", storeService.getStoreDtoByAccountInfo(account_id, account_pw));

        return restResponseDto;
    }
}
