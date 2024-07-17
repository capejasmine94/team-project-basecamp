package com.bulmeong.basecamp.campingcar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulmeong.basecamp.campingcar.dto.LocationDto;
import com.bulmeong.basecamp.campingcar.dto.RentalCompanyDto;
import com.bulmeong.basecamp.campingcar.mapper.AdminSqlMapper;

@Service
public class AdminService {
    @Autowired
    private AdminSqlMapper adminSqlMapper;
    //판매자 회원가입 등록 
    public void registerSeller(RentalCompanyDto rentalCompanyDto) {
        adminSqlMapper.createSeller(rentalCompanyDto);
    }
    // 판매자 id,pw 검사 
    public RentalCompanyDto getSellerByIdAndPw (String account_id, String account_pw) {
        return adminSqlMapper.selectCompanyByAccountIdandPw(account_id, account_pw);
    } 
    // 지역 카테고리 List
    public List<LocationDto> getLocationAll() {
        return adminSqlMapper.findLocationAll();
    }
}
