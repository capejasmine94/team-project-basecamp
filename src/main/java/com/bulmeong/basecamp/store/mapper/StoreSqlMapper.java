package com.bulmeong.basecamp.store.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bulmeong.basecamp.store.dto.StoreBankAccountDto;
import com.bulmeong.basecamp.store.dto.StoreDeliveryInfoDto;
import com.bulmeong.basecamp.store.dto.StoreDto;

@Mapper
public interface StoreSqlMapper {

    public int selectCountStoreByAccountId(String account_id);

    public int selectCountStoreByName(String name);

    public StoreDto selectStoreByAccountInfo(@Param("account_id") String account_id, @Param("account_pw") String account_pw);

    //스토어 가입
    public void insertStoreDto(StoreDto storeDto);
    public void insertStoreDeliveryInfoDto(StoreDeliveryInfoDto storeDeliveryInfoDto);
    public void insertStoreBankAccountDto(StoreBankAccountDto storeBankAccountDto);

    public StoreBankAccountDto selectStoreBankAccountDtoByStoreId(int store_id);
}