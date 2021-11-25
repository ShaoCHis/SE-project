package com.example.sebackend.repository;

import com.example.sebackend.model.Administrator;
import com.example.sebackend.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Objects;

public interface StudentRepository extends CrudRepository<Student,Integer> {
    default Student findByEmail(String email){
        Iterable<Student> students=findAll();
        for(Student student:students){
            if(Objects.equals(student.getEmail(), email)){
                return student;
            }
        }
        return null;
    }
}
