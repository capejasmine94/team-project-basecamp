package com.bulmeong.basecamp.secondHandProduct.dto;

import lombok.Data;

@Data
public class BuyerConfirmationDto {
    private int buyer_confirmation_id;
    private int product_id;
    private int buyer_user_id;
    private int seller_user_id;
    private int review_confirmation;
}
