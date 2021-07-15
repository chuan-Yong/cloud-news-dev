package com.cloud.service.config;

import com.cloud.service.interceptors.PassPortInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 23:27 2021/4/22
 * @Modified by:ycy
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public PassPortInterceptor passPortInterceptor() {
        return new PassPortInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passPortInterceptor())
                .addPathPatterns("/passport/getSMSCode");
    }
}
