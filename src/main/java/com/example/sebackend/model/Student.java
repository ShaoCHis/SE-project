package com.example.sebackend.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "student_id")
    private Integer studentId;

    private String name;

    private String password;

    private String email;

    private String salt;

    //激活状态
    private Integer status;
}
