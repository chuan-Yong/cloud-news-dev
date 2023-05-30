package com.cloud.user.controller;

import com.cloud.bo.UpdateUserInfoBo;
import com.cloud.entity.AppUser;
import com.cloud.grace.result.GraceJSONResult;
import com.cloud.grace.result.ResponseStatusEnum;
import com.cloud.service.api.BaseController;
import com.cloud.service.api.controller.user.UserInfoControllerApi;
import com.cloud.user.service.UserService;
import com.cloud.utils.JsonUtils;
import com.cloud.vo.AppUserVo;
import com.cloud.vo.UserAccountInfoVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 11:53 2021/6/27
 * @Modified by:ycy
 */
@RestController
public class UserInfoController extends BaseController implements UserInfoControllerApi {

    @Autowired
    private UserService userService;

    public AppUser getUser(String userId) {
        String userJson = redisOperator.get(REDIS_USER_INFO+":" + userId);
        AppUser user;
        if (StringUtils.isNotBlank(userJson)) {
            user =  JsonUtils.jsonToPojo(userJson,AppUser.class);
        } else {
            user = userService.getUser(userId);

            //由于用户信息基本不怎么改变，故将用户信息存放入redis中
            redisOperator.set(REDIS_USER_INFO + ":" + userId,
                    JsonUtils.objectToJson(user));
        }
        return user;
    }


    @Override
    public GraceJSONResult getAccountInfo(String userId) {
        //判断参数不为空
        //userId = "220903BN5H5N0094";
        if(StringUtils.isBlank(userId)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.UN_LOGIN);
        }
        AppUser user = getUser(userId);
        UserAccountInfoVo userAccountInfoVo = new UserAccountInfoVo();
        BeanUtils.copyProperties(user, userAccountInfoVo);
        return GraceJSONResult.ok(userAccountInfoVo);
    }


    @Override
    public GraceJSONResult updateUserInfo(@Valid UpdateUserInfoBo userInfoBo) {
        //检验Bo
        /*if(bindingResult.hasErrors()) {
            Map<String, Object> errorsMap = getErrors(bindingResult);
            return GraceJSONResult.errorMap(errorsMap);
        }*/

        //更新用户信息
        userService.updateUserInfo(userInfoBo);
        return GraceJSONResult.ok();
    }

    @Override
    public GraceJSONResult getByUserInfo(String userId) {
        //判断参数不为空
        if(StringUtils.isBlank(userId)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.UN_LOGIN);
        }
        AppUser user = getUser(userId);
        AppUserVo appUserVo = new AppUserVo();
        BeanUtils.copyProperties(user, appUserVo);
        return GraceJSONResult.ok(appUserVo);
    }

    @Override
    public GraceJSONResult queryInfoByIds(String userIds) {

        if (StringUtils.isBlank(userIds)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.USER_NOT_EXIST_ERROR);
        }

        List<AppUserVo> publisherList = new ArrayList<>();
        List<String> userIdList = JsonUtils.jsonToList(userIds, String.class);
        assert userIdList != null;
        for (String userId : userIdList) {
            // 获得用户基本信息
            AppUserVo userVO = getBasicUserInfo(userId);
            // 添加到publisherList
            publisherList.add(userVO);
        }

        return GraceJSONResult.ok(publisherList);
    }

    private AppUserVo getBasicUserInfo(String userId) {
        // 1. 根据userId查询用户的信息
        AppUser user = getUser(userId);
        // 2. 返回用户信息
        AppUserVo userVO = new AppUserVo();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}


