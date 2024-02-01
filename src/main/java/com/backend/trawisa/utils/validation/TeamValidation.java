package com.backend.trawisa.utils.validation;

import com.app.base.project.utils.MultiLangMessage;
import com.app.base.project.utils.Utils;
import com.backend.trawisa.exception.ApiValidationException;
import com.backend.trawisa.model.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamValidation {

    private final ApiValidationException validationException;
    private final MultiLangMessage langMessage;

    private final ValidationUtils validationUtils;

    @Autowired
    public TeamValidation(ApiValidationException validationException, MultiLangMessage langMessage, ValidationUtils validationUtils) {
        this.validationException = validationException;
        this.langMessage = langMessage;
        this.validationUtils = validationUtils;
    }

    public boolean isCreateTeamReqValid(CreateTeamRequest createTeamRequest) throws ApiValidationException {

        if (Utils.isNullOrEmpty(createTeamRequest.getName())) {
            validationException.setErrorMessage("Team name is required");
            throw validationException;
        } else if (Utils.isNullOrEmpty(createTeamRequest.getDescription())) {
            validationException.setErrorMessage("description is required");
            throw validationException;
        }

        return true;
    }

    public boolean isUpdateTeamReqValid(UpdateTeamRequest updateTeamRequest) throws ApiValidationException {

        if (updateTeamRequest.getTeamId() == null) {
            validationException.setErrorMessage("Team Id is required");
            throw validationException;
        } else if (Utils.isNullOrEmpty(updateTeamRequest.getName())) {
            validationException.setErrorMessage("Team name is required");
            throw validationException;
        } else if (Utils.isNullOrEmpty(updateTeamRequest.getDescription())) {
            validationException.setErrorMessage("description is required");
            throw validationException;
        }

        return true;
    }

    public boolean isAddPlayerInTeamReqValid(AddPlayerInTeamRequest request) throws ApiValidationException {
        if (request.getTeamId() == null) {
            validationException.setErrorMessage("Team Id is required");
            throw validationException;
        }

        return true;
    }

    public boolean isTeamReqToPlayerReqValid(SendTeamReqPlayerRequest request) throws ApiValidationException {

        if (request.getPlayerId() == null || request.getPlayerId().isEmpty()) {
            validationException.setErrorMessage("playerId is required");
            throw validationException;
        } else if (request.getTeamId() == null) {
            validationException.setErrorMessage("teamId is required");
            throw validationException;
        }

        return true;
    }

    public boolean isManageTeamReqValid(ManageTeamNotificationRequest request) throws ApiValidationException {

        if (request.getIsNotificationEnable() == null) {
            validationException.setErrorMessage("isNotificationEnable is required");
            throw validationException;
        } else if (request.getTeamId() == null) {
            validationException.setErrorMessage("Team Id is required");
            throw validationException;
        }

        return true;
    }
}
