package com.bulmeong.basecamp.camp.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.camp.dto.CampsiteAreaDto;
import com.bulmeong.basecamp.camp.dto.CampsiteAreaImageDto;
import com.bulmeong.basecamp.camp.dto.CampsiteAreaPointDto;
import com.bulmeong.basecamp.camp.dto.CampsiteAreaSelectCategoryDto;
import com.bulmeong.basecamp.camp.dto.CampsiteBankDto;
import com.bulmeong.basecamp.camp.dto.CampsiteCategoryDto;
import com.bulmeong.basecamp.camp.dto.CampsiteDto;
import com.bulmeong.basecamp.camp.dto.CampsiteImageDto;
import com.bulmeong.basecamp.camp.dto.CampsiteSelectCategoryDto;
import com.bulmeong.basecamp.camp.mapper.CampsiteSqlMapper;
import com.bulmeong.basecamp.common.dto.ImageDto;
import com.bulmeong.basecamp.common.util.ImageUtil;

@Service
public class CampsiteService {
    @Autowired
    private CampsiteSqlMapper campsiteSqlMapper;

    //캠핑장 판매자 아이디 추가
    public void insertCampsite(CampsiteDto campsiteDto, CampsiteBankDto campsiteBankDto, MultipartFile profileImage) {
        if(profileImage != null && !profileImage.isEmpty())
        campsiteDto.setProfile_image(ImageUtil.saveImageAndReturnLocation(profileImage));
        campsiteSqlMapper.insertCampsite(campsiteDto);
        campsiteBankDto.setCampsite_id(campsiteDto.getId());
        campsiteSqlMapper.insertCampsiteBank(campsiteBankDto);
    }

    //캠핑장 부가 정보 추가
    public void updateCampsite(CampsiteDto campsiteDto, String[] campsiteCategoryDtos, MultipartFile[] main_images, MultipartFile map_image) {
        // 이미지 추가
        List<ImageDto> mainImages = ImageUtil.saveImageAndReturnDtoList(main_images);
        if(map_image != null && !map_image.isEmpty())
            campsiteDto.setMap_image(ImageUtil.saveImageAndReturnLocation(map_image));
        for(ImageDto image : mainImages) {
            CampsiteImageDto imageDto = new CampsiteImageDto();
            imageDto.setCampsite_id(campsiteDto.getId());
            imageDto.setLocation(image.getLocation());
            imageDto.setOrigin_filename(image.getOrigin_filename());
            campsiteSqlMapper.insertCampMainImage(imageDto);
        }
        for(String category : campsiteCategoryDtos) {
            CampsiteSelectCategoryDto categorySelect = new CampsiteSelectCategoryDto();
            categorySelect.setCategory_id(Integer.parseInt(category));
            categorySelect.setCampsite_id(campsiteDto.getId());
            campsiteSqlMapper.insertCampsiteCategory(categorySelect);
        }
        if(campsiteDto.getIs_authenticated() == null)    
            campsiteSqlMapper.authCampSite(campsiteDto);
        campsiteSqlMapper.updateCampsite(campsiteDto);
    }

    // 캠핑장 구역 추가
    public void insertArea(CampsiteAreaDto campsiteAreaDto) {
        campsiteSqlMapper.insertArea(campsiteAreaDto);
    }

    // 캠핑장 구역 고유번호로 포인트 생성
    public void insertPoint(int id) {
        campsiteSqlMapper.insertPoint(id);
    }
    public void createNameForArea(CampsiteAreaDto campsiteAreaDto){
        campsiteSqlMapper.updateArea(campsiteAreaDto);
    }

    public void deletePoint(int id) {
        campsiteSqlMapper.deletePoint(id);
    }

    // 캠핑장 구역 부가정보 추가
    public void updateArea(CampsiteAreaDto campsiteAreaDto, String[] areaCategoryDtos, MultipartFile[] main_images, MultipartFile map_image) {
        List<ImageDto> mainImages = ImageUtil.saveImageAndReturnDtoList(main_images);
        if(map_image != null && !map_image.isEmpty())
            campsiteAreaDto.setMap_image(ImageUtil.saveImageAndReturnLocation(map_image));
        for(ImageDto image : mainImages) {
            CampsiteAreaImageDto imageDto = new CampsiteAreaImageDto();
            imageDto.setArea_id(campsiteAreaDto.getId());
            imageDto.setLocation(image.getLocation());
            imageDto.setOrigin_filename(image.getOrigin_filename());
            campsiteSqlMapper.insertAreaImage(imageDto);
        }
        System.out.println(campsiteAreaDto);
        campsiteSqlMapper.updateArea(campsiteAreaDto);
        for(String category : areaCategoryDtos) {
            CampsiteAreaSelectCategoryDto categorySelect = new CampsiteAreaSelectCategoryDto();
            categorySelect.setCategory_id(Integer.parseInt(category));
            categorySelect.setArea_id(campsiteAreaDto.getId());
            campsiteSqlMapper.insertAreaCategory(categorySelect);
        }
       
    }

    // 캠핑장 포인트 수정
    public void updatePoint(CampsiteAreaPointDto campsiteAreaPointDto) {
        campsiteSqlMapper.updatePoint(campsiteAreaPointDto);
    }

    // 아이디 & 비밀번호로 정보 찾기
    public CampsiteDto getCampsiteDtoByAccountInfo(CampsiteDto campsiteDto) {
        return campsiteSqlMapper.getCampsiteDtoByAccountInfo(campsiteDto);
    }
    public CampsiteDto getCampsiteDtoByAccountInfo(String account, String password) {
        CampsiteDto campsiteDto = new CampsiteDto();
        campsiteDto.setAccount(account);
        campsiteDto.setPassword(password); 
        return campsiteSqlMapper.getCampsiteDtoByAccountInfo(campsiteDto);
    }


    // 아이디로만 정보 찾기
    public CampsiteDto getCampsiteDtoByAccount(CampsiteDto campsiteDto) {
        return campsiteSqlMapper.getCampsiteDtoByAccount(campsiteDto);
    }
    public CampsiteDto getCampsiteDtoByAccount(String account) {
        CampsiteDto campsiteDto = new CampsiteDto();
        campsiteDto.setAccount(account);
        return campsiteSqlMapper.getCampsiteDtoByAccount(campsiteDto);
    }

    // 고유번호로 정보 찾기
    public CampsiteDto getCampsiteDtoById(int id) {
        CampsiteDto camp = new CampsiteDto();
        camp.setId(id);
        return campsiteSqlMapper.getCampsiteDtoById(camp);
    }

    public CampsiteDto getCampsiteDtoById(CampsiteDto campsiteDto) {
        return campsiteSqlMapper.getCampsiteDtoById(campsiteDto);
    }

    // 이름으로 정보 찾기
    public CampsiteDto getCampsiteDtoByName(CampsiteDto campsiteDto) {
        return campsiteSqlMapper.getCampsiteDtoByName(campsiteDto);
    }
    public CampsiteDto getCampsiteDtoByName(String name) {
        CampsiteDto campsiteDto = new CampsiteDto();
        campsiteDto.setName(name);
        return campsiteSqlMapper.getCampsiteDtoByName(campsiteDto);
    }


    // 캠프 등록이 된 유저인지 확인
    public boolean isAuthed(CampsiteDto campsiteDto) {
        return campsiteDto.getIs_authenticated().equals("T");
    }


    // (회원가입용) 판매회원 제일 마지막 순서
    public int newCampsiteID() {
        return campsiteSqlMapper.newCampsiteID();
    }

    // 구역 맨 마지막 순서
    public int newAreaID(int campsite_id) {
        return campsiteSqlMapper.newAreaIDByCampsiteId(campsite_id);
    }

    public List<Integer> getCampsiteCategoriesByCampsiteId(int id) { 
        return campsiteSqlMapper.getSelectCategoriesByCampsiteId(id);
    }

    // 캠핑장 카테고리
    public List<CampsiteCategoryDto> getCampsiteCategory() {
        return campsiteSqlMapper.getCampsiteCategory();
    }

    public List<CampsiteCategoryDto> getAreaCategory() {
        return campsiteSqlMapper.getAreaCategory();
    }

    // 캠핑장 에리어 리스트
    public List<Map<String,Object>> getAreaList(int campsite_id) { 
        List<Map<String,Object>> result = new ArrayList<>();
        List<CampsiteAreaDto> list = campsiteSqlMapper.getAreaList(campsite_id);
        for(CampsiteAreaDto area : list) {
            Map<String,Object> map = new HashMap<>();
            map.put("dto", area);
            int count = campsiteSqlMapper.pointCountByAreaId(area.getId());
            map.put("pointCount", count);
            map.put("points", campsiteSqlMapper.getPointList(area.getId()));
            map.put("selectAreaCategory", campsiteSqlMapper.getSelectAreaCategoriesByAreaId(area.getId()));
            result.add(map);
        }
        return result; 
    }

    public List<CampsiteAreaPointDto> getPointList(int area_id) {
        return campsiteSqlMapper.getPointList(area_id);
    }

    // 캠핑장 구역 정보
    public Map<String,Object> getAreaInfo(int area_id){
        Map<String,Object> map = new HashMap<>();
        CampsiteAreaDto area = new CampsiteAreaDto();
        area.setId(area_id);
        map.put("dto", campsiteSqlMapper.getAreaDtoById(area));
        int count = campsiteSqlMapper.pointCountByAreaId(area_id);
        map.put("pointCount", count);
        map.put("points", campsiteSqlMapper.getPointList(area_id));
        map.put("selectAreaCategory", campsiteSqlMapper.getSelectAreaCategoriesByAreaId(area_id));
        return map;
    }
}
