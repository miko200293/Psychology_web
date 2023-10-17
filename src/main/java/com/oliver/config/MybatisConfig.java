package com.oliver.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;

public class MybatisConfig {
    @Bean
    public MybatisPlusInterceptor pageinationInterceptor() {
        MybatisPlusInterceptor intercept=new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor =new PaginationInnerInterceptor(DbType.MYSQL);
        intercept.addInnerInterceptor(paginationInnerInterceptor);
        return intercept;
    }

}
