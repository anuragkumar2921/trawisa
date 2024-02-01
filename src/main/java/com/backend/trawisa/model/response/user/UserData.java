package com.backend.trawisa.model.response.user;

import com.backend.trawisa.model.enumtype.Gender;
import com.backend.trawisa.model.entity.ClassGradeEntity;
import com.backend.trawisa.model.entity.LoginTypeEntity;
import com.backend.trawisa.model.entity.RoleEntity;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class UserData {
    private int id;
    private String name;
    private String userName;
    private String email;
    private String profileImage;
    private int zipCode;
    private String location;
    private String description;
    private Gender gender;
    private Boolean isProfileComplete;
    private RoleEntity roleType;
    private LoginTypeEntity socialName;
    private ClassGradeEntity class_grade;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

}
