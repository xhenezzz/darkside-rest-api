package com.example.paymenttest.controller.rest;

import com.example.paymenttest.common.ApiResponse;
import com.example.paymenttest.dto.ProductDto;
import com.example.paymenttest.entity.Product;
import com.example.paymenttest.entity.User;
import com.example.paymenttest.entity.Wishlist;
import com.example.paymenttest.service.AuthenticationService;
import com.example.paymenttest.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    @Autowired
    WishlistService wishlistService;

    @Autowired
    AuthenticationService authenticationService;
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product,
                                                     @RequestParam("token") String token){
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        Wishlist wishlist = new Wishlist(user, product);

        wishlistService.createWishlist(wishlist);

        ApiResponse apiResponse = new ApiResponse(true, "Added to wishlist");

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishlist(@PathVariable("token") String token){
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

         List<ProductDto> productDtos =  wishlistService.getWishlistForUser(user);
         return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
}
