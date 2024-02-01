package com.backend.trawisa.utils.validation;

import com.app.base.project.utils.MultiLangMessage;
import com.backend.trawisa.exception.ApiValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ProfileValidation {

    private final ApiValidationException validationException;
    private final MultiLangMessage langMessage;

    private final ValidationUtils validationUtils;

    @Autowired
    public ProfileValidation(ApiValidationException validationException, MultiLangMessage langMessage, ValidationUtils validationUtils) {
        this.validationException = validationException;
        this.langMessage = langMessage;
        this.validationUtils = validationUtils;
    }

    public boolean isProfileUpdateRequestValid(MultipartFile file) throws ApiValidationException {

        if (file == null){
            validationException.setErrorMessage("Image is required");
            throw validationException;
        }
        return true;
    }
}
