package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * cms application8004
 *
 * @author Luo
 */
@SpringBootApplication
@ComponentScan({"com.ljw"}) //指定扫描位置
@MapperScan("com.ljw.educms.mapper")
@EnableDiscoveryClient //目的是让注册生效nacos
public class CmsApplication8004 {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication8004.class, args);
    }
}
