package com.cloud.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author ycy
 */
@SpringBootApplication
@MapperScan(value = "com.cloud.article.mapper")
@ComponentScan(basePackages = {"com.cloud","org.n3r"})
@EnableEurekaClient
@EnableFeignClients({"com.cloud"})
@EnableHystrix
public class ServiceArticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceArticleApplication.class,args);
    }
}
