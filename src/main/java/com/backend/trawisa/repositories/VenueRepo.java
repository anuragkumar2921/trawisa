package com.backend.trawisa.repositories;

import com.backend.trawisa.model.entity.UserEntity;
import com.backend.trawisa.model.entity.VenueEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepo extends JpaRepository<VenueEntity, Integer> {

    Page<VenueEntity> findAllByUserEntity(UserEntity userEntity, Pageable pageable);

    Page<VenueEntity> findAllByUserEntityAndNameContainingIgnoreCase(UserEntity userEntity, Pageable pageable, String venueName);
}
