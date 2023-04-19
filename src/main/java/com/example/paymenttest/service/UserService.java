package com.example.paymenttest.service;

import com.example.paymenttest.dto.ProductDto;
import com.example.paymenttest.dto.ResponseDto;
import com.example.paymenttest.dto.UserDto;
import com.example.paymenttest.dto.user.SignInDto;
import com.example.paymenttest.dto.user.SignInResponseDto;
import com.example.paymenttest.dto.user.SignupDto;
import com.example.paymenttest.entity.AuthenticationToken;
import com.example.paymenttest.entity.User;
import com.example.paymenttest.exeption.AuthenticationFailException;
import com.example.paymenttest.exeption.CustomException;
import com.example.paymenttest.repo.TokenRepository;
import com.example.paymenttest.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    private TokenRepository tokenRepository;

    @Transactional
    public ResponseDto signUp(SignupDto signupDto) throws CustomException {
        if(Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))){
            throw new CustomException("user already present");
        }

        String encryptedPassword = signupDto.getPassword();
        try {
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        }

        User user = new User(signupDto.getName(), signupDto.getSurname(), signupDto.getEmail(),
                encryptedPassword);
        userRepository.save(user);

        final AuthenticationToken authenticationToken = new AuthenticationToken(user);

        authenticationService.saveConfirmationToken(authenticationToken);

        ResponseDto responseDto = new ResponseDto("success", "dummy response");
        return responseDto;
    }

    public SignInResponseDto signIn(SignInDto signInDto) {
        //Ищем пользователя по Email
        User user = userRepository.findByEmail(signInDto.getEmail());
        if(Objects.isNull(user)){
            throw new AuthenticationFailException("user is not valid");
        }

        //Проверяем пароль пользователя!
        try {
            if(!user.getPassword().equals(hashPassword(signInDto.getPassword()))){
                throw new AuthenticationFailException("wrong password!");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }


        AuthenticationToken token = authenticationService.getToken(user);

        if(Objects.isNull(token)){
            throw new CustomException("token is not present");
        }

        return new SignInResponseDto("success", token.getToken());
    }

    String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toString();
        return hash;
    }

    public UserDto getUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    public List<UserDto> getAllUsers(){
        List<User> allUsers = userRepository.findAll();

        List<UserDto> userDtos = new ArrayList<>();
        for (User user: allUsers){
            userDtos.add(getUserDto(user));
        }
        return userDtos;
    }
}
