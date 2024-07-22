package com.bulmeong.basecamp.campingcar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bulmeong.basecamp.campingcar.dto.BasicFacilitiesDto;
import com.bulmeong.basecamp.campingcar.dto.CampingcarDto;
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
    // 판매자 회원가입_지역Category
    public List<LocationDto> findLocationAll();
    // 판매자 id 가지고 오기
    public RentalCompanyDto getRentalCompanyInfoByid(int id);
    public CampingcarDto getCompingCarByid(int id);

    // 차량등록
    public void createCamping(CampingcarDto campingcarDto);

    // 차량등록X기본보유시설 
    public void createCarBasic(@Param("basic_facilities_id")int basic_facilities_id, @Param("product_id")int product_id);

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

    // 차량등록_차량등록 세부 이미지
    public void createDetailImg(@Param("product_id")int product_id, 
                                @Param("location")String location,
                                @Param("original_filename")String original_filename);
    
}