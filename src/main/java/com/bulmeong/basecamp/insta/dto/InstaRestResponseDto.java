package com.bulmeong.basecamp.insta.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class InstaRestResponseDto {

    private String result; // "success" , "fail"
    private String reason; 
    private Map<String, Object> data = new HashMap<>();

    public void add(String name, Object value){
        data.put(name, value);
    }
}
