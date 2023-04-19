package com.example.paymenttest.repo;

import com.example.paymenttest.entity.Cart;
import com.example.paymenttest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository  extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
}
