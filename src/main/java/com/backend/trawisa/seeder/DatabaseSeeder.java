package com.backend.trawisa.seeder;

import com.backend.trawisa.model.request.SocialRequest;
import com.backend.trawisa.model.entity.LoginTypeEntity;
import com.backend.trawisa.repositories.LoginTypeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder {

    @Autowired
    private LoginTypeRepo socialRepo;

    @Autowired
    private ModelMapper modelMapper;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        // Here goes the seeders

        List<LoginTypeEntity> loginTypeEntityLis = socialRepo.findAll();
        if (loginTypeEntityLis.isEmpty()) {
            seedSocialType();
        }
    }

    private void seedSocialType() {
        List<SocialRequest> socialList = new ArrayList<>();
        socialList.add(new SocialRequest("google"));
        socialList.add(new SocialRequest("apple"));
        socialList.add(new SocialRequest("facebook"));
        socialList.add(new SocialRequest("email"));
        List<LoginTypeEntity> loginTypeEntityList = socialList.stream().map((socialType) -> this.modelMapper.map(socialType, LoginTypeEntity.class)).toList();


        socialRepo.saveAll(loginTypeEntityList);
    }
}
