package com.backend.trawisa.service_listener;

import com.app.base.project.utils.apiResponse.ApiResponse;
import com.backend.trawisa.model.request.CreateProfileRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileServiceListener {

    ApiResponse createProfile(MultipartFile profileImg, CreateProfileRequest createProfileRequest);
    ApiResponse myProfile();

    ApiResponse editProfile(CreateProfileRequest profileRequest);

    ApiResponse deleteProfile();
    ApiResponse deleteProfileImg();
    ApiResponse getProfileImage();

    ApiResponse updateProfileImage(MultipartFile profileImg);
}
