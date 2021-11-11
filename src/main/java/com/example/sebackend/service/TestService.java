package com.example.sebackend.service;
/*
 ** Created By ShaoCHi
 ** Date 2021/11/11 8:56 上午
 ** Tongji University
 */

import com.example.sebackend.model.*;
import com.example.sebackend.repository.TestRepository;
import com.example.sebackend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TestService {

  @Autowired
  private TestRepository testRepository;

  public Result<Test> getTest(Integer id){
    Optional<Test> test=testRepository.findById(id);
    if(!test.isPresent()){
      return Result.wrapErrorResult("Test is not exist!");
    }
    return Result.wrapSuccessfulResult(test.get());
  }
}
