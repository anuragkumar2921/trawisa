package com.backend.trawisa.model.response.teams;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TeamDataResponse {
    private int id;
    private String name;
    private String description;
    private String teamImg;
    private boolean notification;
    private List<PlayerUserData> playersInTeam;
}
