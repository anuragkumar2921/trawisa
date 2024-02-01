package com.backend.trawisa.model.entity;

import com.backend.trawisa.model.enumtype.DartType;
import com.backend.trawisa.model.enumtype.TournamentLevel;
import com.backend.trawisa.model.enumtype.TournamentType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.app.base.project.constant.BaseFinalConstant.COLUM_DEFAULT.BOOLEAN_DEFAULT_FALSE;
import static com.app.base.project.constant.BaseFinalConstant.COLUM_DEFAULT.BOOLEAN_DEFAULT_TRUE;
import static com.backend.trawisa.constant.db.ColumnConstant.*;
import static com.backend.trawisa.constant.db.TableConstant.TOURNAMENTS;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = TOURNAMENTS)
public class TournamentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tournamentName;
    private DartType dartType;

    @Column(columnDefinition = BOOLEAN_DEFAULT_TRUE)
    private boolean isSeries;

    @Enumerated(EnumType.ORDINAL)
    private TournamentType tournamentType;

    private String seriesName;
    private Integer totalTeams;

    @Enumerated(EnumType.ORDINAL)
    private TournamentType playerSystem;

    @Column(columnDefinition = BOOLEAN_DEFAULT_TRUE)
    private Boolean isQualifyingRound;

    private Integer totalQualifyingTeam;

    @Column(columnDefinition = BOOLEAN_DEFAULT_TRUE)
    private Boolean isLeagueLimit;

    private TournamentLevel leagueLimitFrom;
    private TournamentLevel leagueLimitTo;

    @Column(columnDefinition = BOOLEAN_DEFAULT_TRUE)
    private Boolean isClass;

    @Column(columnDefinition = BOOLEAN_DEFAULT_FALSE)
    private Boolean isDistance;

    @JoinColumn(name = TOURNAMENT_SETTING_ID)
    @OneToOne(cascade = CascadeType.ALL)
    TournamentSettingEntity tournamentSettingEntity;

    @OneToOne(mappedBy = TOURNAMENT_ENTITY)
    @JsonManagedReference
    private EveryoneVsEveryoneEntity everyoneEntity;

    @OneToOne(mappedBy = TOURNAMENT_ENTITY)
    @JsonManagedReference
    private KORoundEntity koRoundEntity;

    @OneToOne(mappedBy = TOURNAMENT_ENTITY)
    @JsonManagedReference
    private RoundGroupEntity roundGroupEntities;

    @OneToOne(mappedBy = TOURNAMENT_ENTITY)
    @JsonManagedReference
    private ScratchRoundEntity scratchRoundEntities;


    @OneToOne(mappedBy = TOURNAMENT_ENTITY)
    @JsonManagedReference
    private GroupStageRoundEntity groupStageRoundEntities;



    @ManyToOne
    @JoinColumn(name = USER_ID)
    @JsonBackReference
    private UserEntity userEntity;

}
