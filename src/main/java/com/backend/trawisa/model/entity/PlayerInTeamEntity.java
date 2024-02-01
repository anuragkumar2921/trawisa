package com.backend.trawisa.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

import static com.backend.trawisa.constant.db.ColumnConstant.*;
import static com.backend.trawisa.constant.db.TableConstant.PLAYER_IN_TEAM;

@Entity
@Data
@Table(name = PLAYER_IN_TEAM)
public class PlayerInTeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = PLAYER_ID)
    @JsonIgnore
    UserEntity userEntity;


    @ManyToOne
    @JoinColumn(name = TEAM_ID)
    @JsonBackReference
    TeamsEntity teamsEntity;

    @CreationTimestamp()
    @Column(name = CREATED_AT, updatable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = UPDATED_AT)
    private ZonedDateTime updatedAt;

    public PlayerInTeamEntity(UserEntity userEntity, TeamsEntity teamsEntity) {
        this.userEntity = userEntity;
        this.teamsEntity = teamsEntity;
    }

    public PlayerInTeamEntity() {

    }
}
