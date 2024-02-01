package com.backend.trawisa.service;

import com.app.base.project.exception.ResourceNotFoundException;
import com.app.base.project.utils.MultiLangMessage;
import com.app.base.project.utils.apiResponse.ApiDataResponse;
import com.app.base.project.utils.apiResponse.ApiResponse;
import com.backend.trawisa.model.request.*;
import com.backend.trawisa.model.response.teams.CreateTeamResponse;
import com.backend.trawisa.model.response.teams.PlayerUserData;
import com.backend.trawisa.model.response.teams.TeamDataResponse;
import com.backend.trawisa.model.response.teams.UserListData;
import com.backend.trawisa.model.entity.PlayerInTeamEntity;
import com.backend.trawisa.model.entity.TeamRequestToPlayerEntity;
import com.backend.trawisa.model.entity.TeamsEntity;
import com.backend.trawisa.model.entity.UserEntity;
import com.backend.trawisa.repositories.AuthRepo;
import com.backend.trawisa.repositories.PlayerInTeamRepo;
import com.backend.trawisa.repositories.TeamRepo;
import com.backend.trawisa.repositories.TeamRequestToPlayerRepo;
import com.backend.trawisa.security.jwt.JwtUtils;
import com.backend.trawisa.service_listener.TeamServiceListener;
import com.backend.trawisa.utils.CommonApiUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.app.base.project.constant.BaseFinalConstant.IMAGES_FOLDER.TEAMS;
import static com.app.base.project.utils.Utils.isNullOrEmpty;

@Service
public class TeamServiceImpl implements TeamServiceListener {

    private final TeamRepo teamRepo;
    @Qualifier("getApiDataResponse")
    private final ApiDataResponse apiDataResponse;
    @Qualifier("getApiResponse")
    private final ApiResponse apiResponse;

    private final MultiLangMessage langMessage;

    private final CommonApiUtils apiUtils;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;
    private final String requestSuccessMsg;

    private final FileServiceImpl fileService;

    private final AuthRepo authRepo;
    private final PlayerInTeamRepo playerInTeamRepo;
    private final TeamRequestToPlayerRepo teamRequestToPlayerRepo;

    public TeamServiceImpl(TeamRepo teamRepo, ApiDataResponse apiDataResponse, ApiResponse apiResponse,
                           MultiLangMessage langMessage, CommonApiUtils apiUtils, JwtUtils jwtUtils, ModelMapper modelMapper, FileServiceImpl fileService,
                           AuthRepo authRepo, PlayerInTeamRepo playerInTeamRepo, TeamRequestToPlayerRepo teamRequestToPlayerRepo) {
        this.teamRepo = teamRepo;
        this.apiDataResponse = apiDataResponse;
        this.apiResponse = apiResponse;
        this.langMessage = langMessage;
        this.apiUtils = apiUtils;
        this.jwtUtils = jwtUtils;
        this.modelMapper = modelMapper;
        this.requestSuccessMsg = this.langMessage.getLocalizeMessage("requestSuccess");
        this.fileService = fileService;
        this.authRepo = authRepo;
        this.playerInTeamRepo = playerInTeamRepo;
        this.teamRequestToPlayerRepo = teamRequestToPlayerRepo;
    }

    @Override
    public ApiResponse createTeam(MultipartFile teamImgFile, CreateTeamRequest createTeamRequest) {

        UserEntity userEntity = jwtUtils.getCurrentUser();
        boolean isTeamNameExist = teamRepo.findByName(createTeamRequest.getName()).isPresent();

        if (isTeamNameExist) {
            apiResponse.setErrorResponse("Team name already exist", HttpStatus.IM_USED.value());
            return apiResponse;
        }

        TeamsEntity teamsEntity = new TeamsEntity();

        if (teamImgFile != null) {
            String img_folder = TEAMS + "/" + userEntity.getId();
            String teamImg = fileService.uploadFileToS3(teamImgFile, img_folder);
            teamsEntity.setTeamImg(teamImg);
        }

        teamsEntity.setName(createTeamRequest.getName());
        teamsEntity.setDescription(createTeamRequest.getName());
        teamsEntity.setUserEntity(userEntity);
        teamRepo.save(teamsEntity);

        CreateTeamResponse createTeamResponse = new CreateTeamResponse();
        createTeamResponse.setTeamId(teamsEntity.getId());
        apiDataResponse.setSuccessResponse("Team created successfully ", createTeamResponse, HttpStatus.OK.value());

        return apiDataResponse;

    }

    @Override
    public ApiResponse getTeamList(String sortBy, String sortDirection, String searchKeyword) {
        UserEntity userInfo = jwtUtils.getCurrentUser();
        Sort sort;
        if (sortDirection.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, sort); // Fetch all records without pagination
        Page<TeamsEntity> teamPage;
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            teamPage = teamRepo.findByUserEntityAndNameContaining(userInfo, searchKeyword, pageable);
        } else {
            teamPage = teamRepo.findAllByUserEntity(userInfo, pageable);
        }

        List<TeamsEntity> teamsEntityList = teamPage.getContent();
        List<TeamDataResponse> teamList = teamsEntityList.stream().map((teamsEntity -> this.modelMapper.map(teamsEntity, TeamDataResponse.class))).toList();


        apiDataResponse.setSuccessResponse(requestSuccessMsg, teamList, HttpStatus.OK.value());


        return apiDataResponse;
    }

    @Override
    public ApiResponse getTeamDetail(int teamId) {
        UserEntity userInfo = jwtUtils.getCurrentUser();
        TeamsEntity teamsEntity = teamRepo.findByIdAndUserEntity(teamId, userInfo).orElseThrow(() -> new ResourceNotFoundException("Team Detail", true));
        TeamDataResponse teamData = this.modelMapper.map(teamsEntity, TeamDataResponse.class);
        List<PlayerUserData> playerUserDataList = teamsEntity.getPlayersInTeam().stream()
                .map(player -> modelMapper.map(player.getUserEntity(), PlayerUserData.class))
                .toList();
        teamData.setPlayersInTeam(playerUserDataList);
        apiDataResponse.setSuccessResponse(requestSuccessMsg, teamData, HttpStatus.OK.value());
        return apiDataResponse;
    }

    @Override
    public ApiResponse updateTeamDetail(UpdateTeamRequest teamRequest) {
        TeamsEntity teamsEntity = getTeamDetails(teamRequest.getTeamId());
        teamsEntity.setName(teamRequest.getName());
        teamsEntity.setDescription(teamRequest.getDescription());
        this.teamRepo.save(teamsEntity);
        apiResponse.setMessageResponse("Team detail updated successfully", HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public ApiResponse updateTeamImg(MultipartFile teamImg, Integer teamId) {

        UserEntity userEntity = jwtUtils.getCurrentUser();
        TeamsEntity teamsEntity = getTeamDetails(teamId);

        if (!isNullOrEmpty(teamsEntity.getTeamImg())) {
            fileService.deleteFileFromS3(teamsEntity.getTeamImg());
        }

        String folderName = TEAMS + "/" + userEntity.getId();
        String teamImgName = fileService.uploadFileToS3(teamImg, folderName);
        teamsEntity.setTeamImg(teamImgName);
        this.teamRepo.save(teamsEntity);
        apiResponse.setMessageResponse("Image updated successfully", HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public ApiResponse getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Sort sort;
        if (sortDirection.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<UserEntity> userListPage = this.authRepo.findAll(pageable);

        List<UserEntity> userEntityList = userListPage.getContent();

        List<UserListData> userList = userEntityList.stream().map((userData) -> this.modelMapper.map(userData, UserListData.class)).toList();

        apiDataResponse.setSuccessResponse(requestSuccessMsg, userList, HttpStatus.OK.value());

        return apiDataResponse;
    }

    @Override
    public ApiResponse addPlayerInTeam(AddPlayerInTeamRequest request) {
        TeamsEntity teamsEntity = getTeamDetails(request.getTeamId());
        if (teamsEntity.getPlayerCount() >= 2) {
            apiResponse.setErrorResponse("Team is full", HttpStatus.BAD_REQUEST.value());
            return apiResponse;
        }
        UserEntity user = jwtUtils.getCurrentUser();
        TeamRequestToPlayerEntity teamRequestToPlayer = this.teamRequestToPlayerRepo.findAllByUserEntity(user).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (teamsEntity.getPlayerCount() == 0) {
            this.teamRequestToPlayerRepo.delete(teamRequestToPlayer);
        } else if (teamsEntity.getPlayerCount() == 1) {
            List<TeamRequestToPlayerEntity> teamRequestToPlayerList = this.teamRequestToPlayerRepo.findAllByTeamsEntity(teamsEntity);
            this.teamRequestToPlayerRepo.deleteAll(teamRequestToPlayerList);
        }

        PlayerInTeamEntity playerInTeamEntity = new PlayerInTeamEntity();
        playerInTeamEntity.setTeamsEntity(teamsEntity);
        playerInTeamEntity.setUserEntity(user);
        this.playerInTeamRepo.save(playerInTeamEntity);

        int playerCount = teamsEntity.getPlayerCount() + 1;
        teamsEntity.setPlayerCount(playerCount);
        this.teamRepo.save(teamsEntity);
        apiResponse.setErrorResponse(requestSuccessMsg, HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public ApiResponse sendTeamReqToPlayer(SendTeamReqPlayerRequest request) {
        TeamsEntity teamsEntity = getTeamDetails(request.getTeamId());

        List<UserEntity> userEntityList = this.authRepo.findAllById(request.getPlayerId());
        List<TeamRequestToPlayerEntity> teamRequestToPlayer = new ArrayList<>();
        for (UserEntity user : userEntityList) {
            teamRequestToPlayer.add(new TeamRequestToPlayerEntity(user, teamsEntity));
        }
        teamRequestToPlayerRepo.saveAll(teamRequestToPlayer);
        apiResponse.setMessageResponse(requestSuccessMsg, HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public ApiResponse rejectTeamReqToPlayer(RejectTeamReqPlayerRequest request) {
        UserEntity userEntity = jwtUtils.getCurrentUser();
        TeamsEntity teamsEntity = getTeamDetails(request.getTeamId());
        TeamRequestToPlayerEntity teamRequestToPlayer = this.teamRequestToPlayerRepo.findByTeamsEntityAndUserEntity(teamsEntity, userEntity)
                .orElseThrow(() -> new ResourceNotFoundException("You have already rejected Team Request"));
        this.teamRequestToPlayerRepo.delete(teamRequestToPlayer);
        this.apiResponse.setMessageResponse(requestSuccessMsg, HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public ApiResponse manageTeamNotification(ManageTeamNotificationRequest request) {
        TeamsEntity teamsEntity = getTeamDetails(request.getTeamId());
        teamsEntity.setNotification(request.getIsNotificationEnable());
        this.teamRepo.save(teamsEntity);
        apiResponse.setMessageResponse(requestSuccessMsg, HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public ApiResponse deleteTeam(Integer teamId) {
        TeamsEntity teamsEntity = getTeamDetails(teamId);
        this.teamRepo.delete(teamsEntity);
        apiResponse.setMessageResponse("Team deleted successfully", HttpStatus.OK.value());
        return apiResponse;
    }

    private TeamsEntity getTeamDetails(int teamId) {
        return teamRepo.findById(teamId).orElseThrow(() -> new ResourceNotFoundException("Team", true));
    }


}
