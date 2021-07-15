package com.cloud.user.service.impl;

import com.cloud.bo.UpdateUserInfoBo;
import com.cloud.entity.AppUser;
import com.cloud.enums.Sex;
import com.cloud.enums.UserStatus;
import com.cloud.grace.result.ResponseStatusEnum;
import com.cloud.grace.result.exception.GraceException;
import com.cloud.user.mapper.AppUserMapper;
import com.cloud.user.service.UserService;
import com.cloud.utils.DateUtil;
import com.cloud.utils.DesensitizationUtil;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

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

    @Override
    public AppUser getUser(String userId) {
        Example example = new Example(AppUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", userId);
        return userMapper.selectOneByExample(criteria);
    }

    @Override
    public AppUser queryMobileIsExist(String mobile) {
        Example userExample = new Example(AppUser.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("mobile",mobile);
        return userMapper.selectOneByExample(criteria);
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
    }
}
