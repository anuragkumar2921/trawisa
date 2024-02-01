package com.backend.trawisa.service_listener;

import com.app.base.project.utils.apiResponse.ApiResponse;
import com.backend.trawisa.model.request.*;

public interface AuthServiceListener {

    ApiResponse createAccount(CreateAccountRequest createAccountRequest);
    ApiResponse resendOtp(ResendOtpRequest resendOtpRequest);
    ApiResponse login(LoginRequest loginRequest);
    ApiResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
    ApiResponse changePassword(ChangePassRequest changePassRequest);
    ApiResponse verifyOtp(VerifyOtpRequest verifyOtpRequest);
}
