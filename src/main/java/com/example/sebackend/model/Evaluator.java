package com.example.sebackend.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Evaluator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "evaluator_id")
    private Integer evaluatorId;

    private enum category{
        teacher,
        assistant,
        main_teacher
    }

    private String name;

    private String password;

    private String email;

    private String salt;
}
