package com.cloud.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author ycy
 */
@SpringBootApplication
@MapperScan(value = "com.cloud.article.mapper")
@ComponentScan(basePackages = {"com.cloud","org.n3r"})
public class ServiceArticleApplication {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceArticleApplication.class,args);
    }
}
