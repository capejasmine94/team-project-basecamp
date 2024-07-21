package com.bulmeong.basecamp.camp.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.camp.dto.CampsiteBankDto;
import com.bulmeong.basecamp.camp.dto.CampsiteDto;
import com.bulmeong.basecamp.camp.dto.CampsiteImageDto;
import com.bulmeong.basecamp.camp.mapper.CampsiteSqlMapper;
import com.bulmeong.basecamp.common.dto.ImageDto;
import com.bulmeong.basecamp.common.util.ImageUtil;

@Service
public class CampsiteService {
    @Autowired
    private CampsiteSqlMapper campsiteSqlMapper;

    //캠핑장 판매자 아이디 추가
    public void insertCampsite(CampsiteDto campsiteDto, CampsiteBankDto campsiteBankDto, MultipartFile profileImage) {
        campsiteDto.setProfile_image(ImageUtil.saveImageAndReturnLocation(profileImage));
        campsiteSqlMapper.insertCampsite(campsiteDto);
        campsiteBankDto.setCampsite_id(campsiteDto.getId());
        campsiteSqlMapper.insertCampsiteBank(campsiteBankDto);
    }

    //캠핑장 부가 정보 추가
    public void updateCampsite(CampsiteDto campsiteDto, MultipartFile[] main_images, MultipartFile map_image) {
        // 이미지 추가
        List<ImageDto> mainImages = ImageUtil.saveImageAndReturnDtoList(main_images);
        campsiteDto.setMap_image(null);
        for(ImageDto image : mainImages) {
            CampsiteImageDto imageDto = new CampsiteImageDto();
            imageDto.setCampsite_id(campsiteDto.getId());
            imageDto.setLocation(image.getLocation());
            imageDto.setOrigin_filename(image.getOrigin_filename());
            campsiteSqlMapper.insertCampMainImage(imageDto);
        }
        if(campsiteDto.getIs_authenticated().equals('F'))    
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

    public boolean isAuthed(CampsiteDto campsiteDto) {
        return campsiteDto.getIs_authenticated().equals("T");
    }

    public int newCampsiteID() {
        return campsiteSqlMapper.newCampsiteID();
    }
}
