package com.bulmeong.basecamp.camp.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public List<Integer> getCampsiteCategoriesByCampsiteId(int id) { 
        return campsiteSqlMapper.getSelectCategoriesByCampsiteId(id);
    }

    // 캠핑장 카테고리
    public List<CampsiteCategoryDto> getCampsiteCategory() {
        return campsiteSqlMapper.getCampsiteCategory();
    }
}
