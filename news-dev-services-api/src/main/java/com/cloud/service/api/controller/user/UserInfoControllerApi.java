package com.cloud.service.api.controller.user;

import com.cloud.bo.UpdateUserInfoBo;
import com.cloud.grace.result.GraceJSONResult;
import com.cloud.service.config.MyServiceList;
import com.cloud.service.fallback.UserControllerFactoryFallback;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author 99141
 */
@Api(value = "用户信息controller",tags = {"用户信息controller"})
@RequestMapping("user")
@FeignClient(value = MyServiceList.SERVICE_USER,fallbackFactory = UserControllerFactoryFallback.class)
@Service
@Component
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
    @ApiOperation(value = "修改/完善用户信息", notes = "更新用户信息接口", httpMethod = "POST")
    @PostMapping("/updateUserInfo")
    GraceJSONResult updateUserInfo(@RequestBody @Valid UpdateUserInfoBo userInfoBo);

    /**
     * 获取用户基本信息
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取用户基本信息", notes = "获取用户基本信息接口", httpMethod = "POST")
    @PostMapping("/getUserInfo")
    GraceJSONResult getByUserInfo(@RequestParam String userId);

    @ApiOperation(value = "根据用户的ids查询用户列表", notes = "根据用户的ids查询用户列表", httpMethod = "GET")
    @GetMapping("/queryByIds")
    GraceJSONResult queryInfoByIds(@RequestParam String userIds);
}
