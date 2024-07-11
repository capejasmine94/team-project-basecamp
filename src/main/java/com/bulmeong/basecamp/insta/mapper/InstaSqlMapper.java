package com.bulmeong.basecamp.insta.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.bulmeong.basecamp.insta.dto.InstaUserInfoDto;

@Mapper
public interface InstaSqlMapper {
    public void insertInstaUserInfo(InstaUserInfoDto InstaUserInfoDto);
}
