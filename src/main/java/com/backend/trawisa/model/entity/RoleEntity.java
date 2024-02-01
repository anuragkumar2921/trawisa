package com.backend.trawisa.model.entity;

import com.backend.trawisa.model.enumtype.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.backend.trawisa.constant.db.TableConstant.ROLE_TABLE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = ROLE_TABLE)
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Enumerated(EnumType.STRING)
    Role role;

    @OneToMany(mappedBy = "roleType")
    @JsonBackReference
    List<UserEntity> userList = new ArrayList<>();

}
