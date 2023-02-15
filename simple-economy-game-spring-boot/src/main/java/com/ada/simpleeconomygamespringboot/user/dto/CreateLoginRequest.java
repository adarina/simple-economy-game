package com.ada.simpleeconomygamespringboot.user.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateLoginRequest {

    private String username;

    private String password;
}