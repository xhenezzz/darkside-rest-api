package com.example.paymenttest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data @AllArgsConstructor @NoArgsConstructor
public class ProductDto {
    private Long Id;
    private @NotNull String name;
    private @NotNull String imageURL;
    private @NotNull double price;
    private @NotNull String description;

    public ProductDto(String name, String imageURL, double price, String description) {
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
        this.description = description;
    }
}
