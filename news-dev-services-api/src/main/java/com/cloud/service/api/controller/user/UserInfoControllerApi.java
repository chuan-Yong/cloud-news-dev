package com.cloud.service.api.controller.user;

import com.cloud.bo.UpdateUserInfoBo;
import com.cloud.grace.result.GraceJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author 99141
 */
@Api(value = "用户信息controller",tags = {"用户信息controller"})
@RequestMapping("user")
public interface UserInfoControllerApi {

    /**
     * 获取账户详情
     * @param userId ： 账户Id
     * @return
     */
    @ApiOperation(value = "获取用户详情", notes = "获取用户详情接口", httpMethod = "POST")
    @PostMapping("/getAccountInfo")
    GraceJSONResult getAccountInfo(@RequestParam String userId);

    /**
     * 获取账户详情
     * @param userInfoBo
     * @return
     */
    @ApiOperation(value = "修改/完善用户信息", notes = "更新用户信息接口", httpMethod = "GET")
    @PostMapping("/updateUserInfo")
    GraceJSONResult updateUserInfo(@RequestBody @Valid UpdateUserInfoBo userInfoBo,
                                   BindingResult bindingResult);

    /**
     * 获取用户基本信息
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取用户基本信息", notes = "获取用户基本信息接口", httpMethod = "POST")
    @PostMapping("/getUserInfo")
    GraceJSONResult getUserInfo(@RequestParam String userId);

}
