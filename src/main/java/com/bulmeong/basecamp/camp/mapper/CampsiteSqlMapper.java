package com.bulmeong.basecamp.camp.mapper;

import java.util.List;
import java.util.Map;

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
    //===================================================================================================================
    // 회원가입 구역
    //===================================================================================================================
    public int newCampsiteId();
    public CampsiteDto getCampsiteDtoByAccountInfo(@Param("account") String account, @Param("password") String password);
    public void registerUser(CampsiteDto campsiteDto);
    public void registerBank(CampsiteBankDto campsiteBankDto);
    public void registerCamp(CampsiteDto campsiteDto);

    //===================================================================================================================
    // 이미지
    //===================================================================================================================
    public void addCampMainImage(CampsiteImageDto imageDto);
    public void deleteCampMainImage(@Param("campsite_id") int campsite_id);
    public List<CampsiteImageDto> campMainImage(@Param("campsite_id") int campsite_id);

    public void addAreaMainImage(CampsiteAreaImageDto imageDto);
    public void deleteAreaMainImage(@Param("area_id") int area_id);
    public List<CampsiteAreaImageDto> areaMainImage(@Param("area_id") int area_id);
    
    //===================================================================================================================
    // 카테고리
    //===================================================================================================================
    public List<CampsiteCategoryDto> campCategory();
    public List<CampsiteCategoryDto> areaCategory();
    public List<Map<String,Object>> selectCampCategory(int campsite_id);
    public List<Map<String,Object>> selectAreaCategory(int area_id);
    public void addSelectCampCategory(CampsiteSelectCategoryDto selectCategoryDto);
    public void addSelectAreaCategory(CampsiteAreaSelectCategoryDto selectAreaCategoryDto);
    public void deleteSelectCampCategory(@Param("campsite_id") int campsite_id);
    public void deleteSelectAreaCategory(@Param("area_id") int area_id);
    
    //===================================================================================================================
    // 캠핑장
    //===================================================================================================================
    public int minPriseByCampsiteId(@Param("campsite_id") int campsite_id);
    public List<CampsiteDto> getAllCampsiteDto();
    public CampsiteDto getCampsiteDtoById(@Param("campsite_id") int campsite_id);
    public List<CampsiteAreaDto> getAreaListByCampsiteId(@Param("campsite_id") int campsite_id);
    public CampsiteAreaDto getAreaById(@Param("area_id") int area_id);
    public void updateCamp(CampsiteDto campsiteDto);
    public List<CampsiteAreaPointDto> pointByAreaId(@Param("area_id") int area_id);
    public List<CampsiteAreaPointDto> pointByPointId(@Param("point_id") int point_id);
    public void registerArea(CampsiteAreaDto campsiteAreaDto);
    public void updateArea(CampsiteAreaDto campsiteAreaDto);
    public void registerPoint(CampsiteAreaPointDto pointDto);
    public void deletePoints(@Param("area_id") int area_id);
    public void deletePoint(@Param("point_id") int point_id);
    public void registerPointId(CampsiteAreaPointDto pointDto);
    public List<Map<String,Object>> getPointList(@Param("area_id") int area_id);

}
