package com.programming.springtutorial.controller;

import com.programming.springtutorial.dto.*;
import com.programming.springtutorial.repo.RefreshTokenRepository;
import com.programming.springtutorial.service.AuthService;
import com.programming.springtutorial.service.JwtService;
import com.programming.springtutorial.service.RefreshTokenService;
import com.programming.springtutorial.user.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth/")
public class SecurityController {

    private final AuthService service;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @PostMapping("register")
    public AuthResponse register(@RequestBody RegisterRequest request){
        return service.register(request);
    }

    @PostMapping("authenticate")
    public JwtResponse authenticate(@RequestBody AuthRequest request){
        return service.authenticate(request);
    }

    @GetMapping("hello")
    public String hello(){
        return "Hello Testing from server";
    }

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserData)
                .map(userData -> {
//                    String accessToken = jwtService.generateToken(userData.getEmail());
                    JwtResponse authenticate = service.authenticate(
                            new AuthRequest(userData.getEmail(), userData.getPassword())
                    );

                    return JwtResponse.builder()
                            .accessToken(authenticate.getToken())
                            .token(refreshTokenRequest.getToken())
                            .build();
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database!"));
    }


}
