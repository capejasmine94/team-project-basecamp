package com.bulmeong.basecamp.store.dto;

import lombok.Data;

@Data
public class StoreBankAccountDto {
    private int id;
    private int store_id;
    private String bank_name;
    private int account_number;
    private String account_holder;
}
