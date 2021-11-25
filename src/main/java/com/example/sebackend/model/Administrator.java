package com.example.sebackend.model;

import lombok.*;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "administer_id")
    private Integer administerId;

    private String name;

    private String password;

    private String email;

    private String salt;
}
