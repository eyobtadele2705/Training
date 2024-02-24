package com.programming.emailverificationdemo.dto;

import org.hibernate.annotations.NaturalId;

public record UserRegister(
         String firstName,
         String lastName,
         String email,
         String password,
         String role
) {

}
