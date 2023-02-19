package com.cloud.service.api.controller.user;

import com.cloud.bo.RegistLoginBo;
import com.cloud.grace.result.GraceJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(value = "获取短信验证码",tags = {"获取短信验证码controller"})
@RequestMapping("passport")
public interface PassPortControllerApi {


    @ApiOperation(value = "短信验证码的接口", notes = "短信验证码的接口", httpMethod = "GET")
    @GetMapping("/getSMSCode")
    GraceJSONResult getSMSCode(@RequestParam String mobile, HttpServletRequest servletRequest);

    @ApiOperation(value = "一键注册登录接口", notes = "一键注册登录接口", httpMethod = "POST")
    @PostMapping("/doLogin")
    GraceJSONResult doLogin(@RequestBody @Valid RegistLoginBo registLoginBo,
                            BindingResult bindingResult,
                            HttpServletRequest servletRequest,
                            HttpServletResponse servletResponse);
    @ApiOperation(value = "用户退出登录接口", notes = "用户退出登录接口", httpMethod = "POST")
    @PostMapping("/logout")
    public GraceJSONResult logOut(@RequestParam String userId ,
                                  HttpServletRequest servletRequest,
                                  HttpServletResponse servletResponse);
}
