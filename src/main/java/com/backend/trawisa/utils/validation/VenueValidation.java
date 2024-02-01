package com.backend.trawisa.utils.validation;

import com.app.base.project.utils.MultiLangMessage;
import com.backend.trawisa.exception.ApiValidationException;
import com.backend.trawisa.model.request.CreateVenueRequest;
import com.backend.trawisa.model.request.UpdateVenueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import static com.app.base.project.utils.Utils.isNullOrEmpty;

@Component
public class VenueValidation {
    private final ApiValidationException validationException;
    private final MultiLangMessage langMessage;

    private final ValidationUtils validationUtils;

    @Autowired
    public VenueValidation(ApiValidationException validationException, MultiLangMessage langMessage, ValidationUtils validationUtils) {
        this.validationException = validationException;
        this.langMessage = langMessage;
        this.validationUtils = validationUtils;
    }

    public boolean isCreateVenueReqValid(CreateVenueRequest request) throws ApiValidationException {

        if (isNullOrEmpty(request.getName())) {
            validationException.setErrorMessage("Name is required");
            throw validationException;
        } else if (isNullOrEmpty(request.getStreet())) {
            validationException.setErrorMessage("Street is required");
            throw validationException;
        } else if (isNullOrEmpty(request.getHouseNumber())) {
            validationException.setErrorMessage("House number is required");
            throw validationException;
        } else if (isNullOrEmpty(request.getCity())) {
            validationException.setErrorMessage("City is required");
            throw validationException;
        } else if (isNullOrEmpty(request.getZipCode())) {
            validationException.setErrorMessage("Postal Code is required");
            throw validationException;
        } else if (request.getIsLookingForTeam() == null) {
            validationException.setErrorMessage("isLookingForTeam is required");
            throw validationException;
        } else if (isNullOrEmpty(request.getWebsite())) {
            validationException.setErrorMessage("Website is required");
            throw validationException;
        } else if (isNullOrEmpty(request.getDescription())) {
            validationException.setErrorMessage("Description is required");
            throw validationException;
        }

        return true;
    }

    public boolean isUpdateVenueImgReqValid(MultipartFile file, Integer venueId) throws ApiValidationException {

        if (venueId == null) {
            validationException.setErrorMessage("venueId is required");
            throw validationException;
        } else if (file == null) {
            validationException.setErrorMessage("Image is required");
            throw validationException;
        }


        return true;
    }

    public boolean isUpdateVenueValid(UpdateVenueRequest request) throws ApiValidationException {
        if (request.getVenueId() == null) {
            validationException.setErrorMessage("venueId is required");
            throw validationException;
        }
        isCreateVenueReqValid(request);

        return true;
    }

    public boolean isDeleteVenueValid(Integer venueId) throws ApiValidationException {
        if (venueId == null) {
            validationException.setErrorMessage("venueId is required");
            throw validationException;
        }
        return true;
    }

}
