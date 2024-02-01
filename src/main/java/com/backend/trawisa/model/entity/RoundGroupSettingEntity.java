package com.backend.trawisa.model.entity;

import com.backend.trawisa.model.enumtype.DartRingInType;
import com.backend.trawisa.model.enumtype.DartRingOutType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.backend.trawisa.constant.db.TableConstant.GROUP_ROUND_SETTING_TABLE;
import static com.backend.trawisa.constant.db.TableConstant.ROUND_GROUP_SETTING_TABLE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = ROUND_GROUP_SETTING_TABLE)
public class RoundGroupSettingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private Integer totalDartPoint;

    @Enumerated(EnumType.ORDINAL)
    private DartRingInType ringInType;

    @Enumerated(EnumType.ORDINAL)
    private DartRingOutType ringOutType;

    private String remainingPoint;


}
