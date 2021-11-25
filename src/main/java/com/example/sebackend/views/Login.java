package com.example.sebackend.views;

import lombok.Data;

@Data
public class Login {
    private String email;

    private String password;

    private Integer type;
}
