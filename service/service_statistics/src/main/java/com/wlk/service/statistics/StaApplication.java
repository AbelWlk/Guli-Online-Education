package com.wlk.service.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.wlk.service.statistics.mapper")
@ComponentScan("com.wlk")
@SpringBootApplication
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class, args);
    }
}
