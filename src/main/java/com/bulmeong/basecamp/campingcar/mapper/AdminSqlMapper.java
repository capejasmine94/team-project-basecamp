package com.bulmeong.basecamp.campingcar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}
