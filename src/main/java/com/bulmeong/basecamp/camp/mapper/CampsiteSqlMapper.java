package com.bulmeong.basecamp.camp.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.bulmeong.basecamp.camp.dto.CampsiteBankDto;
import com.bulmeong.basecamp.camp.dto.CampsiteDto;
import com.bulmeong.basecamp.camp.dto.CampsiteImageDto;

@Mapper
public interface CampsiteSqlMapper {
    public CampsiteDto getCampsiteDtoByAccountInfo(CampsiteDto campsiteDto);
    public CampsiteDto getCampsiteDtoByAccount(CampsiteDto campsiteDto);
    public CampsiteDto getCampsiteDtoByName(CampsiteDto campsiteDto);
    public void insertCampsite(CampsiteDto campsiteDto);
    public void updateCampsite(CampsiteDto campsiteDto);
    public void authCampSite(CampsiteDto campsiteDto);
    public void insertCampMainImage(CampsiteImageDto campsiteImageDto);
    public void insertCampsiteBank(CampsiteBankDto campsiteBankDto);
    public int newCampsiteID();
}
