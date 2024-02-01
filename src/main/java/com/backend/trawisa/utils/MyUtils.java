package com.backend.trawisa.utils;

import com.app.base.project.exception.ResourceNotFoundException;
import com.backend.trawisa.model.entity.UserEntity;

public class MyUtils {


    public boolean checkSocialLogin(String loginType){
        return (loginType.equals("google") || loginType.equals("facebook") || loginType.equals("apple"));
    }


    public static void checkValidUser(UserEntity user, UserEntity currentUser) {

        if (!user.equals(currentUser)) {
            throw new ResourceNotFoundException("User not found");

        }
    }

}
