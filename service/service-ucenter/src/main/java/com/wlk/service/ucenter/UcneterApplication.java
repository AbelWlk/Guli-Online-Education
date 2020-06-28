package com.wlk.service.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.wlk.service.ucenter.mapper")
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.wlk"})
@SpringBootApplication
public class UcneterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcneterApplication.class, args);
    }
}
