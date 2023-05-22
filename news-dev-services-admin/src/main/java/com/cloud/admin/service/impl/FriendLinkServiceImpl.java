package com.cloud.admin.service.impl;

import com.cloud.admin.repository.FriendLinkRepository;
import com.cloud.admin.service.FriendLinkService;
import com.cloud.enums.YesOrNo;
import com.cloud.mo.FriendLinkMO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 12:15 2023/5/22
 * @Modified by:ycy
 */
@Service
@Transactional
public class FriendLinkServiceImpl implements FriendLinkService {

    @Resource
    private FriendLinkRepository friendLinkRepository;

    @Override
    public void saveOrUpdateFriendLink(FriendLinkMO friendLinkMO) {
        friendLinkRepository.save(friendLinkMO);
    }

    @Override
    public List<FriendLinkMO> queryAllFriendLinkList() {
        return friendLinkRepository.findAll();
    }

    @Override
    public void delete(String linkId) {
        friendLinkRepository.deleteById(linkId);
    }

    @Override
    public List<FriendLinkMO> queryPortalAllFriendLinkList() {
        return friendLinkRepository.getAllByIsDelete(YesOrNo.YES.type);
    }
}
