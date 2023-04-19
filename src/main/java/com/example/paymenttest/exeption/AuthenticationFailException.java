package com.example.paymenttest.exeption;

public class AuthenticationFailException extends IllegalArgumentException{
    public AuthenticationFailException(String msg){
        super((msg));
    }

}
