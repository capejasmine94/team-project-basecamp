package com.bulmeong.basecamp.store.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductSearchOptionData {
    private List<String> status_list;
    private int subcategory_id;
    private String search_option;
    private String search_word;
    private int store_id;
}
