package com.backend.trawisa.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class GroupStageRoundRequest {

    @NotNull(message = "{tournamentIdNullErr}")
    @JsonProperty("tournamentId")
    private Integer tournamentId;

    @NotNull(message = "{tournamentFormatNullErr}")
    @JsonProperty("tournamentFormat")
    private Integer tournamentFormat;

    @NotNull(message = "{isSecondRoundNullErr}")
    @JsonProperty("isSecondRound")
    private Boolean isSecondRound;

    @NotNull(message = "{totalDartPointNullErr}")
    @JsonProperty("totalDartPoint")
    private Integer totalDartPoint;

    @NotNull(message = "{ringInTypeNullErr}")
    @JsonProperty("ringInType")
    private Integer ringInType;

    @NotNull(message = "{ringOutTypeNullErr}")
    @JsonProperty("ringOutType")
    private Integer ringOutType;

    @NotNull(message = "{winningRuleErr}")
    @JsonProperty("winningRule")
    private Integer winningRule;

    @NotNull(message = "{winningMaxCountNullErr}")
    @JsonProperty("winningMaxCount")
    private Integer winningMaxCount;

    @NotNull(message = "{matchTypeNullErr}")
    @JsonProperty("matchType")
    private Integer matchType;

    @NotNull(message = "{groupSizeErr}")
    @JsonProperty("groupSize")
    private Integer groupSize;

    @NotNull(message = "{inputLevelNullErr}")
    @JsonProperty("inputLevel")
    private Integer inputLevel;

    @NotEmpty(message = "{boardNoEmptyErr}")
    @JsonProperty("boardNo")
    private List<Integer> boardNo;

    @NotNull(message = "{dartTypeNullErr}")
    @JsonProperty("dartType")
    private Integer dartType;

    @NotNull(message = "{venueIdNullErr}")
    @JsonProperty("venueId")
    private Integer venueId;


    @JsonProperty("privacy")
    private String privacy;

    @JsonProperty("remark")
    private String remark;

    @NotEmpty(message = "{tournamentDateTimeEmptyErr}")
    @JsonProperty("tournamentDateTime")
    private List<RoundDateTime> tournamentDateTime;
}
