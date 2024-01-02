package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication  // 默认有ComponentScan，但只能扫描同级、子级
@ComponentScan({"com.ljw"}) //指定扫描位置
@EnableDiscoveryClient // nacos注册
@EnableFeignClients // 服务发现
@MapperScan(value = "com.ljw.eduorder.mapper")

public class OrdersApplication8007 {
    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication8007.class, args);
    }
}
