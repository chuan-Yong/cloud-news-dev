package com.cloud.admin.service;

import com.cloud.bo.NewAdminBO;
import com.cloud.entity.AdminUser;
import com.cloud.utils.PagedGridResult;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 16:48 2022/11/6
 * @Modified by:ycy
 */
public interface AdminUserService {

    AdminUser queryAdminUserByUserName(String userName);

    /**
     * 创建admin
     */
    void createAdminUser(NewAdminBO newAdminUser);

    PagedGridResult queryAdminList(Integer page, Integer pageSize);
}
