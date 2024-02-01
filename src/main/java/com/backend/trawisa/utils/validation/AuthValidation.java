package com.backend.trawisa.utils.validation;


import com.app.base.project.utils.MultiLangMessage;
import com.backend.trawisa.exception.ApiValidationException;
import com.backend.trawisa.model.request.*;
import com.backend.trawisa.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.app.base.project.utils.BaseValidationUtils.isValidEmail;
import static com.app.base.project.utils.BaseValidationUtils.isValidPassword;
import static com.app.base.project.utils.Utils.isNullOrEmpty;

@Component
public class AuthValidation {


    private final ApiValidationException validationException;
    private final MultiLangMessage langMessage;

    private final ValidationUtils validationUtils;

    @Autowired
    public AuthValidation(ApiValidationException validationException, MultiLangMessage langMessage, ValidationUtils validationUtils) {
        this.validationException = validationException;
        this.langMessage = langMessage;
        this.validationUtils = validationUtils;
    }


    public Boolean isRegisterReqValid(CreateAccountRequest request) throws ApiValidationException {

        String registerType = request.getLoginBy();


        if (isNullOrEmpty(request.getEmail())) {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("emailEmpty"));
            throw validationException;
        } else if (!isValidEmail(request.getEmail())) {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("invalidEmail"));
            throw validationException;
        } else if (registerType == null) {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("loginBYErrorMsg"));
            throw validationException;
        } else if (isNullOrEmpty(request.getFcmToken())) {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("fcmEmptyError"));
            throw validationException;
        }

        if (registerType.equalsIgnoreCase("email")) {
            if (isNullOrEmpty(request.getPassword())) {
                validationException.setErrorMessage(langMessage.getLocalizeMessage("passwordEmpty"));
                throw validationException;
            } else if (!isValidPassword(request.getPassword())) {
                validationException.setErrorMessage(langMessage.getLocalizeMessage("invalidPasswordFormat"));
                throw validationException;
            }

        } else if (new MyUtils().checkSocialLogin(registerType)) {
            if (isNullOrEmpty(request.getSocialId())) {
                validationException.setErrorMessage(langMessage.getLocalizeMessage("socialIdTokenEmpty"));
                throw validationException;
            }
        } else {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("invalidLoginBy"));
            throw validationException;
        }
        return true;
    }


    public Boolean isLoginReqValid(LoginRequest request) throws ApiValidationException {

        String registerType = request.getLoginBy();


        if (isNullOrEmpty(request.getEmail())) {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("emailEmpty"));
            throw validationException;
        } else if (!isValidEmail(request.getEmail())) {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("invalidEmail"));
            throw validationException;
        } else if (registerType == null) {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("loginBYErrorMsg"));
            throw validationException;
        } else if (isNullOrEmpty(request.getFcmToken())) {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("fcmEmptyError"));
            throw validationException;
        }

        if (registerType.equalsIgnoreCase("email")) {
            if (isNullOrEmpty(request.getPassword())) {
                validationException.setErrorMessage(langMessage.getLocalizeMessage("passwordEmpty"));
                throw validationException;
            } else if (!isValidPassword(request.getPassword())) {
                validationException.setErrorMessage(langMessage.getLocalizeMessage("invalidPasswordFormat"));
                throw validationException;
            }

        } else if (registerType.equalsIgnoreCase("social")) {
            if (isNullOrEmpty(request.getSocialId())) {
                validationException.setErrorMessage(langMessage.getLocalizeMessage("socialIdTokenEmpty"));
                throw validationException;
            } else if (isNullOrEmpty(request.getSocialType())) {
                validationException.setErrorMessage(langMessage.getLocalizeMessage("socialTypeEmptyError"));
                throw validationException;
            } else if (!request.getSocialType().equals("google") && !request.getSocialType().equals("apple") && !request.getSocialType().equals("facebook")) {
                validationException.setErrorMessage(langMessage.getLocalizeMessage("invalidSocialType"));
                throw validationException;
            }
        } else {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("invalidLoginBy"));
            throw validationException;
        }
        return true;
    }


    public Boolean isResendOtpReqValid(ResendOtpRequest otpRequest) throws ApiValidationException {
        validationUtils.checkEmailValidation(otpRequest.getEmail());
        return true;
    }


    public Boolean isForgotPasswordReqValid(ForgotPasswordRequest request) throws ApiValidationException {
        validationUtils.checkEmailValidation(request.getEmail());
        return true;
    }

    public Boolean isChangePassReqValid(ChangePassRequest request) throws ApiValidationException {
        validationUtils.checkEmailValidation(request.getEmail());
        validationUtils.checkOtp(request.getOtp());
        validationUtils.checkPasswordValidation(request.getNewPassword());
        return true;
    }


    public Boolean isCreateProfileValid(CreateProfileRequest request) throws ApiValidationException {
        if (isNullOrEmpty(request.getName())) {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("nameRequired"));
            throw validationException;
        } else if (isNullOrEmpty(request.getUserName())) {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("userNameRequired"));
            throw validationException;
        } else if (isNullOrEmpty(String.valueOf(request.getPostalCode()))) {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("postalCodeRequired"));
            throw validationException;
        } else if (isNullOrEmpty(request.getAddress())) {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("adderessRequired"));
            throw validationException;
        } else if (isNullOrEmpty(request.getClasses())) {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("classes"));
            throw validationException;
        } else if (isNullOrEmpty(request.getGender())) {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("genderRequired"));
            throw validationException;
        }

        validationUtils.isValidGender(request.getGender());
        return true;
    }

    public Boolean isVerifyOtpRequestValid(VerifyOtpRequest request) throws ApiValidationException{
        if (isNullOrEmpty(String.valueOf(request.getOtp()))){
            validationException.setErrorMessage(langMessage.getLocalizeMessage("otpIsRequired"));
            throw validationException;
        } else if(String.valueOf(request.getOtp()).length() != 5){
            validationException.setErrorMessage(langMessage.getLocalizeMessage("otpLengthErr"));
            throw validationException;
        }
        validationUtils.checkEmailValidation(request.getEmail());
        return true;
    }


}
