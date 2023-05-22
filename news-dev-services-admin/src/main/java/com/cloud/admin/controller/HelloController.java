package com.cloud.admin.controller;

import com.cloud.grace.result.GraceJSONResult;
import com.cloud.service.api.controller.user.HelloControllerApi;
import com.cloud.utils.RedisOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 99141
 */
@RestController
public class HelloController implements HelloControllerApi {

    final static Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @Resource
    private RedisOperator redisOperator;

    @Override
    public Object hello() {
        LOGGER.info("debug:debug----");
        LOGGER.info("info:info----");
        LOGGER.info("error:error----");
        LOGGER.info("warning:warning----");
        return GraceJSONResult.ok();
    }

    @Override
    public Object redis() {
        redisOperator.set("age","18");
        return GraceJSONResult.ok(redisOperator.get("age"));
    }
}
