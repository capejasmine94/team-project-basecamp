package com.bulmeong.basecamp.store.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StoreSqlMapper {

    public int selectCountStoreByAccountId(String account_id);

    public int selectCountStoreByName(String name);
}
