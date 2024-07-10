package com.bulmeong.basecamp.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.bulmeong.basecamp.user.dto.UserDto;

@Mapper
public interface UserSqlMapper {
    public UserDto getUserByAccountAndPassword(UserDto userDto);
}
