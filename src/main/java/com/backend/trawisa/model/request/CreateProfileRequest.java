package com.backend.trawisa.model.request;

import lombok.Data;

@Data
public class CreateProfileRequest {

    private String name;


    private String userName;


    private String gender;


    private Integer postalCode;


    private String address;


    private String classes;


    private String desc;
}
