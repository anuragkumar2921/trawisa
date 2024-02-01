package com.backend.trawisa.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTournamentRequest {
    @NotBlank(message = "{tournamentNameErr}")
    @JsonProperty("tournamentName")
    private String tournamentName;

    @NotBlank(message = "{dartTypeNullErr}")
    @JsonProperty("dartType")
    private  String dartType;

    @NotNull(message = "{isSeriesErr}")
    @JsonProperty("isSeries")
    private Boolean isSeries;

    @NotBlank(message = "{tournamentTypeErr}")
    @JsonProperty("tournamentType")
    private String tournamentType;

    @NotBlank(message = "{seriesNameErr}")
    @JsonProperty("seriesName")
    private String seriesName;

    @NotNull(message = "{totalTeamsErr}")
    @JsonProperty("totalTeams")
    private Integer totalTeams;

    @NotBlank(message = "{playerSystemErr}")
    @JsonProperty("playerSystem")
    private String playerSystem;

    @NotNull(message = "{isQualifyingRoundErr}")
    @JsonProperty("isQualifyingRound")
    private Boolean isQualifyingRound;

    @NotNull(message = "{totalQualifyingTeamErr}")
    @JsonProperty("totalQualifyingTeam")
    private Integer totalQualifyingTeam;

    @NotNull(message = "{isLeagueLimitErr}")
    @JsonProperty("isLeagueLimit")
    private Boolean isLeagueLimit;

    @NotBlank(message = "{leagueLimitFromErr}")
    @JsonProperty("leagueLimitFrom")
    private String leagueLimitFrom;

    @NotBlank(message = "{leagueLimitToErr}")
    @JsonProperty("leagueLimitTo")
    private String leagueLimitTo;

    @NotNull(message = "{isClassErr}")
    @JsonProperty("isClass")
    private Boolean isClass;

    @NotNull(message = "{isDistanceErr}")
    @JsonProperty("isDistance")
    private Boolean isDistance;

    // This is only for Team vs Team


    @JsonProperty("isOneCount")
    private Boolean isOneCount;


    @JsonProperty("isTeamOneVsTwo")
    private Boolean isTeamOneVsTwo;

    @JsonProperty("isTeamSecondRound")
    private Boolean isTeamSecondRound;

    @JsonProperty("oneVsThreeAndTwoVsFour")
    private Boolean oneVsThreeAndTwoVsFour;

    @JsonProperty("oneVsThreeAndTwoVsFourAndSecondRound")
    private Boolean oneVsThreeAndTwoVsFourAndSecondRound;

    @JsonProperty("oneVsFourAndTwoVsThree")
    private Boolean oneVsFourAndTwoVsThree;

    @JsonProperty("oneVsFourAndTwoVsThreeAndSecondRound")
    private Boolean oneVsFourAndTwoVsThreeAndSecondRound;

}