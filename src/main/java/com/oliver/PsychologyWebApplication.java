package com.oliver;

import com.oliver.interceptors.JwtInterceptors;
import com.oliver.interceptors.login;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@MapperScan("com.oliver.mapper")
//禁用Security的自动配置
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@ConfigurationPropertiesScan(basePackages = {"com.oliver.config"})
public class PsychologyWebApplication  {

    public static void main(String[] args) {
        SpringApplication.run(PsychologyWebApplication.class, args);
    }



}
