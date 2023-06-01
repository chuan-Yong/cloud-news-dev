package com.cloud.service.fallback;

import com.cloud.bo.UpdateUserInfoBo;
import com.cloud.grace.result.GraceJSONResult;
import com.cloud.grace.result.ResponseStatusEnum;
import com.cloud.service.api.controller.user.UserInfoControllerApi;
import com.cloud.vo.AppUserVo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 16:35 2023/5/30
 * @Modified by:ycy
 */
@Component
public class UserControllerFactoryFallback implements FallbackFactory<UserInfoControllerApi> {

    @Override
    public UserInfoControllerApi create(Throwable throwable) {
        return new UserInfoControllerApi() {

            @Override
            public GraceJSONResult getAccountInfo(String userId) {
                return GraceJSONResult.errorCustom(ResponseStatusEnum.SYSTEM_ERROR_FEIGN);
            }

            @Override
            public GraceJSONResult updateUserInfo(UpdateUserInfoBo userInfoBo) {
                return GraceJSONResult.errorCustom(ResponseStatusEnum.SYSTEM_ERROR_FEIGN);
            }

            @Override
            public GraceJSONResult getByUserInfo(String userId) {
                return GraceJSONResult.errorCustom(ResponseStatusEnum.SYSTEM_ERROR_FEIGN);
            }

            @Override
            public GraceJSONResult queryInfoByIds(String userIds) {
                System.out.println("进入客户端（服务调用者）的降级方法");
                List<AppUserVo> publisherList = new ArrayList<>();
                return GraceJSONResult.ok(publisherList);
            }
        };
    }
}
