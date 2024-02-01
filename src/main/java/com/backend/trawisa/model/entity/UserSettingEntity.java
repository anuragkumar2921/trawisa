package com.backend.trawisa.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

import static com.app.base.project.constant.BaseFinalConstant.COLUM_DEFAULT.BOOLEAN_DEFAULT_TRUE;
import static com.backend.trawisa.constant.db.ColumnConstant.*;
import static com.backend.trawisa.constant.db.TableConstant.USERS_SETTING;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = USERS_SETTING)
public class UserSettingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = BOOLEAN_DEFAULT_TRUE)
    private boolean playWithTeams;

    @Column(columnDefinition = BOOLEAN_DEFAULT_TRUE)
    private boolean playWithPlayer;

    private Double radius;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =USER_ID)
    @JsonBackReference
    private UserEntity userEntity;

    @CreationTimestamp()
    @Column(name = CREATED_AT, updatable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = UPDATED_AT)
    private ZonedDateTime updatedAt;


    public UserSettingEntity(boolean playWithTeams,boolean playWithPlayer, double radius) {
        this.playWithTeams = playWithTeams; // Set default value
        this.playWithPlayer = playWithPlayer; // Set default value
        this.radius = radius; // Set default value
    }

}
