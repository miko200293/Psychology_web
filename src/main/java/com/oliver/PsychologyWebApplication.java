package com.oliver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.oliver.mapper")
public class PsychologyWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsychologyWebApplication.class, args);
    }

}
