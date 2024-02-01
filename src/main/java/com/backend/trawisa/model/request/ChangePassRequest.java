package com.backend.trawisa.model.request;

import lombok.Data;

@Data
public class ChangePassRequest {
    private String email;
    private int otp;
    private String newPassword;
}
