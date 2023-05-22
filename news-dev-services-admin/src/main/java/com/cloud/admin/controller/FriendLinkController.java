package com.cloud.admin.controller;

import com.cloud.admin.service.FriendLinkService;
import com.cloud.bo.SaveFriendLinkBO;
import com.cloud.grace.result.GraceJSONResult;
import com.cloud.mo.FriendLinkMO;
import com.cloud.service.api.BaseController;
import com.cloud.service.api.controller.admin.FriendLinkControllerApi;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 12:03 2023/5/22
 * @Modified by:ycy
 */
@RestController
public class FriendLinkController extends BaseController implements FriendLinkControllerApi {

    @Resource
    private FriendLinkService friendLinkService;


    @Override
    public GraceJSONResult saveOrUpdateFriendLink(@Valid SaveFriendLinkBO saveFriendLinkBO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, Object> map = getErrors(result);
            return GraceJSONResult.errorMap(map);
        }

        //saveFriendLinkBO -> ***MO
        FriendLinkMO saveFriendLinkMO = new FriendLinkMO();
        BeanUtils.copyProperties(saveFriendLinkBO, saveFriendLinkMO);
        saveFriendLinkMO.setCreateTime(new Date());
        saveFriendLinkMO.setUpdateTime(new Date());

        friendLinkService.saveOrUpdateFriendLink(saveFriendLinkMO);

        return GraceJSONResult.ok();
    }

    @Override
    public GraceJSONResult getFriendLinkList() {
        return GraceJSONResult.ok(friendLinkService.queryAllFriendLinkList());
    }

    @Override
    public GraceJSONResult delete(String linkId) {
        friendLinkService.delete(linkId);
        return GraceJSONResult.ok();
    }

    @Override
    public GraceJSONResult queryPortalAllFriendLinkList() {
        return GraceJSONResult.ok(friendLinkService.queryPortalAllFriendLinkList());
    }
}
