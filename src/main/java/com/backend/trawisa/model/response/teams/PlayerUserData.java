package com.backend.trawisa.model.response.teams;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlayerUserData {
    private int id;
    private String name;
    private String userNames;
    private String email;
    private String profileImage;


}
