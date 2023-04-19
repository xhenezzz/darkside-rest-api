package com.example.paymenttest.repo;

import com.example.paymenttest.entity.AuthenticationToken;
import com.example.paymenttest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Long> {
    AuthenticationToken findByUser(User user);
    AuthenticationToken findByToken(String token);

}
