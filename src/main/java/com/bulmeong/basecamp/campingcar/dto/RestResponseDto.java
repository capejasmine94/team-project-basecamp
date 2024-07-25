package com.bulmeong.basecamp.campingcar.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class RestResponseDto {
    private String result; //응답 상태를 나타내는 필드: "success" or "fail" 
    private String reason; // r실패 시 실패 이유를 나타내는 필드 "데이터가 부족합니다" 알림용
    private Map<String,Object> data = new HashMap<>();// 추가 데이터를 저장할 맵

    // model.addAttribute와 같이 키와 값으로 받을 수 있도록 메소드
    public void add(String name, Object value) {
        data.put(name,value);
    }
}
