package com.backend.trawisa.model.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
public class EveryoneVsEveryoneRequest {
    @JsonProperty("tournamentId")
    @NotNull(message = "{tournamentIdNullErr}")
    private Integer tournamentId;

    @JsonProperty("tournamentFormat")
    @NotNull(message = "{tournamentFormatNullErr}")
    private Integer tournamentFormat;

    @JsonProperty("isSecondRound")
    @NotNull(message = "{isSecondRoundNullErr}")
        private Boolean isSecondRound;

    @JsonProperty("totalDartPoint")
    @NotNull(message = "{totalDartPointNullErr}")
    private Integer totalDartPoint;

    @JsonProperty("dartType")
    @NotNull(message = "{dartTypeNullErr}")
    private Integer dartType;

    @JsonProperty("ringInType")
    @NotNull(message = "{ringInTypeNullErr}")
    private Integer ringInType;

    @JsonProperty("ringOutType")
    @NotNull(message = "{ringOutTypeNullErr}")
    private Integer ringOutType;

    @JsonProperty("winningRule")
    @NotNull(message = "{winningRuleNullErr}")
    private Integer winningRule;

    @JsonProperty("winningMaxCount")
    @NotNull(message = "{winningMaxCountNullErr}")
    private Integer winningMaxCount;

    @JsonProperty("matchType")
    @NotNull(message = "{matchTypeNullErr}")
    private Integer matchType;

    @JsonProperty("inputLevel")
    @NotNull(message = "{inputLevelNullErr}")
    private Integer inputLevel;

    @JsonProperty("venueId")
    @NotNull(message = "{venueIdNullErr}")
    private Integer venueId;

    @JsonProperty("privacy")
    private String privacy;

    @JsonProperty("remark")
    private String remark;

    @JsonProperty("boardNo")
    @NotNull(message = "{boardNoNullErr}")
    @NotEmpty(message = "{boardNoEmptyErr}")
    private List<Integer> boardNo;

    @JsonProperty("tournamentDateTime")
    @NotNull(message = "{tournamentDateTimeNullErr}")
    @NotEmpty(message = "{tournamentDateTimeEmptyErr}")
    private List<RoundDateTime> tournamentDateTime;
}
