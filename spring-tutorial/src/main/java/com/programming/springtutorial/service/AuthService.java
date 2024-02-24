package com.programming.springtutorial.service;

import com.programming.springtutorial.dto.AuthRequest;
import com.programming.springtutorial.dto.AuthResponse;
import com.programming.springtutorial.dto.JwtResponse;
import com.programming.springtutorial.dto.RegisterRequest;
import com.programming.springtutorial.repo.TokenRepository;
import com.programming.springtutorial.repo.UserRepository;
import com.programming.springtutorial.token.Token;
import com.programming.springtutorial.token.TokenType;
import com.programming.springtutorial.user.RefreshToken;
import com.programming.springtutorial.user.Role;
import com.programming.springtutorial.user.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final RefreshTokenService refreshTokenService;

    public AuthResponse register(RegisterRequest request) {
        var user = UserData.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(request.getEmail());

        saveUserToken(savedUser, jwtToken);
        return new AuthResponse(jwtToken);
    }


    public JwtResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User with the given email address does not exist."));

        System.out.println("This email is "+ user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getEmail());
        var token = jwtService.generateToken(request.getEmail());

        revokeAllUserTokens(user);
        saveUserToken(user, token);
        return new JwtResponse(token, refreshToken.getToken());
    }

    private void revokeAllUserTokens(UserData user){
        var validTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validTokens.isEmpty())
            return;

        validTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validTokens);
    }

    private void saveUserToken(UserData savedUser, String jwtToken) {
        var token = Token.builder()
                .user(savedUser)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

}
