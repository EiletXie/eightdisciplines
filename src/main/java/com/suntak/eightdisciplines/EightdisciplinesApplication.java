package com.suntak.eightdisciplines;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class EightdisciplinesApplication  {

    public static void main(String[] args) {
        SpringApplication.run(EightdisciplinesApplication.class, args);
    }

//    @Bean(name = "multipartResolver")
//    public CommonsMultipartResolver multipartResolver(){
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setDefaultEncoding("utf-8");
//        multipartResolver.setMaxInMemorySize(-1);
//        return multipartResolver;
//    }


}
