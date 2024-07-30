package com.bulmeong.basecamp.camp.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.camp.dto.CampsiteAreaDto;
import com.bulmeong.basecamp.camp.dto.CampsiteAreaPointDto;
import com.bulmeong.basecamp.camp.dto.CampsiteBankDto;
import com.bulmeong.basecamp.camp.dto.CampsiteCategoryDto;
import com.bulmeong.basecamp.camp.dto.CampsiteDto;
import com.bulmeong.basecamp.camp.dto.CampsiteImageDto;
import com.bulmeong.basecamp.camp.dto.CampsiteSelectCategoryDto;
import com.bulmeong.basecamp.camp.mapper.CampsiteSqlMapper;
import com.bulmeong.basecamp.common.dto.ImageDto;
import com.bulmeong.basecamp.common.util.ImageUtil;
import com.bulmeong.basecamp.common.util.Utils;

@Service
public class CampsiteService {
    @Autowired
    private CampsiteSqlMapper campsiteSqlMapper;
    @Autowired
    private Utils utils;

    //===================================================================================================================
    // 로그인 / 회원가입 구역
    //===================================================================================================================
    //판매자 로그인 확인
    public CampsiteDto getCampsiteDtoByAccountInfo(String account, String password) {
        return campsiteSqlMapper.getCampsiteDtoByAccountInfo(account,password);
    }
    
    //새 회원가입 번호
    public String newCampsiteId() { 
        return String.format("%02d", campsiteSqlMapper.newCampsiteId()) ; 
    }
    
    //판매자 회원 등록
    public void registerSeller(CampsiteDto campsiteDto, CampsiteBankDto campsiteBankDto, MultipartFile profileImage) {
        // 프로필 사진 저장
        if(profileImage != null) {
            String profileImageToString = ImageUtil.saveImageAndReturnLocation(profileImage);   
            campsiteDto.setProfile_image(profileImageToString);
        }

        // 새 판매자 계정 생성
        campsiteSqlMapper.registerUser(campsiteDto);

        // 새 계좌 생성
        campsiteBankDto.setCampsite_id(campsiteDto.getId());
        campsiteSqlMapper.registerBank(campsiteBankDto);
    }
    
    // 캠핑장 등록
    public void registerCamp(CampsiteDto campsiteDto, MultipartFile mapImage, MultipartFile[] mainImages, String[] categories) {
        //배치도 이미지 저장
        String mapImageToString = ImageUtil.saveImageAndReturnLocation(mapImage);
        campsiteDto.setMap_image(mapImageToString);

        //메인 이미지 저장
        List<ImageDto> mainImageList = ImageUtil.saveImageAndReturnDtoList(mainImages);
        for(ImageDto img : mainImageList) {
            CampsiteImageDto imageDto = new CampsiteImageDto();
            imageDto.setCampsite_id(campsiteDto.getId());
            imageDto.setLocation(img.getLocation());
            imageDto.setOrigin_filename(img.getOrigin_filename());
            campsiteSqlMapper.addCampMainImage(imageDto);
        }

        // 카테고리 저장
        for(String category : categories) {
            int category_id = Integer.parseInt(category);
            CampsiteSelectCategoryDto dto = new CampsiteSelectCategoryDto();
            dto.setCampsite_id(campsiteDto.getId());
            dto.setCategory_id(category_id);
            campsiteSqlMapper.addSelectCampCategory(dto);
        }

        // 판매자 정보 업데이트
        campsiteSqlMapper.registerCamp(campsiteDto);
        
        // 세션 데이터 갱신
        utils.setSession("campsite", campsiteInfo(campsiteDto.getId()));
    }
    //-------------------------------------------------------------------------------------------------------------------



    //===================================================================================================================
    // 카테고리
    //===================================================================================================================
    // 모든 카테고리
    public Map<String,Object> categories() {
        // 초기값
        Map<String, Object> result = new HashMap<>();

        // 캠핑장 카테고리
        result.put("camp", campsiteSqlMapper.campCategory());

        // 구역 카테고리
        result.put("area", campsiteSqlMapper.areaCategory());

        // 마무리
        return result;
    }

    // 모든 카테고리 캠핑장 번호로 찾기
    private List<CampsiteCategoryDto> showCategory(int campsite_id) {
        List<CampsiteCategoryDto> result = new ArrayList<>();
        List<Map<String, Object>> list = campsiteSqlMapper.selectCampCategory(campsite_id);
        for(Map<String, Object> category : list) {
            CampsiteCategoryDto dto = new CampsiteCategoryDto();
            dto.setId((int)category.get("id"));
            dto.setName((String)category.get("name"));
            dto.setImage((String)category.get("image"));
            result.add(dto);
        }
        List<CampsiteAreaDto> areaList = campsiteSqlMapper.getAreaListByCampsiteId(campsite_id);
        for(CampsiteAreaDto area : areaList) {
            int area_id = area.getId();
            list = campsiteSqlMapper.selectAreaCategory(area_id);
            for(Map<String, Object> category : list) {
                CampsiteCategoryDto dto = new CampsiteCategoryDto();
                dto.setId((int)category.get("id"));
                dto.setName((String)category.get("name"));
                dto.setImage((String)category.get("image"));
                result.add(dto);
            }
        }
        return result;
    }

    // 캠핑장 데이터 변경 시 사용
    private void updateSelectCampCategory(int campsite_id, String[] categories) {
        campsiteSqlMapper.deleteSelectCampCategory(campsite_id);
        for(String category : categories) {
            int category_id = Integer.parseInt(category);
            CampsiteSelectCategoryDto dto = new CampsiteSelectCategoryDto();
            dto.setCampsite_id(campsite_id);
            dto.setCategory_id(category_id);
            campsiteSqlMapper.addSelectCampCategory(dto);
        }
    }

    //-------------------------------------------------------------------------------------------------------------------



    //===================================================================================================================
    // 판매자 구역
    //===================================================================================================================
    //고유 번호로 캠핑장 모든 정보
    public Map<String,Object> campsiteInfo(int campsite_id) {
        // 초기값
        Map<String, Object> result = new HashMap<>();

        // Dto 확인
        CampsiteDto campsiteDto = campsiteSqlMapper.getCampsiteDtoById(campsite_id);
        if(campsiteDto == null) return null;
        
        // Dto
        result.put("dto", campsiteDto);

        // 구역
        List<CampsiteAreaDto> areaList = campsiteSqlMapper.getAreaListByCampsiteId(campsite_id);
        List<Map<String,Object>> areaInfoList = new ArrayList<>();
        for(CampsiteAreaDto area : areaList) {
            if(area == null) continue;
            int area_id = area.getId();
            Map<String,Object> areaInfo = areaInfo(area_id);
            areaInfoList.add(areaInfo);
        }
        result.put("area", areaInfoList);

        //카테고리 
        result.put("campCategory", campsiteSqlMapper.selectCampCategory(campsite_id));
        result.put("showCategory", showCategory(campsite_id));

        //리뷰

        // 마무리
        return result;
    }
    
    //고유 번호로 구역 모든 정보
    public Map<String,Object> areaInfo(int area_id) {
        // 초기값
        Map<String, Object> result = new HashMap<>();

        // Dto 확인
        CampsiteAreaDto areaDto = campsiteSqlMapper.getAreaById(area_id);
        if(areaDto == null) return null;
        
        // Dto
        result.put("dto", areaDto);

        // 포인트

        // 카테고리
        result.put("category", campsiteSqlMapper.selectAreaCategory(area_id));

        // 마무리
        return result;
    }

    // 캠핑장 수정
    public void updateCamp(CampsiteDto campsiteDto, MultipartFile mapImage, MultipartFile[] mainImages, String[] categories) {
        //배치도 이미지 저장
        String mapImageToString = ImageUtil.saveImageAndReturnLocation(mapImage);
        campsiteDto.setMap_image(mapImageToString);

        //메인 이미지 저장
        campsiteSqlMapper.deleteCampMainImage(campsiteDto.getId());
        List<ImageDto> mainImageList = ImageUtil.saveImageAndReturnDtoList(mainImages);
        for(ImageDto img : mainImageList) {
            CampsiteImageDto imageDto = new CampsiteImageDto();
            imageDto.setCampsite_id(campsiteDto.getId());
            imageDto.setLocation(img.getLocation());
            imageDto.setOrigin_filename(img.getOrigin_filename());
            campsiteSqlMapper.addCampMainImage(imageDto);
        }

        // 카테고리 수정
        updateSelectCampCategory(campsiteDto.getId(), categories);

        // 판매자 정보 업데이트
        campsiteSqlMapper.updateCamp(campsiteDto);

        // 세션 데이터 갱신
        utils.setSession("campsite", campsiteInfo(campsiteDto.getId()));
    }
    
    //-------------------------------------------------------------------------------------------------------------------



    //===================================================================================================================
    // 
    //===================================================================================================================

    //-------------------------------------------------------------------------------------------------------------------




}
