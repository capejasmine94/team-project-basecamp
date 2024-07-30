package com.bulmeong.basecamp.campingcar.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.bulmeong.basecamp.campingcar.dto.CampingCarLikeDto;

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

}
