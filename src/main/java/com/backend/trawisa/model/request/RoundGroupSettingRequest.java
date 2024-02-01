package com.backend.trawisa.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class RoundGroupSettingRequest {

    @JsonProperty("title")
    @NotNull(message = "{titleErr}")
    private String title;

    @JsonProperty("totalDartPoint")
    @NotNull(message = "{totalDartPointNullErr}")
    private Integer totalDartPoint;

    @JsonProperty("ringInType")
    @NotNull(message = "{ringInTypeNullErr}")
    private Integer ringInType;

    @JsonProperty("ringOutType")
    @NotNull(message = "{ringOutTypeNullErr}")
    private Integer ringOutType;

    @JsonProperty("remainingPoint")
    @NotNull(message = "{remainingPointErr}")
    private String remainingPoint;


}
