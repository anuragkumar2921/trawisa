package com.backend.trawisa.model.entity;


import com.backend.trawisa.model.enumtype.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Entity(name = EveryoneAgainstEveryone_TABLE)
public class EveryoneVsEveryoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Enumerated(EnumType.ORDINAL)
    private TournamentFormat tournamentFormat;

    @Column(columnDefinition = BOOLEAN_DEFAULT_TRUE)
    private Boolean isSecondRound;

    private Integer totalDartPoint;

    @Enumerated(EnumType.ORDINAL)
    private DartRingInType ringInType;

    @Enumerated(EnumType.ORDINAL)
    private DartRingOutType ringOutType;

    @Enumerated(EnumType.ORDINAL)
    private DartWinningRule winningRule; //BESTOF FirstOf

    private Integer winningMaxCount;// 3 -> BESTOF =3 or FirstOf =3

    @Enumerated(EnumType.ORDINAL)
    private DartMatchType matchType; // Legs and sets

    @Enumerated(EnumType.ORDINAL)
    private DartInputLevelType inputLevel;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = EVERYONE_ROUND_ID)
    private List<TournamentDateTimeEntity> tournamentDateTimeEntities;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = EVERYONE_ROUND_ID)
    private List<TournamentBoardEntity> boardEntities;


    @ManyToOne
    @JoinColumn(name = VENUE_ID)
    @JsonManagedReference
    private VenueEntity venueEntity;

    @OneToOne
    @JoinColumn(name = TOURNAMENT_ID)
    @JsonBackReference
    private TournamentEntity tournamentEntity;

    @Enumerated(EnumType.ORDINAL)
    private TournamentPrivacy privacy;

    @Lob
    @Column(length = 5000)
    private String remark;



    @CreationTimestamp()
    @Column(name = CREATED_AT, updatable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = UPDATED_AT)
    private ZonedDateTime updatedAt;


}
