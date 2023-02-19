package com.cloud.admin.service;

import com.cloud.admin.mapper.AdminUserMapper;
import com.cloud.bo.NewAdminBO;
import com.cloud.entity.AdminUser;
import com.cloud.grace.result.ResponseStatusEnum;
import com.cloud.grace.result.exception.GraceException;
import com.cloud.utils.PagedGridResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 16:48 2022/11/6
 * @Modified by:ycy
 */
@Service
public class AdminUserServiceImpl implements AdminUserService{

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Autowired
    private Sid sid;

    @Override
    public AdminUser queryAdminUserByUserName(String userName) {
        Example example = new Example(AdminUser.class);
        example.createCriteria().andEqualTo("username",userName);
        return adminUserMapper.selectOneByExample(example);
    }

    @Transactional
    @Override
    public void createAdminUser(NewAdminBO newAdminUser) {
        AdminUser adminUser = new AdminUser();
        adminUser.setId(sid.nextShort());
        adminUser.setAdminName(newAdminUser.getAdminName());
        adminUser.setUsername(newAdminUser.getUsername());
        if (StringUtils.isNotBlank(adminUser.getPassword())) {
            String hashpw = BCrypt.hashpw(newAdminUser.getPassword(), BCrypt.gensalt());
            adminUser.setPassword(hashpw);
        }

        //判断是否开启人脸验证 如开启则关联用户进行保存
        if (StringUtils.isNotBlank(adminUser.getFaceId())) {
            adminUser.setFaceId(newAdminUser.getFaceId());
        }

        adminUser.setCreatedTime(new Date());
        adminUser.setUpdatedTime(new Date());

        int result = adminUserMapper.insert(adminUser);
        if(result != 1) {
            GraceException.display(ResponseStatusEnum.ADMIN_CREATE_ERROR);
        }
    }

    @Override
    public PagedGridResult queryAdminList(Integer page, Integer pageSize) {
        Example example = new Example(AdminUser.class);
        example.orderBy("createTime").desc();
        PageHelper.startPage(page, pageSize);
        List<AdminUser> adminUsers = adminUserMapper.selectByExample(example);
        return setterPagedGrid(adminUsers,page);
    }

    private PagedGridResult setterPagedGrid(List<?> adminUserList,Integer page){
        PageInfo<?> pageList = new PageInfo<>(adminUserList);
        PagedGridResult pagedGridResult = new PagedGridResult();
        pagedGridResult.setRows(adminUserList);
        pagedGridResult.setPage(page);
        pagedGridResult.setRecords(pageList.getPages());
        pagedGridResult.setTotal(pageList.getTotal());
        return pagedGridResult;
    }
}
