package com.backend.trawisa.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.backend.trawisa.constant.db.ColumnConstant.LOGIN_TYPE;
import static com.backend.trawisa.constant.db.TableConstant.LOGIN_TYPE_TABLE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name =LOGIN_TYPE_TABLE)
public class LoginTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = LOGIN_TYPE)
    String loginType;

}
