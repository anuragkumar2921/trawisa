package com.backend.trawisa.service;

import com.backend.trawisa.model.request.SocialRequest;
import com.backend.trawisa.repositories.LoginTypeRepo;
import com.backend.trawisa.service_listener.SocialTypeServiceListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocialServiceService implements SocialTypeServiceListener {

    private final LoginTypeRepo socialRepo;

    @Autowired
    public SocialServiceService(LoginTypeRepo socialRepo) {
        this.socialRepo = socialRepo;
    }

    @Override
    public void addSocialType(SocialRequest socialRequest) {

    }
}
