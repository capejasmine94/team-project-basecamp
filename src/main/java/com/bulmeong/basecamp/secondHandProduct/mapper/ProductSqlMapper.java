package com.bulmeong.basecamp.secondHandProduct.mapper;

import com.bulmeong.basecamp.secondHandProduct.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductSqlMapper {

    public UserDto userTest(UserDto userDto);

}
