package com.cloud.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author ycy
 */
@SpringBootApplication//(exclude = MongoAutoConfiguration.class)
@MapperScan(value = "com.cloud.admin.mapper")
@ComponentScan(basePackages = {"com.cloud","org.n3r"})
public class ServiceAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAdminApplication.class,args);
    }
}
