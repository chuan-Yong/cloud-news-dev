package com.cloud.user.service.impl;

import com.cloud.bo.UpdateUserInfoBo;
import com.cloud.entity.AppUser;
import com.cloud.enums.Sex;
import com.cloud.enums.UserStatus;
import com.cloud.grace.result.ResponseStatusEnum;
import com.cloud.grace.result.exception.GraceException;
import com.cloud.user.mapper.AppUserMapper;
import com.cloud.user.mapper.AppUserMapperCustom;
import com.cloud.user.service.UserService;
import com.cloud.utils.DateUtil;
import com.cloud.utils.DesensitizationUtil;
import com.cloud.utils.JsonUtils;
import com.cloud.utils.RedisOperator;
import com.cloud.vo.PublisherVO;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 17:22 2021/6/20
 * @Modified by:ycy
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public AppUserMapper userMapper;

    @Autowired
    public Sid sid;

    @Autowired
    public RedisOperator redisOperator;

    @Autowired
    public AppUserMapperCustom appUserMapperCustom;

    public static final String REDIS_USER_INFO = "redis_user_info";

    @Override
    public AppUser getUser(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public AppUser queryMobileIsExist(String mobile) {
        Example userExample = new Example(AppUser.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("mobile",mobile);
        return userMapper.selectOneByExample(userExample);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AppUser createUser(String mobile) {
        AppUser appUser = new AppUser();
        appUser.setId(sid.nextShort());
        appUser.setMobile(mobile);
        appUser.setNickname("用户:" + DesensitizationUtil.commonDisplay(mobile));

        appUser.setFace("");
        appUser.setBirthday(DateUtil.stringToDate("1900-01-01"));
        appUser.setSex(Sex.secret.type);
        appUser.setActiveStatus(UserStatus.INACTIVE.type);

        appUser.setCreatedTime(new Date());
        appUser.setTotalIncome(0);
        appUser.setUpdatedTime(new Date());
        userMapper.insert(appUser);
        return appUser;
    }

    @Override
    public void updateUserInfo(UpdateUserInfoBo userInfoBo) {

        String userId = userInfoBo.getId();
        //先删除redis缓存数据,然后在进行更新;
        redisOperator.del(REDIS_USER_INFO + ":" + userId);

        if(StringUtils.isBlank(userId)) {
            GraceException.display(ResponseStatusEnum.USER_STATUS_ERROR);
        }
        AppUser user = new AppUser();
        BeanUtils.copyProperties(userInfoBo,user);
        user.setUpdatedTime(new Date());
        user.setActiveStatus(UserStatus.ACTIVE.type);

        int result = userMapper.updateByPrimaryKeySelective(user);
        if(result != 1) {
            GraceException.display(ResponseStatusEnum.USER_UPDATE_ERROR);
        }

        //查询最新的用户基本信息;
        AppUser appUser = getUser(userId);
        redisOperator.set(REDIS_USER_INFO+":"+userId,
                JsonUtils.objectToJson(appUser));

        //缓存双删策略
        try {
            Thread.sleep(100);
            redisOperator.del(REDIS_USER_INFO + ":" + userId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PublisherVO> getUserList(List<String> userIdList) {
        Map<String, Object> map = new HashMap<>();
        map.put("userIdList", userIdList);
        List<PublisherVO> publisherList = appUserMapperCustom.getUserList(map);
        return publisherList;
    }

}
