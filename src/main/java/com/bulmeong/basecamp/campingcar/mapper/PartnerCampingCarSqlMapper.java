package com.bulmeong.basecamp.campingcar.mapper;
import java.util.List;
import java.util.Map;

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
import com.bulmeong.basecamp.campingcar.dto.RentalPeakPriceDto;
import com.bulmeong.basecamp.campingcar.dto.RentalReview;
import com.bulmeong.basecamp.campingcar.dto.ReservationDto;
import com.bulmeong.basecamp.campingcar.dto.ReturnExternalInspectionDto;


@Mapper
public interface PartnerCampingCarSqlMapper {
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

    // 성수기 가격 등록
    public void createRentalPeakPrice(RentalPeakPriceDto rentalPeakPriceDto);
    // 특정 차량의 성수기 가격 정보 가져오기
    RentalPeakPriceDto getRentalPeakPriceByProductId(int product_id);

    // 성수기 날짜 업데이트
    void updateRentalPeakDates(RentalPeakPriceDto rentalPeakPriceDto);

    // 성수기/휴일관리_캘린더 표시를 위한 등록 차량 리스트
    public List<Map<String,Object>> getcampingCarListForCalendar(int id); 

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
    
    
    // 사용자 : 차량 등록 list(차량등록에 관한 테이블을 엮음) : 
    // 차량등록, 회사,회사지역카태고리,캠핑카유형, 운전자조건(면허증,경력,나이), 좋아요, 성수기 가격
    public List<Map<String,Object>> findCampingCarAll();

    // 판매자 : 차량 현황 및 수정 list(차랴등록에 관한 테이블 엮음)
    // 차량등록, 회사, 회사지역카테고리, 캠피앜유형, 운전자조건(면허증,경력,나이),좋아요, 성수기 가격ㄴ
    public List<Map<String,Object>> findCampingCarBySellerId(int rental_company_id);

    // 판매자: 예약신청 내역 리스트
    public List<Map<String,Object>> findbookReservationAll(int id);
    // 판매자 : 예약상태 update
    public void reserationApproved(ReservationDto reservationDto);
    // 판매자 : 리뷰 관리
    public List<Map<String,Object>> reviewManagebyRentCompanyId(int id);
    // 판매자 : 리뷰 관리의 답글 등록 
    public void reivewReplyContentByReviewId(RentalReview rentalReviewDto);
    // 판매자 : 차량 대여 관리 
    public List<Map<String,Object>> rentalManagementList(int id);
    
    // 판매자 : 렌트 외관 촬영 사진 리스트
    public List<Map<String,Object>> rentalShootList(int id); 

    // 판매자 : 차량 반납 관리 
    public List<Map<String,Object>> returnManagementList(int id); 

    // 차량 반납 외관 사진 업로드 
    public void createReturnShoot(ReturnExternalInspectionDto parmas);
}