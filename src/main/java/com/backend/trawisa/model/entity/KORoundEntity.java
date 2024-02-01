package com.backend.trawisa.model.entity;

import com.backend.trawisa.model.enumtype.TournamentFormat;
import com.backend.trawisa.model.enumtype.TournamentPrivacy;
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
import static com.backend.trawisa.constant.db.TableConstant.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = KO_ROUND_TABLE)

public class KORoundEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.ORDINAL)
    private TournamentFormat tournamentFormat;

    private Integer koRound;

    @Column(columnDefinition = BOOLEAN_DEFAULT_TRUE)
    private Boolean isCompensate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = KO_ROUND_ID)
    private List<KOMainRoundSettingEntity> koMainRoundEntities;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = KO_ROUND_ID)
    private List<KOQualifyingSettingEntity> koQualifyingSettingEntities;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = ROUND_ID)
    private List<TournamentDateTimeEntity> tournamentDateTimeEntities;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = ROUND_ID)
    private List<TournamentBoardEntity> boardEntities;

    private String inputLevel;

    @Enumerated(EnumType.ORDINAL)
    private TournamentPrivacy privacy;

    @Lob
    @Column(length = 5000)
    private String remark;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = VENUE_ID)
    private VenueEntity venueEntity;

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
