package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * sta application8008
 *
 * @author Luo
 */
@SpringBootApplication
@EnableDiscoveryClient // nacos注册
@EnableFeignClients // nacos注册后，服务调用
@ComponentScan(basePackages = {"com.ljw"})
@MapperScan("com.ljw.staservice.mapper")
@EnableScheduling() //定时任务

public class StaApplication8008 {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication8008.class, args);
    }
}
