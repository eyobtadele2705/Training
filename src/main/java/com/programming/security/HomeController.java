package com.programming.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String getMessage(){
        return "Welcome to the HomeController ";
    }

    @GetMapping("/admin")
    public String getAdmin(){
        return "Welcome to the Admin Page ";
    }

    @GetMapping("/user")
    public String getUser(){
        return "Welcome to the User Page ";
    }
}
