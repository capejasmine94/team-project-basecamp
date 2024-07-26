package com.bulmeong.basecamp.camp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bulmeong.basecamp.camp.dto.CampsiteAreaDto;
import com.bulmeong.basecamp.camp.dto.CampsiteAreaImageDto;
import com.bulmeong.basecamp.camp.dto.CampsiteAreaPointDto;
import com.bulmeong.basecamp.camp.dto.CampsiteAreaSelectCategoryDto;
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
    public CampsiteDto getCampsiteDtoById(CampsiteDto campsiteDto);
    public CampsiteAreaDto getAreaDtoById(CampsiteAreaDto campsiteAreaDto);
    public void insertCampsite(CampsiteDto campsiteDto);
    public void insertArea(CampsiteAreaDto campsiteAreaDto);
    public void insertPoint(int id);
    public void updateCampsite(CampsiteDto campsiteDto);
    public void updateArea(CampsiteAreaDto campsiteAreaDto);
    public void authCampSite(CampsiteDto campsiteDto);
    public void insertAreaImage(CampsiteAreaImageDto campsiteAreaImageDto);
    public void insertCampMainImage(CampsiteImageDto campsiteImageDto);
    public void insertCampsiteBank(CampsiteBankDto campsiteBankDto);
    public void insertAreaCategory(CampsiteAreaSelectCategoryDto campsiteAreaCategoryDto);
    public void insertCampsiteCategory(CampsiteSelectCategoryDto campsiteCategoryDto);
    public void deletePoint(@Param("id") int id);
    public void updatePoint(CampsiteAreaPointDto campsiteAreaPointDto);
    public int newCampsiteID();
    public int newAreaIDByCampsiteId(int id);
    public int pointCountByAreaId(int id);
    public List<CampsiteCategoryDto> getCampsiteCategory();
    public CampsiteCategoryDto getCategoryBySelectCategoryId(int id);
    public List<Integer> getSelectCategoriesByCampsiteId(@Param("id") int campsite_id);
    public List<Integer> getSelectAreaCategoriesByAreaId(@Param("id") int area_id);
    public List<CampsiteAreaDto> getAreaList(@Param("campsite_id") int campsite_id);
    public List<CampsiteAreaPointDto> getPointList(@Param("area_id") int area_id);
    public List<CampsiteCategoryDto> getAreaCategory();
}
