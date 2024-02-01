package com.backend.trawisa.utils.validation;

import com.app.base.project.utils.MultiLangMessage;
import com.backend.trawisa.exception.ApiValidationException;
import com.backend.trawisa.model.enumtype.Gender;
import org.springframework.stereotype.Component;

import static com.app.base.project.utils.BaseValidationUtils.isValidEmail;
import static com.app.base.project.utils.BaseValidationUtils.isValidPassword;
import static com.app.base.project.utils.Utils.isNullOrEmpty;

@Component
public class ValidationUtils {

    ApiValidationException validationException;
    MultiLangMessage langMessage;

    public ValidationUtils(ApiValidationException validationException, MultiLangMessage langMessage) {
        this.validationException = validationException;
        this.langMessage = langMessage;
    }

    public void checkEmailValidation(String email) throws ApiValidationException {
        if (isNullOrEmpty(email)) {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("emailEmpty"));
            throw validationException;
        } else if (!isValidEmail(email)) {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("invalidEmail"));
            throw validationException;
        }
    }

    public void checkOtp(int otp) throws ApiValidationException {
        String OTP = String.valueOf(otp);
        if (OTP.length() != 5) {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("invalidOtp"));
            throw validationException;
        }
    }

    public void checkPasswordValidation(String password) throws ApiValidationException {
        if (isNullOrEmpty(password)) {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("passwordEmpty"));
            throw validationException;
        } else if (!isValidPassword(password)) {
            validationException.setErrorMessage(langMessage.getLocalizeMessage("invalidPasswordFormat"));
            throw validationException;
        }
    }

    public void isValidGender(String gender) throws ApiValidationException {
        try {
            Gender genderEnum = Gender.valueOf(gender); // Assuming the gender is in string format like "MALE", "FEMALE", etc.

            // Assuming Gender is an enum with specific values
            if (genderEnum != Gender.Male && genderEnum != Gender.Female && genderEnum != Gender.Other) {
                validationException.setErrorMessage(langMessage.getLocalizeMessage("invalidGender"));
                throw validationException;
            }
        } catch (IllegalArgumentException e) {
            // Handle the case where the provided string does not match any enum constant
            validationException.setErrorMessage(langMessage.getLocalizeMessage("invalidGender"));
            throw validationException;
        }
    }
}
