package com.backend.trawisa.repositories;

import com.backend.trawisa.model.entity.RoundGroupEntity;
import com.backend.trawisa.model.entity.TournamentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoundGroupRepo extends JpaRepository<RoundGroupEntity,Integer> {
    Optional<RoundGroupEntity> findByTournamentEntity(TournamentEntity tournament);
}
