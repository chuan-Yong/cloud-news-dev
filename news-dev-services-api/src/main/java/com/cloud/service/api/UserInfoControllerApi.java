package com.cloud.service.api;

import com.cloud.bo.UpdateUserInfoBo;
import com.cloud.grace.result.GraceJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @author 99141
 */
@Api(value = "用户详情信息相关controller",tags = {"用户详情信息相关controller"})
@RequestMapping("user")
public interface UserInfoControllerApi {

    /**
     * 获取账户详情
     * @param userId ： 账户Id
     * @return
     */
    @ApiOperation(value = "获取用户详情", notes = "获取用户详情接口", httpMethod = "GET")
    @GetMapping("/getAccountInfo")
    GraceJSONResult getAccountInfo(String userId);

    /**
     * 获取账户详情
     * @param userInfoBo
     * @return
     */
    @ApiOperation(value = "修改/完善用户信息", notes = "更新用户信息接口", httpMethod = "GET")
    @GetMapping("/updateUserInfo")
    GraceJSONResult updateUserInfo(@RequestBody @Valid UpdateUserInfoBo userInfoBo,
                                   BindingResult bindingResult);

}
