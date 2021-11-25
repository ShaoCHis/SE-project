package com.example.sebackend.repository;

import com.example.sebackend.model.Administrator;
import com.example.sebackend.model.Evaluator;
import org.springframework.data.repository.CrudRepository;

import java.util.Objects;

public interface EvaluatorRepository extends CrudRepository<Evaluator,Integer> {
    default Evaluator findByEmail(String email){
        Iterable<Evaluator> evaluators=findAll();
        for(Evaluator evaluator:evaluators){
            if(Objects.equals(evaluator.getEmail(), email)){
                return evaluator;
            }
        }
        return null;
    }
}
