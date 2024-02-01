package com.backend.trawisa.model.request.ko_round;

import com.backend.trawisa.model.enumtype.DartMatchType;
import com.backend.trawisa.model.enumtype.DartRingInType;
import com.backend.trawisa.model.enumtype.DartRingOutType;
import com.backend.trawisa.model.enumtype.DartWinningRule;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class KoQualifyingRoundSettingModel {
    private String name;
    private Integer totalDartPoint;
    private Integer ringInType;
    private Integer ringOutType;
    private Integer winningRule;
    private Integer qualifyingMaxCount;
    private Integer matchType;
}
