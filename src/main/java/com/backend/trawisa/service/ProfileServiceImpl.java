package com.backend.trawisa.service;

import com.app.base.project.constant.BaseFinalConstant;
import com.app.base.project.utils.MultiLangMessage;
import com.app.base.project.utils.Utils;
import com.app.base.project.utils.apiResponse.ApiDataResponse;
import com.app.base.project.utils.apiResponse.ApiResponse;
import com.backend.trawisa.model.enumtype.Gender;
import com.backend.trawisa.model.request.CreateProfileRequest;
import com.backend.trawisa.model.response.profile.MyProfileResponse;
import com.backend.trawisa.model.entity.UserEntity;
import com.backend.trawisa.repositories.AuthRepo;
import com.backend.trawisa.security.jwt.JwtUtils;
import com.backend.trawisa.service_listener.ProfileServiceListener;
import com.backend.trawisa.utils.CommonApiUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfileServiceImpl implements ProfileServiceListener {

    private final AuthRepo authRepo;
    @Qualifier("getApiDataResponse")
    private final ApiDataResponse apiDataResponse;
    @Qualifier("getApiResponse")
    private final ApiResponse apiResponse;
    private final MultiLangMessage langMessage;
    private final CommonApiUtils apiUtils;

    private final JwtUtils jwtUtils;

    private final FileServiceImpl fileService;

    private final ModelMapper modelMapper;
    private final String requestSuccessMsg;

    public ProfileServiceImpl(AuthRepo authRepo, ApiDataResponse apiDataResponse, ApiResponse apiResponse, MultiLangMessage langMessage, CommonApiUtils apiUtils, JwtUtils jwtUtils, FileServiceImpl fileService, ModelMapper modelMapper) {
        this.authRepo = authRepo;
        this.apiDataResponse = apiDataResponse;
        this.apiResponse = apiResponse;
        this.langMessage = langMessage;
        this.apiUtils = apiUtils;
        this.jwtUtils = jwtUtils;
        this.fileService = fileService;
        this.modelMapper = modelMapper;
        this.requestSuccessMsg = this.langMessage.getLocalizeMessage("requestSuccess");
    }

    @Override
    public ApiResponse createProfile(MultipartFile profileImg, CreateProfileRequest request) {
        UserEntity userEntity = jwtUtils.getCurrentUser();
        boolean userNameExist = authRepo.findByUserNames(request.getUserName()).isPresent();

        if (userNameExist) {
            apiResponse.setErrorResponse(langMessage.getLocalizeMessage("uniqueUserNameErr"), HttpStatus.IM_USED.value());
            return apiResponse;
        }


        if (profileImg != null) {
            String fileName = fileService.uploadFileToS3(profileImg, BaseFinalConstant.IMAGES_FOLDER.PROFILE);
            userEntity.setProfileImage(fileName);
        }
        userEntity.setName(request.getName());
        userEntity.setUserNames(request.getUserName());
        userEntity.setGender(Gender.valueOf(request.getGender()));
        userEntity.setZipCode(request.getPostalCode());
        userEntity.setLocation(request.getAddress());
        userEntity.setIsProfileComplete(true);

        if (Utils.isNN(request.getDesc()))
            userEntity.setDescription(request.getDesc());

        authRepo.save(userEntity);
        apiResponse.setMessageResponse(langMessage.getLocalizeMessage("profileCreatedSuccess"), HttpStatus.CREATED.value());

        return apiResponse;
    }

    @Override
    public ApiResponse myProfile() {
        UserEntity userEntity = jwtUtils.getCurrentUser();
        MyProfileResponse myProfileResponse = this.modelMapper.map(userEntity, MyProfileResponse.class);
        myProfileResponse.setAddress(userEntity.getLocation());

        apiDataResponse.setSuccessResponse(requestSuccessMsg, myProfileResponse, HttpStatus.OK.value());

        return apiDataResponse;
    }

    @Override
    public ApiResponse editProfile(CreateProfileRequest profileRequest) {
        UserEntity userEntity = jwtUtils.getCurrentUser();

        boolean userNameExist = authRepo.findByUserNames(profileRequest.getUserName()).isPresent();

        if (!profileRequest.getUserName().equals(userEntity.getUserNames()) && userNameExist) {
            apiResponse.setErrorResponse(langMessage.getLocalizeMessage("uniqueUserNameErr"), HttpStatus.IM_USED.value());
            return apiResponse;
        }

        userEntity.setName(profileRequest.getName());
        userEntity.setUserNames(profileRequest.getUserName());
        userEntity.setGender(Gender.valueOf(profileRequest.getGender()));
        userEntity.setZipCode(profileRequest.getPostalCode());
        userEntity.setLocation(profileRequest.getAddress());
        //Add class
        userEntity.setDescription(profileRequest.getDesc());
        this.authRepo.save(userEntity);
        apiResponse.setMessageResponse("Profile updated successfully ", HttpStatus.CREATED.value());
        return apiResponse;
    }

    @Override
    public ApiResponse deleteProfile() {
        UserEntity userEntity = jwtUtils.getCurrentUser();
        if (userEntity.getProfileImage() != null)
            fileService.deleteFileFromS3(userEntity.getProfileImage());
        this.authRepo.delete(userEntity);
        apiResponse.setMessageResponse("Profile Deleted successfully", HttpStatus.CREATED.value());
        return apiResponse;
    }

    @Override
    public ApiResponse deleteProfileImg() {
        UserEntity userEntity = jwtUtils.getCurrentUser();
        if (userEntity.getProfileImage() != null)
            fileService.deleteFileFromS3(userEntity.getProfileImage());
        userEntity.setProfileImage(null);
        this.authRepo.save(userEntity);
        apiResponse.setMessageResponse("Profile image deleted successfully", HttpStatus.CREATED.value());
        return apiResponse;
    }

    @Override
    public ApiResponse getProfileImage() {
        return null;
    }

    @Override
    public ApiResponse updateProfileImage(MultipartFile profileImg) {
        UserEntity userEntity = jwtUtils.getCurrentUser();

        if (userEntity.getProfileImage() != null)
            fileService.deleteFileFromS3(userEntity.getProfileImage());
        String fileName = fileService.uploadFileToS3(profileImg, BaseFinalConstant.IMAGES_FOLDER.PROFILE);
        userEntity.setProfileImage(fileName);
        authRepo.save(userEntity);
        apiResponse.setMessageResponse("Profile image uploaded successfully", HttpStatus.CREATED.value());
        return apiResponse;
    }


}
