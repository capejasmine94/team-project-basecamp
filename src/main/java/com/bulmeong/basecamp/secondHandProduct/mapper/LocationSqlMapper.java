package com.bulmeong.basecamp.secondHandProduct.mapper;

import com.bulmeong.basecamp.secondHandProduct.dto.LocationDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LocationSqlMapper {

    public void insertMyLocation(LocationDto locationDto);

    public int isAreaName(int user_id);

    public String selectMyLocation(int user_id);

    public void updateMyLocation(LocationDto locationDto);

}
