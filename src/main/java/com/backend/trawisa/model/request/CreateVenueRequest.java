package com.backend.trawisa.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateVenueRequest {
    @JsonProperty("name")
    @NotBlank( message = "{nameErr}")
    private String name;

    @JsonProperty("street")
    @NotBlank( message = "{streetErr}")
    private String street;

    @JsonProperty("houseNumber")
    @NotBlank( message = "{houseNumberErr}")
    private String houseNumber;

    @JsonProperty("city")
    @NotBlank( message = "{cityErr}")
    private String city;

    @JsonProperty("zipCode")
    @NotBlank( message = "{zipCodeErr}")
    private String zipCode;

    @JsonProperty("isLookingForTeam")
    @NotNull( message = "{isLookingForTeamErr}")
    private Boolean isLookingForTeam;

    @JsonProperty("website")
    @NotBlank( message = "{websiteErr}")
    private String website;

    @JsonProperty("description")
    @NotBlank( message = "{descriptionErr}")
    private String description;

    @JsonProperty("steelDart")
    @NotNull( message = "{steelDartErr}")
    private Integer steelDart;

    @JsonProperty("eDart")
    @NotNull( message = "{nameErr}")
    private Integer eDart;

    @JsonProperty("latLong")
    @NotBlank( message = "{latLongErr}")
    private String  latLong;

}
