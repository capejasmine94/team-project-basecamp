package com.bulmeong.basecamp.camp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulmeong.basecamp.camp.dto.CampsiteDto;
import com.bulmeong.basecamp.camp.mapper.CampsiteSqlMapper;

@Service
public class CampsiteService {
    @Autowired
    private CampsiteSqlMapper campsiteSqlMapper;

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


    
}
