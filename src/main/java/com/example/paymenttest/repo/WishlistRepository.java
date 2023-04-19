package com.example.paymenttest.repo;

import com.example.paymenttest.entity.User;
import com.example.paymenttest.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findAllByUserOrderByCreatedDateDesc(User user);
}
