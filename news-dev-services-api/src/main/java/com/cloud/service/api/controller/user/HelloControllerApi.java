package com.cloud.service.api.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @author 99141
 */
@Api(value = "controller标题",tags = {"helloWorld测试controller"})
public interface HelloControllerApi {

    /**
     * 测试用例
     */
    @ApiOperation(value = "hello方法的接口", notes = "hello方法的接口", httpMethod = "GET")
    @GetMapping("/hello")
    public Object hello() ;

    /**
     * redis测试用例
     */
    @ApiOperation(value = "redis方法的接口", notes = "redis方法的接口", httpMethod = "GET")
    @GetMapping("/redis")
    public Object redis() ;

}
