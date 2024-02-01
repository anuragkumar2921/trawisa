package com.backend.trawisa.model.request;


import lombok.Data;

@Data
public class UpdateSettingRequest {
    private Boolean playWithTeams = null;
    private Boolean playWithPlayer = null;
    private Double radius = null;
}
