package com.bulmeong.basecamp.campingcar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulmeong.basecamp.campingcar.dto.BasicFacilitiesDto;
import com.bulmeong.basecamp.campingcar.dto.CarTypeDto;
import com.bulmeong.basecamp.campingcar.dto.DriverAgeCondDto;
import com.bulmeong.basecamp.campingcar.dto.DriverExperienceCondDto;
import com.bulmeong.basecamp.campingcar.dto.DriverLicenseDto;
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

    // 차량등록_캠핑카 유형 Category List
    public List<CarTypeDto> getCarTypeAll() {
        return adminSqlMapper.findCarTypeAll(); 
    }
    // 차량등록_운전 면허증 Category List
    public List<DriverLicenseDto> getDriverLicenseAll() {
        return adminSqlMapper.findDriverLicenseAll();
    }
    // 차량등록_운전자 나이 Category List
    public List<DriverAgeCondDto> getDriverAgeAll() {
        return adminSqlMapper.findDriverAgeAll(); 
    }
    // 차량등록_운전자 경력 Category List
    public List<DriverExperienceCondDto> getDriverExperienceAll() {
        return adminSqlMapper.findDriverExperienceAll();
    }

    // 캠핑카 기본 보유 시설 Category 
    public List<BasicFacilitiesDto> getBasicFacilitiesAll() {
        return adminSqlMapper.findBasicFacilitiesAll();
    }

}
