package com.programming.emailverificationdemo.event;

import com.programming.emailverificationdemo.user.UserEntity;
import lombok.*;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {


    private UserEntity user;
    private String applicationUrl;

    public RegistrationCompleteEvent(UserEntity user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}