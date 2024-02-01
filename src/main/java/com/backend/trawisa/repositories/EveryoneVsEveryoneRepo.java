package com.backend.trawisa.repositories;

import com.backend.trawisa.model.entity.EveryoneVsEveryoneEntity;
import com.backend.trawisa.model.entity.TournamentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EveryoneVsEveryoneRepo extends JpaRepository<EveryoneVsEveryoneEntity, Integer> {
   Optional<EveryoneVsEveryoneEntity> findByTournamentEntity(TournamentEntity tournamentEntity);
}
