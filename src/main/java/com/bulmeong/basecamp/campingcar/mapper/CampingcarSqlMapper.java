package com.bulmeong.basecamp.campingcar.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bulmeong.basecamp.campingcar.controller.ReturnExternalInspectionDto;
import com.bulmeong.basecamp.campingcar.dto.BasicFacilitiesDto;
import com.bulmeong.basecamp.campingcar.dto.CampingCarLikeDto;
import com.bulmeong.basecamp.campingcar.dto.ProductDetailImgDto;
import com.bulmeong.basecamp.campingcar.dto.RentUserDto;
import com.bulmeong.basecamp.campingcar.dto.RentalExternalInspectionDto;
import com.bulmeong.basecamp.campingcar.dto.ReservationDto;

@Mapper
public interface CampingcarSqlMapper {

    // 좋아요 관련 
    public void createLike(CampingCarLikeDto campingCarLikeDto);

    // 등록된 차량 좋아요 누른 수 
    public int countLikeByProduct_id(int product_id);

    // 회원이 등록된 차량에 좋아요를 눌른 수, count를 통해 회원이 좋아요 눌렀는지 확인
    public int checkUserLike(CampingCarLikeDto campingCarLikeDto);

    // 좋아요 1번 이상 눌렀을 경우 취소 
    public void deleteLikeByLikeDto(CampingCarLikeDto campingCarLikeDot);

    // 상세페이지_캠핑카 기본 정보 조회
    public Map<String,Object> findCampingCarById(@Param("id") int id);

    // 상세페이지_세부이미지
    public List<ProductDetailImgDto> findDetail_ImgByCarId(@Param("id") int id);

    // 상세페이지_기본보유옵션
    public List<BasicFacilitiesDto> findBasicfacilitiesByCarId(@Param("id") int id);
    // 상세페이지_리뷰수
    public int countByProductId(int id);
    //렌트 고객 검증
    public int findRentUserByUserId(int id); 
    //렌트 고객 등록
    public void createRentUser(RentUserDto rentUserParams);

    // 예약하기
    public void createReservation(ReservationDto reservationDto);

    // 최종 예약 정보 확인
    public Map<String,Object> findReservationByRentUserIdAndProduct_Id(@Param("rent_user_id") int rent_user_id, @Param("product_id")int product_id);

    //차량 대여 점검 
    public void createRentShoot(RentalExternalInspectionDto parmas);
    // 차량 외관 촬영 
    public void createReturnShoot(ReturnExternalInspectionDto parmas);
    // 이용내역
    public List<Map<String,Object>> useageHistroyAllByRentUserId(int id);
}
