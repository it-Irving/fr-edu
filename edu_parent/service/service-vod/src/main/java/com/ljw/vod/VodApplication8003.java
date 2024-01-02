package com.ljw.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 视频点播application8003
 *
 * @author Luo
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.ljw"})
@EnableDiscoveryClient //nacos注册
public class VodApplication8003 {
    public static void main(String[] args) {
        SpringApplication.run(VodApplication8003.class, args);
    }
}
