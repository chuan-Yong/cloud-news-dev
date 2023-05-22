package com.cloud.admin.controller;

import com.cloud.admin.service.AdminUserService;
import com.cloud.bo.AdminLoginBO;
import com.cloud.bo.NewAdminBO;
import com.cloud.entity.AdminUser;
import com.cloud.grace.result.GraceJSONResult;
import com.cloud.grace.result.ResponseStatusEnum;
import com.cloud.service.api.BaseController;
import com.cloud.service.api.controller.admin.AdminUserControllerApi;
import com.cloud.utils.PagedGridResult;
import com.cloud.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author ycy
 */
@RestController
public class AdminUserController extends BaseController implements AdminUserControllerApi {

    final static Logger LOGGER = LoggerFactory.getLogger(AdminUserController.class);

    @Resource
    private RedisOperator redisOperator;

    @Resource
    private AdminUserService adminService;

    @Override
    public GraceJSONResult adminLogin(AdminLoginBO adminLoginBo,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        //查询admin用户信息
        AdminUser adminUser = adminService.queryAdminUserByUserName(adminLoginBo.getUsername());
        //判断adminUser是否为空
        if (adminUser == null) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.ADMIN_NOT_EXIT_ERROR);
        }
        //判断密码是否一致
        boolean checkpw = BCrypt.checkpw(adminLoginBo.getPassword(), adminUser.getPassword());
        if(checkpw) {
            doLoginSettings(adminUser,request,response);
            return GraceJSONResult.ok();
        } else {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.ADMIN_NOT_EXIT_ERROR);
        }
    }

    public void doLoginSettings(AdminUser adminUser,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        //将token放入redis中
        String token = UUID.randomUUID().toString();
        redisOperator.set(REDIS_ADMIN_TOKEN + ":" + adminUser.getId(),token);
        //将token放入cookie中
        setCookie(request,response,"atoken",token,REDIS_MAXAGE);
        setCookie(request,response,"aid",adminUser.getId(),REDIS_MAXAGE);
        setCookie(request,response,"aname",adminUser.getAdminName(),REDIS_MAXAGE);
    }


    @Override
    public GraceJSONResult adminIsExist(String username) {
        checkAdminExist(username);
        return GraceJSONResult.ok();
    }


    public void checkAdminExist(String username) {
        AdminUser adminUser = adminService.queryAdminUserByUserName(username);
        if (adminUser != null) {
            GraceJSONResult.errorCustom(ResponseStatusEnum.ADMIN_USERNAME_EXIST_ERROR);
        }
    }


    @Override
    public GraceJSONResult addNewAdmin(NewAdminBO newAdminBO,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        //验证Bo中的用户名和密码是否为空，faceid 不为空表示人脸登录，否则需要输入密码
        if (StringUtils.isBlank(newAdminBO.getImg64())) {
            if (StringUtils.isBlank(newAdminBO.getPassword()) ||
                StringUtils.isBlank(newAdminBO.getConfirmPassword())) {
                return GraceJSONResult.errorCustom(ResponseStatusEnum.ADMIN_PASSWORD_ERROR);
            }
        }

        //判断两次密码是否一致
        if (StringUtils.isNotBlank(newAdminBO.getPassword())) {
            if (!StringUtils.equalsIgnoreCase(newAdminBO.getPassword(),newAdminBO.getConfirmPassword())) {
                return GraceJSONResult.errorCustom(ResponseStatusEnum.ADMIN_PASSWORD_ERROR);
            }
        }
        checkAdminExist(newAdminBO.getUsername());
        adminService.createAdminUser(newAdminBO);
        return GraceJSONResult.ok();
    }

    @Override
    public GraceJSONResult getAdminList(Integer page, Integer pageSize) {
        if (page == null) {
            page = COMMON_START_PAGE;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult result = adminService.queryAdminList(page, pageSize);
        return GraceJSONResult.ok(result);
    }


    @Override
    public GraceJSONResult adminLogout(String adminId,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        redisOperator.del(REDIS_ADMIN_TOKEN + ":" + adminId);
        //删除cookie
        deleteCookie(request, response, "atoken");
        deleteCookie(request, response, "aid");
        deleteCookie(request, response, "aname");
        return GraceJSONResult.ok();
    }
}
