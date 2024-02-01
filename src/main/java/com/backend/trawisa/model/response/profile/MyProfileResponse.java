package com.backend.trawisa.model.response.profile;

import com.backend.trawisa.model.enumtype.Gender;
import lombok.Data;

@Data
public class MyProfileResponse {
    private int id;
    private String name;
    private String userNames;
    private String email;
    private String profileImage;
    private Gender gender;
    private String classes;
    private int zipCode;
    private String address;
    private String description;
}
