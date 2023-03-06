package com.juane;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication  //指定springboot启动类
@Slf4j  //输出日志
//@ServletComponentScan   //配合过滤器使用
@EnableTransactionManagement    //开启事务管理，在操作多张表时
@MapperScan("com.juane.dao")
public class PeapyoungApplication {
    public static void main(String[] args) {
        SpringApplication.run(PeapyoungApplication.class, args);
        System.out.println("项目启动成功！");
    }
}
