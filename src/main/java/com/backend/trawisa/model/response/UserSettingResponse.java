package com.backend.trawisa.model.response;

import lombok.Data;

@Data
public class UserSettingResponse {
    private boolean playWithTeams;
    private boolean playWithPlayer;
    private Double radius;

}
