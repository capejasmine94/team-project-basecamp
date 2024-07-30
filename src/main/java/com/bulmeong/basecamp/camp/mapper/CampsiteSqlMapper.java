package com.bulmeong.basecamp.camp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bulmeong.basecamp.camp.dto.CampsiteAreaDto;
import com.bulmeong.basecamp.camp.dto.CampsiteAreaImageDto;
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
    public void addAreaMainImage(CampsiteAreaImageDto imageDto);
    
    //===================================================================================================================
    // 카테고리
    //===================================================================================================================
    public List<CampsiteCategoryDto> campCategory();
    public List<CampsiteCategoryDto> areaCategory();
    public List<Map<String,Object>> selectCampCategory(int campsite_id);
    public List<Map<String,Object>> selectAreaCategory(int area_id);
    public void addSelectCampCategory(CampsiteSelectCategoryDto selectCategoryDto);
    public void deleteSelectCampCategory(@Param("campsite_id") int campsite_id);

    //===================================================================================================================
    // 캠핑장
    //===================================================================================================================
    public CampsiteDto getCampsiteDtoById(@Param("campsite_id") int campsite_id);
    public List<CampsiteAreaDto> getAreaListByCampsiteId(@Param("campsite_id") int campsite_id);
    public CampsiteAreaDto getAreaById(@Param("area_id") int area_id);
    public void updateCamp(CampsiteDto campsiteDto);

}
