package com.backend.trawisa.constant;

public interface ApiConstant {
    String API_VERSION_1 = "api/v1/";
    String AUTH = API_VERSION_1 + "auth/";
    String REGISTER = "register";
    String RESEND_OTP ="resendOtp";
    String VERIFY_OTP ="verifyOTP";

    String LOGIN ="login";

    String FORGOT_PASSWORD = "forgotPassword";

    String CHANGE_PASSWORD = "changePassword";

    String PROFILE =  API_VERSION_1 + "profile/";
    String CREATE_PROFILE = "createProfile";
    String MY_PROFILE = "myProfile";
    String UPDATE_PROFILE = "updateProfile";
    String DELETE_PROFILE = "deleteProfile";
    String DELETE_PROFILE_IMG = "deleteProfileImg";
    String UPDATE_PROFILE_IMG = "updateProfileImg";

    String SETTING = API_VERSION_1+"setting";
    String GET_SETTING_DETAIL = "getSettingDetail";
    String UPDATE_SETTING="updateSetting";

    String VENUE = API_VERSION_1+"venue";

    String CREATE_VENUE = "createVenue";

    String GET_VENUE_List ="getList";
    String GET_VENUE_DETAIL ="getVenueDetail";
    String UPDATE_VENUE_DETAIL ="updateVenueDetail";
    String UPDATE_VENUE_IMG ="updateVenueImg";
    String DELETE_VENUE ="deleteVenue";
    String DELETE_VENUE_IMG ="deleteVenueImg";

    String TEAMS = API_VERSION_1+"teams";
    String CREATE_TEAMS = "createTeam";
    String GET_TEAMS = "getTeams";
    String GET_TEAMS_DETAIL = "getTeamDetail";
    String UPDATE_TEAMS_DETAIL = "updateTeamDetail";
    String UPDATE_TEAMS_Img = "updateTeamImg";
    String GET_ALL_USER = "getaAllUser";
    String ADD_PLAYER_IN_TEAM = "addPlayerInTeam";
    String SEND_TEAM_REQUEST = "sendTeamRequest";
    String REJECT_TEAM_REQUEST = "rejectTeamRequest";
    String MANAGE_TEAM_NOTIFICATION = "manageTeamNotification";
    String DELETE_TEAM = "deleteTeam";

    String TOURNAMENT = API_VERSION_1 + "tournament/";
    String CREATE_TOURNAMENT = "create";
    String CREATE_EVERYONE_VS_EVERYONE_ROUND= "everyoneRound";
    String GROUP_STAGE= "groupStageRound";
    String ROUND_GROUP= "roundGroup";
    String SCRATCH_GROUP= "scratchGroup";
    String KO_ROUND= "koRound";

    String HOME = API_VERSION_1 + "home/";

    String TOURNAMENT_LIST = "tournamentList";



}
