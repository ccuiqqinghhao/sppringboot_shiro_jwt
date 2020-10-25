package com.example.uiddemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.uiddemo.mapper")
public class UiddemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(UiddemoApplication.class, args);
    }

}
