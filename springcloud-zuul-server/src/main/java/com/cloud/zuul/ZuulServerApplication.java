package com.cloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 18:27 2023/5/28
 * @Modified by:ycy
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
                                  MongoAutoConfiguration.class})
@EnableZuulProxy
@ComponentScan(basePackages = {"com.cloud","org.n3r"})
@EnableEurekaClient
public class ZuulServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulServerApplication.class, args);
    }
}
