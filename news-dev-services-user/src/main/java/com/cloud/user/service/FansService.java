package com.cloud.user.service;

import com.cloud.enums.Sex;
import com.cloud.utils.PagedGridResult;
import com.cloud.vo.RegionRatioVO;

import java.util.List;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 22:52 2023/5/24
 * @Modified by:ycy
 */
public interface FansService {

    /**
     * 查询当前用户是否关注作家
     */
    public boolean isMeFollowThisWriter(String writerId, String fanId);


    /**
     * 关注成为粉丝
     */
    public void follow(String writerId, String fanId);

    /**
     * 粉丝取消关注
     */
    public void unfollow(String writerId, String fanId);

    /**
     * 查询我的粉丝数
     */
    public PagedGridResult queryMyFansList(String writerId,
                                           Integer page,
                                           Integer pageSize);

    /**
     * 查询粉丝数
     */
    public Integer queryFansCounts(String writerId, Sex sex);

    /**
     * 查询粉丝数
     */
    public List<RegionRatioVO> queryRegionRatioCounts(String writerId);
}
