package com.backend.trawisa.model.entity;

import com.backend.trawisa.model.enumtype.DartMatchType;
import com.backend.trawisa.model.enumtype.DartRingInType;
import com.backend.trawisa.model.enumtype.DartRingOutType;
import com.backend.trawisa.model.enumtype.DartWinningRule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.backend.trawisa.constant.db.TableConstant.KO_QUALIFYING_ROUND_TABLE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = KO_QUALIFYING_ROUND_TABLE)
public class KOQualifyingSettingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    private String name;

    private Integer totalDartPoint;

    @Enumerated(EnumType.ORDINAL)
    private DartRingInType ringInType;

    @Enumerated(EnumType.ORDINAL)
    private DartRingOutType ringOutType;

    @Enumerated(EnumType.ORDINAL)
    private DartWinningRule winningRule;

    private Integer qualifyingMaxCount;

    @Enumerated(EnumType.ORDINAL)
    private DartMatchType matchType;
}
