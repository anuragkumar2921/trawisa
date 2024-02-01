package com.backend.trawisa.model.request;

import lombok.Data;

import java.util.List;

@Data
public class SendTeamReqPlayerRequest {
    private Integer teamId;
    private List<Integer> playerId;
}
