package com.bulmeong.basecamp.store.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bulmeong.basecamp.store.dto.StoreDto;

@Mapper
public interface StoreSqlMapper {

    public int selectCountStoreByAccountId(String account_id);

    public int selectCountStoreByName(String name);

    public StoreDto selectStoreByAccountInfo(@Param("account_id") String account_id, @Param("account_pw") String account_pw);
}
