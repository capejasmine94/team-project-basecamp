package com.bulmeong.basecamp.campingcar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bulmeong.basecamp.campingcar.dto.BasicFacilitiesDto;
import com.bulmeong.basecamp.campingcar.dto.CarTypeDto;
import com.bulmeong.basecamp.campingcar.dto.DriverAgeCondDto;
import com.bulmeong.basecamp.campingcar.dto.DriverExperienceCondDto;
import com.bulmeong.basecamp.campingcar.dto.DriverLicenseDto;
import com.bulmeong.basecamp.campingcar.dto.LocationDto;
import com.bulmeong.basecamp.campingcar.dto.RentalCompanyDto;


@Mapper
public interface AdminSqlMapper {
    // 판매자 회원가입
    public void createSeller (RentalCompanyDto rentalCompanyDto);
    // 판매자 id,pw 검사
    public RentalCompanyDto selectCompanyByAccountIdandPw(@Param("account_id") String account_id, @Param("account_pw") String account_pw);
    // 지역Category
    public List<LocationDto> findLocationAll();
    // 차량등록_캠핑카 유형 Category
    public List<CarTypeDto> findCarTypeAll();
    // 차량등록_운전 면허증 Category
    public List<DriverLicenseDto> findDriverLicenseAll();
    // 차량등록_운전자 나이 Category
    public List<DriverAgeCondDto> findDriverAgeAll();
    // 차량등록_운전자 경력 Category 
    public List<DriverExperienceCondDto> findDriverExperienceAll();
    // 캠핑카 기본 보유 시설 Category 
    public List<BasicFacilitiesDto> findBasicFacilitiesAll();
    

}