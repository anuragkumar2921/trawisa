package com.backend.trawisa.repositories;

import com.backend.trawisa.model.entity.TeamRequestToPlayerEntity;
import com.backend.trawisa.model.entity.TeamsEntity;
import com.backend.trawisa.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRequestToPlayerRepo extends JpaRepository<TeamRequestToPlayerEntity, Integer> {
    List<TeamRequestToPlayerEntity> findAllByTeamsEntity(TeamsEntity teamsEntity);

    Optional<TeamRequestToPlayerEntity> findAllByUserEntity(UserEntity teamsEntity);

    Optional<TeamRequestToPlayerEntity> findByTeamsEntityAndUserEntity(TeamsEntity teamsEntity, UserEntity userEntity);

}
