package com.example.projectprogressmanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.projectprogressmanagement.mapper")
public class ProjectProgressManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjectProgressManagementApplication.class, args);
    }
}
