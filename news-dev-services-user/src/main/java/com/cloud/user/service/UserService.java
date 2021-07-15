package com.cloud.user.service;

import com.cloud.bo.UpdateUserInfoBo;
import com.cloud.entity.AppUser;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 17:18 2021/6/20
 * @Modified by:ycy
 */
public interface UserService {

    AppUser getUser(String userId);

    /**
     * 判断用户是否存在
     */
    AppUser queryMobileIsExist(String mobile);

    /**
     * 创建用户
     */
    AppUser createUser(String mobile);

    /**
     * 更新用户
     */
    void updateUserInfo(UpdateUserInfoBo userInfoBo);
}
