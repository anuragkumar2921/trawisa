package com.backend.trawisa.model.request;

import lombok.Data;

@Data
public class CreateAccountRequest {
    private String email;
    private String password;
    private String loginBy;
    private String socialId;
    private String fcmToken;
}

