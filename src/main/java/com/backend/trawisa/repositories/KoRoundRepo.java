package com.backend.trawisa.repositories;

import com.backend.trawisa.model.entity.KORoundEntity;
import com.backend.trawisa.model.entity.TournamentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KoRoundRepo extends JpaRepository<KORoundEntity,Integer> {

    Optional<KORoundEntity> findByTournamentEntity(TournamentEntity tournamentEntity);
}
