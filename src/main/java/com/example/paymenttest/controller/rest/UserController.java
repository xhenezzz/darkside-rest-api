package com.example.paymenttest.controller.rest;

import com.example.paymenttest.common.ApiResponse;
import com.example.paymenttest.dto.ProductDto;
import com.example.paymenttest.dto.ResponseDto;
import com.example.paymenttest.dto.UserDto;
import com.example.paymenttest.dto.user.SignInDto;
import com.example.paymenttest.dto.user.SignInResponseDto;
import com.example.paymenttest.dto.user.SignupDto;
import com.example.paymenttest.exeption.CustomException;
import com.example.paymenttest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto signUpDto) throws CustomException{
        return userService.signUp(signUpDto);
    }

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto)throws CustomException {
        return userService.signIn(signInDto);
    }
}
