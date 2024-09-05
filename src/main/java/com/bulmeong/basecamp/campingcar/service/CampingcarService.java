package com.bulmeong.basecamp.campingcar.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulmeong.basecamp.campingcar.dto.BasicFacilitiesDto;
import com.bulmeong.basecamp.campingcar.dto.CampingCarLikeDto;
import com.bulmeong.basecamp.campingcar.dto.ProductDetailImgDto;
import com.bulmeong.basecamp.campingcar.dto.RentUserDto;
import com.bulmeong.basecamp.campingcar.dto.RentalExternalInspectionDto;
import com.bulmeong.basecamp.campingcar.dto.RentalReview;
import com.bulmeong.basecamp.campingcar.dto.ReservationDto;
import com.bulmeong.basecamp.campingcar.dto.ReturnExternalInspectionDto;
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
        return campingCarSqlMapper.findReservationByRentUserIdAndReservationId(rentUserId, id);
    }

    public boolean isReviewWritten(int id) {
        return campingCarSqlMapper.isReviewWritten(id) > 0;
    }

    // 차량 대여 점검 Shoot
    public void registerRentShoot(RentalExternalInspectionDto parmas) {
        campingCarSqlMapper.createRentShoot(parmas);
    }


    // 렌트고객 이용내역
    public List<Map<String,Object>> getUseageHistroyAllByRentUserId(int id) {
        return campingCarSqlMapper.useageHistroyAllByRentUserId(id);
    }

    // 반납 점검 이미지 리스트(동의요청)
    public Map<String,Object> getReturnInspectionImgList(int id) {
        return campingCarSqlMapper.returnInspectionImgList(id);
    }

    // 리뷰 등록
    public void registerReview(RentalReview review) {
        campingCarSqlMapper.createCarReview(review);
    }

    // 해당 차량의 리뷰 리스트
    public List<Map<String,Object>> getReviewAllbyCarId(int id) {
        return campingCarSqlMapper.reviewAllbyCarId(id);
    }
    // 해당 차량의 리뷰 별점 평균 
    public Double getAvgByCarId(int id) {
        return campingCarSqlMapper.avgByCarId(id);
    }

    // 해당 차량의 리뷰 참여 인원 수
    public int getReviewByCountPersont (int id) {
        return campingCarSqlMapper.reviewByCountPerson(id);
    }

    // 해당 차량의 각 별점 마다 인원수
    public List<Map<String,Object>> ratingGroupBycar(int id) {
        return campingCarSqlMapper.ratingGroupBycar(id);
    }

    public List<Map<String, Object>> getMyLikeList(int rentUserPk) {
        return campingCarSqlMapper.getMyLikeList(rentUserPk);
    }

    public List<Map<String, Object>> getSearchResultList(Map<String, Object> map) {
        System.out.println(map+"으어어어어어");
        System.out.println("필터링 리스트 어떻게 나오니.. " + campingCarSqlMapper.getSearchResultList(map));
        return campingCarSqlMapper.getSearchResultList(map);
    }

    // 예약하기에서 특정 날짜 막기 
    public List<LocalDate> getReservedDates(int product_id) {
        List<Map<String,Object>> reservations = campingCarSqlMapper.ReservationById(product_id);
        
        List<LocalDate> reservedDates = new ArrayList<>();

        for (Map<String, Object> reservation : reservations) {
            // Timestamp 객체로 반환된 start_date와 end_date를 가져옵니다.
            Timestamp startTimestamp = (Timestamp) reservation.get("start_date");
            Timestamp endTimestamp = (Timestamp) reservation.get("end_date");
    
            // Timestamp를 LocalDate로 변환합니다.
            LocalDate start_date = startTimestamp.toLocalDateTime().toLocalDate();
            LocalDate end_date = endTimestamp.toLocalDateTime().toLocalDate();
    
            // start_date부터 end_date까지의 모든 날짜를 reservedDates에 추가합니다.
            while (!start_date.isAfter(end_date)) {
                reservedDates.add(start_date);
                start_date = start_date.plusDays(1);
            }
        }

        return reservedDates;
    }

    public Map<String,Object> findRetanlCarCheckList(int id) {
        return campingCarSqlMapper.findRetanlCarCheckList(id);
    } 

    // 예약 상태 변경
    public void reservationApproved(ReservationDto reservationDto)  {
        campingCarSqlMapper.reservationApproved(reservationDto);
    }

}


