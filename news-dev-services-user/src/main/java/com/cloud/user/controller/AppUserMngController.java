package com.cloud.user.controller;

import com.cloud.enums.UserStatus;
import com.cloud.grace.result.GraceJSONResult;
import com.cloud.grace.result.ResponseStatusEnum;
import com.cloud.service.api.BaseController;
import com.cloud.service.api.controller.user.AppUserMngControllerApi;
import com.cloud.user.service.AppUserMngService;
import com.cloud.user.service.UserService;
import com.cloud.utils.JsonUtils;
import com.cloud.utils.PagedGridResult;
import com.cloud.vo.PublisherVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 16:04 2023/5/22
 * @Modified by:ycy
 */

@RestController
public class AppUserMngController extends BaseController implements AppUserMngControllerApi {

    final static Logger logger = LoggerFactory.getLogger(AppUserMngController.class);

    @Resource
    private AppUserMngService appUserMngService;

    @Resource
    private UserService userService;

    @Override
    public GraceJSONResult queryAll(String nickname,
                                    Integer status,
                                    Date startDate,
                                    Date endDate,
                                    Integer page,
                                    Integer pageSize) {

        // System.out.println(startDate);
        // System.out.println(endDate);

        if (page == null) {
            page = COMMON_START_PAGE;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult result = appUserMngService.queryAllUserList(nickname,
                status,
                startDate,
                endDate,
                page,
                pageSize);

        return GraceJSONResult.ok(result);
    }

    @Override
    public GraceJSONResult userDetail(String userId) {
        return GraceJSONResult.ok(userService.getUser(userId));
    }

    @Override
    public GraceJSONResult freezeUserOrNot(String userId, Integer doStatus) {
        if (!UserStatus.isUserStatusValid(doStatus)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.USER_STATUS_ERROR);
        }
        appUserMngService.freezeUserOrNot(userId, doStatus);

        // 刷新用户状态：
        // 1. 删除用户会话，从而保障用户需要重新登录以后再来刷新他的会话状态
        // 2. 查询最新用户的信息，重新放入redis中，做一次更新
        redisOperator.del(REDIS_USER_INFO + ":" + userId);

        return GraceJSONResult.ok();
    }

    @Override
    public GraceJSONResult queryByIds(String userIds) {
        if (StringUtils.isBlank(userIds)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.USER_NOT_EXIST_ERROR);
        }
        List<String> userIdList = JsonUtils.jsonToList(userIds, String.class);
//        System.out.println(userIdList);

        List<PublisherVO> userList = userService.getUserList(userIdList);

        return GraceJSONResult.ok(userList);
    }
}
