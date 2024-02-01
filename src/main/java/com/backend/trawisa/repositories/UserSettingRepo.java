package com.backend.trawisa.repositories;

import com.backend.trawisa.model.entity.UserEntity;
import com.backend.trawisa.model.entity.UserSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSettingRepo extends JpaRepository<UserSettingEntity,Integer> {

    UserSettingEntity findByUserEntity(UserEntity userEntity);

}
