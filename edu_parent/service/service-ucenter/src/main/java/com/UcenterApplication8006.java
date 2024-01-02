package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 会员管理（登录、注册） application8006
 *
 * @author Luo
 */
@SpringBootApplication
@ComponentScan({"com.ljw"}) //指定扫描位置
@MapperScan("com.ljw.educenter.mapper")
@EnableDiscoveryClient // 目的是让注册生效nacos

public class UcenterApplication8006 {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication8006.class, args);
    }
}
