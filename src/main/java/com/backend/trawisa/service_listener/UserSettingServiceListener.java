package com.backend.trawisa.service_listener;

import com.app.base.project.utils.apiResponse.ApiResponse;
import com.backend.trawisa.model.request.UpdateSettingRequest;

public interface UserSettingServiceListener {
    ApiResponse getUserSettingDetail();

    ApiResponse updateUserSetting(UpdateSettingRequest settingRequest);
}
