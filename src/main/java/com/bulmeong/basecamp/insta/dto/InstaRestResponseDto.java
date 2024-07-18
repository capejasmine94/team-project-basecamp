package com.bulmeong.basecamp.insta.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class InstaRestResponseDto {

    private String result; // result에 들어갈 값 = "success" , "fail" 둘 중 하나
    private String reason; // "fail"일때만 들어가는 케이스 _ "데이터가 부족합니다"

    // 응답 할 때 data = 항상 Map 형식
    private Map<String, Object> data = new HashMap<>(); // 우리가 넘겨줘야 할 데이터

    public void add(String name, Object value){
        data.put(name, value);
    }
}
