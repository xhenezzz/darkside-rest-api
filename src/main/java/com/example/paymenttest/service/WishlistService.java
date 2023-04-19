package com.example.paymenttest.service;

import com.example.paymenttest.dto.ProductDto;
import com.example.paymenttest.entity.User;
import com.example.paymenttest.entity.Wishlist;
import com.example.paymenttest.repo.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistService {
    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    ProductService productService;

    public void createWishlist(Wishlist wishlist) {
        wishlistRepository.save(wishlist);
    }

    public List<ProductDto> getWishlistForUser(User user) {
        final List<Wishlist> wiishlistItem =  wishlistRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Wishlist wishlist : wiishlistItem){
            productDtos.add(productService.getProductDto(wishlist.getProduct()));
        }

        return productDtos;
    }
}
