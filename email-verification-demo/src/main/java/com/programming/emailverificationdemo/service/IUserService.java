package com.programming.emailverificationdemo.service;

import com.programming.emailverificationdemo.dto.UserRegister;
import com.programming.emailverificationdemo.user.UserEntity;
import org.apache.catalina.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<UserEntity> getUsers();
    UserEntity registerUser(UserRegister user);
    Optional<UserEntity> findByEmail(String email);

    String verifyToken(String token);
}
