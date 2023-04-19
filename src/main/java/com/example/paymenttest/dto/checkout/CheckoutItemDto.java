package com.example.paymenttest.dto.checkout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CheckoutItemDto {
    private String productName;
    private int quantity;
    private double price;
    private Long productId;
    private Long userId;
}
