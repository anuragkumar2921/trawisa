package com.backend.trawisa.controller.v1;


import com.app.base.project.utils.apiResponse.ApiResponse;
import com.app.base.project.utils.apiResponse.ResponseHandler;
import com.backend.trawisa.exception.ApiValidationException;
import com.backend.trawisa.model.request.*;
import com.backend.trawisa.security.jwt.JwtUtils;
import com.backend.trawisa.service.AuthService;
import com.backend.trawisa.utils.validation.AuthValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.backend.trawisa.constant.ApiConstant.*;


@RestController
@RequestMapping(AUTH)
public class AuthController {

    private final AuthService authService;
    private final AuthValidation authValidation;

    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthService authService, AuthValidation authValidation, JwtUtils jwtUtils) {
        this.authService = authService;
        this.authValidation = authValidation;
        this.jwtUtils = jwtUtils;
    }


    @PostMapping(REGISTER)
    public ResponseEntity<ApiResponse> userRegister(@RequestBody CreateAccountRequest request)
            throws ApiValidationException {
        authValidation.isRegisterReqValid(request);
        ApiResponse baseApiResponse = this.authService.createAccount(request);
        return ResponseHandler.sendResponse(baseApiResponse);

    }

    @PostMapping(VERIFY_OTP)
    public ResponseEntity<ApiResponse> verifyOtp(@RequestBody VerifyOtpRequest request)
            throws ApiValidationException {
        authValidation.isVerifyOtpRequestValid(request);
        ApiResponse apiResponse = this.authService.verifyOtp(request);
        return ResponseHandler.sendResponse(apiResponse);

    }



    @PostMapping(RESEND_OTP)
    public ResponseEntity<ApiResponse> resendOtp(@RequestBody ResendOtpRequest resendOtpRequest) throws ApiValidationException {
        authValidation.isResendOtpReqValid(resendOtpRequest);
        ApiResponse baseApiResponse = this.authService.resendOtp(resendOtpRequest);
        return ResponseHandler.sendResponse(baseApiResponse);

    }


    @PostMapping(LOGIN)
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) throws ApiValidationException {
        authValidation.isLoginReqValid(loginRequest);

        ApiResponse baseApiResponse = this.authService.login(loginRequest);
        return ResponseHandler.sendResponse(baseApiResponse);

    }

    @PostMapping(FORGOT_PASSWORD)
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) throws ApiValidationException {
        authValidation.isForgotPasswordReqValid(request);
        ApiResponse baseApiResponse = this.authService.forgotPassword(request);
        return ResponseHandler.sendResponse(baseApiResponse);
    }

    @PostMapping(CHANGE_PASSWORD)
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody ChangePassRequest changePassRequest) throws ApiValidationException {
        this.authValidation.isChangePassReqValid(changePassRequest);
        ApiResponse baseApiResponse = this.authService.changePassword(changePassRequest);
        return ResponseHandler.sendResponse(baseApiResponse);
    }


}
