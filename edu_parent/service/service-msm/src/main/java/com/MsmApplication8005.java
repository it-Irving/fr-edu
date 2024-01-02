package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 短信服务application8005
 *
 * @author Luo
 */
@ComponentScan({"com.ljw"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//取消数据源自动配置
@EnableDiscoveryClient //目的是让注册生效nacos
public class MsmApplication8005 {
    public static void main(String[] args) {
        SpringApplication.run(MsmApplication8005.class, args);
    }
}
