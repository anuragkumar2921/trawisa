package com.backend.trawisa.model.entity;

import com.backend.trawisa.model.enumtype.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import static com.backend.trawisa.constant.db.TableConstant.GROUP_STAGE_ROUND_TABLE;
import static com.backend.trawisa.constant.db.TableConstant.SCRATCH_GROUP_ROUND_TABLE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = SCRATCH_GROUP_ROUND_TABLE)
public class ScratchRoundEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer id;

    @Enumerated(EnumType.ORDINAL)
    private TournamentFormat tournamentFormat;

    private Integer hobby;
    private Integer cLeague;
    private Integer extraScratches;
    private Integer totalDartPoint;

    @Enumerated(EnumType.ORDINAL)
    private DartRingInType ringInType;

    @Enumerated(EnumType.ORDINAL)
    private DartRingOutType ringOutType;

    private Integer generalNumber;
    private Integer maxDeviation;

    private DartInputLevelType inputLevel;

    @ManyToOne
    @JoinColumn(name = VENUE_ID)
    private VenueEntity venueEntity;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = SCRATCH_GROUP_ROUND_ID)
    private List<TournamentBoardEntity> boardEntities;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = SCRATCH_GROUP_ROUND_ID)
    private List<TournamentDateTimeEntity> tournamentDateTimeEntities;

    @Enumerated(EnumType.ORDINAL)
    private TournamentPrivacy privacy;

    @Lob
    @Column(length = 5000)
    private String remark;

    @OneToOne
    @JoinColumn(name = TOURNAMENT_ID)
    @JsonBackReference
    private TournamentEntity tournamentEntity;

    @CreationTimestamp()
    @Column(name = CREATED_AT, updatable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = UPDATED_AT)
    private ZonedDateTime updatedAt;

}
