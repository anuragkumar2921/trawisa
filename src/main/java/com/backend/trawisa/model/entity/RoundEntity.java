package com.backend.trawisa.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

import static com.backend.trawisa.constant.db.ColumnConstant.ROUND_ID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "round_type")
public abstract class RoundEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uniqueId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = ROUND_ID)
    private List<TournamentDateTimeEntity> tournamentDateTimeEntities;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = ROUND_ID)
    private List<TournamentBoardEntity> boardEntities;
}
