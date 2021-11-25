package com.example.sebackend.service;

import com.example.sebackend.model.*;
import com.example.sebackend.repository.*;
import com.example.sebackend.utils.HashHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class AuthenticationService {
    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private EvaluatorRepository evaluatorRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Student getStudent(String sessionId) {
        if(sessionId == null)
            return null;
        String email=template.opsForValue().get(sessionId);
        if(email == null)
            return null;
        Student student= studentRepository.findByEmail(email);
        return student;
    }

    public Administrator getAdministrator(String sessionId) {
        if(sessionId == null)
            return null;
        String email=template.opsForValue().get(sessionId);
        if(email == null)
            return null;
        Administrator administrator= administratorRepository.findByEmail(email);
        return administrator;
    }

    public Evaluator getEvaluator(String sessionId) {
        if(sessionId == null)
            return null;
        String email=template.opsForValue().get(sessionId);
        if(email == null)
            return null;
        Evaluator evaluator=evaluatorRepository.findByEmail(email);
        return evaluator;
    }

    //type==1,学生；type==2,审阅者；type==3,管理员
    public String createSessionId(String email,String password,Integer type){
        String sessionId=null;
        if(type==1) {
            Student student = studentRepository.findByEmail(email);
            if (student == null)
                return null;
            String passwordHashed = HashHelper.computeSha256Hash(password + student.getSalt());
            if (!student.getPassword().equals(passwordHashed))
                return null;

            double seed = ThreadLocalRandom.current().nextDouble();
            sessionId = HashHelper.computeMD5Hash(student.getEmail() + seed);
            template.opsForValue().set(sessionId, student.getEmail(), 1, TimeUnit.DAYS);
        }
        else if(type==2){
            Evaluator evaluator = evaluatorRepository.findByEmail(email);
            if (evaluator == null)
                return null;
            String passwordHashed = HashHelper.computeSha256Hash(password + evaluator.getSalt());
            if (!evaluator.getPassword().equals(passwordHashed))
                return null;

            double seed = ThreadLocalRandom.current().nextDouble();
            sessionId = HashHelper.computeMD5Hash(evaluator.getEmail() + seed);
            template.opsForValue().set(sessionId, evaluator.getEmail(), 1, TimeUnit.DAYS);
        }
        else{
            Administrator administrator = administratorRepository.findByEmail(email);
            if (administrator == null)
                return null;
            String passwordHashed = HashHelper.computeSha256Hash(password + administrator.getSalt());
            if (!administrator.getPassword().equals(passwordHashed))
                return null;

            double seed = ThreadLocalRandom.current().nextDouble();
            sessionId = HashHelper.computeMD5Hash(administrator.getEmail() + seed);
            template.opsForValue().set(sessionId, administrator.getEmail(), 1, TimeUnit.DAYS);
        }
        return sessionId;
    }

    public void invalidateSessionId(String sessionId){
        template.delete(sessionId);
    }
}
