package com.ada.simpleeconomygamespringboot.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetLoginResponse {

    private String username;

    private String accessToken;

    private Long id;

    private String role;

    public GetLoginResponse(String username, String accessToken) {
        this.username = username;
        this.accessToken = accessToken;
    }
}
