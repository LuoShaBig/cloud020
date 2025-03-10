package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 我想把这个服务注册：用户信息维护
 */
@SpringBootApplication
public class OrderMian80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMian80.class,args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
