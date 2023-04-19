package com.example.paymenttest.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Web {
    @GetMapping
    public String home(){
        return "index";
    }
}
