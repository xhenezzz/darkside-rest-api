package com.example.paymenttest.common;

import java.time.LocalDateTime;

public class ApiResponse{
    private final boolean success;
    private final String message;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getTimestamp(){
        return LocalDateTime.now().toString();
    }
}
