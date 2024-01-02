package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * edu启动类
 *
 * @author Luo
 */
@SpringBootApplication
@EnableDiscoveryClient // nacos注册
@EnableFeignClients // nacos注册后，服务调用
@ComponentScan(basePackages = {"com.ljw"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
