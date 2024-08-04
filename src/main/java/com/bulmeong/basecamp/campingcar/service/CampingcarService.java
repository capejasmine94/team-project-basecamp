package com.bulmeong.basecamp.campingcar.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulmeong.basecamp.campingcar.dto.CampingCarLikeDto;
import com.bulmeong.basecamp.campingcar.dto.ProductDetailImgDto;
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

    //  상세페에지 
    public Map<String,Object> getCampingCarDetailByid(int id) {
        Map<String, Object> result = new HashMap<>();

        // 캠핑카 기본 정보 
        Map<String,Object> campingCar = campingCarSqlMapper.findCampingCarById(id);
        result.put("campingcarDto", campingCar);

        // 캠핑카 세부이미지
        List<ProductDetailImgDto> detailImg = campingCarSqlMapper.findDetail_ImgByCarId(id);
        result.put("detailImgDto", detailImg);

        //캠핑카 기본 보유 옵션 조회
        List<Map<String, Object>> basicFacilities  = campingCarSqlMapper.findBasicfacilitiesByCarId(id);
        result.put("basicFacilitiesDto", basicFacilities);

        return result;
    }



}
