package com.example.paymenttest.dto.cart;

import com.example.paymenttest.entity.Cart;
import com.example.paymenttest.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CartItemDto {
    private Long Id;
    private Integer quantity;
    private Product product;


    public CartItemDto(Cart cart) {
        this.Id = cart.getId();
        this.quantity = cart.getQuantity();
        this.setProduct(cart.getProduct());
    }
}
