package com.programming.emailverificationdemo.controller;

import com.programming.emailverificationdemo.dto.UserRegister;
import com.programming.emailverificationdemo.service.UserService;
import com.programming.emailverificationdemo.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserEntity> getUsers(){
        return userService.getUsers();
    }


}
