package com.backend.trawisa.service;

import com.app.base.project.exception.ResourceNotFoundException;
import com.app.base.project.utils.*;
import com.app.base.project.utils.apiResponse.ApiDataResponse;
import com.app.base.project.utils.apiResponse.ApiResponse;
import com.backend.trawisa.model.request.*;
import com.backend.trawisa.model.response.login.LoginResponse;
import com.backend.trawisa.model.response.user.UserData;
import com.backend.trawisa.model.entity.LoginTypeEntity;
import com.backend.trawisa.model.entity.UserEntity;
import com.backend.trawisa.repositories.AuthRepo;
import com.backend.trawisa.repositories.LoginTypeRepo;
import com.backend.trawisa.security.jwt.JwtUtils;
import com.backend.trawisa.service_listener.AuthServiceListener;
import com.backend.trawisa.utils.CommonApiUtils;
import com.backend.trawisa.utils.MyUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.app.base.project.constant.BaseConstant.MAX_OTP;

@Service
public class AuthService implements AuthServiceListener {

    private final AuthRepo authRepo;
    private final PasswordEncoder passwordEncoder;

    @Qualifier("getApiDataResponse")
    private final ApiDataResponse apiDataResponse;
    @Qualifier("getApiResponse")
    private final ApiResponse apiResponse;
    private final MultiLangMessage langMessage;
    private final LoginTypeRepo socialRepo;
    private final CommonApiUtils apiUtils;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;

    public AuthService(AuthRepo authRepo, PasswordEncoder passwordEncoder, ApiDataResponse apiDataResponse, ApiResponse apiResponse, MultiLangMessage langMessage, LoginTypeRepo socialRepo, CommonApiUtils apiUtils, JwtUtils jwtUtils, ModelMapper modelMapper) {
        this.authRepo = authRepo;
        this.passwordEncoder = passwordEncoder;
        this.apiDataResponse = apiDataResponse;
        this.apiResponse = apiResponse;
        this.langMessage = langMessage;
        this.socialRepo = socialRepo;
        this.apiUtils = apiUtils;
        this.jwtUtils = jwtUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse createAccount(CreateAccountRequest request) {

        Optional<UserEntity> userInfo = this.authRepo.findByEmail(request.getEmail());
        if (userInfo.isPresent()) {

            UserEntity userData = userInfo.get();

            if (!userData.isAccountVerified()) {
                this.authRepo.delete(userData);
            } else {
                apiResponse.setErrorResponse(langMessage.getLocalizeMessage("emailAlreadyExist"), HttpStatus.IM_USED.value());
                return apiResponse;
            }
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(request.getEmail());

        if (request.getLoginBy().equals("email")) {
            userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        } else if (new MyUtils().checkSocialLogin(request.getLoginBy())) {
            String encodeSocialId = passwordEncoder.encode(request.getSocialId());
            userEntity.setSocialToken(encodeSocialId);
            userEntity.setPassword(encodeSocialId);
            userEntity.setAccountVerified(true);
        }

        LoginTypeEntity loginTypeEntity = socialRepo.findByLoginType(request.getLoginBy()).orElseThrow(
                () -> new ResourceNotFoundException(StringUtils.notFoundMessage(request.getLoginBy())));
        userEntity.setLoginType(loginTypeEntity);
        int otp = RandomUtils.generateOTP();
        userEntity.setOtp(otp);
        userEntity.setFcmToken(request.getFcmToken());
        this.authRepo.save(userEntity);
        apiResponse.setMessageResponse(langMessage.getLocalizeMessage("registrationSuccessful") + " OTP " + otp, HttpStatus.OK.value());
        return apiResponse;
    }


    @Override
    public ApiResponse login(LoginRequest loginRequest) {

        boolean isValidCredentials;
        String jwtPassword;
        UserEntity userEntity = apiUtils.getUserByEmail(loginRequest.getEmail());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsers(this.modelMapper.map(userEntity, UserData.class));

        if (loginRequest.getLoginBy().equalsIgnoreCase("email") && !userEntity.isAccountVerified()) {
            int otp = RandomUtils.generateOTP();
            userEntity.setOtp(otp);
            this.authRepo.save(userEntity);
            loginResponse.setToken("");
//            apiDataResponse.setErrorResponseData("User not verified " + otp, loginResponse, HttpStatus.FORBIDDEN.value());
            apiDataResponse.setErrorResponse("User not found", HttpStatus.FORBIDDEN.value());
            return apiDataResponse;
        }

        if (loginRequest.getLoginBy().equalsIgnoreCase("email")) {
            isValidCredentials = passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword());
            jwtPassword = loginRequest.getPassword();
        } else {
            isValidCredentials = passwordEncoder.matches(loginRequest.getSocialId(), userEntity.getSocialToken());
            jwtPassword = loginRequest.getSocialId();
        }

        if (!isValidCredentials) {
            apiResponse.setErrorResponse(langMessage.getLocalizeMessage("invalidPassword"), HttpStatus.BAD_REQUEST.value());
            return apiResponse;
        }

        this.jwtUtils.doAuthenticate(String.valueOf(userEntity.getId()), jwtPassword);
        String jwtToken = jwtUtils.getJwtToken(String.valueOf(userEntity.getId()));

        loginResponse.setToken(jwtToken);
        apiDataResponse.setSuccessResponse("Login Success", loginResponse, HttpStatus.OK.value());
        return apiDataResponse;

    }

    @Override
    public ApiResponse resendOtp(ResendOtpRequest resendOtpRequest) {
        UserEntity userEntity = apiUtils.getUserByEmail(resendOtpRequest.getEmail());

        int otp = RandomUtils.generateOTP();
        int previousOtpCount = userEntity.getOtpCount();

        Print.log("previousOtpCount " + previousOtpCount);

        Boolean timeDiff_1Hr = DateTimeUtils.getUtcTimeDiff_1HR(userEntity.getUpdatedAt().toString());

        if (previousOtpCount == MAX_OTP && !timeDiff_1Hr) {
            apiResponse.setErrorResponse(langMessage.getLocalizeMessage("otpLimitExceed"), HttpStatus.TOO_MANY_REQUESTS.value());
            return apiResponse;
        } else if (previousOtpCount == MAX_OTP && timeDiff_1Hr) {
            previousOtpCount = 0;
        }

        userEntity.setOtp(otp);
        userEntity.setOtpCount(previousOtpCount + 1);
        userEntity.setIsOtpUsed(false);
        this.authRepo.save(userEntity);
        apiResponse.setMessageResponse(langMessage.getLocalizeMessage("otpSentSuccessfully") + " OTP " + otp, HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public ApiResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        UserEntity userEntity = apiUtils.getUserByEmail(forgotPasswordRequest.getEmail());
        int otp = RandomUtils.generateOTP();
        userEntity.setOtp(otp);
        userEntity.setIsOtpUsed(false);
        this.authRepo.save(userEntity);
        apiResponse.setMessageResponse(langMessage.getLocalizeMessage("otpSentSuccessfully") + " OTP " + otp, HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public ApiResponse changePassword(ChangePassRequest changePassRequest) {
        UserEntity userEntity = apiUtils.getUserByEmail(changePassRequest.getEmail());

        if (userEntity.getOtp() != changePassRequest.getOtp() || userEntity.getIsOtpUsed()) {
            apiResponse.setErrorResponse(langMessage.getLocalizeMessage("invalidOtp"), HttpStatus.BAD_REQUEST.value());
            return apiResponse;
        }
        userEntity.setPassword(passwordEncoder.encode(changePassRequest.getNewPassword()));
        this.authRepo.save(userEntity);
        apiResponse.setMessageResponse(langMessage.getLocalizeMessage("passwordChangedSuccess"), HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public ApiResponse verifyOtp(VerifyOtpRequest verifyOtpRequest) {
        UserEntity userEntity = apiUtils.getUserByEmail(verifyOtpRequest.getEmail());
        if (userEntity.isAccountVerified()) {
            apiResponse.setErrorResponse("Account already verified", HttpStatus.BAD_REQUEST.value());
            return apiResponse;
        } else if (userEntity.getOtp() != verifyOtpRequest.getOtp()) {
            apiResponse.setErrorResponse(langMessage.getLocalizeMessage("invalidOtp"), HttpStatus.OK.value());
            return apiResponse;
        }
        userEntity.setAccountVerified(true);
        userEntity.setIsOtpUsed(true);
        this.authRepo.save(userEntity);
        ApiDataResponse response = new ApiDataResponse();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsers(this.modelMapper.map(userEntity, UserData.class));
        String jwtToken = jwtUtils.getJwtToken(String.valueOf(userEntity.getId()));
        loginResponse.setToken(jwtToken);
        response.setSuccessResponse(langMessage.getLocalizeMessage("accountVerified"), loginResponse, HttpStatus.OK.value());
        return response;
    }


}
