package com.backend.trawisa.model.response.login;

import com.backend.trawisa.model.response.user.UserData;
import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private UserData users;
}
