package com.example.paymenttest.controller.rest;

import com.example.paymenttest.common.ApiResponse;
import com.example.paymenttest.dto.cart.AddToCartDto;
import com.example.paymenttest.dto.cart.CartDto;
import com.example.paymenttest.entity.User;
import com.example.paymenttest.service.AuthenticationService;
import com.example.paymenttest.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
                                                 @RequestParam("token") String token){
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        cartService.addToCart(addToCartDto, user);

        return new ResponseEntity<>(new ApiResponse(true, "Added to Cart"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token){
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        CartDto cartDto = cartService.listCartItems(user);
        return new ResponseEntity<>(cartDto,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Long itemId,
                                               @RequestParam("token") String token){
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        cartService.deleteCartItem(itemId, user);
        return new ResponseEntity<>(new ApiResponse(true, "item has been deleted!"), HttpStatus.OK);

    }
}
