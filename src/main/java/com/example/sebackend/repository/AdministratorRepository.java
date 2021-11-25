package com.example.sebackend.repository;

import com.example.sebackend.model.Administrator;
import org.springframework.data.repository.CrudRepository;

import java.util.Objects;

public interface AdministratorRepository extends CrudRepository<Administrator,Integer> {
    default Administrator findByEmail(String email){
        Iterable<Administrator> administrators=findAll();
        for(Administrator administrator:administrators){
            if(Objects.equals(administrator.getEmail(), email)){
                return administrator;
            }
        }
        return null;
    }
}
