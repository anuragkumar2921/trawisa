package com.backend.trawisa.controller.v1;

import com.app.base.project.utils.Print;
import com.app.base.project.utils.apiResponse.ApiResponse;
import com.app.base.project.utils.apiResponse.ResponseHandler;
import com.backend.trawisa.exception.ApiValidationException;
import com.backend.trawisa.model.request.*;
import com.backend.trawisa.service.TeamServiceImpl;
import com.backend.trawisa.utils.validation.TeamValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.app.base.project.constant.BaseFinalConstant.PAGINATION.*;
import static com.backend.trawisa.constant.ApiConstant.*;

@RestController
@RequestMapping(TEAMS)
public class TeamController {

    private final TeamServiceImpl teamService;
    private final TeamValidation teamValidation;

    @Autowired
    public TeamController(TeamServiceImpl teamService, TeamValidation teamValidation) {
        this.teamService = teamService;
        this.teamValidation = teamValidation;
    }

    @PostMapping(CREATE_TEAMS)
    public ResponseEntity<ApiResponse> createTeam(MultipartFile teamImg, @ModelAttribute CreateTeamRequest teamRequest) throws ApiValidationException {

        teamValidation.isCreateTeamReqValid(teamRequest);
        ApiResponse apiResponse = this.teamService.createTeam(teamImg, teamRequest);
        return ResponseHandler.sendResponse(apiResponse);
    }

    @GetMapping(GET_TEAMS)
    public ResponseEntity<ApiResponse> getTeamList(
            @RequestParam(value = "sortBy", defaultValue = SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = SORT_DIRECTION, required = false) String sortDirection,
            @RequestParam(value = "search", defaultValue = "", required = false) String search
    ) {
        ApiResponse apiResponse = this.teamService.getTeamList(sortBy, sortDirection, search);
        return ResponseHandler.sendResponse(apiResponse);
    }

    @GetMapping(GET_TEAMS_DETAIL)
    public ResponseEntity<ApiResponse> getTeamDetail(
            @RequestParam(value = "teamId") int teamId
    ) {
        ApiResponse apiResponse = this.teamService.getTeamDetail(teamId);
        return ResponseHandler.sendResponse(apiResponse);
    }

    @PutMapping(UPDATE_TEAMS_DETAIL)
    public ResponseEntity<ApiResponse> updateTeamDetail(@RequestBody UpdateTeamRequest teamRequest) throws ApiValidationException {


        this.teamValidation.isUpdateTeamReqValid(teamRequest);
        ApiResponse apiResponse = this.teamService.updateTeamDetail(teamRequest);
        return ResponseHandler.sendResponse(apiResponse);
    }

    @PutMapping(UPDATE_TEAMS_Img)
    public ResponseEntity<ApiResponse> updateTeamImg(
            @RequestParam(value = "teamImg", required = false) MultipartFile teamImg,
            @RequestParam(value = "teamId", required = false) Integer teamId
    ) {
        ApiResponse apiResponse = this.teamService.updateTeamImg(teamImg, teamId);
        return ResponseHandler.sendResponse(apiResponse);
    }

    @GetMapping(GET_ALL_USER)
    public ResponseEntity<ApiResponse> getAllUser(
            @RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = SORT_DIRECTION, required = false) String sortDirection
    ) {
        ApiResponse apiResponse = this.teamService.getAllUser(pageNumber, pageSize, sortBy, sortDirection);
        return ResponseHandler.sendResponse(apiResponse);
    }


    @PostMapping(ADD_PLAYER_IN_TEAM)
    public ResponseEntity<ApiResponse> addPlayerInTeam(@RequestBody AddPlayerInTeamRequest request) throws ApiValidationException {
        this.teamValidation.isAddPlayerInTeamReqValid(request);
        ApiResponse apiResponse = this.teamService.addPlayerInTeam(request);
        return ResponseHandler.sendResponse(apiResponse);
    }

    @PostMapping(SEND_TEAM_REQUEST)
    public ResponseEntity<ApiResponse> addPlayerInTeam(@RequestBody SendTeamReqPlayerRequest request) throws ApiValidationException {
        this.teamValidation.isTeamReqToPlayerReqValid(request);
        ApiResponse apiResponse = this.teamService.sendTeamReqToPlayer(request);
        return ResponseHandler.sendResponse(apiResponse);
    }

    @DeleteMapping(REJECT_TEAM_REQUEST)
    public ResponseEntity<ApiResponse> rejectTeamRequest(@RequestBody RejectTeamReqPlayerRequest request) throws ApiValidationException {
        ApiResponse apiResponse = this.teamService.rejectTeamReqToPlayer(request);
        return ResponseHandler.sendResponse(apiResponse);
    }


    @PostMapping(MANAGE_TEAM_NOTIFICATION)
    public ResponseEntity<ApiResponse> manageTeamNotification(@RequestBody ManageTeamNotificationRequest request) throws ApiValidationException {
        this.teamValidation.isManageTeamReqValid(request);
        ApiResponse apiResponse = this.teamService.manageTeamNotification(request);
        return ResponseHandler.sendResponse(apiResponse);
    }

    @DeleteMapping(DELETE_TEAM + "/{teamId}")
    public ResponseEntity<ApiResponse> deleteTeam(@PathVariable(name = "teamId") Integer teamId) {
        ApiResponse apiResponse = this.teamService.deleteTeam(teamId);
        return ResponseHandler.sendResponse(apiResponse);
    }
}
