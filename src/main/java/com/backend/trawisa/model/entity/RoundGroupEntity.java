package com.backend.trawisa.model.entity;

import com.backend.trawisa.model.enumtype.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.List;

import static com.app.base.project.constant.BaseFinalConstant.COLUM_DEFAULT.BOOLEAN_DEFAULT_TRUE;
import static com.backend.trawisa.constant.db.ColumnConstant.*;
import static com.backend.trawisa.constant.db.ColumnConstant.UPDATED_AT;
import static com.backend.trawisa.constant.db.TableConstant.GROUP_ROUND_TABLE;
import static com.backend.trawisa.constant.db.TableConstant.ROUND_GROUP_TABLE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = ROUND_GROUP_TABLE)
public class RoundGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.ORDINAL)
    private TournamentFormat tournamentFormat;

    private Boolean isRoundLimited;

    private Integer totalDartPoint;
    private Integer eliminationPoint;

    @Enumerated(EnumType.ORDINAL)
    private DartRingInType ringInType;

    @Enumerated(EnumType.ORDINAL)
    private DartRingOutType ringOutType;

    private Integer round;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = GROUP_ROUND_ID)
    private List<RoundGroupSettingEntity> roundGroupSetting;

    private Integer playerOnBoard;

    private Integer maxDeviation;

    @Column(columnDefinition = BOOLEAN_DEFAULT_TRUE)
    private Boolean isRedrawBoard;

    @Enumerated(EnumType.ORDINAL)
    private DartInputLevelType inputLevel;

    @OneToOne
    @JoinColumn(name = VENUE_ID)
    private VenueEntity venueEntity;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = GROUP_ROUND_ID)
    private List<TournamentBoardEntity> boardEntities;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = GROUP_ROUND_ID)
    private List<TournamentDateTimeEntity> tournamentDateTimeEntities;

    @Enumerated(EnumType.ORDINAL)
    private TournamentPrivacy privacy;

    @Lob
    @Column(length = 5000)
    private String remark;

    @OneToOne
    @JoinColumn(name = ROUND_GROUP_ID)
    @JsonBackReference
    private TournamentEntity tournamentEntity;

    @CreationTimestamp()
    @Column(name = CREATED_AT, updatable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = UPDATED_AT)
    private ZonedDateTime updatedAt;
}
