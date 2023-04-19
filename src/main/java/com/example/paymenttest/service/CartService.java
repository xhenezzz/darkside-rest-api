package com.example.paymenttest.service;

import com.example.paymenttest.dto.cart.AddToCartDto;
import com.example.paymenttest.dto.cart.CartDto;
import com.example.paymenttest.dto.cart.CartItemDto;
import com.example.paymenttest.entity.Cart;
import com.example.paymenttest.entity.Product;
import com.example.paymenttest.entity.User;
import com.example.paymenttest.exeption.CustomException;
import com.example.paymenttest.repo.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    ProductService productService;

    @Autowired
    CartRepository cartRepository;

    public void addToCart(AddToCartDto addToCartDto, User user) {
        Product product = productService.findById(addToCartDto.getProductId());

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());

        cartRepository.save(cart);
    }

    public CartDto listCartItems(User user) {
        List<Cart> cartList =  cartRepository.findAllByUserOrderByCreatedDateDesc(user);

        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart : cartList) {
            CartItemDto cartItemDto = new CartItemDto(cart);
            totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemDto);
        }

        CartDto cartDto = new CartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItems(cartItems);
        return cartDto;
    }

    public void deleteCartItem(Long cartItemId, User user) {

        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);
        if(optionalCart.isEmpty()){
            throw new CustomException("cart item id is invalid: " + cartItemId);
        }

        Cart cart = optionalCart.get();

        if(cart.getUser() != user){
            throw new CustomException("cart item doesn't belong to user: " + cartItemId);
        }

        cartRepository.delete(cart);
    }
}
