package com.backend.trawisa.controller.v1;


import com.app.base.project.utils.Print;
import com.app.base.project.utils.apiResponse.ApiResponse;
import com.app.base.project.utils.apiResponse.ResponseHandler;
import com.backend.trawisa.exception.ApiValidationException;
import com.backend.trawisa.model.request.UpdateSettingRequest;
import com.backend.trawisa.service.UserSettingServiceImpl;
import com.backend.trawisa.utils.validation.SettingValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.backend.trawisa.constant.ApiConstant.*;

@RestController
@RequestMapping(SETTING)
public class SettingController {

    UserSettingServiceImpl userSettingService;

    SettingValidation settingValidation;

    @Autowired
    public SettingController(UserSettingServiceImpl userSettingService, SettingValidation settingValidation) {
        this.userSettingService = userSettingService;
        this.settingValidation = settingValidation;
    }

    @GetMapping(GET_SETTING_DETAIL)
    public ResponseEntity<ApiResponse> getSettingDetail() {
        ApiResponse apiResponse = this.userSettingService.getUserSettingDetail();
        return ResponseHandler.sendResponse(apiResponse);
    }

    @PostMapping(UPDATE_SETTING)
    public ResponseEntity<ApiResponse> updateUserSetting(@RequestBody UpdateSettingRequest settingsRequest) throws ApiValidationException {
        settingValidation.isUpdateSettingRequestValid(settingsRequest);
        ApiResponse apiResponse = this.userSettingService.updateUserSetting(settingsRequest);
        return ResponseHandler.sendResponse(apiResponse);
    }
}

