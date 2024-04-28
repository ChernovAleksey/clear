package com.example.clear;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:app.properties")
public class PropSource {
  public static Integer AGE;

  @Value("${prop.critical}")
  private void setAge(Integer number) {
    AGE = number;
  }
}