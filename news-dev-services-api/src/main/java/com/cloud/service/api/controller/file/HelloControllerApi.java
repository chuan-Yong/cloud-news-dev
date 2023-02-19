package com.cloud.service.api.controller.file;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @author 99141
 */
@Api(value = "文件上传test",tags = {"文件上传测试controller"})
public interface HelloControllerApi {

    /**
     * 测试用例
     */
    @ApiOperation(value = "hello方法的接口", notes = "hello方法的接口", httpMethod = "GET")
    @GetMapping("/fiel-hello")
    public Object hello() ;

    /**
     * redis测试用例
     */
    @ApiOperation(value = "redis方法的接口", notes = "redis方法的接口", httpMethod = "GET")
    @GetMapping("/redis")
    public Object redis() ;

}
