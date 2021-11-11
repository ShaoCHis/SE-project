package com.example.sebackend.controller;
/*
 ** Created By ShaoCHi
 ** Date 2021/11/11 8:52 上午
 ** Tongji University
 */

import com.example.sebackend.model.*;
import com.example.sebackend.service.*;
import com.example.sebackend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // This means that this class is a Controller
@RequestMapping(path="api/tests") // This means URL's start with /demo (after Application path)
public class TestController {
  @Autowired
  private TestService testService;

  @GetMapping(path="/getTest/{id}")
  public @ResponseBody
  Result<Test> getAllTest(@PathVariable Integer id){
    return testService.getTest(id);
  }
}
