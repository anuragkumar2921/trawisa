package com.backend.trawisa.repositories;

import com.backend.trawisa.model.entity.PlayerInTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerInTeamRepo extends JpaRepository<PlayerInTeamEntity,Integer> {
}
