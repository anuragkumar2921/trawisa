package com.backend.trawisa.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

import static com.backend.trawisa.constant.db.ColumnConstant.*;
import static com.backend.trawisa.constant.db.TableConstant.TEAM_REQUEST_PLAYER;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = TEAM_REQUEST_PLAYER)
public class TeamRequestToPlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = PLAYER_ID)
    UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = TEAM_ID)
    TeamsEntity teamsEntity;

    @CreationTimestamp()
    @Column(name = CREATED_AT, updatable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = UPDATED_AT)
    private ZonedDateTime updatedAt;

    public TeamRequestToPlayerEntity(UserEntity userEntity, TeamsEntity teamsEntity) {
        this.userEntity = userEntity;
        this.teamsEntity = teamsEntity;
    }
}
