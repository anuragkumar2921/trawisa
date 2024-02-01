package com.backend.trawisa.service_listener;

import com.app.base.project.utils.apiResponse.ApiResponse;
import com.backend.trawisa.model.request.*;
import com.backend.trawisa.model.request.ko_round.KnockOutRoundRequest;

public interface TournamentServiceListener {
     ApiResponse createTournament(CreateTournamentRequest request);
    ApiResponse createEveryoneVsEveryoneRound(EveryoneVsEveryoneRequest request);
    ApiResponse createKnockOutRound(KnockOutRoundRequest request);
    ApiResponse createRoundGroupRound(RoundGroupRequest request);
    ApiResponse createScratchRound(ScratchRoundRequest request);
    ApiResponse createGroupStageRound(GroupStageRoundRequest request);

}
