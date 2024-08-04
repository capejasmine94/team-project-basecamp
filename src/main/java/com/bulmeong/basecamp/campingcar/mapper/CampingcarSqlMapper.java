package com.bulmeong.basecamp.campingcar.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bulmeong.basecamp.campingcar.dto.CampingCarLikeDto;
import com.bulmeong.basecamp.campingcar.dto.ProductDetailImgDto;

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
    public List<Map<String,Object>> findBasicfacilitiesByCarId(@Param("id") int id);
}
