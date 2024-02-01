package com.backend.trawisa.repositories;

import com.backend.trawisa.model.entity.GroupStageRoundEntity;
import com.backend.trawisa.model.entity.TournamentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupStageRoundRepo extends JpaRepository<GroupStageRoundEntity, Integer> {
    Optional<GroupStageRoundEntity> findByTournamentEntity(TournamentEntity tournamentEntity);
}
