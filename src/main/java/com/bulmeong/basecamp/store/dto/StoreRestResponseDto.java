package com.bulmeong.basecamp.store.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class StoreRestResponseDto {
    private Map<String, Object> data = new HashMap<>();

    public void add(String name, Object value) {
        data.put(name, value);
    }
}
