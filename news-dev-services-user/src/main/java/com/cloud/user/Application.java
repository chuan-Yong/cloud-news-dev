package com.cloud.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author ycy
 */
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@MapperScan(value = "com.cloud.user.mapper")
@ComponentScan(basePackages = {"com.cloud","org.n3r"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
