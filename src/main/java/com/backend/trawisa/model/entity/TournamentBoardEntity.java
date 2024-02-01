package com.backend.trawisa.model.entity;

import com.backend.trawisa.model.enumtype.DartType;
import com.backend.trawisa.model.enumtype.TournamentFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.ZonedDateTime;

import static com.backend.trawisa.constant.db.ColumnConstant.CREATED_AT;
import static com.backend.trawisa.constant.db.ColumnConstant.UPDATED_AT;
import static com.backend.trawisa.constant.db.TableConstant.TOURNAMENTS_BOARD;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = TOURNAMENTS_BOARD)
public class TournamentBoardEntity {
    public TournamentBoardEntity(Integer boardNo, DartType dartType, TournamentFormat tournamentFormat) {
        this.boardNo = boardNo;
        this.dartType = dartType;
        this.tournamentFormat = tournamentFormat;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer boardNo;

    @Enumerated(EnumType.ORDINAL)
    private DartType dartType;

    @Enumerated(EnumType.ORDINAL)
    private TournamentFormat tournamentFormat;


    @CreationTimestamp()
    @Column(name = CREATED_AT, updatable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = UPDATED_AT)
    private ZonedDateTime updatedAt;

}
