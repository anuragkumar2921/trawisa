package com.backend.trawisa.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.backend.trawisa.constant.db.TableConstant.CLASS_GRADE;

@Entity
@Table(name = CLASS_GRADE)
@Getter
@Setter
@NoArgsConstructor
public class ClassGradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "class_grade")
    @JsonBackReference
    List<UserEntity> userEntityList = new ArrayList<>();
}
