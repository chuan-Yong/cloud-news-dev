package com.cloud.service.rule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 17:20 2023/5/28
 * @Modified by:ycy
 */
@Configuration
public class MyRule {

    @Bean
    public IRule iRule() {
        return new RandomRule();
    }
}
