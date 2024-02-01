package com.backend.trawisa.model.request;


import lombok.Data;

@Data
public class VerifyOtpRequest {
    private String Email;
    private int Otp;
}
