package com.cloud.user.service;

import com.cloud.utils.PagedGridResult;

import java.util.Date;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 16:05 2023/5/22
 * @Modified by:ycy
 */
public interface AppUserMngService {
    /**
     * 查询管理员列表
     */
    PagedGridResult queryAllUserList(String nickname,
                                            Integer status,
                                            Date startDate,
                                            Date endDate,
                                            Integer page,
                                            Integer pageSize);

    /**
     * 冻结用户账号，或者解除冻结状态
     */
    void freezeUserOrNot(String userId, Integer doStatus);

}
