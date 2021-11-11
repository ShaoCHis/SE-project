package com.example.sebackend.repository;
/*
 ** Created By ShaoCHi
 ** Date 2021/11/11 8:54 上午
 ** Tongji University
 */

import com.example.sebackend.model.Test;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<Test,Integer> {

}
