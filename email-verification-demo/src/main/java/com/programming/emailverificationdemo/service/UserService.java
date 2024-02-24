package com.programming.emailverificationdemo.service;

import com.programming.emailverificationdemo.dto.UserRegister;
import com.programming.emailverificationdemo.exception.UserAlreadyExistsException;
import com.programming.emailverificationdemo.repo.TokenVerificationRepository;
import com.programming.emailverificationdemo.repo.UserRepository;
import com.programming.emailverificationdemo.token.VerificationToken;
import com.programming.emailverificationdemo.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;
    private final TokenVerificationRepository verificationRepository;
    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity registerUser(UserRegister user) {
        Optional<UserEntity> user1 = this.findByEmail(user.email());

        if (user1.isPresent()){
            throw new UserAlreadyExistsException("User with email address "+ user.email() + " already exists");
        }
        var newUser = new UserEntity();

        newUser.setFirstName(user.firstName());
        newUser.setLastName(user.lastName());
        newUser.setEmail(user.email());
        newUser.setPassword(encoder.encode(user.password()));
        newUser.setRole(user.role());

        return userRepository.save(newUser);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public String verifyToken(String token) {

        VerificationToken theToken = verificationRepository.findByToken(token);
        if (theToken == null){
            return "Invalid verification token.";
        }
        UserEntity user = theToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((theToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            verificationRepository.delete(theToken);
            return "Token is already expired!";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "User account verified successfully.";
    }

    public void saveVerificationToken(UserEntity user, String token) {

        var verificationToken = new VerificationToken(token,user);

        verificationRepository.save(verificationToken);
    }
}
