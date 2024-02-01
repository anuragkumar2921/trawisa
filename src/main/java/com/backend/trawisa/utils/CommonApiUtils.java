package com.backend.trawisa.utils;

import com.app.base.project.exception.ResourceNotFoundException;
import com.app.base.project.utils.MultiLangMessage;
import com.app.base.project.utils.Print;
import com.backend.trawisa.model.entity.UserEntity;
import com.backend.trawisa.repositories.AuthRepo;
import org.springframework.stereotype.Component;

@Component
public class CommonApiUtils {

    private final AuthRepo authRepo;
    private final MultiLangMessage langMessage;


    public CommonApiUtils(AuthRepo authRepo, MultiLangMessage langMessage) {
        this.authRepo = authRepo;
        this.langMessage = langMessage;
    }

    public UserEntity getUserByEmail(String email) {
        return authRepo.findByEmail(email).orElseThrow(()
                -> new ResourceNotFoundException(langMessage.getLocalizeMessage("userNotFound")));
    }

    public UserEntity getUserById(String id) {
        Print.log("UserId " + id);
        return authRepo.findById(Integer.parseInt(id)).orElseThrow(()
                -> new ResourceNotFoundException("getUserById not found"));
    }



}
