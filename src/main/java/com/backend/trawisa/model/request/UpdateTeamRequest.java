package com.backend.trawisa.model.request;

import lombok.Data;

@Data
public class UpdateTeamRequest {
    private Integer teamId;
    private String name;
    private String description;
}
