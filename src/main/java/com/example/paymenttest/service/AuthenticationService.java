package com.example.paymenttest.service;

import com.example.paymenttest.entity.AuthenticationToken;
import com.example.paymenttest.entity.User;
import com.example.paymenttest.exeption.AuthenticationFailException;
import com.example.paymenttest.repo.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

    @Autowired
    TokenRepository tokenRepository;

    public void saveConfirmationToken(AuthenticationToken authenticationToken){
        tokenRepository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepository.findByUser(user);
    }

    public User getUser(String token){
        AuthenticationToken auth = tokenRepository.findByToken(token);
        if(Objects.isNull(token)){
            return null;
        }
        return auth.getUser();
    }

    public void authenticate(String token){
        if(Objects.isNull(token)){
            throw new AuthenticationFailException("token not present");
        }

        if(Objects.isNull(getUser(token))){
            throw new AuthenticationFailException("token not valid");
        }
    }
}
