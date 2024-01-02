package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 *
 * @author Luo
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) // 不加载该类（数据库）
@EnableDiscoveryClient //nacos注册
@ComponentScan({"com.ljw"})
public class OssApplicationMain8002 {
    public static void main(String[] args) {
        SpringApplication.run(OssApplicationMain8002.class, args);
    }
}
