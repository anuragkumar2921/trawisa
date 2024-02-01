package com.backend.trawisa.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.backend.trawisa.constant.db.ColumnConstant.*;
import static com.backend.trawisa.constant.db.TableConstant.VENUE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = VENUE)
public class VenueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 60)
    private String name;

    private String street;
    private String houseNumber;
    private String city;
    private String zipCode;
    private Boolean isLookingForTeam;
    private String website;
    private String description;

    private int steelDart;
    private int eDart;

    private String venueImg;

    @Column(precision = 18, scale = 15)
    private BigDecimal latitude;

    @Column(precision = 18, scale = 15)
    private BigDecimal longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = USER_ID)
    @JsonBackReference
    private UserEntity userEntity;

    @CreationTimestamp()
    @Column(name = CREATED_AT, updatable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = UPDATED_AT)
    private ZonedDateTime updatedAt;

    @OneToMany(mappedBy = VENUE_ENTITY)
    @JsonBackReference
    List<EveryoneVsEveryoneEntity> everyoneVsEveryoneEntity = new ArrayList<>();

}
