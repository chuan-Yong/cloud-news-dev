package com.cloud.files;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ycy
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.cloud","org.n3r"})
public class ServiceFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceFileApplication.class,args);
    }
}
