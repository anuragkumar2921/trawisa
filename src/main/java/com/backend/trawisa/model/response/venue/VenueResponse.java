package com.backend.trawisa.model.response.venue;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class VenueResponse {

    private int id;
    private String name;
    private String street;
    private String houseNumber;
    private String city;
    private String zipCode;
    private Boolean isLookingForTeam;
    private String website;
    private String description;
    private String venueImg;
    private int steelDart;
    private int eDart;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

}
