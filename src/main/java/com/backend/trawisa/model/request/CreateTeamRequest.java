package com.backend.trawisa.model.request;

import lombok.Data;

@Data
public class CreateTeamRequest {
    private String name;
    private String description;
}
