package com.bulmeong.basecamp.store.dto;

import lombok.Data;
import java.util.*;

@Data
public class OptionRequestData {
    private List<Map<String, String>> optionDataList;
    private int additional_price;
    private int quantity;
    private int product_id;
}
