package com.project.wall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.project.wall.dao"})
public class WallApplication {

    public static void main(String[] args) {
        SpringApplication.run(WallApplication.class, args);
    }

}
