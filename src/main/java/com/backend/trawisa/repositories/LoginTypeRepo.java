package com.backend.trawisa.repositories;

import com.backend.trawisa.model.entity.LoginTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginTypeRepo extends JpaRepository<LoginTypeEntity, Integer> {

    Optional<LoginTypeEntity> findByLoginType(String name);
}
