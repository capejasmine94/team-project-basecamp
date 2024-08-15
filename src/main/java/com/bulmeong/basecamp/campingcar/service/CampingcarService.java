package com.bulmeong.basecamp.campingcar.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulmeong.basecamp.campingcar.controller.ReturnExternalInspectionDto;
import com.bulmeong.basecamp.campingcar.dto.BasicFacilitiesDto;
import com.bulmeong.basecamp.campingcar.dto.CampingCarLikeDto;
import com.bulmeong.basecamp.campingcar.dto.ProductDetailImgDto;
import com.bulmeong.basecamp.campingcar.dto.RentUserDto;
import com.bulmeong.basecamp.campingcar.dto.RentalExternalInspectionDto;
import com.bulmeong.basecamp.campingcar.dto.ReservationDto;
import com.bulmeong.basecamp.campingcar.mapper.CampingcarSqlMapper;

@Service
public class CampingcarService {

    @Autowired
    private CampingcarSqlMapper campingCarSqlMapper;


    // 좋아요 관련 
    public void like(CampingCarLikeDto campingCarLikeDto){
        campingCarSqlMapper.createLike(campingCarLikeDto);
    }

    // 등록된 차량 좋아요 누른 수 
    public int getLikeTotalCountByProduct_id(int product_id) {
        return campingCarSqlMapper.countLikeByProduct_id(product_id);
    }

    // 회원이 등록된 차량에 좋아요를 눌른 수, count를 통해 회원이 좋아요 눌렀는지 확인
    public boolean isLiked(CampingCarLikeDto campingCarLikeDto) {
        return campingCarSqlMapper.checkUserLike(campingCarLikeDto) > 0; 
    }

     // 좋아요 1번 이상 눌렀을 경우 취소
     public void unLike(CampingCarLikeDto campingCarLikeDto) {
        campingCarSqlMapper.deleteLikeByLikeDto(campingCarLikeDto);
     } 

    //  상세페이지 
    public Map<String,Object> getCampingCarDetailByid(int id) {
        Map<String, Object> result = new HashMap<>();

        // 캠핑카 기본 정보 
        Map<String,Object> campingCar = campingCarSqlMapper.findCampingCarById(id);
        result.put("campingcarDto", campingCar);
        // 상세 페이지 리뷰수
        int countReview = campingCarSqlMapper.countByProductId(id);
        result.put("countReview", countReview);
        return result;
    }

    //상세페이지_세부이미지 
    public List<ProductDetailImgDto> getProductDetailImgByProductId(int id) {

        List<ProductDetailImgDto> productDetailImgDto=campingCarSqlMapper.findDetail_ImgByCarId(id);
        System.out.println("DImg:" + productDetailImgDto);
        return productDetailImgDto;
    }

    // 상세페이지_기본 보유 옵션 
    public List<BasicFacilitiesDto> getBasicFacilitiesByProductId(int id) {
        List<BasicFacilitiesDto> basicFacilities  = campingCarSqlMapper.findBasicfacilitiesByCarId(id);
        return basicFacilities;
    }
    // 렌트 고객 검증
    public boolean isRentUser(int id) {
        return campingCarSqlMapper.findRentUserByUserId(id) > 0;
    }
    // 렌트 고객ID 가지고 오기
    public int getExistingByRentUserId(int id) {
        return campingCarSqlMapper.existingByRentUserId(id);
    }

    // 최초 렌트 고객 등록
    public void registeRentUser(RentUserDto parms, ReservationDto reservationDto) {
        campingCarSqlMapper.createRentUser(parms);

        reservationDto.setRent_user_id(parms.getId());

        campingCarSqlMapper.createReservation(reservationDto);
    }
    // 기존 렌트 고객 예약 등록
    public void existingRentUserReservation(ReservationDto reservationDto) {
        campingCarSqlMapper.createReservation(reservationDto);
    }
    
     // 최종 예약 정보 확인
    public Map<String, Object> getReservationDetails(int rentUserId, int id) {
        return campingCarSqlMapper.findReservationByRentUserIdAndProduct_Id(rentUserId, id);
    }

    // 차량 대여 점검 Shoot
    public void registerRentShoot(RentalExternalInspectionDto parmas) {
        campingCarSqlMapper.createRentShoot(parmas);
    }

    // 차량 외부 촬영
    public void registerReturnShoot(ReturnExternalInspectionDto params) {
        campingCarSqlMapper.createReturnShoot(params);
    }
    
    public List<Map<String,Object>> getUseageHistroyAllByRentUserId(int id) {
        return campingCarSqlMapper.useageHistroyAllByRentUserId(id);
    }

}
