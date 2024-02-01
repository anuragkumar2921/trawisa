package com.backend.trawisa.repositories;

import com.backend.trawisa.model.entity.TeamsEntity;
import com.backend.trawisa.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepo extends JpaRepository<TeamsEntity, Integer> {
    Optional<TeamsEntity> findByName(String name);

    Page<TeamsEntity> findAllByUserEntity(UserEntity userInfo, Pageable pageable);

    Page<TeamsEntity> findByUserEntityAndNameContaining(UserEntity userEntity, String searchKeyWord, Pageable pageable);

    Optional<TeamsEntity> findByIdAndUserEntity(int id, UserEntity userEntity);
    Optional<TeamsEntity> findByUserEntityAndId(UserEntity userEntity, int id);
    Optional<TeamsEntity> findByUserEntity(UserEntity userEntity);
}
