package com.backend.trawisa.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.app.base.project.constant.BaseFinalConstant.COLUM_DEFAULT.BOOLEAN_DEFAULT_TRUE;
import static com.backend.trawisa.constant.db.ColumnConstant.*;
import static com.backend.trawisa.constant.db.TableConstant.TEAM_TABLE;


@Data
@Entity
@Table(name = TEAM_TABLE)
public class TeamsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Lob
    @Column(length = 5000)
    private String description;

    private String teamImg;

    @Column(columnDefinition = BOOLEAN_DEFAULT_TRUE)
    private Boolean notification;

    private int playerCount;

    @ManyToOne
    @JoinColumn(name = USER_ID)
    @JsonBackReference
    private UserEntity userEntity;


    @OneToMany(mappedBy = TEAM_ENTITY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PlayerInTeamEntity> playersInTeam = new ArrayList<>();

    @CreationTimestamp()
    @Column(name = CREATED_AT, updatable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = UPDATED_AT)
    private ZonedDateTime updatedAt;

}
