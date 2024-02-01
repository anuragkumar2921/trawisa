package com.backend.trawisa.service;

import com.app.base.project.exception.ResourceNotFoundException;
import com.app.base.project.utils.LocalizationUtils;
import com.app.base.project.utils.MultiLangMessage;
import com.app.base.project.utils.Print;
import com.app.base.project.utils.apiResponse.ApiDataResponse;
import com.app.base.project.utils.apiResponse.ApiResponse;
import com.backend.trawisa.model.mapper.TournamentMapper;
import com.backend.trawisa.model.request.*;
import com.backend.trawisa.model.request.ko_round.KnockOutRoundRequest;
import com.backend.trawisa.model.entity.*;
import com.backend.trawisa.repositories.*;
import com.backend.trawisa.security.jwt.JwtUtils;
import com.backend.trawisa.service_listener.TournamentServiceListener;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.backend.trawisa.utils.MyUtils.checkValidUser;

@Service
public class TournamentServiceImpl implements TournamentServiceListener {

    private final TournamentRepo tournamentRepo;
    private final VenueRepo venueRepo;
    private final KoRoundRepo koRoundRepo;

    private final ScratchRoundRepo scratchRoundRepo;
    private final GroupStageRoundRepo groupStageRoundRepo;
    private final EveryoneVsEveryoneRepo everyoneRepo;
    private final RoundGroupRepo roundGroupRepo;
    @Qualifier("getApiDataResponse")
    private final ApiDataResponse apiDataResponse;
    @Qualifier("getApiResponse")
    private final ApiResponse apiResponse;
    private final MultiLangMessage langMessage;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;
    private final TournamentMapper tournamentMapper;
    private final String requestSuccessMsg;

    private final LocalizationUtils localization;

    @Autowired
    public TournamentServiceImpl(TournamentRepo tournamentRepo, ApiDataResponse apiDataResponse, ApiResponse apiResponse, MultiLangMessage langMessage,
                                 JwtUtils jwtUtils, ModelMapper modelMapper, EveryoneVsEveryoneRepo everyoneRepo,
                                 VenueRepo venueRepo, KoRoundRepo koRoundRepo, GroupStageRoundRepo groupStageRoundRepo,
                                 ScratchRoundRepo scratchRoundRepo, TournamentMapper tournamentMapper,
                                 LocalizationUtils localization,RoundGroupRepo roundGroupRepo) {
        this.tournamentRepo = tournamentRepo;
        this.apiDataResponse = apiDataResponse;
        this.apiResponse = apiResponse;
        this.langMessage = langMessage;
        this.jwtUtils = jwtUtils;
        this.modelMapper = modelMapper;
        this.tournamentMapper = tournamentMapper;
        this.everyoneRepo = everyoneRepo;
        this.venueRepo = venueRepo;
        this.koRoundRepo = koRoundRepo;
        this.groupStageRoundRepo = groupStageRoundRepo;
        this.scratchRoundRepo = scratchRoundRepo;
        this.roundGroupRepo = roundGroupRepo;
        this.localization = localization;
        this.requestSuccessMsg = this.langMessage.getLocalizeMessage("requestSuccess");
        ;
    }

    @Override
    public ApiResponse createTournament(CreateTournamentRequest request) {
        UserEntity user = jwtUtils.getCurrentUser();

        Optional<TournamentEntity> isTournamentNameExit = this.tournamentRepo.findByTournamentName(request.getTournamentName());

        if (isTournamentNameExit.isPresent()) {
            apiResponse.setMessageResponse(localization.getMessage("tournamentAlreadyUsedErr"), HttpStatus.IM_USED.value());
            return apiResponse;
        }
        TournamentEntity tournamentEntity = this.tournamentMapper.toTournamentEntity(request);
        Print.log("tournamentEntity "+tournamentEntity);
        tournamentEntity.setUserEntity(user);
        tournamentRepo.save(tournamentEntity);
        apiResponse.setMessageResponse(requestSuccessMsg, HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public ApiResponse createEveryoneVsEveryoneRound(EveryoneVsEveryoneRequest request) {
        TournamentEntity tournament = getTournamentInfo(request.getTournamentId());
        checkValidUser(tournament.getUserEntity(), jwtUtils.getCurrentUser());
        VenueEntity venue = getVenueInfo(request.getVenueId());

        boolean isTournamentAlreadyCreated = this.everyoneRepo.findByTournamentEntity(tournament).isPresent();
        if (isTournamentAlreadyCreated) {
            apiResponse.setErrorResponse(localization.getMessage("tournamentAlreadyUsedErr"), HttpStatus.BAD_REQUEST.value());
            return apiResponse;
        }
        EveryoneVsEveryoneEntity everyoneEntity = this.tournamentMapper.toEveryoneEntity(request, venue, tournament);
        Print.log("everyoneEntity "+everyoneEntity);
        this.everyoneRepo.save(everyoneEntity);
        apiResponse.setMessageResponse(requestSuccessMsg, HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public ApiResponse createKnockOutRound(KnockOutRoundRequest request) {
        TournamentEntity tournament = getTournamentInfo(request.getTournamentId());
        checkValidUser(tournament.getUserEntity(), jwtUtils.getCurrentUser());
        VenueEntity venue = getVenueInfo(request.getVenueId());

        boolean isTournamentAlreadyCreated = this.koRoundRepo.findByTournamentEntity(tournament).isPresent();
        if (isTournamentAlreadyCreated) {
            apiResponse.setErrorResponse("Tournament Id is already used", HttpStatus.BAD_REQUEST.value());
            return apiResponse;
        }
        KORoundEntity koRoundEntity = this.tournamentMapper.toKORoundEntity(request, venue, tournament);
        Print.log("koRoundEntity "+koRoundEntity);
        this.koRoundRepo.save(koRoundEntity);
        this.apiResponse.setMessageResponse(requestSuccessMsg, HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public ApiResponse createRoundGroupRound(RoundGroupRequest request) {
        TournamentEntity tournament = getTournamentInfo(request.getTournamentId());
        checkValidUser(tournament.getUserEntity(), jwtUtils.getCurrentUser());
        VenueEntity venue = getVenueInfo(request.getVenueId());

        boolean isTournamentAlreadyCreated = this.roundGroupRepo.findByTournamentEntity(tournament).isPresent();
        if (isTournamentAlreadyCreated) {
            apiResponse.setErrorResponse("Tournament Id is already used", HttpStatus.BAD_REQUEST.value());
            return apiResponse;
        }

        RoundGroupEntity roundGroup  = this.tournamentMapper.toRoundGroupEntity(request, tournament,venue);

        Print.log("RoundGroupEntity ==> "+roundGroup);
        this.roundGroupRepo.save(roundGroup);
        this.apiResponse.setMessageResponse(requestSuccessMsg, HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public ApiResponse createScratchRound(ScratchRoundRequest request) {
        TournamentEntity tournament = getTournamentInfo(request.getTournamentId());
        checkValidUser(tournament.getUserEntity(), jwtUtils.getCurrentUser());
        VenueEntity venue = getVenueInfo(request.getVenueId());
        boolean isTournamentAlreadyCreated = this.scratchRoundRepo.findByTournamentEntity(tournament).isPresent();
        if (isTournamentAlreadyCreated) {
            apiResponse.setErrorResponse("Tournament Id is already used", HttpStatus.BAD_REQUEST.value());
            return apiResponse;
        }
        ScratchRoundEntity scratchRoundEntity = tournamentMapper.toScratchRoundEntity(request, venue, tournament);
        Print.log("scratchRoundEntity "+scratchRoundEntity);
        this.scratchRoundRepo.save(scratchRoundEntity);
        this.apiResponse.setMessageResponse(requestSuccessMsg, HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public ApiResponse createGroupStageRound(GroupStageRoundRequest request) {
        TournamentEntity tournament = getTournamentInfo(request.getTournamentId());
        checkValidUser(tournament.getUserEntity(), jwtUtils.getCurrentUser());
        VenueEntity venue = getVenueInfo(request.getVenueId());
        boolean isTournamentAlreadyCreated = this.groupStageRoundRepo.findByTournamentEntity(tournament).isPresent();
        if (isTournamentAlreadyCreated) {
            apiResponse.setErrorResponse("Tournament Id is already used", HttpStatus.BAD_REQUEST.value());
            return apiResponse;
        }
        GroupStageRoundEntity groupStageRoundEntity = this.tournamentMapper.toGroupStageRoundEntity(request, tournament, venue);
        Print.log("groupStageRoundEntity "+groupStageRoundEntity);
        this.groupStageRoundRepo.save(groupStageRoundEntity);

        apiResponse.setMessageResponse(requestSuccessMsg, HttpStatus.OK.value());
        return apiResponse;

    }

    private TournamentEntity getTournamentInfo(int tournamentId) {
        return this.tournamentRepo.findById(tournamentId).orElseThrow(() -> new ResourceNotFoundException("Tournament Id", true));
    }

    private VenueEntity getVenueInfo(int venueId) {
        return this.venueRepo.findById(venueId).orElseThrow(() -> new ResourceNotFoundException("Venue", true));
    }

}
