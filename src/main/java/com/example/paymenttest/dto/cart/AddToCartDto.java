package com.example.paymenttest.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@Data @AllArgsConstructor @NoArgsConstructor
public class AddToCartDto {
    private Long Id;
    private Long productId;
    private int quantity;
}
