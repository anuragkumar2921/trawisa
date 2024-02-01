package com.backend.trawisa.controller.v1;

import com.app.base.project.utils.apiResponse.ApiResponse;
import com.app.base.project.utils.apiResponse.ResponseHandler;
import com.backend.trawisa.exception.ApiValidationException;
import com.backend.trawisa.model.request.*;
import com.backend.trawisa.model.request.ko_round.KnockOutRoundRequest;
import com.backend.trawisa.service.TournamentServiceImpl;
import com.backend.trawisa.utils.validation.TournamentValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.backend.trawisa.constant.ApiConstant.*;

@RestController
@RequestMapping(TOURNAMENT)
public class TournamentController {

    private final TournamentServiceImpl tournamentService;

    @Autowired
    public TournamentController(TournamentServiceImpl tournamentService) {
        this.tournamentService = tournamentService;
    }

    @PostMapping(CREATE_TOURNAMENT)
    public ResponseEntity<ApiResponse> createTournament(@Valid @RequestBody CreateTournamentRequest createTournamentRequest) {
        ApiResponse apiResponse = tournamentService.createTournament(createTournamentRequest);
        return ResponseHandler.sendResponse(apiResponse);
    }


    @PostMapping(CREATE_EVERYONE_VS_EVERYONE_ROUND)
    public ResponseEntity<ApiResponse> createEveryoneRound(@Valid @RequestBody EveryoneVsEveryoneRequest request){
        ApiResponse apiResponse = tournamentService.createEveryoneVsEveryoneRound(request);
        return ResponseHandler.sendResponse(apiResponse);
    }


    @PostMapping(KO_ROUND)
    public ResponseEntity<ApiResponse> createKoRound(@Valid @RequestBody KnockOutRoundRequest request) {
        ApiResponse apiResponse = this.tournamentService.createKnockOutRound(request);
        return ResponseHandler.sendResponse(apiResponse);
    }

    @PostMapping(ROUND_GROUP)
    public ResponseEntity<ApiResponse> createRoundGroup(@Valid @RequestBody RoundGroupRequest request) {
        ApiResponse apiResponse = tournamentService.createRoundGroupRound(request);
        return ResponseHandler.sendResponse(apiResponse);
    }


    @PostMapping(GROUP_STAGE)
    public ResponseEntity<ApiResponse> createGroupStageRound(@Valid @RequestBody GroupStageRoundRequest request) {
        ApiResponse apiResponse = tournamentService.createGroupStageRound(request);
        return ResponseHandler.sendResponse(apiResponse);
    }

    @PostMapping(SCRATCH_GROUP)
    public ResponseEntity<ApiResponse> createScratchRound(@Valid @RequestBody ScratchRoundRequest request){
        ApiResponse apiResponse = tournamentService.createScratchRound(request);
        return ResponseHandler.sendResponse(apiResponse);
    }

}
