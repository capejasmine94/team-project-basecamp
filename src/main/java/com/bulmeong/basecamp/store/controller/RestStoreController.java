package com.bulmeong.basecamp.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.store.dto.StoreRestResponseDto;
import com.bulmeong.basecamp.store.service.StoreService;

@RestController
@RequestMapping("/api/store")
public class RestStoreController {

    @Autowired
    private StoreService storeService;

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
}
