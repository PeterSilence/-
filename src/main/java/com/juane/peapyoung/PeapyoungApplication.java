package com.juane.peapyoung;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.juane.peapyoung.dao")
public class PeapyoungApplication {
    public static void main(String[] args) {
        SpringApplication.run(PeapyoungApplication.class, args);
        System.out.println("项目启动成功！");
    }
}
