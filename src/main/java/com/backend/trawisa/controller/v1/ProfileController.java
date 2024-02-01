package com.backend.trawisa.controller.v1;

import com.app.base.project.utils.apiResponse.ApiResponse;
import com.app.base.project.utils.apiResponse.ResponseHandler;
import com.backend.trawisa.exception.ApiValidationException;
import com.backend.trawisa.model.request.CreateProfileRequest;
import com.backend.trawisa.service.ProfileServiceImpl;
import com.backend.trawisa.utils.validation.AuthValidation;
import com.backend.trawisa.utils.validation.ProfileValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.backend.trawisa.constant.ApiConstant.*;

@RestController
@RequestMapping(PROFILE)
public class ProfileController {

    ProfileServiceImpl profileService;
    private final AuthValidation authValidation;
    private final ProfileValidation profileValidation;


    @Autowired
    public ProfileController(ProfileServiceImpl profileService, AuthValidation authValidation, ProfileValidation profileValidation) {
        this.profileService = profileService;
        this.authValidation = authValidation;
        this.profileValidation = profileValidation;
    }

    @PostMapping(CREATE_PROFILE)
    public ResponseEntity<ApiResponse> createProfile(
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            @ModelAttribute CreateProfileRequest request) throws ApiValidationException {
        authValidation.isCreateProfileValid(request);
        ApiResponse apiResponse = profileService.createProfile(profileImage, request);

        return ResponseHandler.sendResponse(apiResponse);
    }

    @GetMapping(MY_PROFILE)
    public ResponseEntity<ApiResponse> myProfile() {
        ApiResponse apiResponse = profileService.myProfile();
        return ResponseHandler.sendResponse(apiResponse);

    }

    @PostMapping(UPDATE_PROFILE)
    public ResponseEntity<ApiResponse> updateProfile(
            @Valid @RequestBody CreateProfileRequest profileRequest
    ) throws ApiValidationException {
        authValidation.isCreateProfileValid(profileRequest);
        ApiResponse apiResponse = profileService.editProfile(profileRequest);
        return ResponseHandler.sendResponse(apiResponse);
    }

    @DeleteMapping(DELETE_PROFILE)
    public ResponseEntity<ApiResponse> deleteProfile() {
        ApiResponse apiResponse = profileService.deleteProfile();
        return ResponseHandler.sendResponse(apiResponse);
    }

    @DeleteMapping(DELETE_PROFILE_IMG)
    public ResponseEntity<ApiResponse> deleteProfileImg() {
        ApiResponse apiResponse = profileService.deleteProfileImg();
        return ResponseHandler.sendResponse(apiResponse);
    }

    @PutMapping(UPDATE_PROFILE_IMG)
    public ResponseEntity<ApiResponse> updateProfile(
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage
    ) throws ApiValidationException {
        profileValidation.isProfileUpdateRequestValid(profileImage);
        ApiResponse apiResponse = profileService.updateProfileImage(profileImage);
        return ResponseHandler.sendResponse(apiResponse);
    }
}
