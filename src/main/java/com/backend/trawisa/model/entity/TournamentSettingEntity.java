package com.backend.trawisa.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.app.base.project.constant.BaseFinalConstant.COLUM_DEFAULT.BOOLEAN_DEFAULT_TRUE;
import static com.backend.trawisa.constant.db.ColumnConstant.TOURNAMENT_SETTING;

@Entity(name = TOURNAMENT_SETTING)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TournamentSettingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(columnDefinition = BOOLEAN_DEFAULT_TRUE)
    Boolean isOneCount;

    Boolean isTeamOneVsTwo;
    Boolean isTeamSecondRound;
    Boolean oneVsThree_TwoVsFour;
    Boolean oneVsThree_TwoVsFour_SecondRound;
    Boolean oneVsFour_TwoVsThree;
    Boolean oneVsFour_TwoVsThree_SecondRound;


}
