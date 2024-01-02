package com.atguigu.canal;

import com.atguigu.canal.client.CanalClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class CanalApplication10000 implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(CanalApplication10000.class, args);
    }

    @Resource
    private CanalClient canalClient;
    @Override
    public void run(String... strings) throws Exception {
        //项目启动，执行canal客户端监听
        canalClient.run();
    }

}
