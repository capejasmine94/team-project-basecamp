package com.bulmeong.basecamp.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulmeong.basecamp.store.dto.StoreDto;
import com.bulmeong.basecamp.store.mapper.StoreSqlMapper;

@Service
public class StoreService {

    @Autowired
    private StoreSqlMapper storeSqlMapper;

    public boolean isExistAccountId(String account_id){
        return storeSqlMapper.selectCountStoreByAccountId(account_id)>0;
    } 

    public boolean isExistStoreName(String name){
        return storeSqlMapper.selectCountStoreByName(name)>0;
    } 

    public StoreDto getStoreDtoByAccountInfo(String account_id, String account_pw){
        return storeSqlMapper.selectStoreByAccountInfo(account_id, account_pw);
    }
}