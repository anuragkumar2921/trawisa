package com.backend.trawisa.service;

import com.app.base.project.utils.MultiLangMessage;
import com.app.base.project.utils.apiResponse.ApiDataResponse;
import com.app.base.project.utils.apiResponse.ApiResponse;
import com.backend.trawisa.model.request.UpdateSettingRequest;
import com.backend.trawisa.model.response.UserSettingResponse;
import com.backend.trawisa.model.entity.UserEntity;
import com.backend.trawisa.model.entity.UserSettingEntity;
import com.backend.trawisa.repositories.UserSettingRepo;
import com.backend.trawisa.security.jwt.JwtUtils;
import com.backend.trawisa.service_listener.UserSettingServiceListener;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserSettingServiceImpl implements UserSettingServiceListener {


    private final UserSettingRepo userSettingRepo;

    @Qualifier("getApiDataResponse")
    private final ApiDataResponse apiDataResponse;
    @Qualifier("getApiResponse")
    private final ApiResponse apiResponse;

    private final MultiLangMessage langMessage;


    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;
    private final String requestSuccessMsg;

    @Autowired
    public UserSettingServiceImpl(UserSettingRepo userSettingRepo, ApiDataResponse apiDataResponse, ApiResponse apiResponse, MultiLangMessage langMessage, JwtUtils jwtUtils, ModelMapper modelMapper) {
        this.userSettingRepo = userSettingRepo;
        this.apiDataResponse = apiDataResponse;
        this.apiResponse = apiResponse;
        this.langMessage = langMessage;
        this.jwtUtils = jwtUtils;
        this.modelMapper = modelMapper;
        this.requestSuccessMsg = this.langMessage.getLocalizeMessage("requestSuccess");
    }

    @Override
    public ApiResponse getUserSettingDetail() {
        UserEntity user = jwtUtils.getCurrentUser();
        UserSettingEntity userSetting = userSettingRepo.findByUserEntity(user);
        UserSettingResponse userSettingResponse = this.modelMapper.map(userSetting, UserSettingResponse.class);
        apiDataResponse.setSuccessResponse(requestSuccessMsg, userSettingResponse, HttpStatus.OK.value());
        return apiDataResponse;
    }

    @Override
    public ApiResponse updateUserSetting(UpdateSettingRequest settingRequest) {
        UserEntity userEntity = jwtUtils.getCurrentUser();
        UserSettingEntity userSetting = userSettingRepo.findByUserEntity(userEntity);
        userSetting.setRadius(settingRequest.getRadius());
        userSetting.setPlayWithPlayer(settingRequest.getPlayWithPlayer());
        userSetting.setPlayWithTeams(settingRequest.getPlayWithTeams());
        userSettingRepo.save(userSetting);
        UserSettingResponse userSettingResponse = this.modelMapper.map(userSetting, UserSettingResponse.class);
        apiDataResponse.setSuccessResponse(requestSuccessMsg, userSettingResponse, HttpStatus.OK.value());
        return apiDataResponse;
    }
}
