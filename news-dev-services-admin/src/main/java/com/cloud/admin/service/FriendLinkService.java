package com.cloud.admin.service;

import com.cloud.mo.FriendLinkMO;

import java.util.List;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 12:14 2023/5/22
 * @Modified by:ycy
 */
public interface FriendLinkService {
    /**
     * 新增或者更新友情链接
     */
    void saveOrUpdateFriendLink(FriendLinkMO friendLinkMO);

    /**
     * 查询友情链接
     */
    List<FriendLinkMO> queryAllFriendLinkList();

    /**
     * 删除友情链接
     */
    void delete(String linkId);

    /**
     * 首页查询友情链接
     */
    List<FriendLinkMO> queryPortalAllFriendLinkList();
}
