package com.backend.trawisa.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RoundGroupRequest {

    @JsonProperty("tournamentId")
    @NotNull(message = "{tournamentIdNullErr}")
    private Integer tournamentId;

    @JsonProperty("tournamentFormat")
    @NotNull(message = "{tournamentFormatNullErr}")
    private Integer tournamentFormat;

    @JsonProperty("isRoundLimited")
    @NotNull(message = "{isRoundNoLimitedErr}")
    private Boolean isRoundLimited;

    @JsonProperty("totalDartPoint")
    private Integer totalDartPoint;

    @JsonProperty("eliminationPoint")
    private Integer eliminationPoint;

    @JsonProperty("ringInType")
    private Integer ringInType;

    @JsonProperty("ringOutType")
    private Integer ringOutType;

    @JsonProperty("round")
    private Integer round;

    @JsonProperty("playerOnBoard")
    @NotNull(message = "{playerOnBoardErr}")
    private Integer playerOnBoard;

    @JsonProperty("maxDeviation")
    @NotNull(message = "{maxDeviationErr}")
    private Integer maxDeviation;

    @JsonProperty("isRedrawBoard")
    @NotNull(message = "{isRedrawBoardErr}")
    private Boolean isRedrawBoard;

    @JsonProperty("inputLevel")
    @NotNull(message = "{inputLevelNullErr}")
    private Integer inputLevel;

    @NotNull(message = "{dartTypeNullErr}")
    @JsonProperty("dartType")
    private Integer dartType;

    @JsonProperty("venueId")
    @NotNull(message = "{venueIdNullErr}")
    private Integer venueId;

    @JsonProperty("privacy")
    @NotNull(message = "{privacyBlankErr}")
    private String privacy;

    @JsonProperty("remark")
    @NotNull(message = "{remarkBlankErr}")
    private String remark;

    @JsonProperty("roundGroupSetting")
    private List<RoundGroupSettingRequest> roundGroupSetting;

    @JsonProperty("boardNo")
    @NotNull(message = "{boardNoNullErr}")
    @NotEmpty(message = "{boardNoEmptyErr}")
    private List<Integer> boardNo;

    @JsonProperty("tournamentDateTime")
    @NotNull(message = "{tournamentDateTimeNullErr}")
    @NotEmpty(message = "{tournamentDateTimeEmptyErr}")
    private List<RoundDateTime> tournamentDateTime;
}
