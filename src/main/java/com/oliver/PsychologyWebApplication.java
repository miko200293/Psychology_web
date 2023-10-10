package com.oliver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;



@MapperScan("com.oliver.mapper")
//禁用Security的自动配置
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class PsychologyWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsychologyWebApplication.class, args);
    }

}
