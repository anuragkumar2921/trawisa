package com.backend.trawisa.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ScratchRoundRequest {
    @JsonProperty("tournamentId")
    @NotNull(message = "{tournamentIdNullErr}")
    private Integer tournamentId;

    @JsonProperty("tournamentFormat")
    @NotNull(message = "{tournamentFormatNullErr}")
    private Integer tournamentFormat;

    @JsonProperty("hobby")
    @NotNull(message = "{hobbyNullErr}")
    private Integer hobby;

    @JsonProperty("cLeague")
    @NotNull(message = "{cLeagueNullErr}")
    private Integer cLeague;

    @JsonProperty("extraScratches")
    @NotNull(message = "{extraScratchesNullErr}")
    private Integer extraScratches;

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

    @JsonProperty("generalNumber")
    @NotNull(message = "{generalNumberNullErr}")
    private Integer generalNumber;

    @JsonProperty("maxDeviation")
    @NotNull(message = "{maxDeviationNullErr}")
    private Integer maxDeviation;

    @JsonProperty("inputLevel")
    @NotNull(message = "{inputLevelNullErr}")
    private Integer inputLevel;

    @JsonProperty("venueId")
    @NotNull(message = "{venueIdNullErr}")
    private Integer venueId;

    @JsonProperty("boardNo")
    @NotNull(message = "{boardNoNullErr}")
    @NotEmpty(message = "{boardNoEmptyErr}")
    private List<Integer> boardNo;

    @JsonProperty("tournamentDateTime")
    @NotNull(message = "{tournamentDateTimeNullErr}")
    @NotEmpty(message = "{tournamentDateTimeEmptyErr}")
    private List<RoundDateTime> tournamentDateTime;

    @JsonProperty("privacy")
    private String privacy;

    @JsonProperty("remark")
    private String remark;

}
