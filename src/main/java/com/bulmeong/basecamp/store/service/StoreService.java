package com.bulmeong.basecamp.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulmeong.basecamp.store.dto.StoreBankAccountDto;
import com.bulmeong.basecamp.store.dto.StoreDeliveryInfoDto;
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

    public void registerStore(StoreDto storeDto, StoreDeliveryInfoDto storeDeliveryInfoDto, StoreBankAccountDto storeBankAccountDto){
        storeSqlMapper.insertStoreDto(storeDto);

        int store_id = storeDto.getId();

        storeDeliveryInfoDto.setStore_id(store_id);
        storeBankAccountDto.setStore_id(store_id);

        storeSqlMapper.insertStoreDeliveryInfoDto(storeDeliveryInfoDto);
        storeSqlMapper.insertStoreBankAccountDto(storeBankAccountDto);
    }

    public StoreBankAccountDto getStoreBankAccountDtoByStoreId(int store_id){
        return storeSqlMapper.selectStoreBankAccountDtoByStoreId(store_id);
    }
}