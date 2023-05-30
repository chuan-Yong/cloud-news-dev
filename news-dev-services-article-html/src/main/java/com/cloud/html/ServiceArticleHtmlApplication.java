package com.cloud.html;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 23:56 2023/5/23
 * @Modified by:ycy
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan(value = "com.cloud.article.mapper")
@ComponentScan(basePackages = {"com.cloud","org.n3r"})
public class ServiceArticleHtmlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceArticleHtmlApplication.class,args);
    }
}