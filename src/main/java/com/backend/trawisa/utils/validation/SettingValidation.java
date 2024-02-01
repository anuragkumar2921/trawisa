package com.backend.trawisa.utils.validation;

import com.app.base.project.utils.MultiLangMessage;
import com.backend.trawisa.exception.ApiValidationException;
import com.backend.trawisa.model.request.UpdateSettingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettingValidation {

    private final ApiValidationException validationException;
    private final MultiLangMessage langMessage;

    private final ValidationUtils validationUtils;

    @Autowired
    public SettingValidation(ApiValidationException validationException, MultiLangMessage langMessage, ValidationUtils validationUtils) {
        this.validationException = validationException;
        this.langMessage = langMessage;
        this.validationUtils = validationUtils;
    }


    public boolean isUpdateSettingRequestValid(UpdateSettingRequest settingRequest) throws ApiValidationException {

        if (settingRequest.getPlayWithPlayer() == null){
            validationException.setErrorMessage("playWithPlayer is required");
            throw validationException;
        }else  if (settingRequest.getPlayWithTeams() == null){
            validationException.setErrorMessage("playWithTeams is required");
            throw validationException;
        }else if (settingRequest.getRadius() == null){
            validationException.setErrorMessage("radius is required");
            throw validationException;
        }


       return true;
    }
}
