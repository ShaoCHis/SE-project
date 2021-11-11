package com.example.sebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class SEbackEndApplication {
  @PostConstruct
  public void setDefaultTimezone() {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
  }
  public static void main(String[] args) {
    SpringApplication.run(SEbackEndApplication.class, args);
  }

}
