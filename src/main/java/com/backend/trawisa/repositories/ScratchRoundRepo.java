package com.backend.trawisa.repositories;

import com.backend.trawisa.model.entity.ScratchRoundEntity;
import com.backend.trawisa.model.entity.TournamentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScratchRoundRepo extends JpaRepository<ScratchRoundEntity, Integer> {
    Optional<ScratchRoundEntity> findByTournamentEntity(TournamentEntity tournament);
}
