package com.programming.emailverificationdemo.controller;

import com.programming.emailverificationdemo.dto.UserRegister;
import com.programming.emailverificationdemo.event.RegistrationCompleteEvent;
import com.programming.emailverificationdemo.repo.TokenVerificationRepository;
import com.programming.emailverificationdemo.service.UserService;
import com.programming.emailverificationdemo.token.VerificationToken;
import com.programming.emailverificationdemo.user.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final TokenVerificationRepository tokenRepository;


    @PostMapping
    public String addUser(@RequestBody UserRegister userRegister, final HttpServletRequest request){
        UserEntity user = userService.registerUser(userRegister);

        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));

        return "User registered successfully, please check your email to complete the registration.";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam String token){
        VerificationToken theToken = tokenRepository.findByToken(token);

        if (theToken.getUser().isEnabled()){
            return "This account has already been verified.";
        }

        return userService.verifyToken(token);
//        String verificationResult = userService.verifyToken(token);
    }
    public String applicationUrl(HttpServletRequest request) {

        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }

}
