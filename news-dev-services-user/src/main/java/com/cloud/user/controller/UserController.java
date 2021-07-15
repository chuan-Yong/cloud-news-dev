package com.cloud.user.controller;

import com.cloud.bo.UpdateUserInfoBo;
import com.cloud.entity.AppUser;
import com.cloud.grace.result.GraceJSONResult;
import com.cloud.grace.result.ResponseStatusEnum;
import com.cloud.service.api.BaseController;
import com.cloud.service.api.UserInfoControllerApi;
import com.cloud.user.service.UserService;
import com.cloud.vo.UserAccountInfoVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.Map;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 11:53 2021/6/27
 * @Modified by:ycy
 */
public class UserController extends BaseController implements UserInfoControllerApi {

    @Autowired
    private UserService userService;

    @Override
    public GraceJSONResult getAccountInfo(String userId) {
        //判断参数不为空
        if(StringUtils.isNotBlank(userId)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.UN_LOGIN);
        }
        AppUser user = getUser(userId);
        UserAccountInfoVo userAccountInfoVo = new UserAccountInfoVo();
        BeanUtils.copyProperties(user, userAccountInfoVo);
        return GraceJSONResult.ok(userAccountInfoVo);
    }

    public AppUser getUser(String userId) {
        return userService.getUser(userId);
    }

    @Override
    public GraceJSONResult updateUserInfo(
            @Valid UpdateUserInfoBo userInfoBo,
            BindingResult bindingResult) {
        //检验Bo
        if(bindingResult.hasErrors()) {
            Map<String, Object> errorsMap = getErrors(bindingResult);
            return GraceJSONResult.errorMap(errorsMap);
        }

        //更新用户信息
        userService.updateUserInfo(userInfoBo);
        return GraceJSONResult.ok();
    }
}


