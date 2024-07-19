package com.bulmeong.basecamp.camp.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.bulmeong.basecamp.camp.dto.CampsiteDto;

@Mapper
public interface CampsiteSqlMapper {
    public CampsiteDto getCampsiteDtoByAccountInfo(CampsiteDto campsiteDto);
    public CampsiteDto getCampsiteDtoByAccount(CampsiteDto campsiteDto);
    public CampsiteDto getCampsiteDtoByName(CampsiteDto campsiteDto);
}
