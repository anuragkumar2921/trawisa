package com.backend.trawisa.service_listener;

import com.app.base.project.utils.apiResponse.ApiResponse;
import com.backend.trawisa.model.request.*;
import org.springframework.web.multipart.MultipartFile;

public interface TeamServiceListener {
    ApiResponse createTeam(MultipartFile teamImg, CreateTeamRequest createTeamRequest);

    ApiResponse getTeamList(String sortBy, String sortDirection, String searchKeyword);

    ApiResponse getTeamDetail(int teamId);
    ApiResponse updateTeamDetail(UpdateTeamRequest teamRequest);

    ApiResponse updateTeamImg (MultipartFile teamImg,Integer teamId);
    ApiResponse getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

    ApiResponse addPlayerInTeam(AddPlayerInTeamRequest request);
    ApiResponse sendTeamReqToPlayer(SendTeamReqPlayerRequest request);
    ApiResponse rejectTeamReqToPlayer(RejectTeamReqPlayerRequest request);

    ApiResponse manageTeamNotification(ManageTeamNotificationRequest request);

    ApiResponse deleteTeam(Integer teamId);

}
