package com.bulmeong.basecamp.camp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bulmeong.basecamp.camp.dto.CampsiteBankDto;
import com.bulmeong.basecamp.camp.dto.CampsiteCategoryDto;
import com.bulmeong.basecamp.camp.dto.CampsiteDto;
import com.bulmeong.basecamp.camp.dto.CampsiteImageDto;
import com.bulmeong.basecamp.camp.dto.CampsiteSelectCategoryDto;

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
    public void insertCampsiteCategory(CampsiteSelectCategoryDto campsiteCategoryDto);
    public int newCampsiteID();
    public List<CampsiteCategoryDto> getCampsiteCategory();
    public CampsiteCategoryDto getCategoryBySelectCategoryId(int id);
    public List<CampsiteSelectCategoryDto> getSelectCategoriesByCampsiteId(@Param("id") int campsite_id);
}
