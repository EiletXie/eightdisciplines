package com.suntak.eightdisciplines;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.suntak.eightdisciplines.dao")
public class EightdisciplinesApplication  {

    public static void main(String[] args) {
        SpringApplication.run(EightdisciplinesApplication.class, args);
    }

}
