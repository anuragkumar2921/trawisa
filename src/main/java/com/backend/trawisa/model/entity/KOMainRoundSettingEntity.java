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

import static com.backend.trawisa.constant.db.TableConstant.KO_MAIN_ROUND_TABLE;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = KO_MAIN_ROUND_TABLE)
public class KOMainRoundSettingEntity {

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

    private Integer winningMaxCount;


    @Enumerated(EnumType.ORDINAL)
    private DartMatchType matchType;
}
