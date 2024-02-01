package com.backend.trawisa.model.request.ko_round;

import com.backend.trawisa.model.request.RoundDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class KnockOutRoundRequest {

    @NotNull(message = "{tournamentIdNullErr}")
    @JsonProperty("tournamentId")
    private Integer tournamentId;

    @NotNull(message = "{tournamentFormatNullErr}")
    @JsonProperty("tournamentFormat")
    private Integer tournamentFormat;

    @NotNull(message = "{koRoundNullErr}")
    @JsonProperty("koRound")
    private Integer koRound;

    @NotNull(message = "{isCompensateNullErr}")
    @JsonProperty("isCompensate")
    private Boolean isCompensate;

    @NotEmpty(message = "{mainRoundSettingErr}")
    @JsonProperty("mainRoundSetting")
    private List<KoMainRoundSettingModel> mainRoundSetting;

    @NotEmpty(message = "{qualifyingRoundSettingErr}")
    @JsonProperty("qualifyingRoundSetting")
    private List<KoQualifyingRoundSettingModel> qualifyingRoundSetting;

    @NotNull(message = "{inputLevelNullErr}")
    @JsonProperty("inputLevel")
    private String inputLevel;

    @NotNull(message = "{venueIdNullErr}")
    @JsonProperty("venueId")
    private Integer venueId;

    @NotEmpty(message = "{boardNoEmptyErr}")
    @JsonProperty("boardNo")
    private List<Integer> boardNo;

    @NotEmpty(message = "{tournamentDateTimeEmptyErr}")
    @JsonProperty("tournamentDateTime")
    private List<RoundDateTime> tournamentDateTime;

    @NotNull(message = "{dartTypeNullErr}")
    @JsonProperty("dartType")
    private Integer dartType;

    @JsonProperty("privacy")
    private String privacy;

    @JsonProperty("remark")
    private String remark;


}
