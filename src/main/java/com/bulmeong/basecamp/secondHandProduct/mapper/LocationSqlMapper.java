package com.bulmeong.basecamp.secondHandProduct.mapper;

import com.bulmeong.basecamp.secondHandProduct.dto.LocationDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LocationSqlMapper {

    public int isLocationRegistered(int user_id);

    public void insertMyLocation(LocationDto locationDto);
    public void updateMyLocation(LocationDto locationDto);

    public LocationDto selectMyLocation(int user_id);

}
