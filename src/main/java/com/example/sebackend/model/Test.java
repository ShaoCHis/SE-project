package com.example.sebackend.model;
/*
 ** Created By ShaoCHi
 ** Date 2021/11/11 8:37 上午
 ** Tongji University
 */


import javax.persistence.*;

@Entity
public class Test {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String name;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
